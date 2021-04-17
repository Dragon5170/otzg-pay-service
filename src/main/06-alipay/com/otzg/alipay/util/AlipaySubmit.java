package com.otzg.alipay.util;

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
import com.otzg.util.HashFMap;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class AlipaySubmit {

    /**
     * 统一收单线上收款二维码
     *
     * @param appAuthToken
     * @param outTradeNo
     * @param subject
     * @param totalAmount
     * @param buyerId      必填项与线下的不同
     * @return
     */
    public Map createPay(String appAuthToken,
                         String outTradeNo,
                         String subject,
                         String totalAmount,
                         String buyerId) {

        try {

            AlipayTradeCreateModel model = new AlipayTradeCreateModel();
            model.setOutTradeNo(outTradeNo);
            model.setSubject(subject);
            model.setTotalAmount(totalAmount);
            model.setBuyerId(buyerId);


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
            request.setNotifyUrl(LogUtil.getServUrl() + AlipayConfig.getNotifyUrl());

            AlipayTradeCreateResponse response = getAlipayClient().execute(request);
            if (response.isSuccess()) {
                System.out.println("调用成功=>" + response.getBody());
                //如果支付结果 response.code=10000时表示支付成功
                //如果支付结果 response.code=10003时表示等待用户付款

                return ResultUtil.paySuccess(response.getBody());
            } else {
                System.out.println("调用失败");
                return ResultUtil.payFailed(response.getMsg());
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 统一收单线下收款二维码
     *
     * @param appAuthToken
     * @param outTradeNo
     * @param subject
     * @param totalAmount
     * @return
     */
    public Map precreatePay(String appAuthToken,
                            String outTradeNo,
                            String subject,
                            String totalAmount) {

        try {

            AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
            model.setOutTradeNo(outTradeNo);
            model.setSubject(subject);
            model.setTotalAmount(totalAmount);
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
            request.setNotifyUrl(LogUtil.getServUrl() + AlipayConfig.getNotifyUrl());

            AlipayTradePrecreateResponse response = getAlipayClient().execute(request);
            if (response.isSuccess()) {
                System.out.println("调用成功=>" + response.getBody());
                //如果支付结果 response.code=10000时表示支付成功
                //如果支付结果 response.code=10003时表示等待用户付款

                return ResultUtil.paySuccess(response.getBody());
            } else {
                System.out.println("调用失败");
                return ResultUtil.payFailed(response.getMsg());
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    //======================================app收款=======================================================/

    /**
     * app收款
     *
     * @param appAuthToken
     * @param outTradeNo
     * @param subject
     * @param totalAmount
     * @return
     */
    public Map appPay(String appAuthToken,
                      String outTradeNo,
                      String subject,
                      String totalAmount) {

        try {

            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            model.setOutTradeNo(outTradeNo);
            model.setSubject(subject);
            model.setTotalAmount(totalAmount);

            model.setBody("我是测试数据");
            model.setTimeoutExpress("30m");
            model.setProductCode("QUICK_MSECURITY_PAY");


            /**
             * 系统商编号
             * 该参数作为系统商返佣数据提取的依据，请填写系统商签约协议的PID
             */
            ExtendParams extendParams = new ExtendParams();
            extendParams.setSysServiceProviderId(AlipayConfig.pid);
            model.setExtendParams(extendParams);

            AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
            //第三方应用授权
            request.putOtherTextParam("app_auth_token", appAuthToken);

            request.setBizModel(model);
            //绑定异步通知接口
            request.setNotifyUrl(LogUtil.getServUrl() + AlipayConfig.getNotifyUrl());

            AlipayTradeAppPayResponse response = getAlipayClient().sdkExecute(request);
            if (response.isSuccess()) {
                System.out.println("调用成功=>" + response.getBody());
                //如果支付结果 response.code=10000时表示支付成功
                //如果支付结果 response.code=10003时表示等待用户付款

                Map jo = new HashFMap().p("tradeNo", response.getTradeNo()).p("body", response.getBody());
                return ResultUtil.paySuccess(jo);
            } else {
                System.out.println("调用失败");
                return ResultUtil.payFailed( response.getMsg());
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }

    //======================================支付宝条码收款=======================================================/

    /**
     * 当面付(条码收款)
     *
     * @param appAuthToken       三方授权，商户授权令牌
     * @param outTradeNo         平台业务定单号
     * @param authCode           扫码获取的用户条码
     * @param subject            订单标题
     * @param totalAmount        付款总金额
     * @param discountableAmount 参与优惠计算的金额
     * @return
      */
    public Map barCodPay(String appAuthToken,
                         String authCode,
                         String outTradeNo,
                         String subject,
                         String totalAmount,
                         String discountableAmount,
                         String storeId) {

        try {
            AlipayTradePayModel model = new AlipayTradePayModel();
            model.setOutTradeNo(outTradeNo);
            model.setSubject(subject);
            model.setTotalAmount(totalAmount);
            //支付宝中的付款码
            model.setAuthCode(authCode);
            model.setScene("bar_code");
            model.setStoreId(storeId);

            /**
             * 系统商编号
             * 该参数作为系统商返佣数据提取的依据，请填写系统商签约协议的PID
             */
            ExtendParams extendParams = new ExtendParams();
            extendParams.setSysServiceProviderId(AlipayConfig.pid);
            model.setExtendParams(extendParams);

            AlipayTradePayRequest request = new AlipayTradePayRequest();
            //第三方应用授权
            request.putOtherTextParam("app_auth_token", appAuthToken);

            request.setBizModel(model);
            //绑定异步通知接口
            request.setNotifyUrl(LogUtil.getServUrl() + AlipayConfig.getNotifyUrl());

            AlipayTradePayResponse response = getAlipayClient().execute(request);
            if (response.isSuccess()) {
                System.out.println("调用成功=>" + response.getBody());
                //如果支付结果 response.code=10000时表示支付成功
                //如果支付结果 response.code=10003时表示等待用户付款

                Map jo = new HashFMap().p("tradeNo", response.getTradeNo()).p("buyerUserId", response.getBuyerUserId());
                return ResultUtil.paySuccess(jo);
            } else {
                System.out.println("调用失败");
                return ResultUtil.payFailed( response.getMsg());
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            System.out.println("调用失败");
            return ResultUtil.payFailed(e.getErrMsg());
        }
    }
    //======================================刷脸收款=======================================================/


    public Map facePay(String appAuthToken, String ftoken, String totalAmount) {
        try {
            TradepayParam tradepayParam = new TradepayParam();
            tradepayParam.setOut_trade_no(UUID.randomUUID().toString());

            //auth_code和scene填写需要注意
            tradepayParam.setAuth_code(ftoken); // 人脸zolozVerify接口返回的ftoken
            tradepayParam.setScene("security_code");//对于刷脸付，必须写为security_code
            tradepayParam.setSubject("smilepay");
            tradepayParam.setStore_id("smilepay test");
            tradepayParam.setTimeout_express("5m");
            tradepayParam.setTotal_amount(totalAmount);


            AlipayTradePayRequest request = new AlipayTradePayRequest();
            //第三方应用授权
            request.putOtherTextParam("app_auth_token", appAuthToken);

            request.setBizContent(tradepayParam.toString());

            AlipayTradePayResponse response = getAlipayClient().execute(request);
            if (response.isSuccess()) {
                System.out.println("调用成功=>" + response.getBody());
                //如果支付结果 response.code=10000时表示支付成功
                //如果支付结果 response.code=10003时表示等待用户付款

                Map jo = new HashFMap()
                        .p("tradeNo", response.getTradeNo())
                        .p("buyerUserId", response.getBuyerUserId());
                return ResultUtil.paySuccess(jo);
            } else {
                System.out.println("调用失败");
                return ResultUtil.payFailed( response.getMsg());
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            System.out.println("调用失败");
            return ResultUtil.payFailed(e.getErrMsg());
        }
    }


    /**
     * 查询支付结果
     *
     * @param outTradeNo
     * @param tradeNo
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
    public Map payQuery(String appAuthToken,
                        String outTradeNo,
                        String tradeNo) {
        try {
            AlipayTradeQueryModel model = new AlipayTradeQueryModel();
            model.setOutTradeNo(outTradeNo);
            model.setTradeNo(tradeNo);

            AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
            //第三方应用授权
            request.putOtherTextParam("app_auth_token", appAuthToken);
            request.setBizModel(model);

            AlipayTradeQueryResponse response = getAlipayClient().execute(request);
            LogUtil.print("查询支付结果" + response.getBody());
            //{"alipay_trade_query_response":{"code":"10000","msg":"Success","buyer_logon_id":"375***@qq.com",
            // "buyer_pay_amount":"0.00","buyer_user_id":"2088002123336273","invoice_amount":"0.00",
            // "out_trade_no":"2019033011111111119","point_amount":"0.00","receipt_amount":"0.00",
            // "total_amount":"0.01","trade_no":"2019120622001436275742033599","trade_status":"WAIT_BUYER_PAY"},
            // "sign":"VEWHEd08A+yuEjakUTJkOADvyV/4Hm5zWG5r/tP3J2+aeqF/EMFNWeiN4nvDcL08gm4mKIKCpF2FIQmAmHuZ0j264eOGcVRYfR1Mf2fE3pgSRVIuKi6/9vSyMysb6+1zzJu1GQ3R6iVU8rv8MmqYD41G6YWKupuAcU4X9efNotzMdXNk3vY5sELZZhJBhitL0z43f7mDhApvt2GvnWI6XW/WVNKjqcLP38vLxYnG0OdtBwJMEwkPGV5+tTzjQnWX3A+h5KDeEd7eLNKtVmtobl9LJI9fe//yJsM1zTaA7Ck9qbwtesl/5Pq4pKuOCdLBH7G3r2h1ZAiDtevyA71bEw=="}
            if (response.isSuccess()) {
                //交易状态：WAIT_BUYER_PAY（交易创建，等待买家付款）、TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）、TRADE_SUCCESS（交易支付成功）、TRADE_FINISHED（交易结束，不可退款）
                if (response.getTradeStatus().equals("TRADE_SUCCESS")) {
                    return ResultUtil.paySuccess();
                } else if (response.getTradeStatus().equals("WAIT_BUYER_PAY")) {
                    return ResultUtil.payWaiting();
                } else if (response.getTradeStatus().equals("TRADE_CLOSED")) {
                    return ResultUtil.paySuccess();
                } else if (response.getTradeStatus().equals("TRADE_FINISHED")) {
                    return ResultUtil.paySuccess();
                } else {
                    return ResultUtil.payFailed();
                }
            } else {
                return ResultUtil.payFailed();
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            System.out.println("调用失败");
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
                return ResultUtil.payWaiting(response.getBody());
            }

            Map jo = new HashFMap()
            .p("appAuthToken", response.getAppAuthToken())
            .p("appRefreshToken", response.getAppRefreshToken())
            .p("authAppId", response.getAuthAppId())
            .p("expiresIn", response.getExpiresIn())
            .p("reExpiresIn", response.getReExpiresIn())
            .p("userId", response.getUserId());
            return ResultUtil.paySuccess(jo);


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

            //jo=>{"code":"10000","data":{"expiresIn":"31536000","appRefreshToken":"201912BBf17117f29f0c49fa82a0ee8abb369F96","appAuthToken":"201912BBbcc767843e584bec8dd0747867819X96","reExpiresIn":"32140800","userId":"2088431307185963","authAppId":"2019012863166278"},"success":true}


        } catch (AlipayApiException e) {
            e.printStackTrace();
            return ResultUtil.payFailed(e.getMessage());
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
                 Map jo = new HashFMap()
                .p("appAuthToken", response.getAppAuthToken())
                .p("appRefreshToken", response.getAppRefreshToken())
                .p("authAppId", response.getAuthAppId())
                .p("expiresIn", response.getExpiresIn())
                .p("reExpiresIn", response.getReExpiresIn())
                .p("userId", response.getUserId());
                return ResultUtil.paySuccess(jo);
            } else {
                return ResultUtil.payFailed(response.getCode());
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return ResultUtil.payFailed(e.getMessage());
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
                 Map jo = new HashFMap()
                .p("userId", response.getUserId())
                .p("authAppId", response.getAuthAppId())
                //交换令牌的有效期，单位秒，换算成天的话为365天
                .p("expiresIn", response.getExpiresIn())
                .p("authMethods", response.getAuthMethods())
                .p("authStart", response.getAuthStart())
                //当前app_auth_token的授权失效时间
                .p("authEnd", response.getAuthEnd())
                .p("status", response.getStatus());
                return ResultUtil.paySuccess(jo);
            } else {
                return ResultUtil.payFailed( response.getMsg());
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return ResultUtil.payFailed(e.getMessage());
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
            Map<String, String> params = new HashMap<String, String>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
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


    /**
     * 退款
     * 订单支付时传入的商户订单号,不能和 trade_no同时为空。
     *
     * @param outTradeNo
     * @param refundAmount
     * @param storeId
     * @return {"alipay_trade_refund_response":{"code":"10000","msg":"Success","
     * buyer_logon_id":"vvn***@sandbox.com","buyer_user_id":"2088102169133529",
     * "fund_change":"N","gmt_refund_pay":"2019-01-09 15:01:46",
     * "out_trade_no":"191018154556136588110",
     * "refund_detail_item_list":[{"amount":"1.00","fund_channel":"ALIPAYACCOUNT"}],
     * "refund_fee":"1.00","send_back_fee":"1.00","trade_no":"2019010922001433520200663215"},
     * "sign":"fsRfZ8cHsiK+NizXnvE1RweP1rDiQQe30EMj7B5mqk0V/VayuE5xXj4kVbRQsiNr4iMROnbbjKjL3iPraH9gDqCzWKn2HD4dkiXhe24MZA+9uBNpBXf0RRg1eNcT89eSGnRAfxcvuovHIvkTca3VpZ3IfzXu8/WtLe7tKWjBG9HlWEILMo2x2PhiP2jDKmv5HYu4humw6xMiMzn30JYi1kx8A5EnpY5uW9Ys1pfZpmLsOqsfgzppfpNMr8zRJaDtvbGH/bz4Z5nTVe9+BiBK+3qJbPPPWeQfPVmKXm0DJnUkFcU1ViL283yd2WUUJPHdQ77GEHqf9U/SVKACGIFRiw=="}
     */
    public Map refund(String appAuthToken,
                      String outTradeNo,
                      String refundAmount,
                      String storeId) throws AlipayApiException {

        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        model.setOutTradeNo(outTradeNo);
        model.setRefundAmount(refundAmount);
        model.setStoreId(storeId);

        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        //第三方应用授权
        request.putOtherTextParam("app_auth_token", appAuthToken);
        request.setBizModel(model);

        AlipayTradeRefundResponse response = getAlipayClient().execute(request);
        if (response.isSuccess()) {
            return ResultUtil.paySuccess(response.getTradeNo());
        } else {
            return ResultUtil.payFailed( response.getMsg());
        }
    }


    /**
     * 退款查询
     *
     * @param outTradeNo 平台单号
     * @param tradeNo    收款单号
     * @return {"alipay_trade_fastpay_refund_query_response":{"code":"10000","msg":"Success","out_request_no":"191018154556136588113","out_trade_no":"191018154556136588113","refund_amount":"1.00","total_amount":"1.00","trade_no":"2019010922001433520200660680"},"sign":"j8OcS61wlzGJ0Zb7wUMplSTmtRDqet/vuJUUDPxCefyMZjnBWbI3tNiN4QeVwhHT1ugP0H2IdNGOJueswboEqT20Cy5fqrZj1WnzxpT1YpCGbL+eAo69A5oZLI0W4hbZnepbR0MpxnOA70A7vD3SSouPn34LQqMF9VX/cbp14zgWNDil0oEKhaoYkKX3ggpyYInE0QpMxFh4hmN5zYcQH088p4yOny8L315AN066imFBrgTLgmyu/48l1Vn89RzOfXTc88XJ7tyvHxmooR21icar5zBhGT6+8PnBcMN7d734bB+d/qZE7pKKbL8+7weMBPxpdLtx1mCbBMqE3VSyFg=="}
     */
    public Map refundQuery(String appAuthToken, String outTradeNo,
                           String tradeNo) throws AlipayApiException {

        AlipayTradeFastpayRefundQueryModel model = new AlipayTradeFastpayRefundQueryModel();
        model.setOutTradeNo(outTradeNo);
        //请求退款接口时，传入的退款请求号，如果在退款请求时未传入，则该值为创建交易时的外部交易号
        model.setOutRequestNo(outTradeNo);
        //收款单号
        model.setTradeNo(tradeNo);

        AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
        //第三方应用授权
        request.putOtherTextParam("app_auth_token", appAuthToken);
        request.setBizModel(model);

        AlipayTradeFastpayRefundQueryResponse response = getAlipayClient().execute(request);
        if (response.isSuccess()) {
            return ResultUtil.paySuccess();
        } else {
            return ResultUtil.payFailed(response.getMsg());
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


    /**
     * 支付宝发红包一共四步
     * 1.创建红包
     * 2.打款
     * 3.管理活动接口
     * 4.活动触发
     */

    //第一步创建红包
    Map result = new HashMap();

    public Map redbag1(String appAuthToken, AlipayMarketingCampaignCashCreateModel alipayModel) {
        //初始化请求类
        AlipayMarketingCampaignCashCreateRequest request = new AlipayMarketingCampaignCashCreateRequest();
        ///第三方应用授权
        request.putOtherTextParam("app_auth_token", appAuthToken);
        //设置业务参数，alipayModel为前端发送的请求信息，开发者需要根据实际情况填充此类
        request.setBizModel(alipayModel);
//        request.setReturnUrl(prop.getProperty("RETURN_URL"));
        request.setNotifyUrl(AlipayConfig.getNotifyUrl());
        //sdk请求客户端，已将配置信息初始化
        try {
            //因为是接口服务，使用exexcute方法获取到返回值
            AlipayMarketingCampaignCashCreateResponse alipayResponse = getAlipayClient().execute(request);
            if (alipayResponse.isSuccess()) {
                System.out.println("调用成功");
                //TODO 实际业务处理，开发者编写。可以通过alipayResponse.getXXX的形式获取到返回值
            } else {
                System.out.println("调用失败");
            }
            result.put("success", true);
            result.put("code", "0000");
            result.put("data", alipayResponse.getBody());
            return result;

        } catch (AlipayApiException e) {
            e.printStackTrace();
            if (e.getCause() instanceof java.security.spec.InvalidKeySpecException) {
                result.put("success", false);
                result.put("code", "0001");
                result.put("data", "商户私钥格式不正确，请确认配置文件Alipay-Config.properties中是否配置正确");
                return result;
            }
        }
        return result;
    }

    public Map redbag4(String appAuthToken, AlipayMarketingCampaignCashTriggerModel alipayModel) {
        //初始化请求类
        AlipayMarketingCampaignCashTriggerRequest request = new AlipayMarketingCampaignCashTriggerRequest();
        //第三方应用授权
        request.putOtherTextParam("app_auth_token", appAuthToken);
        //设置业务参数，alipayModel为前端发送的请求信息，开发者需要根据实际情况填充此类
        request.setBizModel(alipayModel);
//        alipayRequest.setReturnUrl(AlipayConfig.get);
        request.setNotifyUrl(AlipayConfig.getNotifyUrl());


        //sdk请求客户端，已将配置信息初始化
        AlipayClient alipayClient = getAlipayClient();
        try {
            //因为是接口服务，使用exexcute方法获取到返回值
            AlipayMarketingCampaignCashTriggerResponse alipayResponse = alipayClient.execute(request);
            if (alipayResponse.isSuccess()) {
                System.out.println("调用成功");
                //TODO 实际业务处理，开发者编写。可以通过alipayResponse.getXXX的形式获取到返回值
            } else {
                System.out.println("调用失败");
            }


            result.put("success", true);
            result.put("code", "0000");
            result.put("data", alipayResponse.getBody());
            return result;

        } catch (AlipayApiException e) {
            e.printStackTrace();
            if (e.getCause() instanceof java.security.spec.InvalidKeySpecException) {
                result.put("success", false);
                result.put("code", "0001");
                result.put("data", "商户私钥格式不正确，请确认配置文件Alipay-Config.properties中是否配置正确");
                return result;
            }
        }
        return result;
    }

}
