package com.otzg.pay.util;

import com.otzg.pay.dto.PayOrderDto;
import com.otzg.util.DtoCheckUtil;

/**
 * 基本收款单校验工具
 * 说明：
 * 1.此类check方法只校验收单的基本必传参数
 * 2.各支付渠道的收款需要有自己的校验工具，并且继承于此类。
 * 3.各自类根据支付渠道要求，实现自己的check方法
 *
 * 使用方法：
 *
 *      PayOrderDtoCheck payOrderDtoCheck = new PayOrderDtoWxpayCheck(payOrderDto)  通过构造函数传参
 *      payOrderDtoCheck.getPayOrderDto() == null                                   说明校验失败，通过返回错误原因。
 *      payOrderDtoCheck.getMsg()                                                   获取失败原因
 * @Author G.
 * @Date 2019/11/23 0023 下午 1:43
 */
public abstract class PayOrderCheckUtil extends DtoCheckUtil<PayOrderDto> {
    //构造函数
    public PayOrderCheckUtil(PayOrderDto payOrderDto) {
        super(payOrderDto);
        baseCheck();
        check();
    }

    /**
     * 校验收款业务单参数
     *
     */
    protected void baseCheck() {
        //子系统的业务单号必填
        if (isCorrect(t.getSubOrderNo(), 32)) {
            msg = "业务单号(subOrderNo)参数必填";
            return;
        }
        //支付渠道不能为空,且支付系统支持
        if (isCorrect(t.getPayChannel(), 16)) {
            msg = "支付渠道(payChannel)参数必填";
            return;
        }
        //支付项目名称必填
        if (isCorrect(t.getSubject(), 64)) {
            msg = "支付项目(subject)参数错误";
            return;
        }
        //支付金额必填
        if (checkAmount(t.getAmount())) {
            msg = "支付金额(amount)参数错误";
            return;
        }
        //支付业务类型必填
        if (isCorrect(t.getPayType(), 16)) {
            msg = "支付业务类型(payType)参数错误";
            return;
        }
        //收款商户id必填
        if (isCorrect(t.getShopId(), 32)) {
            msg = "收款商户id(shopId)参数错误";
            return;
        }
    }

    protected abstract void check();
}
