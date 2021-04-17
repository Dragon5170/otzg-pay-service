package com.otzg.wxpay.util;

import com.otzg.log.util.LogUtil;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;

/**
 * 读取证书
 * @author 小郑
 * 
 */
@SuppressWarnings("deprecation")
public class LoadSslUtil {
	private static LoadSslUtil readSSL = null;
	
	private LoadSslUtil(){
		
	}
	
	public static LoadSslUtil getInstance(){
		if(readSSL == null){
			readSSL = new LoadSslUtil();
		}
		return readSSL;
	}
//	/**
//	 *  读取 apiclient_cert.p12 证书
//	 * @return
//	 * @throws Exception
//	 */
//	public  SSLConnectionSocketFactory readCustomSSL() throws Exception{
//		/**
//		* 注意PKCS12证书 是从微信商户平台-》账户设置-》 API安全 中下载的
//		*/
//	    KeyStore keyStore  = KeyStore.getInstance("PKCS12");
//        /*
//	        此处要改
//	        wxconfig.SSLCERT_PATH :常量，指向证书的位置。例如：
//	        “E:/apache-tomcat-6.0.14/webapps-066/ayzk/WEB-INF/classes/apiclient_cert.p12”
//        */
//		String certPath="D:\\workspace\\otzg-pay\\target\\artifacts\\bcb_pay_war_exploded";
//        FileInputStream instream = new FileInputStream(new File(WxpayConfig.getCertPath(certPath,WxpayConfig.APPMCHID)));
//        try {
//         /*
//	        此处要改
//	        wxconfig.SSLCERT_PASSWORD:常量，指向证书的密码。例如：
//	        “123456..”
//        */
//            keyStore.load(instream, WxpayConfig.APPMCHID.toCharArray());
//        } finally {
//            instream.close();
//        }
//        /*
//	        此处要改
//	        wxconfig.mch_id:常量，指向商户号。例如：
//	        “123456789..”
//        */
//        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, WxpayConfig.APPMCHID.toCharArray()).build();
//
//        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory( sslcontext, new String[] { "TLSv1" }, null,
//                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
//        return sslsf;
//	}


	/**
	 * 加载证书
	 * @author G/2016年11月2日 下午5:36:39
	 * @param keyStoreUrl
	 * @param mchid
	 */
	public SSLConnectionSocketFactory loadCert(String keyStoreUrl,String mchid){
		FileInputStream instream = null;
		SSLContext sslcontext = null;
		try {
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			instream = new FileInputStream(new File(keyStoreUrl));
			keyStore.load(instream, mchid.toCharArray());
			sslcontext = org.apache.http.ssl.SSLContexts.custom().loadKeyMaterial(keyStore, mchid.toCharArray()).build();
		} catch (Exception e) {
			LogUtil.saveTradeLog("官方微信--证书加载失败!"+e.toString());
		} finally {
			try {
				if (instream != null) {
					instream.close();
				}
			} catch (IOException e) {
				LogUtil.saveTradeLog("官方微信--证书加载失败"+e.toString());
			}
		}
		return new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
	}



}