package com.otzg.wxpay.data;

/**
 * 微信扫码收款(被扫)
 * 微信刷脸收款(被扫)
 */
public class WxMicroPayData extends WxPayData {
    /**
     * 扫码支付授权码，设备读取用户微信中的条码或者二维码信息
     * （注：用户付款码条形码规则：18位纯数字，以10、11、12、13、14、15开头）
     */
    String authCode;
    String deviceInfo;
    boolean isProfitSharing;

    public WxMicroPayData() {
    }

    public WxMicroPayData(String subMchId, String attach, String outTradeNo, String authCode, String body, String deviceInfo, Double totalFee, boolean isProfitSharing) {
        this.subMchId = subMchId;
        this.attach = attach;
        this.outTradeNo = outTradeNo;
        this.authCode = authCode;
        this.body = body;
        this.deviceInfo = deviceInfo;
        this.totalFee = totalFee;
        this.isProfitSharing = isProfitSharing;
    }

    public String getSubMchId() {
        return subMchId;
    }

    public void setSubMchId(String subMchId) {
        this.subMchId = subMchId;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public Double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Double totalFee) {
        this.totalFee = totalFee;
    }

    public boolean isProfitSharing() {
        return isProfitSharing;
    }

    public void setProfitSharing(boolean profitSharing) {
        isProfitSharing = profitSharing;
    }
}
