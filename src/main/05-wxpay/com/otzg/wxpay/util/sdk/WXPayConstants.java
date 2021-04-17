package com.otzg.wxpay.util.sdk;

import org.apache.http.client.HttpClient;

/**
 * 常量
 */
public class WXPayConstants {


    //微信支付渠道域名
    public static final String DOMAIN_API = "https://api.mch.weixin.qq.com";
    public static final String DOMAIN_API2 = "https://api2.mch.weixin.qq.com";

    public static final String DOMAIN_APIHK = "apihk.mch.weixin.qq.com";
    public static final String DOMAIN_APIUS = "apius.mch.weixin.qq.com";


    public static final String FAIL     = "FAIL";
    public static final String SUCCESS  = "SUCCESS";
    public static final String HMACSHA256 = "HMAC-SHA256";
    public static final String MD5 = "MD5";

    public static final String FIELD_SIGN = "sign";
    public static final String FIELD_SIGN_TYPE = "sign_type";

    public static final String WXPAYSDK_VERSION = "WXPaySDK/3.0.9";
    public static final String USER_AGENT = WXPAYSDK_VERSION +
            " (" + System.getProperty("os.arch") + " " + System.getProperty("os.name") + " " + System.getProperty("os.version") +
            ") Java/" + System.getProperty("java.version") + " HttpClient/" + HttpClient.class.getPackage().getImplementationVersion();

    public static final String MICROPAY_URL_SUFFIX     = "/pay/micropay";
    public static final String UNIFIEDORDER_URL_SUFFIX = "/pay/unifiedorder";
    public static final String ORDERQUERY_URL_SUFFIX   = "/pay/orderquery";
    public static final String REVERSE_URL_SUFFIX      = "/secapi/pay/reverse";
    public static final String CLOSEORDER_URL_SUFFIX   = "/pay/closeorder";
    public static final String REFUND_URL_SUFFIX       = "/secapi/pay/refund";
    public static final String REFUNDQUERY_URL_SUFFIX  = "/pay/refundquery";
    public static final String DOWNLOADBILL_URL_SUFFIX = "/pay/downloadbill";
    public static final String REPORT_URL_SUFFIX       = "/payitil/report";
    public static final String SHORTURL_URL_SUFFIX     = "/tools/shorturl";
    public static final String AUTHCODETOOPENID_URL_SUFFIX = "/tools/authcodetoopenid";




//    //支付押金（付款码支付）
//    public static final String DEPOSIT_MICROPAY_URL_SUFFIX = "/deposit/micropay";
//
//    //发普通红包接口
//    public static final String TRANSFERS_SENDREDPACK_URL_SUFFIX = "/mmpaymkttransfers/sendredpack";
//    //发裂变(群红包)红包接口
//    public static final String TRANSFERS_SENDGROUPREDPACK_URL_SUFFIX = "/mmpaymkttransfers/sendgroupredpack";


//    //微信支付渠道域名
//    public static final String DOMAIN_API = "https://api.mch.weixin.qq.com";
//    public static final String DOMAIN_API2 = "https://api2.mch.weixin.qq.com";
//
//    //微信财付通预下单接口
//    public static final String UNIFIEDORDER_URL_SUFFIX = "/pay/unifiedorder";
//    //提交付款码支付
//    public static final String MICROPAY_URL_SUFFIX     = "/pay/micropay";
//    //查询订单
//    public static final String ORDERQUERY_URL_SUFFIX   = "/pay/orderquery";
//
//    /**
//     *  撤销订单
//     *  支付交易返回失败或支付系统超时，调用该接口撤销交易。如果此订单用户支付失败，微信支付系统会将此订单关闭；如果用户支付成功，微信支付系统会将此订单资金退还给用户。
//     *  注意：7天以内的交易单可调用撤销，其他正常支付的单如需实现相同功能请调用申请退款API。提交支付交易后调用【查询订单API】，没有明确的支付结果再调用【撤销订单API】
//     */
//    public static final String REVERSE_URL_SUFFIX      = "/secapi/pay/reverse";
//    //微信财付通取消订单接口URL
//    public static final String CLOSEORDER_URL_SUFFIX   = "/pay/closeorder";
//    //微信财付通退款接口
//    public static final String REFUND_URL_SUFFIX       = "/secapi/pay/refund";
//    //微信财付通退款查询接口
//    public static final String REFUNDQUERY_URL_SUFFIX  = "/pay/refundquery";
//    public static final String DOWNLOADBILL_URL_SUFFIX = "/pay/downloadbill";
//    public static final String REPORT_URL_SUFFIX       = "/payitil/report";
//    public static final String SHORTURL_URL_SUFFIX     = "/tools/shorturl";
//    public static final String AUTHCODETOOPENID_URL_SUFFIX = "/tools/authcodetoopenid";


