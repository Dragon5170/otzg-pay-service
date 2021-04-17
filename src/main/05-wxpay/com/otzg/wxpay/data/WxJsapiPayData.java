package com.otzg.wxpay.data;

/**
 * 微信JSAPi收款对象
 */
public class WxJsapiPayData extends WxPayData {
    String subAppId;
    String openid;
    String subOpenid;

    public WxJsapiPayData(String subMchId, String subAppId, String attach, String body, String outTradeNo, Double totalFee, String openid, String subOpenid) {
        this.subMchId = subMchId;
        this.subAppId = subAppId;
        this.attach = attach;
        this.body = body;
        this.outTradeNo = outTradeNo;
        this.totalFee = totalFee;
        this.openid = openid;
        this.subOpenid = subOpenid;
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

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
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

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSubOpenid() {
        return subOpenid;
    }

    public void setSubOpenid(String subOpenid) {
        this.subOpenid = subOpenid;
    }

}
