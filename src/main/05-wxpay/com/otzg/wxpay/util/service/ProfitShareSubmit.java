package com.otzg.wxpay.util.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.otzg.util.ResultUtil;
import com.otzg.wxpay.util.sdk.WXPayConstants;
import com.otzg.wxpay.util.sdk.WXPayRequest;
import com.otzg.wxpay.util.sdk.WXPayUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 分账相关
 * <p>
 * 说明：实现分账只是在普通支付下单接口中新增了一个分账参数profit_sharing，其他与普通支付方式完全相同。
 * 目前支持付款码支付、JSAPI支付、Native支付、APP支付、小程序支付、H5支付、委托代扣。
 * <p>
 * 分账方：在服务商模式下，分账方是商品或者服务的提供方，这里指特约商户
 * 分账接收方：特约商户上游的供应方，合作的商户，商户的员工，用户等接收分账资金的商户或个人
 */
public class ProfitShareSubmit {

    /**
     * 请求单次分账
     * <xml>
     * <appid>wx2421b1c4370ec43b</appid>
     * <mch_id>10000100</mch_id>
     * <sub_appid>wx2203b1494370e08cm</sub_appid>
     * <sub_mch_id>1415701182</sub_mch_id>
     * <nonce_str>6cefdb308e1e2e8aabd48cf79e546a02</nonce_str>
     * <out_order_no>P20150806125346</out_order_no>
     * <transaction_id>4006252001201705123297353072</transaction_id>
     * <sign>FE56DD4AA85C0EECA82C35595A69E153</sign>
     * <sign_type>HMAC-SHA256</sign_type>
     * <receivers>
     * [
     * {
     * "type": "MERCHANT_ID",
     * "account":"190001001",
     * "amount":100,
     * "description": "分到商户"
     * },
     * {
     * "type": "PERSONAL_WECHATID",
     * "account":"86693952",
     * "amount":888,
     * "description": "分到个人"
     * }
     * ]
     * </receivers>
     * </xml>
     */
    public final static Map<String, String> profitSharingData(String subMchId,
                                                              String transactionId,
                                                              String outOrderNo,
                                                              JSONArray receivers) {
        Map<String, String> paramMap = new HashMap<>();
        //子商户号
        paramMap.put("sub_mch_id", subMchId);
        paramMap.put("transaction_id", transactionId);
        //服务商系统内部的分账单号，在服务商系统内部唯一（单次分账、多次分账、完结分账应使用不同的商户分账单号），同一分账单号多次请求等同一次。只能是数字、大小写字母_-|*@
        paramMap.put("out_order_no", outOrderNo);
        //随机数
        paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
        //分账接收方列表，不超过50个json对象，不能设置出资子商户作为分账接受方
        paramMap.put("receivers", receivers.toString());

        return paramMap;
    }

    public final static Map profitSharing(Map<String, String> map) throws Exception {
        WXPayConfig wxPayConfig = new WXPayConfig(SignType.HMACSHA256.name(), true);
        wxPayConfig.setUrl(WXPayConstants.getProfitSharingUrl());
        WXPayRequest wxPayRequest = new WXPayRequest(wxPayConfig);
        //内置60秒的轮询
        Map<String, Object> result = wxPayRequest.requestCert(map);
        System.out.println("调用结果=" + result);
        //调用结果={transaction_id=4164583679620190411182047672979, nonce_str=LbQjExfMQquvNd7A7hapTT4SzjesdobU, bank_type=CMC, openid=085e9858e9914da9a0da5522a, sign=64DA7555B343847E35A316B3E1E10322, err_code=SUCCESS, err_code_des=ok, return_msg=OK, fee_type=CNY, mch_id=1519839251, cash_fee=1, device_info=001, out_trade_no=134520174432770725, cash_fee_type=CNY, total_fee=1, appid=wx4dc8cb7f0eb12288, trade_type=MICROPAY, result_code=SUCCESS, time_end=20190411182047, attach=测试刷卡付, is_subscribe=Y, return_code=SUCCESS}
        if (result.get("return_code") != null
                && result.get("return_code").equals("SUCCESS")
                && result.get("result_code") != null
                && result.get("result_code").equals("SUCCESS")
        ) {
            JSONObject jo = new JSONObject();
            jo.put("transaction_id", result.get("transaction_id"));
            jo.put("openid", result.get("openid"));
            return ResultUtil.getJson(true, result.get("result_code").toString(), result.get("return_msg").toString(), jo);
        } else {
            return ResultUtil.getJson(false, result.get("result_code").toString(), result.get("err_code_des").toString());
        }
    }


