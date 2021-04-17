package com.otzg.alipay.redbag.util;

import java.util.Properties;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

/**
 * @ClassName: DefaultAlipayClientFactory
 * @Description: TODO(公共请求参数拼接类)
 * @author 君泓 junying.wjy
 * @date 2018年7月23日 下午4:38:38
 *
 */
public class DefaultAlipayClientFactory {

	private static AlipayClient alipayClient = null;

	/**
	 * 封装公共请求参数
	 * 
	 * @return AlipayClient
	 */
	public static AlipayClient getAlipayClient() {

	if (alipayClient != null) {
			return alipayClient;
		}

		Properties prop = AlipayConfig.getProperties();

		// 网关
		String URL = prop.getProperty("ALIPAY_GATEWAY_URL");
		// 商户APP_ID
		String APP_ID = prop.getProperty("APP_ID");
		// 商户RSA 私钥
		String APP_PRIVATE_KEY = prop.getProperty("RSA2_PRIVATE_KEY");
		// 请求方式 json
		String FORMAT = prop.getProperty("FORMAT");
		// 编码格式，目前只支持UTF-8
		String CHARSET = prop.getProperty("CHARSET");
		// 支付宝公钥
		String ALIPAY_PUBLIC_KEY = prop.getProperty("ALIPAY_RSA2_PUBLIC_KEY");
		// 签名方式
		String SIGN_TYPE = prop.getProperty("SIGN_TYPE");

		return new DefaultAlipayClient(URL, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
	}
}