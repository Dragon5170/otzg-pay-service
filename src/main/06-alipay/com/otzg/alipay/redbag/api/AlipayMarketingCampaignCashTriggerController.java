package com.otzg.alipay.redbag.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;

import com.otzg.alipay.redbag.entity.Result;
import com.otzg.alipay.redbag.util.AlipayConfig;
import com.otzg.alipay.redbag.util.DefaultAlipayClientFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayMarketingCampaignCashTriggerModel;
import com.alipay.api.request.AlipayMarketingCampaignCashTriggerRequest;
import com.alipay.api.response.AlipayMarketingCampaignCashTriggerResponse;

@Controller
public class AlipayMarketingCampaignCashTriggerController {
	
    @RequestMapping(value="/alipayMarketingCampaignCashTrigger.htm")
    public String toHtml(HttpServletRequest request,ModelMap modelMap){
        
        return "api/alipayMarketingCampaignCashTrigger";
        
    }

    @RequestMapping(value="/alipayMarketingCampaignCashTrigger.json", method = RequestMethod.POST)
    @ResponseBody
    public Object doPost(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap,AlipayMarketingCampaignCashTriggerModel alipayModel){
        Result<AlipayMarketingCampaignCashTriggerResponse> result = new Result<AlipayMarketingCampaignCashTriggerResponse>();
        Properties prop = AlipayConfig.getProperties();
        
		//初始化请求类
		AlipayMarketingCampaignCashTriggerRequest alipayRequest = new AlipayMarketingCampaignCashTriggerRequest();
		//添加demo请求标示，用于标记是demo发出
		alipayRequest.putOtherTextParam(AlipayConfig.ALIPAY_DEMO, AlipayConfig.ALIPAY_DEMO_VERSION);
		//设置业务参数，alipayModel为前端发送的请求信息，开发者需要根据实际情况填充此类
		alipayRequest.setBizModel(alipayModel);
		alipayRequest.setReturnUrl(prop.getProperty("RETURN_URL"));
		alipayRequest.setNotifyUrl(prop.getProperty("NOTIFY_URL"));
		//sdk请求客户端，已将配置信息初始化
		AlipayClient alipayClient = DefaultAlipayClientFactory.getAlipayClient();
		try {
									//因为是接口服务，使用exexcute方法获取到返回值
			AlipayMarketingCampaignCashTriggerResponse alipayResponse = alipayClient.execute(alipayRequest);
			if(alipayResponse.isSuccess()){
				System.out.println("调用成功");
				//TODO 实际业务处理，开发者编写。可以通过alipayResponse.getXXX的形式获取到返回值
			} else {
				System.out.println("调用失败");
			}
						result.setSuccess(true);
			result.setValue(alipayResponse);
			return result;
						
		} catch (AlipayApiException e) {
			e.printStackTrace();
		    if(e.getCause() instanceof java.security.spec.InvalidKeySpecException){
		        result.setMessage("商户私钥格式不正确，请确认配置文件Alipay-Config.properties中是否配置正确");
		        return result;
		    }
		}

        return null;
        
    }
}