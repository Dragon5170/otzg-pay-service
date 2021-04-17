/**
 *
 */
package com.otzg.wxpay.util;


import com.otzg.log.util.LogUtil;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

//import java.security.cert.Certificate;

/**
 * @author G/2016年10月14日
 */
public class TokenThread implements Runnable {

    public static AccessToken accessToken = null;
    public static String jsapiTicket = null;

    public void run() {
        while (true) {
            try {
                accessToken = this.getAccessToken();
                if (accessToken != null) {
                    accessToken.getAccessToken();
                    LogUtil.saveTradeLog("进入线程accessToken"+accessToken.getAccessToken());
                    System.out.println(accessToken.getAccessToken());
                    jsapiTicket = WxPayUtil.getJsapiTicket(accessToken.getAccessToken());
                    System.out.println(jsapiTicket);
                    LogUtil.saveTradeLog("返回值jsapiTicket"+jsapiTicket);
                    //获取到access_token 休眠7000秒
                    Thread.sleep(6500 * 1000);
                } else {
                    LogUtil.saveTradeLog("如果有accessToken"+accessToken.getAccessToken());
                    //获取的access_token为空 休眠3秒
                    Thread.sleep(1000 * 3);
                }
            } catch (Exception e) {
                System.out.println("发生异常：" + e.getMessage());
                e.printStackTrace();
                try {
                    //发生异常休眠1秒
                    Thread.sleep(1000 * 10);
                } catch (Exception e1) {
                }
            }
        }
    }

    /**
     * 获取access_token
     *
     * @return
     */
    private AccessToken getAccessToken() {
//        LogUtil.saveTradeLog("进入线程accessToken");
//        //正式
//        String Url = String.format(WxpayConfig.WXPAY_ACCESS_TOKEN, WxpayConfig.GZHAPPID, WxpayConfig.GZHAPPSECRET);
//        String result = getHttpsResponse(Url, "");
//        //9月12日修改待测试
//        if (result != null && result.length() > 0) {
//            LogUtil.saveTradeLog("进入线程accessToken"+result);
//            //response.getWriter().println(result);
//            JSONObject json = JSONObject.fromObject(result);
//            AccessToken token = new AccessToken();
//            token.setAccessToken(json.getString("access_token"));
//            token.setExpiresin(json.getInt("expires_in"));
//            return token;
//        }
        return null;
    }

    @SuppressWarnings("static-access")
    public String getHttpsResponse(String hsUrl, String requestMethod) {
        URL url;
        InputStream is = null;
        String resultData = "";
        try {
            url = new URL(hsUrl);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

            //9月12日修改待测试
            if (con.HTTP_ACCEPTED == 202) {
                TrustManager[] tm = {xtm};

                SSLContext ctx = SSLContext.getInstance("TLS");
                ctx.init(null, tm, null);

                con.setSSLSocketFactory(ctx.getSocketFactory());
                con.setHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String arg0, SSLSession arg1) {
                        return true;
                    }
                });

                con.setDoInput(true); //允许输入流，即允许下载

                //在android中必须将此项设置为false
                con.setDoOutput(false); //允许输出流，即允许上传
                con.setUseCaches(false); //不使用缓冲
                if (null != requestMethod && !requestMethod.equals("")) {
                    con.setRequestMethod(requestMethod); //使用指定的方式
                } else {
                    con.setRequestMethod("GET"); //使用get请求
                }
                is = con.getInputStream();  //获取输入流，此时才真正建立链接
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader bufferReader = new BufferedReader(isr);
                String inputLine = "";
                while ((inputLine = bufferReader.readLine()) != null) {
                    resultData += inputLine + "\n";
                }
//			     System.out.println(resultData);

//			     Certificate[] certs = con.getServerCertificates();

//			     int certNum = 1;

//			     for (Certificate cert : certs) {
//			    	 X509Certificate xcert = (X509Certificate) cert;
//			     }
// *
            }else{

                is = con.getInputStream();  //获取输入流，此时才真正建立链接
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader bufferReader = new BufferedReader(isr);
                String inputLine = "";
                while ((inputLine = bufferReader.readLine()) != null) {
                    resultData += inputLine + "\n";
                }
                System.out.println("错误"+resultData);
                LogUtil.saveTradeLog("赋值"+resultData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultData;
    }

    public static Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = createNonceStr(16);
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;
        //System.out.println(string1);

        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }

    public static String createNonceStr(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    X509TrustManager xtm = new X509TrustManager() {
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1)
                throws CertificateException {
            // TODO Auto-generated method stub
        }

        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1)
                throws CertificateException {
            // TODO Auto-generated method stub
        }
    };

}
