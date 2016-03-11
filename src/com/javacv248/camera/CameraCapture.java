package com.javacv248.camera;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_objdetect.CascadeClassifier;
import org.bytedeco.javacpp.opencv_core.Mat;

import java.awt.Graphics2D;  
import java.awt.event.ActionEvent;  
import java.awt.event.ActionListener;  
import java.awt.event.MouseAdapter;  
import java.awt.event.MouseEvent;  
import java.awt.image.BufferedImage;  
import java.awt.image.DataBufferByte;
import java.awt.image.MemoryImageSource;
import java.awt.image.Raster;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;   
import java.io.InputStream;

import javax.imageio.ImageIO;  
import javax.imageio.stream.MemoryCacheImageInputStream;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import javax.swing.Timer;  

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.Scalar;
import org.bytedeco.javacpp.opencv_core.Size;
import org.bytedeco.javacpp.opencv_highgui;
import org.bytedeco.javacpp.opencv_highgui.CvCapture;
import org.bytedeco.javacpp.opencv_highgui.VideoCapture;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.opencv.core.Core;
import org.opencv.highgui.Highgui;

import com.javacv248.test.OpenCVFindFaceSAndCompare;
import com.pactera.javaCVapi.MysqlTest;
/** 
 *  
 * Use JavaCV/OpenCV to capture camera images 
 *  
 * There are two functions in this demo: 
 * 1) show real-time camera images  
 * 2) capture camera images by mouse-clicking anywhere in the JFrame,  
 * the jpg file is saved in a hard-coded path.  
 *  
 * @author ljs 
 * 2011-08-19 
 * 
 */  
public class CameraCapture {  
    public static String savedImageFile = "K:\\data\\face\\camera\\";  
    private static int i=1;
    public static Rect rect=new Rect();
    public static Mat mat;
    public static BufferedImage bImage;
    public static int width=0;
    public static int height=0;
    //timer for image capture animation  
    static class TimerAction implements ActionListener {  
        private Graphics2D g;  
        private CanvasFrame canvasFrame;  
        private int width,height;  
          
        private int delta=10;  
        private int count = 0;  
          
        private Timer timer;  
        public void setTimer(Timer timer){  
            this.timer = timer;  
        }  
           
