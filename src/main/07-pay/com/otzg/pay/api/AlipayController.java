//package com.xgh.pay.api;
//
//import com.xgh.base.BaseController;
//import com.xgh.pay.alipay.AlipaySubmit;
//import com.xgh.pay.entity.PayOrders;
//import com.xgh.pay.service.PayOrdersServ;
//import com.xgh.util.UrlUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.UnsupportedEncodingException;
//import java.util.Map;
//
///**
// *  @author G/2015-10-13
// *
// */
//
//@RestController("alipay")
//public class AlipayController  extends BaseController {
//
//	//收款记录文件名
//	private static final String receiptlog = "receiptlog";
//
//	@Autowired
//	private PayOrdersServ payOrdersServ;
//
//
//	/**
//	 * 支付宝收款异步通知
//	 * @author G/2016-1-4 上午8:58:35
//	 * @throws UnsupportedEncodingException
//	 */
//	public final void payNotify() throws UnsupportedEncodingException{
//		HttpServletRequest request = getRequest();
//
//		if(AlipaySubmit.notifyCheck(request)){//验证成功
//
//			//获取支付宝POST过来反馈信息
//			Map<String,Object> params = UrlUtil.getRequestMap(request);
//			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
//
//			//////////////////////////////////////////////////////////////////////////////////////////
//			//请在这里加上平台的业务逻辑程序代码
//			/**
//			  *交易成功状态有两个
//			  *TRADE_FINISHED普通 即时到账的交易成功状态）
//			  *TRADE_SUCCESS（开通 了高级即时到账或机票分 销产品后的交易成功状态）
//			  */
//
//			if(params.get("trade_status").equals("TRADE_FINISHED")){
//				//注意：
//				//退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
//			}else if(params.get("trade_status").equals("TRADE_SUCCESS")){
//				//判断该笔订单是否在平台网站中已经做过处理
//				//如果没有做过处理，根据订单号（out_trade_no）在平台网站的订单系统中查到该笔订单的详细，并执行平台的业务程序
//				//如果有做过处理，不执行平台的业务程序
//				//注意：
//				//付款完成后，支付宝系统发送该交易状态通知
//			}
//
//			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
//			if(params.get("trade_status").equals("TRADE_FINISHED")
//					|| params.get("trade_status").equals("TRADE_SUCCESS")){
//
//				//根据支付渠道订单号判断是否已支付
//				if(!getTradeServ().doCheckByChannelNo(params.get("trade_no"))){
//					//根据平台支付订单号获取支付订单
//					PayOrders payOrders = payOrdersServ.findByPayOrdersNo(params.get("out_trade_no"));
//					if(payOrders!=null){
//						int result = getTradeServ().doReceiptByPayChannel(Constants.PAYTYPE_ALIPAY,params.get("trade_no"),
//								params.get("seller_id"),params.get("seller_email"),params.get("buyer_id"),
//								params.get("buyer_email"),new BigDecimal(params.get("total_amount")),1,
//								payOrders);
//
//						if(result==0){
//							//回复支付宝
//							sendBack("success");
//							//写入日志
//							write2TradeLog("收款成功:"+params.toString(),receiptlog);
//						}else if(result==3001){
//							//更新账户失败需要重新发送
//							sendBack("fail");
//							//写入日志
//							write2TradeLog("更新账户失败",receiptlog);
//						}else if(result==4001){
//							//保存记录失败需要重新发送
//							sendBack("fail");
//							//写入日志
//							write2TradeLog("保存收款记录失败",receiptlog);
//						}
//					}
//				}else{//表示已经再次接收到收款通知
//					//回复支付宝已经成功不用再发了
//					sendBack("success");
//				}
//			}
//		}else{//验证失败
//			write2TradeLog("验证失败:ip地址="+request.getRemoteAddr()+":"+request.getRemotePort(),receiptlog);
//		}
//	}
//
////
////	/**
////	 * 回复支付宝
////	 * @author:G/2017年9月28日
////	 * @param
////	 * @return
////	 */
////	private void sendBack(String msg){
////		getResponse().setContentType("text/plain;charset=UTF-8");
////		try{
////			getResponse().getWriter().write(msg);
////		}catch (final IOException ex){
////			LogUtil.print("回复支付宝失败"+ex);
////		}
////	}
////
////	//订单号(线上订单)
////	public String payOrdersNo;
////
////	//支付宝付款链接
////	public final void getAppReceipt(){
////		if(payOrdersNo==null){
////			sendParamError();
////			return;
////		}
////
////		JSONObject jo = getTradeServ().getAlipayAppReceipt(payOrdersNo);
////		if(!jo.getBoolean("success")){
////			sendJson(false,jo.getString("message"));
////			return;
////		}
////		sendJson(jo);
////	}
////
////	/**
////	 * 保存交易记录到文本文件
////	 * @author G/2015-12-23 上午10:52:18
////	 * @param str 记录内容
////	 * @param fn 文件名称前缀
////	 */
//// 	private final void write2TradeLog(String str,String fn){
//// 		LogUtil.saveTradeLog(str,fn);
////	}
////
//}
