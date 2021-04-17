package com.otzg.wxpay.data;

public class WxMiniPayData extends WxPayData {
    String subAppId;
    //附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
    String openid;
    String subOpenid;

    public WxMiniPayData(String subMchId, String subAppId, String attach, String body, String outTradeNo, Double totalFee, String openid, String subOpenid) {
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
