package com.otzg.pay.util;

import com.otzg.pay.dto.PayOrderDto;
import com.otzg.util.RespTips;

import java.util.Map;

/**
 * 微信收款单校验工具
 *
 * @Author G.
 * @Date 2019/11/23 0023 下午 3:28
 */
public class WxPayOrderDtoCheckPayOrderCheckUtil extends PayOrderCheckUtil {

    public WxPayOrderDtoCheckPayOrderCheckUtil(PayOrderDto t) {
        super(t);
    }

    @Override
    public Map getMsg() {
        return super.getMsg();
    }

    @Override
    public void check() {
        if (!TradeType.has(t.getPayType())) {
            msg = "微信支付类型(payType)参数错误";
            return;
        }

        if (t.getPayType().equals(TradeType.JSAPI.name())) {  //微信及小程序
            if (isCorrect(t.getBuyerId(), 32)) {
                msg = "付款人openid(buyerId)参数错误";
                return;
            }
            if (isCorrect(t.getIpAddress(), 64)) {
                msg = "终端ip地址(ipAddress)参数错误";
                return;
            }
//            if (checkParam(t.getPayNotify(), 255)) {
//                msg = "回调地址(payNotify)参数错误";
//                return;
//            }
        } else if (t.getPayType().equals(TradeType.APP.name())) {   //app
            if (isCorrect(t.getAppId(), 32)) {
                msg = "商户应用id(appId)参数错误";
                return;
            }
//            if (checkParam(t.getPayNotify(), 255)) {
//                msg = "回调地址(payNotify)参数错误";
//                return;
//            }
        } else if (t.getPayType().equals(TradeType.MWEB.name())) {   //h5收款校验
            if (isCorrect(t.getIpAddress(), 64)) {
                msg = "终端ip地址(ipAddress)参数错误";
                return;
            }
//            if (checkParam(t.getPayNotify(), 255)) {
//                msg = "回调地址(payNotify)参数错误";
//                return;
//            }
        } else if (t.getPayType().equals(TradeType.MICROPAY.name())) {   //刷脸及扫码收款校验
            if (isCorrect(t.getAuthCode(), 128)) {
                msg = "用户条码(authCode)参数错误";
                return;
            }
            if (isCorrect(t.getIpAddress(), 64)) {
                msg = "终端ip地址(ipAddress)参数错误";
                return;
            }
        } else if (t.getPayType().equals(TradeType.NATIVE.name())) {     //主扫
            if (isCorrect(t.getProductId(), 32)) {
                msg = "商品id(productId)参数错误";
                return;
            }
//            if (checkParam(t.getPayNotify(), 255)) {
//                msg = "回调地址(payNotify)参数错误";
//                return;
//            }
        }
        hasErrors = true;
        code = RespTips.SUCCESS_CODE.code;

    }

    public enum TradeType {
        JSAPI,APP,MWEB,MICROPAY,NATIVE;

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
