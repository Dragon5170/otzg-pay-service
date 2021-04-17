package com.otzg.alipay.redbag.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.otzg.alipay.redbag.util.AlipayConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;

@Controller
public class ReturnController {

    @RequestMapping(value="/returnUrl.htm")
    public void toHtml(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws IOException {
        
        Properties prop = AlipayConfig.getProperties();
        // 编码
        String charset = prop.getProperty("CHARSET");
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        boolean validation = false;
        String result = null;
        try {
            validation = AlipaySignature.rsaCheckV1(params, prop.getProperty("ALIPAY_RSA2_PUBLIC_KEY"), charset,
                    prop.getProperty("SIGN_TYPE"));
            if (validation) {
                result = "验证通过";
            }else{
                result = "验证失败，请确认“支付宝公钥”是否匹配，注意非商户的公钥是支付宝公钥";
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            result = "处理出错，请查看代码进行排查";
        }
        
      //TODO 根据业务需要进行处理
        
        response.setContentType("text/html;charset=" + charset);
        response.setCharacterEncoding(charset);
        response.getWriter().write(result);// 直接将完整的表单html输出到页面
        response.getWriter().flush();
        response.getWriter().close();
        return;
        
    }
}