    /**
     * 分账结果查询
     * <xml>
     * <mch_id>10000100</mch_id>
     * <sub_mch_id>1415701182</sub_mch_id>
     * <nonce_str>6cefdb308e1e2e8aabd48cf79e546a02</nonce_str>
     * <out_order_no>P20150806125346</out_order_no>
     * <transaction_id>4006252001201705123297353072</transaction_id>
     * <sign>FE56DD4AA85C0EECA82C35595A69E153</sign>
     * <sign_type>HMAC-SHA256</sign_type>
     * </xml>
     *
     * @param subMchId
     * @param transactionId
     * @param outOrderNo
     * @return
     */
    public final static Map<String, String> profitSharingQueryData(String subMchId,
                                                                   String transactionId,
                                                                   String outOrderNo) {
        Map<String, String> paramMap = new HashMap<>();
        //子商户号
        paramMap.put("sub_mch_id", subMchId);
        paramMap.put("transaction_id", transactionId);
        //服务商系统内部的分账单号，在服务商系统内部唯一（单次分账、多次分账、完结分账应使用不同的商户分账单号），同一分账单号多次请求等同一次。只能是数字、大小写字母_-|*@
        paramMap.put("out_order_no", outOrderNo);
        //随机数
        paramMap.put("nonce_str", WXPayUtil.generateNonceStr());

        return paramMap;
    }

    /**
     * 服务商分账结果查询
     *
     * @param map
     * @return
     * @throws Exception
     */
    public final static Map profitSharingQuery(Map<String, String> map) throws Exception {
        WXPayConfig wxPayConfig = new WXPayConfig(SignType.HMACSHA256.name());
        wxPayConfig.setUrl(WXPayConstants.getProfitSharingQueryUrl());
        WXPayRequest wxPayRequest = new WXPayRequest(wxPayConfig);

        Map<String, Object> result = wxPayRequest.requestNoAppId(map);
        System.out.println("调用结果=" + result);
        if (result.get("return_code") != null
                && result.get("return_code").equals("SUCCESS")
                && result.get("result_code") != null
                && result.get("result_code").equals("SUCCESS")
        ) {
            JSONObject jo = new JSONObject();
            jo.put("transaction_id", result.get("transaction_id"));
            jo.put("openid", result.get("openid"));
            return ResultUtil.getJson(true, result.get("result_code").toString(), result.get("return_msg").toString(), jo);
        } else {
            return ResultUtil.getJson(false, result.get("result_code").toString(), result.get("err_code_des").toString());
        }
    }

    /**
     * 添加分账接收方
     *
     * <xml>
     * <mch_id>10000100</mch_id>
     * <sub_mch_id>1415701182</sub_mch_id>
     * <appid>wx2421b1c4370ec43b</appid>
     * <sub_appid>wx2203b1494370e08cm</sub_appid>
     * <nonce_str>6cefdb308e1e2e8aabd48cf79e546a02</nonce_str>
     * <sign>ABC6DD4AA85C0EECA82C35595A69EFGH</sign>
     * <sign_type>HMAC-SHA256</sign_type>
     * <receiver>
     * {
     * "type": "MERCHANT_ID",
     * "account": "190001001",
     * "name": "示例商户全称",
     * "relation_type": "STORE_OWNER"
     * }
     * </receiver>
     * </xml>
     */
    public final static Map<String, String> addProfitSharingReceiverData(String subMchId,
                                                                         JSONObject receiver) {
        Map<String, String> paramMap = new HashMap<>();
        //子商户号
        paramMap.put("sub_mch_id", subMchId);
        //随机数
        paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
        //分账者
        paramMap.put("receiver", receiver.toString());

        return paramMap;
    }

