package com.otzg.wxpay.util.sdk;

import com.otzg.util.FuncUtil;
import com.otzg.wxpay.util.XmlUtil;
import com.otzg.wxpay.util.XmlUtils;
import com.otzg.wxpay.util.service.SignType;
import com.otzg.wxpay.util.service.WXPayConfig;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Map;


public class WXPayRequest {
    private WXPayConfig config;
    public WXPayRequest(WXPayConfig config){
        this.config = config;
    }

    /**
     * 向 Map 中添加 appid、mch_id、nonce_str、sign_type、sign <br>
     * 该函数适用于商户适用于统一下单等接口，不适用于红包、代金券接口
     *
     * @param reqData
     * @return
     * @throws Exception
     */
    private String fillRequestData(Map<String, String> reqData) throws Exception {
        reqData.put("appid", config.getAppId());
        reqData.put("mch_id", config.getMchId());
        reqData.put("nonce_str", WXPayUtil.generateNonceStr());
        if (SignType.HMACSHA256.name().equals(config.getSignType())) {
            reqData.put("sign_type", WXPayConstants.HMACSHA256);
        }
        reqData.put("sign", WXPayUtil.generateSignature(reqData, config.getKey(), config.getSignType()));
        System.out.println("payData=>" + XmlUtil.Map2Xml(reqData));
        return XmlUtil.Map2Xml(reqData);
    }

    /**
     * 向 Map 中添加 mch_id、nonce_str、sign_type、sign <br>
     * 满足有些接口不能传appid的情况
     *  @param reqData
     * @return
     * @throws Exception
     */
    private String fillRequestDataNoAppId(Map<String, String> reqData) throws Exception {
        reqData.put("mch_id", config.getMchId());
        reqData.put("nonce_str", WXPayUtil.generateNonceStr());
        if (SignType.HMACSHA256.name().equals(config.getSignType())) {
            reqData.put("sign_type", WXPayConstants.HMACSHA256);
        }
        reqData.put("sign", WXPayUtil.generateSignature(reqData, config.getKey(), config.getSignType()));
        System.out.println("payData=>" + XmlUtil.Map2Xml(reqData));
        return XmlUtil.Map2Xml(reqData);
    }


    /**
     * 请求，只请求一次，不做重试
     * @param data
     * @param useCert 是否使用证书，针对退款、撤销等操作
     * @return
     * @throws Exception
     */
    private String requestOnce(String data, boolean useCert) throws Exception {
        BasicHttpClientConnectionManager connManager;
        if (useCert) {
            // 证书
            char[] password = config.getMchId().toCharArray();
            InputStream certStream = config.getCertStream();
            KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(certStream, password);

            // 实例化密钥库 & 初始化密钥工厂
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(ks, password);

            // 创建 SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());

            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
                    sslContext,
                    new String[]{"TLSv1"},
                    null,
                    new DefaultHostnameVerifier());

            connManager = new BasicHttpClientConnectionManager(
                    RegistryBuilder.<ConnectionSocketFactory>create()
                            .register("http", PlainConnectionSocketFactory.getSocketFactory())
                            .register("https", sslConnectionSocketFactory)
                            .build(),
                    null,
                    null,
                    null
            );
        }
        else {
            connManager = new BasicHttpClientConnectionManager(
                    RegistryBuilder.<ConnectionSocketFactory>create()
                            .register("http", PlainConnectionSocketFactory.getSocketFactory())
                            .register("https", SSLConnectionSocketFactory.getSocketFactory())
                            .build(),
                    null,
                    null,
                    null
            );
        }

        HttpClient httpClient = HttpClientBuilder.create()
                .setConnectionManager(connManager)
                .build();

        HttpPost httpPost = new HttpPost(config.getUrl());

        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(config.socketTimeout).setConnectTimeout(config.connectTimeout).build();
        httpPost.setConfig(requestConfig);

        StringEntity postEntity = new StringEntity(data, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.addHeader("User-Agent", WXPayConstants.USER_AGENT + " " + config.getMchId());
        httpPost.setEntity(postEntity);

        HttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity httpEntity = httpResponse.getEntity();
        return EntityUtils.toString(httpEntity, "UTF-8");

    }


