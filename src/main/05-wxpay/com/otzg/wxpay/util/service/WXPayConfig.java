package com.otzg.wxpay.util.service;

import com.otzg.log.util.LogUtil;
import com.otzg.wxpay.util.sdk.IWXPayDomain;
import com.otzg.wxpay.util.sdk.WXPayConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Component
public class WXPayConfig {

    //xx公司账号
    static String mchId = "";
    //(xx公司账号的服务号)
    //appid是微信公众账号或开放平台APP的唯一标识，在公众平台申请公众账号或者在开放平台申请APP账号后，微信会自动分配对应的appid，用于标识该应用。可在微信公众平台-->开发者中心查看，商户的微信支付审核通过邮件中也会包含该字段值。
    static String appId = "";

    //交易过程生成签名的密钥，仅保留在商户系统和微信支付后台，不会在网络中传播。商户妥善保管该Key，切勿在网络中传输，不能在其他客户端中存储，保证key不会被泄漏。商户可根据邮件提示登录微信商户平台进行设置。也可按一下路径设置：微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置
    static String key = "";

    //AppSecret是APPID对应的接口密码，用于获取接口调用凭证access_token时使用。在微信支付中，先通过OAuth2.0接口获取用户openid，此openid用于微信内网页支付模式下单接口使用。在开发模式中获取AppSecret（成为开发者且帐号没有异常状态）。
    static String appSecret = "";

    //微信收款异步通知接口
    static String notifyUrl = "";
    //微信证书存放路径(初始值，spring 从配置文件中注入)
    static String certRootPath = "D:/workspace/otzg-pay-service/src/main/webapp";

    //沙箱key
    static String sandBoxKey = "";

    static boolean useSandbox = false;
    //自动回复
    static boolean autoReport = false;


    //==================================服务商公众号参数=======================================
    static String gzhAppId = "";
    static String gzhAppSecret = "";
    //==================================spring容器启动后自动重新配置参数=======================================

    @Value("${pay.wx.mchId}")
    public void setMchId(String value) {
        mchId = value;
    }

    @Value("${pay.wx.appId}")
    public void setAppId(String value) {
        appId = value;
    }

    @Value("${pay.wx.gzhAppId}")
    public void setGzhAppId(String value) {
        gzhAppId = value;
    }

    @Value("${pay.wx.gzhAppSecret}")
    public void setGzhAppSecret(String value) {
        gzhAppSecret = value;
    }



    @Value("${pay.wx.key}")
    public void setKey(String value) {
        key = value;
    }
    @Value("${pay.wx.notifyUrl}")
    public void setNotifyUrl(String value){
        notifyUrl=value;
    }
    @Value("${pay.wx.autoReport}")
    public void setAutoReport(boolean value){
        autoReport=value;
    }

    @Value("${pay.wx.sandboxKey}")
    public void setSandBoxKey(String value) {
        sandBoxKey = value;
    }

    @Value("${pay.wx.useSandbox}")
    public void setUseSandbox(boolean value) {
        useSandbox = value;
    }

    @Value("${pay.wx.certRootPath}")
    public void setCertRootPath(String value){ certRootPath = value;}

    @Value("${pay.wx.appSecret}")
    public void setAppSecret(String value){ appSecret = value;}

    //=========================================================================================================


    public static String getMchId() {
        return mchId;
    }

    public static String getKey() {
        return key;
    }

    public static String getAppId() {
        return appId;
    }

    public static String getGzhAppId() {
        return gzhAppId;
    }

    public static String getGzhAppSecret() {
        return gzhAppSecret;
    }

    public static String getNotifyUrl() {
        return LogUtil.getServUrl()+notifyUrl;
    }

    public static boolean isAutoReport() {
        return autoReport;
    }

    public static String getCertRootPath() {
        return certRootPath;
    }

    //提交地址
    String url;

    String signType= SignType.MD5.name();
    byte[] certData;


    //连接超时时间，默认10秒
    public final static int socketTimeout = 10000;

    //传输超时时间，默认30秒
    public final static int connectTimeout = 30000;
    //http读取时间超时
    public final static int httpReadTimeout = 20000;


    /**
     * 微信财富通三种支付场景
     * 	1=TRADETYPE_JSAPI 微信网页支付
     *  2=TRADETYPE_NATIVE 扫码支付
     *  3=TRADETYPE_APP app支付
     */
    public final static String TRADETYPE_JSAPI = "JSAPI";
    public final static String TRADETYPE_NATIVE = "NATIVE";
    public final static String TRADETYPE_APP = "APP";

    /**
     * 微信证书在服务器端存放位置
     */

    /**
     * 公众号商户证书
     */
    public final static String GZHCERTPATH = "/WEB-INF/weixin/gzh_cert/apiclient_cert.p12";
    /**
     * app移动应用商户证书
     */
    public final static String APPCERTPATH = "/WEB-INF/weixin/app_cert/apiclient_cert.p12";

    //服务商微信公钥
    public final static String APPPUBLICCERTPATH = "/WEB-INF/weixin/app_cert/public_cert.pem";

    /**
     * 公众号的全局唯一票据
     */
    public final static String WXPAY_ACCESS_TOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
    /**
     * 获取jsapi_ticket
     */
    public final static String WXPAY_JSAPI_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";


    //获取证书路径
    public final static String getCertPath(){
        return getCertRootPath() + APPCERTPATH;
    }

    public final static String getPublicCertPath(){
        return getCertRootPath() + APPPUBLICCERTPATH;
    }


    /**
     * 是否自动上报。
     * 若要关闭自动上报，子类中实现该函数返回 false 即可。
     *
     * @return
     */
    public boolean shouldAutoReport() {
        return true;
    }

    /**
     * 进行健康上报的线程的数量
     *
     * @return
     */
    public int getReportWorkerNum() {
        return 6;
    }


    /**
     * 健康上报缓存消息的最大数量。会有线程去独立上报
     * 粗略计算：加入一条消息200B，10000消息占用空间 2000 KB，约为2MB，可以接受
     *
     * @return
     */
    public int getReportQueueMaxSize() {
        return 10000;
    }

    /**
     * 批量上报，一次最多上报多个数据
     *
     * @return
     */
    public int getReportBatchSize() {
        return 10;
    }

    public String getSignType() {
        return signType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    public WXPayConfig() {
    }

    public WXPayConfig(String signType) {
        this.signType = signType;
    }
    public WXPayConfig(String signType, boolean cert) {
        if(signType!=null){
            this.signType=signType;
        }
        if(cert){
            loadCert();
        }
    }

    void loadCert(){
        try{
            String certPath = getCertPath();
            System.out.println("文件路径="+certPath);
            File file = new File(certPath);
            if(file.exists() && file.isFile()){
                InputStream certStream = new FileInputStream(file);
                this.certData = new byte[(int) file.length()];
                certStream.read(this.certData);
                certStream.close();
            }else{
                System.out.println("文件路径错误="+certPath);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public IWXPayDomain getWXPayDomain() {
        IWXPayDomain iwxPayDomain = new IWXPayDomain() {
            @Override
            public void report(String domain, long elapsedTimeMillis, Exception ex) {

            }
            @Override
            public DomainInfo getDomain(WXPayConfig config) {
                return new IWXPayDomain.DomainInfo(WXPayConstants.DOMAIN_API, true);
            }
        };
        return iwxPayDomain;
    }
}
