package com.otzg.pay.service;

import com.otzg.base.Finder;
import com.otzg.pay.dto.PayRefundOrderDto;
import com.otzg.pay.entity.PayOrder;
import com.otzg.pay.entity.RefundOrder;

import java.util.Map;

/**
 * @Author G.
 * @Date 2019/11/27 0027 上午 11:13
 */
public interface RefundOrderServ {

    RefundOrder findByOrderNo(String refundOrderNo);

    /**
     * 根据查询该单号的退款单是否已经创建
     * @param refundOrderNo
     * @return
     */
    boolean checkByRefundOrderNo(String refundOrderNo);

    /**
     * 执行退款业务
     * 1.根据收款单支付渠道，收款单号，收款金额
     * 2.冻结收款渠道账户金额
     * 3.执行退款业务
     * @param payAccountId  基本账户id,可以获取支付渠道账户
     * @param payOrder
     * @param payRefundOrderDto
     * @return
     */
    int refundByUnit(Long payAccountId,PayOrder payOrder, PayRefundOrderDto payRefundOrderDto);

    /**
     * 查询退款结果
     * 1.如果执行成功则更新支付单
     * @param refundOrder
     * @return
     */
    int queryRefundOrderByUnit(RefundOrder refundOrder);

    Map findRefundOrderByUnit(Finder finder, String unitId, String payChannel);
}
