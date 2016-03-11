package com.javacv248.test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class OpenCVFindFaceSAndCompare {
	private static String xml="javacv0.8\\libs\\haarcascade\\haarcascade_frontalface_alt.xml";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.loadLibrary("opencv_java248");
		List<Mat> list=RecognitionFace("K:\\data\\face\\1.jpg");
		double result=openCVHistCompare(list.get(0),list.get(0),true);
		System.out.println(result);
	}
	/**
	 * @description 从给的图片中 提取出所有人脸.
	 * @param image1
	 * @return 返回一个Mat的List，Mat表示人脸
	 */
	public static List<Mat> RecognitionFace(String image1) {
		//openCV ：找出人脸
	    List<Mat> cuts=new ArrayList<Mat>();
	    int i=1;
	    String xmlfilePath=xml;
	    
	    CascadeClassifier faceDetector = new CascadeClassifier(xmlfilePath);
	    Mat image = Highgui.imread(image1);
	    Size minSize = new Size(image.width()/10, image.height()/10);
		Size maxSize = new Size(image.width()/2, image.height()/2);
	    MatOfRect faceDetections = new MatOfRect();
	    faceDetector.detectMultiScale(image, faceDetections,1.1f, 4, 0,minSize, maxSize);
	    //提高准确率，
	    //faceDetector.detectMultiScale(faceDetections, objects, scaleFactor, minNeighbors, flags, minSize, maxSize);(image, faceDetections);
	    System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));
	    for (Rect rect : faceDetections.toArray()) {
	    	Mat cutimage=new Mat(image,rect);
	    	if(true){
	        	Highgui.imwrite("K:\\data\\facecut\\cut\\"+i+".jpg", cutimage);
	        	i++;
	        }
	    	//将脸部画出来
	        Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));             
	        cuts.add(cutimage);
	    }
	    //存储已经画出脸部的图片
	    Highgui.imwrite("K:\\data\\facecut\\cut\\jieguo.jpg", image);
	    return cuts;
	}
	public static List<RectPoint> recognitionFaces(Mat image) {
		//openCV ：利用openCV找出每个人脸的位置
	    List<RectPoint> cuts=new ArrayList<RectPoint>();
	    String xmlfilePath=xml;
	    CascadeClassifier faceDetector = new CascadeClassifier(xmlfilePath);
	    Size minSize = new Size(image.width()/10, image.height()/10);
		Size maxSize = new Size(image.width()/2, image.height()/2);
	    MatOfRect faceDetections = new MatOfRect();
	    faceDetector.detectMultiScale(image, faceDetections,1.1f, 4, 0,minSize, maxSize);
	   System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));
	    for (Rect rect : faceDetections.toArray()) {
	    	RectPoint r=new RectPoint(rect.x,rect.y,rect.width,rect.height);
	    	cuts.add(r);
	    }
	    return cuts;
	}
		
/**
 * @description 比对两张人脸个的相似度
 * @param image1
 * @param image2
 * @param isGray 是否采用灰度化方式
 * @return
 */
	public static double openCVHistCompare(Mat image1,Mat image2,boolean isGray){
	      //Mat m = Highgui.imread(image1,Highgui.CV_LOAD_IMAGE_COLOR);
	     // Mat m2 = Highgui.imread(image2,Highgui.CV_LOAD_IMAGE_COLOR);
	      List<Mat> images = new ArrayList<Mat>();
	      List<Mat> images2 = new ArrayList<Mat>();
	      if(isGray){
	    	  //使用灰色图比较
	    	  //System.out.println("Use Gray!");
	    	  Mat gray1 = new Mat(image1.size(),CvType.CV_8UC1);
	    	  Imgproc.cvtColor(image1,gray1,Imgproc.COLOR_RGB2GRAY);
	    	  Mat gray2 = new Mat(image2.size(),CvType.CV_8UC1);
	    	  Imgproc.cvtColor(image2,gray2,Imgproc.COLOR_RGB2GRAY);
	    	  images.add(gray1);
	    	  images2.add(gray2);
	      }else{
	        images.add(image1);
	        images2.add(image2); 
	      }
	      MatOfInt channels= new MatOfInt(0);
	      MatOfInt histSize = new MatOfInt(256);
	      MatOfFloat ranges= new MatOfFloat(0,256);
	      Mat hist = new Mat();
	      Mat hist2 = new Mat();
	      //计算直方图  (似乎是将多张图(list)计算出一个直方图，再跟另一个多个图计算出来的直方图进行比较)
	     
	      Imgproc.calcHist(images, channels, new Mat(), hist, histSize, ranges);
	      Imgproc.calcHist(images2, channels, new Mat(), hist2, histSize, ranges); 
	      double d=Imgproc.compareHist(hist, hist2, Imgproc.CV_COMP_CORREL);
	      return d;
	  }

}
