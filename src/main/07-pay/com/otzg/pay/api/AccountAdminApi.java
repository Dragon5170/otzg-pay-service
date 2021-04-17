//package com.otzg.pay.api;
//
//import com.otzg.admin.controller.AbstractController;
//import com.otzg.base.Finder;
//import com.otzg.member.entity.Member;
//import com.otzg.member.service.MemberServ;
//import com.otzg.pay.entity.Account;
//import com.otzg.pay.service.AccountServ;
//import com.otzg.pay.service.PayOrdersServ;
//import com.otzg.util.CheckUtil;
//import com.otzg.util.DateUtil;
//import com.otzg.util.RespTips;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class AccountAdminApi extends AbstractController {
//    @Autowired
//    private AccountServ accountServ;
//    @Autowired
//    private MemberServ memberServ;
//    @Autowired
//    private PayOrdersServ payOrdersServ;
//
//    //平台收益统计{1天、7天、全部}
//    @RequestMapping(value = "/admin/platform/profit/statistics")
//    public final void findPlatformProfitStatistics(String token) {
//        //获取账户
//        Account account = accountServ.findPlatform();
//        if (CheckUtil.isEmpty(account)) {
//            sendJson(false, RespTips.ACCOUNT_IS_UNAVAILABLE.code, "账户不存在!");
//            return;
//        }
//
//        sendSuccess(accountServ.getStatistics(account.getId()));
//    }
//
//    //按输入的日期获取到今天为止的平台每天收益金额
//    @RequestMapping(value = "/admin/platform/profit/every_day")
//    public final void findPlatformDayAmountByDateBetween(String startDate, String endDate) {
//        if (!CheckUtil.isDate(startDate)) {
//            sendParamError();
//            return;
//        }
//        if (!CheckUtil.isDate(endDate)) {
//            endDate = DateUtil.yearMonthDay();
//        }
//
//        //获取账户
//        Account account = accountServ.findPlatform();
//        if (CheckUtil.isEmpty(account)) {
//            sendJson(false, RespTips.ACCOUNT_IS_UNAVAILABLE.code, "账户不存在!");
//            return;
//        }
//
//        sendJson(accountServ.findDayAmountByDateBetween(account.getId(), startDate, endDate));
//    }
//
//    //按输入的日期获取推广员每天收益金额
//    @RequestMapping(value = "/admin/member/profit/every_day")
//    public final void findMemberDayAmountByDateBetween(String startDate, String endDate) {
//        if (!CheckUtil.isDate(startDate)) {
//            sendParamError();
//            return;
//        }
//        if (!CheckUtil.isDate(endDate)) {
//            endDate = DateUtil.yearMonthDay();
//        }
//        //返回结束日期的最后一秒
//        endDate = DateUtil.getEndTime(endDate);
//
//        sendJson(accountServ.findMemberDayAmountByDateBetween(startDate, endDate, null));
//    }
//
//    //按输入的日期获取所有商户每天收益金额
//    @RequestMapping(value = "/admin/unit/profit/every_day")
//    public final void findUnitDayAmountByDateBetween(String startDate, String endDate) {
//        if (!CheckUtil.isDate(startDate)) {
//            sendParamError();
//            return;
//        }
//        if (!CheckUtil.isDate(endDate)) {
//            endDate = DateUtil.yearMonthDay();
//        }
//
//        //返回结束日期的最后一秒
//        endDate = DateUtil.getEndTime(endDate);
//
//        sendJson(accountServ.findUnitDayAmountByDateBetween(startDate, endDate));
//    }
//
//    //推广员总收益收益排行
//    @RequestMapping(value = "/admin/member/profit/sort")
//    public final void findMemberProfitSort(String token) {
//        sendSuccess(accountServ.findMemberProfitSort());
//    }
//
//    // 商户总收益收益排行
//    @RequestMapping(value = "/admin/unit/profit/sort")
//    public final void findUnitProfitSort(String token) {
//        sendSuccess(accountServ.findUnitProfitSort());
//    }
//
//    //推广员总收益及平均收益
//    @RequestMapping(value = "/admin/member/profit/total")
//    public final void findMemberTotalAndAvg() {
//        sendSuccess(accountServ.findMemberProfitTotalAndAvg());
//    }
//
//    //商户总收益及平均收益
//    @RequestMapping(value = "/admin/unit/profit/total")
//    public final void findUnitTotalAndAvg() {
//        sendSuccess(accountServ.findUnitProfitTotalAndAvg());
//    }
//
//    //推广员的商户收益分页
//    @RequestMapping(value = "/admin/member/unit/profit/find")
//    public final void findUnitProfitByDateBetweenGroupByMember(Finder finder) {
//        sendJson(accountServ.findUnitProfitByDateBetweenGroupByMember(finder));
//    }
//
//
//    //商户数据统计分页
//    @RequestMapping(value = "/admin/unit/profit/find")
//    public final void findUnitProfitByDateBetween(Finder finder) {
//        sendJson(accountServ.findUnitProfitByDateBetween(finder));
//    }
//
//    //推广员数据统计分页
//    @RequestMapping(value = "/admin/member/profit/find")
//    public final void findMemberProfitByDateBetween(Finder finder) {
//        sendJson(accountServ.findMemberProfitByDateBetween(finder));
//    }
//
//    //平台的收益分页
//    @RequestMapping(value = "/admin/platform/profit/find")
//    public final void findPlatformProfitByDateBetween(Finder finder) {
//        Account account = accountServ.findPlatform();
//        if (CheckUtil.isEmpty(account)) {
//            sendDataNull();
//            return;
//        }
//        sendJson(accountServ.findPlatformAccountLog(finder, account.getId()));
//    }
//
//    //获取所有账户流水
//    @RequestMapping("/admin/account/find")
//    public void getAccountByAdmin(Long memberId,
//                                  Finder finder) {
//        if (CheckUtil.isEmpty(memberId)) {
//            sendParamError();
//            return;
//        }
//
//        Account account = accountServ.findByMember(memberId);
//        if (CheckUtil.isEmpty(account)) {
//            sendDataNull();
//            return;
//        }
//        sendJson(accountServ.findAccountLog(finder, account.getId(), null));
//    }
//
//    //获取商户监听到的流水
//    @RequestMapping("/admin/unit/account/find")
//    public void getUnitAccountLogByAdmin(Long memberId,
//                                         Finder finder) {
//        if (CheckUtil.isEmpty(memberId)) {
//            sendParamError();
//            return;
//        }
//
//        Member member = memberServ.findById(memberId);
//        if (CheckUtil.isEmpty(member)) {
//            sendDataNull();
//            return;
//        }
//
//        sendJson(accountServ.findAccountLog(finder, null, member.getAccount()));
//    }
//}
//
