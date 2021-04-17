/**
 * 功能：
 * 1、微信财付通收款-》获取支付二维码 或者获取预付款id
 * 2、退款操作并获取返回值
 * <p>
 * //把参数转换成XML数据格式
 * String data = WxpaySubmit.ReceiveData("测试商品","16101815455603649","1");
 * //获取二维码链接
 * String codeUrl = getCodeUrl(data);
 * P("codeUrl="+codeUrl);
 * //获取预付款id
 * String prepayId = getPrepayId(data);
 * P("prepayId="+prepayId);
 * <p>
 * String data=WxpaySubmit.RefundData("","16101815455603649","124444446","1","1");
 * String result=WxpaySubmit.doRefund(data,path);
 * P("codeUrl="+result);
 */
package com.otzg.wxpay.util.service;


import com.otzg.util.ResultUtil;
import com.otzg.log.util.LogUtil;
import com.otzg.pay.dto.PayOrderDto;
import com.otzg.pay.util.*;
import com.otzg.util.FuncUtil;
import com.otzg.util.SubmitUtil;
import com.otzg.wxpay.util.sdk.WXPayConstants;
import com.otzg.wxpay.util.sdk.WXPayRequest;
import com.otzg.wxpay.util.sdk.WXPayUtil;
import net.sf.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 适用于境内服务商收款接口
 *
 * @author G/2019年11月16日
 */
public class WxpayUtil implements PayReceive, PayQuery {

    /**
     * 刷脸付数据
     * 扫码付款数据
     * <xml>
     * <appid>wx2421b1c4370ec43b</appid>
     * <attach>订单额外描述</attach>
     * <auth_code>120269300684844649</auth_code>
     * <body>付款码支付测试</body>
     * <device_info>1000</device_info>
     * <goods_tag></goods_tag>
     * <mch_id>10000100</mch_id>
     * <sub_mch_id>10000101</sub_mch_id>
     * <nonce_str>8aaee146b1dee7cec9100add9b96cbe2</nonce_str>
     * <out_trade_no>1415757673</out_trade_no>
     * <spbill_create_ip>14.17.22.52</spbill_create_ip>
     * <time_expire></time_expire>
     * <total_fee>1</total_fee>
     * <sign>C29DB7DB1FD4136B84AE35604756362C</sign>
     * </xml>
     *
     * @return
     */
    final Map<String, String> microPayData(String subMchId, String payOrderNo, PayOrderDto payOrderDto) {

        Map<String, String> paramMap = new HashMap<>();
        //交押金
        //paramMap.put("deposit", "Y");
        paramMap.put("sub_mch_id", subMchId);
        //附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
        if (null != payOrderDto.getAttach())
            paramMap.put("attach", payOrderDto.getAttach());
        paramMap.put("auth_code", payOrderDto.getAuthCode());
        //商品简单描述
        paramMap.put("body", payOrderDto.getSubject());
        //订单优惠标记，代金券或立减优惠功能的参数
        paramMap.put("goods_tag", "");
        //终端设备号(商户自定义，如门店编号)
        if (null != payOrderDto.getDeviceInfo())
            paramMap.put("device_info", payOrderDto.getDeviceInfo());
        //商户 后台的贸易单号
        paramMap.put("out_trade_no", payOrderNo);
        //金额必须为整数  单位为转换为分
        String tfee = "" + Math.round(payOrderDto.getAmount() * 100);
        paramMap.put("total_fee", tfee);
        if (payOrderDto.getIsProfitSharing().equals(1)) {
            paramMap.put("profit_sharing", "Y");
        }
        //支付成功后，回调地址
        return paramMap;
    }

