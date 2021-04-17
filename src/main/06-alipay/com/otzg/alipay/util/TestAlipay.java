package com.otzg.alipay.util;

import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayMarketingCampaignCashCreateModel;
import com.alipay.api.domain.AlipayMarketingCampaignCashTriggerModel;
import org.junit.Test;

import java.util.Map;

/**
 * @Author G.
 * @Date 2019/12/5 0005 上午 9:54
 */
public class TestAlipay {

    @Test
    public void test1() {
        String outTradeNo = "2019033011111111119";
        String subject = "测试收款";
        String totalAmount="0.01";
        Map jo = new AlipaySubmit().appPay(appAuthToken,outTradeNo,subject,totalAmount);
        System.out.println("result=" + jo);
        //result={"data":{"body":"alipay_sdk=alipay-sdk-java-4.8.73.ALL&app_auth_token=201912BBbcc767843e584bec8dd0747867819X96&app_id=2019011162891191&biz_content=%7B%22body%22%3A%22%E6%88%91%E6%98%AF%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE%22%2C%22extend_params%22%3A%7B%22sys_service_provider_id%22%3A%222088821411693203%22%7D%2C%22out_trade_no%22%3A%222019033011111111119%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E6%B5%8B%E8%AF%95%E6%94%B6%E6%AC%BE%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.01%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=%2Falipay%2Fnotify&sign=YZDhGTIqrQmIMCmi7cUnfY223yHSrUAMlaGLmqyy%2FSEbAdxAzFU9WM7s6a3XK6M9zepzpoU34GLSLrUX%2FXBwpxUn9511rgBKo6aT7Ps7Uh4lqu1e3YNUrebFV7EjyUgh8wwJRX1npUBK4S%2Byn29WPQ3rHNQof1NK6bC9aPbj2JQ4wZguqRo9vADjQbvYKLzGMzljTIX8uu8qGKOiA2cRYk4zM3qKOXFSzV0pHEutQ5rfDGEtESDZM35Q6hlTYICnzhyWtVep%2BshSC%2FapDvh07K5%2FUp4nvbdFeLJSo2VJ4oL7thHgzP3athYUbsUueUj85f67CzVptL%2Fqf8vSAThvwA%3D%3D&sign_type=RSA2&timestamp=2019-12-06+14%3A43%3A26&version=1.0"},"success":true}
        //2019-12-07 10:53
        //result={"data":{"body":"alipay_sdk=alipay-sdk-java-4.8.73.ALL&app_auth_token=201912BBbcc767843e584bec8dd0747867819X96&app_id=2019011162891191&biz_content=%7B%22body%22%3A%22%E6%88%91%E6%98%AF%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE%22%2C%22extend_params%22%3A%7B%22sys_service_provider_id%22%3A%222088821411693203%22%7D%2C%22out_trade_no%22%3A%222019033011111111119%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E6%B5%8B%E8%AF%95%E6%94%B6%E6%AC%BE%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.01%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=%2Falipay%2Fnotify&sign=RMuU0xCkThdUSrF%2B1yvgw1LaQs8C917TSG2%2BI0DSk7MAaGnr%2FpVQRtP83kQgSskxDzNH%2FDyh99lIiGOWjeeXRTBiykPAMQasvjjbT0ivtAKym2udjUEWKJ7IaLUgek7S0W24lneHg8Oqh0FB%2FucVXbNdXL%2FN4KrgHzieQfxjLSWsy%2FOkvTdkloQ0c45KiJg1jrZXWgkbMrqjV8STKp5PpO1pw873S2OmHEf53ZfKyAxn1rOq01C5i1%2BMfVmjztPtat876USEk%2FDV87dWSBh8umD7vw6XnvU2ukaL%2BZLGqiXwDtsjpOfgwD0OUPS61WmyUy%2FcXH2WNuwNjN1kRoCU8g%3D%3D&sign_type=RSA2&timestamp=2019-12-07+10%3A52%3A39&version=1.0"},"success":true}
    }
    @Test
    public void test2() {
        String outTradeNo = "2019033011111111123";
        String subject = "测试李唐收款";
        String totalAmount="0.01";
        Map jo = new AlipaySubmit().precreatePay(appAuthToken,outTradeNo,subject,totalAmount);
        System.out.println("result=" + jo);
        //2019-12-6 15:29
        //调用成功=>{"alipay_trade_precreate_response":{"code":"10000","msg":"Success","out_trade_no":"2019033011111111122","qr_code":"https:\/\/qr.alipay.com\/bax04945bkly02a65o3c00a0"},"sign":"NzNRNTRppOIAacmHQqJ7H4LwfU0+anoHdXsF/GWXUqTQVEvuLX5cFxdFzMBIPM7j9Cc5dPcBcvyb+ifu0lQKIXWr/nQRn+1FKFfB8tpqtxZaxM00vx5CT+J46xiQo417K4BPQYDUCZkvZvoo54BIVar4RzZ20bnzC0Qu/G41gyxP9oBmfHu1RwFv6XaOs2zzGs31BJoTr5Od2/ZvnAO6C9qN8TeYexdMAknj+AxOeLcXHvU5EUpBpeLZnSbZSn0NuuHuycMaW2DvNpyeix3v2mQ4ciiKfTHEpxrD5GFVrW6eJDteM0UEwo1Qa9173GfVKBb7MWNrRFbL7XlPPYlVcA=="}
        //result={"msg":"Success","code":"10000","data":{"body":"{\"alipay_trade_precreate_response\":{\"code\":\"10000\",\"msg\":\"Success\",\"out_trade_no\":\"2019033011111111122\",\"qr_code\":\"https:\\/\\/qr.alipay.com\\/bax04945bkly02a65o3c00a0\"},\"sign\":\"NzNRNTRppOIAacmHQqJ7H4LwfU0+anoHdXsF/GWXUqTQVEvuLX5cFxdFzMBIPM7j9Cc5dPcBcvyb+ifu0lQKIXWr/nQRn+1FKFfB8tpqtxZaxM00vx5CT+J46xiQo417K4BPQYDUCZkvZvoo54BIVar4RzZ20bnzC0Qu/G41gyxP9oBmfHu1RwFv6XaOs2zzGs31BJoTr5Od2/ZvnAO6C9qN8TeYexdMAknj+AxOeLcXHvU5EUpBpeLZnSbZSn0NuuHuycMaW2DvNpyeix3v2mQ4ciiKfTHEpxrD5GFVrW6eJDteM0UEwo1Qa9173GfVKBb7MWNrRFbL7XlPPYlVcA==\"}"},"success":true}
    }
    @Test
    public void test3() {
        String outTradeNo = "2019120700110111";
        String subject = "测试李唐收款";
        String totalAmount="0.01";
        String buyerId="2088002123336273";
        Map jo = new AlipaySubmit().createPay(appAuthToken,outTradeNo,subject,totalAmount,buyerId);
        System.out.println("result=" + jo);
        //2019-12-6 15:29
        //result={"msg":"Success","code":"10000","data":{"body":"{\"alipay_trade_create_response\":{\"code\":\"10000\",\"msg\":\"Success\",\"out_trade_no\":\"2019120700110111\",\"trade_no\":\"2019120722001436275742426378\"},\"sign\":\"AN5oA2TUEMyWXXTvBefV+/f5O+Lkc2d/0W7ZAYbNsALj8Vs1Zjv9b2ekzISrmU3gaB2MXvhvjN78qyjNAyHLb16pG7DZPODoxS0zwtN1TbRNdgCwCgwpGVP/1nnjrHpoWRChYsCRWUSJTCDLm760XsNZ42ml/r9+k/RefFkKv0Assep3kczNKA6c/gwtuoWYdAjTFtz4/NhrbzWJuPEqkTQOVqlaLJ6lLX/CJSpRg3E5f5s9oqH8DON+cT6z68UasN82XnyBsfApvVc3OjweHpW8KY0mPrF/MUCRTnbe+OZSVbfS5b+j+wMkbXK43FbZ3pOlnemfYZ9Q40/QYsF2qg==\"}"},"success":true}
    }

