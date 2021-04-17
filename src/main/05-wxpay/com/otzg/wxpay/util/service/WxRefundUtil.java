package com.otzg.wxpay.util.service;

import com.otzg.util.ResultUtil;
import com.otzg.log.util.LogUtil;
import com.otzg.pay.dto.PayRefundOrderDto;
import com.otzg.pay.util.PayRefund;
import com.otzg.wxpay.util.sdk.WXPayConstants;
import com.otzg.wxpay.util.sdk.WXPayRequest;
import com.otzg.wxpay.util.sdk.WXPayUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 适用于境内服务商退款接口
 *
 * @Author G.
 * @Date 2019/11/27 0027 上午 10:43
 */
public class WxRefundUtil implements PayRefund {

    /**
     * 请求退款的数据
     * <p>
     * <xml>
     * <appid>wx2421b1c4370ec43b</appid>
     * <mch_id>10000100</mch_id>
     * <nonce_str>6cefdb308e1e2e8aabd48cf79e546a02</nonce_str>
     * <out_refund_no>1415701182</out_refund_no>
     * <out_trade_no>1415757673</out_trade_no>
     * <refund_fee>1</refund_fee>
     * <total_fee>1</total_fee>
     * <transaction_id>4006252001201705123297353072</transaction_id>
     * <sign>FE56DD4AA85C0EECA82C35595A69E153</sign>
     * </xml>
     *
     * @author G/2016年10月19日 上午11:08:56
     */
    public final Map<String, String> refundData(String subMchId,
                                                String payOrderNo,
                                                String refundOrderNo,
                                                Double totalAmount,
                                                PayRefundOrderDto payRefundOrderDto) {
        Map<String, String> paramMap = new HashMap<>();
        //子商户号
        paramMap.put("sub_mch_id", subMchId);
        //随机数
        paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
        //transaction_id是微信系统为每一笔支付交易分配的订单号，通过这个订单号可以标识这笔交易，它由支付订单API支付成功时返回的数据里面获取到。
//        paramMap.put("transaction_id", wxRefundPayDto.getTransactionId());
        //根据API给的签名规则进行签名
        paramMap.put("out_trade_no", payOrderNo);
        //商户系统内部的退款单号
        paramMap.put("out_refund_no", refundOrderNo);
        //金额必须为整数  单位为转换为分
        //订单金额
        paramMap.put("total_fee", "" + Math.round(totalAmount * 100));
        //退款金额
        paramMap.put("refund_fee", "" + Math.round(payRefundOrderDto.getAmount() * 100));
        //仅针对老资金流商户使用
        //REFUND_SOURCE_UNSETTLED_FUNDS---未结算资金退款（默认使用未结算资金退款）
        //REFUND_SOURCE_RECHARGE_FUNDS---可用余额退款
//        paramMap.put("refund_account", "REFUND_SOURCE_RECHARGE_FUNDS");
        paramMap.put("refund_account", "REFUND_SOURCE_UNSETTLED_FUNDS");
        return paramMap;
    }

    /**
     * 退款结果查询
     * <xml>
     * <appid>wx2421b1c4370ec43b</appid>
     * <mch_id>10000100</mch_id>
     * <nonce_str>0b9f35f484df17a732e537c37708d1d0</nonce_str>
     * <out_refund_no>123456789</out_refund_no>
     * <out_trade_no>1415757673</out_trade_no>
     * <sub_mch_id>1900000109</sub_mch_id>
     * <sign>66FFB727015F450D167EF38CCC549521</sign>
     * </xml>
     *
     * @return
     */
    public final Map<String, String> refundQueryData(String subMchId, String outRefundNo) {
        Map<String, String> paramMap = new HashMap<>();
        //子商户号
        paramMap.put("sub_mch_id", subMchId);
        //随机数
        //transaction_id是微信系统为每一笔支付交易分配的订单号，通过这个订单号可以标识这笔交易，它由支付订单API支付成功时返回的数据里面获取到。
        //根据API给的签名规则进行签名
        paramMap.put("out_refund_no", outRefundNo);
        return paramMap;
    }

    public WxRefundUtil() {
    }

