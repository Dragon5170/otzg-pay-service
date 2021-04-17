package com.otzg.wxpay.dto;

import java.io.Serializable;

/**
 * @Author G.
 * @Date 2019/12/13 0013 上午 11:06
 */
public class WxRedPackDto implements Serializable {

    /**
     * 服务商模式下触达用户时的appid
     * (可填服务商自己的appid或子商户的appid)
     *
     * 非必填
     * String(32)
     */
    String msgAppId;

    /**
     * 商户名称
     * 必填
     * String(32)
     */
    String sendName;

    /**
     * 用户openid接收红包的种子用户（首个用户）用户在wxappid下的openid
     * 必填
     */
    String reOpenId;

    /**
     * 红包祝福语
     * 必填
     * String(128)
     */
    String wishing;


    /**
     * 发送者ip(普通红包必填项)
     */
    String clientIp;

    /**
     * 红包分配方式
     * <p>
     * 微信规则
     * 红包金额设置方式
     * ALL_RAND—全部随机,商户指定总金额和红包发放总人数，由微信支付随机计算出各红包金额
     */
    String amtType;


    /**
     * 活动名称
     * String(32)
     */
    String actName;
    /**
     * 备注
     */
    String remark;
    /**
     * 发放金额
     */
    Double totalAmount;
    /**
     *
     * 红包数量
     */
    Integer totalNum;

    /**
     * 场景id发
     * 放红包使用场景，红包金额大于200或者小于1元时必传
     * PRODUCT_1:商品促销
     * PRODUCT_2:抽奖
     * PRODUCT_3:虚拟物品兑奖
     * PRODUCT_4:企业内部福利
     * PRODUCT_5:渠道分润
     * PRODUCT_6:保险回馈
     * PRODUCT_7:彩票派奖
     * PRODUCT_8:税务刮奖
     */
    String sceneId;


    /**
     * 发起人id
     * String(32)
     */
    String memberId;

    /**
     * 商户id
     * String(32)
     */
    String unitId;

    /**
     * 子系统发红包业务单号
     * String(32)
     */
    String redPackOrderNo;

    public String getMsgAppId() {
        return msgAppId;
    }

    public void setMsgAppId(String msgAppId) {
        this.msgAppId = msgAppId;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public String getReOpenId() {
        return reOpenId;
    }

    public void setReOpenId(String reOpenId) {
        this.reOpenId = reOpenId;
    }

    public String getWishing() {
        return wishing;
    }

    public void setWishing(String wishing) {
        this.wishing = wishing;
    }

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getAmtType() {
        return amtType;
    }

    public void setAmtType(String amtType) {
        this.amtType = amtType;
    }

    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public String getSceneId() {
        return sceneId;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getRedPackOrderNo() {
        return redPackOrderNo;
    }

    public void setRedPackOrderNo(String redPackOrderNo) {
        this.redPackOrderNo = redPackOrderNo;
    }
}
