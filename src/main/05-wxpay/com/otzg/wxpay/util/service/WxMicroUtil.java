package com.otzg.wxpay.util.service;

import com.otzg.log.util.LogUtil;
import com.otzg.util.CheckUtil;
import com.otzg.util.FuncUtil;
import com.otzg.wxpay.entity.WxMicroAccount;
import com.otzg.wxpay.util.*;
import com.otzg.wxpay.util.sdk.WXPayConstants;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 子商户入驻
 *
 * @Author G.
 * @Date 2019/11/29 0029 下午 1:41
 */
public class WxMicroUtil {


    //小微商户入驻数据
    private final Map getMicroSubmitData(WxMicroAccount wxMicroAccount) {
        try {
            Map<String, String> paramMap = new TreeMap<>();
            //接口版本号
            paramMap.put("version", "3.0");
            //平台证书序列号
            paramMap.put("cert_sn", "749535475D89DF9ECF413A6CA05F74FF4CEDDCB8");
            paramMap.put("mch_id", WXPayConfig.getMchId());
            paramMap.put("nonce_str", FuncUtil.getNonce("nonce", 16));

            //业务申请编号
            paramMap.put("business_code", wxMicroAccount.getBusinessCode());
            //身份证人像面照片
            paramMap.put("id_card_copy", wxMicroAccount.getIdCardCopy());
            //身份证国徽面照片
            paramMap.put("id_card_national", wxMicroAccount.getIdCardNational());
            //身份证姓名
            paramMap.put("id_card_name", RSAUtil.rsaEncrypt(wxMicroAccount.getIdCardName()));
            //身份证号码
            paramMap.put("id_card_number", RSAUtil.rsaEncrypt(wxMicroAccount.getIdCardNumber()));
            //身份证有效期限
            paramMap.put("id_card_valid_time", wxMicroAccount.getIdCardValidTime());
            //开户名称
            paramMap.put("account_name", RSAUtil.rsaEncrypt(wxMicroAccount.getAccountName()));
            //开户银行
            paramMap.put("account_bank", wxMicroAccount.getAccountBank());
            //开户银行省市编码
            paramMap.put("bank_address_code", wxMicroAccount.getBankAddressCode());
            //银行账号
            paramMap.put("account_number", RSAUtil.rsaEncrypt(wxMicroAccount.getAccountNumber()));
            //门店名称
            paramMap.put("store_name", wxMicroAccount.getStoreName());
            //门店省市编码
            paramMap.put("store_address_code", wxMicroAccount.getStoreAddressCode());
            //门店街道名称
            paramMap.put("store_street", wxMicroAccount.getStoreStreet());
            //门店门口照片
            paramMap.put("store_entrance_pic", wxMicroAccount.getStoreEntrancePic());
            //店内环境照片
            paramMap.put("indoor_pic", wxMicroAccount.getIndoorPic());
            //商户简称
            paramMap.put("merchant_shortname", wxMicroAccount.getMerchantShortName());
            //客服电话
            paramMap.put("service_phone", wxMicroAccount.getServicePhone());
            //售卖商品/提供服务描述
            paramMap.put("product_desc", wxMicroAccount.getProductDesc());
            //费率
            paramMap.put("rate", wxMicroAccount.getRate());
            //超级管理员姓名
            paramMap.put("contact", RSAUtil.rsaEncrypt(wxMicroAccount.getContact()));
            //手机号
            paramMap.put("contact_phone", RSAUtil.rsaEncrypt(wxMicroAccount.getContactPhone()));
            paramMap.put("contact_email", RSAUtil.rsaEncrypt(wxMicroAccount.getContactEmail()));
            //签名类型
            paramMap.put("sign_type", "HMAC-SHA256");
            //签名
            paramMap.put("sign", SHAUtil.wechatCertficatesSignBySHA256(paramMap, WXPayConfig.getKey()));

            return paramMap;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.saveTradeLog("小微进件数据签名错误=>" + e.toString());
            return null;
        }
    }

