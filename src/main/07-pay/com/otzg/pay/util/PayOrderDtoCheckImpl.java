package com.otzg.pay.util;

import com.otzg.pay.dto.PayOrderDto;

import java.util.Map;

/**
 * 收款单校验器
 *
 * @Author G.
 * @Date 2019/12/10 0010 下午 1:55
 */
public class PayOrderDtoCheckImpl implements PayOrderDtoCheck {
    PayOrderCheckUtil payOrderDtoPayOrderCheckUtil;

    public PayOrderDtoCheckImpl(PayOrderDto payOrderDto) {
        if(payOrderDto.getPayChannel().equals("alipay")){
            payOrderDtoPayOrderCheckUtil = new AliPayOrderDtoCheckPayOrderCheckUtil(payOrderDto);
        }else
        if(payOrderDto.getPayChannel().equals("wxpay")){
            payOrderDtoPayOrderCheckUtil = new WxPayOrderDtoCheckPayOrderCheckUtil(payOrderDto);
        }else{
            payOrderDtoPayOrderCheckUtil = null;
        }
    }

    @Override
    public boolean hasErrors() {
        return payOrderDtoPayOrderCheckUtil.hasErrors();
    }

    @Override
    public Map getMsg() {
        return payOrderDtoPayOrderCheckUtil.getMsg();
    }
}