    /**
     * 提交退款
     *
     * @return {transaction_id=4200000232201901097507856496, nonce_str=3PFz75JiEy4q7Ajw, out_refund_no=1901081654330013, sign=CDA7C77CD91BF60252234C2E54D0C097C3996EDA8080E09B2CE1BE8F52DEE448, return_msg=OK, mch_id=1519839251, refund_id=50000009452019010907917586560, cash_fee=1, out_trade_no=1901081654330013, coupon_refund_fee=0, refund_channel=, appid=wx4dc8cb7f0eb12288, refund_fee=1, total_fee=1, result_code=SUCCESS, coupon_refund_count=0, cash_refund_fee=1, return_code=SUCCESS}
     * @author G/2016年10月27日 上午9:04:42
     */

    /**
     * 成功必须返回支付渠道单号
     * 微信 refund_id => payChannelNo
     *
     * @param payChannelAccount
     * @param payOrderNo
     * @return
     */
    @Override
    public Map refund(String payChannelAccount, String payOrderNo, String refundOrderNo, PayRefundOrderDto payRefundOrderDto) {
        try {
            WXPayConfig wxPayConfig = new WXPayConfig(SignType.HMACSHA256.name(), true);
            wxPayConfig.setUrl(WXPayConstants.getPayRefundUrl());
            WXPayRequest wxPayRequest = new WXPayRequest(wxPayConfig);

            Map<String, String> data = refundData(payChannelAccount, payOrderNo, refundOrderNo, payRefundOrderDto.getAmount(), payRefundOrderDto);
            Map map = wxPayRequest.requestCert(data);
            LogUtil.saveTradeLog("调用结果=" + map);
            //2019-11-27 16:52
            //{nonce_str=sEOZMKGvN0zRa0Dl, appid=wxa574b9142c67f42e,sign=3B0F81C9A5819007C641DF9C628B8A75105A5391B785A1147F959585E24FEFCD,
            // err_code=ORDERNOTEXIST, return_msg=OK, result_code=FAIL,
            // err_code_des=订单不存在, mch_id=1513549201,
            // sub_mch_id=1525006091, return_code=SUCCESS}

            //{nonce_str=tosypgdXsYEfNLHB, appid=wxa574b9142c67f42e, sign=60B702BB7D44604E3ED5D21EDD69796F20AC401C28544722AC3131ECF3CDBC6A,
            // err_code=ERROR, return_msg=OK, result_code=FAIL,
            // err_code_des=订单已全额退款, mch_id=1513549201,
            // sub_mch_id=1525006091, return_code=SUCCESS}

            //{nonce_str=rMHj46BQOh1o2fOG, appid=wxa574b9142c67f42e, sign=471B5A921975D8484E6356865A9802B63EBFAD2F55EAD6C656671C9C9017B792,
            // err_code=NOTENOUGH, return_msg=OK, result_code=FAIL,
            // err_code_des=基本账户余额不足，请充值后重新发起,
            // mch_id=1513549201, sub_mch_id=1525006091, return_code=SUCCESS}

            //{nonce_str=vOLA5GKwnTM7kIsh, appid=wxa574b9142c67f42e, sign=81F2CA6EA2B72E69ACA9F9FED5D16352F83DAE0B1B5BB212FD8191C5E641489E,
            // err_code=NOTENOUGH, return_msg=OK, result_code=FAIL,
            // err_code_des=基本账户余额不足，请充值后重新发起,
            // mch_id=1513549201, sub_mch_id=1525006091, return_code=SUCCESS}

            //{transaction_id=4200000434201911276476814411, nonce_str=QzsYpbhkDxagqn1G, out_refund_no=2019112717363163181867378635,
            // sign=98ED8407FC1B9FA8E479705A7C51A21066A7714836F09F5DBD4DA28E7CD5E983, return_msg=OK, mch_id=1513549201, sub_mch_id=1525006091,
            // refund_id=50300302252019112713407326039, cash_fee=1, out_trade_no=2019112717261637271867378635, coupon_refund_fee=0,
            // refund_channel=, appid=wxa574b9142c67f42e, refund_fee=1, total_fee=1, result_code=SUCCESS, coupon_refund_count=0,
            // cash_refund_fee=1, return_code=SUCCESS}

            //{transaction_id=4200000451201911296106271730, nonce_str=ctDU0gSdTnx7ic68BUAbcf9pnIFGEVLz,
            // bank_type=CFT, openid=olFJwwNOyw5v5OpMq-2Ex959r5is,
            // sign=36C159DBDED986CD2757DAB6F8344594, fee_type=CNY, mch_id=1513549201,
            // sub_mch_id=1525006091, cash_fee=1, out_trade_no=201911291535815111867378635,
            // appid=wxa574b9142c67f42e, total_fee=1, trade_type=NATIVE, result_code=SUCCESS,
            // time_end=20191129153905, is_subscribe=Y, return_code=SUCCESS}
            if (map.get("return_code") != null
                    && map.get("return_code").equals("SUCCESS")
                    && map.get("result_code") != null
                    && map.get("result_code").equals("SUCCESS")
            ) {
                LogUtil.saveTradeLog("调用成功=" + map.toString());
                //如果成功应返回一个结构体payChannelNo
                return ResultUtil.paySuccess(map.get("refund_id").toString());
            } else {
                LogUtil.saveTradeLog("调用失败");
                return ResultUtil.payFailed();
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.saveTradeLog("微信支付错误=>" + e.toString());
            return ResultUtil.payWaiting();
        }
    }


    /**
     * 提交退款结果查询
     *
     * @return {transaction_id=4200000232201901097507856496, nonce_str=PM8FDErs4JbioIFY,
     * out_refund_no_0=1901081654330013, refund_status_0=SUCCESS, sign=C2557FD486231E91B17EB63E749247662239B39D010DD12B900BED7846C774AF,
     * refund_fee_0=1, refund_recv_accout_0=支付用户的零钱, return_msg=OK, mch_id=1519839251,
     * refund_success_time_0=2019-01-09 12:02:30, cash_fee=1, refund_id_0=50000009452019010907917586560,
     * out_trade_no=1901081654330013, appid=wx4dc8cb7f0eb12288, refund_fee=1, total_fee=1, result_code=SUCCESS,
     * refund_account_0=REFUND_SOURCE_UNSETTLED_FUNDS, refund_count=1, return_code=SUCCESS, refund_channel_0=ORIGINAL}
     * @author G/2016年10月27日 上午9:04:42
     */

    @Override
    public Map query(String payChannelAccount, String payOrderNo, String refundOrderNo) {
        try {
            WXPayConfig wxPayConfig = new WXPayConfig();
            wxPayConfig.setUrl(WXPayConstants.getPayRefundQueryUrl());
            WXPayRequest wxPayRequest = new WXPayRequest(wxPayConfig);

            Map<String, String> map = refundQueryData(payChannelAccount, refundOrderNo);
            Map<String, Object> result = wxPayRequest.request(map);
            LogUtil.saveTradeLog("微信退款查询调用结果=" + result.toString());
            //2019-11-28 13:59
            //{transaction_id=4200000425201911283941785875, nonce_str=K5m6EFWnSUj2yawB, out_refund_no_0=20191128113553134861867378635, refund_status_0=SUCCESS, sign=D70AE04C4A386E7EBC3975ECFC8692E4, refund_fee_0=1, refund_recv_accout_0=支付用户的零钱, return_msg=OK, mch_id=1513549201, refund_success_time_0=2019-11-28 11:38:22, sub_mch_id=1525006091, cash_fee=1, refund_id_0=50300302512019112813429427470, out_trade_no=201911281133112801867378635, appid=wxa574b9142c67f42e, refund_fee=1, total_fee=1, result_code=SUCCESS, refund_account_0=REFUND_SOURCE_UNSETTLED_FUNDS, refund_count=1, return_code=SUCCESS, refund_channel_0=ORIGINAL}

            //{nonce_str=DZQ4Lt8zucJgJE5u, appid=wxa574b9142c67f42e, sign=30237FD39734B35A36F4E92E38737D2C,
            // err_code=REFUNDNOTEXIST, err_code_des=not exist, result_code=FAIL, return_msg=OK, mch_id=1513549201,
            // sub_mch_id=1525006091, return_code=SUCCESS}
            if (result.get("return_code") != null
                    && result.get("return_code").equals("SUCCESS")
                    && result.get("result_code") != null
                    && result.get("result_code").equals("SUCCESS")
            ) {
                LogUtil.saveTradeLog("微信退款成功=" + result.toString());
                return ResultUtil.paySuccess("refund_id_0");
            } else {
                LogUtil.saveTradeLog("微信退款未成功=" + result.toString());
                return ResultUtil.payWaiting();
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.saveTradeLog("调用微信退款查询接口失败");
            return ResultUtil.payFailed();
        }
    }
}
