package com.otzg.alipay.util;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.*;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.otzg.alipay.util.face.api.request.TradepayParam;
import com.otzg.util.ResultUtil;
import com.otzg.log.util.LogUtil;
import com.otzg.pay.dto.PayOrderDto;
import com.otzg.pay.util.*;
import com.otzg.util.CheckUtil;
import com.otzg.util.FMap;
import com.otzg.util.FuncUtil;
import com.otzg.util.HashFMap;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class AlipayUtil implements PayReceive, PayQuery {

    /**
     * 统一收单线上收款二维码
     *
     * @param appAuthToken
     * @return
     */
    public Map createPay(String appAuthToken, String payOrderNo, PayOrderDto payOrderDto) {

        try {
            AlipayTradeCreateModel model = new AlipayTradeCreateModel();
            model.setOutTradeNo(payOrderNo);
            model.setSubject(payOrderDto.getSubject());
            model.setTotalAmount(FuncUtil.getDoubleScale(payOrderDto.getAmount()).toString());
            //必填项与线下的不同
            model.setBuyerId(payOrderDto.getBuyerId());


            /**
             * 系统商编号
             * 该参数作为系统商返佣数据提取的依据，请填写系统商签约协议的PID
             */
            ExtendParams extendParams = new ExtendParams();
            extendParams.setSysServiceProviderId(AlipayConfig.pid);
            model.setExtendParams(extendParams);


            //初始化请求类
            AlipayTradeCreateRequest request = new AlipayTradeCreateRequest();
            //第三方应用授权
            request.putOtherTextParam("app_auth_token", appAuthToken);

            request.setBizModel(model);
            //绑定异步通知接口
            request.setNotifyUrl(AlipayConfig.getNotifyUrl());

            AlipayTradeCreateResponse response = getAlipayClient().execute(request);
            if (response.isSuccess()) {
                L("调用成功=>" + response.getBody());
                //如果支付结果 response.code=10000时表示支付成功
                //如果支付结果 response.code=10003时表示等待用户付款

                //获取预支付信息成功，等待支付
                return ResultUtil.payWaiting(response.getBody());
            } else {
                L("调用失败");
                return ResultUtil.payFailed();
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            L("调用失败");
            return ResultUtil.payFailed();
        }
    }

    /**
     * 统一收单线下收款二维码
     *
     * @param appAuthToken
     * @return
     */
    public Map precreatePay(String appAuthToken, String payOrderNo, PayOrderDto payOrderDto) {

        try {
            AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
            model.setOutTradeNo(payOrderNo);
            model.setSubject(payOrderDto.getSubject());
            model.setTotalAmount(FuncUtil.getDoubleScale(payOrderDto.getAmount()).toString());
            model.setTimeoutExpress("30m");


            /**
             * 系统商编号
             * 该参数作为系统商返佣数据提取的依据，请填写系统商签约协议的PID
             */
            ExtendParams extendParams = new ExtendParams();
            extendParams.setSysServiceProviderId(AlipayConfig.pid);
            model.setExtendParams(extendParams);


            //初始化请求类
            AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
            //第三方应用授权
            request.putOtherTextParam("app_auth_token", appAuthToken);

            request.setBizModel(model);
            //绑定异步通知接口
            request.setNotifyUrl(AlipayConfig.getNotifyUrl());

            AlipayTradePrecreateResponse response = getAlipayClient().execute(request);
            if (response.isSuccess()) {
                L("调用成功=>" + response.getBody());
                //如果支付结果 response.code=10000时表示支付成功
                //如果支付结果 response.code=10003时表示等待用户付款

                //获取预支付信息成功，等待支付
                return ResultUtil.payWaiting(response.getBody());
            } else {
                L("调用失败");
                return ResultUtil.payFailed();
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            L("调用失败");
            return ResultUtil.payFailed();
        }
    }

    //======================================app收款=======================================================/

    /**
     * app收款
     *
     * @param appAuthToken
     * @return
     */
    public Map appPay(String appAuthToken, String payOrderNo, PayOrderDto payOrderDto) {

        try {

            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            model.setOutTradeNo(payOrderNo);
            model.setSubject(payOrderDto.getSubject());
            model.setTotalAmount(FuncUtil.getDoubleScale(payOrderDto.getAmount()).toString());

            if (!CheckUtil.isEmpty(payOrderDto.getDetail())) {
                model.setBody(payOrderDto.getDetail());
            }
            model.setTimeoutExpress("30m");
            model.setProductCode("QUICK_MSECURITY_PAY");


            /**
             * 系统商编号
             * 该参数作为系统商返佣数据提取的依据，请填写系统商签约协议的PID
             */
            ExtendParams extendParams = new ExtendParams();
            extendParams.setSysServiceProviderId(AlipayConfig.pid);
            if (null != payOrderDto.getHbFqNum())
                extendParams.setHbFqNum(payOrderDto.getHbFqNum());
            model.setExtendParams(extendParams);

            AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
            //第三方应用授权
            request.putOtherTextParam("app_auth_token", appAuthToken);

            request.setBizModel(model);
            //绑定异步通知接口
            request.setNotifyUrl(AlipayConfig.getNotifyUrl());

            AlipayTradeAppPayResponse response = getAlipayClient().sdkExecute(request);
            if (response.isSuccess()) {
                L("调用成功=>" + response.getBody());
                //如果支付结果 response.code=10000时表示支付成功
                //如果支付结果 response.code=10003时表示等待用户付款

                //获取预支付信息成功等待支付
                return ResultUtil.payWaiting(response.getBody());
            } else {
                L("调用失败");
                return ResultUtil.payFailed();
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return ResultUtil.payFailed();
        }
    }

    //======================================支付宝条码收款=======================================================/

    /**
     * 当面付(条码收款)
     *
     * @param appAuthToken 三方授权，商户授权令牌
     * @return {
     * {"alipay_trade_pay_response":
     * {"code":"10003",
     * "msg":" order success pay inprocess",
     * "buyer_logon_id":"vvn***@sandbox.com",
     * "buyer_pay_amount":"0.00",
     * "buyer_user_id":"2088102169133529",
     * "buyer_user_type":"PRIVATE",
     * "invoice_amount":"0.00",
     * "out_trade_no":"191018154556136588118",
     * "point_amount":"0.00",
     * "receipt_amount":"0.00",
     * "total_amount":"1.00",
     * "trade_no":"2019011022001433520200667601"},
     * "sign":"Io//wg+Q85Qg+408yv1WGsIAFrtIRno3eURYv2J6KrXwlUPXfVkF1N40luV6KgsvMBPArg44kwC8jyIJUlIZUN3tsOhrdtcXdC1u0tQHY9U4qZ2Z0qW2S8rr8fMZTWV7Py4mViroNOoPIckFjICSYaM7nhDl46uOuXpmFTJcOd9SsXEMS8aDQK3oicWvLtItwkxQdSLEQ01Ommg3XiwhdvtDuJRbA7bDEDlZnbeGeXzfW4WBExYHJMmVvetXZcsAAhJN0R5olx9PCmabLDzKe4FiHSxORDqZtvwnr37f2xRizEW37N4vbS6/kyuNL0NtTpoVBBq5RKmd3RLtuDjHDg=="}
     */
    public Map barCodPay(String appAuthToken, String payOrderNo, PayOrderDto payOrderDto) {

        try {
            AlipayTradePayModel model = new AlipayTradePayModel();
            model.setOutTradeNo(payOrderNo);
            model.setSubject(payOrderDto.getSubject());
            model.setTotalAmount(FuncUtil.getDoubleScale(payOrderDto.getAmount()).toString());
            //支付宝中的付款码
            model.setAuthCode(payOrderDto.getAuthCode());
            model.setScene("bar_code");
//            model.setStoreId(storeId);

            /**
             * 系统商编号
             * 该参数作为系统商返佣数据提取的依据，请填写系统商签约协议的PID
             */
            ExtendParams extendParams = new ExtendParams();
            extendParams.setSysServiceProviderId(AlipayConfig.pid);
            if (null != payOrderDto.getHbFqNum())
                extendParams.setHbFqNum(payOrderDto.getHbFqNum());
            model.setExtendParams(extendParams);

            AlipayTradePayRequest request = new AlipayTradePayRequest();
            //第三方应用授权
            request.putOtherTextParam("app_auth_token", appAuthToken);

            request.setBizModel(model);
            //绑定异步通知接口
            request.setNotifyUrl(AlipayConfig.getNotifyUrl());

            AlipayTradePayResponse response = getAlipayClient().execute(request);
            if (response.isSuccess()) {
                L("调用成功=>" + response.getBody());
                //如果支付结果 response.code=10000时表示支付成功
                //如果支付结果 response.code=10003时表示等待用户付款
                return ResultUtil.paySuccess();
            } else {
                L("等待用户付款");
                return ResultUtil.payWaiting();
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            L("调用错误");
            return ResultUtil.payFailed();
        }
    }
    //======================================刷脸收款=======================================================/


    public Map facePay(String appAuthToken, String payOrderNo, PayOrderDto payOrderDto) {
        try {
            TradepayParam tradepayParam = new TradepayParam();
            tradepayParam.setOut_trade_no(UUID.randomUUID().toString());

            //auth_code和scene填写需要注意
            tradepayParam.setAuth_code(payOrderDto.getAuthCode()); // 人脸zolozVerify接口返回的ftoken
            tradepayParam.setScene("security_code");//对于刷脸付，必须写为security_code
            tradepayParam.setSubject("smilepay");
            tradepayParam.setStore_id("smilepay test");
            tradepayParam.setTimeout_express("5m");
            tradepayParam.setTotal_amount(FuncUtil.getDoubleScale(payOrderDto.getAmount()).toString());
            tradepayParam.setOut_trade_no(payOrderNo);


            AlipayTradePayRequest request = new AlipayTradePayRequest();
            //第三方应用授权
            request.putOtherTextParam("app_auth_token", appAuthToken);

            request.setBizContent(JSON.toJSONString(tradepayParam));

            AlipayTradePayResponse response = getAlipayClient().execute(request);
            if (response.isSuccess()) {
                L("调用成功=>" + response.getBody());
                //如果支付结果 response.code=10000时表示支付成功
                //如果支付结果 response.code=10003时表示等待用户付款
                return ResultUtil.paySuccess(response.getBody());
            } else {
                L("调用失败");
                return ResultUtil.payFailed();
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            L("调用失败");
            return ResultUtil.payFailed();
        }
    }

    //=========================================支付宝条码收款结束====================================================/


    //====================================支付宝授权业务====================================================//

    /**
     * 获取授权链接
     * <p>
     * ->客户登录支付宝
     * ->客户点击授权链接
     * ->发起授权
     * ->支付宝回调授权地址返回授权码 app_id 和 app_auth_code
     * ->通过app_auth_code换取app_auth_token
     * <p>
     * <p>
     * 支付宝文档如下:
     * ===================
     * 链接地址:https://docs.open.alipay.com/20160728150111277227/intro
     * <p>
     * 第四步：获取app_auth_code
     * 商户授权成功后，pc或者钱包客户端会跳转至开发者定义的回调页面（即redirect_uri参数对应的url），在回调页面请求中会带上当次授权的授权码app_auth_code和开发者的app_id，示例如下：
     * http://example.com/doc/toAuthPage.html?app_id=2015101400446982&app_auth_code=ca34ea491e7146cc87d25fca24c4cD11
     * 第五步：使用app_auth_code换取app_auth_token
     * 接口名称：alipay.open.auth.token.app
     * 开发者通过app_auth_code可以换取app_auth_token、授权商户的userId以及授权商户AppId。
     * 注意:应用授权的app_auth_code唯一的；app_auth_code使用一次后失效，一天（从生成app_auth_code开始的24小时）未被使用自动过期； app_auth_token永久有效。
     * 请求参数说明
     * 参数	参数名称	类型	必填	描述	范例
     * grant_type	授权类型	String	是	如果使用app_auth_code换取token，则为authorization_code，如果使用refresh_token换取新的token，则为refresh_token	authorization_code
     * code	授权码	String	否	与refresh_token二选一，用户对应用授权后得到，即第一步中开发者获取到的app_auth_code值	bf67d8d5ed754af297f72cc482287X62
     * refresh_token	刷新令牌	String	否	与code二选一，可为空，刷新令牌时使用	201510BB0c409dd5758b4d939d4008a525463X62
     * 接口请求示例: 请先阅读SDK接入说明
     * AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2");
     * AlipayOpenAuthTokenAppRequest request = new AlipayOpenAuthTokenAppRequest();
     * request.setBizContent("{" +
     * "    \"grant_type\":\"authorization_code\"," +
     * "    \"code\":\"1cc19911172e4f8aaa509c8fb5d12F56\"" +
     * "  }");
     * AlipayOpenAuthTokenAppResponse response = alipayClient.execute(request);
     * 同步响应参数说明
     * <p>
     * 参数	参数名称	类型	必填	描述	范例
     * app_auth_token	商户授权令牌	String	是	通过该令牌来帮助商户发起请求，完成业务	201510BBaabdb44d8fd04607abf8d5931ec75D84
     * user_id	授权商户的ID	String	是	授权者的PID	2088011177545623
     * auth_app_id	授权商户的AppId	String	是	授权商户的AppId（如果有服务窗，则为服务窗的AppId）	2013111800001989
     * expires_in	令牌有效期	Number	是	此字段已作废，默认永久有效	31536000
     * re_expires_in	刷新令牌有效期	Number	是	此字段已作废，默认永久有效	32140800
     * app_refresh_token	刷新令牌时使用	String	是	刷新令牌后，我们会保证老的app_auth_token从刷新开始10分钟内可继续使用，请及时替换为最新token	201510BB09dece3ea7654531b66bf9f97cdceE67
     * 同步响应结果示例
     * <p>
     * {
     * "alipay_open_auth_token_app_response": {
     * "code": "10000",
     * "msg": "Success",
     * "app_auth_token": "201510BBb507dc9f5efe41a0b98ae22f01519X62",
     * "app_refresh_token": "201510BB0c409dd5758b4d939d4008a525463X62",
     * "auth_app_id": "2013111800001989",
     * "expires_in": 31536000,
     * "re_expires_in": 32140800,
     * "user_id": "2088011177545623"
     * },
     * "sign": "TR5xJkWX65vRjwnNNic5n228DFuXGFOCW4isWxx5iLN8EuHoU2OTOeh1SOzRredhnJ6G9eOXFMxHWl7066KQqtyxVq2PvW9jm94QOuvx3TZu7yFcEhiGvAuDSZXcZ0sw4TyQU9+/cvo0JKt4m1M91/Quq+QLOf+NSwJWaiJFZ9k="
     * }
     * 注意：
     * <p>
     * 在授权过程中，建议在拼接授权url的时候，开发者可增加自己的一个自定义信息，便于知道是哪个商户授权。
     * 开发者代替商户发起请求时请务必带上app_auth_token，否则支付宝将认为是本应用替自己发起的请求。请注意app_auth_token是POST请求参数，不是biz_content的子参数；在SDK中带上app_auth_token代码示例
     * request.putOtherTextParam("app_auth_token", "201611BB888ae9acd6e44fec9940d09201abfE16");
     * 开发者代替商户发起请求时，POST公共请求参数中的app_id应填写开发者的app_id；如果业务参数biz_content中需要AppId，则应填写商户的AppId。
     */

    //获取授权链接
    public String getAuthTokenUrl(String uid) {
        return String.format(AlipayConfig.auth_url, AlipayConfig.app_id, String.format(AlipayConfig.getAuthNotifyUrl(), uid));
    }

    /**
     * 换取应用授权令牌。
     * 在应用授权的场景下，商户把名下应用授权给ISV后，支付宝会给ISV颁发应用授权码app_auth_code，
     * ISV可通过获取到的app_auth_code换取app_auth_token。app_auth_code作为换取app_auth_token的票据，
     * 每次用户授权带上的app_auth_code将不一样，app_auth_code只能使用一次，一天（从当前时间算起的24小时）未被使用自动过期。
     * 刷新应用授权令牌，ISV可通过获取到的refresh_token刷新app_auth_token，
     * 刷新后老的refresh_token会在一段时间后失效（失效时间为接口返回的re_expires_in）。
     */
    public Map getOpenAuthTokenAppByCode(String appAuthCode) {
        try {
            AlipayOpenAuthTokenAppModel model = new AlipayOpenAuthTokenAppModel();
            model.setCode(appAuthCode);
            model.setGrantType("authorization_code");

            AlipayOpenAuthTokenAppRequest request = new AlipayOpenAuthTokenAppRequest();
            request.setBizModel(model);

            AlipayOpenAuthTokenAppResponse response = getAlipayClient().execute(request);
            //app_auth_token令牌信息String是授权令牌信息
            //app_refresh_token刷新令牌String是刷新令牌
            //auth_app_id授权方应用id String 是 授权方应用 id
            //expires_in令牌有效期 String 是有效期
            //re_expires_in刷新令牌有效时间String是刷新令牌有效期
            //userid支付宝用户标识String是支付宝用户标识

            if (!response.isSuccess()) {
                return ResultUtil.getJson(false, response.getCode(), response.getBody());
            }

            FMap jo = new HashFMap()
                    .p("appAuthToken", response.getAppAuthToken())
                    .p("appRefreshToken", response.getAppRefreshToken())
                    .p("authAppId", response.getAuthAppId())
                    .p("expiresIn", response.getExpiresIn())
                    .p("reExpiresIn", response.getReExpiresIn())
                    .p("userId", response.getUserId());
            return ResultUtil.getJson(true, response.getCode(), response.getMsg(), jo);


            //2019-12-05 13:55
            //{"code":"10000","data":{"appRefreshToken":"201912BB38046e1221ca4c69b8b0859ddafb4X27",
            // "appAuthToken":"201912BBcafdea2c002c4ad687ef7e9e9a02cC27","userId":"2088002123336273",
            // "authAppId":"2019120569622857"},"success":true}
            //李唐token
            //{"code":"10000","data":{"appRefreshToken":"201912BB393c97a1110e4d71aa40773d3b7adC96",
            // "appAuthToken":"201912BB1036430444af4866a42fc6f9028c9D96","userId":"2088431307185963",
            // "authAppId":"2019012863166278"},"success":true}

            //jo=>{"code":"10000","data":{"appRefreshToken":"201912BBdd96cdfee5444d2dade579865e215X27",
            // "appAuthToken":"201912BBe1f7f50ed3cd4910800178b111fb3X27","userId":"2088002123336273",
            // "authAppId":"2019120569622857"},"success":true}

            //jo=>{"code":"10000","data":{"expiresIn":"31536000","appRefreshToken":"201912BBf17117f29f0c49fa82a0ee8abb369F96","appAuthToken":"201912BBbcc767843e584bec8dd0747867819X96",
            // "reExpiresIn":"32140800","userId":"2088431307185963","authAppId":"2019012863166278"},"success":true}


        } catch (AlipayApiException e) {
            e.printStackTrace();
            return ResultUtil.getJson(false, e.getErrCode(), e.getErrMsg());
        }
    }

    /**
     * 换取token
     * 1、openAuthTokenApp
     * 2、appRefreshToken
     * 3、userId
     * 4、authAppId
     */
    public Map getOpenAuthTokenAppByRefreshToken(String refreshToken) {
        try {
            AlipayOpenAuthTokenAppModel model = new AlipayOpenAuthTokenAppModel();
            model.setGrantType("refresh_token");
            model.setRefreshToken(refreshToken);

            AlipayOpenAuthTokenAppRequest request = new AlipayOpenAuthTokenAppRequest();
            request.setBizModel(model);

            AlipayOpenAuthTokenAppResponse response = getAlipayClient().execute(request);
            //app_auth_token令牌信息String是授权令牌信息
            //app_refresh_token刷新令牌String是刷新令牌
            //auth_app_id授权方应用id String 是 授权方应用 id
            //expires_in令牌有效期 String 是有效期
            //re_expires_in刷新令牌有效时间String是刷新令牌有效期
            //userid支付宝用户标识String是支付宝用户标识

            if (response.isSuccess()) {
                FMap jo = new HashFMap()
                        .p("appAuthToken", response.getAppAuthToken())
                        .p("appRefreshToken", response.getAppRefreshToken())
                        .p("authAppId", response.getAuthAppId())
                        .p("expiresIn", response.getExpiresIn())
                        .p("reExpiresIn", response.getReExpiresIn())
                        .p("userId", response.getUserId());

                return ResultUtil.getJson(true, response.getCode(), response.getMsg(), jo);
            } else {
                return ResultUtil.getJson(false, response.getCode(), response.getMsg());
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return ResultUtil.getJson(false, e.getErrCode(), e.getMessage());
        }
    }

    /**
     * 查询授权信息
     *
     * @param appAuthToken
     * @return
     */
    public Map authTokenQuery(String appAuthToken) {
        try {
            AlipayOpenAuthTokenAppQueryModel model = new AlipayOpenAuthTokenAppQueryModel();
            model.setAppAuthToken(appAuthToken);

            AlipayOpenAuthTokenAppQueryRequest request = new AlipayOpenAuthTokenAppQueryRequest();
            request.setBizModel(model);

            AlipayOpenAuthTokenAppQueryResponse response = getAlipayClient().execute(request);
            if (response.isSuccess()) {
                FMap jo = new HashFMap()
                        .p("userId", response.getUserId())
                        .p("authAppId", response.getAuthAppId())

                        //交换令牌的有效期，单位秒，换算成天的话为365天
                        .p("expiresIn", response.getExpiresIn())
                        .p("authMethods", response.getAuthMethods())
                        .p("authStart", response.getAuthStart())
                        //当前app_auth_token的授权失效时间
                        .p("authEnd", response.getAuthEnd())
                        .p("status", response.getStatus());
                return ResultUtil.getJson(true, response.getCode(), response.getMsg(), jo);
            } else {
                return ResultUtil.getJson(false, response.getCode(), response.getMsg());
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return ResultUtil.getJson(false, e.getErrCode(), e.getErrMsg());
        }
    }


    /**
     * 校验异步通知
     *
     * @param
     * @return
     * @author:G/2017年9月14日
     */
    public boolean notifyCheck(HttpServletRequest request) {
        try {
            //获取支付宝POST过来反馈信息
            Map<String, String> params = new HashMap();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = iter.next();
                String[] values = requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                //乱码解决，这段代码在出现乱码时使用。
                //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }
            //切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
            //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
            return AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, "RSA2");
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return false;
        }
    }

    private AlipayClient getAlipayClient() {
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.alipay_gateway,
                AlipayConfig.app_id,
                AlipayConfig.app_private_key,
                "json",
                AlipayConfig.charset,
                AlipayConfig.alipay_public_key,
                AlipayConfig.sign_type);
        return alipayClient;
    }


    //支付宝收款业务入口
    public final Map pay(String appAuthToken, String payOrderNo, PayOrderDto payOrderDto) {
        try {
            if (payOrderDto.getPayType().equals(AliPayOrderDtoCheckPayOrderCheckUtil.TradeType.BARCODE.name())) {
                return barCodPay(appAuthToken, payOrderNo, payOrderDto);
            } else if (payOrderDto.getPayType().equals(AliPayOrderDtoCheckPayOrderCheckUtil.TradeType.APP.name())) {
                return appPay(appAuthToken, payOrderNo, payOrderDto);
            } else if (payOrderDto.getPayType().equals(AliPayOrderDtoCheckPayOrderCheckUtil.TradeType.CREATE.name())) {
                return createPay(appAuthToken, payOrderNo, payOrderDto);
            } else if (payOrderDto.getPayType().equals(AliPayOrderDtoCheckPayOrderCheckUtil.TradeType.PRECREATE.name())) {
                return precreatePay(appAuthToken, payOrderNo, payOrderDto);
            } else if (payOrderDto.getPayType().equals(AliPayOrderDtoCheckPayOrderCheckUtil.TradeType.FACE.name())) {
                return facePay(appAuthToken, payOrderNo, payOrderDto);
            }else
                return ResultUtil.payFailed();

        } catch (Exception e) {
            LogUtil.saveTradeLog("支付宝收款错误=>" + e.toString());
            return ResultUtil.payFailed();
        }
    }


    /**
     * 查询支付结果
     *
     * @param outTradeNo
     * @return {"alipay_trade_query_response":{"code":"10000","msg":"Success","
     * buyer_logon_id":"vvn***@sandbox.com","buyer_pay_amount":"0.00",
     * "buyer_user_id":"2088102169133529","buyer_user_type":"PRIVATE",
     * "invoice_amount":"0.00","out_trade_no":"191018154556136588113",
     * "point_amount":"0.00","receipt_amount":"0.00",
     * "send_pay_date":"2019-01-09 17:00:09","
     * total_amount":"1.00","trade_no":"2019010922001433520200660680",
     * "trade_status":"TRADE_CLOSED"},
     * "sign":"IEtVhRN7tqDsEcWvaLW9zfwx1sR2Fxm/ivQfyXVIiEih+nNq3Ven1hLE/s86Ha0+ovOk29IhC1BrUqz7Ry/vciD+qtesVzEkZ4ypxXK+sgh0iLKFoS9jPoYQZdkkb57Jj0W3xokmwocNO1GOjUf+bMht4ZblncDaMCKK4UNnBRhTyHr/AMx8lv1qYq1aJ30s+Sr4+o9vrkW+f6o358LyrcPuDTYkh2cQ/J9+KR5G6oimRVS7kami7w8ssSI/U1qZrnBcLkllAvDCI26pbQqUO6K/4diEFu3ewmotmIa5miciGLJgrc8yBiIJ7wv3bP9+qTENUmoo+sOl1AkTFM/YRA=="}
     */
    public Map query(String appAuthToken,
                           String outTradeNo) {
        try {
            AlipayTradeQueryModel model = new AlipayTradeQueryModel();
            model.setOutTradeNo(outTradeNo);

            AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
            //第三方应用授权
            request.putOtherTextParam("app_auth_token", appAuthToken);
            request.setBizModel(model);

            AlipayTradeQueryResponse response = getAlipayClient().execute(request);
            LogUtil.print("支付宝查询支付结果" + response.getBody());
            //{"alipay_trade_query_response":{"code":"10000","msg":"Success","buyer_logon_id":"375***@qq.com",
            // "buyer_pay_amount":"0.00","buyer_user_id":"2088002123336273","invoice_amount":"0.00",
            // "out_trade_no":"2019033011111111119","point_amount":"0.00","receipt_amount":"0.00",
            // "total_amount":"0.01","trade_no":"2019120622001436275742033599","trade_status":"WAIT_BUYER_PAY"},
            // "sign":"VEWHEd08A+yuEjakUTJkOADvyV/4Hm5zWG5r/tP3J2+aeqF/EMFNWeiN4nvDcL08gm4mKIKCpF2FIQmAmHuZ0j264eOGcVRYfR1Mf2fE3pgSRVIuKi6/9vSyMysb6+1zzJu1GQ3R6iVU8rv8MmqYD41G6YWKupuAcU4X9efNotzMdXNk3vY5sELZZhJBhitL0z43f7mDhApvt2GvnWI6XW/WVNKjqcLP38vLxYnG0OdtBwJMEwkPGV5+tTzjQnWX3A+h5KDeEd7eLNKtVmtobl9LJI9fe//yJsM1zTaA7Ck9qbwtesl/5Pq4pKuOCdLBH7G3r2h1ZAiDtevyA71bEw=="}
            if (!response.isSuccess()) {
                return ResultUtil.payFailed();
            }

            if (response.getTradeStatus().equals("WAIT_BUYER_PAY")) {
                return ResultUtil.payWaiting();
            }

            //交易状态：WAIT_BUYER_PAY（交易创建，等待买家付款）、TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）、TRADE_SUCCESS（交易支付成功）、TRADE_FINISHED（交易结束，不可退款）
            if (response.getTradeStatus().equals("TRADE_SUCCESS")) {
                return ResultUtil.paySuccess();
            } else if (response.getTradeStatus().equals("TRADE_CLOSED")) {
                return ResultUtil.paySuccess();
            } else if (response.getTradeStatus().equals("TRADE_FINISHED")) {
                return ResultUtil.paySuccess();
            } else {
                return ResultUtil.payFailed();
            }

        } catch (AlipayApiException e) {
            e.printStackTrace();
            L("调用失败");
            return ResultUtil.payWaiting();
        }
    }


    //输出到LOG文件
    static void L(String s) {
        LogUtil.saveTradeLog(s);
    }
}
