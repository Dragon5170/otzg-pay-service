package com.otzg.base;

import com.otzg.util.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.*;

public abstract class BaseController extends BaseBean {
	/**
	 * 获取参数
	 * @param name
	 * @return
	 */
	protected final Object getPara(String name) {
		return getRequest().getParameter(name);
    }

	/**
	 * 获取request
	 * @return
	 */
	protected final HttpServletRequest getRequest() {
		 return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    }

	/**
	 * 获取Session对象
	 * @return 
	 */
	protected final HttpSession getSession(){
		return getRequest().getSession();
	}
	
	/**
	 * 获取上下文
	 * @return
	 */
    protected final ServletContext getServletContext(){
        return getSession().getServletContext();
    }
    /**
     * 获取response对象
     * @return
     */
    protected final HttpServletResponse getResponse(){
    	return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
    }
    
    /**
	 * WEBSERVER发送纯文本格式的信息，包含JSON/text/plain等
	 * 客户端应做对应格式的解析, 如  $.ajax 应包含 dataType:'json|text'等
	 */
	protected final void sendJson(Object json) {
		getResponse().setContentType("text/plain;charset=UTF-8");
		try{
			getResponse().getWriter().write(json.toString());
		}catch (final IOException ex){
			P("sendJson is wrong :"+ex);
		}
	}

	/**
	 * 发送json数据
	 * @param b true|false
	 * @param c 错误码
	 * @param m 提示信息
	 */
	protected final void sendJson(boolean b,String c,String m){
		sendJson(ResultUtil.getJson(b,c,m));
	}

	protected final void sendJson(boolean b,String c,String m,Object o){
		sendJson(ResultUtil.getJson(b,c,m,o));
	}

   	/**
   	 * 发送html
   	 */
   	public final void sendHtml(Object html) {
   		getResponse().setContentType("text/html;charset=UTF-8");
   		try{
   			getResponse().getWriter().write(html.toString());
   		}catch (final IOException ex){
   			P("sendHTML is wrong :"+ex);
			sendJson(false,RespTips.DATA_NULL.code,RespTips.DATA_NULL.tips);
   		}
   	}

	/**
	 * 发送Img
	 * @param image 图片
	 * @param style 图片格式
	 */
	public final void sendImage(BufferedImage image,String style) {
		try {
			ImageIO.write(image,style,getResponse().getOutputStream());
		}catch (IOException e){
			P("sendImage is wrong :"+e);
			sendJson(false,RespTips.DATA_NULL.code,RespTips.DATA_NULL.tips);
		}
	}

	/**
	 * 发送Img
	 * @param style 图片格式
	 */
	public final void sendImage(byte[] bytes,String style) {
		HttpServletResponse response = getResponse();
		try {
			BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
			//指定头部为图片
			if(CheckUtil.isEmpty(style)){
				response.setContentType("image/jpeg");
			}else{
				response.setContentType(style);
			}
			bos.write(bytes);
			bos.flush();
			bos.close();
		} catch (IOException e) {
			P("sendImage is wrong :"+e);
			sendJson(false,RespTips.DATA_NULL.code,RespTips.DATA_NULL.tips);
		}
	}

	/**
	 * 根据文件保存路径获取文件并发送
	 * @param filePath
	 */
	public final void sendImage(String filePath){
		if(CheckUtil.isEmpty(filePath)){
			sendJson(false,RespTips.DATA_NULL.code,RespTips.DATA_NULL.tips);
		}else{
			sendImage(new File(filePath));
		}
	}

	public final void sendImage(File image){
		try {
			FileInputStream in = new FileInputStream(image);
			int length = in.available();
			byte data[] = new byte[length];
			getResponse().setContentLength(length);
			String fileType = image.getName().substring(image.getName().lastIndexOf(".") + 1).toLowerCase();
			getResponse().setContentType("image/"+fileType);
			in.read(data);
			OutputStream out = getResponse().getOutputStream();
			out.write(data);
			out.flush();
		} catch (IOException e) {
			P("sendImage is wrong :"+e);
			sendJson(false,RespTips.DATA_NULL.code,RespTips.DATA_NULL.tips);
		}
	}