    /**
     * 请求收款的数据Jsapi
     * <p>
     * 用户标识	openid	trade_type=JSAPI，此参数必传，用户在主商户appid下的唯一标识。
     * openid和sub_openid可以选传其中之一，如果选择传sub_openid,则必须传sub_appid。
     * 下单前需要调用【网页授权获取用户信息】接口获取到用户的Openid。
     * <p>
     * 用户子标识sub_openid	 trade_type=JSAPI，此参数必传，用户在子商户appid下的唯一标识。
     * openid和sub_openid可以选传其中之一，如果选择传sub_openid,则必须传sub_appid。
     * 下单前需要调用【网页授权获取用户信息】接口获取到用户的Openid。
     *
     * <xml>
     * <appid>wx2421b1c4370ec43b</appid>
     * <attach>支付测试</attach>
     * <body>JSAPI支付测试</body>
     * <mch_id>10000100</mch_id>
     * <detail><![CDATA[{ "goods_detail":[ { "goods_id":"iphone6s_16G", "wxpay_goods_id":"1001", "goods_name":"iPhone6s 16G", "quantity":1, "price":528800, "goods_category":"123456", "body":"苹果手机" }, { "goods_id":"iphone6s_32G", "wxpay_goods_id":"1002", "goods_name":"iPhone6s 32G", "quantity":1, "price":608800, "goods_category":"123789", "body":"苹果手机" } ] }]]></detail>
     * <nonce_str>1add1a30ac87aa2db72f57a2375d8fec</nonce_str>
     * <notify_url>http://wxpay.wxutil.com/pub_v2/pay/notify.v2.php</notify_url>
     * <openid>oUpF8uMuAJO_M2pxb1Q9zNjWeS6o</openid>
     * <out_trade_no>1415659990</out_trade_no>
     * <spbill_create_ip>14.23.150.211</spbill_create_ip>
     * <total_fee>1</total_fee>
     * <trade_type>JSAPI</trade_type>
     * <sign>0CB01533B8C1EF103065174F50BCA001</sign>
     * </xml>
     */
    final Map<String, String> receiveJsapiData(String subMchId, String payOrderNo, PayOrderDto payOrderDto) {
        Map<String, String> paramMap = new HashMap<>();
        //openid
        paramMap.put("openid", payOrderDto.getBuyerId());
        paramMap.put("sub_mch_id", subMchId);
//        paramMap.put("sub_appid", payOrderDto.getSubAppId());
//        paramMap.put("sub_openid", payOrderDto.getSubOpenid());
        //交易类型
        paramMap.put("trade_type", "JSAPI");
        //本机的Ip
        paramMap.put("spbill_create_ip", FuncUtil.getLocalIp());

        if (null != payOrderDto.getAttach())
            paramMap.put("attach", payOrderDto.getAttach());
        //描述
        paramMap.put("body", payOrderDto.getSubject());
        //商户 后台的贸易单号
        paramMap.put("out_trade_no", payOrderNo);
        //金额必须为整数  单位为转换为分
        paramMap.put("total_fee", "" + Math.round(payOrderDto.getAmount() * 100));
        //支付成功后，回调地址
        paramMap.put("notify_url", WXPayConfig.getNotifyUrl());
        return paramMap;
    }


    /**
     * 请求收款的数据Native
     *
     * @author G/2016年10月19日 上午11:08:56
     */
    final Map<String, String> receiveNativeData(String subMchId, String payOrderNo, PayOrderDto payOrderDto) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("sub_mch_id", subMchId);
        //随机数
        paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
        //交易类型
        paramMap.put("trade_type", "NATIVE");

        if (null != payOrderDto.getAttach())
            paramMap.put("attach", payOrderDto.getAttach());

