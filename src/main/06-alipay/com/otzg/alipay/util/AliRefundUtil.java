package com.otzg.alipay.util;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeFastpayRefundQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.otzg.util.ResultUtil;
import com.otzg.log.util.LogUtil;
import com.otzg.pay.dto.PayRefundOrderDto;
import com.otzg.pay.util.PayRefund;
import com.otzg.util.FuncUtil;

import java.util.Map;

/**
 * @Author G.
 * @Date 2019/12/11 0011 上午 10:35
 */
public class AliRefundUtil implements PayRefund {
    public AliRefundUtil() {
    }

    /**
     * 退款
     *
     * 注意此接口为可重入接口
     * 订单支付时传入的商户订单号,不能和 trade_no同时为空。
     * @return {"alipay_trade_refund_response":{"code":"10000","msg":"Success","
     * buyer_logon_id":"vvn***@sandbox.com","buyer_user_id":"2088102169133529",
     * "fund_change":"N","gmt_refund_pay":"2019-01-09 15:01:46",
     * "out_trade_no":"191018154556136588110",
     * "refund_detail_item_list":[{"amount":"1.00","fund_channel":"ALIPAYACCOUNT"}],
     * "refund_fee":"1.00","send_back_fee":"1.00","trade_no":"2019010922001433520200663215"},
     * "sign":"fsRfZ8cHsiK+NizXnvE1RweP1rDiQQe30EMj7B5mqk0V/VayuE5xXj4kVbRQsiNr4iMROnbbjKjL3iPraH9gDqCzWKn2HD4dkiXhe24MZA+9uBNpBXf0RRg1eNcT89eSGnRAfxcvuovHIvkTca3VpZ3IfzXu8/WtLe7tKWjBG9HlWEILMo2x2PhiP2jDKmv5HYu4humw6xMiMzn30JYi1kx8A5EnpY5uW9Ys1pfZpmLsOqsfgzppfpNMr8zRJaDtvbGH/bz4Z5nTVe9+BiBK+3qJbPPPWeQfPVmKXm0DJnUkFcU1ViL283yd2WUUJPHdQ77GEHqf9U/SVKACGIFRiw=="}
     */

    @Override
    public Map refund(String payChannelAccount, String payOrderNo, String refundOrderNo, PayRefundOrderDto payRefundOrderDto) {
        try{
            AlipayTradeRefundModel model = new AlipayTradeRefundModel();
            //按照收款业务单退款
            model.setOutTradeNo(payOrderNo);
            //标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传。
            model.setOutRequestNo(refundOrderNo);
            model.setRefundAmount(FuncUtil.getDoubleScale(payRefundOrderDto.getAmount()).toString());

            AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
            //第三方应用授权
            request.putOtherTextParam("app_auth_token", payChannelAccount);
            request.setBizModel(model);

            AlipayTradeRefundResponse response = getAlipayClient().execute(request);
            LogUtil.saveTradeLog("支付渠道退款结果=>"+response.getBody());
            //{"alipay_trade_refund_response":{"code":"10000","msg":"Success",
            // "buyer_logon_id":"375***@qq.com","buyer_user_id":"2088002123336273",
            // "fund_change":"N","gmt_refund_pay":"2019-12-12 09:58:22","out_trade_no":"20191212954213580745463060",
            // "refund_detail_item_list":[{"amount":"0.01","fund_channel":"PCREDIT"}],
            // "refund_fee":"0.01","send_back_fee":"0.01","trade_no":"2019121222001436275746629865"},
            // "sign":"lwcmzUAhLT3AzDsz0iFUSzQBaGYI4UcIBgCbU/f8UC7CPgRwMm9bhsAZ5bYWTb9dXvoiKlJmOMav/EBbdZ85aOb0imtTq1tfLi4m6qIJIE0+7YxV5GaiGmXpAhiaUgXmQOauV1sLwWPaOXgtqlpAxff+XtZnoffy5Mjrt1fgGRanpfw2lhucCkSaFaHzKnNbehd7e5fWZ0lZmNi0GK8HDDIskmQIrY5ELtwIJP0YjiWhimgqJZXzvKCqDrU7Gb9hOPZDStsd6BNEB3czabjtkHt0+18TX40Gre9aD7+lS54PMF6mQEnijKoohZ7ZYC55IYnwX+yRNmQOWrl2ZEx/dw=="}
            if (response.isSuccess()) {
                //如果成功应返回一个结构体payChannelNo
                return ResultUtil.paySuccess(response.getTradeNo());
            } else {
                return ResultUtil.payFailed();
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.payWaiting();
        }
    }

    AlipayClient getAlipayClient() {
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.alipay_gateway,
                AlipayConfig.app_id,
                AlipayConfig.app_private_key,
                "json",
                AlipayConfig.charset,
                AlipayConfig.alipay_public_key,
                AlipayConfig.sign_type);
        return alipayClient;
    }

    /**
     * 退款查询
     * @return {"alipay_trade_fastpay_refund_query_response":
     * {"code":"10000","msg":"Success","out_request_no":"191018154556136588113",
     * "out_trade_no":"191018154556136588113","refund_amount":"1.00","total_amount":"1.00",
     * "trade_no":"2019010922001433520200660680"},
     * "sign":"j8OcS61wlzGJ0Zb7wUMplSTmtRDqet/vuJUUDPxCefyMZjnBWbI3tNiN4QeVwhHT1ugP0H2IdNGOJueswboEqT20Cy5fqrZj1WnzxpT1YpCGbL+eAo69A5oZLI0W4hbZnepbR0MpxnOA70A7vD3SSouPn34LQqMF9VX/cbp14zgWNDil0oEKhaoYkKX3ggpyYInE0QpMxFh4hmN5zYcQH088p4yOny8L315AN066imFBrgTLgmyu/48l1Vn89RzOfXTc88XJ7tyvHxmooR21icar5zBhGT6+8PnBcMN7d734bB+d/qZE7pKKbL8+7weMBPxpdLtx1mCbBMqE3VSyFg=="}
     */
    @Override
    public Map query(String payChannelAccount, String payOrderNo, String refundOrderNo) {
        try{
            AlipayTradeFastpayRefundQueryModel model = new AlipayTradeFastpayRefundQueryModel();
            //订单支付时传入的商户订单号,和支付宝交易号不能同时为空。 trade_no,out_trade_no如果同时存在优先取trade_no
            model.setOutTradeNo(payOrderNo);
            //请求退款接口时，传入的退款请求号，如果在退款请求时未传入，则该值为创建交易时的外部交易号
            model.setOutRequestNo(refundOrderNo);

            AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
            //第三方应用授权
            request.putOtherTextParam("app_auth_token", payChannelAccount);
            request.setBizModel(model);

            AlipayTradeFastpayRefundQueryResponse response = getAlipayClient().execute(request);
            if (response.isSuccess()) {
                return ResultUtil.paySuccess(response.getTradeNo());
            } else {
                return ResultUtil.payFailed();
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResultUtil.payWaiting();
        }
    }
}
