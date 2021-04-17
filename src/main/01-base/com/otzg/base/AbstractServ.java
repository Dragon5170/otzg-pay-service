package com.otzg.base;

import com.otzg.util.DateUtil;
import com.otzg.util.FuncUtil;
import org.redisson.Redisson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.UUID;

public abstract class AbstractServ extends BaseBean {

    @Autowired
    protected BaseDao baseDao;
    @Autowired
    protected Redisson redisson;

    protected Long getId() {
        return UUID.randomUUID().getLeastSignificantBits();
    }

    //自动生成订单号
    protected String getPayOrderNo(String unitId) {
        return DateUtil.yearMonthDayTimeShort() + FuncUtil.getRandInt(0001, 9999) + Math.abs(unitId.hashCode());
    }

    protected final void rollBack() {
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    }


}