        public TimerAction(CanvasFrame canvasFrame){  
            this.g = (Graphics2D)canvasFrame.getCanvas().getGraphics();   
            this.canvasFrame = canvasFrame;  
            this.width = canvasFrame.getCanvas().getWidth();  
            this.height = canvasFrame.getCanvas().getHeight();
        }  
        public void actionPerformed(ActionEvent e) {  
            int offset = delta*count;  
            if(width-offset>=offset && height-offset >= offset) {          
                g.drawRect(offset, offset, width-2*offset, height-2*offset);  
                canvasFrame.repaint();  
                count++;  
            }else{  
                //when animation is done, reset count and stop timer.  
                timer.stop();  
                count = 0;  
            }              
        }  
    }  
	public static void JavaCVFaceDetection(){
		System.out.println("Hello, OpenCV");
	   // System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	   // System.out.println(Core.NATIVE_LIBRARY_NAME);
	}
    public static void main(String[] args) throws Exception {  
        //两种方式加载library ,第一个是为了打jar包 
		System.load("H:\\scalaWorkpace\\JavaCV0.8_OpenCV248_2.0\\javacv0.8\\libs\\x64\\opencv_java248.dll");
    	//System.loadLibrary("opencv_java248");
		JavaCVFaceDetection();
    	CvCapture cc=new CvCapture();
    	//调用摄像头
    	
    	OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
    	
    	//调用视频
    	//FrameGrabber grabber=new OpenCVFrameGrabber("E:\\迅雷下载\\1.mp4");
        grabber.start();  
        CanvasFrame canvasFrame = new CanvasFrame("Camera"); 
        IplImage image = grabber.grab();
        width = image.width();  
        height = image.height();  
        canvasFrame.setCanvasSize(width, height); 
        bImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
        Graphics2D bGraphics = bImage.createGraphics();      
        TimerAction timerAction = new TimerAction(canvasFrame);  
        final Timer timer=new Timer(1, timerAction);  
        timerAction.setTimer(timer); 
        //click the frame to capture an image  
        canvasFrame.getCanvas().addMouseListener(new MouseAdapter(){  
            public void mouseClicked(MouseEvent e){       
                timer.start();   
                try {  
                	//opencv_highgui.imwrite(savedImageFile+i+".jpg", new Mat(image));
                	//标出人脸后存储
                	//opencv_core.rectangle(mat, new Point(rect.x(), rect.y()), new Point(rect.x() + rect.width(), rect.y() + rect.height()), new Scalar(0, 255));       
                	//opencv_highgui.cvSaveImage(savedImageFile+i+".jpg",mat.asIplImage());
                	//不标出人脸
                	//ImageIO.write(bImage, "jpg", new File("tmp\\camera.jpg"));  
                	
                	//将图片按照人脸识别的坐标截取出来。拿去比对
                	//需要将切的人脸存储下来，然后再读出来
                	Mat cutimage=new Mat(mat,rect);
                	ImageIO.write(cutimage.getBufferedImage(), "jpg", new File("tmp\\output\\cutCamera.jpg")); 
                	//需要将切的存储下来，然后再读出来
                	org.opencv.core.Mat mat=Highgui.imread("tmp\\output\\cutCamera.jpg");
                	//从数据库拿到图片的字节流，然后存为图片，然后再读出来（数据库存储的必须是人脸）
                	MysqlTest mysqlTest =new MysqlTest();
                	byte[] bytes=mysqlTest.getPhotoBytes("select * from test where p='test'");
                	InputStream in = new ByteArrayInputStream(bytes);
            		BufferedImage saves=ImageIO.read(in);
            		//将mysql中读出来的bytes转为ipmage图片存储在临时目录下
            		ImageIO.write(saves, "jpg", new File("tmp\\IpmToMatTmp\\iplTmp.jpg"));
            		//读取临时目录的图片转成openCV的Mat
            		org.opencv.core.Mat mat2=Highgui.imread("tmp\\IpmToMatTmp\\iplTmp.jpg");
                	System.out.println(OpenCVFindFaceSAndCompare.openCVHistCompare(mat,mat2,true));
                 
                	
                	i++;
                } catch (Exception e1) {  
                    e1.printStackTrace();  
                }                     
            }
        });  
        //为了打jar包
        String xmlfilePath="H:\\scalaWorkpace\\JavaCV0.8_OpenCV248_2.0\\javacv0.8\\libs\\haarcascade\\haarcascade_frontalface_alt2.xml";
        //String xmlfilePath="javacv0.8\\libs\\haarcascade\\haarcascade_frontalface_alt2.xml";
        
        CascadeClassifier faceDetector;  
        while(canvasFrame.isVisible() && (image=grabber.grab()) != null){  
            if(!timer.isRunning()) {
            		//将图片写进流，当点击的时候保存图片
            	//讲图片画出	
            	bGraphics.drawImage(image.getBufferedImage(),null,0,0); 
            		
            	mat=new Mat(image);
            		//获取图片的流，将图片写进bytes
            	/*
            	 * BufferedImage matbuff=mat.getBufferedImage();
            
            	ByteArrayOutputStream baos=new ByteArrayOutputStream();
            	ImageIO.write(matbuff, "jpg", baos); 
            	byte[] bytes=baos.toByteArray();
            		
            	DataInputStream in = new DataInputStream(new ByteArrayInputStream(bytes));
            	BufferedImage saves=ImageIO.read(in);
            		
            	opencv_highgui.cvSaveImage(savedImageFile+"99.jpg",IplImage.createFrom(saves));
            	*/
            		
            		faceDetector=new CascadeClassifier(xmlfilePath);
            		//如果未识别出人脸，则rect会使用上一次的数据，所以在这里要把rect初始化
            		rect=new Rect();
            		faceDetector.detectMultiScale(mat,rect, 1.1f, 4, 0,new Size(width/20, height/20),new Size(width, height));
            		opencv_core.rectangle(mat, new Point(rect.x(), rect.y()), new Point(rect.x() + rect.width(), rect.y() + rect.height()), new Scalar(0, 255));
            		//剪切头像出来
            		Mat cutimage=new Mat(mat,rect);
            		//将人脸标出
            		canvasFrame.showImage(mat.asIplImage());
            		//canvasFrame.showImage(opencv_highgui.cvLoadImage("D:\\CameraCapture\\huancun\\1.jpg"));
            		// bGraphics.drawImage(image.getBufferedImage(),null,0,0);
            		 
                  	//播放视频时 
            		//canvasFrame.showImage(image);   
            		bGraphics.drawImage(image.getBufferedImage(),null,0,0);  
            }
        }  
          
        //release resources  
        opencv_core.cvReleaseImage(image);     
        grabber.stop();  
        canvasFrame.dispose();  
    }  
 }