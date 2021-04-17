/**
 *
 */
package com.otzg.wxpay.util;


import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author G/2016年10月17日
 */
public class HtmlUtil {
    private final static int CONNECT_TIMEOUT = 5000; // in milliseconds
    private final static String DEFAULT_ENCODING = "UTF-8";

    public final static Map<String, String> getMapFromRequest(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            InputStream inputStream = request.getInputStream();
            // 读取输入流
            SAXReader reader = new SAXReader();
            Document document = reader.read(inputStream);
            // 得到xml根元素
            Element root = document.getRootElement();
            // 得到根元素的所有子节点
            @SuppressWarnings("unchecked")
            List<Element> elementList = root.elements();

            // 遍历所有子节点
            for (Element e : elementList)
                map.put(e.getName(), e.getText());

            // 释放资源
            inputStream.close();
        } catch (IOException e1) {
            e1.printStackTrace();
            return null;
        } catch (DocumentException e1) {
            e1.printStackTrace();
            return null;
        }
        return map;
    }

    public final static String postData(String urlStr, String data) {
        return postData(urlStr, data, "utf-8");
    }

    public final static String postData(String urlStr, String data, String contentType) {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlStr);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            conn.setConnectTimeout(CONNECT_TIMEOUT);
            conn.setReadTimeout(CONNECT_TIMEOUT);
            if (contentType != null && !contentType.isEmpty())
                conn.setRequestProperty("content-type", contentType);
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), DEFAULT_ENCODING);
            if (data == null)
                data = "";
            writer.write(data);
            writer.flush();
            writer.close();

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), DEFAULT_ENCODING));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\r\n");
            }
            return sb.toString();
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        return null;
    }


    //上传文件
    public final static String postFileData(String urlStr, Map<String, String> params,String fileName, File file) {
        String result = "";
//        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        try {
            HttpPost httpPost = new HttpPost(urlStr);

            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(200000).setSocketTimeout(200000).build();
            httpPost.setConfig(requestConfig);


            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            //上传的文件
            builder.addPart(fileName, new FileBody(file));
            //其他参数
            params.keySet().stream().forEach(key -> {
                builder.addPart(key, new StringBody(params.get(key), ContentType.MULTIPART_FORM_DATA));
            });

            HttpEntity fileEntity = builder.build();
            httpPost.setEntity(fileEntity);

            System.out.println("executing request " + httpPost.getRequestLine());
            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                System.out.println(response.getStatusLine());
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(response.getEntity());
                    System.out.println(result);
                    System.out.println("Response content length: " + resEntity.getContentLength());
                }
                EntityUtils.consume(resEntity);
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

//    public static String postFileData(String url, Map<String, String> params, File file) {
//        final String newLine = "\r\n";
//        // 固定的前缀
//        final String preFix = "--";
//        //final String preFix = "";
//        // 分界线，就是上面提到的boundary，可以是任意字符串，建议写长一点，这里简单的写了一个#
//        final String bounDary = "----WebKitFormBoundaryCXRtmcVNK0H70msG";
//        //请求返回内容
//        String output = "";
//
//        try {
//            //统一资源定位符
//            URL uploadFileUrl = new URL(url);
//            //打开http链接类
//            HttpURLConnection httpURLConnection = (HttpURLConnection) uploadFileUrl.openConnection();
//            //设置是否向httpURLConnection输出
//            httpURLConnection.setDoOutput(true);
//            //设置请求方法默认为get
//            httpURLConnection.setRequestMethod("POST");
//            //Post请求不能使用缓存
//            httpURLConnection.setUseCaches(false);
//            //设置token
////            httpURLConnection.setRequestProperty("authorization", (String) GlobalSession.getSessionAttribute("token"));
//            //为web端请求
//            httpURLConnection.setRequestProperty("os", "web");
//            //从新设置请求内容类型
//            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
//            httpURLConnection.setRequestProperty("Accept", "*/*");
//            httpURLConnection.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
//            httpURLConnection.setRequestProperty("Cache-Control", "no-cache");
//            //application/json;charset=UTF-8 application/x-www-form-urlencoded
//            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + bounDary);
//            httpURLConnection.setRequestProperty("User-Agent", "(Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.84 Safari/537.36)");
//
//
//            DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());
//
//            //设置DataOutputStream设置DataOutputStream数据输出流
//            //OutputStream outputStream = httpURLConnection.getOutputStream();
//
//            //上传普通文本文件
//            if (params.size() != 0 && params != null) {
//                for (Map.Entry<String, String> entry : params.entrySet()) {
//                    //获取参数名称和值
//                    String key = entry.getKey();
//                    String value = params.get(key);
//                    //向请求中写分割线
//                    dos.writeBytes(preFix + bounDary + newLine);
//                    //向请求拼接参数
//                    //String parm = key + "=" + URLEncoder.encode(value,"utf-8") +"\r\n" ;
//                    dos.writeBytes("Content-Disposition: form-data; " + "name=\"" + key + "\"" + newLine);
//                    //向请求中拼接空格
//                    dos.writeBytes(newLine);
//                    //写入值
//                    dos.writeBytes(URLEncoder.encode(value, "utf-8"));
//                    //dos.writeBytes(value);
//                    //向请求中拼接空格
//                    dos.writeBytes(newLine);
//                }
//            }
//
//            //上传文件
//            if (file != null && !params.isEmpty()) {
//                //向请求中写分割线
//                //把file装换成byte
//                File del = new File(file.toURI());
//                InputStream inputStream = new FileInputStream(del);
//                byte[] bytes = input2byte(inputStream);
//                String filePrams = "file";
//                String fileName = file.getName();
//                //向请求中加入分隔符号
//                dos.write((preFix + bounDary + newLine).getBytes());
//                //将byte写入
//                dos.writeBytes("Content-Disposition: form-data; " + "name=\"" + URLEncoder.encode(filePrams, "utf-8") + "\"" + "; filename=\"" + URLEncoder.encode(fileName, "utf-8") + "\"" + newLine);
//                dos.writeBytes(newLine);
//                dos.write(bytes);
//                //向请求中拼接空格
//                dos.writeBytes(newLine);
//            }
//            dos.writeBytes(preFix + bounDary + preFix + newLine);
//            //请求完成后关闭流
//            //得到相应码
//            dos.flush();
//            //判断请求没有成功
//            if (httpURLConnection.getResponseCode() != 200) {
//                return "{result:'fail',response:'errorCode:" + httpURLConnection.getResponseCode() + "'}";
//            }
//            //判断请求成功
//            if (httpURLConnection.getResponseCode() == 200) {
//                //将服务器的数据转化返回到客户端
//                InputStream inputStream = httpURLConnection.getInputStream();
//                byte[] bytes = new byte[0];
//                bytes = new byte[inputStream.available()];
//                inputStream.read(bytes);
//                output = new String(bytes);
//                inputStream.close();
//            }
//            dos.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "{result:'fail',response:'" + e.getMessage() + "'}";
//        }
//        return output;
//    }

    /**
     * 将输入流转化成字节流
     * @param inStream
     * @return
     * @throws IOException
     */
    public static final byte[] input2byte(InputStream inStream) throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        return in2b;
    }


    @Test
    public void testPostFile() {
        String url = "http://127.0.0.1:8030/test/upload/img";
        String filePath = "C:\\Users\\Administrator\\Desktop\\商品信息2\\0df05c892726c1cac5062b01fd03fb1.jpg";
        File file = new File(filePath);


        Map<String, String> params = new HashMap<>();
        params.put("mchId", "23427342937");
        String r = postFileData(url, params,"file", file);
        System.out.println(r);
    }

}
