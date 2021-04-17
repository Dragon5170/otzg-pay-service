package com.otzg.pay.util;

import java.util.Map;

/**
 * @Author G.
 * @Date 2019/12/10 0010 下午 4:35
 */
public interface PayQuery {

    /**
     * 查询接口
     * @param payChannelAccount
     * @param payOrderNo
     * @return
     *   成功返回 {success:true,code:0000,data:{payChannelNo:支付渠道单号,payerId:付款人id,payeeId:收款人id}}
     *   失败返回 {success:false,code:0000,msg:失败原因}
     */
    Map query(String payChannelAccount, String payOrderNo);
}
