package com.javacv248.test;

import java.util.ArrayList;
import java.util.List;

import org.bytedeco.javacpp.Pointer;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_core.Mat;
import org.bytedeco.javacpp.opencv_highgui;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point3;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.objdetect.CascadeClassifier;

/**
 * @description OpenCV和JavaCV相结合
 * @author Administrator
 *
 */
public class JavaCVFaceFind {
	private static String xml="javacv0.8\\libs\\haarcascade\\haarcascade_frontalface_alt.xml";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.loadLibrary("opencv_java248");
		String image="K:\\data\\face\\1.jpg";
		recognitionFace_javacv(image);
	}
	/**
	 * javacv无法识别一张图中的多张两，所以需要opencv找出图中的所有人脸，并标出连的rect（范围），然后再调用javacv
	 * 讲所有的人脸都标出来
	 * @param image1
	 * @return
	 */
	public static List<Mat> recognitionFace_javacv(String image1){
		//改进的人脸识别
		List<Mat> cuts=new ArrayList<Mat>();
		int i=100;
	    String xmlfilePath=xml;
	    CascadeClassifier faceDetector = new CascadeClassifier(xmlfilePath);
	    org.opencv.core.Mat image = Highgui.imread(image1);  
	    MatOfRect faceDetections = new MatOfRect();
	    faceDetector.detectMultiScale(image, faceDetections, 1.1f, 4, 0,new Size(image.width()/10, image.width()/10),new Size(image.width()/2, image.width()/2));
	    System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));
	    System.out.println("识别的脸数："+faceDetections.toList().size());
	    IplImage ip=opencv_highgui.cvLoadImage(image1);
	    org.bytedeco.javacpp.opencv_core.Mat mat=new org.bytedeco.javacpp.opencv_core.Mat(ip);
	    for (Rect rect : faceDetections.toArray()) {
	    	//得到每张脸的位置坐标
	    	//把人脸画出来
	        opencv_core.rectangle(mat, new org.bytedeco.javacpp.opencv_core.Point(rect.x, rect.y), new org.bytedeco.javacpp.opencv_core.Point(rect.x + rect.width, rect.y + rect.height), new org.bytedeco.javacpp.opencv_core.Scalar(0,255,0,0));       
	        org.bytedeco.javacpp.opencv_core.Rect ccc=new org.bytedeco.javacpp.opencv_core.Rect(rect.x, rect.y, rect.width, rect.height);
	        org.bytedeco.javacpp.opencv_core.Mat cutimage=new org.bytedeco.javacpp.opencv_core.Mat(opencv_highgui.imread(image1),ccc);        
	        org.bytedeco.javacpp.opencv_core.Mat cutmat=new org.bytedeco.javacpp.opencv_core.Mat(mat,ccc);
	        if(true){
	        	opencv_highgui.imwrite("K:\\data\\facecut\\cut\\"+i+".jpg", cutimage);
	        	i++;
	        }
	        cuts.add(cutmat);
	    }  
	    opencv_highgui.imwrite("K:\\data\\facecut\\cut\\jieguo.jpg", mat);
	    return cuts;
	}
}