        //本机的Ip
        paramMap.put("spbill_create_ip", FuncUtil.getLocalIp());
        //商户根据自己业务传递的参数 必填
        paramMap.put("product_id", payOrderDto.getProductId());
        //描述
        paramMap.put("body", payOrderDto.getSubject());
        //商户 后台的贸易单号
        paramMap.put("out_trade_no", payOrderNo);
        //金额必须为整数  单位为转换为分
        paramMap.put("total_fee", "" + Math.round(payOrderDto.getAmount() * 100));
        //支付成功后，回调地址
        paramMap.put("notify_url", WXPayConfig.getNotifyUrl());
        if (payOrderDto.getIsProfitSharing().equals(1)) {
            paramMap.put("profit_sharing", "Y");
        }
        return paramMap;
    }


    /**
     * app收款
     *
     * @return
     */
    final Map<String, String> receiveAppData(String subMchId, String payOrderNo, PayOrderDto payOrderDto) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("sub_mch_id", subMchId);
        paramMap.put("sub_appid", payOrderDto.getAppId());

        //随机数
        paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
        //交易类型
        paramMap.put("trade_type", "APP");
        //本机的Ip
        paramMap.put("spbill_create_ip", FuncUtil.getLocalIp());
        //商户根据自己业务传递的参数 必填
        paramMap.put("product_id", "10001");
        //描述
        paramMap.put("body", payOrderDto.getSubject());

        if (null != payOrderDto.getAttach())
            paramMap.put("attach", payOrderDto.getAttach());
        //商户 后台的贸易单号
        paramMap.put("out_trade_no", payOrderNo);

        //金额必须为整数  单位为转换为分
        paramMap.put("total_fee", "" + Math.round(payOrderDto.getAmount() * 100));
        //支付成功后，回调地址
        paramMap.put("notify_url", WXPayConfig.getNotifyUrl());
        return paramMap;
    }


    /**
     * h5收款
     *
     * @return
     */
    final Map<String, String> receiveH5Data(String subMchId, String payOrderNo, PayOrderDto payOrderDto) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("sub_mch_id", subMchId);
        //随机数
        paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
        //交易类型->H5支付的交易类型为MWEB
        paramMap.put("trade_type", "MWEB");
        //本机的Ip
        paramMap.put("spbill_create_ip", payOrderDto.getIpAddress());
        paramMap.put("scene_info", payOrderDto.getSubject());
        //描述
        paramMap.put("body", payOrderDto.getSubject());
        if (null != payOrderDto.getAttach())
            paramMap.put("attach", payOrderDto.getAttach());
        //商户 后台的贸易单号
        paramMap.put("out_trade_no", payOrderNo);

        //金额必须为整数  单位为转换为分
        paramMap.put("total_fee", "" + Math.round(payOrderDto.getAmount() * 100));
        //支付成功后，回调地址
        paramMap.put("notify_url", WXPayConfig.getNotifyUrl());
        return paramMap;
    }

    /**
     * 小程序
     *
     * @return 例子参数
     * <xml>
     * <appid>wx2421b1c4370ec43b</appid>
     * <sub_appid>wx2421b1c4370ec13b</sub_appid>
     * <sub_mch_id>10000101</appid>
     * <attach>支付测试</attach>
     * <body>JSAPI支付测试</body>
     * <mch_id>10000100</mch_id>
     * <detail><![CDATA[{ "goods_detail":[ { "goods_id":"iphone6s_16G", "wxpay_goods_id":"1001", "goods_name":"iPhone6s 16G", "quantity":1, "price":528800, "goods_category":"123456", "body":"苹果手机" }, { "goods_id":"iphone6s_32G", "wxpay_goods_id":"1002", "goods_name":"iPhone6s 32G", "quantity":1, "price":608800, "goods_category":"123789", "body":"苹果手机" } ] }]]></detail>
     * <nonce_str>1add1a30ac87aa2db72f57a2375d8fec</nonce_str>
     * <notify_url>http://wxpay.wxutil.com/pub_v2/pay/notify.v2.php</notify_url>
     * <openid>oUpF8uMuAJO_M2pxb1Q9zNjWeS6o</openid>
     * <out_trade_no>1415659990</out_trade_no>
     * <spbill_create_ip>14.23.150.211</spbill_create_ip>
     * <total_fee>1</total_fee>
     * <trade_type>JSAPI</trade_type>
     * <sign>0CB01533B8C1EF103065174F50BCA001</sign>
     * </xml>
     */
    final Map<String, String> receiveMiniData(String subMchId, String payOrderNo, PayOrderDto payOrderDto) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("sub_mch_id", subMchId);
        //openid
        paramMap.put("openid", payOrderDto.getBuyerId());
