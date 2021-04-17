package com.otzg.log.controller;

import com.otzg.alipay.util.AlipayConfig;
import com.otzg.base.BaseController;
import com.otzg.log.util.LogUtil;
import com.otzg.wxpay.util.service.WXPayConfig;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController("logController")
public class LogController extends BaseController {
    /**
     * @author admin1
     */
    @RequestMapping("/log/pay")
    public void payLog(String date){
        sendHtml(LogUtil.readTradeLog(LogUtil.getFileSavePath(),date));
    }

    /**
     * @author admin2
     */
    @RequestMapping("/log/sys")
    public void sysLog(String date){
        sendHtml(LogUtil.readSysErrorLog(LogUtil.getFileSavePath(),date));
    }

    /**
     * @author admin3
     */
    @RequestMapping("/log/web")
    public void webLog(String date){
        sendHtml(LogUtil.readWebAccessLog(LogUtil.getFileSavePath(),date));
    }

    /**
     * @author admin4
     *
     */
    @RequestMapping(value="/log/info")
    public void test(){
        JSONObject jo = new JSONObject();
        jo.put("version",LogUtil.getVersion());
        jo.put("server-url",LogUtil.getServUrl());
        jo.put("file-save-path",LogUtil.getFileSavePath());
        jo.put("local-path",LogUtil.getLocalPath());
        jo.put("server-ip-path",LogUtil.getServerIpPath());
        jo.put("wx.appId=", WXPayConfig.getAppId());
        jo.put("wx.mchId=", WXPayConfig.getMchId());
        jo.put("wx.isAutoReport=", WXPayConfig.isAutoReport());
        jo.put("wx.key=", WXPayConfig.getKey());
        jo.put("wx.notifyUrl=", WXPayConfig.getNotifyUrl());
        jo.put("alipay.notifyUrl=", AlipayConfig.getNotifyUrl());
        jo.put("alipay.authNotifyUrl=", AlipayConfig.getAuthNotifyUrl());
        //返回结果
        sendSuccess(jo);
    }

    @RequestMapping(value="/test/upload/img",method = RequestMethod.POST)
    public void testUploadImg(String mchId,MultipartFile file){
        P("mchid="+mchId);
        P("file Name="+file.getName());
        P("file o Name="+file.getOriginalFilename());
        P("file contentType="+file.getContentType());
        P("file size="+file.getSize());
        sendJson(true, mchId,file.getName());
    }

}