	//文件分片
	public final void sendFile(String name,byte[] buff){
		HttpServletResponse response = getResponse();
		try {

			//处理中文名称在各个浏览器内的编码问题
			String userAgent = getRequest().getHeader("User-Agent");
			// 针对IE或者以IE为内核的浏览器：
			if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
				name = java.net.URLEncoder.encode(name, "UTF-8");
			} else {
				// 非IE浏览器的处理：
				name = new String(name.getBytes("UTF-8"), "ISO-8859-1");
			}

			//设置返回头部
			response.reset();
			response.setContentType("application/x-download");
			response.addHeader("Content-disposition","attachment; filename=\""+name+"\"");
			response.addHeader("Content-Length", "" + buff.length);
			response.setContentType("application/octet-stream");


			//3.通过response获取ServletOutputStream对象(out)
			OutputStream out = new BufferedOutputStream(response.getOutputStream());
			InputStream in = new BufferedInputStream(new ByteArrayInputStream(buff));

			byte[] buffer = new byte[1024];
			int i = -1;
			//不能一次性读完，大文件会内存溢出（不能直接fis.read(buffer);）
			while ((i = in.read(buffer)) != -1) {
				out.write(buffer, 0, i);
			}
			in.close();
			out.flush();
			out.close();
		}catch (IOException e){
			P("sendFile is wrong :"+e);
			sendJson(false,RespTips.DATA_NULL.code,RespTips.DATA_NULL.tips);
		}
	}

   	public final void sendFile(String name,String path){
		try {
			File file = new File(path);
			if(!file.exists()){
				sendJson(false,RespTips.DATA_NULL.code,RespTips.DATA_NULL.tips);
				return;
			}

			//处理中文名称在各个浏览器内的编码问题
			String userAgent = getRequest().getHeader("User-Agent");
			// 针对IE或者以IE为内核的浏览器：
			if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
				name = java.net.URLEncoder.encode(name, "UTF-8");
			} else {
				// 非IE浏览器的处理：
				name = new String(name.getBytes("UTF-8"), "ISO-8859-1");
			}

			//设置返回头部
			getResponse().reset();
			getResponse().addHeader("Content-disposition","attachment; filename=\""+name+"\"");
			getResponse().addHeader("Content-Length", "" + file.length());
			getResponse().setContentType("application/octet-stream");


			//3.通过response获取ServletOutputStream对象(out)
			ServletOutputStream out = getResponse().getOutputStream();
			FileInputStream in = new FileInputStream(file);
			//5.创建数据缓冲区
			byte[] buffer = new byte[1024];
			int b = 0;
			while (b != -1){
				b = in.read(buffer);
				//4.写到输出流(out)中
				out.write(buffer,0,b);
			}
			in.close();
			out.close();
			out.flush();

		}catch (IOException e){
			P("sendFile is wrong :"+e);
			sendJson(false,RespTips.DATA_NULL.code,RespTips.DATA_NULL.tips);
		}
	}
   	
   	/**
   	 * 发生url跳转
   	 */
   	protected final void sendRedirect(String url) {
   		try {
   			getResponse().sendRedirect(url);
   		} catch (IOException e) {
   			P("sendRedirect is wrong :"+e);
   		}
   	}
   	
   	/**
	 * 向浏览器发送参数错误
	 * @author G/2018年1月6日
	 */
	protected final void sendParamError(){
		sendJson(false, RespTips.PARAM_ERROR.code, RespTips.PARAM_ERROR.tips);
	}
	protected final void sendAccessFail() {
		sendJson(false, RespTips.NO_AUTH.code, RespTips.NO_AUTH.tips);
	}
	/**
	 * 向浏览器发送没有数据
	 * @author G/2018年1月6日
	 */
	protected final void sendDataNull(){
		sendJson(false, RespTips.DATA_NULL.code,RespTips.DATA_NULL.tips);
	}

	/**
	 * 向浏览器发送操作不成功
	 * @author G/2018年1月6日
	 */
	protected final void sendFail(){
		sendJson(false, RespTips.ERROR_CODE.code,RespTips.ERROR_CODE.tips);
	}
	protected final void sendFail(String m){
		sendJson(false, RespTips.ERROR_CODE.code,m);
	}

	/**
	 * 向浏览器发送操作成功
	 * @author G/2018年1月6日
	 */
	protected final void sendSuccess(){
		sendJson(true, RespTips.SUCCESS_CODE.code, RespTips.SUCCESS_CODE.tips);
	}

	/**
	 * 向浏览器发送操作成功
	 * @author G/2018年1月6日
	 */
	protected final void sendSuccess(Object o){
		sendJson(true, RespTips.SUCCESS_CODE.code, RespTips.SUCCESS_CODE.tips, o);
	}

}