    //=================================关于分账================================================/
    //请求单次分账
    public static final String PROFITSHARING_URL_SUFFIX = "/secapi/pay/profitsharing";
    //添加分账接收方：服务商代子商户发起添加分账接收方请求，后续可通过发起分账请求将结算后的钱分到该分账接收方。HMAC-SHA256
    public static final String PROFITSHARING_ADDRECEIVER_URL_SUFFIX = "/pay/profitsharingaddreceiver";
    //删除分账接收方
    public static final String PROFITSHARING_REMOVERECEIVER_URL_SUFFIX = "/pay/profitsharingremovereceiver";
    //分账结果查询
    public static final String PROFITSHARING_QUERY_URL_SUFFIX = "/pay/profitsharingquery";


    //=================================提现(不支持的)================================================/
    //企业付款到领取(提现)
    public static final String TRANSFER_URL_SUFFIX = "/mmpaymkttransfers/promotion/transfers";

    //=================================小微入驻================================================/
    //申请入驻
    public static final String MICRO_URL_SUFFIX = "/applyment/micro/submit";
    //查询申请入驻
    public static final String MICRO_QUERY_URL_SUFFIX = "/applyment/micro/getstate";
    //图片上传API
    public static final String MICRO_UPLOAD_URL_SUFFIX = "/secapi/mch/uploadmedia";

    //支付押金（人脸支付）
    public static final String DEPOSIT_FACEPAY_URL_SUFFIX = "/deposit/facepay";
    //支付押金（付款码支付）
    public static final String DEPOSIT_MICROPAY_URL_SUFFIX = "/deposit/micropay";

    //=================================红包========================================
    //发普通红包接口
    public static final String TRANSFERS_SENDREDPACK_URL_SUFFIX = "/mmpaymkttransfers/sendredpack";
    //发裂变(群红包)红包接口
    public static final String TRANSFERS_SENDGROUPREDPACK_URL_SUFFIX = "/mmpaymkttransfers/sendgroupredpack";
    //红包查询接口 用于商户对已发放的红包进行查询红包的具体信息，可支持普通红包和裂变包。
    public static final String TRANSFERS_SENDGROUPREDPACK_QUERY_URL_SUFFIX = "/mmpaymkttransfers/gethbinfo";



    // sandbox
    final static String SANDBOX_SUFFIX = "/sandboxnew";

    static boolean useSandBox=false;

    public static void setUseSandBox(boolean useSandBox) {
        WXPayConstants.useSandBox = useSandBox;
    }

    private static String getDomain(){
        String currentDomain= DOMAIN_API;
//        if(USEDOMAIN2){
//            currentDomain=DOMAIN_API2;
//        }
        if(useSandBox){
            return currentDomain+SANDBOX_SUFFIX;
        }
        return currentDomain;
    }

    public static final String getUnifiedOrderUrl(){
        return getDomain()+UNIFIEDORDER_URL_SUFFIX;
    }

    //订单查询链接
    public static final String getOrderQueryUrl(){
        return getDomain() + ORDERQUERY_URL_SUFFIX;
    }

