package com.otzg.wxpay.data;

public class WxRefundPayData extends WxPayData {
    //是微信系统为每一笔支付交易分配的订单号，通过这个订单号可以标识这笔交易，它由支付订单API支付成功时返回的数据里面获取到。建议优先使用
    //商户系统内部的订单号,transaction_id 、out_trade_no 二选一，如果同时存在优先级：transaction_id>out_trade_no
    String transactionId;

    //商户系统内部的退款单号，商户系统内部唯一，同一退款单号多次请求只退一笔
    String outRefundNo;

    //退款总金额，单位为分
    //     仅针对老资金流商户使用
    //     REFUND_SOURCE_UNSETTLED_FUNDS---未结算资金退款（默认使用未结算资金退款）
    //     REFUND_SOURCE_RECHARGE_FUNDS---可用余额退款
    Double refundFee;

    public WxRefundPayData(String subMchId, String transactionId, String outTradeNo, String outRefundNo, Double totalFee, Double refundFee) {
        this.subMchId = subMchId;
        this.transactionId = transactionId;
        this.outTradeNo = outTradeNo;
        this.outRefundNo = outRefundNo;
        this.totalFee = totalFee;
        this.refundFee = refundFee;
    }

    public String getSubMchId() {
        return subMchId;
    }

    public void setSubMchId(String subMchId) {
        this.subMchId = subMchId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getOutRefundNo() {
        return outRefundNo;
    }

    public void setOutRefundNo(String outRefundNo) {
        this.outRefundNo = outRefundNo;
    }

    public Double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Double totalFee) {
        this.totalFee = totalFee;
    }

    public Double getRefundFee() {
        return refundFee;
    }

    public void setRefundFee(Double refundFee) {
        this.refundFee = refundFee;
    }
}
