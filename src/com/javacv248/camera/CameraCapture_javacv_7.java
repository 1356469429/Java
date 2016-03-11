package com.javacv248.camera;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_highgui;
import org.bytedeco.javacpp.opencv_highgui.CvCapture;


public class CameraCapture_javacv_7 {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		CvCapture cvCapture=opencv_highgui.cvCreateFileCapture("E:\\迅雷下载\\1.mp4");
		IplImage image;
		while(true){
			if(opencv_highgui.cvGrabFrame(cvCapture)==1){
				image=opencv_highgui.cvRetrieveFrame(cvCapture);
				opencv_highgui.cvSaveImage("D:\\CameraCapture\\huancun\\"+Math.random()+".jpg", image);
				//opencv_highgui.cvShowImage("ss", image);
			}
		}
	}
}
