package com.otzg.wxpay.data;

/**
 * 微信统一下单收款类
 */
public class WxNativePayData extends WxPayData {
    boolean isProfitSharing;
    String productId;
    public WxNativePayData(String subMchId, String productId, String body, String attach, String outTradeNo, Double totalFee,boolean isProfitSharing) {
        this.subMchId = subMchId;
        this.productId = productId;
        this.body = body;
        //不是必传项
        this.attach = attach;
        this.outTradeNo = outTradeNo;
        this.totalFee = totalFee;
        this.isProfitSharing = isProfitSharing;
    }

    public String getSubMchId() {
        return subMchId;
    }

    public void setSubMchId(String subMchId) {
        this.subMchId = subMchId;
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public boolean isProfitSharing() {
        return isProfitSharing;
    }

    public void setProfitSharing(boolean profitSharing) {
        isProfitSharing = profitSharing;
    }
}
