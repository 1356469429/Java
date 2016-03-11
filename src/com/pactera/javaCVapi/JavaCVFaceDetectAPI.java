package com.pactera.javaCVapi;
import java.util.ArrayList;
import java.util.List;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.CvHistogram;
import org.bytedeco.javacpp.opencv_core.CvMat;
import org.bytedeco.javacpp.opencv_core.CvRect;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_core.MatVector;
import org.bytedeco.javacpp.FloatPointer;
import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.opencv_highgui;
import org.bytedeco.javacpp.opencv_imgproc;
import org.bytedeco.javacpp.opencv_legacy.CvFaceData;
import org.bytedeco.javacpp.opencv_objdetect.CvHaarClassifierCascade;
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
public class JavaCVFaceDetectAPI {
	private  Mat image;
	private static String xml="libs\\haarcascade\\haarcascade_frontalface_alt.xml";
	private boolean useGray=false;
	private boolean saveCut=false;
	public boolean isSaveCut() {
		return saveCut;
	}
	public void setSaveCut(boolean saveCut) {
		this.saveCut = saveCut;
	}
	public boolean isUseGray() {
		return useGray;
	}
	public void setUseGray(boolean useGray) {
		this.useGray = useGray;
	}
	
	/**
     * 
     * @author linMingQiang
     * @param image1 ��ȡʶ���ͼƬ����
     *  @describe ��һ��ͼƬ��ʶ�������
     *  @return List<Mat> ��������ΪList
     */
	/*public List<Mat> RecognitionFace(String image1) {
	//openCV ：找出人脸
    List<Mat> cuts=new ArrayList<Mat>();
    String xmlfilePath=xml;
    CascadeClassifier faceDetector = new CascadeClassifier(xmlfilePath);
    image = Highgui.imread(image1);
    MatOfRect faceDetections = new MatOfRect();
    faceDetector.detectMultiScale(image, faceDetections);
    //提高准确率，
    //faceDetector.detectMultiScale(faceDetections, objects, scaleFactor, minNeighbors, flags, minSize, maxSize);(image, faceDetections);
    
    System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));
    for (Rect rect : faceDetections.toArray()) {
        Core.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));       
        Mat cutimage=new Mat(image,rect);        
        if(saveCut){
        	Highgui.imwrite("D:\\CameraCapture\\comparePicture\\11.jpg", cutimage);
        	
        	//Highgui.imwrite("D:\\CameraCapture\\comparePicture\\12.jpg", image);
        }
        cuts.add(cutimage);
    }
    Highgui.imwrite("D:\\CameraCapture\\comparePicture\\12.jpg", image);
    return cuts;
}*/
	public double openCVHistCompare(Mat image1,Mat image2){
      //Mat m = Highgui.imread(image1,Highgui.CV_LOAD_IMAGE_COLOR);
     // Mat m2 = Highgui.imread(image2,Highgui.CV_LOAD_IMAGE_COLOR);
      List<Mat> images = new ArrayList<Mat>();
      List<Mat> images2 = new ArrayList<Mat>();
      if(useGray){
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
public void javaCV_HistCompare(){
	  float minRange= 0f;
	  float maxRange= 255f;
	  float[] minMax = new  float[]{minRange, maxRange};
	  float[][] ranges = new float[][]{minMax};
	  int[] i=new int[]{256};
	  //创建直方图  
	  
	CvHistogram ch=opencv_imgproc.cvCreateHist(1, i, opencv_core.CV_HIST_ARRAY,ranges, 1);
	CvHistogram ch2=opencv_imgproc.cvCreateHist(1, i,opencv_core.CV_HIST_ARRAY,ranges, 1);
	//CvHistogram ch=opencv_imgproc.cvCreateHist(1, i,opencv_core.CV_HIST_ARRAY, f, 0);
	//CvHistogram ch2=opencv_imgproc.cvCreateHist(1, i,opencv_core.CV_HIST_ARRAY, f, 0);
	 IplImage ip=opencv_highgui.cvLoadImage("D:\\tmp\\input\\face\\huancun\\camera1.jpg",opencv_imgproc.COLOR_RGB2GRAY);
	//opencv_core.CreateImage(new CvSize(image.width(),image.height()), 0, 0);
	 IplImage[] s=new IplImage[2];
	 org.bytedeco.javacpp.opencv_core.Mat mat1=new org.bytedeco.javacpp.opencv_core.Mat();
	 org.bytedeco.javacpp.opencv_core.Mat mat2=new org.bytedeco.javacpp.opencv_core.Mat();
	 //opencv_imgproc.cvtColor(new org.bytedeco.javacpp.opencv_core.Mat(ip), mat1, opencv_imgproc.COLOR_RGB2GRAY);
	 //opencv_imgproc.cvtColor(new org.bytedeco.javacpp.opencv_core.Mat(ip), mat2, opencv_imgproc.COLOR_RGB2GRAY);
	 //System.out.println(ip.arrayChannels());
	 s[0]=mat1.asIplImage();
	 s[1]=mat2.asIplImage();
	 
	//计算直方图 
	opencv_imgproc.cvCalcHist(ip, ch,0,null);
	opencv_imgproc.cvCalcHist(ip, ch2,0,null);
	//归一化直方图 
	 opencv_imgproc.cvNormalizeHist(ch, 1); 
	 opencv_imgproc.cvNormalizeHist(ch2, 1); 
	 
	 System.out.println(opencv_imgproc.cvCompareHist(ch, ch2, opencv_imgproc.CV_COMP_CORREL));
	 }

/**
 *  javacv无法识别一张图中的多张两，所以需要opencv找出图中的所有人脸，并标出连的rect（范围），然后再调用javacv
 * @param image1
 * @return
 */
/*public List<org.bytedeco.javacpp.opencv_core.Mat> recognitionFace_javacv(String image1){
	//改进的人脸识别
	List<org.bytedeco.javacpp.opencv_core.Mat> cuts=new ArrayList<org.bytedeco.javacpp.opencv_core.Mat>();
	int i=100;
    String xmlfilePath=xml;
    CascadeClassifier faceDetector = new CascadeClassifier(xmlfilePath);
    image = Highgui.imread(image1);  
    MatOfRect faceDetections = new MatOfRect();
    faceDetector.detectMultiScale(image, faceDetections);
    //提高准确率，
    //faceDetector.detectMultiScale(faceDetections, objects, scaleFactor, minNeighbors, flags, minSize, maxSize);(image, faceDetections);
    //System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));
    System.out.println("识别的脸数："+faceDetections.toList().size());
    IplImage ip=opencv_highgui.cvLoadImage(image1);
    org.bytedeco.javacpp.opencv_core.Mat mat=new org.bytedeco.javacpp.opencv_core.Mat(ip);
    for (Rect rect : faceDetections.toArray()) {
    	//得到每张脸的位置坐标
        opencv_core.rectangle(mat, new org.bytedeco.javacpp.opencv_core.Point(rect.x, rect.y), new org.bytedeco.javacpp.opencv_core.Point(rect.x + rect.width, rect.y + rect.height), new org.bytedeco.javacpp.opencv_core.Scalar(0, 255));       
        org.bytedeco.javacpp.opencv_core.Rect ccc=new org.bytedeco.javacpp.opencv_core.Rect(rect.x, rect.y, rect.width, rect.height);
        org.bytedeco.javacpp.opencv_core.Mat cutimage=new org.bytedeco.javacpp.opencv_core.Mat(opencv_highgui.imread(image1),ccc);        
        org.bytedeco.javacpp.opencv_core.Mat cutmat=new org.bytedeco.javacpp.opencv_core.Mat(mat,ccc);
        if(true){
        	opencv_highgui.imwrite("D:\\CameraCapture\\comparePicture\\"+i+".jpg", cutimage);
        	i++;
        }
        cuts.add(cutmat);
    }  
    opencv_highgui.imwrite("D:\\CameraCapture\\comparePicture\\jieguo.jpg", mat);
    return cuts;
}*/
public static double javaCV_HistCompare(IplImage image1,IplImage image2) {
	int[] hist_size = {256};
	IplImage gray1 = opencv_core.cvCreateImage(opencv_core.cvGetSize(image1), 8, 1);
	opencv_imgproc.cvCvtColor(image1, gray1, opencv_imgproc.CV_BGR2GRAY);
	CvHistogram gray_hist = opencv_imgproc.cvCreateHist(1,hist_size, opencv_core.CV_HIST_ARRAY);
	opencv_imgproc.cvCalcHist(gray1, gray_hist);
	
	IplImage gray2 = opencv_core.cvCreateImage(opencv_core.cvGetSize(image2), 8, 1);
	
	opencv_imgproc.cvCvtColor(image2, gray2, opencv_imgproc.CV_BGR2GRAY);
	CvHistogram gray_hist2 = opencv_imgproc.cvCreateHist(1, hist_size, opencv_core.CV_HIST_ARRAY);
	
	opencv_imgproc.cvCalcHist(gray2, gray_hist2);
	return opencv_imgproc.cvCompareHist(gray_hist, gray_hist2, opencv_imgproc.CV_COMP_CORREL);
}
public double openCVHistCompare_javacv(org.bytedeco.javacpp.opencv_core.Mat image1,org.bytedeco.javacpp.opencv_core.Mat image2){
	/*List<org.bytedeco.javacpp.opencv_core.Mat> images = new ArrayList<org.bytedeco.javacpp.opencv_core.Mat>();
    List<org.bytedeco.javacpp.opencv_core.Mat> images2 = new ArrayList<org.bytedeco.javacpp.opencv_core.Mat>();
    */
	
	MatVector images=new MatVector();
	MatVector images2=new MatVector();
	images.put(image1);
	images2.put(image2);
    if(true){
  	  //使用灰色图比较
  	  //System.out.println("Use Gray!");
    org.bytedeco.javacpp.opencv_core.Mat gray1 = new org.bytedeco.javacpp.opencv_core.Mat(image1.size(),CvType.CV_8UC2);
  	  opencv_imgproc.cvtColor(image1,gray1,Imgproc.COLOR_RGB2GRAY);
  	org.bytedeco.javacpp.opencv_core.Mat gray2 = new org.bytedeco.javacpp.opencv_core.Mat(image2.size(),CvType.CV_8UC2);
  	opencv_imgproc.cvtColor(image2,gray2,Imgproc.COLOR_RGB2GRAY);
  	  images.put(gray1);
  	  images2.put(gray2);
  	  //images.add(gray1);
  	  //images2.add(gray2);
    }else{
    	//images.put(image1);
    	//images2.put(image2);
        //images.add(image1);
        //images2.add(image2); 
    }
    int[] a={0};
    int[] b={256};
    float[] c={0,256};
    IntPointer channels=new IntPointer(0);
    IntPointer histSize=new IntPointer(256);
    FloatPointer ranges=new FloatPointer(0,256);
    org.bytedeco.javacpp.opencv_core.Mat hist=new org.bytedeco.javacpp.opencv_core.Mat();
    org.bytedeco.javacpp.opencv_core.Mat hist2=new org.bytedeco.javacpp.opencv_core.Mat();
    opencv_imgproc.calcHist(images , channels, new org.bytedeco.javacpp.opencv_core.Mat(), hist, histSize, ranges);
    opencv_imgproc.calcHist(images2 , channels, new org.bytedeco.javacpp.opencv_core.Mat(), hist2, histSize, ranges);
    
    double d=opencv_imgproc.compareHist(hist, hist2, opencv_imgproc.CV_COMP_CORREL);
    //MatOfInt channels= new MatOfInt(0);
   // MatOfInt histSize = new MatOfInt(256);
    //MatOfFloat ranges= new MatOfFloat(0,256);
   // Mat hist = new Mat();
   // Mat hist2 = new Mat();
    //计算直方图  (似乎是将多张图(list)计算出一个直方图，再跟另一个多个图计算出来的直方图进行比较)
    //Imgproc.calcHist(images2, channels, new Mat(), hist2, histSize, ranges); 
    //double d=Imgproc.compareHist(hist, hist2, Imgproc.CV_COMP_CORREL);
    return d;
}
public void hashCompare(){
	/*感知哈希算法(perceptual hash algorithm)，它的作用是对每张图像生成一个“指纹”(fingerprint)字符串，然后比较不同图像的指纹。结果越接近，就说明图像越相似。 
	实现步骤： 
	1. 缩小尺寸：将图像缩小到8*8的尺寸，总共64个像素。这一步的作用是去除图像的细节，只保留结构/明暗等基本信息，摒弃不同尺寸/比例带来的图像差异； 
	2. 简化色彩：将缩小后的图像，转为64级灰度，即所有像素点总共只有64种颜色； 
	3. 计算平均值：计算所有64个像素的灰度平均值； 
	4. 比较像素的灰度：将每个像素的灰度，与平均值进行比较，大于或等于平均值记为1，小于平均值记为0； 
	5. 计算哈希值：将上一步的比较结果，组合在一起，就构成了一个64位的整数，这就是这张图像的指纹。组合的次序并不重要，只要保证所有图像都采用同样次序就行了； 
	6. 得到指纹以后，就可以对比不同的图像，看看64位中有多少位是不一样的。在理论上，这等同于”汉明距离”(Hamming distance,在信息论中，两个等长字符串之间的汉明距离是两个字符串对应位置的不同字符的个数)。
	        如果不相同的数据位数不超过5，就说明两张图像很相似；如果大于10，就说明这是两张不同的图像。
	*/
	Mat srmat=Highgui.imread("we.jpg");
	Mat dstmat=new Mat();
	Imgproc.resize(srmat, dstmat, new Size(8, 8), 0, 0, Imgproc.INTER_CUBIC);
	Imgproc.cvtColor(dstmat, dstmat, Imgproc.COLOR_BGR2GRAY);
	int i1=0,i2=0;
	int arr1[], arr2[];
	


}
}
