package com.otzg.wxpay.data;

/**
 * 退款查询传输对象
 */
public class WxRefundPayQueryData extends WxPayData {

    public WxRefundPayQueryData(String subMchId, String outTradeNo) {
        this.subMchId = subMchId;
        this.outTradeNo = outTradeNo;
    }

    public String getSubMchId() {
        return subMchId;
    }

    public void setSubMchId(String subMchId) {
        this.subMchId = subMchId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }
}