    @Test
    public void test4() {
        String authCode = "289327002348850976";
        String totalAmount="0.01";
        Map jo = new AlipaySubmit().facePay(appAuthToken,authCode,totalAmount);
        System.out.println("result=" + jo);


        //16:49:10.461 [main] DEBUG sdk.biz.err - 2019-12-07 16:49:10^_^https://openapi.alipay.com/gateway.do?alipay_sdk=alipay-sdk-java-4.8.73.ALL&app_auth_token=201912BBbcc767843e584bec8dd0747867819X96&app_id=2019011162891191&biz_content=%7B%22auth_code%22%3A%22289327002348850976%22%2C%22out_trade_no%22%3A%22f73a9e23-7240-44b6-8fff-284800f31516%22%2C%22scene%22%3A%22security_code%22%2C%22store_id%22%3A%22smilepay+test%22%2C%22subject%22%3A%22smilepay%22%2C%22timeout_express%22%3A%225m%22%2C%22total_amount%22%3A%220.01%22%7D&charset=UTF-8&format=json&method=alipay.trade.pay&sign=BcQkfF97ZuTDhhM3bKaWLxgMsERcE%2FAtQqOP0StxNtPcubUSDiflLiJzxThKpzVi4ufFOvcWWG37emBnLnBxZwsFoUHHlHHKFb3bh6ADJHe2lRILdkdUaeHMWB5xUeAgSVaaL35Xt5w%2BpDSiE%2FJlk0OnFbUNTX%2BEF2Y0UdhauViHhut8D%2B0h038%2Bh%2FQ1DCgUfO36THAXWEA%2FCTDgGiQKqGZ3VUoajr%2FxqT6LXpD%2FcTeiQ%2B6%2FkwlOYNlzQ%2BVchBQ4LvxPSiOJycUxJAWMSOzH1CvSorgY8wpGwtpYBUnfShDbc7h6k3JNf1FcTdd3twTfNbyTJ8RUVYE%2Fh7%2FDfpbJnw%3D%3D&sign_type=RSA2&timestamp=2019-12-07+16%3A49%3A09&version=1.0
        //16:49:11.891 [main] INFO sdk.biz.err - Summary^_^10003^_^null^_^ProtocalMustParams:charset=UTF-8&method=alipay.trade.pay&sign=BcQkfF97ZuTDhhM3bKaWLxgMsERcE/AtQqOP0StxNtPcubUSDiflLiJzxThKpzVi4ufFOvcWWG37emBnLnBxZwsFoUHHlHHKFb3bh6ADJHe2lRILdkdUaeHMWB5xUeAgSVaaL35Xt5w+pDSiE/Jlk0OnFbUNTX+EF2Y0UdhauViHhut8D+0h038+h/Q1DCgUfO36THAXWEA/CTDgGiQKqGZ3VUoajr/xqT6LXpD/cTeiQ+6/kwlOYNlzQ+VchBQ4LvxPSiOJycUxJAWMSOzH1CvSorgY8wpGwtpYBUnfShDbc7h6k3JNf1FcTdd3twTfNbyTJ8RUVYE/h7/DfpbJnw==&version=1.0&app_id=2019011162891191&sign_type=RSA2&timestamp=2019-12-07 16:49:09^_^ProtocalOptParams:alipay_sdk=alipay-sdk-java-4.8.73.ALL&format=json^_^ApplicationParams:app_auth_token=201912BBbcc767843e584bec8dd0747867819X96&biz_content={"auth_code":"289327002348850976","out_trade_no":"f73a9e23-7240-44b6-8fff-284800f31516","scene":"security_code","store_id":"smilepay test","subject":"smilepay","timeout_express":"5m","total_amount":"0.01"}^_^674ms,1335ms,93ms
        //2019-12-07 16:50
        //调用成功=>{"alipay_trade_pay_response":{"code":"10003","msg":" order success pay inprocess","buyer_logon_id":"375***@qq.com","buyer_pay_amount":"0.00","buyer_user_id":"2088002123336273","invoice_amount":"0.00","out_trade_no":"f73a9e23-7240-44b6-8fff-284800f31516","point_amount":"0.00","receipt_amount":"0.00","total_amount":"0.01","trade_no":"2019120722001436275742003018"},"sign":"aPmNqQ+C4PK0fq7t60Gu6541TQwb5FWKotbSUErdGTOQRSPQ6V749Pfrm8TF8dC14YI2zOnJtKOOgrOP7ulpMrz6gdiKkql1I6lxkyG3TqQdgQfwHjbxeWGElOin+08fbP4ChWlGjbBucc2zurMtW4aQ9Ge4TRrZ6tWpDRAvEiUSwxjQOC8aUbY2UWWDeoP5/x0EvXupS45s7/gCeLO9FFVAwd6kyLQS5pyCGqVyiQU6eP6qPbiijje6L2sRRL9PnHLPUxLIb32VRdhegPGf5y790ad+WEYCuJucOZWtkt9AiqS+zxcc8uC4qXmdmoyxu5psCGcBd7+I8c0izTRZMQ=="}
        //result={"msg":" order success pay inprocess","code":"10003","data":{"tradeNo":"2019120722001436275742003018","buyerUserId":"2088002123336273"},"success":true}

    }

