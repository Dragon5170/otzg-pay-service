package com.otzg.pay.service;

import com.otzg.base.AbstractServ;
import com.otzg.base.Finder;
import com.otzg.base.Page;
import com.otzg.util.ResultUtil;
import com.otzg.pay.dao.RefundOrderDao;
import com.otzg.pay.dao.RefundOrderLogDao;
import com.otzg.pay.dto.PayRefundOrderDto;
import com.otzg.pay.entity.PayChannelAccount;
import com.otzg.pay.entity.PayOrder;
import com.otzg.pay.entity.RefundOrder;
import com.otzg.pay.entity.RefundOrderLog;
import com.otzg.pay.util.PayRefund;
import com.otzg.pay.util.PayRefundUtil;
import com.otzg.pay.enums.PayStatus;
import com.otzg.util.*;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

/**
 * @Author G.
 * @Date 2019/11/27 0027 上午 11:31
 */
@Service
public class RefundOrderServImpl extends AbstractServ implements RefundOrderServ {
    @Autowired
    RefundOrderDao refundOrderDao;

    @Autowired
    PayChannelAccountServ payChannelAccountServ;
    @Autowired
    RefundOrderLogDao refundOrderLogDao;

    @Override
    public RefundOrder findByOrderNo(String refundOrderNo) {
        return refundOrderDao.findByRefundOrderNo(refundOrderNo).orElse(null);
    }

    @Override
    public boolean checkByRefundOrderNo(String refundOrderNo) {
        return refundOrderDao.findByRefundOrderNo(refundOrderNo).isPresent();
    }

    /**
     * 执行退款业务
     * 1.收款单支付渠道，收款单号，收款金额
     * 2.冻结收款渠道账户金额
     * 3.执行退款业务
     *
     * @param payOrder
     * @param payRefundOrderDto
     * @return
     */
    @Override
    @Transactional
    public int refundByUnit(Long payAccountId, PayOrder payOrder, PayRefundOrderDto payRefundOrderDto) {
        //获取支付渠道商户账户
        PayChannelAccount payChannelAccount = payChannelAccountServ.findByAccountAndPayChannel(payAccountId, payOrder.getPayChannel());
        //如果支付渠道账户不存在或者支付账户余额小于退款金额返回失败
        if (null == payChannelAccount
                || FuncUtil.getBigDecimalScale(payChannelAccount.getBalance()).compareTo(FuncUtil.getBigDecimalScale(new BigDecimal(payRefundOrderDto.getAmount()))) < 0) {
            return 2;
        }

        //对支付渠道账户加锁
        RLock lock = redisson.getLock(payChannelAccount.toString());
        try {
            boolean l = lock.tryLock(30, TimeUnit.SECONDS);
            if (!l) {
                P("redisson lock false");
                return 5;
            }

            //判断该退款单号是否已经生成
            if (checkByRefundOrderNo(payRefundOrderDto.getRefundOrderNo())) {
                return 1;
            }

            //判断是否还可以退款
            if(!checkCanRefund(payOrder, payRefundOrderDto.getAmount())){
                return -1;
            }

            //如果冻结账户不成功
            PT("冻结支付渠道账户退款余额");
            if (!payChannelAccountServ.freezeBalance(payChannelAccount, new BigDecimal(payRefundOrderDto.getAmount()))) {
                return 3;
            }
            PT("冻结支付渠道账户退款余额成功。开始执行退款业务=> refundOrderDto.orderNo="+ payRefundOrderDto.getSubOrderNo());

            RefundOrder refundOrder = createRefundOrder(payOrder.getPayChannel(), payOrder.getPayChannelAccount(), payOrder.getPayOrderNo(), payRefundOrderDto);
            PT("创建退款业务单成功=> refundOrder.getRefundOrderNo="+refundOrder.getRefundOrderNo());

            //=======================================提交支付渠道退款===================================================
            //策略模式获取退款渠道
            PayRefund payRefund = new PayRefundUtil(payOrder.getPayChannel());
            //提交支付渠道退款
            Map payResult = payRefund.refund(payChannelAccount.getPayChannelAccount(), payOrder.getPayOrderNo(),refundOrder.getPayRefundOrderNo(), payRefundOrderDto);
            //如果没有返回成功
            if (payResult.get("result").equals(PayStatus.FAILED.status)) {
                PT("退款业务执行失败,账户冻结余额解冻");
                payChannelAccountServ.unFreezeBalance(payChannelAccount, new BigDecimal(payRefundOrderDto.getAmount()), true);
                return 4;
            }

            PT("执行退款业务成功 result data="+payResult.get("body"));


            String payChannelNo = payResult.get("body").toString();
            //更新退款单
            finishRefundOrder(refundOrder, payChannelNo);

            //更新账户余额
            payChannelAccountServ.substract(payChannelAccount, payRefundOrderDto.getUnitId(), payOrder.getPayChannel(), refundOrder.getPayRefundOrderNo(), payRefundOrderDto.getSubject(), payChannelNo, new BigDecimal(payRefundOrderDto.getAmount()));

            PT("更新退款单成功");
            return 0;

        } catch (Exception e) {
            rollBack();
            P("error");
            return 6;
        } finally {
            lock.unlock();
            P("redisson lock unlock");
        }
    }

