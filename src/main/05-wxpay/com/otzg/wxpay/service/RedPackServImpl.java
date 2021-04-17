package com.otzg.wxpay.service;

import com.alibaba.fastjson.JSONObject;
import com.otzg.base.AbstractServ;
import com.otzg.base.Finder;
import com.otzg.base.Page;
import com.otzg.util.ResultUtil;
import com.otzg.pay.entity.PayChannelAccount;
import com.otzg.pay.service.PayChannelAccountServ;
import com.otzg.util.*;
import com.otzg.wxpay.dao.WxRedPackDao;
import com.otzg.wxpay.dao.WxRedPackLogDao;
import com.otzg.wxpay.dto.WxRedPackDto;
import com.otzg.wxpay.entity.WxRedPack;
import com.otzg.wxpay.entity.WxRedPackLog;
import com.otzg.wxpay.util.service.WxRedPackUtil;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

/**
 * @Author G.
 * @Date 2019/12/13 0013 上午 10:41
 */
@Service
public class RedPackServImpl extends AbstractServ implements RedPackServ {
    @Autowired
    WxRedPackDao wxRedPackDao;

    @Autowired
    WxRedPackLogDao wxRedPackLogDao;
    @Autowired
    private PayChannelAccountServ payChannelAccountServ;

    /**
     * 微信发红包是可重入接口
     * @param payAccountId
     * @param wxRedPackDto
     * @return
     */
    @Override
    @Transactional
    public int sendRedPack(Long payAccountId,WxRedPackDto wxRedPackDto) {
        Optional<WxRedPack> op = wxRedPackDao.findByRedPackOrderNo(wxRedPackDto.getRedPackOrderNo());
        //如果没有查到对应单号的红包
        if(!op.isPresent()){
            return sendFirst(payAccountId,wxRedPackDto);
        }

        //如果已经发送{-1:发送失败,0:发送中,1:已发送,2:已接收,3:已退款}
        if(op.get().getStatus()< 0
                || op.get().getStatus()>1){
            return op.get().getStatus();
        }

        //如果结果等于0或者等于1，需要再次查询确定
        new WxRedPackUtil().pay(op.get());
        return query(op.get().getRedPackOrderNo());
    }

    int sendFirst(Long payAccountId,WxRedPackDto wxRedPackDto){
        //获取支付渠道商户号
        PayChannelAccount payChannelAccount = payChannelAccountServ.findByAccountAndPayChannel(payAccountId, "wxpay");
        if (null == payChannelAccount
                || null == payChannelAccount.getPayChannelAccount()) {
            return -4;
        }

        //发红包金额
        BigDecimal amount = FuncUtil.getBigDecimalScale(new BigDecimal(wxRedPackDto.getTotalAmount()));
        PT("发红包金额="+amount);


        //如果微信账户余额不足返回
        if(payChannelAccount.getBalance().compareTo(amount)<0){
            return -5;
        }


        RLock lock = redisson.getLock(""+payChannelAccount.getId());
        try {
            boolean l = lock.tryLock(30, TimeUnit.SECONDS);
            if (!l) {
                P("redisson lock false");
                return -6;
            }

            String payRedPackOrderNo = getPayOrderNo(wxRedPackDto.getUnitId());

            WxRedPack wxRedPack = new WxRedPack();
            wxRedPack.setId(getId());
            wxRedPack.setSubMchId(payChannelAccount.getPayChannelAccount());
            wxRedPack.setPayRedPackOrderNo(payRedPackOrderNo);
            wxRedPack.setUnitId(wxRedPackDto.getUnitId());
            wxRedPack.setMemberId(wxRedPackDto.getMemberId());
            wxRedPack.setCreateTime(DateUtil.now());
            wxRedPack.setUpdateTime(DateUtil.now());
            wxRedPack.setStatus(WxRedPack.StatusType.WAIT.index);

            trans(wxRedPack,wxRedPackDto);
            wxRedPackDao.save(wxRedPack);

            //冻结支付渠道账户金额
            payChannelAccountServ.freezeBalance(payChannelAccount,amount);

            int r = new WxRedPackUtil().pay(wxRedPack);
            if(r==2){
                new WxRedPackUtil().pay(wxRedPack);
            }

            return query(wxRedPack.getRedPackOrderNo());

        }catch (Exception e){
            e.printStackTrace();
            return -7;
        }finally {
            lock.unlock();
        }
    }


    void trans(WxRedPack wxRedPack, WxRedPackDto wxRedPackDto) {
        wxRedPack.setActName(wxRedPackDto.getActName());
        wxRedPack.setMsgAppId(wxRedPackDto.getMsgAppId());
        wxRedPack.setActName(wxRedPackDto.getActName());
        wxRedPack.setRemark(wxRedPackDto.getRemark());
        wxRedPack.setReOpenId(wxRedPackDto.getReOpenId());
        wxRedPack.setSendName(wxRedPackDto.getSendName());
        wxRedPack.setAmount(new BigDecimal(wxRedPackDto.getTotalAmount()));
        wxRedPack.setTotalNum(wxRedPackDto.getTotalNum());
        wxRedPack.setClientIp(wxRedPackDto.getClientIp());
        wxRedPack.setWishing(wxRedPackDto.getWishing());
        wxRedPack.setRedPackOrderNo(wxRedPackDto.getRedPackOrderNo());
        wxRedPack.setSceneId(wxRedPackDto.getSceneId());
    }