    @Test
    public void testReceive() throws AlipayApiException {

        //收款码
        String authCode = "289327002348850976";
        String appAuthToken ="201912BBcafdea2c002c4ad687ef7e9e9a02cC27";
        //李唐token
        //收款
        Map jo = new AlipaySubmit().barCodPay(appAuthToken,authCode, "2019033011111111120", "测试收款", "0.01", null, "store_1");
        System.out.println("result=" + jo);
        //2019-12-5 9:46
        //{"alipay_trade_pay_response":{"code":"10000","msg":"Success","buyer_logon_id":"375***@qq.com","buyer_pay_amount":"0.01","buyer_user_id":"2088002123336273","fund_bill_list":[{"amount":"0.01","fund_channel":"PCREDIT"}],"gmt_payment":"2019-12-05 09:47:47","invoice_amount":"0.01","out_trade_no":"2019033011111111115","point_amount":"0.00","receipt_amount":"0.01","total_amount":"0.01","trade_no":"2019120522001436275741299784"},"sign":"zIujk6vxyS9/Cy8UA90A/Y+G8LX7cp6l6/VXBW70+M8QvaiFpbj0ylXIDhTlfkQXNy88/wecvtdHsUFYORXEUOy5vQZBfkp3D6FFi3zQEFyGHpOvGxG5bwEFvoPuWpbNdDVOBZTEjEmXD+lSUdc5bTYDnzQdqlsuhpkjXIkCaIOwvLMYxzi/sZuEmNi1ECCP1zBfC+5IigDivMCI0cOSvVIbfHGAs91Ao0NCxiGOKppPbuwhXjkw3SknugDfmj69evwAYDP9jFbtRdQdv2MPqi6atQto2Y30qSI3JI9gIZ+MBlUN2LSXek0McxMGEzVmCSfnDvdtIBm/5PreT6t52g=="}
        //2019-12-05 14:09
        //{"alipay_trade_pay_response":{"code":"10003","msg":" order success pay inprocess","buyer_logon_id":"375***@qq.com","buyer_pay_amount":"0.00","buyer_user_id":"2088002123336273","invoice_amount":"0.00","out_trade_no":"2019033011111111116","point_amount":"0.00","receipt_amount":"0.00","total_amount":"0.01","trade_no":"2019120522001436275740916242"},"sign":"0zcW5wOgdzvWC+sfiUDAxtxvwQeoo7J89uxNm5QiP2tIDXV9rSf0OwL/CEWam/5j0I2L85JAA4G8uGcvLjQuJHWEFiBmaKwHXiGd3+KOKZuhjMx4r+Ed8eqUsiGDGOA8f7WXnmnYY0dQKEF/NdcZBujJlzhNwd3h+1M/EUwVGK9Grv/7CjVQ2ADttKYozcQES44+8srk66CDXDnNy1TQi3eFhsln01gETtnddLDLqfLnwT/S3ui2CrFfG52MnzyKka5EitnLXG9lXWUAQKofjdiFXOWbh/HJBnyp4kp+wS5yiTVyt5Jp0MlSp0psfjnHYQQ7LZExPR+mgl0eJHG+bw=="}
        //{"msg":" order success pay inprocess","code":"10003","data":{"tradeNo":"2019120522001436275740916242","buyerUserId":"2088002123336273"},"success":true}
        //{"alipay_trade_pay_response":{"code":"10003","msg":" order success pay inprocess","buyer_logon_id":"375***@qq.com","buyer_pay_amount":"0.00","buyer_user_id":"2088002123336273","invoice_amount":"0.00","out_trade_no":"2019033011111111117","point_amount":"0.00","receipt_amount":"0.00","total_amount":"0.01","trade_no":"2019120522001436275741529549"},"sign":"Y3VOQtM6Kp/KB20k+LhK2+VwuSNzA68coUrMF1RnSFGh/kCAdKRvfCJBagp5HW6qrPCdr/CeQiUFJi1ZNJcYXa5CR2vXqkIkbJBYxiInNViy3KfIPkIqsSfC3NWXvixZ8X83D4C/vQVbWWFWA4zCmeC8IwQWR0CZdZzdSZuoVO94trMbCPoVAuYv8uMqKOHJKiKjTZqtIQXjf4s49NUMLj8pB8kCNMQFqOb4EfMTvLorzjIC0JYz+hHFFYLVdkztcL/x1dcifIf/8m8xf1yNE+8MsjoEcZ4tzMX3e9xAZdJWt93HZFWzIyjnEqG8GwOV7cqbdqYGIeGVw0WqPRnhdA=="}
        //{"code":"10003","msg":" order success pay inprocess","buyer_logon_id":"375***@qq.com","buyer_pay_amount":"0.00","buyer_user_id":"2088002123336273","invoice_amount":"0.00","out_trade_no":"2019033011111111117","point_amount":"0.00","receipt_amount":"0.00","total_amount":"0.01","trade_no":"2019120522001436275741529549"},"sign":"Y3VOQtM6Kp/KB20k+LhK2+VwuSNzA68coUrMF1RnSFGh/kCAdKRvfCJBagp5HW6qrPCdr/CeQiUFJi1ZNJcYXa5CR2vXqkIkbJBYxiInNViy3KfIPkIqsSfC3NWXvixZ8X83D4C/vQVbWWFWA4zCmeC8IwQWR0CZdZzdSZuoVO94trMbCPoVAuYv8uMqKOHJKiKjTZqtIQXjf4s49NUMLj8pB8kCNMQFqOb4EfMTvLorzjIC0JYz+hHFFYLVdkztcL/x1dcifIf/8m8xf1yNE+8MsjoEcZ4tzMX3e9xAZdJWt93HZFWzIyjnEqG8GwOV7cqbdqYGIeGVw0WqPRnhdA=="}

        //调用成功=>{"alipay_trade_pay_response":{"code":"10000","msg":"Success","buyer_logon_id":"375***@qq.com","buyer_pay_amount":"0.01","buyer_user_id":"2088002123336273","fund_bill_list":[{"amount":"0.01","fund_channel":"PCREDIT"}],"gmt_payment":"2019-12-05 14:42:26","invoice_amount":"0.01","out_trade_no":"2019033011111111118","point_amount":"0.00","receipt_amount":"0.01","total_amount":"0.01","trade_no":"2019120522001436275741453407"},"sign":"tx/RKJvmshqBuw7sKvPEbwfj2ZTSZ52zxz97hvu2EOpn4rV93ORkIjeJRuRfdJKi51prLIgMhc/vpRu/xCXNQ7vQEUS6lWwE+0o7i3IUwzFQuOsB42CcYqcN03o3dQLh6jNqfePai92Y+9sXfn8lhP6qV7sNZzFZrhXnDz2Wbl1eMG6uO8b6rFhOzIf3ipCdg21LQnF5kwXKsQTWnACXx1+Iku0uoI5vmsWDDblt2TdTC5+y80Nfzai3mHc5ul6aubmBEDLzZ/00PcVjN0iKYLZf2/sDT0rMNZ9rz9Xxv3LQZZa73GMz6R41SKwWACrQD8EcZZgpL++WLXAATut2AQ=="}
        //result={"msg":"Success","code":"10000","data":{"tradeNo":"2019120522001436275741453407","buyerUserId":"2088002123336273"},"success":true}

    }
    //李唐token
//    String appAuthToken ="201912BBb50aa9efa15a49629cda9afa1fb46E27";
    String appAuthToken ="201912BB367a5747d4c94baaa9dfaa13c3a7aX96";
    @Test
    public void testReceiveQuery() throws AlipayApiException {
        //查询
        Map jo = new AlipaySubmit().payQuery(appAuthToken,"20191211161824952745463060",null);
        System.out.println("result="+jo);
        //2019-12-05 9:48
        //{"alipay_trade_query_response":{"code":"10000","msg":"Success","buyer_logon_id":"375***@qq.com","buyer_pay_amount":"0.01",
        // "buyer_user_id":"2088002123336273","fund_bill_list":[{"amount":"0.01","fund_channel":"PCREDIT"}],
        // "invoice_amount":"0.01","out_trade_no":"2019033011111111115","point_amount":"0.00",
        // "receipt_amount":"0.01","send_pay_date":"2019-12-05 09:47:46","total_amount":"0.01",
        // "trade_no":"2019120522001436275741299784","trade_status":"TRADE_SUCCESS"},
        // "sign":"awOBjiN0z9UY4aro3Qgr/ExLPrY0LRwp5+L0PNbEm4+2N010tk3q7wYIbJ5PEkkC2GhnVXt1vwZKvFBCFClASWmPJ7gN0Y3oyc7vNROTegVVuUrUtVOoN6FUhhFPJyNi9bA+3cnkOt34DxesETELc/4uapDLGhyFLmc8L9yRgHBdwntgZ8RMWNFe/GeVEKYfA5JqrD2MvF1MKpso7PPSL5Lok/JMQCTT0TnOzXYfRmobCdvQEfPJhBJ18+omT1irAeQ5TW3okd16Gn7fXV5eYBHfWJ43f8MHNDBAfCGQ68De4MNbsKnfYyRx161j5cs0qA99G6bxb6Mk43RGXa6Nsw=="}
        //查询支付结果{"alipay_trade_query_response":{"code":"10000","msg":"Success","buyer_logon_id":"375***@qq.com","buyer_pay_amount":"0.01","buyer_user_id":"2088002123336273","fund_bill_list":[{"amount":"0.01","fund_channel":"PCREDIT"}],"invoice_amount":"0.01","out_trade_no":"2019033011111111118","point_amount":"0.00","receipt_amount":"0.01","send_pay_date":"2019-12-05 14:42:26","total_amount":"0.01","trade_no":"2019120522001436275741453407","trade_status":"TRADE_SUCCESS"},"sign":"GvPfPe2jl8v1t+6wviOqCA+6qN2JFbjfQawoqO244gijlkzAawIIrHwE7kCypDfI6nz9IeutfMe2TbiVwfZdw/IRX4hcanmrhKZayU4KcBpJjZM4vzo2Co2DTNxyIsOug6LThhuQapdiSYRFqCNaZLtFtSEs5atM/rzBtdKuR8JIt2xzpP4DWORd7cfif+1O4dZXxXNXgnXKWyKxfWT4K65YcCuljS60ylHeZLL1mrCVjc+SxWdrVm/kvKW1mEv9i/2598Sh2iBHN4KNswkK1M+aSxlH7eL+zds3kPbexGpQmG6r94KqwDRikgb+xAFeADvIb5OiFHrPW5DxLQqh0g=="}
        //result={"msg":"支付成功","code":"10000","success":true}
    }
    @Test
    public void testAuthTokenQuery() {
        //查询
        Map jo = new AlipaySubmit().authTokenQuery(appAuthToken);
        System.out.println("result="+ jo.get("data"));
//        System.out.println("result="+ DateUtil.dateTime2Str(jo.getJSONObject("data").getDate("authEnd")));
        //2019-12-06 14:34
        //result={"expiresIn":31536000,"authStart":1575613852000,"authEnd":1607149852000,
        // "authMethods":["alipay.open.mini.version.audit.rejected","alipay.open.auth.appauth.cancelled",
        // "alipay.open.mini.version.audit.passed","alipay.trade.refund","alipay.open.mini.version.audit.cancel",
        // "alipay.open.mini.experience.create","alipay.open.mini.baseinfo.modify","alipay.trade.precreate",
        // "alipay.open.mini.version.detail.query","zoloz.authentication.customer.facemanage.create",
        // "alipay.open.mini.version.online","alipay.open.mini.version.delete","alipay.trade.create",
        // "alipay.open.mini.template.usage.query","alipay.trade.order.settle","alipay.open.app.members.create","zoloz.authentication.customer.facemanage.delete","alipay.trade.close","monitor.heartbeat.syn","alipay.open.mini.version.gray.cancel","alipay.open.mini.category.query","alipay.open.mini.version.build.query","zoloz.authentication.smilepay.initialize","alipay.trade.query","zoloz.authentication.customer.ftoken.query","alipay.open.app.members.delete","alipay.open.mini.version.offline","alipay.trade.pay","alipay.open.mini.version.audit.apply","alipay.open.mini.experience.query","alipay.data.dataservice.bill.downloadurl.query","alipay.trade.wap.pay","alipay.open.auth.token.app.query","alipay.trade.app.pay","alipay.open.mini.version.audited.cancel","alipay.open.mini.safedomain.delete","alipay.open.mini.version.upload","alipay.trade.fastpay.refund.query","alipay.open.mini.experience.cancel","alipay.open.mini.version.list.query","alipay.open.mini.version.rollback","alipay.open.mini.baseinfo.query","alipay.trade.page.pay","alipay.open.auth.token.app","alipay.open.mini.safedomain.create","alipay.open.mini.version.gray.online","alipay.trade.cancel","alipay.open.app.members.query","zoloz.authentication.customer.smilepay.initialize","koubei.trade.order.consult"],"userId":"2088431307185963","authAppId":"2019012863166278","status":"valid"}
    }
   @Test
    public void testAuthTokenRefresh() {
        //李唐token
        String appRefreshToken = "201912BBdd96cdfee5444d2dade579865e215X27";
        //刷新令牌
        Map jo = new AlipaySubmit().getOpenAuthTokenAppByRefreshToken(appRefreshToken);
        System.out.println("jo=>"+ jo);
        //jo=>{"code":"10000","data":{"expiresIn":"31536000","appRefreshToken":"201912BBaf0714216b664ebfb33ae41260598X27",
       // "appAuthToken":"201912BB7ddf4a1016674b2ba31041f70105fX27","reExpiresIn":"32140800","userId":"2088002123336273",
       // "authAppId":"2019120569622857"},"success":true}
       //jo=>{"code":"10000","data":{"expiresIn":"31536000","appRefreshToken":"201912BBaf0714216b664ebfb33ae41260598X27",
       // "appAuthToken":"201912BB7ddf4a1016674b2ba31041f70105fX27","reExpiresIn":"32140800","userId":"2088002123336273",
       // "authAppId":"2019120569622857"},"success":true}

       //2019-12-06 14:29:06 2019-12-06 14:29:06 info:
       // jo=>{"code":"10000","data":{"expiresIn":"31536000","appRefreshToken":"201912BBf17117f29f0c49fa82a0ee8abb369F96","appAuthToken":"201912BBbcc767843e584bec8dd0747867819X96","reExpiresIn":"32140800","userId":"2088431307185963","authAppId":"2019012863166278"},"success":true}

   }

