package com.otzg.wxpay.api;

import com.otzg.pay.api.AbstractController;
import com.otzg.util.CheckUtil;
import com.otzg.util.RespTips;
import com.otzg.wxpay.dto.WxMicroAccountDto;
import com.otzg.wxpay.entity.WxMicroAccount;
import com.otzg.wxpay.service.WxMicroAccountServ;
import com.otzg.wxpay.util.WxMicroAccountCheckCheckUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author G.
 * @Date 2019/11/30 0030 下午 3:10
 */
@RestController
public class MicroAccountApi extends AbstractController {
    @Autowired
    WxMicroAccountServ wxMicroAccountServ;
    @RequestMapping("/pay/microAccount/save")
	public final void save(String unitId,WxMicroAccountDto wxMicroAccountDto){
        if(CheckUtil.isEmpty(unitId)){
            sendParamError();
            return;
        }

        if(null!= wxMicroAccountServ.query(unitId)){
            sendJson(false, RespTips.PAYACCOUNT_FOUND.code,"商户材料已提交");
            return;
        }

        wxMicroAccountDto.setIdCardValidTime("[\""+wxMicroAccountDto.getIdCardValidTime()+"\",\"长期\"]");
        //校验
        WxMicroAccountCheckCheckUtil check = new WxMicroAccountCheckCheckUtil(wxMicroAccountDto);
        if(check.hasErrors()){
            sendJson(check.getMsg());
            return;
        }

        //校验通过
        WxMicroAccount wxMicroAccount = check.getPojo();
        wxMicroAccount.setUnitId(unitId);

        int r = wxMicroAccountServ.saveMicroAccount(wxMicroAccount);
        if(r==0){
            sendSuccess();
        }
    }

    @RequestMapping("/pay/microAccount/query")
    public final void query(String unitId) {
        if (CheckUtil.isEmpty(unitId)) {
            sendParamError();
            return;
        }

        Map jo = wxMicroAccountServ.query(unitId);
        if(null == jo){
            sendFail();
        }else {
            sendSuccess(jo);
        }
    }

}
