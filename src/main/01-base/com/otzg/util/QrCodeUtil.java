package com.otzg.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;


/**
 * 二维码生成工具
 *  @author G/2015-3-13
 *
 */
public class QrCodeUtil {
	private static final int width = 300;// 默认二维码宽度
	private static final int height = 300;// 默认二维码高度
	private static final String format = "png";// 默认二维码文件格式

	/**
	 * 单位二维码生成，并生成图片文件
	 * @author G/2015-2-4 上午10:25:00
	 * @param contents  二维码内容
	 * @param width		二维码宽度
	 * @param height	二维码高度
	 * @return	true|false 是否创建成功
	 */
	public final static boolean createQrCode(String contents,String format,File file,int width,int height) {
		boolean r = false;
		try {
			Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType,Object>();  
			// 指定纠错等级    
	        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
	        // 指定编码格式    
	        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");    
			BitMatrix matrix = new MultiFormatWriter().encode(contents,BarcodeFormat.QR_CODE,width,height,hints);
	        MatrixToImageWriter.writeToFile(matrix,format,file);
	        r=true;
		 } catch (Exception e) {
		     e.printStackTrace();
		 }
		return r;
	}
	
//  /**二维码解码*/
//	 public static String decode(String imgPath) {    
//	        BufferedImage image = null;    
//	        Result result = null;    
//	        try {    
//	            image = ImageIO.read(new File(imgPath));    
//	            if (image == null) {    
//	                System.out.println("the decode image may be not exit.");    
//	            }    
//	            LuminanceSource source = new BufferedImageLuminanceSource(image);    
//	            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));    
//	    
//	            Map<DecodeHintType, Object> hints = new HashMap<DecodeHintType, Object>();   
//	            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");    
//	    
//	            result = new MultiFormatReader().decode(bitmap, hints);    
//	            return result.getText();    
//	        } catch (Exception e) {    
//	            e.printStackTrace();    
//	        }    
//	        return null;    
//	    }  
	
	/**
	 * 条形码编码
	 * @author G/2017年7月28日 下午3:50:44
	 * @param contents
	 * @param format
	 * @param file
	 * @param width
	 * @param height
	 */
	public static boolean createBarCode(String contents,String format,File file,int width, int height){    
		boolean r = false;
		if (width < 200) width = 200;
        if (height < 50) height = 50;
        try {    
        	Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType,Object>();  
	        // 指定编码格式    
	        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8"); 
            BitMatrix matrix = new MultiFormatWriter().encode(contents,BarcodeFormat.CODE_128,width,height,hints);    
            MatrixToImageWriter.writeToFile(matrix,format,file);
            r=true;
        } catch (Exception e) {    
            e.printStackTrace();    
        }    
        return r;
    }   
	
//    /**条形码解码*/
//    public static String decode(String imgPath) {    
//        BufferedImage image = null;    
//        Result result = null;    
//        try {    
//            image = ImageIO.read(new File(imgPath));    
//            if (image == null) {    
//                System.out.println("the decode image may be not exit.");    
//            }    
//            LuminanceSource source = new BufferedImageLuminanceSource(image);    
//            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));    
//    
//            result = new MultiFormatReader().decode(bitmap, null);    
//            return result.getText();    
//        } catch (Exception e) {    
//            e.printStackTrace();    
//        }    
//        return null;    
//    }  
	
	/**
	 * 图片合成方法,参数为两个文件
	 * @param bgfile
	 * @param fgfile
	 * @return 字节码
	 */
	public static byte[] admix(byte[] bgfile,File fgfile){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ByteArrayInputStream image = new ByteArrayInputStream(bgfile);
			BufferedImage fgBufferedImage = Thumbnails.of(fgfile).forceSize(40,40).asBufferedImage();
			Thumbnails.of(image).scale(1f).watermark(Positions.CENTER, fgBufferedImage,1f).toOutputStream(baos);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return baos.toByteArray();
	}
 
	/**
	 * 单位二维码生成，并生成图片文件
	 * @author G/2015-2-4 上午10:25:00
	 * @param contents  二维码内容
	 * @param width		二维码宽度
	 * @param height	二维码高度
	 * @return FileOutputStream 文件缓冲
	 */
	public final static byte[] getQrCodeImg(String contents,String format,int width,int height) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			Hashtable<EncodeHintType, String> hints = new Hashtable<>();
	        hints.put(EncodeHintType.CHARACTER_SET, "utf-8"); 
	        hints.put(EncodeHintType.MARGIN,"1");
			BitMatrix matrix = new MultiFormatWriter().encode(contents,BarcodeFormat.QR_CODE,width,height,hints);
	        MatrixToImageWriter.writeToStream(matrix, format,baos);
		 } catch (Exception e) {
		     e.printStackTrace();
		 }
		return baos.toByteArray();
	}

	public final static byte[] getQrCodeImg(String contents){
		return getQrCodeImg(contents,format,width,height);
	}


	public final static byte[] getBarCodeImg(String contents,String format,int width,int height) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if (width < 200) width = 200;
        if (height < 50) height = 50;
        try {    
        	Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType,Object>();  
	        // 指定编码格式    
	        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8"); 
            BitMatrix matrix = new MultiFormatWriter().encode(contents,BarcodeFormat.CODE_128,width,height,hints);    
            MatrixToImageWriter.writeToStream(matrix,format,baos);
        } catch (Exception e) {    
            e.printStackTrace();    
        }    
		return baos.toByteArray();
	}

//	/**
//	 * 二维码解码
//	 * @param image
//	 * @return
//	 */
//	public final static String parseQrCode(BufferedImage image){
//		try {
//			BufferedImageLuminan1ceSource source = new BufferedImageLuminanceSource(image);
//			BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
//			Hashtable hints = new Hashtable();
////			hints.put(DecodeHintType.CHARACTER_SET,"utf-8");
//			//优化精度
//			hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
//			//复杂模式，开启PURE_BARCODE模式
////			hints.put(DecodeHintType., Boolean.TRUE);
//
//			Result result =  new MultiFormatReader().decode(bitmap);
//			String resultStr = result.getText();
//			return resultStr;
//		} catch (NotFoundException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

	public static void main(String args[]) {
//		File file = new File("d:/ewm.jpg");
//		BufferedImage image = ImageUtil.getBufferedImage(file);
//		print(parseQrCode(file));

	}


}
