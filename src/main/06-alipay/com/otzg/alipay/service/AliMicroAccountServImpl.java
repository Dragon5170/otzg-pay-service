package com.otzg.alipay.service;

import com.otzg.alipay.dao.AliMicroAccountDao;
import com.otzg.alipay.dao.AliMicroAccountLogDao;
import com.otzg.alipay.entity.AliMicroAccount;
import com.otzg.alipay.entity.AliMicroAccountLog;
import com.otzg.alipay.util.AlipayUtil;
import com.otzg.base.AbstractServ;
import com.otzg.pay.service.PayChannelAccountServ;
import com.otzg.util.DateUtil;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

/**
 * @Author G.
 * @Date 2019/12/9 0009 上午 10:19
 */
@Service
public class AliMicroAccountServImpl extends AbstractServ implements AliMicroAccountServ {
    @Autowired
    AliMicroAccountDao aliMicroAccountDao;
    @Autowired
    AliMicroAccountLogDao aliMicroAccountLogDao;

    @Autowired
    PayChannelAccountServ payChannelAccountServ;


    @Override
    @Transactional
    public int createMicroAccount(String unitId,String appId,String authCode) {
        Map jo = new AlipayUtil().getOpenAuthTokenAppByCode(authCode);
        P("po=>"+jo);
        if(jo.get("success").equals(false)
                || null == jo.get("data")){
            createAliMicroAccountLog(false,unitId,jo.get("msg").toString());
            return 1;
        }

        //返回的结果
        // jo=>{"code":"10000","data":{"expiresIn":"31536000",
        // "appRefreshToken":"201912BBdea8490b9f8045678af034db396f8A27",
        // "appAuthToken":"201912BB9ad9d86023494151b7e7c8d66f434X27",
        // "reExpiresIn":"32140800","userId":"2088002123336273",
        // "authAppId":"2019120569622857"},"success":true}


        //获取成功的内容
        jo= (Map) jo.get("data");

        //日志
        P("获取授权码成功:{unitId=%s,authToken=%s}",unitId,jo.get("appAuthToken"));


        //开始创建支付宝平台账户
        RLock lock = redisson.getLock(unitId);
        try{

            AliMicroAccount aliMicroAccount;

            Optional<AliMicroAccount> op = aliMicroAccountDao.findByUnitId(unitId);
            if(op.isPresent()){
                aliMicroAccount = op.get();
            }else{
                aliMicroAccount = new AliMicroAccount();
                aliMicroAccount.setId(getId());
                aliMicroAccount.setCreateTime(DateUtil.now());
                aliMicroAccount.setUnitId(unitId);
            }

            aliMicroAccount.setAppAuthToken(jo.get("appAuthToken").toString());
            aliMicroAccount.setAppRefreshToken(jo.get("appRefreshToken").toString());
            aliMicroAccount.setUserId(jo.get("userId").toString());
            aliMicroAccount.setAuthAppId(jo.get("authAppId").toString());
            aliMicroAccount.setAppId(appId);

            aliMicroAccount.setAuthStart(DateUtil.now().getTime());
            aliMicroAccount.setAuthEnd(Long.parseLong(jo.get("reExpiresIn").toString()));  //authToken 有效期一年

            aliMicroAccount.setUpdateTime(DateUtil.now());
            aliMicroAccount.setStatus(1);
            aliMicroAccountDao.save(aliMicroAccount);

            //创建支付宝支付渠道账户
            payChannelAccountServ.setPayChannelAccount(unitId,"alipay",jo.get("userId").toString(),jo.get("appAuthToken").toString());

            //创建支付账户记录
            createAliMicroAccountLog(true,unitId,jo.toString());
            return 0;
        }catch (Exception e){
            rollBack();
            e.printStackTrace();
            P("创建支付渠道账户失败");
            return 2;
        }finally {
            lock.unlock();
        }
    }

    void createAliMicroAccountLog(boolean r,String unitId,String detail){
        AliMicroAccountLog aliMicroAccountLog = new AliMicroAccountLog();
        aliMicroAccountLog.setId(getId());
        aliMicroAccountLog.setUnitId(unitId);
        aliMicroAccountLog.setCreateTime(DateUtil.now());
        if(r){
            aliMicroAccountLog.setStatus(1);
        }else{
            aliMicroAccountLog.setStatus(0);
        }
        aliMicroAccountLog.setDetail(detail);
        aliMicroAccountLogDao.save(aliMicroAccountLog);
    }
}
