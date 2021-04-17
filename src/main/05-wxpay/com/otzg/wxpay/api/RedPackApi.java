package com.otzg.wxpay.api;

import com.otzg.base.Finder;
import com.otzg.pay.api.AbstractController;
import com.otzg.pay.entity.PayAccount;
import com.otzg.pay.service.PayAccountServ;
import com.otzg.util.CheckUtil;
import com.otzg.util.RespTips;
import com.otzg.wxpay.dto.WxRedPackDto;
import com.otzg.wxpay.dto.WxRedPackDtoCheck;
import com.otzg.wxpay.service.RedPackServ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author G.
 * @Date 2019/12/13 0013 下午 2:35
 */
@RestController
public class RedPackApi extends AbstractController {
    @Autowired
    private PayAccountServ payAccountServ;
    @Autowired
    RedPackServ redPackServ;

    @RequestMapping("/wxpay/redpack/send")
    public void sendRedPack(WxRedPackDto wxRedPackDto) {
        if (CheckUtil.isEmpty(wxRedPackDto)) {
            sendParamError();
            return;
        }

        //校验参数
        WxRedPackDtoCheck wxRedPackDtoCheck = new WxRedPackDtoCheck(wxRedPackDto);
        wxRedPackDto = wxRedPackDtoCheck.get();
        if (null == wxRedPackDto) {
            sendJson(wxRedPackDtoCheck.getMsg());
            return;
        }


        //判断基本账户是否已创建
        PayAccount payAccount = payAccountServ.findByUnitId(wxRedPackDto.getUnitId());
        if (null == payAccount
                || !payAccount.isUseable()) {
            sendJson(false, RespTips.PAYACCOUNT_IS_UNAVAILABLE.code, RespTips.PAYACCOUNT_IS_UNAVAILABLE.tips);
            return;
        }

        //立即发红包
        int r = redPackServ.sendRedPack(payAccount.getId(), wxRedPackDto);
        //发送结果状态：{-1:发送失败,0:发送中,1:已发送,2:已接收,3:已退款}
        //{-4:微信渠道账户未创建，-5:微信账户余额不足，-6：微信账户加锁失败,-7:系统异常}
        if (r == 0 || r == 1) {
            sendJson(true, RespTips.PAYCHANNEL_PAY_SUCCESS.code, RespTips.PAYCHANNEL_PAY_SUCCESS.tips);
        } else if (r == 2) {
            sendJson(true, RespTips.PAYCHANNEL_PAY_SUCCESS.code, RespTips.PAYCHANNEL_PAY_SUCCESS.tips);
        } else if (r == 3) {
            sendJson(true, RespTips.REFUND_DATA_FOUND.code, RespTips.REFUND_DATA_FOUND.tips);

        } else if (r == -4) {
            sendJson(false, RespTips.PAYACCOUNT_IS_INSUFFICIENT.code, RespTips.PAYACCOUNT_IS_INSUFFICIENT.tips);
        } else if (r == -5) {
            sendJson(false, RespTips.PAYACCOUNT_IS_INSUFFICIENT.code, RespTips.PAYACCOUNT_IS_INSUFFICIENT.tips);
        } else if (r == -6) {
            sendJson(false, RespTips.PAYORDER_LOCK_ERROR.code, RespTips.PAYORDER_LOCK_ERROR.tips);
        } else{
            sendJson(false,RespTips.PAYORDER_ERROR.code,RespTips.PAYORDER_ERROR.tips);
        }
    }

    @RequestMapping("/wxpay/redpack/find")
    public void find(String unitId, Finder f) {
        if (CheckUtil.isEmpty(unitId)) {
            sendParamError();
            return;
        }
        sendJson(redPackServ.findWxRedPackOrderByUnit(f, unitId));
    }

    @RequestMapping("/wxpay/redpack/query")
    public void sendRedPack(String redPackOrderNo) {
        if (CheckUtil.isEmpty(redPackOrderNo)) {
            sendParamError();
            return;
        }
        int r = redPackServ.query(redPackOrderNo);
        //{-1:发送失败,0:发送中,1:已发送,2:已接收,3:已退款}
        if (r == 0 || r == 1) {
            sendJson(true, RespTips.PAYCHANNEL_PAY_SUCCESS.code, RespTips.PAYCHANNEL_PAY_SUCCESS.tips);
        } else if (r == 2) {
            sendJson(true, RespTips.PAYCHANNEL_PAY_SUCCESS.code, RespTips.PAYCHANNEL_PAY_SUCCESS.tips);
        } else if (r == 3) {
            sendJson(true, RespTips.REFUND_DATA_FOUND.code, RespTips.REFUND_DATA_FOUND.tips);
        } else {
            sendJson(false,RespTips.PAYORDER_ERROR.code,RespTips.PAYORDER_ERROR.tips);
        }
    }
}