    void saveLog(String status,WxRedPack wxRedPack,String hblist){
        WxRedPackLog wxRedPackLog = new WxRedPackLog();
        wxRedPackLog.setId(getId());
        wxRedPackLog.setCreateTime(DateUtil.now());
        wxRedPackLog.setStatus(status);
        wxRedPackLog.setPayRedPackOrderNo(wxRedPack.getPayRedPackOrderNo());
        wxRedPackLog.setRedPackOrderNo(wxRedPack.getRedPackOrderNo());

        wxRedPackLog.setUnitId(wxRedPack.getUnitId());
        wxRedPackLog.setMemberId(wxRedPack.getMemberId());
        wxRedPackLog.setSendName(wxRedPack.getSendName());
        wxRedPackLog.setTotalNum(wxRedPack.getTotalNum());
        wxRedPackLog.setAmount(wxRedPack.getAmount());
        wxRedPackLog.setReOpenId(wxRedPack.getReOpenId());
        wxRedPackLog.setHblist(hblist);
        wxRedPackLogDao.save(wxRedPackLog);
    }

    /**
     * 调接口查询结果
     * @param redPackOrderNo
     * @return
     */
    @Override
    @Transactional
    public int query(String redPackOrderNo) {
        Optional<WxRedPack> op = wxRedPackDao.findByRedPackOrderNo(redPackOrderNo);
        if(op.isPresent()
                && op.get().getTotalNum().equals(1)    //如果是普通红包并且已经得到最终结果
                && !op.get().getStatus().equals(0)      //不等于零和1表示：{-1:已失败,2:已收款,3:已退款}
                && !op.get().getStatus().equals(1)){
            return op.get().getStatus();                //返回结果状态
        }

        WxRedPack wxRedPack = op.get();

        //查询发红包状态
        JSONObject jo = new WxRedPackUtil().query(op.get().getPayRedPackOrderNo());
        String status = jo.getString("status");

        if("FAIL".equals(status)){
            refundAmount(wxRedPack.getUnitId(),wxRedPack.getAmount());
            wxRedPack.setStatus(WxRedPack.StatusType.FAIL.index);
            //更新时间
            wxRedPack.setUpdateTime(DateUtil.now());
            wxRedPackDao.save(wxRedPack);
            //存记录
            saveLog(status,wxRedPack,null);
            return 3;
        }else

        //如果发生退款
        if("REFUND".equals(status)){
            refundAmount(wxRedPack.getUnitId(),wxRedPack.getAmount());
            wxRedPack.setStatus(WxRedPack.StatusType.REFUND.index);
            //存记录
            saveLog(status,wxRedPack,null);
        }else

        //红包全部已接收
        if("RECEIVED".equals(status)){
            wxRedPack.setStatus(WxRedPack.StatusType.RECEIVED.index);
            //存记录
            saveLog(status,wxRedPack,jo.get("hbinfo").toString());
        }

        //更新时间
        wxRedPack.setUpdateTime(DateUtil.now());
        wxRedPackDao.save(wxRedPack);
        return op.get().getStatus();

    }

    void refundAmount(String unitId,BigDecimal amount){

        //获取支付渠道商户号
        PayChannelAccount payChannelAccount = payChannelAccountServ.findByUnitIdAndPayChannel(unitId, "wxpay");
        if (null == payChannelAccount
                || null == payChannelAccount.getPayChannelAccount()) {
            return;
        }

        payChannelAccountServ.unFreezeBalance(payChannelAccount,amount,true);
    }



    @Override
    public Map findWxRedPackOrderByUnit(Finder finder, String unitId) {
        Page<WxRedPack> page = findByUnit(finder,unitId);
        return ResultUtil.getPageJson(page.getTotalPages(),page.getTotalCount(),page.getItems()
                .stream()
                .map(wxRedPack->FastJsonUtil.getJson(wxRedPack.getBaseJson()))
                .toArray());
    }

    Page<WxRedPack> findByUnit(Finder finder, String unitId){
        StringJoiner hql = new StringJoiner(" ");
        hql.add("select w from WxRedPack w where w.unitId='"+unitId+"'");
        if(!CheckUtil.isEmpty(finder.getStatus()))
            hql.add(" and w.status="+finder.getStatus());

        if(!CheckUtil.isEmpty(finder.getStartTime()))
            hql.add(" and w.createTime >= '"+finder.getStartTime()+"'");
        if(!CheckUtil.isEmpty(finder.getEndTime()))
            hql.add(" and w.updateTime <= '"+finder.getEndTime()+"'");

        hql.add("order by w.updateTime desc");
        return baseDao.findPageByHql(hql.toString(),finder.getPageSize(),finder.getStartIndex());
    }
}
