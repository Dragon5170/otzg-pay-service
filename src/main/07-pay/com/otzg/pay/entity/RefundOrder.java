package com.otzg.pay.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.StringJoiner;

/**
 * @Author G.
 * @Date 2019/11/27 0027 上午 11:14
 */
@Entity
@Table
public class RefundOrder implements Serializable {
    @Id
    Long id;

    //支付系统收款时候的业务单号
    @Column(name = "pay_order_no", length = 64, nullable = false)
    String payOrderNo;

    //支付系统退款业务单号
    @Column(name = "pay_refund_order_no", length = 64, nullable = false, unique = true)
    String payRefundOrderNo;

    //子系统收款时候的业务单号
    @Column(name = "sub_order_no", length = 64, nullable = false)
    String subOrderNo;

    //子系统退款业务单号(由子系统业务生成)
    @Column(name = "refund_order_no", length = 64, nullable = false, unique = true)
    String refundOrderNo;

    //发起人id
    @Column(name = "member_id", length = 32)
    String memberId;

    //退单商户id
    @Column(name = "unit_id", length = 32,nullable = false)
    String unitId;


    //退款项目
    @Column(name = "subject", nullable = false, length = 64)
    String subject;

    //退款发生额
    @Column(name = "amount", nullable = false, precision = 8, scale = 2)
    BigDecimal amount;

    //支付渠道{支付宝、微信、邮储}
    @Column(name = "pay_channel", nullable = false, length = 16)
    String payChannel;

    //支付渠道收款账户{微信:subMchId,支付宝:pid,邮储:}
    @Column(name = "payee_channel_account", nullable = false, length = 64)
    String payChannelAccount;

    //创建时间
    @Column(name = "create_time", nullable = false, length = 19)
    Date createTime;

    //修改时间
    @Column(name = "update_time", nullable = false, length = 19)
    Date updateTime;

    /**
     * 支付订单状态{-1:支付失败,0:未支付,1:支付成功}
     * 2、支付成功订单状态置为1，
     * 3、支付失败订单状态为-1，
     */
    @Column(name = "status", nullable = false, length = 1)
    Integer status = 0;

    public RefundOrder() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubOrderNo() {
        return subOrderNo;
    }

    public void setSubOrderNo(String subOrderNo) {
        this.subOrderNo = subOrderNo;
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

    public String getPayOrderNo() {
        return payOrderNo;
    }

    public void setPayOrderNo(String payOrderNo) {
        this.payOrderNo = payOrderNo;
    }

    public String getPayRefundOrderNo() {
        return payRefundOrderNo;
    }

    public void setPayRefundOrderNo(String payRefundOrderNo) {
        this.payRefundOrderNo = payRefundOrderNo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public String getPayChannelAccount() {
        return payChannelAccount;
    }

    public void setPayChannelAccount(String payChannelAccount) {
        this.payChannelAccount = payChannelAccount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getRefundOrderNo() {
        return refundOrderNo;
    }

    public void setRefundOrderNo(String refundOrderNo) {
        this.refundOrderNo = refundOrderNo;
    }


    public String getJson() {
        return new StringJoiner(", ", "{", "}")
                .add("id:" + id)
                .add("payOrderNo:'" + payOrderNo + "'")
                .add("payRefundOrderNo:'" + payRefundOrderNo + "'")
                .add("subOrderNo:'" + subOrderNo + "'")
                .add("refundOrderNo:'" + refundOrderNo + "'")
                .add("memberId:'" + Optional.ofNullable(memberId).orElse("") + "'")
                .add("unitId:'" + unitId + "'")
                .add("subject:'" + subject + "'")
                .add("amount:" + amount)
                .add("payChannel:'" + payChannel + "'")
                .add("payChannelAccount:'" + payChannelAccount + "'")
                .add("status:" + status)
                .add("statusTips:'" + StatusType.tips(status)+"'")
                .toString();
    }

    public enum StatusType {
        FAIL(-1,"失败"),
        WAIT(0,"等待"),
        SUCC(1,"成功");

        public Integer index;
        public String name;
        StatusType(Integer i, String n) {
            this.index = i;
            this.name = n;
        }

        //判断是否在枚举类型内的值
        public final static String tips(Integer i){
            return Arrays.stream(StatusType.values())
                    .filter(statusType -> statusType.index.equals(i))
                    .map(statusType -> statusType.name)
                    .findFirst()
                    .orElse("");
        }
    }
}
