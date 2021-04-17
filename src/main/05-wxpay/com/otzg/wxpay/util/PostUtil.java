package com.otzg.wxpay.util;

import com.otzg.log.util.LogUtil;
import com.otzg.wxpay.util.service.WXPayConfig;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.util.Map;

public class PostUtil {
    //带证书提交
    public final static String doPostWithCert(String url, String data) {
        String result = "";

        //获取证书路径
        String keyStoreUrl= WXPayConfig.getCertPath();
        System.out.println("keyStoreUrl="+keyStoreUrl);

        //加载证书
        SSLConnectionSocketFactory sslsf = LoadSslUtil.getInstance().loadCert(keyStoreUrl, WXPayConfig.getMchId());
        //如果加载失败直接返回
        if (sslsf == null) {
            return result;
        }


        //构造一个客户端
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        try {

            //构造post请求
            HttpPost httpPost = new HttpPost(url);
            RequestConfig config = RequestConfig.custom().setConnectTimeout(10000).setConnectionRequestTimeout(10000).setSocketTimeout(10000).build();
            httpPost.setConfig(config);

            //得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
            StringEntity postEntity = new StringEntity(data, "UTF-8");
            httpPost.addHeader("Content-Type", "text/xml");
            httpPost.addHeader(HTTP.USER_AGENT, "wxpay sdk java v1.0 " + WXPayConfig.getMchId());
            httpPost.setEntity(postEntity);

            CloseableHttpResponse response = httpClient.execute(httpPost);

            result = EntityUtils.toString(response.getEntity(), "UTF-8");
            LogUtil.saveTradeLog("官方微信--请求返回结果："+result);

            return result;
        } catch (Exception e) {
            LogUtil.saveTradeLog("官方微信--请求失败："+e.toString());
        }

        return result;
    }

    //带证书上传文件
    public final static String doPostFileDataWithCert(String url, Map<String, String> paramMap, String fileKey, File file) {
        //获取证书路径
        String keyStoreUrl= WXPayConfig.getCertPath();
        System.out.println("keyStoreUrl="+keyStoreUrl);

        try {
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            //上传的文件
            multipartEntityBuilder.addBinaryBody(fileKey, file, ContentType.create("image/jpg"), file.getName());
            //其他参数
            paramMap.keySet().stream().forEach(key->{
                multipartEntityBuilder.addTextBody(key,paramMap.get(key).toString(), ContentType.MULTIPART_FORM_DATA);
            });

            //加载证书
            SSLConnectionSocketFactory sslsf = LoadSslUtil.getInstance().loadCert(keyStoreUrl, WXPayConfig.getMchId());
            //如果加载失败直接返回
            if (sslsf == null) {
                return null;
            }
            //构造一个客户端
            CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            try {
                HttpPost httpPost = new HttpPost(url);
                RequestConfig config = RequestConfig.custom().setConnectTimeout(10000).setConnectionRequestTimeout(10000).setSocketTimeout(10000).build();
                httpPost.setConfig(config);
                ////这里的Content-type要设置为"multipart/form-data"，否则返回“参数填写有误，请检查后重试”
                httpPost.addHeader(HTTP.CONTENT_TYPE, "multipart/form-data; charset=UTF-8");
                httpPost.addHeader(HTTP.USER_AGENT, "wxpay sdk java v1.0 " + WXPayConfig.getMchId());
                httpPost.setEntity(multipartEntityBuilder.build());
                CloseableHttpResponse response = httpClient.execute(httpPost);

                String result = EntityUtils.toString(response.getEntity(), "UTF-8");
                LogUtil.saveTradeLog("官方微信--请求返回结果："+result);

                return result;
            } catch (Exception e) {
                LogUtil.saveTradeLog("官方微信--请求失败："+e.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