    /**
     * 服务商分账
     * <p>
     * 服务商按指令分账主要是用于服务商帮助特约商户完成订单收单成功以后的资金分配。
     *
     * @param map
     * @return
     * @throws Exception
     */
    public final static Map addProfitSharingReceiver(Map<String, String> map) throws Exception {
        WXPayConfig wxPayConfig = new WXPayConfig(SignType.HMACSHA256.name());
        wxPayConfig.setUrl(WXPayConstants.getProfitSharingAddReceiverUrl());
        WXPayRequest wxPayRequest = new WXPayRequest(wxPayConfig);
        //内置60秒的轮询
        Map<String, Object> result = wxPayRequest.request(map);
        System.out.println("调用结果=" + result);
        //调用结果={transaction_id=4164583679620190411182047672979, nonce_str=LbQjExfMQquvNd7A7hapTT4SzjesdobU, bank_type=CMC, openid=085e9858e9914da9a0da5522a, sign=64DA7555B343847E35A316B3E1E10322, err_code=SUCCESS, err_code_des=ok, return_msg=OK, fee_type=CNY, mch_id=1519839251, cash_fee=1, device_info=001, out_trade_no=134520174432770725, cash_fee_type=CNY, total_fee=1, appid=wx4dc8cb7f0eb12288, trade_type=MICROPAY, result_code=SUCCESS, time_end=20190411182047, attach=测试刷卡付, is_subscribe=Y, return_code=SUCCESS}
        if (result.get("return_code") != null
                && result.get("return_code").equals("SUCCESS")
                && result.get("result_code") != null
                && result.get("result_code").equals("SUCCESS")
        ) {
            JSONObject jo = new JSONObject();
            jo.put("transaction_id", result.get("transaction_id"));
            jo.put("openid", result.get("openid"));
            return ResultUtil.getJson(true, result.get("result_code").toString(), result.get("return_msg").toString(), jo);
        } else {
            return ResultUtil.getJson(false, result.get("result_code").toString(), result.get("err_code_des").toString());
        }
    }


    /**
     * 删除分账接收方
     *
     * <xml>
     * <mch_id>10000100</mch_id>
     * <sub_mch_id>1415701182</sub_mch_id>
     * <appid>wx2421b1c4370ec43b</appid>
     * <sub_appid>wx2203b1494370e08cm</sub_appid>
     * <nonce_str>6cefdb308e1e2e8aabd48cf79e546a02</nonce_str>
     * <sign>ABC6DD4AA85C0EECA82C35595A69EFGH</sign>
     * <sign_type>HMAC-SHA256</sign_type>
     * <receiver>
     * {
     * "type": "MERCHANT_ID",
     * "account": "190001001",
     * "name": "示例商户全称"
     * }
     * </receiver>
     * </xml>
     *
     * @param subMchId
     * @param receiver
     * @return
     */
    public final static Map<String, String> deleteProfitsharingReceiverData(String subMchId,
                                                                            JSONObject receiver) {
        Map<String, String> paramMap = new HashMap<>();
        //子商户号
        paramMap.put("sub_mch_id", subMchId);
        //随机数
        paramMap.put("nonce_str", WXPayUtil.generateNonceStr());
        //分账者
        paramMap.put("receiver", receiver.toString());

        return paramMap;
    }

    public final static Map deleteProfitsharingReceiver(Map<String, String> map) throws Exception {
        WXPayConfig wxPayConfig = new WXPayConfig(SignType.HMACSHA256.name());
        wxPayConfig.setUrl(WXPayConstants.getProfitSharingRemoveReceiverUrl());
        WXPayRequest wxPayRequest = new WXPayRequest(wxPayConfig);
        //内置60秒的轮询
        Map<String, Object> result = wxPayRequest.request(map);
        System.out.println("调用结果=" + result);
        //调用结果={transaction_id=4164583679620190411182047672979, nonce_str=LbQjExfMQquvNd7A7hapTT4SzjesdobU, bank_type=CMC, openid=085e9858e9914da9a0da5522a, sign=64DA7555B343847E35A316B3E1E10322, err_code=SUCCESS, err_code_des=ok, return_msg=OK, fee_type=CNY, mch_id=1519839251, cash_fee=1, device_info=001, out_trade_no=134520174432770725, cash_fee_type=CNY, total_fee=1, appid=wx4dc8cb7f0eb12288, trade_type=MICROPAY, result_code=SUCCESS, time_end=20190411182047, attach=测试刷卡付, is_subscribe=Y, return_code=SUCCESS}
        if (result.get("return_code") != null
                && result.get("return_code").equals("SUCCESS")
                && result.get("result_code") != null
                && result.get("result_code").equals("SUCCESS")
        ) {
            JSONObject jo = new JSONObject();
            jo.put("transaction_id", result.get("transaction_id"));
            jo.put("openid", result.get("openid"));
            return ResultUtil.getJson(true, result.get("result_code").toString(), result.get("return_msg").toString(), jo);
        } else {
            return ResultUtil.getJson(false, result.get("result_code").toString(), result.get("err_code_des").toString());
        }
    }


    String subMchId = "1525006091";
    //    String subMchId = "1521225291";
    String subAppId = "wxd8de5d37b6976b55"; //


    //    String openid = "olFJwwK3yub_nNNXXOhkTf07nYC0";
    String openid = "olFJwwNOyw5v5OpMq-2Ex959r5is"; //wzg


    //商户号下的公众号appid
    String gzhAppId = "wxd8de5d37b6976b55";

