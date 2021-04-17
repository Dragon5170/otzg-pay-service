package com.otzg.pay.service;

import com.otzg.pay.entity.PayChannelAccount;

import java.math.BigDecimal;

/**
 * 支付渠道账户
 */
public interface PayChannelAccountServ {

    PayChannelAccount findByUnitIdAndPayChannel(String unitId, String payChannel);

    /**
     * 单位账户绑定支付渠道账号
     * 1、禁止更新账户其他信息
     * 2、一个会员单位只能有一个账户
     *
     * @param channelAccountName 支付渠道账号名称
     * @param channel            aplipay:支付宝,wxpay:微信支付,xghpay:象过河支付
     * @author G/2018/6/29 12:18
     */
    int createPayChannelAccount(String channelAccountName,
                                String channel,
                                String payAccountName,
                                Long payAccountId,
                                String unitId);

    /**
     * 商户授权以后绑定支付渠道账号
     *
     * @param unitId
     * @param channel
     * @param channelId       保存支付宝商户id，保存微信商户id
     * @param channelAccount  保存支付宝授权token，保存微信商户id
     * @return
     */
    int setPayChannelAccount(String unitId,
                             String channel,
                             String channelId,
                             String channelAccount);


    //根据基本账户获取支付渠道账户信息
    PayChannelAccount findByAccountAndPayChannel(Long payAccountId, String payChannel);


    /**
     * 收款进账业务
     * 前置：
     * 1.对支付渠道账户加锁
     * 操作：
     * 1.更新收款账户余额及发生额
     * 2.保存收款账户记录
     *
     * @param unitId
     * @param payOrderNo
     * @param subject
     * @param payChannel
     * @param payChannelNo
     * @param amount
     * @return
     */
    int add(String unitId, String payOrderNo, String subject, String payChannel, String payChannelNo, BigDecimal amount);


    /**
     * 付款出账业务
     *
     * 前置：
     * 1.对支付渠道账户加锁
     * 2.冻结待付款金额
     * 操作：
     * 1.调支付渠道接口
     * 2.成功则生成出账记录
     *
     * 后置:
     * 1.解冻冻结金额.
     * 2.不成功则返还余额
     * 3.账户解锁
     *
     * @param payOrderNo
     * @param subject
     * @param payChannelNo
     * @param amount
     * @return
     */
    boolean substract(PayChannelAccount payChannelAccount, String unitId, String payChannel, String payOrderNo, String subject, String payChannelNo, BigDecimal amount);


    /**
     * 冻结付款余额
     *
     * 前置：
     * 1.必须对支付渠道账户加锁
     * 操作：
     * 1.第一阶段账户预处理业务
     * 2.账户余额减少
     * 3.冻结金额增加
     */
    boolean freezeBalance(PayChannelAccount payChannelAccount, BigDecimal freezeAmount);

    /**
     * 解冻冻结金额
     * 前置：
     * 1.必须对支付渠道账户加锁
     * 操作：
     * 1.删除冻结金额
     * 2.如果tag为true,余额增加。
     */
    boolean unFreezeBalance(PayChannelAccount payChannelAccount, BigDecimal freezeAmount, boolean tag);

}


