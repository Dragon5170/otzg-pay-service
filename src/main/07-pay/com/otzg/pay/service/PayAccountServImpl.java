package com.otzg.pay.service;

import com.otzg.base.AbstractServ;
import com.otzg.base.Finder;
import com.otzg.base.Page;
import com.otzg.util.ResultUtil;
import com.otzg.pay.dao.PayAccountDao;
import com.otzg.pay.dao.PayAccountLogDao;
import com.otzg.pay.dao.PayChannelAccountDao;
import com.otzg.pay.entity.PayAccount;
import com.otzg.pay.entity.PayAccountLog;
import com.otzg.pay.enums.PayAccountType;
import com.otzg.util.CheckUtil;
import com.otzg.util.FastJsonUtil;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

@Service
public class PayAccountServImpl extends AbstractServ implements PayAccountServ {

    @Autowired
    PayAccountDao payAccountDao;
    @Autowired
    PayChannelAccountDao payChannelAccountDao;
    @Autowired
    PayAccountLogDao payAccountLogDao;

    @Override
    @Transactional
    public int create(String unitId,String name,String contact,String mobilePhone) {
        RLock lock = redisson.getLock(unitId);
        try {
            boolean l = lock.tryLock(30, TimeUnit.SECONDS);
            if (!l) {
                P("redisson lock false");
                return 1;
            }

            PayAccount payAccount = new PayAccount(unitId,name,contact,mobilePhone, PayAccountType.UNIT.index);
            payAccount.setId(getId());
            payAccountDao.save(payAccount);
            return 0;
        }catch (Exception e){
            rollBack();
            P("error");
            return 2;
        } finally {
            lock.unlock();
            P("redisson lock unlock");
        }
    }

    @Override
    public PayAccount findByUnitId(String unitId) {
        return payAccountDao.findByUnitId(unitId).orElse(null);
    }

    @Override
    public Map findByUnit(Finder finder, String unitId,String payChannel) {
        Page<PayAccountLog> page = findPage(finder,unitId,payChannel);
        return ResultUtil.getPageJson(page.getTotalPages(),page.getTotalCount(),
                page.getItems()
                .stream()
                .map(payAccountLog->FastJsonUtil.getJson(payAccountLog.getJson()))
                .toArray());
    }

    Page<PayAccountLog> findPage(Finder finder,String unitId,String payChannel){
        StringJoiner hql = new StringJoiner(" ");
        hql.add("select palg from PayAccountLog palg where palg.unitId='"+unitId+"'");
        if(!CheckUtil.isEmpty(payChannel))
            hql.add(" and palg.payChannel='"+payChannel+"'");
        if(!CheckUtil.isEmpty(finder.getStatus()))
            hql.add(" and palg.status="+finder.getStatus());
        if(!CheckUtil.isEmpty(finder.getStartTime()))
            hql.add(" and palg.createTime >= '"+finder.getStartTime()+"'");
        if(!CheckUtil.isEmpty(finder.getEndTime()))
            hql.add(" and palg.updateTime <= '"+finder.getEndTime()+"'");

        return baseDao.findPageByHql(hql.toString(),finder.getPageSize(),finder.getStartIndex());
    }


    @Override
    public PayAccount findById(Long id) {
        return null;
    }
    @Override
    public PayAccount findByMemberId(String memberId) {
        return null;
    }

    @Override
    public PayAccount findPlatform() {
        return null;
    }

    @Override
    public boolean checkPayAccountByMember(String memberId) {
        return false;
    }

    @Override
    public boolean checkPayAccountUnit(String unitId) {
        return false;
    }

    @Override
    public boolean checkPayAccountAmountByMember(String payerMemberId, BigDecimal profitAmount) {
        return false;
    }

    @Override
    public boolean checkPayAccountAmountByUnit(String payerUnitId, BigDecimal profitAmount) {
        return false;
    }

    @Override
    public int savePayPassword(String password, String unitId, String memberId) {
        return 0;
    }

    @Override
    public int checkPayPassword(String password, String unitId, String memberId) {
        return 0;
    }


    @Override
    public Map findPayAccountByAdmin(Finder finder, String memberId, String unitId) {
        return null;
    }

}