    private String request(String data, boolean useCert) throws Exception {
        String uuid = FuncUtil.getNonce("nonce", 16);
        Exception exception = null;
        long elapsedTimeMillis = 0;
        long startTimestampMs = WXPayUtil.getCurrentTimestampMs();
        boolean firstHasDnsErr = false;
        boolean firstHasConnectTimeout = false;
        boolean firstHasReadTimeout = false;
        IWXPayDomain.DomainInfo domainInfo = config.getWXPayDomain().getDomain(config);
        if(domainInfo == null){
            throw new Exception("WXPayConfig.getWXPayDomain().getDomain() is empty or null");
        }
        try {
            String result = requestOnce(data,useCert);
            elapsedTimeMillis = WXPayUtil.getCurrentTimestampMs()-startTimestampMs;
            config.getWXPayDomain().report(domainInfo.domain, elapsedTimeMillis, null);
            WXPayReport.getInstance(config).report(
                    uuid,
                    elapsedTimeMillis,
                    domainInfo.domain,
                    domainInfo.primaryDomain,
                    config.connectTimeout,
                    config.httpReadTimeout,
                    firstHasDnsErr,
                    firstHasConnectTimeout,
                    firstHasReadTimeout);
            return result;
        }
        catch (UnknownHostException ex) {  // dns 解析错误，或域名不存在
            exception = ex;
            firstHasDnsErr = true;
            elapsedTimeMillis = WXPayUtil.getCurrentTimestampMs()-startTimestampMs;
            WXPayUtil.getLogger().warn("UnknownHostException for domainInfo {}", domainInfo);
            WXPayReport.getInstance(config).report(
                    uuid,
                    elapsedTimeMillis,
                    domainInfo.domain,
                    domainInfo.primaryDomain,
                    config.connectTimeout,
                    config.httpReadTimeout,
                    firstHasDnsErr,
                    firstHasConnectTimeout,
                    firstHasReadTimeout
            );
        }
        catch (ConnectTimeoutException ex) {
            exception = ex;
            firstHasConnectTimeout = true;
            elapsedTimeMillis = WXPayUtil.getCurrentTimestampMs()-startTimestampMs;
            WXPayUtil.getLogger().warn("connect timeout happened for domainInfo {}", domainInfo);
            WXPayReport.getInstance(config).report(
                    uuid,
                    elapsedTimeMillis,
                    domainInfo.domain,
                    domainInfo.primaryDomain,
                    config.connectTimeout,
                    config.httpReadTimeout,
                    firstHasDnsErr,
                    firstHasConnectTimeout,
                    firstHasReadTimeout
            );
        }
        catch (SocketTimeoutException ex) {
            exception = ex;
            firstHasReadTimeout = true;
            elapsedTimeMillis = WXPayUtil.getCurrentTimestampMs()-startTimestampMs;
            WXPayUtil.getLogger().warn("timeout happened for domainInfo {}", domainInfo);
            WXPayReport.getInstance(config).report(
                    uuid,
                    elapsedTimeMillis,
                    domainInfo.domain,
                    domainInfo.primaryDomain,
                    config.connectTimeout,
                    config.httpReadTimeout,
                    firstHasDnsErr,
                    firstHasConnectTimeout,
                    firstHasReadTimeout);
        }
        catch (Exception ex) {
            exception = ex;
            elapsedTimeMillis = WXPayUtil.getCurrentTimestampMs()-startTimestampMs;
            WXPayReport.getInstance(config).report(
                    uuid,
                    elapsedTimeMillis,
                    domainInfo.domain,
                    domainInfo.primaryDomain,
                    config.connectTimeout,
                    config.httpReadTimeout,
                    firstHasDnsErr,
                    firstHasConnectTimeout,
                    firstHasReadTimeout);
        }
        config.getWXPayDomain().report(domainInfo.domain, elapsedTimeMillis, exception);
        throw exception;
    }

    public Map<String, Object> request(Map<String, String> data) throws Exception {
        return XmlUtil.parse(request(this.fillRequestData(data),false));
    }

    public Map<String, Object> requestCert(Map<String, String> data) throws Exception {
        //返回多级的xml
        return XmlUtils.parse(request(this.fillRequestData(data),true));

//        return XmlUtil.parse(request(this.fillRequestData(data),true));
    }

