package com.otzg.pay.service;

import com.otzg.base.AbstractServ;
import com.otzg.pay.dao.PayAccountLogDao;
import com.otzg.pay.dao.PayChannelAccountDao;
import com.otzg.pay.entity.PayAccountLog;
import com.otzg.pay.entity.PayChannelAccount;
import com.otzg.util.DateUtil;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @Author G.
 * @Date 2019/11/27 0027 下午 1:47
 */
@Service
public class PayChannelAccountServImpl extends AbstractServ implements PayChannelAccountServ {
    @Autowired
    PayChannelAccountDao payChannelAccountDao;
    @Autowired
    PayAccountLogDao payAccountLogDao;

    @Override
    public PayChannelAccount findByAccountAndPayChannel(Long payAccountId, String payChannel) {
        return payChannelAccountDao.findByPayAccountIdAndPayChannel(payAccountId, payChannel).orElse(null);
    }

    @Override
    public PayChannelAccount findByUnitIdAndPayChannel(String unitId, String payChannel) {
        return payChannelAccountDao.findByUnitIdAndPayChannel(unitId, payChannel).orElse(null);
    }

    @Override
    @Transactional
    public int createPayChannelAccount(String channelAccountName,
                                       String channel,
                                       String payAccountName,
                                       Long payAccountId,
                                       String unitId) {

        Optional<PayChannelAccount> op = payChannelAccountDao.findByUnitIdAndPayChannel(unitId, channel);
        if (op.isPresent()) {
            return 1;
        }

        PayChannelAccount payChannelAccount = new PayChannelAccount();
        payChannelAccount.setId(getId());
        payChannelAccount.setCreateTime(DateUtil.now());
        payChannelAccount.setUpdateTime(DateUtil.now());
        payChannelAccount.setStatus(0);

        payChannelAccount.setUnitId(unitId);
        payChannelAccount.setPayAccountId(payAccountId);
        payChannelAccount.setPayAccountName(payAccountName);

        payChannelAccount.setPayChannel(channel);
        payChannelAccount.setPayChannelAccountName(channelAccountName);
        payChannelAccount.setBalance(BigDecimal.ZERO);
        payChannelAccount.setFreezeBalance(BigDecimal.ZERO);
        payChannelAccountDao.save(payChannelAccount);
        return 0;
    }

    @Override
    @Transactional
    public int setPayChannelAccount(String unitId,
                                    String channel,
                                    String channelId,
                                    String channelAccount) {


        Optional<PayChannelAccount> op = payChannelAccountDao.findByUnitIdAndPayChannel(unitId, channel);
        if (!op.isPresent()) {
            return 1;
        }

        PayChannelAccount payChannelAccount = op.get();
        payChannelAccount.setUpdateTime(DateUtil.now());
        payChannelAccount.setStatus(1);
        payChannelAccount.setPayChannelId(channelId);
        payChannelAccount.setPayChannelAccount(channelAccount);

        payChannelAccountDao.save(payChannelAccount);
        return 0;
    }




    @Override
    @Transactional
    public int add(String unitId,
                   String payOrderNo,
                   String subject,
                   String payChannel,
                   String payChannelNo,
                   BigDecimal amount) {

        PayChannelAccount payChannelAccount = payChannelAccountDao.findByUnitIdAndPayChannel(unitId, payChannel).orElse(null);
        if(null == payChannel){
            PT("支付渠道账户未创建:{unitId=%s payChannel=%s}",unitId,payChannel);
            return 1;
        }

        //对支付渠道账户加锁
        RLock lock = redisson.getLock(payChannelAccount.getId().toString());
        try {
            boolean l = lock.tryLock(30, TimeUnit.SECONDS);
            if (!l) {
                P("redisson lock false");
                return 1;
            }

            payChannelAccount.setBalance(payChannelAccount.getBalance().add(amount));
            payChannelAccount.setUpdateTime(DateUtil.now());
            payChannelAccountDao.save(payChannelAccount);

            PayAccountLog payAccountLog = new PayAccountLog();
            payAccountLog.setId(getId());
            payAccountLog.setBalanceBefore(payChannelAccount.getBalance().subtract(amount));
            payAccountLog.setBalanceAfter(payChannelAccount.getBalance());
            payAccountLog.setCreateTime(DateUtil.now());
            payAccountLog.setDetails(subject);
            payAccountLog.setUnitId(unitId);
            payAccountLog.setPayChannel(payChannel);
            payAccountLog.setPayChannelNo(payChannelNo);
            payAccountLog.setPayOrderNo(payOrderNo);
            payAccountLog.setIsRead(0);
            payAccountLog.setProfitType(0);
            payAccountLogDao.save(payAccountLog);
            return 0;
        } catch (Exception e) {
            rollBack();
            P("error");
            return 2;
        } finally {
            lock.unlock();
            P("redisson lock unlock");
        }
    }
    @Override
    @Transactional
    public boolean substract(PayChannelAccount payChannelAccount,
                             String unitId,
                             String payChannel,
                             String payOrderNo,
                             String subject,
                             String payChannelNo,
                             BigDecimal amount) {

        //冻结金额删除
        if (!unFreezeBalance(payChannelAccount, amount, false)) {
            return false;
        }

        PayAccountLog payAccountLog = new PayAccountLog();
        payAccountLog.setId(getId());
        //账户操作前的余额等于当前的余额加上冻结的金额
        payAccountLog.setBalanceBefore(payChannelAccount.getBalance().add(amount));
        //操作后端余额
        payAccountLog.setBalanceAfter(payChannelAccount.getBalance());
        payAccountLog.setCreateTime(DateUtil.now());
        payAccountLog.setDetails(subject);
        payAccountLog.setUnitId(unitId);
        payAccountLog.setPayChannel(payChannel);
        payAccountLog.setPayChannelNo(payChannelNo);
        payAccountLog.setPayOrderNo(payOrderNo);
        payAccountLog.setIsRead(0);
        payAccountLog.setProfitType(0);
        payAccountLogDao.save(payAccountLog);
        return true;
    }

    /**
     * 两阶段事务动账之前需要冻结付款金额
     * 1.余额-冻结金额
     * 2.增加冻结金额
     * <p>
     * 要求：
     * 开启全局账户锁
     *
     * @param freezeAmount
     * @return
     */
    @Override
    @Transactional
    public boolean freezeBalance(PayChannelAccount payChannelAccount, BigDecimal freezeAmount) {
        //余额减掉冻结金额
        payChannelAccount.setBalance(payChannelAccount.getBalance().subtract(freezeAmount));
        payChannelAccount.setFreezeBalance(payChannelAccount.getFreezeBalance().add(freezeAmount));
        payChannelAccount.setUpdateTime(DateUtil.now());
        payChannelAccountDao.save(payChannelAccount);
        P("冻结账户余额成功");
        return true;
    }

    /**
     * 解冻冻结金额
     * 1.删除冻结金额
     * 2.如果tag为true,余额增加。
     *
     * @param freezeAmount
     * @return
     */
    @Override
    @Transactional
    public boolean unFreezeBalance(PayChannelAccount payChannelAccount, BigDecimal freezeAmount, boolean tag) {
        //减掉冻结金额
        payChannelAccount.setFreezeBalance(payChannelAccount.getFreezeBalance().subtract(freezeAmount));

        //如果tag为真表示要返还到余额
        if (tag) {
            payChannelAccount.setBalance(payChannelAccount.getBalance().add(freezeAmount));
        }

        payChannelAccount.setUpdateTime(DateUtil.now());
        payChannelAccountDao.save(payChannelAccount);
        P("冻结账户余额成功");
        return true;
    }

}
