package com.otzg.wxpay.api;

import com.otzg.pay.api.AbstractController;
import com.otzg.util.CheckUtil;
import com.otzg.util.UrlUtil;
import com.otzg.wxpay.util.TokenThread;
import com.otzg.wxpay.util.service.WXPayConfig;
import com.otzg.wxpay.util.service.WxpayUtil;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 *  @author G
 *
 */
@RestController
public class WxpayApi extends AbstractController {
	//存放快速交易成功订单号
	private static Map<String,String> successOrdersNo = new HashMap<String,String>();

	//收款记录文件名
	private static final String receiptlog = "receiptlog";
	//token 支付接口提供
	private static final String token = "";
	public String wechatTradeType="jsapi";

	/**
	 * 获取openid
	 * @author G/2016年11月23日 上午11:14:22
	 */
	@RequestMapping("/pay/code_get")
	public final void getCode(String code){
		//参数
        if(CheckUtil.isEmpty(code)){
        	sendParamError();
        	return;
        }

		//根据code换取openId
		JSONObject jo = new WxpayUtil().getOpenId(code);
		P("获取openid="+jo.toString());
		if(jo.get("openid")==null){
			sendFail(jo.toString());
			return;
		}

		HttpSession session=getSession();
		session.setAttribute("openid",jo.get("openid"));
		session.setAttribute("access_token",jo.get("access_token"));
		Object wxRedirectUrl=session.getAttribute("wxRedirectUrl");
		if(wxRedirectUrl!=null){
			try {
				getResponse().sendRedirect(wxRedirectUrl.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	//调取微信h5页面配置
	public final String getWxJsapi(){
		JSONObject WxConfigJson=new JSONObject();
		if(TokenThread.accessToken!=null){
			String accessToken=TokenThread.accessToken.getAccessToken();
			String ticket="";
			//如果尚未缓存jsapiTicket则重新获取
			if(TokenThread.jsapiTicket==null){
				TokenThread.jsapiTicket=new WxpayUtil().getJsapiTicket(accessToken);;
			}
			ticket=TokenThread.jsapiTicket;

			if(ticket!=null && !ticket.equals("")){
				String url= UrlUtil.getRequestURL(getRequest());
				Map<String,String> ret=WxpayUtil.JsapiTicketSign(ticket,url);
				WxConfigJson =JSONObject.fromObject(ret);
				//System.out.println(jo.toString());
			}
			WxConfigJson.put("appId",new WXPayConfig().getGzhAppId());
		}
		return "WxJsapiConfigPage";
	}











	//修改账户需要管理员密码
	public String adminPassword;
//	public final void savePayAccount(){
//		String openid = (String) getSession().getAttribute("openid");
//		if(openid==null){
//			sendJson(false,"请用微信扫码");
//			return;
//		}
//		if(uid==null){
//			sendJson(false,"请登陆后重新扫码");
//			return;
//		}
//		if(adminPassword==null || adminPassword.isEmpty()){
//			sendJson(false,"请输入管理员密码");
//			return;
//		}
//		if(!getMemberServ().checkAdminPassWord(uid,adminPassword)){
//			sendJson(false,"管理员密码错误设置失败");
//			return;
//		}
//		//设置提现账户
//		getTradeServ().saveAccount(WxpayConfig.GZHMCHID,openid,"wxpay",uid);
//		sendJson(true,"设置成功");
//	}
//
//	/**
//	 * 调取微信支付页面
//	 * 1、通知微信返回openid，微信通知到./wxpay/getCode.do
//	 * 2、得到openid后要从session里面获取下一步的链接
//	 * @author G/2016年11月23日 上午11:14:41
//	 */
//	public final String pay(){
//		//获取prepayid
//		Object openid = getSession().getAttribute("openid");
//		//如果已经获得了openid
//		if(openid!=null){
//			if(payOrdersNo==null){
//				sendJson(false,"订单号为空");
//				return null;
//			}
//
//			JSONObject jo=new JSONObject();
//			PayOrders payOrders = getPayOrdersServ().findByPayOrdersNo(payOrdersNo);
//			if(payOrders!=null){
//				//单位由元转换成分
//				BigDecimal amount=payOrders.getAmount().multiply(new BigDecimal("100"));
//				out_trade_no=payOrders.getNumber();
//				subject=payOrders.getSubject();
//				total_fee=amount.toString();
//				String xmlData=WxpaySubmit.ReceiveJsapiData(subject,out_trade_no,amount.doubleValue(),openid.toString());
//				jo=WxpaySubmit.getReceive(xmlData);
//			}
//			if(jo!=null && jo.get("prepay_id")!=null){
//				//调起微信支付的参数
//				WxPayJson = WxpayUtil.getJsapiPayJson(jo.getString("prepay_id"));
//			}
//			return "WxJsapiPayPage";
//		}else{
//
//			//保存下一步的链接地址供回调后的方法 跳转
//			getSession().setAttribute("wxRedirectUrl",Constants.SITEDOMAIN+"/wxpay/pay.do?payOrdersNo="+payOrdersNo);
//			//如果未获得openid
//			String url="https://open.weixin.qq.com/connect/oauth2/authorize?"
//					+ "appid="+WxpayConfig.GZHAPPID
//					+ "&redirect_uri="+CommonFunc.getUrlEncode(Constants.SITEDOMAIN+"/wxpay/getCode.do")
//					+ "&response_type=code"
//					+ "&scope=snsapi_base"
//					+ "&state=STATE#wechat_redirect";
//			try {
//	            //提交到微信
//				getResponse().sendRedirect(url);
//			} catch (IOException e) {
//			}
//		}
//		return null;
//	}
//
//	/**
//	 * 游客线下直扫二维码付款
//	 * @author G/2017年2月14日 上午10:44:40
//	 * @return
//	 */
//	public final String createH5Pay(){
//		//单号
//		String number = DateTimeFunc.CYearMonthDayTimeShort()+"Z"+(int)(Math.random()*900+100);
//		//提交到微信支付
//		try {
//			getResponse().sendRedirect(Constants.SITEDOMAIN+"/wxpay/pay.do?payOrdersNo="+number);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//
//	//测试微信网页二维码链接
//	public String wxpay_CodeUrl;
//
//
//	public final String payReturn(){
//		//从向量清除对应订单信息
//		if(out_trade_no!=null && !out_trade_no.isEmpty()){
//			successOrdersNo.remove(out_trade_no);
//		}
//		//设置返回值信息
//		state="1";
//		subject=CommonFunc.encoding2U8(subject);
//		message="支付成功!!";
//		return "payReturn";
//	}
//	/**
//	 * 把订单结果信息组合起来保存到向量
//	 * @author G/2016年10月27日 下午3:26:44
//	 * @param number
//	 * @param subject
//	 * @param fee
//	 * @param message
//	 */
//	private final void setSuccessOrders(String number,String subject,String fee,String message){
//		String str=number+","+subject+","+fee+","+message;
//		successOrdersNo.put(number,str);
//	}
//
//	/**
//	 * 供前台异步刷新用来确认订单是否已经支付成功
//	 * @author G/2016年10月27日 下午3:47:09
//	 */
//	public final void getOrdersStatus(){
//		String result="";
//		if(out_trade_no!=null){
//			result=successOrdersNo.get(out_trade_no);
//			//收到返回结果
//			if(result!=null && !result.isEmpty()){
//				String[] r= result.split(",");
//				if(r[3].equals("SUCCESS")){
//					JSONObject jo=new JSONObject();
//					jo.put("number",r[0]);
//					jo.put("subject",r[1]);
//					jo.put("price",r[2]);
//					sendJson(true,"支付成功",jo);
//				}else{
//					sendJson(false,r[3]);
//				}
//			}else{
//				sendJson(false,"尚未收到支付结果");
//			}
//		}
//	}
//
//	/**
//	 * 微信财付通收款异步通知
//	 * @author G/2016年10月17日 下午4:42:36
//	 * e.g.
//	 * <xml>
//	 * <appid><![CDATA[wx2421b1c4370ec43b]]></appid>
//	 * <attach><![CDATA[支付测试]]></attach>
//	 * <bank_type><![CDATA[CFT]]></bank_type>
//	 * <fee_type><![CDATA[CNY]]></fee_type>
//	 * <is_subscribe><![CDATA[Y]]></is_subscribe>
//	 * <mch_id><![CDATA[10000100]]></mch_id>
//	 * <nonce_str><![CDATA[5d2b6c2a8db53831f7eda20af46e531c]]></nonce_str>
//	 * <openid><![CDATA[oUpF8uMEb4qRXf22hE3X68TekukE]]></openid>
//	 * <out_trade_no><![CDATA[1409811653]]></out_trade_no>
//	 * <result_code><![CDATA[SUCCESS]]></result_code>
//	 * <return_code><![CDATA[SUCCESS]]></return_code>
//	 * <sign><![CDATA[B552ED6B279343CB493C5DD0D78AB241]]></sign>
//	 * <sub_mch_id><![CDATA[10000100]]></sub_mch_id>
//	 * <time_end><![CDATA[20140903131540]]></time_end>
//	 * <total_fee>1</total_fee>
//	 * <trade_type><![CDATA[JSAPI]]></trade_type>
//	 * <transaction_id><![CDATA[1004400740201409030005092168]]></transaction_id>
//	 * </xml>
//	 */
//	public final void payNotify(){
//		request=getRequest();
//	    //解析结果存储在HashMap
//	    Map<String,String> params = HtmlUtil.getMapFromRequest(request);
//	    //校验签名
//	    if(WxpayUtil.verify(params)){
//	    	String returnCode = params.get("return_code");
//		    if(returnCode.equals("SUCCESS")) {
//		        String resultCode = params.get("result_code");
//		        //平台支付订单号
//		        String outTradeNo = params.get("out_trade_no");
//		        if(resultCode.equals("SUCCESS")) {
//		        	//根据支付渠道订单号判断是否已支付
//					if(!getTradeServ().doCheckByChannelNo(params.get("trade_no"))){
//						//根据平台支付订单号获取支付订单
//						PayOrders  payOrders = getPayOrdersServ().findByPayOrdersNo(outTradeNo);
//						if(payOrders!=null){
//							//微信收款金额返回的单位为分->转换成元
//							BigDecimal amount=new BigDecimal(params.get("total_fee")).divide(new BigDecimal("100"));
//							getTradeServ().doReceiptByPayChannel(Constants.PAYTYPE_WXPAY,params.get("transaction_id"),
//									params.get("mch_id"),"",params.get("openid"),
//									"",amount,1,
//									payOrders);
//						}
//					}
//
//		        	//写入日志
//					write2TradeLog("支付成功:"+params.toString(),receiptlog);
//					sendBack("SUCCESS");
//		        }else{
//		        	//把结果放入向量供页面实时查询结果
//					setSuccessOrders(outTradeNo,"","",params.get("err_code_des"));
//		            write2TradeLog("支付业务失败:ip地址="+request.getRemoteAddr()+":"+request.getRemotePort()+"内容="+params.toString(),receiptlog);
//		            sendBack("FAIL");
//		        }
//		    }else{
//		        write2TradeLog("支付失败:ip地址="+request.getRemoteAddr()+":"+request.getRemotePort()+"内容="+params.toString(),receiptlog);
//		        sendBack("FAIL");
//		    }
//	    }else{
//	    	write2TradeLog("验证失败:ip地址="+request.getRemoteAddr()+":"+request.getRemotePort(),receiptlog);
//	    	sendBack("FAIL");
//	    }//校验成功
//	}
//
//	/**
//	 * 回复微信收款成功
//	 * @author:G/2017年9月28日
//	 * @param msg=SUCCESS/FAIL
//	 * @return
//	 */
//	private void sendBack(String msg){
//		getResponse().setContentType("text/plain;charset=UTF-8");
//		 //返回微信财富同的map
//	    Map<String,String> retMap = new HashMap<String, String>();
//	    //构建微信回复
//        retMap.put("return_code",msg);
//		try{
//			getResponse().getWriter().print(XmlUtil.Map2Xml(retMap));
//			CommonFunc.write2log("回复微信财付通成功!!");
//		}catch (final IOException ex){
//			CommonFunc.write2log("回复财付通失败"+ex);
//		}
//	}
//
//	/**
//	 * 保存交易记录到文本文件
//	 * @author G/2015-12-23 上午10:52:18
//	 * @param str 记录内容
//	 * @param fn 文件名称前缀
//	 */
// 	private final void write2TradeLog(String str,String fn){
// 		CommonFunc.savePaymentLog(str,fn);
//	}
//
// 	private final TradeServ getTradeServ() {
//		return (TradeServ) getAppContext().getBean(Constants.SPRING_TRADINGBUSINESS_SERVER);
//	}
//	//订单服务
//	private final PayOrdersServ getPayOrdersServ(){
//		return (PayOrdersServ) getAppContext().getBean(Constants.SPRING_PAYORDERS_SERVER);
//	}
//	// 获取MemberService对象
//	private final MemberServ getMemberServ() {
//		return (MemberServ) getAppContext().getBean(Constants.SPRING_MEMBER_SERVER);
//	}
}