    public Map<String, Object> requestCertNoAppId(Map<String, String> data) throws Exception {
        return XmlUtil.parse(request(this.fillRequestDataNoAppId(data),true));
    }

    /**
     * 提交刷卡支付，针对软POS，尽可能做成功
     * 内置重试机制，最多60s
     * @param reqData
     * @return
     * @throws Exception
     */
    public Map<String, Object> requestTimes(Map<String, String> reqData) throws Exception {
        int remainingTimeMs = 60*1000;
        long startTimestampMs = 0;
        Map<String, Object> lastResult = null;
        Exception lastException = null;

        while (true) {
            startTimestampMs = WXPayUtil.getCurrentTimestampMs();
            int readTimeoutMs = remainingTimeMs - config.connectTimeout;
            if (readTimeoutMs > 1000) {
                try {
                    lastResult = request(reqData);
                    String returnCode = lastResult.get("return_code").toString();
                    if (returnCode.equals("SUCCESS")) {
                        String resultCode = lastResult.get("result_code").toString();
                        String errCode = lastResult.get("err_code").toString();
                        if (resultCode.equals("SUCCESS")) {
                            break;
                        }else {
                            // 看错误码，若支付结果未知，则重试提交刷卡支付
                            if (errCode.equals("SYSTEMERROR") || errCode.equals("BANKERROR") || errCode.equals("USERPAYING")) {
                                remainingTimeMs = remainingTimeMs - (int)(WXPayUtil.getCurrentTimestampMs() - startTimestampMs);
                                if (remainingTimeMs <= 100) {
                                    break;
                                }else {
                                    WXPayUtil.getLogger().info("microPayWithPos: try micropay again");
                                    if (remainingTimeMs > 5*1000) {
                                        Thread.sleep(5*1000);
                                    }else {
                                        Thread.sleep(1*1000);
                                    }
                                    continue;
                                }
                            }else {
                                break;
                            }
                        }
                    }else {
                        break;
                    }
                }catch (Exception ex) {
                    lastResult = null;
                    lastException = ex;
                }
            }else {
                break;
            }
        }

        if (lastResult == null) {
            throw lastException;
        }
        else {
            return lastResult;
        }
    }


    /**
     * 微信坑爹接口不能传appid
     * @param data
     * @return
     * @throws Exception
     */
    public Map<String, Object> requestNoAppId(Map<String, String> data) throws Exception {
        return XmlUtil.parse(request(this.fillRequestDataNoAppId(data),false));
    }

//    /**
//     * 判断xml数据的sign是否有效，必须包含sign字段，否则返回false。
//     *
//     * @param reqData 向wxpay post的请求数据
//     * @return 签名是否有效
//     * @throws Exception
//     */
//    public boolean isResponseSignatureValid(Map<String, String> reqData) throws Exception {
//        // 返回数据的签名方式和请求中给定的签名方式是一致的
//        return WXPayUtil.isSignatureValid(reqData, this.config.getKey(), config.signType);
//    }
//
//    /**
//     * 处理 HTTPS API返回数据，转换成Map对象。return_code为SUCCESS时，验证签名。
//     * @param xmlStr API返回的XML格式数据
//     * @return Map类型数据
//     * @throws Exception
//     */
//    public Map<String, String> processResponseXml(String xmlStr) throws Exception {
//        String RETURN_CODE = "return_code";
//        String return_code;
//        Map<String, String> respData = WXPayUtil.xmlToMap(xmlStr);
//        if (respData.containsKey(RETURN_CODE)) {
//            return_code = respData.get(RETURN_CODE);
//        }
//        else {
//            throw new Exception(String.format("No `return_code` in XML: %s", xmlStr));
//        }
//
//        if (return_code.equals(WXPayConstants.FAIL)) {
//            return respData;
//        }
//        else if (return_code.equals(WXPayConstants.SUCCESS)) {
//            if (this.isResponseSignatureValid(respData)) {
//                return respData;
//            }
//            else {
//                throw new Exception(String.format("Invalid sign value in XML: %s", xmlStr));
//            }
//        }
//        else {
//            throw new Exception(String.format("return_code value %s is invalid in XML: %s", return_code, xmlStr));
//        }
//    }


}