    //小微商户进件提交,返回applyment_id
    public final String doMicroSubmit(WxMicroAccount wxMicroAccount) {
        //添加证书验证初始化
        RSAUtil.setCertPath(WXPayConfig.getPublicCertPath());

        Map map = getMicroSubmitData(wxMicroAccount);
        if (null == map) {
            return null;
        }

        String url = WXPayConstants.getMicroSubmitUrl();
        //提交请求
        String resultXml = PostUtil.doPostWithCert(url, XmlUtil.Map2Xml(map));

        //正常操作返回的结果
        Map<String, Object> resultMap = XmlUtil.parse(resultXml);
        System.out.println("result=>" + resultMap.toString());
        if (CheckUtil.isEmpty(resultMap)
                || !resultMap.get("return_code").equals("SUCCESS")
                || !resultMap.get("result_code").equals("SUCCESS")) {
            return null;
        }
        //返回applyment_id
        return resultMap.get("applyment_id").toString();

    }

    //初始化一个返回值
    final Map getInitMap() {
        Map map = new TreeMap();
        map.put("success", false);
        return map;
    }


    //图片上传API,获取media_id
    public final String doUploadImg(File file) {
        //文件md5hash
        String media_hash = MD5Util.md5HashCode(file);
        //失败直接返回
        if (CheckUtil.isEmpty(media_hash)) {
            return null;
        }

        //上传参数
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("mch_id", WXPayConfig.getMchId());
        paramMap.put("media_hash", media_hash);
        paramMap.put("sign_type", "HMAC-SHA256");
        paramMap.put("sign", SHAUtil.sha256Sign(paramMap, WXPayConfig.getKey()));
        System.out.println("Sign=>" + XmlUtil.Map2Xml(paramMap));

        //获取上传链接
        String url = WXPayConstants.getMicroUploadUrl();
        //获取返回值
        String resultXml = PostUtil.doPostFileDataWithCert(url, paramMap, "media", file);

        //正常操作返回的结果
        Map<String, Object> resultMap = XmlUtil.parse(resultXml);
        if (CheckUtil.isEmpty(resultMap)
                || !resultMap.get("return_code").equals("SUCCESS")
                || !resultMap.get("result_code").equals("SUCCESS")) {
            return null;
        }
        //{sign=9833D0D4FF18FC17D6ADE529AA593CD7, media_id=_qdPgxYeHjr20zZTSLO0ytytIW4WzQGYSjL9keglclA2AQyG_RoQhc87zbvCHC6B_zgcfMm7mi2zlOPEErgzFfy8z-koErfC1n0RAITyOCc, return_msg=OK, result_code=SUCCESS, return_code=SUCCESS}
        //返回media_id
        return resultMap.get("media_id").toString();
    }

    //查询入驻提交状态
    private final String getMicroSubmitQueryData(String businessCode) {
        Map<String, String> paramMap = new TreeMap<>();
        //接口版本号
        paramMap.put("version", "1.0");
        paramMap.put("mch_id", WXPayConfig.getMchId());
        paramMap.put("nonce_str", FuncUtil.getNonce("nonce", 16));
        //商户申请单号
//        paramMap.put("applyment_id", applyment_id);
        //业务申请编号
        paramMap.put("business_code",businessCode);
        paramMap.put("sign_type", "HMAC-SHA256");
        //签名
        paramMap.put("sign", SHAUtil.wechatCertficatesSignBySHA256(paramMap, WXPayConfig.getKey()));
        System.out.println("=>" + paramMap.toString());

        return XmlUtil.Map2Xml(paramMap);
    }

    //查询进件状态
    public final Map doMicroSubmitQuery(String businessCode) {
        Map result = getInitMap();
        String url = WXPayConstants.getMicroSubmitQueryUrl();
        String data = getMicroSubmitQueryData(businessCode);
        //提交请求
        String resultXml = PostUtil.doPostWithCert(url, data);
        //正常操作返回的结果
        Map<String, Object> resultMap = XmlUtil.parse(resultXml);
        //{nonce_str=zciinAeKFho1agXy, applyment_state=REJECTED,
        // sign=6D9A2C9C7B20453F16AD14DC76F3EDACE138D41E781B71BD571C4AD740A60F72,
        // applyment_state_desc=已驳回, return_msg=OK, result_code=SUCCESS,
        // audit_detail={"audit_detail":[{"param_name":"id_card_copy","reject_reason":"身份证正面识别失败，请重新上传"}]},
        // return_code=SUCCESS, applyment_id=2000002132954441}
        System.out.println("result map =>" + resultMap.toString());
        if (CheckUtil.isEmpty(resultMap)
                || !resultMap.get("return_code").equals("SUCCESS")
                || !resultMap.get("result_code").equals("SUCCESS")) {
            result.put("success",true);
        }
        result.put("data",resultMap);
        return result;
    }

}
