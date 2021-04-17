package com.otzg.pay.service;

import com.alibaba.fastjson.JSONObject;
import com.otzg.base.*;
import com.otzg.pay.dao.PayOrderDao;
import com.otzg.pay.dao.PayOrderLogDao;
import com.otzg.pay.dto.PayOrderDto;
import com.otzg.pay.entity.PayOrder;
import com.otzg.pay.entity.PayOrderLog;
import com.otzg.pay.enums.PayStatus;
import com.otzg.pay.util.*;
import com.otzg.util.*;
import org.redisson.api.RReadWriteLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author G./2018/7/3 10:38
 */
@Service
public class PayOrderServImpl extends AbstractServ implements PayOrderServ {

    //支付单dao
    @Autowired
    PayOrderDao payOrderDao;
    //支付单记录dao
    @Autowired
    PayOrderLogDao payOrderLogDao;
    //支付渠道服务
    @Autowired
    PayChannelAccountServ payChannelAccountServ;

    //消息队列工具
    @Autowired
    SyncQue syncPayQue;



    @Override
    @Transactional
    public Map createPayOrderByUnit(String payChannelAccount, PayOrderDto payOrderDto) {
        RReadWriteLock lock = redisson.getReadWriteLock(LockUtil.ORDERPRESTR + payOrderDto.getSubOrderNo());
        try {
            lock.writeLock().lock();
            P("加锁成功");

            //判断业务单号是否已经生成
            PayOrder old = findBySubOrderNo(payOrderDto.getSubOrderNo());
            if (null != old && old.getStatus()>-1) {
                //返回支付结果{-1:支付失败,0:未支付,1:支付成功}
                return ResultUtil.payResult(old.getStatus(), old.getPayBody());
            }

            //收款方法
            PayReceiveUtil payReceiveUtil = new PayReceiveUtil(payOrderDto);
            if (null == payReceiveUtil) {
                throw new Exception();
            }

            String payOrderNo = getPayOrderNo(payOrderDto.getShopId());

            //根据创建支付单
            PayOrder payOrder = new PayOrder(payOrderNo, payChannelAccount, payOrderDto);
            payOrder.setId(getId());
            //状态未支付
            payOrder.setStatus(0);
            payOrderDao.save(payOrder);

            //如果是微信或支付宝扫码付
            if (WxPayOrderDtoCheckPayOrderCheckUtil.TradeType.MICROPAY.name().equals(payOrderDto.getPayType())
                    || AliPayOrderDtoCheckPayOrderCheckUtil.TradeType.BARCODE.name().equals(payOrderDto.getPayType())) {

                new Thread(() -> payReceiveUtil.pay(payChannelAccount, payOrderNo, payOrderDto)).start();
                return ResultUtil.paySuccess();
            } else {
                //去支付
                Map pr = payReceiveUtil.pay(payChannelAccount, payOrderNo, payOrderDto);
                //如果有返回信息则保存
                if (null != pr) {
                    payOrder.setPayBody(pr.get("body").toString());
                }
                return pr;
            }
        } catch (Exception e) {
            rollBack();
            P("error");
            return ResultUtil.payFailed();
        } finally {
            lock.writeLock().unlock();
            P("redisson lock unlock");
        }
    }

    @Override
    public PayOrder findByUnitAndType(String unitId, String payType) {
        return null;
    }

    @Override
    public PayOrder findWaitByUnitAndType(String unitId, String payType) {
        return null;
    }

    @Override
    public PayOrder findByUnitAndSubOrderNo(String unitId, String subOrderNo) {
        return payOrderDao.findByUnitIdAndSubOrderNo(unitId, subOrderNo).orElse(null);
    }

    @Override
    public PayOrder findByPayOrderNo(String unitId, String payOrderNo) {
        return payOrderDao.findByPayOrderNo(payOrderNo).orElse(null);
    }

    //微信查询支付结果并更新数据
    @Override
    @Transactional
    public Map queryByPayChannel(PayOrder payOrder) {
        //创建收款工具
        PayQueryUtil payQueryUtil = new PayQueryUtil(payOrder);

        Map payResult = payQueryUtil.query(payOrder.getPayChannelAccount(), payOrder.getPayOrderNo());
        P("支付渠道查账结果=>"+payResult);

        if (payResult.get("result").equals(PayStatus.FAILED.status)) {
            payOrderCancel(payOrder.getSubOrderNo());
        } else if (payResult.get("result").equals(PayStatus.SUCCESS.status)) {
            saveSuccess(payOrder, payOrder.getPayChannel(), payOrder.getMemberId(), payOrder.getUnitId());
        }

        return payResult;
    }

    @Override
    public PayOrder findBySubOrderNo(String subOrderNo) {
        return payOrderDao.findBySubOrderNo(subOrderNo)
                .stream()
                .sorted(Comparator.comparing(PayOrder::getUpdateTime).reversed())
                .findFirst()
                .orElse(null);
    }

    PayOrder findWaitBySubOrderNo(String subOrderNo) {
        return payOrderDao.findBySubOrderNo(subOrderNo)
                .stream().filter(payOrder -> payOrder.getStatus().equals(PayOrder.StatusType.WAIT.index))
                .sorted(Comparator.comparing(PayOrder::getUpdateTime))
                .findFirst()
                .orElse(null);
    }

