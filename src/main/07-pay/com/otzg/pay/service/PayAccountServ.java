package com.otzg.pay.service;

import com.otzg.base.Finder;
import com.otzg.pay.entity.PayAccount;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author G./2018/6/30 9:43
 */
public interface PayAccountServ {
    //创建基本账户
    int create(String unitId,String name,String contact,String mobilePhone);

    //根据账户id获取基本账户
    PayAccount findById(Long id);
    //根据会员id获取基本账户
    PayAccount findByMemberId(String memberId);
    //根据单位id获取基本账户
    PayAccount findByUnitId(String unitId);
    //获取平台账户信息(PayAccount)
    PayAccount findPlatform();

    //账户查询
    Map findByUnit(Finder finder, String unitId,String payChannel);


    /**
     * 判断账户是否可用
     * @author G
     * @param memberId
     */
    boolean checkPayAccountByMember(String memberId);
    boolean checkPayAccountUnit(String unitId);

    /**
     * 判断付款单位账户余额是否够用
     * @author G
     * @param payerMemberId
     * @param profitAmount
     */
    boolean checkPayAccountAmountByMember(String payerMemberId, BigDecimal profitAmount);
    boolean checkPayAccountAmountByUnit(String payerUnitId, BigDecimal profitAmount);

    /**
     * 设置某个单位的支付密码
     * @param password
     * @param memberId
     * @param unitId
     * @return
     */
    int savePayPassword(String password,String unitId,String memberId);

    /**
     * 验证支付密码
     * @param password
     * @param memberId
     * @param unitId
     * @return
     */
    int checkPayPassword(String password,String unitId,String memberId);




    //=============================管理端方法=======================================

    //获取账户流水
    Map findPayAccountByAdmin(Finder finder,String memberId,String unitId);


}