    /**
     * 检查收款业务单是否还能退款
     * @param payOrder
     * @param amount
     * @return
     */
    boolean checkCanRefund(PayOrder payOrder,Double amount){
        List<RefundOrder> refundOrderList = refundOrderDao.findByPayOrderNo(payOrder.getPayOrderNo());
        BigDecimal am = refundOrderList.stream().filter(refundOrder -> refundOrder.getStatus().equals(1)).map(RefundOrder::getAmount).reduce(BigDecimal.ZERO,BigDecimal::add);
        //如果没有退过款
        if(am.equals(BigDecimal.ZERO)){
            return true;
        }

        //退款金额加上已经退款的金额
        am = FuncUtil.getBigDecimalScale(am.add(FuncUtil.getBigDecimalScale(new BigDecimal(amount))));


        //如果退款金额大于已经退款的金额
        if(am.compareTo(payOrder.getAmount())>0){
            return false;
        }
        return true;
    }

    //创建退款业务单,并返回退款单号
    RefundOrder createRefundOrder(String payChannel, String payChannelAccount,String payOrderNo, PayRefundOrderDto payRefundOrderDto) {
        RefundOrder refundOrder = new RefundOrder();

        refundOrder.setId(getId());
        refundOrder.setUnitId(payRefundOrderDto.getUnitId());
        refundOrder.setMemberId(payRefundOrderDto.getMemberId());
        //收款时候的业务单号
        refundOrder.setPayOrderNo(payOrderNo);
        //支付系统退款业务单号
        refundOrder.setPayRefundOrderNo(getPayOrderNo(payRefundOrderDto.getUnitId()));
        //子系统收款时候的业务单号
        refundOrder.setSubOrderNo(payRefundOrderDto.getSubOrderNo());
        //子系统退款业务单号
        refundOrder.setRefundOrderNo(payRefundOrderDto.getRefundOrderNo());


        refundOrder.setSubject(payRefundOrderDto.getSubject());
        refundOrder.setAmount(new BigDecimal(payRefundOrderDto.getAmount()));

        refundOrder.setPayChannel(payChannel);
        refundOrder.setPayChannelAccount(payChannelAccount);

        refundOrder.setCreateTime(DateUtil.now());
        refundOrder.setUpdateTime(DateUtil.now());
        refundOrder.setStatus(0);

        //保存退款单
        refundOrderDao.save(refundOrder);

        //返回退款单号
        return refundOrder;
    }

    void finishRefundOrder(RefundOrder refundOrder, String payChannelNo) {
        refundOrder.setUpdateTime(DateUtil.now());
        refundOrder.setStatus(1);
        //保存退款单
        refundOrderDao.save(refundOrder);

        //增加一个退款记录单
        RefundOrderLog refundOrderLog = new RefundOrderLog();
        refundOrderLog.setId(getId());

        refundOrderLog.setPayRefundOrderNo(refundOrder.getPayRefundOrderNo());
        refundOrderLog.setSubOrderNo(refundOrder.getSubOrderNo());
        refundOrderLog.setRefundOrderNo(refundOrder.getRefundOrderNo());
        refundOrderLog.setAmount(refundOrder.getAmount());
        refundOrderLog.setCreateTime(DateUtil.now());

        refundOrderLog.setPayChannel(refundOrder.getPayChannel());
        //支付渠道返回的退款单号
        refundOrderLog.setPayChannelNo(payChannelNo);
        refundOrderLog.setUnitId(refundOrder.getUnitId());
        refundOrderLog.setStatus(1);
        refundOrderLogDao.save(refundOrderLog);
    }

    @Override
    @Transactional
    public int queryRefundOrderByUnit(RefundOrder refundOrder) {
        PayRefund payRefund = new PayRefundUtil(refundOrder.getPayChannel());
        Map payResult = payRefund.query(refundOrder.getPayChannelAccount(), refundOrder.getPayOrderNo(),refundOrder.getPayRefundOrderNo());
        P("微信退款查询结果=>" + payResult);
        if (payResult.get("result").equals(PayStatus.FAILED.status)) {
            return 1;
        }

        //{"msg":"退款成功","code":"SUCCESS","data":"4200000435201911279540935041","success":true}
        //{"msg":"调用失败","code":"FAIL","success":false}

        //更新退款单
        finishRefundOrder(refundOrder, payResult.get("body").toString());

        return 0;
    }


    @Override
    public Map findRefundOrderByUnit(Finder finder, String unitId, String payChannel) {
        Page<RefundOrder> page = findByUnit(finder,unitId,payChannel);
        return ResultUtil.getPageJson(page.getTotalPages(),page.getTotalCount(),page.getItems()
                .stream()
                .map(refundOrder->FastJsonUtil.getJson(refundOrder.getJson()))
                .toArray());
    }

    Page<RefundOrder> findByUnit(Finder finder, String unitId, String payChannel){
        StringJoiner hql = new StringJoiner(" ");
        hql.add("select ro from RefundOrder ro where ro.unitId='"+unitId+"'");
        if(!CheckUtil.isEmpty(payChannel))
            hql.add(" and ro.payChannel='"+payChannel+"'");
        if(!CheckUtil.isEmpty(finder.getStatus()))
            hql.add(" and ro.status="+finder.getStatus());
        if(!CheckUtil.isEmpty(finder.getStartTime()))
            hql.add(" and ro.createTime >= '"+finder.getStartTime()+"'");
        if(!CheckUtil.isEmpty(finder.getEndTime()))
            hql.add(" and ro.updateTime <= '"+finder.getEndTime()+"'");

        hql.add("order by  ro.updateTime desc");
        return baseDao.findPageByHql(hql.toString(),finder.getPageSize(),finder.getStartIndex());
    }
}

