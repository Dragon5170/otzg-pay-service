package com.otzg.wxpay.entity;

import com.otzg.util.DateUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;


/**
 * 微信红包
 * 1.红包分两种 a.普通红包，b.裂变红包
 * 2.普通红包 client_ip 为必填项
 * 3.裂变红包 amtType 为必填项 表示红包分配规则
 */
@Entity
@Table
public class WxRedPack implements Serializable {
    @Id
    Long id;

    //子商户号
    @Column(name = "sub_mch_id", nullable = false, length = 32)
    String subMchId;

    /**
     * 对应微信接口的msgappid
     *
     * 服务商模式下触达用户时的appid(可填服务商自己的appid或子商户的appid)，
     * 服务商模式下必填，服务商模式下填入的子商户appid必须在微信支付商户平台中先录入，
     * 否则会校验不过。
     */
    @Column(name = "msg_app_id", length = 32)
    String msgAppId;

    //商户名称
    @Column(name = "send_name", nullable = false, length = 32)
    String sendName;

    //用户openid接收红包的种子用户（首个用户）用户在wxappid下的openid
    @Column(name = "re_open_id", nullable = false, length = 32)
    String reOpenId;

    //红包祝福语
    @Column(name = "wishing", nullable = false, length = 128)
    String wishing;


    //发送者ip(普通红包必填项)
    @Column(name = "client_ip", length = 15)
    String clientIp;

    /**
     * 红包分配方式(裂变红包必填)
     * <p>
     * 微信规则
     * 红包金额设置方式
     * ALL_RAND—全部随机,商户指定总金额和红包发放总人数，由微信支付随机计算出各红包金额
     */
    @Column(name = "amt_type", length = 32)
    String amtType;


    //活动名称
    @Column(name = "act_name", nullable = false, length = 32)
    String actName;
    //备注
    @Column(name = "remark", nullable = false, length = 256)
    String remark;

    //支付发生额
    @Column(name = "amount", nullable = false, precision = 8, scale = 2)
    BigDecimal amount;

    //红包发放总人数
    @Column(name = "total_num", nullable = false, length = 8)
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
    @Column(name = "scene_id", length = 32)
    String sceneId;


    //发起人id
    @Column(name = "member_id", length = 32)
    String memberId;

    //收单商户id
    @Column(name = "unit_id", length = 32)
    String unitId;

    /**
     * 商户订单号
     * <p>
     * 对应微信接口变量(mch_billno)
     * <p>
     * 微信规则：
     * 商户订单号（每个订单号必须唯一）
     * 组成：mch_id+yyyymmdd+10位一天内不能重复的数字。
     * 接口根据商户订单号支持重入，如出现超时可再调用。
     */
    @Column(name = "pay_red_pack_order_no", nullable = false, length = 28)
    String payRedPackOrderNo;

    @Column(name = "red_pack_order_no", nullable = false, length = 28)
    String redPackOrderNo;

    //创建时间
    @Column(name = "create_time", nullable = false, length = 19)
    Date createTime;
    //修改时间
    @Column(name = "update_time", nullable = false, length = 19)
    Date updateTime;
    //账户状态{-1:发送失败,0:发送中,1:已发送,2:已接收,3:已退款}
    @Column(name = "status", nullable = false, length = 1)
    Integer status = 1;


    public WxRedPack() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubMchId() {
        return subMchId;
    }

    public void setSubMchId(String subMchId) {
        this.subMchId = subMchId;
    }

    public String getMsgAppId() {
        return msgAppId;
    }

    public void setMsgAppId(String msgAppId) {
        this.msgAppId = msgAppId;
    }

    public String getPayRedPackOrderNo() {
        return payRedPackOrderNo;
    }

    public void setPayRedPackOrderNo(String payRedPackOrderNo) {
        this.payRedPackOrderNo = payRedPackOrderNo;
    }

    public String getRedPackOrderNo() {
        return redPackOrderNo;
    }

    public void setRedPackOrderNo(String redPackOrderNo) {
        this.redPackOrderNo = redPackOrderNo;
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

    public String getAmtType() {
        return amtType;
    }

    public void setAmtType(String amtType) {
        this.amtType = amtType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getBaseJson() {
        return new StringJoiner(", ", "{", "}")
                .add("id:" + id)
                .add("msgAppId:'" + Optional.ofNullable(msgAppId).orElse("") + "'")
                .add("sendName:'" + sendName + "'")
                .add("reOpenId:'" + reOpenId + "'")
                .add("wishing:'" + wishing + "'")
                .add("clientIp:'" + Optional.ofNullable(clientIp).orElse("") + "'")
                .add("amtType:'" + Optional.ofNullable(amtType).orElse("") + "'")
                .add("actName:'" + actName + "'")
                .add("remark:'" + remark + "'")
                .add("amount:" + amount)
                .add("sceneId:'" + Optional.ofNullable(sceneId).orElse("") + "'")
                .add("createTime:'" + DateUtil.dateTime2Str(createTime)+ "'")
                .add("updateTime:'" + DateUtil.dateTime2Str(updateTime)+ "'")

                .add("memberId:'" + memberId + "'")
                .add("unitId:'" + unitId + "'")
                .add("payRedPackOrderNo:'" + payRedPackOrderNo + "'")
                .add("redPackOrderNo:'" + redPackOrderNo + "'")
                .add("status:" + status)
                .add("statusType:'" + StatusType.tips(status)+"'")
                .toString();
    }

    public String getJson() {
        return new StringJoiner(", ", "{", "}")
                .add("id:" + id)
                .add("subMchId:'" + subMchId + "'")
                .add("msgAppId:'" + Optional.ofNullable(msgAppId).orElse("") + "'")
                .add("payRedPackOrderNo:'" + payRedPackOrderNo + "'")
                .add("redPackOrderNo:'" + redPackOrderNo + "'")
                .add("sendName:'" + sendName + "'")
                .add("reOpenId:'" + reOpenId + "'")
                .add("wishing:'" + wishing + "'")
                .add("clientIp:'" + Optional.ofNullable(clientIp).orElse("") + "'")
                .add("amtType:'" + Optional.ofNullable(amtType).orElse("") + "'")
                .add("actName:'" + actName + "'")
                .add("remark:'" + remark + "'")
                .add("amount:" + amount)
                .add("totalNum:" + totalNum)
                .add("sceneId:'" + Optional.ofNullable(sceneId).orElse("") + "'")
                .add("createTime:'" + DateUtil.dateTime2Str(createTime)+ "'")
                .add("updateTime:'" + DateUtil.dateTime2Str(updateTime)+ "'")
                .add("status:" + status)
                .add("statusType:'" + StatusType.tips(status)+"'")
                .toString();
    }

    //账户状态{-1:发送失败,0:发送中,1:已发送,2:已接收,3:已退款}
    public enum StatusType {
        FAIL(-1,"发送失败"),
        WAIT(0,"发送中"),
        SENT(1,"已发送"),
        RECEIVED(2,"已接收"),
        REFUND(3,"已退款");

        public Integer index;
        public String name;
        StatusType(Integer i, String n) {
            this.index = i;
            this.name = n;
        }

        //判断是否在枚举类型内的值
        public final static String tips(Integer i){
            return Arrays.asList(StatusType.values())
                    .stream()
                    .filter(statusType -> statusType.index.equals(i))
                    .map(statusType -> statusType.name)
                    .findFirst()
                    .orElse("");
        }
    }
}
