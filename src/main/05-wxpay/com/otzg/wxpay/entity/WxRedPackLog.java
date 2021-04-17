package com.otzg.wxpay.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author G.
 * @Date 2019/12/13 0013 下午 3:51
 */
@Entity
@Table
public class WxRedPackLog implements Serializable {

    @Id
    Long id;

    //商户名称
    @Column(name = "send_name", nullable = false, length = 32)
    String sendName;

    //用户openid接收红包的种子用户（首个用户）用户在wxappid下的openid
    @Column(name = "re_open_id", nullable = false, length = 32)
    String reOpenId;

    //支付发生额
    @Column(name = "amount", nullable = false, precision = 8, scale = 2)
    BigDecimal amount;

    //红包发放总人数
    @Column(name = "total_num", nullable = false, length = 8)
    Integer totalNum;

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

    @Lob
    String hblist;
    /**
     * SENDING:发放中
     * SENT:已发放待领取
     * FAILED：发放失败
     * RECEIVED:已领取
     * RFUND_ING:退款中
     * REFUND:已退款
     */
    @Column(name = "status", nullable = false, length = 16)
    String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHblist() {
        return hblist;
    }

    public void setHblist(String hblist) {
        this.hblist = hblist;
    }
}
