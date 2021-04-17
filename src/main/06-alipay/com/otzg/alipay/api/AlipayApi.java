package com.otzg.alipay.api;

import com.otzg.alipay.service.AliMicroAccountServ;
import com.otzg.alipay.util.AlipayUtil;
import com.otzg.pay.api.AbstractController;
import com.otzg.pay.entity.PayAccount;
import com.otzg.pay.service.PayAccountServ;
import com.otzg.pay.service.PayChannelAccountServ;
import com.otzg.util.AesUtil;
import com.otzg.util.CheckUtil;
import com.otzg.util.RespTips;
import com.otzg.util.UrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @Author G.
 * @Date 2019/12/5 0005 上午 10:59
 */
@RestController
public class AlipayApi extends AbstractController {
    @Autowired
    AliMicroAccountServ aliMicroAccountServ;
    @Autowired
    PayAccountServ payAccountServ;
    @Autowired
    PayChannelAccountServ payChannelAccountServ;

    //获取app_auth_code
    //商户授权成功后，PC 或者钱包客户端会跳转至开发者定义的回调页面（即 redirect_uri 参数对应的 url ）
    // 在回调页面请求中会带上当次授权的授权码 app_auth_code 和开发者的 app_id
    //

    /**
     * 支付宝授权回调接口
     * app_auth_code  应用授权的 app_auth_code 是唯一的；app_auth_code 使用一次后失效，
     * 单个授权的有效期为一天（从生成 app_auth_code 开始的24小时）
     * 未被使用自动过期；批量授权的有效期为 10 分钟。     *
     * app_auth_token 永久有效。
     * 和开发者的 app_id
     *
     * @param uid 回调参数表示平台内的单位id=>unitId
     */
    @RequestMapping(value = "/alipay/openAuthTokenApp/notify")
    public final void alipayPayNotify(String uid) {
        Map<String, Object> params = UrlUtil.getRequestMap(getRequest());
        P("支付宝回调结果" + params.toString());

        //http://fm3mzm.natappfree.cc/alipay/openAuthTokenApp/notify?
        // app_id=2019011162891191
        // &source=alipay_app_auth
        // &app_auth_code=f8419ed4af1740a2a47a18a12853eX27

        //2019-12-05 13:47
        //{source=alipay_app_auth, app_id=2019011162891191, app_auth_code=64c6ae3949af4f67af0cd42b3787aX27}
        //李唐授权
        //{source=alipay_app_auth, app_id=2019011162891191, app_auth_code=2ad80d54aa3346c98aea935c2bb9bX96}
        //{source=alipay_app_auth, app_id=2019011162891191, app_auth_code=c708ca25b1024cba9ca3d610deae7D27,

        if (null == params
                || null == params.get("app_auth_code")) {
            sendJson(false, RespTips.PAYCHANNLE_AUTHCODE_ERROR.code, RespTips.PAYCHANNLE_AUTHCODE_ERROR.tips);
            //返回授权失败
            sendRedirect("/alipay/auth/fail.html");
            return;
        }

        //根据authCode 获取authToken 并创建支付渠道账户
        int r = aliMicroAccountServ.createMicroAccount(uid, params.get("app_id").toString(), params.get("app_auth_code").toString());
        if (r == 0) {
            //返回授权成功
            sendRedirect("/alipay/auth/success.html");
        } else {
            //返回授权失败
            sendRedirect("/alipay/auth/fail.html");
        }

    }

    /**
     * 支付宝获取授权链接接口
     */
    @RequestMapping(value = "/alipay/authTokenUrl/get")
    public final void alipayAuthUrl(String unitId){
        if (CheckUtil.isEmpty(unitId)) {
            sendRedirect("/alipay/auth/error.html");
            return;
        }

        //判断商户基本账户是否被禁用
        PayAccount payAccount = payAccountServ.findByUnitId(unitId);
        if (null == payAccount
                || !payAccount.isUseable()) {
            try {
                sendRedirect("/alipay/auth/error.html?msg=" + java.net.URLEncoder.encode(RespTips.PAYACCOUNT_IS_UNAVAILABLE.tips, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return;
        }

        //预创建一个支付渠道账户，授权成功后直接更新账户状态即可
        new Thread(()->payChannelAccountServ.createPayChannelAccount("支付宝收款账户","alipay",payAccount.getName(),payAccount.getId(),unitId)).start();

        //返回前端授权页面
        String url = new AlipayUtil().getAuthTokenUrl(unitId);
        sendRedirect("/alipay/auth/share.html?code=" + AesUtil.getEnCode(url)+"&link="+url);
    }


}
