package com.otzg.wxpay.util;
/**
 *
 */

import com.otzg.log.util.LogUtil;
import com.otzg.util.SubmitUtil;
import net.sf.json.JSONObject;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 *  @author G/2016年10月17日
 *
 */
public class WxPayUtil {

    /**
     * 签名函数
     * @author G/2016年10月18日 上午10:28:05
     * @param paramMap
     * @return
     */
    public final static String Sign(Map<String, String> paramMap) {
        String strSignTemp = createLinkString(paraFilter(paramMap));
        //拼接API密钥：
        strSignTemp = strSignTemp + "&key=" + paramMap.get("key");
        //签名
        String sign = MD5Util.encode(strSignTemp);

        return sign;
    }

    public final static String Sign(Map<String, String> paramMap, String key) {
        String strSignTemp = createLinkString(paraFilter(paramMap));
        //拼接API密钥：
        strSignTemp = strSignTemp + "&key=" + key;
        //签名
        String sign = MD5Util.encode(strSignTemp);
        return sign;
    }


    /**
     * 校验财付通发来的签名
     * @author G/2016年10月19日 上午10:01:19
     * @param paramMap
     * @return
     */
    public final static boolean verify(Map<String, String> paramMap) {
        boolean f = false;
        if (paramMap != null && paramMap.get("sign") != null) {
            String sign = paramMap.get("sign");
            if (Sign(paramMap).equals(sign)) {
                f = true;
            }
        }
        return f;
    }

    /**
     * 获取随机字符串
     * @author G/2016年10月17日 上午11:03:04
     * @param sourceStr
     * @param l =16则获取16位否则是一个32位的
     * @return
     */
    public final static String getNonce(String sourceStr, int l) {
        String result = MD5Util.encode(sourceStr);
        if (l == 16)
            result = result.substring(8, 24);
        return result;
    }

    /**
     * 除去空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public final static Map<String, String> paraFilter(Map<String, String> sArray) {
        Map<String, String> result = new HashMap<String, String>();
        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                    || key.equalsIgnoreCase("sign_type") || key.equalsIgnoreCase("key")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }

    /**
     * 把所有元素重新排序，并按照key=value的模式用"&"拼成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }


    /**
     * 排序方法
     * @param token
     * @param timestamp
     * @param nonce
     * @return
     */
    public final static String sort(String token, String timestamp, String nonce) {
        String[] strArray = {token, timestamp, nonce};
        Arrays.sort(strArray);
        StringBuilder sbuilder = new StringBuilder();
        for (String str : strArray) {
            sbuilder.append(str);
        }
        return sbuilder.toString();
    }

    /**
     * 字符串数组排序
     * @author G/2016年11月22日 下午4:59:17
     * @param strArray
     * @return
     */
    public final static String sort(String[] strArray) {
        Arrays.sort(strArray);
        StringBuilder sbuilder = new StringBuilder();
        for (String str : strArray) {
            sbuilder.append(str);
        }
        return sbuilder.toString();
    }

