package com.otzg.pay.util;

import com.otzg.pay.dto.PayOrderDto;

import java.util.Map;

/**
 * @Author G.
 * @Date 2019/12/10 0010 下午 2:37
 */
public interface PayReceive {
    /**
     * 支付收款接口
     *
     * @param payChannelAccount 支付渠道账号
     * @param payOrderNo        支付业务单号
     * @param payOrderDto       支付业务数据
     * @return 成功返回 {success:true,code:0000,data:{支付渠道支付返回的信息}}
     * 失败返回 {success:false,code:错误码,msg:失败原因}
     */
    Map pay(String payChannelAccount, String payOrderNo, PayOrderDto payOrderDto);
}