//        paramMap.put("sub_appid", payOrderDto.getSubAppId());
//        paramMap.put("sub_openid", payOrderDto.getSubOpenid());
        //交易类型
        paramMap.put("trade_type", "JSAPI");
        //本机的Ip
        paramMap.put("spbill_create_ip", FuncUtil.getLocalIp());

        if (null != payOrderDto.getAttach())
            paramMap.put("attach", payOrderDto.getAttach());
        //描述
        paramMap.put("body", payOrderDto.getSubject());
        //商户 后台的贸易单号
        paramMap.put("out_trade_no", payOrderNo);
        //金额必须为整数  单位为转换为分
        paramMap.put("total_fee", "" + Math.round(payOrderDto.getAmount() * 100));
        //支付成功后，回调地址
        paramMap.put("notify_url", WXPayConfig.getNotifyUrl());
        return paramMap;
    }


    /**
     * 获取全部预支付信息
     *
     * @return {nonce_str=xVY4aB6fwmqRtnS5, code_url=weixin://wxpay/bizpayurl?pr=LJDPPrh,
     * appid=wx4dc8cb7f0eb12288, sign=F71217660263C48FCE4D83B89F65AE2BFA6DA3CA9253CDDA9E4B1761E22322D0,
     * trade_type=NATIVE,
     * return_msg=OK,
     * result_code=SUCCESS,
     * mch_id=1519839251,
     * return_code=SUCCESS,
     * prepay_id=wx09174830845779aca89c32d40421876724}
     * @author G/2016年10月27日 上午9:04:42
     */
    final Map postReceive(Map<String, String> map) throws Exception {
        WXPayConfig wxPayConfig = new WXPayConfig();
        wxPayConfig.setUrl(WXPayConstants.getUnifiedOrderUrl());
        WXPayRequest wxPayRequest = new WXPayRequest(wxPayConfig);
        Map<String, Object> result = wxPayRequest.request(map);
        L("调用结果=" + result.toString());

        if (result.get("return_code") != null
                && result.get("return_code").equals("SUCCESS")
                && result.get("result_code") != null
                && result.get("result_code").equals("SUCCESS")
        ) {
            L("调用成功=" + result.toString());
            return ResultUtil.paySuccess(result.toString());
        } else {
            L("调用失败");
            return ResultUtil.payFailed();
        }
    }

    /**
     * 调取扫码付款
     * <p>
     * 扫码付款分两种情况
     * {1:扫码立即到账,2:系统需要等待用户确认}
     * 当第二种情况时扫码支付结果会返回 WAIT_BUYER_PAY
     * 系统必须做轮询设计等待用户付款
     * <p>
     * 轮询设计
     * 当请求支付返回【WAIT_BUYER_PAY】时，收银系统需要做轮询处理，建议：
     * 收银终端界面阻塞并提示“等待用户确认支付”。
     * 轮询间隔设为3-6秒，轮询总时长60秒左右。
     * 轮询时，收银终端界面提供手动停止功能，停止时必须调用撤销API撤销支付宝交易。
     *
     * @return 调用成功后返回微信收款单号
     * @author G/2016年10月27日 上午9:04:42
     */
    final Map postMicroPay(Map<String, String> map) throws Exception {
        WXPayConfig wxPayConfig = new WXPayConfig();
        wxPayConfig.setUrl(WXPayConstants.getMicroPayUrl());
        WXPayRequest wxPayRequest = new WXPayRequest(wxPayConfig);
        //内置60秒的轮询
//        Map<String, Object> result = wxPayRequest.requestTimes(map);

        //不使用轮询
        Map<String, Object> result = wxPayRequest.request(map);

        L("调用结果=" + result);
        //2020-01-04
        if (result.get("return_code") != null
                && result.get("return_code").equals("SUCCESS")
                && result.get("result_code") != null
                && result.get("result_code").equals("SUCCESS")
        ) {
            //如果返回支付结果
            if (result.get("trade_state") != null
                    && result.get("trade_state").equals("SUCCESS")) {
                L("收款成功=" + result.toString());
                return ResultUtil.paySuccess();
            } else {
               return ResultUtil.payFailed();
            }
        } else {
           return ResultUtil.payFailed();
        }
    }


    //========================================查询支付结果====================================================/

    /**
     * 条码付款结果查询
     * <xml>
     * <appid>wx2421b1c4370ec43b</appid>
     * <mch_id>10000100</mch_id>
     * <nonce_str>ec2316275641faa3aacf3cc599e8730f</nonce_str>
     * <transaction_id>1008450740201411110005820873</transaction_id>
     * <out_trade_no>1415757673</out_trade_no>
     * <sign>FDD167FAA73459FD921B144BAF4F4CA2</sign>
     * </xml>
     *
     * @return
     */
    final Map<String, String> payQueryData(String subMchId, String payOrderNo) {
        Map<String, String> paramMap = new HashMap<>();
        //子商户号
        paramMap.put("sub_mch_id", subMchId);
        //随机数
        paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
        //transaction_id是微信系统为每一笔支付交易分配的订单号，通过这个订单号可以标识这笔交易，它由支付订单API支付成功时返回的数据里面获取到。
        //根据API给的签名规则进行签名
        paramMap.put("out_trade_no", payOrderNo);
        return paramMap;
    }

    /**
     * 提交付款结果查询
     * 支持调用接口提交的交易类型，取值如下：JSAPI，NATIVE，APP，MICROPAY，详细说明见参数规定
     *
     * @return {nonce_str=onrD9MNj3CUuzAw0, out_trade_no=1901081654330016, trade_state=NOTPAY, appid=wx4dc8cb7f0eb12288, sign=FDFD78E9D5569D1E646D37387D45B77C066EB1BEBAD129A1CFD3C53F50F68F24, trade_state_desc=订单未支付, return_msg=OK, result_code=SUCCESS, mch_id=1519839251, return_code=SUCCESS}
     * 根据返回结果中的 trade_state 判断
     * SUCCESS—支付成功
     * REFUND—转入退款
     * NOTPAY—未支付
     * CLOSED—已关闭
     * REVOKED—已撤销(刷卡支付)
     * USERPAYING--用户支付中
     * PAYERROR--支付失败(其他原因，如银行返回失败)
     * @author G/2016年10月27日 上午9:04:42
     */
    final Map postPayQuery(Map<String, String> map) throws Exception {
        WXPayConfig wxPayConfig = new WXPayConfig();
        wxPayConfig.setUrl(WXPayConstants.getOrderQueryUrl());
        WXPayRequest wxPayRequest = new WXPayRequest(wxPayConfig);
        Map<String, Object> result = wxPayRequest.request(map);

        L("调用结果=" + result.toString());

        if (null == result.get("return_code")
                || !result.get("return_code").equals("SUCCESS")) {
            L("调用接口失败");
//            return FastJsonUtil.get(false, RespTips.ERROR_CODE.code, "调用接口失败");
           return ResultUtil.payFailed();
        }

        //如果不正常返回
        if (null == result.get("result_code")
                || !result.get("result_code").equals("SUCCESS")) {
            L("支付结果错误");
//            return FastJsonUtil.get(false, result.get("err_code").toString(), "支付结果错误");
           return ResultUtil.payFailed();
        }

        //如果返回支付结果
        if (result.get("trade_state") != null
                && result.get("trade_state").equals("SUCCESS")) {
            L("收款成功=" + result.toString());

            /**
             * 如果支付成功需要做一个字段转换
             * result.get("transaction_id").toString(),result.get("openid").toString(),result.get("sub_mch_id").toString();
             * String payChannelNo,String payerId,String payeeId;
             */
            return ResultUtil.paySuccess();

        } else if (result.get("trade_state") != null
                && result.get("trade_state").equals("PAYERROR")) {
           return ResultUtil.payFailed();
        } else {
            L("收款未成功");
            return ResultUtil.payWaiting();
        }
    }


    /**
     * 获取openid
     * @author G/2016年10月31日 下午3:44:12
     * @param code
     * @return 微信回调结果
     * @throws UnsupportedEncodingException
     */
    public static JSONObject getOpenId(String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token";
        WXPayConfig wxPayConfig = new WXPayConfig();
        Map<String, String> params = new HashMap<>();
        params.put("appid", wxPayConfig.getGzhAppId());
        params.put("secret", wxPayConfig.getGzhAppSecret());
        params.put("code", code);
        params.put("grant_type", "authorization_code");
        String result = SubmitUtil.postMap(url, params);
        return JSONObject.fromObject(result);
    }

    /**
     * 获取JsapiTicket
     * @author G/2016年11月22日 下午4:47:29
     * @return
     */
    public final static String getJsapiTicket(String accessToken) {
        String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";
        url = String.format(url, accessToken);
        String result = SubmitUtil.get(url);
        JSONObject jo = JSONObject.fromObject(result);
        L( "jsapiTicket值" + jo.toString());
        if (jo != null && jo.getString("ticket") != null) {
            result = jo.getString("ticket");
        }
        return result;
    }

    /**
     * jsapi ticket sign
     * @author G/2016年11月22日 下午5:11:57
     * @param jsapi_ticket
     * @param url
     * @return
     */
    public final static Map<String, String> JsapiTicketSign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = WXPayUtil.generateNonceStr();
        String timestamp = ""+WXPayUtil.getCurrentTimestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;
