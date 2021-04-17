package com.otzg.pay.util;

import com.otzg.pay.dto.PayOrderDto;
import com.otzg.util.RespTips;

import java.util.Map;

/**
 * @Author G.
 * 支付宝支付渠道支付校验器
 * @Date 2019/12/10 0010 上午 9:29
 */
public class AliPayOrderDtoCheckPayOrderCheckUtil extends PayOrderCheckUtil {
    public AliPayOrderDtoCheckPayOrderCheckUtil(PayOrderDto t) {
        super(t);
    }

    @Override
    public Map getMsg() {
        return super.getMsg();
    }

    @Override
    protected void check() {
        if (!AliPayOrderDtoCheckPayOrderCheckUtil.TradeType.has(t.getPayType())) {
            msg = "支付宝支付类型(payType)参数错误";
            return;
        }

        //如果使用花呗分期,必须是3,6,9
        if (!isEmpty(t.getHbFqNum())
                && !(t.getHbFqNum().equals("3")
                || t.getHbFqNum().equals("6")
                || t.getHbFqNum().equals("12"))
        ) {
            msg = "支付宝花呗分期(hbFqNum=3,6,12)参数错误";
            return;
        }


        if (t.getPayType().equals(TradeType.BARCODE.name())             //支付宝扫码付
                || t.getPayType().equals(TradeType.FACE.name())) {      //支付宝刷脸付
            barCodePayCheck();
        } else if (t.getPayType().equals(TradeType.CREATE.name())) {      //支付宝线上与支付
            createPayCheck();
        }

        //最后必须返回成功
        hasErrors = true;
        code = RespTips.SUCCESS_CODE.code;

    }

    void barCodePayCheck() {
        if (isCorrect(t.getAuthCode(), 32)) {
            msg = "用户收款id(authCode)参数错误";
            return;
        }
    }

    void createPayCheck() {
        if (isCorrect(t.getBuyerId(), 32)) {
            msg = "付款人buyerId(buyerId)参数错误";
            return;
        }
    }

    public enum TradeType {
        APP, BARCODE, FACE, CREATE, PRECREATE;

        public static boolean has(String name) {
            try {
                valueOf(name);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

}
