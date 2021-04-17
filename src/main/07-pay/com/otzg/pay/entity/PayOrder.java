package com.otzg.pay.entity;

import com.otzg.pay.dto.PayOrderDto;
import com.otzg.util.DateUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

/**
 * 支付订单类
 * 1、所有发生的支付业务生成支付订单
 * 2、每一个支付订单属性
 * 支付单号、
 * 支付项目、
 * 金额、
 * 业务单号、
 * 业务详情：{
 * 项目名称、
 * 数量
 * }
 * 付款单位、
 * 付款人、
 * 收款单位
 * <p>
 * <p>
 * 关键属性
 * 单据类型 payType:{1:余额收入,2:余额支出,3:交押金,4:退押金,5:收益收入,6:收益支出,7:积分收入,8:积分消费}
 * 支付单号 number
 * 业务单号 ordersNo
 * 业务发生额 amount
 * 付款发起人 payerMember
 * 付款单位 payerUnit
 * 收款单位 payeeUnit
 *
 * @author G./2018/5/29 10:14
 * <p>
 * 修改信息：
 * <p>
 * 2019-05-11
 * 为了游客收款业务
 * 1、付款人信息、付款人单位可以为null
 */

@Entity
public class PayOrder implements Serializable {

    @Id
    Long id;

    //支付渠道{支付宝、微信、邮储}
    @Column(name = "pay_channel", nullable = false, length = 16)
    String payChannel;

    //保存支付渠道的交易类型，各支付渠道的专用类型，微信{JSAPI,NATIVE ...}
    @Column(name = "pay_type", nullable = false, length = 16)
    String payType;

    //支付渠道收款账户{微信:subMchId,支付宝:pid,邮储:}
    @Column(name = "payee_channel_account", nullable = false, length = 64)
    String payChannelAccount;

    //支付项目
    @Column(name = "subject", nullable = false, length = 64)
    String subject;

    //支付发生额
    @Column(name = "amount", nullable = false, precision = 8, scale = 2)
    BigDecimal amount;

    //业务详情
    @Lob
    @Column(name = "details", columnDefinition = "TEXT")
    String details;


    //发起人id
    @Column(name = "member_id", length = 32)
    String memberId;

    //收单商户id
    @Column(name = "unit_id", length = 32)
    String unitId;


    //支付单号(由支付系统生成)
    @Column(name = "pay_order_no", length = 64, nullable = false, unique = true)
    String payOrderNo;

    //子系统业务单号(由子系统业务生成)
    @Column(name = "sub_order_no", length = 64, nullable = false, unique = true)
    String subOrderNo;

    //支付业务发起端id
    @Column(name = "app_id", length = 64)
    String appId;


    //回调子系统通知接口()
    @Column(name = "pay_notify")
    String payNotify;

    //通知回调接口成功,通知子系统支付结果{0:未收到,1:已收到,2回调失败}
    @Column(name = "pay_notify_status", length = 1)
    Integer payNotifyStatus = 0;

    //通知回调次数
    @Column(name = "pay_notify_times", length = 1)
    Integer payNotifyTimes = 0;


    //创建时间
    @Column(name = "create_time", nullable = false, length = 19)
    Date createTime;

    //修改时间
    @Column(name = "update_time", nullable = false, length = 19)
    Date updateTime;


    /**
     * 是否需要分账{0:否,1:是}
     */
    @Column(name = "is_profit_sharing", nullable = false, length = 1)
    Integer isProfitSharing = 0;

    /**
     * 支付订单状态{-1:支付失败,0:未支付,1:支付成功}
     * 2、支付成功订单状态置为1，
     * 3、支付失败订单状态为-1，
     */
    @Column(name = "status", nullable = false, length = 1)
    Integer status = 0;


    /**
     * 订单支付数据保存
     * 1.创建支付单后，获取预支付信息，保存到支付单
     * 2.如果子系统没有拉起支付或者链接失败可以重新获取
     */
    @Column(name = "pay_body",length = 1000)
    String payBody;