    public final static String SHA1(String decript) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取本机Ip
     * 通过 获取系统所有的networkInterface网络接口 然后遍历 每个网络下的InterfaceAddress组。
     * 获得符合 <code>InetAddress instanceof Inet4Address</code> 条件的一个IpV4地址
     * @return
     */
    @SuppressWarnings("rawtypes")
    public final static String localIp() {
        String ip = null;
        Enumeration allNetInterfaces;
        try {
            allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                List<InterfaceAddress> InterfaceAddress = netInterface.getInterfaceAddresses();
                for (InterfaceAddress add : InterfaceAddress) {
                    InetAddress Ip = add.getAddress();
                    if (Ip != null && Ip instanceof Inet4Address) {
                        ip = Ip.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
        }
        return ip;
    }

    /**
     * jsapi ticket sign
     * @author G/2016年11月22日 下午5:11:57
     * @param jsapi_ticket
     * @param url
     * @return
     */
    public final static Map<String, String> JsapiTicketSign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;
//      System.out.println(string1);
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




    /**
     * 获取JsapiTicket
     * @author G/2016年11月22日 下午4:47:29
     * @return
     */
    public final static String getJsapiTicket(String accessToken) {
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";
        url = String.format(url, accessToken);
        String result = SubmitUtil.get(url);
        JSONObject jo = JSONObject.fromObject(result);
        LogUtil.saveTradeLog( "jsapiTicket值" + jo.toString());
        if (jo != null && jo.getString("ticket") != null) {
            result = jo.getString("ticket");
        }
        return result;
    }

    //获取微信网页需要的支付参数
//    public final static JSONObject getJsapiPayJson(String prepayId) {
//        /**
//         * prepay_id 通过微信支付统一下单接口拿到，
//         * paySign 采用统一的微信支付 Sign 签名生成方法，
//         * 注意这里 appId 也要参与签名，
//         * appId 与 config 中传入的 appId 一致，
//         * 即最后参与签名的参数有appId, timeStamp, nonceStr, package, signType
//         */
//        JSONObject jo = new JSONObject();
//        String nonce_str = getNonce("jsapi", 16);
//        String timestamp = create_timestamp();
//        String signature = "";
//        //注意这里参数名必须全部小写，且必须有序
//        Map<String, String> data = new HashMap<String, String>();
//        data.put("appId", WxpayConfig.GZHAPPID);
//        data.put("nonceStr", nonce_str);
//        data.put("timeStamp", timestamp);
//        data.put("package", "prepay_id=" + prepayId);
//        data.put("signType", "MD5");
//        data.put("key", WxpayConfig.GZHKEY);
//
//        signature = WxpayUtil.Sign(data);
//        jo.put("appId", WxpayConfig.GZHAPPID);
//        jo.put("timeStamp", timestamp);
//        jo.put("nonceStr", nonce_str);
//        jo.put("package", "prepay_id=" + prepayId);
//        jo.put("signType", "MD5");
//        jo.put("paySign", signature);
//        return jo;
//    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    public static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }


    @Test
    public void test() {
        HashMap<String, String> paramMap = new HashMap<String, String>();
//		paramMap.put("body","NATIVE"); //交易类型
//		paramMap.put("device_info","");
//		paramMap.put("appid",WxpayConfig.GZHAPPID);
//		paramMap.put("mch_id",WxpayConfig.GZHMCHID);
//		paramMap.put("nonce_str",WxpayUtil.getNonce("nonce",16));
//		paramMap.put("key",WxpayConfig.KEY);
//		System.out.println(XmlUtil.Map2Xml(paraFilter(paramMap)));
//		System.out.println(WxpayUtil.Sign(paramMap));


//		request.appId = "wxd930ea5d5a258f4f";
//		request.partnerId = "1900000109";
//		request.prepayId= "1101000000140415649af9fc314aa427";
//		request.packageValue = "Sign=WXPay";
//		request.nonceStr= "1101000000140429eb40476f8896f4c9";
//		request.timeStamp= "1398746574";
//		request.sign= "7FFECB600D7157C5AA49810D2D8F28BC2811827B";
//		A7E211451F2912B3A9AA491A19DA927A

//        paramMap.put("appid", "wxd930ea5d5a258f4f");
//        paramMap.put("partnerid", "1900000109");
//        paramMap.put("prepayid", "1101000000140415649af9fc314aa427");
//        paramMap.put("package", "Sign=WXPay");
//        paramMap.put("noncestr", "1101000000140429eb40476f8896f4c9");
//        paramMap.put("timestamp", "1398746574");
//        paramMap.put("key", WxpayConfig.APPKEY);
//        System.out.println(WxpayUtil.Sign(paramMap));
//		System.out.println("22D571F7D693B168A1BEE4AE3F55F1BF");
        //"paySign":"22D571F7D693B168A1BEE4AE3F55F1BF"
    }
}

