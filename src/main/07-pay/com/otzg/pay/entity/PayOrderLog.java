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
 *
 * 支付订单记录
 * @author G./2018/5/29 10:50
 */

@Entity
@Table
public class PayOrderLog implements Serializable {

    @Id
    Long id;

    //创建时间
    @Column(name = "create_time", nullable = false, length = 19)
    Date createTime;

    //绑定商户号
    @Column(name = "unit_id",length = 32,nullable = false)
    String unitId;

    //支付单号(由支付系统生成)
    @Column(name = "pay_order_no", length = 64, nullable = false)
    String payOrderNo;

    //子系统业务单号(由子系统业务生成)
    @Column(name = "sub_order_no",length = 64,nullable = false)
    String subOrderNo;


    //支付发生额
    @Column(name = "amount", precision = 8, scale = 2,nullable = false)
    BigDecimal amount;


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

    /**
     * 付款人支付渠道账号,
     * 1、支付渠道id或账号(对应支付宝账号(支付宝登录账号包含邮箱号或手机号),
     * 2、微信对应openId , mchid(公众号商户号|app商户号))
     * @author G/2018/6/4 11:51
     * @param null
     */
    @Column(name = "payer_channel_account",length = 64)
    String payerChannelAccount;

    /**
     * 收款人支付渠道账号
     * 1、支付渠道id或账号(对应支付宝账号(支付宝登录账号包含邮箱号或手机号),
     * 2、微信对应mchid(公众号商户号|app商户号))
     * @author G/2018/6/4 11:51
     * @param null
     */
    @Column(name = "payee_channel_account",length = 64)
    String payeeChannelAccount;

    //========================支付状态==============================//

    //支付状态{-1:失败,1:成功}
    @Column(name = "status",nullable = false,length = 1)
    Integer status;

    public PayOrderLog() {
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

    public String getPayOrderNo() {
        return payOrderNo;
    }

    public void setPayOrderNo(String payOrderNo) {
        this.payOrderNo = payOrderNo;
    }



    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getPayerChannelAccount() {
        return payerChannelAccount;
    }

    public void setPayerChannelAccount(String payerChannelAccount) {
        this.payerChannelAccount = payerChannelAccount;
    }

    public String getPayeeChannelAccount() {
        return payeeChannelAccount;
    }

    public void setPayeeChannelAccount(String payeeChannelAccount) {
        this.payeeChannelAccount = payeeChannelAccount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public String getSubOrderNo() {
        return subOrderNo;
    }

    public void setSubOrderNo(String subOrderNo) {
        this.subOrderNo = subOrderNo;
    }

    public Map getJson(){
        Map<String,Object> jo = new TreeMap<>();
        jo.put("id",this.getId());
        jo.put("unitId",this.getUnitId());

        //支付单号
        jo.put("payOrderNo",this.getPayOrderNo());
        //子系统业务单号
        jo.put("subOrderNo",this.getSubOrderNo());
        jo.put("amount",this.getAmount());

        //支付渠道
        jo.put("payChannel",this.getPayChannel());
        //支付渠道单号
        jo.put("payChannelNo",this.getPayChannelNo());

        //付款人渠道账号
        jo.put("payerChannelAccount",this.getPayerChannelAccount());
        //收款人渠道账号
        jo.put("payeeChannelAccount",this.getPayeeChannelAccount());

        //支付时间
        jo.put("createTime", DateUtil.dateTime2Str(this.getCreateTime()));

        //订单支付状态{-1:失败,1:成功}
        jo.put("status",this.getStatus());
        return jo;

    }
}
