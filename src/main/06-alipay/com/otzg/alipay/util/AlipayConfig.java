package com.otzg.alipay.util;

import com.otzg.log.util.LogUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AlipayConfig {

    // 签名方式
    public final static String sign_type = "RSA2";
    //编码
    public final static String charset = "UTF-8";

    //正式环境
    public final static String alipay_gateway = "https://openapi.alipay.com/gateway.do";
    public final static String auth_url = "https://openauth.alipay.com/oauth2/appToAppAuth.htm?app_id=%s&redirect_uri=%s";


    //支付宝商户号，在此仅供支付宝向平台返利
    static String pid = "";
    //第三方应用
    static String app_id = "";
    //第三方应用
    static String app_private_key = "";

    //支付宝的公钥，查看地址：https://openhome.alipay.com/platform/keyManage.htm?keyType=partner
    static String alipay_public_key = "";


    //支付成功异步回调接口
    static String notifyUrl = "/pay/alipay/notify";
    static String authNotifyUrl = "/alipay/openAuthTokenApp/notify";

    @Value("${pay.alipay.notifyUrl}")
    public void setNotifyUrl(String value) {
        notifyUrl = value;
    }

    @Value("${pay.alipay.authNotifyUrl}")
    public void setAuthNotifyUrl(String value) {
        authNotifyUrl = value;
    }


    @Value("${pay.alipay.pid}")
    public void setPid(String value) {
        pid = value;
    }

    @Value("${pay.alipay.appId}")
    public void setAppId(String value) {
        app_id = value;
    }

    @Value("${pay.alipay.app-private-key}")
    public void setAppPrivateKey(String value) {
        app_private_key = value;
    }

    @Value("${pay.alipay.alipay-public-key}")
    public void setAlipayPublicKey(String value) {
        alipay_public_key = value;
    }


    public static String getNotifyUrl() {
        return LogUtil.getServUrl() + notifyUrl;
    }

    public static String getAuthNotifyUrl() {
        return LogUtil.getServUrl() + authNotifyUrl+"?uid=%s";
    }

}
