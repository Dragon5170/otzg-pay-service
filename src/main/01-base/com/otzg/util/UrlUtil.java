package com.otzg.util;

import org.springframework.core.io.ByteArrayResource;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class UrlUtil {

    /**
     * 获取inputstream数据
     * 一旦取数据后则不能再取
     * @param request
     * @return
     */
//    public static String receivePost(HttpServletRequest request){
//        try{
//            // 读取请求内容
//            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
//            String line = null;
//            StringBuilder sb = new StringBuilder();
//            while((line = br.readLine())!=null){
//                sb.append(line);
//            }
//
//            // 将资料解码
//            String reqBody = sb.toString();
//            LogUtil.print("reqBody==>"+reqBody);
//            return CodeUtil.urlDecode(reqBody);
//        }catch (Exception e){
//            e.printStackTrace();
//            return null;
//        }
//    }



    /**
     * 获取请求的所有参数map
     * @param request
     * @return
     */
    public final static Map<String,Object> getRequestMap(HttpServletRequest request){
        Map<String,Object> params = new HashMap<>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = iter.next();
            String[] values = requestParams.get(name);

            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr += (i == values.length - 1) ? values[i] : values[i] + ",";
            }
            params.put(name, CodeUtil.getUtf8(valueStr));
        }
        return params;
    }

    /**
     * 获取reguest的完整URL数据
     * @author G/2015-12-26 上午9:13:48
     * @param request
     * @return
     */
    public final static String getRequestURL(HttpServletRequest request){
        if (request == null){return "";}
        String url = request.getRequestURL().toString();
        Map<String, String[]> requestParams = request.getParameterMap();
        if(!CheckUtil.isEmpty(requestParams)){
            int j=0;
            for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
                if (j == 0) {
                    url += "?";
                } else {
                    url += "&";
                }
                j++;

                String name = iter.next();
                String[] values = requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                }
                url += name+"="+valueStr;
            }
        }else if (!CheckUtil.isEmpty(request.getQueryString())) {
            url += "?"+request.getQueryString();
        }
        return hidePassword(url);
    }

    private final static String hidePassword(String url){
      return url.replaceAll("(password=).*?([a-z&]|\\z)","$1****$2");
    }

    /**
     * 获取一个请求的hash值，仅要求区分请求的内容
     * @author G/2017年3月27日 下午5:48:04
     * @param request
     * @return
     */
    public final static int getRequestURLHashCode(HttpServletRequest request){
        String url = request.getRequestURL().toString();
        Map<String, String[]> requestParams = request.getParameterMap();
        if(requestParams!=null){
            return (url+requestParams).hashCode();
        }else if(request.getQueryString() != null) {
            return (url+request.getQueryString()).hashCode();
        }
        return 0;
    }

    /**
     * 把所有元素重新排序，并按照key=value的模式用"&"拼成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     * @author G/2018/6/19 11:19
     */
    private static String createLinkString(Map<String,Object> params) {
        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);
        String result = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            Object value = params.get(key);
            if (i == keys.size() - 1) {
                result = result + key + "=" + value;
            } else {
                result = result + key + "=" + value + "&";
            }
        }
        return result;
    }

    /**
     * 除去空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    private final static Map<String,Object> paraFilter(Map<String,Object> sArray) {
        Map<String,Object> result = new HashMap<>();
        if (sArray == null || sArray.size() < 1) {
            return result;
        }

        for (String key : sArray.keySet()) {
            Object value = sArray.get(key);
            if (value == null
                    || value.equals("")
                    || key.equalsIgnoreCase("sign")
                    || key.equalsIgnoreCase("sign_type")
                    || key.equalsIgnoreCase("key")
                    //文件字节码格式不参与签名
                    || (value instanceof ByteArrayResource)
                    ) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }

    public final static String getLinkParamString(Map<String,Object> sArray){
        return createLinkString(paraFilter(sArray));
    }



}
