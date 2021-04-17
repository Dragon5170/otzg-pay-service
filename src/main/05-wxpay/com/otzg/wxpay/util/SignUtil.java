package com.otzg.wxpay.util;

import com.otzg.util.CheckUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class SignUtil {

    /**
     * 获取签名参数字符串
     *
     * @param paramMap 签名参数（sign字段不参与签名）
     * @param payKey   签名密钥
     * @return 待签名字符串
     */
    public final static String getSignTemp(Map<String, String> paramMap, String payKey) {
        ArrayList<String> keyList = new ArrayList<>(paramMap.keySet());
        Collections.sort(keyList);

        StringBuilder signParam = new StringBuilder();
        for (String key : keyList) {
            if (!"sign".equals(key) && !CheckUtil.isEmpty(paramMap.get(key))) {
                signParam.append(key).append("=").append(paramMap.get(key)).append("&");
            }
        }
        signParam.delete(signParam.length() - 1, signParam.length());
        signParam.append("&key=").append(payKey);
        return signParam.toString();
    }
}
