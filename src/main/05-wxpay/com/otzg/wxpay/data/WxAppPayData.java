package com.otzg.wxpay.data;

import java.util.StringJoiner;

/**
 * 微信App下单收款类
 */
public class WxAppPayData extends WxPayData {
    //特约商户在微信开放平台上申请的APPID
    String subAppId;

    public WxAppPayData(String subMchId, String subAppId, String body, String attach, String outTradeNo, Double totalFee) {
        this.subMchId = subMchId;
        this.subAppId = subAppId;
        this.body = body;
        this.attach = attach;
        this.outTradeNo = outTradeNo;
        this.totalFee = totalFee;
    }

    public String getSubMchId() {
        return subMchId;
    }

    public void setSubMchId(String subMchId) {
        this.subMchId = subMchId;
    }

    public String getSubAppId() {
        return subAppId;
    }

    public void setSubAppId(String subAppId) {
        this.subAppId = subAppId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public Double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Double totalFee) {
        this.totalFee = totalFee;
    }
    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", WxAppPayData.class.getSimpleName() + "[", "]")
                .add("subAppId='" + subAppId + "'")
                .toString();
    }
}
