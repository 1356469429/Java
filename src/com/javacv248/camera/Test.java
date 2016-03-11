package com.javacv248.camera;

import java.awt.FileDialog;
import java.util.ArrayList;
import java.util.List;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_highgui.VideoCapture;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.opencv_highgui;
import org.bytedeco.javacpp.opencv_imgproc;
import org.bytedeco.javacpp.opencv_objdetect.CvHaarClassifierCascade;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.opencv.highgui.Highgui;



public class Test {  
    public static void main(String[] args) throws Exception {  
    	//VideoCapture ss=new VideoCapture(0);
       //new Test().recognizeFromCam(); 
    	// System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    	System.loadLibrary("opencv_java248");
    	 new Test().opencvVid(); 
    	//IplImage ip=opencv_highgui.cvLoadImage("D:\\CameraCapture\\comparePicture\\2.jpg");
    	//org.bytedeco.javacpp.opencv_core.Mat a=new org.bytedeco.javacpp.opencv_core.Mat(ip);
    	//BytePointer bp=a.data();
    	//org.opencv.core.Mat ss=Highgui.imread("D:\\CameraCapture\\comparePicture\\2.jpg");
    	//org.opencv.core.Mat ima=new org.opencv.core.MatOfFloat(a.asCvMat().data_fl().asBuffer().array());
    	
    	//opencv_core.cvSetData(ss,opencv_core.cvLoad(a.data()) , 1);
    	//Highgui.imencode(".jpg", ss, ima);
    	//System.out.println(Imgproc.compareHist(ss, ss,Imgproc.CV_COMP_CORREL));
    	//Highgui.imwrite("D:\\CameraCapture\\comparePicture\\22321.jpg",ima);
    	
    }  
    public void recognizeFromCam() throws Exception 
    {  
    	List<IplImage> s=new ArrayList<opencv_core.IplImage>();
          OpenCVFrameGrabber  grabber = null;  
          IplImage pFrame=null;  
          grabber=new OpenCVFrameGrabber("E:\\迅雷下载\\1.mp4"); 
          grabber.start(); 
          List<String> ss=grabber.list;
          System.out.println(ss.size());
       /*while(true){
    	   pFrame = grabber.grab();
    	   s.add(pFrame);
           //opencv_highgui.cvSaveImage("D:\\CameraCapture\\comparePicture\\test.jpg", pFrame);    
    	   System.out.println(s.size());
    	   
    	   if(s.size()==2000){
    		   opencv_highgui.cvSaveImage("D:\\CameraCapture\\comparePicture\\test.jpg", pFrame);    
    		   break;
    	   }
       }*/
          
    }  
    public static void opencvVid(){
    	org.opencv.core.Mat mat=new org.opencv.core.Mat();
    	org.opencv.highgui.VideoCapture s=new org.opencv.highgui.VideoCapture(0);
    	
for(int i=0;i<1000;i++){
	s.read(mat);
	Highgui.imwrite("K:\\data\\facecut\\cut\\"+4+".jpg", mat);
	try {
		Thread.sleep(1000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
    	
    	
    }
}  