    @Test
    public void addReceiverTest() throws Exception {
        JSONObject jsonObject = new JSONObject();
        //MERCHANT_ID：商户ID PERSONAL_WECHATID：个人微信号PERSONAL_OPENID：个人openid（由父商户APPID转换得到）PERSONAL_SUB_OPENID: 个人sub_openid（由子商户APPID转换得到）
//        jsonObject.put("type", "PERSONAL_WECHATID");
        jsonObject.put("type", "MERCHANT_ID");
        jsonObject.put("account", "1523387771");
        jsonObject.put("name", "河南乐之途电子科技有限公司");
        jsonObject.put("relation_type", "STORE_OWNER");
        Map<String, String> payData = addProfitSharingReceiverData(subMchId, jsonObject);
        Map result = addProfitSharingReceiver(payData);
        System.out.println("result=" + result);

        //2019-11-21 09:15
        //{nonce_str=b625b1aeaa2e7545, receiver={"type":"PERSONAL_WECHATID","account":"olFJwwK3yub_nNNXXOhkTf07nYC0","relation_type":"STORE_OWNER"}, appid=wxa574b9142c67f42e, sign=72C79EAB30C832FDDB57FD2F0A64382AA12877B3CF104AEB986F247F9FE9343D, err_code=NOAUTH, result_code=FAIL, err_code_des=无分账权限, mch_id=1513549201, sub_mch_id=1525006091, return_code=SUCCESS}
        //result={"success":false,"code":"FAIL","msg":"无分账权限"}
        //2019-11-21 16:39
        //{nonce_str=054208d008ce6694, receiver={"type":"PERSONAL_WECHATID","account":"olFJwwNOyw5v5OpMq-2Ex959r5is","relation_type":"STORE_OWNER"}, appid=wxa574b9142c67f42e, sign=B849114A42C04F348877D3C6250D0904710CC2710739D248118AD4748CF91451, err_code=USER_NOT_EXIST, result_code=FAIL, err_code_des=微信用户不存在, mch_id=1513549201, sub_mch_id=1525006091, return_code=SUCCESS}
        //result={"success":false,"code":"FAIL","msg":"微信用户不存在"}

        //{nonce_str=b72b98153d575fb0, receiver={"type":"MERCHANT_ID","account":"1523387771","relation_type":"STORE_OWNER"}, appid=wxa574b9142c67f42e, sign=4204EFAE192FA74B2AC89AD985531B953773D22D889AD45984B03011DDCAC50F, err_code=INVALID_REQUEST, result_code=FAIL, err_code_des=分账接收商户全称不匹配, mch_id=1513549201, sub_mch_id=1525006091, return_code=SUCCESS}
        //result={"success":false,"code":"FAIL","msg":"分账接收商户全称不匹配"}

        //{nonce_str=6a85452c72572bb4, receiver={"type":"MERCHANT_ID","account":"1523387771","relation_type":"STORE_OWNER"}, appid=wxa574b9142c67f42e, sign=897AD47561CD53151F75E0F20888E8C1A91C29D88B9C3473197390B7CE6DD127, result_code=SUCCESS, mch_id=1513549201, sub_mch_id=1525006091, return_code=SUCCESS}
        //{nonce_str=4210f4bb3d7a8028, receiver={"type":"MERCHANT_ID","account":"1523387771","relation_type":"STORE_OWNER"}, appid=wxa574b9142c67f42e, sign=7C98FBE20CB8ECA3D330B2D7421D77CF953FC5D3877B7E73A8A8A30F6D7EA4CF, result_code=SUCCESS, mch_id=1513549201, sub_mch_id=1525006091, return_code=SUCCESS}
    }

    @Test
    public void deleteReceiverTest() throws Exception {
        JSONObject jsonObject = new JSONObject();
        //MERCHANT_ID：商户ID PERSONAL_WECHATID：个人微信号PERSONAL_OPENID：个人openid（由父商户APPID转换得到）PERSONAL_SUB_OPENID: 个人sub_openid（由子商户APPID转换得到）
//        jsonObject.put("type", "PERSONAL_WECHATID");
//        jsonObject.put("account", openid);
//        jsonObject.put("name", "示例商户全称");

        jsonObject.put("type", "MERCHANT_ID");
        jsonObject.put("account", "1523387771");
        jsonObject.put("name", "河南乐之途电子科技有限公司");

        Map<String, String> payData = deleteProfitsharingReceiverData(subMchId, jsonObject);
        Map result = deleteProfitsharingReceiver(payData);
        System.out.println("result=" + result);

        //2019-11-21 16:30
        //{nonce_str=eeee1bfe4c053919, receiver={"type":"PERSONAL_WECHATID","account":"olFJwwK3yub_nNNXXOhkTf07nYC0"}, appid=wxa574b9142c67f42e, sign=A7216E9BF793E133CF7BA20520A0E5BB4C2095BDA00AA93E9823E54CC6C3F028, err_code=NOAUTH, result_code=FAIL, err_code_des=无分账权限, mch_id=1513549201, sub_mch_id=1525006091, return_code=SUCCESS}

        //2019-11-21 17:06
        //{nonce_str=c7751c4f76b83802, receiver={"type":"MERCHANT_ID","account":"1523387771","name":"河南乐之途电子科技有限公司"}, appid=wxa574b9142c67f42e, sign=9075FB72DEEF316F04991AD0796A8CF9B1EF93E231D8A7B1FDBE281F098F2933, result_code=SUCCESS, mch_id=1513549201, sub_mch_id=1525006091, return_code=SUCCESS}
    }