    @Override
    @Transactional
    public boolean payOrderCancel(String subOrderNo) {
        payOrderDao.findBySubOrderNo(subOrderNo)
                .stream().filter(payOrder -> payOrder.getStatus().equals(PayOrder.StatusType.WAIT.index))
                .forEach(payOrder -> {
                    payOrder.setStatus(-1);
                    payOrder.setUpdateTime(DateUtil.now());
                    payOrderDao.save(payOrder);
                });
        return true;
    }

    @Override
    public PayOrder getSuccessBySubOrderNo(String subOrderNo) {
        return payOrderDao.findBySubOrderNo(subOrderNo)
                .stream().filter(payOrder -> payOrder.getStatus().equals(PayOrder.StatusType.SUCC.index))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Map findPayOrderByUnit(Finder finder, String unitId, String payChannel) {
        Page<PayOrder> page = findByUnit(finder, unitId, payChannel);
        return ResultUtil.getPageJson(page.getTotalPages(),page.getTotalCount(),page.getItems()
                .stream()
                .map(PayOrder::getBaseJson)
                .toArray());
    }

    Page<PayOrder> findByUnit(Finder finder, String unitId, String payChannel) {
        StringJoiner hql = new StringJoiner(" ");
        hql.add("select po from PayOrder po where po.unitId='" + unitId + "'");
        if (!CheckUtil.isEmpty(payChannel))
            hql.add(" and po.payChannel='" + payChannel + "'");
        if (!CheckUtil.isEmpty(finder.getStatus()))
            hql.add(" and po.status=" + finder.getStatus());
        if (!CheckUtil.isEmpty(finder.getStartTime()))
            hql.add(" and po.createTime >= '" + finder.getStartTime() + "'");
        if (!CheckUtil.isEmpty(finder.getEndTime()))
            hql.add(" and po.updateTime <= '" + finder.getEndTime() + "'");

        hql.add("order by  po.updateTime desc");
        return baseDao.findPageByHql(hql.toString(), finder.getPageSize(), finder.getStartIndex());
    }

    @Override
    @Transactional
    public boolean handleNotify(String payOrderNo, String payChannelNo, String resultCode, String payerId, String payeeId) {
        Optional<PayOrder> op = payOrderDao.findByPayOrderNo(payOrderNo);
        //如果没有订单信息返回错误
        if (!op.isPresent()) {
            return false;
        }

        PayOrder payOrder = op.get();

        //如果已经完成返回成功
        if (payOrder.getStatus() == 1) {
            return true;
        }

        //执行收款操作
        saveSuccess(payOrder, payChannelNo, payerId, payeeId);

        //返回支付成功
        return true;
    }


    //收款成功操作
    void saveSuccess(PayOrder payOrder, String payChannelNo, String payerId, String payeeId) {
        //保存交易日志
        savePayOrderLog(payOrder, payOrder.getPayOrderNo(), payChannelNo, payerId, payeeId);

        //更新支付渠道账户及账户记录
        payChannelAccountServ.add(payOrder.getUnitId(), payOrder.getPayOrderNo(), payOrder.getSubject(), payOrder.getPayChannel(), payChannelNo, payOrder.getAmount());

        //通知子系统收款已成功
        sendSubNotify(payOrder.getSubOrderNo(), payOrder.getPayNotify());
    }


    //更新支付单及支付单记录
    void savePayOrderLog(PayOrder payOrder, String payOrderNo, String payChannelNo, String payerId, String payeeId) {
        //保存支付
        payOrder.setStatus(1);
        payOrder.setUpdateTime(DateUtil.now());
        payOrder.setMemberId(payerId);
        payOrderDao.save(payOrder);
        PT("保存收款单成功 payOrderNo=" + payOrderNo);

        PayOrderLog payOrderLog = new PayOrderLog();
        payOrderLog.setId(getId());
        payOrderLog.setPayChannelNo(payChannelNo);
        payOrderLog.setUnitId(payOrder.getUnitId());
        payOrderLog.setAmount(payOrder.getAmount());
        payOrderLog.setCreateTime(DateUtil.now());
        payOrderLog.setPayOrderNo(payOrder.getPayOrderNo());
        //子系统业务单号
        payOrderLog.setSubOrderNo(payOrder.getSubOrderNo());
        payOrderLog.setPayChannel(payOrder.getPayChannel());

        //支付渠道付款人账号
        payOrderLog.setPayerChannelAccount(payerId);
        //支付渠道收款人账号
        payOrderLog.setPayeeChannelAccount(payeeId);
        payOrderLog.setStatus(1);
        payOrderLogDao.save(payOrderLog);

        PT("保存收款单记录成功");
    }

    /**
     * 通知子系统收款成功
     *
     * @param outTraderNo
     * @param notifyUrl
     */
    void sendSubNotify(String outTraderNo, String notifyUrl) {
        JSONObject jo = new JSONObject();
        jo.put("success", true);
        jo.put("outTraderNo", outTraderNo);
        //走消息队列通知子系统
        new Thread(() -> syncPayQue.send(jo.toString())).start();
    }

    @Override
    @Transactional
    public boolean subReceiveNotify(String subOrderNo) {
        PayOrder payOrder = getSuccessBySubOrderNo(subOrderNo);
        if (null == payOrder) {            //没有支付
            return false;
        }

        payOrder.setPayNotifyStatus(1);
        payOrder.setPayNotifyTimes(1);
        payOrderDao.save(payOrder);
        return true;
    }
}
