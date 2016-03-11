package com.pactera.javaCVapi;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
public class JavaCVFaceDetections {
	//private static String result_photos="pictures\\result_photos\\";
	//图片路径
	private static String sourcePath="D:\\tmp\\input\\face\\huancun";
	//人脸识别路径
	private static String saveCutPath="D:\\CameraCapture\\saveCutPicture\\";
	//比对图片路径，指定一张图片
	private static String comparePath="D:\\tmp\\input\\face\\yuanshi";
	//灰化图片存放路径
	//private static String grayPath="D:\\CameraCapture\\grayPicture\\";
	private boolean useGray=false;
	public void setUseGray(boolean save){
		this.useGray=save;
	}
	public static void main(String[] args) throws Exception {
		 
		JavaCVFaceDetections javaCVFaceDetection=new JavaCVFaceDetections();
		//new JavaCVFaceDetectAPI().recognitionFace_javacv(comparePath);
		//javaCVFaceDetection.runCompare(comparePath, sourcePath);
		//javaCVFaceDetection.runCompare_javacv();
		new JavaCVFaceDetectAPI().javaCV_HistCompare();
		
  }
	public JavaCVFaceDetections(){
		System.out.println("Hello, OpenCV");
	    System.loadLibrary("opencv_java248");
	    //System.loadLibrary("javacpp");
	    System.out.println(Core.NATIVE_LIBRARY_NAME);
	}
	/**
     * 
     * @author linMingQiang
     * @param sourcePath 数据库图片路径
     * @param ComparePath 比对图片路径
     *  @describe 一张图片于源库图片比较，返回各个图片的相似度
     */
	 public void runCompare(String comparePath,String sourcePath){
		 File sp=new File(sourcePath);
		 File[] source = sp.listFiles();
		 JavaCVFaceDetectAPI d=new JavaCVFaceDetectAPI();
		 List<Mat> s=new ArrayList<Mat>();
		 for(int i=0; i<source.length; i++){
			 //System.out.println(source[i].getPath().replace("\\", "\\\\"));
			 s.add(Highgui.imread(source[i].getPath().replace("\\", "\\\\")));
		 }
		 List<Mat> compares=null;//d.RecognitionFace(comparePath);
		 if(compares.size()==0){
			 System.out.println("未识别出脸");
			 System.exit(0);
		 }
		 Mat compare=compares.get(0);
		 Map<String,String> result=new HashMap<String,String>();
		 for(int i=0; i<source.length; i++){
				 double value=d.openCVHistCompare(compare, s.get(i));
				 System.out.println(source[i].getName()+" = "+new DecimalFormat("0.00").format(value*100)+"%");
				 result.put(source[i].getName(), new DecimalFormat("0.00").format(value*100)+"%");
			 
		 }
		 
	}
	 /**
	   * 
	   * @author linMingQiang
	   * @param sourcePath 数据库图片路径
	   * @param ComparePath 比对图片路径
	   * @param saveCutPath 保存识别出的头像的路径
	   *  @describe 一张图片于源库图片比较，返回各个图片的相似度
	   */
	/*public Map<String, String> runCompare(String sourcePath,String comparePath,String saveCutPath){
		Map<String, String> result=new HashMap<String, String>();
		File sp=new File(sourcePath);
	    //File cp=new File(comparePath);
	    File scp=new File(saveCutPath);
	    //String cpName=cp.getName();
	    File[] source = sp.listFiles();
	    DetectFaceDemo d=new DetectFaceDemo();
	    for(int i=0; i<source.length; i++){
	    	//将图片路径经过人脸识别剪切头像后，保存在指定的cutpicturePath目录下
	    	d.runOpemCV249(source[i].getPath().replace("\\\\", "\\"));
	    }
	    //保存compare的头像
	    d.runOpemCV249(comparePath);
	    
	    File[] saveCut = scp.listFiles();
	    for(int i=0; i<saveCut.length; i++){
	    	//比较的两张图片，第三个参数表示是否进行灰化
	    	   double value=d.openCVHistCompare(sourcePath, saveCut[i]);
	    	  
	    	   System.out.println(cpName+" <<Compare TO>>  "+saveCut[i].getName()+"="+new DecimalFormat("0.00").format(value*100)+"%");
	    	   result.put(cpName+" <<Compare TO>>  "+saveCut[i].getName(), new DecimalFormat("0.00").format(value*100)+"%");
	    }
	    return result;
	}*/
  /*public void runCompare_javacv(){
	  	File sp=new File(sourcePath);
		File[] source = sp.listFiles();
		 List<org.bytedeco.javacpp.opencv_core.Mat> s=new ArrayList<org.bytedeco.javacpp.opencv_core.Mat>();
		 for(int i=0; i<source.length; i++){
			 IplImage kk=opencv_highgui.cvLoadImage(source[i].getPath().replace("\\", "\\\\"));
			 s.add(new org.bytedeco.javacpp.opencv_core.Mat(kk));
		 }
		 JavaCVFaceDetectAPI d=new JavaCVFaceDetectAPI();
		 //File cp=new File(comparePath);
		 List<org.bytedeco.javacpp.opencv_core.Mat> compares=d.recognitionFace_javacv(comparePath);
		 if(compares.size()==0){
			 System.out.println("未识别出脸");
			 System.exit(0);
		 }
		 org.bytedeco.javacpp.opencv_core.Mat compare=compares.get(0);
		 Map<String,String> result=new HashMap<String,String>();
		 for(int i=0; i<source.length; i++){
				 double value=d.openCVHistCompare_javacv(compare, s.get(i));
				 System.out.println(source[i].getName()+" = "+new DecimalFormat("0.00").format(value*100)+"%");
				 result.put(source[i].getName(), new DecimalFormat("0.00").format(value*100)+"%");
			 }
		 }*/
}
