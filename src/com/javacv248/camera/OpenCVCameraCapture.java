package com.javacv248.camera;
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
import java.util.List;

import javax.imageio.ImageIO;  
import javax.imageio.stream.MemoryCacheImageInputStream;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import javax.swing.Timer;  

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_core.Point;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_core.Scalar;
import org.bytedeco.javacpp.opencv_highgui;
import org.bytedeco.javacpp.opencv_objdetect.CascadeClassifier;
import org.bytedeco.javacv.CanvasFrame;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

import com.javacv248.test.OpenCVFindFaceSAndCompare;
import com.javacv248.test.RectPoint;
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
public class OpenCVCameraCapture {  
    //public static String savedImageFile = "K:\\data\\face\\camera\\";  
    public static String savedImageFile = "D:\\tmp\\input\\face\\camera\\";
    
    private static int i=1;
    public static Rect rect=new Rect();
    public static Mat mat;
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
	   System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	   // System.out.println(Core.NATIVE_LIBRARY_NAME);
	}
    public static void main(String[] args) throws Exception {  
        //open camera source  
    	JavaCVFaceDetection();
    	//调用摄像头
    	
    	VideoCapture grabber = new VideoCapture(0);
        CanvasFrame canvasFrame = new CanvasFrame("Camera"); 
        Mat image =new Mat();
        grabber.read(image);
        int width = image.width();  
        int height = image.height();  
        canvasFrame.setCanvasSize(width, height); 
        final BufferedImage bImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
        Graphics2D bGraphics = bImage.createGraphics();      
        TimerAction timerAction = new TimerAction(canvasFrame);  
        final Timer timer=new Timer(1, timerAction);  
        timerAction.setTimer(timer); 
        //click the frame to capture an image  
        canvasFrame.getCanvas().addMouseListener(new MouseAdapter(){  
            public void mouseClicked(MouseEvent e){       
                timer.start(); //start animation  
                try {  
                	//opencv_highgui.imwrite(savedImageFile+i+".jpg", new Mat(image));
                	//标出人脸后存储
                	//opencv_core.rectangle(mat, new Point(rect.x(), rect.y()), new Point(rect.x() + rect.width(), rect.y() + rect.height()), new Scalar(0, 255));       
                	//opencv_highgui.cvSaveImage(savedImageFile+i+".jpg",mat.asIplImage());
                	//不标出人脸
                	ImageIO.write(bImage, "jpg", new File(savedImageFile+i+".jpg"));  
                	//
                	/*org.opencv.core.Mat aa=new org.opencv.core.Mat();
                	OpenCVFindFaceSAndCompare.openCVHistCompare(mat,mat);*/
                 i++;
                } catch (Exception e1) {  
                    e1.printStackTrace();  
                }                     
            }
        });  
        String xmlfilePath="javacv0.8\\libs\\haarcascade\\haarcascade_frontalface_alt2.xml";
        CascadeClassifier faceDetector;  
        while(canvasFrame.isVisible() && grabber.read(image)){  
            if(!timer.isRunning()) {
            	//将图片写进流，当点击的时候保存图片或者与数据库进行对比
            	//将图片画出
            	List<RectPoint> rects=OpenCVFindFaceSAndCompare.recognitionFaces(image);
            	Highgui.imwrite("tmp\\1.jpg", image);
            	IplImage ipl=opencv_highgui.cvLoadImage("tmp\\1.jpg");
            	opencv_highgui.cvSaveImage("tmp\\2.jpg", ipl);
            	org.bytedeco.javacpp.opencv_core.Mat mat=new org.bytedeco.javacpp.opencv_core.Mat(ipl);
            	bGraphics.drawImage(ipl.getBufferedImage(),null,0,0);
            	/*
            	 * BufferedImage matbuff=mat.getBufferedImage();
            
            	ByteArrayOutputStream baos=new ByteArrayOutputStream();
            	ImageIO.write(matbuff, "jpg", baos); 
            	byte[] bytes=baos.toByteArray();
            		
            	DataInputStream in = new DataInputStream(new ByteArrayInputStream(bytes));
            	BufferedImage saves=ImageIO.read(in);
            		
            	opencv_highgui.cvSaveImage(savedImageFile+"99.jpg",IplImage.createFrom(saves));
            	*/
            	/*faceDetector=new CascadeClassifier(xmlfilePath);
            	faceDetector.detectMultiScale(mat,rect);*/
            	for(RectPoint r:rects){
            		opencv_core.rectangle(mat, new Point(r.x, r.y), new Point(r.x + r.w, r.y + r.h), new Scalar(0, 255)); 
            	}
            		//剪切头像出来
            		//Mat cutimage=new Mat(mat,rect);
            		//将人脸标出
            	canvasFrame.showImage(mat.asIplImage());
            		//canvasFrame.showImage(opencv_highgui.cvLoadImage("D:\\CameraCapture\\huancun\\1.jpg"));
            		// bGraphics.drawImage(image.getBufferedImage(),null,0,0);
            		 
                  	//播放视频时 
            		//canvasFrame.showImage(image);   
            		//bGraphics.drawImage(image.getBufferedImage(),null,0,0);  
            }
        }  
          
        //release resources  
        //opencv_core.cvReleaseImage(ipl);     
        grabber.release();  
        canvasFrame.dispose();  
    }  
 }