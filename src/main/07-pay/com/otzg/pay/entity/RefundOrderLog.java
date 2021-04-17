package com.otzg.pay.entity;

import com.otzg.util.DateUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Author G.
 * @Date 2019/11/27 0027 下午 6:03
 */
@Entity
@Table
public class RefundOrderLog implements Serializable {

    @Id
    Long id;

    //绑定商户号
    @Column(name = "unit_id",length = 32,nullable = false)
    String unitId;

    //支付系统退款业务单号
    @Column(name = "pay_refund_order_no", length = 64, nullable = false, unique = true)
    String payRefundOrderNo;

    //子系统收款业务单号
    @Column(name = "sub_order_no",length = 64,nullable = false)
    String subOrderNo;

    //子系统退款业务单号
    @Column(name = "refund_order_no",length = 64,nullable = false)
    String refundOrderNo;

    //退款金额额
    @Column(name = "amount", precision = 8, scale = 2,nullable = false)
    BigDecimal amount;

//

    //=============================支付渠道=============================//
    /**
     * 支付
     * @author G/2018/6/4 11:47
     */


    //支付渠道{aplipay:支付宝,wxpay:微信支付}
    @Column(name = "pay_channel",nullable = false,length=8)
    String payChannel;

    //支付渠道单号
    @Column(name = "pay_channel_no",nullable = false,length = 64)
    String payChannelNo;

    //========================支付状态==============================//
    //创建时间
    @Column(name = "create_time", nullable = false, length = 19)
    Date createTime;

    //支付状态{-1:失败,1:成功}
    @Column(name = "status",nullable = false,length = 1)
    Integer status;

    public RefundOrderLog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getPayRefundOrderNo() {
        return payRefundOrderNo;
    }

    public void setPayRefundOrderNo(String payRefundOrderNo) {
        this.payRefundOrderNo = payRefundOrderNo;
    }

    public String getSubOrderNo() {
        return subOrderNo;
    }

    public void setSubOrderNo(String subOrderNo) {
        this.subOrderNo = subOrderNo;
    }

    public String getRefundOrderNo() {
        return refundOrderNo;
    }

    public void setRefundOrderNo(String refundOrderNo) {
        this.refundOrderNo = refundOrderNo;
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

    public String getPayChannelNo() {
        return payChannelNo;
    }

    public void setPayChannelNo(String payChannelNo) {
        this.payChannelNo = payChannelNo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Map getJson(){
        Map<String,Object> jo = new TreeMap<>();
        jo.put("id",this.getId());
        jo.put("unitId",this.getUnitId());

        //支付系统业务单号
        jo.put("payRefundOrderNo",this.getPayRefundOrderNo());
        //子系统收款单号
        jo.put("subOrderNo",this.getSubOrderNo());
        //子系统退款单号
        jo.put("refundOrderNo",this.getRefundOrderNo());

        jo.put("amount",this.getAmount());

        //支付渠道
        jo.put("payChannel",this.getPayChannel());
        //支付渠道单号
        jo.put("payChannelNo",this.getPayChannelNo());

        //支付时间
        jo.put("createTime", DateUtil.dateTime2Str(this.getCreateTime()));

        //订单支付状态{-1:失败,1:成功}
        jo.put("status",this.getStatus());
        return jo;

    }
}
