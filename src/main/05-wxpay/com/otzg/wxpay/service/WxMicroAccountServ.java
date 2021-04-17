package com.otzg.wxpay.service;

import com.otzg.wxpay.entity.WxMicroAccount;

import java.util.Map;

/**
 * 商户入驻相关接口
 *
 * @author G
 */
public interface WxMicroAccountServ {

    /**
     * 商户可以通过以下指引入驻成为小微商户：
     * 步骤一：服务商通过申请入驻接口提交你的小微商户入驻申请
     * 步骤二：服务商通过查询申请状态接口查询你的申请结果（建议提交申请后5分钟再进行查询）
     * 步骤三：小微商户本人签署协议（只能本人微信号扫码签约）
     * 提醒：你可能需要的开户银行对照表、省市区编码可前往对照表查看
     */

    /**
     * 小微商户进件流程
     * 1、服务商收集经营者身份证、经营者银行卡、门店照片等基本资料。
     * 2、服务商调用小微商户进件接口，提交商户基本资料。
     * 3、平台高效完成资料审核，并通过查询状态接口返回签约二维码
     * 4、服务商引导经营者使用微信扫码完成签约
     * 5、服务商现场调试设备，完成首单交易
     */
    int saveMicroAccount(WxMicroAccount wxMicroAccount);


    Map query(String unitId);
}