    public PayOrder() {
    }

    public PayOrder(String payOrderNo,                  //平台支付业务单号
                    String payChannelAccount,           //支付渠道收款账号
                    PayOrderDto payOrderDto) {          //支付业务单

        this.setPayChannel(payOrderDto.getPayChannel());
        this.setPayType(payOrderDto.getPayType());
        this.setPayChannelAccount(payChannelAccount);


        this.setMemberId(payOrderDto.getBuyerId());
        this.setUnitId(payOrderDto.getShopId());
        this.setPayNotify(payOrderDto.getPayNotify());


        this.setPayOrderNo(payOrderNo);
        this.setSubOrderNo(payOrderDto.getSubOrderNo());
        this.setSubject(payOrderDto.getSubject());
        this.setAmount(new BigDecimal(payOrderDto.getAmount()));

        this.setAppId(payOrderDto.getAppId());
        this.setDetails(payOrderDto.getDetail());

        this.setCreateTime(DateUtil.now());
        this.setUpdateTime(this.getCreateTime());

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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


    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPayOrderNo() {
        return payOrderNo;
    }

    public void setPayOrderNo(String payOrderNo) {
        this.payOrderNo = payOrderNo;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getPayChannelAccount() {
        return payChannelAccount;
    }

    public void setPayChannelAccount(String payChannelAccount) {
        this.payChannelAccount = payChannelAccount;
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

    public Integer getPayNotifyStatus() {
        return payNotifyStatus;
    }

    public void setPayNotifyStatus(Integer payNotifyStatus) {
        this.payNotifyStatus = payNotifyStatus;
    }

    public Integer getPayNotifyTimes() {
        return payNotifyTimes;
    }

    public void setPayNotifyTimes(Integer payNotifyTimes) {
        this.payNotifyTimes = payNotifyTimes;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public String getSubOrderNo() {
        return subOrderNo;
    }

    public void setSubOrderNo(String subOrderNo) {
        this.subOrderNo = subOrderNo;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPayNotify() {
        return payNotify;
    }

    public void setPayNotify(String payNotify) {
        this.payNotify = payNotify;
    }

    public String getMemberId() {
        return memberId;
    }

    public Integer getIsProfitSharing() {
        return isProfitSharing;
    }

    public void setIsProfitSharing(Integer isProfitSharing) {
        this.isProfitSharing = isProfitSharing;
    }

    public String getPayBody() {
        return payBody;
    }

    public void setPayBody(String payBody) {
        this.payBody = payBody;
    }

    public Map<String, Object> getBaseJson() {
        Map<String, Object> jo = new TreeMap<>();
        jo.put("id", this.getId());
        jo.put("payOrderNo", this.getPayOrderNo());
        jo.put("subject", this.getSubject());
        jo.put("amount", this.getAmount());
        //支付时间
        jo.put("createTime", DateUtil.dateTime2Str(this.getCreateTime()));
        jo.put("updateTime", DateUtil.dateTime2Str(this.getUpdateTime()));

        //支付订单状态{-2:订单删除,-1:支付失败,0:正在支付,1:支付成功}
        jo.put("status", this.getStatus());
        jo.put("statusTips", StatusType.tips(this.getStatus()));
        return jo;
    }

    public Map<String, Object> getJson() {
        Map<String, Object> jo = getBaseJson();
        //业务信息
        jo.put("subOrderNo", Optional.ofNullable(this.getSubOrderNo()).orElse(""));
        jo.put("details", Optional.ofNullable(this.getDetails()).orElse(""));


        //支付渠道
        jo.put("payChannel", this.getPayChannel());
        //支付类型
        jo.put("payType", this.getPayType());
        //支付渠道收款账户
        jo.put("payChannelAccount", this.getPayChannelAccount());


        /**
         * 回调付款通知
         * @author G/2018/7/5 16:05
         * @param
         */
        jo.put("payNotify", Optional.ofNullable(this.getPayNotify()).orElse(""));
        return jo;

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