    //微信财付通退款查询接口
    public static final String getPayRefundQueryUrl(){
        return getDomain()+REFUNDQUERY_URL_SUFFIX;
    }
    //微信财付通退款接口
    public static final String getPayRefundUrl(){
        return getDomain()+REFUND_URL_SUFFIX;
    }
    //微信财付通退款接口
    public static final String getPayCloseOrderUrl(){
        return getDomain()+CLOSEORDER_URL_SUFFIX;
    }

    public static final String getMicroPayUrl(){
        return getDomain()+MICROPAY_URL_SUFFIX;
    }

    public static final String getDepositFacePayUrl(){
        return getDomain()+DEPOSIT_FACEPAY_URL_SUFFIX;
    }
    public static final String getMicroSubmitUrl(){
        return getDomain()+MICRO_URL_SUFFIX;
    }
    public static final String getMicroSubmitQueryUrl(){
        return getDomain()+MICRO_QUERY_URL_SUFFIX;
    }
    public static final String getMicroUploadUrl(){
        return getDomain()+MICRO_UPLOAD_URL_SUFFIX;
    }

    public static final String getRePackPayUrl(){
        return getDomain()+TRANSFERS_SENDREDPACK_URL_SUFFIX;
    }
    public static final String getRePackGroupPayUrl(){
        return getDomain()+TRANSFERS_SENDGROUPREDPACK_URL_SUFFIX;
    }
    public static final String getRePackPayQueryUrl(){
        return getDomain()+TRANSFERS_SENDGROUPREDPACK_QUERY_URL_SUFFIX;
    }


    //企业付款接口
    public static String getTransPayUrl() {
        return getDomain()+TRANSFER_URL_SUFFIX;
    }
    //请求单次分账
    public static String getProfitSharingUrl() {
        return getDomain()+PROFITSHARING_URL_SUFFIX;
    }

    //服务商添加分账者
    public static String getProfitSharingAddReceiverUrl() {
        return getDomain()+PROFITSHARING_ADDRECEIVER_URL_SUFFIX;
    }

    //服务商删除分账者
    public static String getProfitSharingRemoveReceiverUrl() {
        return getDomain()+PROFITSHARING_REMOVERECEIVER_URL_SUFFIX;
    }

    //服务商分账结果查询
    public static String getProfitSharingQueryUrl() {
        return getDomain()+PROFITSHARING_QUERY_URL_SUFFIX;
    }
















    // sandbox
//    public static final String SANDBOX_MICROPAY_URL_SUFFIX     = "/sandboxnew/pay/micropay";
//    public static final String SANDBOX_UNIFIEDORDER_URL_SUFFIX = "/sandboxnew/pay/unifiedorder";
//    public static final String SANDBOX_ORDERQUERY_URL_SUFFIX   = "/sandboxnew/pay/orderquery";
//    public static final String SANDBOX_REVERSE_URL_SUFFIX      = "/sandboxnew/secapi/pay/reverse";
//    public static final String SANDBOX_CLOSEORDER_URL_SUFFIX   = "/sandboxnew/pay/closeorder";
//    public static final String SANDBOX_REFUND_URL_SUFFIX       = "/sandboxnew/secapi/pay/refund";
//    public static final String SANDBOX_REFUNDQUERY_URL_SUFFIX  = "/sandboxnew/pay/refundquery";
//    public static final String SANDBOX_DOWNLOADBILL_URL_SUFFIX = "/sandboxnew/pay/downloadbill";
//    public static final String SANDBOX_REPORT_URL_SUFFIX       = "/sandboxnew/payitil/report";
//    public static final String SANDBOX_SHORTURL_URL_SUFFIX     = "/sandboxnew/tools/shorturl";
//    public static final String SANDBOX_AUTHCODETOOPENID_URL_SUFFIX = "/sandboxnew/tools/authcodetoopenid";
//
//    public static final String SANDBOX_DEPOSIT_FACEPAY_URL_SUFFIX = "/sandboxnew/deposit/facepay";

}

