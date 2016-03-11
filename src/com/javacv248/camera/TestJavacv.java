package com.javacv248.camera;

import java.util.ArrayList;
import java.util.List;

import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.CvMemStorage;
import org.bytedeco.javacpp.opencv_core.CvRect;
import org.bytedeco.javacpp.opencv_core.CvSeq;
import org.bytedeco.javacpp.opencv_core.CvSize;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_core.MatOp_Base;
import org.bytedeco.javacpp.opencv_core.Rect;
import org.bytedeco.javacpp.opencv_highgui;
import org.bytedeco.javacpp.opencv_highgui.CvCapture;
import org.bytedeco.javacpp.opencv_highgui.VideoCapture;
import org.bytedeco.javacpp.opencv_objdetect;
import org.bytedeco.javacpp.opencv_objdetect.CascadeClassifier;
import org.bytedeco.javacpp.opencv_objdetect.CvHaarClassifier;
import org.bytedeco.javacpp.opencv_objdetect.CvHaarClassifierCascade;
import org.bytedeco.javacv.FrameGrabber.Exception;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameGrabber;


public class TestJavacv {
	public static void JavaCVFaceDetection(){
		System.out.println("Hello, OpenCV");
	    //System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		//System.setProperty("java.library.path","libs\\x64\\");
	   // System.out.println(Core.NATIVE_LIBRARY_NAME);
	}
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		new TestJavacv().test();
	}	
public void test() throws Exception{
	//播放视频的
	JavaCVFaceDetection();
	//OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
	//grabber.start();
    //create a frame for real-time image display  
	//opencv_highgui.cvNamedWindow("test");
	CvCapture cc=opencv_highgui.cvCaptureFromAVI("D:\\tmp\\input\\face\\2.mp4");
	double fps=opencv_highgui.cvGetCaptureProperty(cc, opencv_highgui.CV_CAP_PROP_FPS);
	System.out.println(10/fps);
	IplImage c;
	List<IplImage> s=new ArrayList<opencv_core.IplImage>();
	//int i=10;
		while(true){
			int key =opencv_highgui.cvWaitKey((int)(10/fps));
			if(opencv_highgui.cvGrabFrame(cc)==1){
				c=opencv_highgui.cvRetrieveFrame(cc);
				s.add(c);
				System.out.println(s.size());
				//opencv_highgui.cvShowImage("test", c);
			}
			
		}
		//opencv_highgui.cvDestroyAllWindows();
}
}