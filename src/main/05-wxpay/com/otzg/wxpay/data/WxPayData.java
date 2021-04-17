package com.otzg.wxpay.data;

/**
 * 微信服务商收款的基类
 */
public abstract class WxPayData {
    //信支付分配的子商户号
    String subMchId;
    //商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一。
    String outTradeNo;
    //收款的金额
    Double totalFee;
    /**
     * 商品描述交易字段格式根据不同的应用场景建议按照以下格式上传：
     * （1）PC网站——传入浏览器打开的网站主页title名-实际商品名称，例如：腾讯充值中心-QQ会员充值；
     * （2） 公众号——传入公众号名称-实际商品名称，例如：腾讯形象店- image-QQ公仔；
     * （3） H5——应用在浏览器网页上的场景，传入浏览器打开的移动网页的主页title名-实际商品名称，例如：腾讯充值中心-QQ会员充值；
     * （4） 线下门店——门店品牌名-城市分店名-实际商品名称，例如： image形象店-深圳腾大- QQ公仔）
     * （5） APP——需传入应用市场上的APP名字-实际商品名称，天天爱消除-游戏充值。
     */
    String body;
    //附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
    String attach;

    public String getSubMchId() {
        return subMchId;
    }

    public void setSubMchId(String subMchId) {
        this.subMchId = subMchId;
    }

}