    @Test
    public void profitSharingTest() throws Exception {
//        String transactionId = "4200000433201911201753279992";
        String transactionId = "4200000443201911210989941373";
//        String outOrderNo = "201915757673002";
        String outOrderNo = "2019112115757673002";
        JSONArray ja = new JSONArray();
        JSONObject jo = new JSONObject();
        //MERCHANT_ID：商户ID PERSONAL_WECHATID：个人微信号PERSONAL_OPENID：个人openid（由父商户APPID转换得到）PERSONAL_SUB_OPENID: 个人sub_openid（由子商户APPID转换得到）
//          {
//         "type": "MERCHANT_ID",
//         "account":"190001001",
//         "amount":100,
//         "description": "分到商户"
//};
        jo.put("type", "MERCHANT_ID");
        jo.put("account", "1523387771");
        jo.put("amount", 1);
        jo.put("description", "分到商户");
        ja.add(jo);
        Map<String, String> payData = profitSharingData(subMchId, transactionId, outOrderNo, ja);
        Map result = profitSharing(payData);
        System.out.println("result=" + result);
        //2019-11-21 17:35
        //{return_msg=分账金额未设置, return_code=FAIL}
        //{nonce_str=1a719c1c1c704b1e, appid=wxa574b9142c67f42e, sign=FB50241368A5CBEC6A67DEA121247716CB6EF9020AB614B454899E7426F51F49, err_code=CERT_ERROR, result_code=FAIL, err_code_des=证书已过期, mch_id=1513549201, sub_mch_id=1525006091, return_code=SUCCESS}
        //{nonce_str=ae955943052dee72, appid=wxa574b9142c67f42e, sign=7F9EA348B63058A20354142577EA59331A70B751B5BA4FF884A794226DA54EF5, err_code=CERT_ERROR, result_code=FAIL, err_code_des=证书已过期, mch_id=1513549201, sub_mch_id=1525006091, return_code=SUCCESS}

        //2019-12-3 10:05
        //{transaction_id=4200000443201911210989941373, nonce_str=4ec3eefdec090a78, appid=wxa574b9142c67f42e, sign=58E17CB77F351EA29F49A057547BDC257B3D81509E5243C4AA886324AFA72484, out_order_no=2019112115757673002, result_code=SUCCESS, mch_id=1513549201, sub_mch_id=1525006091, return_code=SUCCESS, order_id=30000104112019120213259965002}
    }

    @Test
    public void profitSharingQueryTest() throws Exception {
        String transactionId = "4200000443201911210989941373";
        String outOrderNo = "2019112115757673002";
        Map<String, String> payData = profitSharingQueryData(subMchId, transactionId, outOrderNo);
        Map result = profitSharingQuery(payData);
        System.out.println("result=" + result);
        //2019-11-21 17:26
        //{return_msg=transaction_id未设置, return_code=FAIL}
        //2019-12-3 10:05
        //{transaction_id=4200000443201911210989941373, nonce_str=4ca84170a5024192, receivers=[{"type":"MERCHANT_ID","account":"1523387771","amount":1,"description":"分到商户","result":"SUCCESS","finish_time":"20191202100539"},{"type":"MERCHANT_ID","account":"1525006091","amount":9,"description":"解冻给分账方","result":"SUCCESS","finish_time":"20191202100539"}], sign=325E6AAA04FBEA64236E8EEE645FB82F73E4C9ADAB85139BC7A604D40A9C216A, out_order_no=2019112115757673002, result_code=SUCCESS, mch_id=1513549201, sub_mch_id=1525006091, return_code=SUCCESS, order_id=30000104112019120213259965002, status=FINISHED}
    }
}
