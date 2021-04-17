package com.otzg.pay.api;

import com.otzg.base.Finder;
import com.otzg.pay.entity.PayAccount;
import com.otzg.pay.service.PayAccountServ;
import com.otzg.pay.service.PayChannelAccountServ;
import com.otzg.util.CheckUtil;
import com.otzg.util.FastJsonUtil;
import com.otzg.util.RespTips;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResultUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayAccountApi extends AbstractController {

    @Autowired
    PayAccountServ payAccountServ;
    @Autowired
    PayChannelAccountServ payChannelAccountServ;

    //创建基本账户
    @RequestMapping("/payAccount/create")
    public void createPayAccount(String unitId, String name, String contact, String mobilePhone, BindingResultUtils v) {
        if (CheckUtil.isEmpty(unitId)
                || CheckUtil.isEmpty(name)
                || CheckUtil.isEmpty(contact)
                || CheckUtil.isEmpty(mobilePhone)) {
            sendParamError();
            return;
        }

        PayAccount payAccount = payAccountServ.findByUnitId(unitId);
        if (null != payAccount) {
            sendJson(false, RespTips.PAYACCOUNT_FOUND.code, RespTips.PAYACCOUNT_FOUND.tips);
            return;
        }

        int r = payAccountServ.create(unitId, name, contact, mobilePhone);
        if (r == 0) {
            sendSuccess();
        } else {
            sendFail();
        }

    }
    //获取会员(推广员及商户)账户流水
    @RequestMapping("/payAccount/info")
    public void getAccount(String unitId) {
        if(CheckUtil.isEmpty(unitId)){
            sendParamError();
            return;
        }

        PayAccount payAccount = payAccountServ.findByUnitId(unitId);
        if (null == payAccount) {
            sendJson(false, RespTips.PAYCHANNEL_SET_ERROR.code, RespTips.PAYCHANNEL_SET_ERROR.tips);
            return;
        }

        sendSuccess(FastJsonUtil.getJson(payAccount.getJson()));
    }

    //============================账户查询====================================
    //获取会员账户流水
    @RequestMapping("/payAccount/find")
    public void getAccountByAdmin(String unitId,String payChannel, Finder finder) {
        if(CheckUtil.isEmpty(unitId)){
            sendParamError();
            return;
        }

        PayAccount payAccount = payAccountServ.findByUnitId(unitId);
        if (null == payAccount) {
            sendJson(false, RespTips.PAYCHANNEL_SET_ERROR.code, RespTips.PAYCHANNEL_SET_ERROR.tips);
            return;
        }

        sendSuccess(payAccountServ.findByUnit(finder,unitId,payChannel));
    }




    //=======================================支付密码======================================================/

//    //通过手机短信，设置账户支付密码
//    @RequestMapping("/pay/account/password/save")
//    public void setAccountPayPassword(String token,
//                                      String mobilePhone,
//                                      String smsCode,
//                                      String password) {
//        if (!CheckUtil.isMobile(mobilePhone)
//                || CheckUtil.isEmpty(smsCode)
//                || !CheckUtil.isPassword(password)) {
//            sendParamError();
//            return;
//        }
//
//        //验证手机短信
//        if (!SmsSend.isSmsCode(mobilePhone, mobilePhone, smsCode)) {
//            sendJson(false, RespTips.SMSCODE_ERROR.code, RespTips.SMSCODE_ERROR.tips);
//            return;
//        }
//
//        //验证通过，删除短信验证码
//        SmsSend.delSmsCode(mobilePhone);
//
//        Member member = getMember(token);
//        //验证会员手机是否相同
//        if (!mobilePhone.equals(member.getMobilePhone())) {
//            sendJson(false, RespTips.DATA_CANT_OPTION.code, "手机号与认证过的不符");
//            return;
//        }
//
//        //修改支付密码
//        int r = payAccountServ.savePayPassword(password, getMember(token));
//        if (r == 1) {
//            sendJson(false, RespTips.ACCOUNT_IS_UNAVAILABLE.code, RespTips.ACCOUNT_IS_UNAVAILABLE.tips);
//            return;
//        }
//
//        sendSuccess();
//    }


//
//    //校验账户支付密码
//    @RequestMapping("/member/account/password/check")
//    public void checkAccountPayPassword(String token,
//                                        String password) {
//        if (!CheckUtil.isPassword(password)) {
//            sendParamError();
//            return;
//        }
//
//        int r = payAccountServ.checkPayPassword(password, getMember(token).getId());
//        if (r == 0) {
//            sendJson(true, RespTips.ACCOUNT_PASSWORD_IS_RIGHT.code,RespTips.ACCOUNT_PASSWORD_IS_RIGHT.tips);
//            return;
//        } else {
//            sendJson(false, RespTips.ACCOUNT_PASSWORD_IS_WRONG.code, RespTips.ACCOUNT_PASSWORD_IS_WRONG.tips);
//            return;
//        }
//    }
//
//    /**
//     * 修改支付密码
//     *
//     * @param token
//     * @param usedPassword 旧密码
//     * @param newPassword  新密码
//     */
//    @RequestMapping(value = "/member/account/password/update")
//    public void accountUpdatePassword(String token,
//                                      String usedPassword,
//                                      String newPassword) {
//        if (CheckUtil.isEmpty(usedPassword) ||
//                CheckUtil.isEmpty(newPassword)) {
//            sendParamError();
//            return;
//        }
//
//        int r = payAccountServ.checkPayPassword(usedPassword, getMember(token).getId());
//        if (r == 1) {
//            sendJson(false, RespTips.ACCOUNT_PASSWORD_IS_WRONG.code, RespTips.ACCOUNT_PASSWORD_IS_WRONG.tips);
//            return;
//        }
//        int i = payAccountServ.savePayPassword(newPassword, getMember(token));
//        if (i == 1) {
//            sendJson(false, RespTips.ACCOUNT_IS_UNAVAILABLE.code, RespTips.ACCOUNT_IS_UNAVAILABLE.tips);
//            return;
//        }
//        sendSuccess();
//    }
//
//    /**
//     * 判断是否存在支付密码
//     *
//     * @param token
//     */
//    @RequestMapping(value = "/member/account/password/find")
//    public final void findAccountPassword(String token) {
//        Account account = payAccountServ.findByMember(getMember(token).getId());
//        if (CheckUtil.isEmpty(account)) {
//            sendJson(false, RespTips.DATA_NULL.code, "账户信息不存在!");
//            return;
//        } else if (CheckUtil.isEmpty(account.getPassword())) {
//            sendJson(false, RespTips.DATA_NULL.code, "支付密码不存在!");
//            return;
//        } else {
//            sendJson(false, RespTips.SUCCESS_CODE.code, "支付密码已存在!");
//            return;
//        }
//    }
//
//
//    //============================消息相关====================================
//
//    /**
//     * 标记当前用户的账户收益记录为已读
//     *
//     * @param token
//     * @param profitType 标记对应的收益类型
//     */
//    @RequestMapping("/member/account/read")
//    public void clearReadMarkByLevel(String token, Integer profitType) {
//        if (CheckUtil.isEmpty(profitType)) {
//            sendParamError();
//            return;
//        }
//
//        int r = payAccountServ.clearReadMarkByProfitType(getMember(token).getAccount(), profitType);
//        if (r == 0) {
//            sendSuccess();
//        }
//
//    }
//
//


//
//
//    //===================================统计相关============================================
//    //会员收益统计{1天、7天、全部}
//    @RequestMapping(value = "/member/profit/statistics")
//    public final void findProfitStatistics(String token) {
//        //获取账户
//        Account account = payAccountServ.findByMember(getMember(token).getId());
//        if (CheckUtil.isEmpty(account)) {
//            sendJson(false, RespTips.ACCOUNT_IS_UNAVAILABLE.code, "账户不存在!");
//            return;
//        }
//
//        sendSuccess(payAccountServ.getStatistics(account.getId()));
//    }
//
//    //商户收益统计{1天、7天、全部}
//    @RequestMapping(value = "/unit/profit/statistics")
//    public final void findUnitProfitStatistics(String token) {
//        sendSuccess(payAccountServ.getUnitStatistics(getMember(token).getAccount()));
//    }
//
//    //按输入的日期获取到今天为止的每天收益金额
//    @RequestMapping(value = "/member/profit/lastDate")
//    public final void findDayAmountByLastDate(String token, String startDate, String endDate) {
//        if (!CheckUtil.isDate(startDate)) {
//            sendParamError();
//            return;
//        }
//        if (!CheckUtil.isDate(endDate)) {
//            endDate = DateUtil.yearMonthDay();
//        }
//
//        //获取账户
//        Account account = payAccountServ.findByMember(getMember(token).getId());
//        if (CheckUtil.isEmpty(account)) {
//            sendJson(false, RespTips.ACCOUNT_IS_UNAVAILABLE.code, "账户不存在!");
//            return;
//        }
//
//        sendJson(payAccountServ.findDayAmountByDateBetween(account.getId(), startDate, endDate));
//    }
//
//    @RequestMapping(value = "/unit/profit/lastDate")
//    public final void findUnitDayAmountByLastDate(String token, String startDate, String endDate) {
//        if (!CheckUtil.isDate(startDate)) {
//            sendParamError();
//            return;
//        }
//        if (!CheckUtil.isDate(endDate)) {
//            endDate = DateUtil.yearMonthDay();
//        }
//
//        sendJson(payAccountServ.findUnitDayAmountByDateBetween(startDate, endDate, getMember(token).getAccount()));
//    }


//    @RequestMapping(value = "/pay/account/save")
//    public void payOrderQuery(String token,
//                              String dateTime,
//                              BigDecimal amount,
//                              String payChannel) {
//        if (CheckUtil.isEmpty(dateTime)
//                ||!CheckUtil.isDatetime(dateTime)
//                || CheckUtil.isEmpty(amount)
//                || CheckUtil.isEmpty(payChannel)
//                ) {
//            sendParamError();
//            return;
//        }
//
//        //保存流水单号
//        int r = accountLogServ.saveAccountLog(getMember(token).getAccount(), dateTime,amount,payChannel);
//        if(r==0){
//            sendSuccess();
//        }else{
//            sendFail();
//        }
//    }
}
