package com.otzg.pay.util;

import com.otzg.pay.dto.PayRefundOrderDto;

import java.util.Map;

/**
 * @Author G.
 * @Date 2019/12/11 0011 上午 10:15
 */
public interface PayRefund {
    /**
     * 退款业务
     * @param payChannelAccount 退款账户，
     * @param payOrderNo        收款时候的业务单号(平台)
     * @param refundOrderNo     退款业务单号(平台)
     * @return
     *       payChannelNo       成功必须返回支付渠道单号
     */
    Map refund(String payChannelAccount, String payOrderNo, String refundOrderNo, PayRefundOrderDto payRefundOrderDto);

    /**
     * 退款结果查询
     * @param payChannelAccount 退款账户，
     * @param payOrderNo        收款时候的业务单号(平台)
     * @param refundOrderNo     退款业务单号(平台)
     * @return
     */
    Map query(String payChannelAccount, String payOrderNo, String refundOrderNo);
}
