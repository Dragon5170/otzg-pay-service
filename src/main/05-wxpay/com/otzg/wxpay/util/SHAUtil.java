package com.otzg.wxpay.util;

import com.otzg.util.CheckUtil;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class SHAUtil {

    /**
     * 获取HMAC-SHA256签名
     *
     * @param paramMap 签名参数（sign不参与签名）
     * @param key      签名密钥
     * @return HMAC-SHA256签名结果
     */
    public final static String sha256Sign(Map<String, String> paramMap, String key) {
        try {
            String payParam = SignUtil.getSignTemp(paramMap, key);
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] array = sha256_HMAC.doFinal(payParam.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte item : array) {
                sb.append(Integer.toHexString((item & 0xFF) | 0x100).substring(1, 3));
            }
            String sign = sb.toString().toUpperCase();
            System.out.println("签名结果:" + sign);
            return sign;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 微信证书HMAC-SHA256签名
     *
     * @param params
     * @param secret
     * @return
     */
    public static String wechatCertficatesSignBySHA256(Map<String, String> params, String secret) {
        // 需要保证排序
        SortedMap<String, String> sortedMap = new TreeMap<>(params);
        // 将参数拼接成字符串
        StringBuilder toSign = new StringBuilder();
        for (String key : sortedMap.keySet()) {
            String value = params.get(key);
            if (!CheckUtil.isEmpty(value) && !"sign".equals(key) && !"key".equals(key)) {
                toSign.append(key).append("=").append(value).append("&");
            }
        }
        toSign.append("key=").append(secret);

        return sha256_HMAC(toSign.toString(), secret);
    }

    /**
     * 加密HMAC-SHA256
     *
     * @param message
     * @param secret
     * @return
     */
    private static String sha256_HMAC(String message, String secret) {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] bytes = sha256_HMAC.doFinal(message.getBytes());
            String sign = byteArrayToHexString(bytes);
            sign = sign.toUpperCase();
            return sign;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密后的字节转字符串
     *
     * @param b
     * @return
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp = null;
        for (int n = 0; b != null && n <b.length;n++){
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }

}
