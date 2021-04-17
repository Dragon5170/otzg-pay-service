package com.otzg.pay.entity;

import com.otzg.pay.enums.PayChannelType;
import com.otzg.util.DateUtil;
import com.otzg.util.FuncUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;
import java.util.StringJoiner;

/**
 * 记录账户流水信息
 * 关联一个类
 * 1、交易账户类 Account
 * 2、支付订单类 PayOrders (只关联订单号)
 */

@Entity
public class PayAccountLog implements Serializable {
    @Id
    Long id;

    @Column(name = "create_time", nullable = false, length = 19)
    Date createTime;

    //绑定商户号
    @Column(name = "unit_id",length = 32,nullable = false)
    String unitId;

    //支付单号(由支付系统生成)
    @Column(name = "pay_order_no", length = 64, nullable = false)
    String payOrderNo;


    //支付渠道{aplipay:支付宝,wxpay:微信支付,qxpay:象过河支付}
    @Column(name = "pay_channel", length = 16)
    String payChannel = PayChannelType.alipay.name();
    //支付渠道单号
    @Column(name = "pay_channel_no",nullable = false,length = 64)
    String payChannelNo;

    //交易前
    @Column(name = "balance_before", nullable = false, precision = 20, scale = 8)
    private BigDecimal balanceBefore = new BigDecimal("0");
    //交易后
    @Column(name = "balance_after", nullable = false, precision = 20, scale = 8)
    private BigDecimal balanceAfter = new BigDecimal("0");


    //交易描述信息
    @Column(name = "details")
    String details;

    /**
     * 收益类型
     * {0:收款(+),1:退款(-),2:提现(-),3:分账收入(+)}
     */
    @Column(name = "profit_type", nullable = false, length = 2)
    Integer profitType = 0;

    //是否已读
    @Column(name = "is_read", nullable = false, length = 1)
    Integer isRead = 0;

    //=============支付渠道end===============//


    public PayAccountLog() {
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public Integer getProfitType() {
        return profitType;
    }

    public void setProfitType(Integer profitType) {
        this.profitType = profitType;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    public String getPayChannelNo() {
        return payChannelNo;
    }

    public void setPayChannelNo(String payChannelNo) {
        this.payChannelNo = payChannelNo;
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

    public BigDecimal getBalanceBefore() {
        return balanceBefore;
    }

    public void setBalanceBefore(BigDecimal balanceBefore) {
        this.balanceBefore = balanceBefore;
    }

    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(BigDecimal balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    public String getJson() {
        return new StringJoiner(", ", "{", "}")
                .add("id:" + id)
                .add("createTime:'" + DateUtil.dateTime2Str(this.getCreateTime())+"'")
                .add("unitId:'" + unitId + "'")
                .add("payOrderNo:'" + payOrderNo + "'")
                .add("payChannel:'" + payChannel + "'")
                .add("payChannelNo:'" + payChannelNo + "'")
                .add("amount:" + FuncUtil.getBigDecimalScale(this.getBalanceAfter().subtract(this.getBalanceBefore())))
                .add("balanceAfter:" + FuncUtil.getBigDecimalScale(this.getBalanceAfter()))
                .add("details:'" + Optional.ofNullable(this.getDetails()).orElse("") + "'")
                .add("profitType:" + profitType)
                .add("isRead:" + isRead)
                .toString();
    }

}