    @Test
    public void testRefund() throws AlipayApiException {
        //退款
//        String outTradeNo ="201912101733375503745463060";
        String outTradeNo ="201912101744278466745463060";
        Map jo = new AlipaySubmit().refund(appAuthToken,outTradeNo,"0.01","1");
        System.out.println("result="+jo);
        //2019-12-05 9:52
        //{"success":true,"code":"10000","msg":"Success","data":"2019120522001436275741299784"}
        //2019-12-05 14:44
        //{"msg":"Success","code":"10000","data":"2019120522001436275741453407","success":true}
    }

    @Test
    public void testRefundQuery() throws AlipayApiException {
        //退款
        String outTradeNo ="2019033011111111118";
        Map jo = new AlipaySubmit().refundQuery(appAuthToken,outTradeNo,null);
        System.out.println("result="+jo);
        //2019-12-05 9:54
        //{"success":true,"code":"10000","msg":"Success"}
        //{"msg":"Success","code":"10000","success":true}
    }

    @Test
    public void testAuthUrl() throws AlipayApiException {
        //查询
        String  a = new AlipaySubmit().getAuthTokenUrl("123456");
        System.out.println("result="+a);
    }

    @Test
    public void testRedbag1() {
        AlipayMarketingCampaignCashCreateModel apiModel = new AlipayMarketingCampaignCashCreateModel();
        apiModel.setStartTime("2019-12-12");
        apiModel.setEndTime("2019-12-13");
        apiModel.setTotalNum("100");
        apiModel.setTotalMoney("1");


        Map a = new AlipaySubmit().redbag1(appAuthToken,apiModel);
        System.out.println("result="+a.toString());

    }
    @Test
    public void testRedbag4() {
        AlipayMarketingCampaignCashTriggerModel apiModel = new AlipayMarketingCampaignCashTriggerModel();
        //现金活动号
        apiModel.setCrowdNo("2088431307185963123123");
        apiModel.setUserId("2088431307185963");


        //查询
//        Map a = new AlipaySubmit().redbag4(appAuthToken,apiModel);
//        System.out.println("result="+a.toString());

        //result={code=0000, data={"alipay_marketing_campaign_cash_trigger_response":
        // {"code":"20001","msg":"Insufficient Token Permissions","sub_code":"aop.invalid-app-auth-token-no-api","sub_msg":"商户未授权当前接口"},
        // "sign":"Wjq0Di0W+qbrbwM6TtAMmkJdObWKx+Rso1FI9wab6aIFO2W9UUCeYyITdEIIU/vInJPUGx6gFtIc3kLUWvgOApKE0j/nKTtyGdSDY94OkDi0hg0NnkHiE2pMhmpVs+yiF1a4d5/6Hk+81WPPCpzDKGthURxVl62nGceDbuc0uXaQNtpibndqhzX82Q4QwDuVKp95W5gVSHTOXkWSZ39X7m1ekl5t/jLqzWUEFJ10WOrRtJLbs2fbRph22DYZMihadYLZg7UTmeaubrlAUudWL4VopLtCEOh8e2pPBdA0vBUIDOcV3tL2SvQTuWZeWrvGrxgtVozd+LJYB/qUzu7NUA=="}, success=true}
    }
}
