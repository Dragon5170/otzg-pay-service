package com.otzg.alipay.service;

/**
 * @Author G.
 * @Date 2019/12/9 0009 上午 10:17
 */
public interface AliMicroAccountServ {
    /**
     * 根据authCode 获取authToken 并创建支付渠道账户
     *
     * @param unitId       绑定平台的单位id
     * @param appId        绑定平台的appId
     * @param authCode      授权code
     * @return
     */
    int createMicroAccount(String unitId,String appId,String authCode);
}