//      System.out.println(string1);
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        return ret;
    }

    static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }



    //微信支付收款业务入口
    public final Map pay(String subMchId, String payOrderNo, PayOrderDto payOrderDto) {
        try {
            if (payOrderDto.getPayType().equals(WxPayOrderDtoCheckPayOrderCheckUtil.TradeType.JSAPI.name())) {
                return postReceive(receiveJsapiData(subMchId, payOrderNo, payOrderDto));
            } else if (payOrderDto.getPayType().equals(WxPayOrderDtoCheckPayOrderCheckUtil.TradeType.APP.name())) {
                return postReceive(receiveAppData(subMchId, payOrderNo, payOrderDto));
            } else if (payOrderDto.getPayType().equals(WxPayOrderDtoCheckPayOrderCheckUtil.TradeType.MWEB.name())) {   //h5收款校验
                return postReceive(receiveH5Data(subMchId, payOrderNo, payOrderDto));
            } else if (payOrderDto.getPayType().equals(WxPayOrderDtoCheckPayOrderCheckUtil.TradeType.MICROPAY.name())) {   //扫码收款校验
                return postMicroPay(microPayData(subMchId, payOrderNo, payOrderDto));
            } else if (payOrderDto.getPayType().equals(WxPayOrderDtoCheckPayOrderCheckUtil.TradeType.NATIVE.name())) {     //主扫
                return postReceive(receiveNativeData(subMchId, payOrderNo, payOrderDto));
            }else
                return ResultUtil.payFailed();

        } catch (Exception e) {
            L("微信支付错误=>" + e.toString());
            return ResultUtil.payFailed();
        }
    }

    //微信支付收款查询接口
    public final Map query(String subMchId, String payOrderNo) {
        try {
            return postPayQuery(payQueryData(subMchId, payOrderNo));
        } catch (Exception e) {
            L("微信收款查询错误=>" + e.toString());
            return ResultUtil.payFailed();
        }
    }

    //输出到LOG文件
    static void L(String s) {
        LogUtil.saveTradeLog(s);
    }
}
