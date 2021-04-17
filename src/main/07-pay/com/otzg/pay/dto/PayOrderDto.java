package com.otzg.pay.dto;

import java.io.Serializable;

/**
 * 微服务应用子系统调用微服务支付服务
 * 进行收款业务的数据传递类
 *
 * 1.必填参数通过构造函数创建
 *
 * v1.0.3
 * @Author G.
 * @Date 2019/12/31 下午 18:10
 */
public class PayOrderDto implements Serializable {

    /**
     * 支付渠道类型{wxpay,alipay,ycpay,...}
     * 必填
     * length(16)
     */
    String payChannel;

    /**
     * 支付平台支持的收单类型
     *
     * 微信：{JSAPI,APP,MWEB(H5),MICROPAY,NATIVE}
     * 支付宝：{APP,BARCODE,FACE,CREATE,PRECREATE}
     * 邮储：{}
     *
     * 必填
     * length(16)
     */
    String payType;






    /**
     * 子系统业务单号
     * 必填
     * length(32)
     */
    String subOrderNo;

    /**
     * 支付项目
     *
     * 必填
     * length(128)
     */
    String subject;


    /**
     * 发起支付的appId
     * 应用的标识
     * length(32)
     */
    String appId;

    /**
     * 支付成功回调地址
     * length(255)
     */
    String payNotify;

    /**
     * 子系统单位(商户)id
     *
     * 必填
     * length(32)
     */
    String shopId;

    /**
     * 子系统会员(个人)id
     * 业务发起人id
     * 微信支付者的openid
     * 支付宝付款者的id
     * length(32)
     */
    String buyerId;

    /**
     * 业务发生的金额
     * 必填
     */
    Double amount;

    /**
     * length(128)
     * 商品描述交易字段格式根据不同的应用场景建议按照以下格式上传：
     * （1）PC网站——传入浏览器打开的网站主页title名-实际商品名称，例如：腾讯充值中心-QQ会员充值；
     * （2） 公众号——传入公众号名称-实际商品名称，例如：腾讯形象店- image-QQ公仔；
     * （3） H5——应用在浏览器网页上的场景，传入浏览器打开的移动网页的主页title名-实际商品名称，例如：腾讯充值中心-QQ会员充值；
     * （4） 线下门店——门店品牌名-城市分店名-实际商品名称，例如： image形象店-深圳腾大- QQ公仔）
     * （5） APP——需传入应用市场上的APP名字-实际商品名称，天天爱消除-游戏充值。
     */
    String detail;

    /**
     * length(127)
     * 附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
     */
    String attach;

    /**
     * 是否需要分账
     * {0:否,1:是}
     */
    Integer isProfitSharing=0;

    /**
     * 如微信jsapi支付时需要获取终端的ip地址
     * 需要调用接口的机器的ip地址
     * length(64)
     */
    String ipAddress;

    /**
     * 扫码支付授权码
     * 微信条码付款，刷脸付必填
     * length(128)
     */
    String authCode;

    /**
     * 微信NATIVE支付时必传
     * length(32)
     */
    String productId;

    /**
     * 微信终端设备号(商户自定义，如门店编号)
     * length(32)
     */
    String deviceInfo;

    /**
     * 支付宝花呗分期数（目前仅支持3、6、12）
     */
    String hbFqNum;

    private PayOrderDto() {

    }

    //通过构造函数创建
    public PayOrderDto(String subOrderNo,
                       String payType,
                       String subject,
                       String shopId,
                       String payChannel,
                       Double amount) {

        this.subOrderNo = subOrderNo;
        this.payType = payType;
        this.subject = subject;
        this.shopId = shopId;
        this.payChannel = payChannel;
        this.amount = amount;
    }


    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setPayNotify(String payNotify) {
        this.payNotify = payNotify;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public void setIsProfitSharing(Integer isProfitSharing) {
        this.isProfitSharing = isProfitSharing;
    }

    public String getSubOrderNo() {
        return subOrderNo;
    }

    public String getPayType() {
        return payType;
    }

    public String getSubject() {
        return subject;
    }

    public String getAppId() {
        return appId;
    }

    public String getPayNotify() {
        return payNotify;
    }

    public String getShopId() {
        return shopId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public Double getAmount() {
        return amount;
    }

    public String getDetail() {
        return detail;
    }

    public String getAttach() {
        return attach;
    }

    public Integer getIsProfitSharing() {
        return isProfitSharing;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getHbFqNum() {
        return hbFqNum;
    }

    public void setHbFqNum(String hbFqNum) {
        this.hbFqNum = hbFqNum;
    }
}
