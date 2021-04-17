package com.otzg.alipay.redbag.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.otzg.alipay.redbag.entity.ApiInfoModel;
import com.otzg.alipay.redbag.entity.ApiParamModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class MainController {

    @RequestMapping(value="/main.htm")
    public String main(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
		return "api/main";
    }

    @RequestMapping(value="/getApiInfo.json", method = RequestMethod.POST)
    @ResponseBody
    public Object getApiInfo(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap){
        List<ApiInfoModel> list = new ArrayList<ApiInfoModel>();

		ApiInfoModel alipayMarketingCampaignCashStatusModifyInfoModel = new ApiInfoModel();
		list.add(alipayMarketingCampaignCashStatusModifyInfoModel);
		alipayMarketingCampaignCashStatusModifyInfoModel.setApiName("alipay.marketing.campaign.cash.status.modify");
		alipayMarketingCampaignCashStatusModifyInfoModel.setApiZhName("更改现金活动状态");
		alipayMarketingCampaignCashStatusModifyInfoModel.setInvokeType(ApiInfoModel.INVOKE_TYPE_REQUEST);
		List<ApiParamModel> alipayMarketingCampaignCashStatusModifyApiInParamChilds = new ArrayList<ApiParamModel>();
		alipayMarketingCampaignCashStatusModifyInfoModel.setApiInParam(alipayMarketingCampaignCashStatusModifyApiInParamChilds);
        ApiParamModel alipayMarketingCampaignCashStatusModifyApiInParam_0 = new ApiParamModel();
        alipayMarketingCampaignCashStatusModifyApiInParamChilds.add(alipayMarketingCampaignCashStatusModifyApiInParam_0);
        alipayMarketingCampaignCashStatusModifyApiInParam_0.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashStatusModifyApiInParam_0.setTitle("要修改的现金红包活动号");
        alipayMarketingCampaignCashStatusModifyApiInParam_0.setDesc("");
        alipayMarketingCampaignCashStatusModifyApiInParam_0.setDescription("要修改的现金红包活动号");
        alipayMarketingCampaignCashStatusModifyApiInParam_0.setIsMust(1);
        alipayMarketingCampaignCashStatusModifyApiInParam_0.setIsListType(false);
        alipayMarketingCampaignCashStatusModifyApiInParam_0.setFullParamName("crowdNo");
        alipayMarketingCampaignCashStatusModifyApiInParam_0.setEnName("crowd_no");


        ApiParamModel alipayMarketingCampaignCashStatusModifyApiInParam_1 = new ApiParamModel();
        alipayMarketingCampaignCashStatusModifyApiInParamChilds.add(alipayMarketingCampaignCashStatusModifyApiInParam_1);
        alipayMarketingCampaignCashStatusModifyApiInParam_1.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashStatusModifyApiInParam_1.setTitle("修改后的活动状态");
        alipayMarketingCampaignCashStatusModifyApiInParam_1.setDesc(" PAUSE或者READY或者CLOSED");
        alipayMarketingCampaignCashStatusModifyApiInParam_1.setDescription("修改后的活动状态, PAUSE或者READY或者CLOSED");
        alipayMarketingCampaignCashStatusModifyApiInParam_1.setIsMust(1);
        alipayMarketingCampaignCashStatusModifyApiInParam_1.setIsListType(false);
        alipayMarketingCampaignCashStatusModifyApiInParam_1.setFullParamName("campStatus");
        alipayMarketingCampaignCashStatusModifyApiInParam_1.setEnName("camp_status");



		List<ApiParamModel> alipayMarketingCampaignCashStatusModifyApiOutParamChilds = new ArrayList<ApiParamModel>();
		alipayMarketingCampaignCashStatusModifyInfoModel.setApiOutParam(alipayMarketingCampaignCashStatusModifyApiOutParamChilds);

		ApiInfoModel alipayMarketingCampaignCashTriggerInfoModel = new ApiInfoModel();
		list.add(alipayMarketingCampaignCashTriggerInfoModel);
		alipayMarketingCampaignCashTriggerInfoModel.setApiName("alipay.marketing.campaign.cash.trigger");
		alipayMarketingCampaignCashTriggerInfoModel.setApiZhName("触发现金红包活动");
		alipayMarketingCampaignCashTriggerInfoModel.setInvokeType(ApiInfoModel.INVOKE_TYPE_REQUEST);
		List<ApiParamModel> alipayMarketingCampaignCashTriggerApiInParamChilds = new ArrayList<ApiParamModel>();
		alipayMarketingCampaignCashTriggerInfoModel.setApiInParam(alipayMarketingCampaignCashTriggerApiInParamChilds);
        ApiParamModel alipayMarketingCampaignCashTriggerApiInParam_1 = new ApiParamModel();
        alipayMarketingCampaignCashTriggerApiInParamChilds.add(alipayMarketingCampaignCashTriggerApiInParam_1);
        alipayMarketingCampaignCashTriggerApiInParam_1.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashTriggerApiInParam_1.setTitle("现金活动号");
        alipayMarketingCampaignCashTriggerApiInParam_1.setDesc("");
        alipayMarketingCampaignCashTriggerApiInParam_1.setDescription("现金活动号");
        alipayMarketingCampaignCashTriggerApiInParam_1.setIsMust(1);
        alipayMarketingCampaignCashTriggerApiInParam_1.setIsListType(false);
        alipayMarketingCampaignCashTriggerApiInParam_1.setFullParamName("crowdNo");
        alipayMarketingCampaignCashTriggerApiInParam_1.setEnName("crowd_no");


        ApiParamModel alipayMarketingCampaignCashTriggerApiInParam_3 = new ApiParamModel();
        alipayMarketingCampaignCashTriggerApiInParamChilds.add(alipayMarketingCampaignCashTriggerApiInParam_3);
        alipayMarketingCampaignCashTriggerApiInParam_3.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashTriggerApiInParam_3.setTitle("此字段如果传入金额");
        alipayMarketingCampaignCashTriggerApiInParam_3.setDesc("就忽略prize_type算法，按照传入的金额发奖。如果不传或者小于等于0，则按照活动创建时指定的prize_type为fixed或者random算法发奖");
        alipayMarketingCampaignCashTriggerApiInParam_3.setDescription("此字段如果传入金额，就忽略prize_type算法，按照传入的金额发奖。如果不传或者小于等于0，则按照活动创建时指定的prize_type为fixed或者random算法发奖");
        alipayMarketingCampaignCashTriggerApiInParam_3.setIsMust(3);
        alipayMarketingCampaignCashTriggerApiInParam_3.setIsListType(false);
        alipayMarketingCampaignCashTriggerApiInParam_3.setFullParamName("orderPrice");
        alipayMarketingCampaignCashTriggerApiInParam_3.setEnName("order_price");


        ApiParamModel alipayMarketingCampaignCashTriggerApiInParam_4 = new ApiParamModel();
        alipayMarketingCampaignCashTriggerApiInParamChilds.add(alipayMarketingCampaignCashTriggerApiInParam_4);
        alipayMarketingCampaignCashTriggerApiInParam_4.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashTriggerApiInParam_4.setTitle("领取红包的外部业务号");
        alipayMarketingCampaignCashTriggerApiInParam_4.setDesc("只由可由字母、数字、下划线组成。同一个活动中不可重复，相同的外部业务号会被幂等并返回之前的结果。不填时，系统会生成一个默认固定的外部业务号。");
        alipayMarketingCampaignCashTriggerApiInParam_4.setDescription("领取红包的外部业务号，只由可由字母、数字、下划线组成。同一个活动中不可重复，相同的外部业务号会被幂等并返回之前的结果。不填时，系统会生成一个默认固定的外部业务号。");
        alipayMarketingCampaignCashTriggerApiInParam_4.setIsMust(3);
        alipayMarketingCampaignCashTriggerApiInParam_4.setIsListType(false);
        alipayMarketingCampaignCashTriggerApiInParam_4.setFullParamName("outBizNo");
        alipayMarketingCampaignCashTriggerApiInParam_4.setEnName("out_biz_no");


        ApiParamModel alipayMarketingCampaignCashTriggerApiInParam_0 = new ApiParamModel();
        alipayMarketingCampaignCashTriggerApiInParamChilds.add(alipayMarketingCampaignCashTriggerApiInParam_0);
        alipayMarketingCampaignCashTriggerApiInParam_0.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashTriggerApiInParam_0.setTitle("用户唯一标识userId");
        alipayMarketingCampaignCashTriggerApiInParam_0.setDesc("user_id与login_id至少有一个非空；都非空时，以user_id为准。");
        alipayMarketingCampaignCashTriggerApiInParam_0.setDescription("用户唯一标识userId。user_id与login_id至少有一个非空；都非空时，以user_id为准。");
        alipayMarketingCampaignCashTriggerApiInParam_0.setIsMust(2);
        alipayMarketingCampaignCashTriggerApiInParam_0.setIsListType(false);
        alipayMarketingCampaignCashTriggerApiInParam_0.setFullParamName("userId");
        alipayMarketingCampaignCashTriggerApiInParam_0.setEnName("user_id");


        ApiParamModel alipayMarketingCampaignCashTriggerApiInParam_2 = new ApiParamModel();
        alipayMarketingCampaignCashTriggerApiInParamChilds.add(alipayMarketingCampaignCashTriggerApiInParam_2);
        alipayMarketingCampaignCashTriggerApiInParam_2.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashTriggerApiInParam_2.setTitle("用户登录账号名：邮箱或手机号");
        alipayMarketingCampaignCashTriggerApiInParam_2.setDesc("user_id与login_id至少有一个非空，都非空时，以user_id为准。");
        alipayMarketingCampaignCashTriggerApiInParam_2.setDescription("用户登录账号名：邮箱或手机号。user_id与login_id至少有一个非空，都非空时，以user_id为准。");
        alipayMarketingCampaignCashTriggerApiInParam_2.setIsMust(2);
        alipayMarketingCampaignCashTriggerApiInParam_2.setIsListType(false);
        alipayMarketingCampaignCashTriggerApiInParam_2.setFullParamName("loginId");
        alipayMarketingCampaignCashTriggerApiInParam_2.setEnName("login_id");



		List<ApiParamModel> alipayMarketingCampaignCashTriggerApiOutParamChilds = new ArrayList<ApiParamModel>();
		alipayMarketingCampaignCashTriggerInfoModel.setApiOutParam(alipayMarketingCampaignCashTriggerApiOutParamChilds);
        ApiParamModel alipayMarketingCampaignCashTriggerApiOutParam_0 = new ApiParamModel();
        alipayMarketingCampaignCashTriggerApiOutParamChilds.add(alipayMarketingCampaignCashTriggerApiOutParam_0);
        alipayMarketingCampaignCashTriggerApiOutParam_0.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashTriggerApiOutParam_0.setTitle("是否中奖结果状态");
        alipayMarketingCampaignCashTriggerApiOutParam_0.setDesc("取值为true或false。如果为true表示发奖成功，这时返回的结果中的其他字段非空；如果为false表示发奖失败，这时返回的其他字段为空");
        alipayMarketingCampaignCashTriggerApiOutParam_0.setDescription("是否中奖结果状态，取值为true或false。如果为true表示发奖成功，这时返回的结果中的其他字段非空；如果为false表示发奖失败，这时返回的其他字段为空");
        alipayMarketingCampaignCashTriggerApiOutParam_0.setIsMust(1);
        alipayMarketingCampaignCashTriggerApiOutParam_0.setIsListType(false);
        alipayMarketingCampaignCashTriggerApiOutParam_0.setFullParamName("triggerResult");
        alipayMarketingCampaignCashTriggerApiOutParam_0.setEnName("trigger_result");


        ApiParamModel alipayMarketingCampaignCashTriggerApiOutParam_1 = new ApiParamModel();
        alipayMarketingCampaignCashTriggerApiOutParamChilds.add(alipayMarketingCampaignCashTriggerApiOutParam_1);
        alipayMarketingCampaignCashTriggerApiOutParam_1.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashTriggerApiOutParam_1.setTitle("现金红包金额");
        alipayMarketingCampaignCashTriggerApiOutParam_1.setDesc("发奖成功时非空，单位为元，保留两位小数");
        alipayMarketingCampaignCashTriggerApiOutParam_1.setDescription("现金红包金额，发奖成功时非空，单位为元，保留两位小数");
        alipayMarketingCampaignCashTriggerApiOutParam_1.setIsMust(2);
        alipayMarketingCampaignCashTriggerApiOutParam_1.setIsListType(false);
        alipayMarketingCampaignCashTriggerApiOutParam_1.setFullParamName("prizeAmount");
        alipayMarketingCampaignCashTriggerApiOutParam_1.setEnName("prize_amount");


        ApiParamModel alipayMarketingCampaignCashTriggerApiOutParam_2 = new ApiParamModel();
        alipayMarketingCampaignCashTriggerApiOutParamChilds.add(alipayMarketingCampaignCashTriggerApiOutParam_2);
        alipayMarketingCampaignCashTriggerApiOutParam_2.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashTriggerApiOutParam_2.setTitle("用户是否重复领取");
        alipayMarketingCampaignCashTriggerApiOutParam_2.setDesc("如果重复领取，返回的是之前的中奖结果，如果是首次领取，则走发奖流程");
        alipayMarketingCampaignCashTriggerApiOutParam_2.setDescription("用户是否重复领取，如果重复领取，返回的是之前的中奖结果，如果是首次领取，则走发奖流程");
        alipayMarketingCampaignCashTriggerApiOutParam_2.setIsMust(2);
        alipayMarketingCampaignCashTriggerApiOutParam_2.setIsListType(false);
        alipayMarketingCampaignCashTriggerApiOutParam_2.setFullParamName("repeatTriggerFlag");
        alipayMarketingCampaignCashTriggerApiOutParam_2.setEnName("repeat_trigger_flag");


        ApiParamModel alipayMarketingCampaignCashTriggerApiOutParam_3 = new ApiParamModel();
        alipayMarketingCampaignCashTriggerApiOutParamChilds.add(alipayMarketingCampaignCashTriggerApiOutParam_3);
        alipayMarketingCampaignCashTriggerApiOutParam_3.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashTriggerApiOutParam_3.setTitle("发送红包商户签约pid");
        alipayMarketingCampaignCashTriggerApiOutParam_3.setDesc("发奖成功时非空");
        alipayMarketingCampaignCashTriggerApiOutParam_3.setDescription("发送红包商户签约pid，发奖成功时非空");
        alipayMarketingCampaignCashTriggerApiOutParam_3.setIsMust(2);
        alipayMarketingCampaignCashTriggerApiOutParam_3.setIsListType(false);
        alipayMarketingCampaignCashTriggerApiOutParam_3.setFullParamName("partnerId");
        alipayMarketingCampaignCashTriggerApiOutParam_3.setEnName("partner_id");


        ApiParamModel alipayMarketingCampaignCashTriggerApiOutParam_4 = new ApiParamModel();
        alipayMarketingCampaignCashTriggerApiOutParamChilds.add(alipayMarketingCampaignCashTriggerApiOutParam_4);
        alipayMarketingCampaignCashTriggerApiOutParam_4.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashTriggerApiOutParam_4.setTitle("用户领取失败的错误信息描述");
        alipayMarketingCampaignCashTriggerApiOutParam_4.setDesc("");
        alipayMarketingCampaignCashTriggerApiOutParam_4.setDescription("用户领取失败的错误信息描述");
        alipayMarketingCampaignCashTriggerApiOutParam_4.setIsMust(2);
        alipayMarketingCampaignCashTriggerApiOutParam_4.setIsListType(false);
        alipayMarketingCampaignCashTriggerApiOutParam_4.setFullParamName("errorMsg");
        alipayMarketingCampaignCashTriggerApiOutParam_4.setEnName("error_msg");


        ApiParamModel alipayMarketingCampaignCashTriggerApiOutParam_5 = new ApiParamModel();
        alipayMarketingCampaignCashTriggerApiOutParamChilds.add(alipayMarketingCampaignCashTriggerApiOutParam_5);
        alipayMarketingCampaignCashTriggerApiOutParam_5.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashTriggerApiOutParam_5.setTitle("红包名称");
        alipayMarketingCampaignCashTriggerApiOutParam_5.setDesc("创建活动时设置，用于账单列表和详情、红包列表和详情的展示");
        alipayMarketingCampaignCashTriggerApiOutParam_5.setDescription("红包名称,创建活动时设置，用于账单列表和详情、红包列表和详情的展示");
        alipayMarketingCampaignCashTriggerApiOutParam_5.setIsMust(2);
        alipayMarketingCampaignCashTriggerApiOutParam_5.setIsListType(false);
        alipayMarketingCampaignCashTriggerApiOutParam_5.setFullParamName("couponName");
        alipayMarketingCampaignCashTriggerApiOutParam_5.setEnName("coupon_name");


        ApiParamModel alipayMarketingCampaignCashTriggerApiOutParam_6 = new ApiParamModel();
        alipayMarketingCampaignCashTriggerApiOutParamChilds.add(alipayMarketingCampaignCashTriggerApiOutParam_6);
        alipayMarketingCampaignCashTriggerApiOutParam_6.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashTriggerApiOutParam_6.setTitle("活动文案");
        alipayMarketingCampaignCashTriggerApiOutParam_6.setDesc("用于账单的备注展示、红包详情页的文案展示");
        alipayMarketingCampaignCashTriggerApiOutParam_6.setDescription("活动文案,用于账单的备注展示、红包详情页的文案展示");
        alipayMarketingCampaignCashTriggerApiOutParam_6.setIsMust(2);
        alipayMarketingCampaignCashTriggerApiOutParam_6.setIsListType(false);
        alipayMarketingCampaignCashTriggerApiOutParam_6.setFullParamName("prizeMsg");
        alipayMarketingCampaignCashTriggerApiOutParam_6.setEnName("prize_msg");


        ApiParamModel alipayMarketingCampaignCashTriggerApiOutParam_7 = new ApiParamModel();
        alipayMarketingCampaignCashTriggerApiOutParamChilds.add(alipayMarketingCampaignCashTriggerApiOutParam_7);
        alipayMarketingCampaignCashTriggerApiOutParam_7.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashTriggerApiOutParam_7.setTitle("商户头像logo的图片url地址");
        alipayMarketingCampaignCashTriggerApiOutParam_7.setDesc("用于账单和红包列表、详情的展示");
        alipayMarketingCampaignCashTriggerApiOutParam_7.setDescription("商户头像logo的图片url地址，用于账单和红包列表、详情的展示");
        alipayMarketingCampaignCashTriggerApiOutParam_7.setIsMust(2);
        alipayMarketingCampaignCashTriggerApiOutParam_7.setIsListType(false);
        alipayMarketingCampaignCashTriggerApiOutParam_7.setFullParamName("merchantLogo");
        alipayMarketingCampaignCashTriggerApiOutParam_7.setEnName("merchant_logo");


        ApiParamModel alipayMarketingCampaignCashTriggerApiOutParam_8 = new ApiParamModel();
        alipayMarketingCampaignCashTriggerApiOutParamChilds.add(alipayMarketingCampaignCashTriggerApiOutParam_8);
        alipayMarketingCampaignCashTriggerApiOutParam_8.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashTriggerApiOutParam_8.setTitle("支付宝业务号");
        alipayMarketingCampaignCashTriggerApiOutParam_8.setDesc("发奖成功时返回,调用者可提供此字符串进行问题排查与核对等");
        alipayMarketingCampaignCashTriggerApiOutParam_8.setDescription("支付宝业务号,发奖成功时返回,调用者可提供此字符串进行问题排查与核对等");
        alipayMarketingCampaignCashTriggerApiOutParam_8.setIsMust(2);
        alipayMarketingCampaignCashTriggerApiOutParam_8.setIsListType(false);
        alipayMarketingCampaignCashTriggerApiOutParam_8.setFullParamName("bizNo");
        alipayMarketingCampaignCashTriggerApiOutParam_8.setEnName("biz_no");


        ApiParamModel alipayMarketingCampaignCashTriggerApiOutParam_9 = new ApiParamModel();
        alipayMarketingCampaignCashTriggerApiOutParamChilds.add(alipayMarketingCampaignCashTriggerApiOutParam_9);
        alipayMarketingCampaignCashTriggerApiOutParam_9.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashTriggerApiOutParam_9.setTitle("外部业务号");
        alipayMarketingCampaignCashTriggerApiOutParam_9.setDesc("回填请求中的out_biz_no,请求参数中传了out_biz_no就会回传回去，如果不传就回传默认的\"default_out_biz_no\"，请求者可用于日志记录与核对等");
        alipayMarketingCampaignCashTriggerApiOutParam_9.setDescription("外部业务号,回填请求中的out_biz_no,请求参数中传了out_biz_no就会回传回去，如果不传就回传默认的\"default_out_biz_no\"，请求者可用于日志记录与核对等");
        alipayMarketingCampaignCashTriggerApiOutParam_9.setIsMust(2);
        alipayMarketingCampaignCashTriggerApiOutParam_9.setIsListType(false);
        alipayMarketingCampaignCashTriggerApiOutParam_9.setFullParamName("outBizNo");
        alipayMarketingCampaignCashTriggerApiOutParam_9.setEnName("out_biz_no");



		ApiInfoModel alipayMarketingCampaignCashCreateInfoModel = new ApiInfoModel();
		list.add(alipayMarketingCampaignCashCreateInfoModel);
		alipayMarketingCampaignCashCreateInfoModel.setApiName("alipay.marketing.campaign.cash.create");
		alipayMarketingCampaignCashCreateInfoModel.setApiZhName("创建现金活动");
		alipayMarketingCampaignCashCreateInfoModel.setInvokeType(ApiInfoModel.INVOKE_TYPE_REQUEST);
		List<ApiParamModel> alipayMarketingCampaignCashCreateApiInParamChilds = new ArrayList<ApiParamModel>();
		alipayMarketingCampaignCashCreateInfoModel.setApiInParam(alipayMarketingCampaignCashCreateApiInParamChilds);
        ApiParamModel alipayMarketingCampaignCashCreateApiInParam_0 = new ApiParamModel();
        alipayMarketingCampaignCashCreateApiInParamChilds.add(alipayMarketingCampaignCashCreateApiInParam_0);
        alipayMarketingCampaignCashCreateApiInParam_0.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashCreateApiInParam_0.setTitle("红包名称");
        alipayMarketingCampaignCashCreateApiInParam_0.setDesc("商户在查询列表、详情看到的名字,同时也会显示在商户付款页面。");
        alipayMarketingCampaignCashCreateApiInParam_0.setDescription("红包名称,商户在查询列表、详情看到的名字,同时也会显示在商户付款页面。");
        alipayMarketingCampaignCashCreateApiInParam_0.setIsMust(1);
        alipayMarketingCampaignCashCreateApiInParam_0.setIsListType(false);
        alipayMarketingCampaignCashCreateApiInParam_0.setFullParamName("couponName");
        alipayMarketingCampaignCashCreateApiInParam_0.setEnName("coupon_name");


        ApiParamModel alipayMarketingCampaignCashCreateApiInParam_1 = new ApiParamModel();
        alipayMarketingCampaignCashCreateApiInParamChilds.add(alipayMarketingCampaignCashCreateApiInParam_1);
        alipayMarketingCampaignCashCreateApiInParam_1.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashCreateApiInParam_1.setTitle("现金红包的发放形式");
        alipayMarketingCampaignCashCreateApiInParam_1.setDesc(" fixed为固定金额,random为随机金额。选择随机金额时，单个红包的金额在平均金额的0.5~1.5倍之间浮动。");
        alipayMarketingCampaignCashCreateApiInParam_1.setDescription("现金红包的发放形式, fixed为固定金额,random为随机金额。选择随机金额时，单个红包的金额在平均金额的0.5~1.5倍之间浮动。");
        alipayMarketingCampaignCashCreateApiInParam_1.setIsMust(1);
        alipayMarketingCampaignCashCreateApiInParam_1.setIsListType(false);
        alipayMarketingCampaignCashCreateApiInParam_1.setFullParamName("prizeType");
        alipayMarketingCampaignCashCreateApiInParam_1.setEnName("prize_type");


        ApiParamModel alipayMarketingCampaignCashCreateApiInParam_2 = new ApiParamModel();
        alipayMarketingCampaignCashCreateApiInParamChilds.add(alipayMarketingCampaignCashCreateApiInParam_2);
        alipayMarketingCampaignCashCreateApiInParam_2.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashCreateApiInParam_2.setTitle("活动发放的现金总金额");
        alipayMarketingCampaignCashCreateApiInParam_2.setDesc("最小金额1.00元,最大金额10000000.00元。每个红包的最大金额不允许超过200元,最小金额不得低于0.20元。 实际的金额限制可能会根据业务进行动态调整。");
        alipayMarketingCampaignCashCreateApiInParam_2.setDescription("活动发放的现金总金额,最小金额1.00元,最大金额10000000.00元。每个红包的最大金额不允许超过200元,最小金额不得低于0.20元。 实际的金额限制可能会根据业务进行动态调整。");
        alipayMarketingCampaignCashCreateApiInParam_2.setIsMust(1);
        alipayMarketingCampaignCashCreateApiInParam_2.setIsListType(false);
        alipayMarketingCampaignCashCreateApiInParam_2.setFullParamName("totalMoney");
        alipayMarketingCampaignCashCreateApiInParam_2.setEnName("total_money");


        ApiParamModel alipayMarketingCampaignCashCreateApiInParam_3 = new ApiParamModel();
        alipayMarketingCampaignCashCreateApiInParamChilds.add(alipayMarketingCampaignCashCreateApiInParam_3);
        alipayMarketingCampaignCashCreateApiInParam_3.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashCreateApiInParam_3.setTitle("红包发放个数");
        alipayMarketingCampaignCashCreateApiInParam_3.setDesc("最小1个,最大10000000个。  但不同的发放形式（即prize_type）会使得含义不同：  (1) 若prize_type选择为固定金额，每个用户领取的红包金额为total_money除以total_num得到固定金额。  (2) 若prize_type选择为随机金额，每个用户领取的红包金额为total_money除以total_num得到的平均金额值的0.5~1.5倍。由于金额是随机的，在红包金额全部被领取完时，有可能total_num有所剩余、或者大于设置值的情况。");
        alipayMarketingCampaignCashCreateApiInParam_3.setDescription("红包发放个数，最小1个,最大10000000个。  但不同的发放形式（即prize_type）会使得含义不同：  (1) 若prize_type选择为固定金额，每个用户领取的红包金额为total_money除以total_num得到固定金额。  (2) 若prize_type选择为随机金额，每个用户领取的红包金额为total_money除以total_num得到的平均金额值的0.5~1.5倍。由于金额是随机的，在红包金额全部被领取完时，有可能total_num有所剩余、或者大于设置值的情况。");
        alipayMarketingCampaignCashCreateApiInParam_3.setIsMust(1);
        alipayMarketingCampaignCashCreateApiInParam_3.setIsListType(false);
        alipayMarketingCampaignCashCreateApiInParam_3.setFullParamName("totalNum");
        alipayMarketingCampaignCashCreateApiInParam_3.setEnName("total_num");


        ApiParamModel alipayMarketingCampaignCashCreateApiInParam_4 = new ApiParamModel();
        alipayMarketingCampaignCashCreateApiInParamChilds.add(alipayMarketingCampaignCashCreateApiInParam_4);
        alipayMarketingCampaignCashCreateApiInParam_4.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashCreateApiInParam_4.setTitle("活动文案");
        alipayMarketingCampaignCashCreateApiInParam_4.setDesc("用户在账单、红包中看到的账单描述、红包描述");
        alipayMarketingCampaignCashCreateApiInParam_4.setDescription("活动文案,用户在账单、红包中看到的账单描述、红包描述");
        alipayMarketingCampaignCashCreateApiInParam_4.setIsMust(1);
        alipayMarketingCampaignCashCreateApiInParam_4.setIsListType(false);
        alipayMarketingCampaignCashCreateApiInParam_4.setFullParamName("prizeMsg");
        alipayMarketingCampaignCashCreateApiInParam_4.setEnName("prize_msg");


        ApiParamModel alipayMarketingCampaignCashCreateApiInParam_5 = new ApiParamModel();
        alipayMarketingCampaignCashCreateApiInParamChilds.add(alipayMarketingCampaignCashCreateApiInParam_5);
        alipayMarketingCampaignCashCreateApiInParam_5.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashCreateApiInParam_5.setTitle("活动开始时间");
        alipayMarketingCampaignCashCreateApiInParam_5.setDesc("必须大于活动创建的时间.   (1) 填固定时间:2016-08-10 22:28:30, 基本格式:yyyy-MM-dd HH:mm:ss  (2) 填字符串NowTime");
        alipayMarketingCampaignCashCreateApiInParam_5.setDescription("活动开始时间,必须大于活动创建的时间.   (1) 填固定时间:2016-08-10 22:28:30, 基本格式:yyyy-MM-dd HH:mm:ss  (2) 填字符串NowTime");
        alipayMarketingCampaignCashCreateApiInParam_5.setIsMust(1);
        alipayMarketingCampaignCashCreateApiInParam_5.setIsListType(false);
        alipayMarketingCampaignCashCreateApiInParam_5.setFullParamName("startTime");
        alipayMarketingCampaignCashCreateApiInParam_5.setEnName("start_time");


        ApiParamModel alipayMarketingCampaignCashCreateApiInParam_6 = new ApiParamModel();
        alipayMarketingCampaignCashCreateApiInParamChilds.add(alipayMarketingCampaignCashCreateApiInParam_6);
        alipayMarketingCampaignCashCreateApiInParam_6.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashCreateApiInParam_6.setTitle("活动结束时间");
        alipayMarketingCampaignCashCreateApiInParam_6.setDesc("必须大于活动开始时间, 基本格式:yyyy-MM-dd HH:mm:ss,活动有效时间最长为6个月，过期后需要创建新的活动。");
        alipayMarketingCampaignCashCreateApiInParam_6.setDescription("活动结束时间,必须大于活动开始时间, 基本格式:yyyy-MM-dd HH:mm:ss,活动有效时间最长为6个月，过期后需要创建新的活动。");
        alipayMarketingCampaignCashCreateApiInParam_6.setIsMust(1);
        alipayMarketingCampaignCashCreateApiInParam_6.setIsListType(false);
        alipayMarketingCampaignCashCreateApiInParam_6.setFullParamName("endTime");
        alipayMarketingCampaignCashCreateApiInParam_6.setEnName("end_time");


        ApiParamModel alipayMarketingCampaignCashCreateApiInParam_7 = new ApiParamModel();
        alipayMarketingCampaignCashCreateApiInParamChilds.add(alipayMarketingCampaignCashCreateApiInParam_7);
        alipayMarketingCampaignCashCreateApiInParam_7.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashCreateApiInParam_7.setTitle("商户打款后的跳转链接");
        alipayMarketingCampaignCashCreateApiInParam_7.setDesc("从支付宝收银台打款成功后的跳转链接。不填时，打款后停留在支付宝支付成功页。商户实际跳转会自动添加crowdNo作为跳转参数。示例: http://www.yourhomepage.com?crowdNo=XXX");
        alipayMarketingCampaignCashCreateApiInParam_7.setDescription("商户打款后的跳转链接，从支付宝收银台打款成功后的跳转链接。不填时，打款后停留在支付宝支付成功页。商户实际跳转会自动添加crowdNo作为跳转参数。示例: http://www.yourhomepage.com?crowdNo=XXX");
        alipayMarketingCampaignCashCreateApiInParam_7.setIsMust(3);
        alipayMarketingCampaignCashCreateApiInParam_7.setIsListType(false);
        alipayMarketingCampaignCashCreateApiInParam_7.setFullParamName("merchantLink");
        alipayMarketingCampaignCashCreateApiInParam_7.setEnName("merchant_link");


        ApiParamModel alipayMarketingCampaignCashCreateApiInParam_8 = new ApiParamModel();
        alipayMarketingCampaignCashCreateApiInParamChilds.add(alipayMarketingCampaignCashCreateApiInParam_8);
        alipayMarketingCampaignCashCreateApiInParam_8.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashCreateApiInParam_8.setTitle("用户在当前活动参与次数、频率限制");
        alipayMarketingCampaignCashCreateApiInParam_8.setDesc("支持日(D)、周(W)、月(M)、终身(L)维度的限制。其中日(D)、周(W)、月(M)最多只能选择一个,终身(L)为必填项。多个配置之间使用\"|\"进行分隔。终身(L)次数限制最大为100，日(D)、周(W)、月(M)频率设置必须小于等于终身(L)的次数。整个字段不填时默认值为:L1。允许多次领取时，活动触发接口需要传入out_biz_no来配合。");
        alipayMarketingCampaignCashCreateApiInParam_8.setDescription("用户在当前活动参与次数、频率限制。支持日(D)、周(W)、月(M)、终身(L)维度的限制。其中日(D)、周(W)、月(M)最多只能选择一个,终身(L)为必填项。多个配置之间使用\"|\"进行分隔。终身(L)次数限制最大为100，日(D)、周(W)、月(M)频率设置必须小于等于终身(L)的次数。整个字段不填时默认值为:L1。允许多次领取时，活动触发接口需要传入out_biz_no来配合。");
        alipayMarketingCampaignCashCreateApiInParam_8.setIsMust(3);
        alipayMarketingCampaignCashCreateApiInParam_8.setIsListType(false);
        alipayMarketingCampaignCashCreateApiInParam_8.setFullParamName("sendFreqency");
        alipayMarketingCampaignCashCreateApiInParam_8.setEnName("send_freqency");



		List<ApiParamModel> alipayMarketingCampaignCashCreateApiOutParamChilds = new ArrayList<ApiParamModel>();
		alipayMarketingCampaignCashCreateInfoModel.setApiOutParam(alipayMarketingCampaignCashCreateApiOutParamChilds);
        ApiParamModel alipayMarketingCampaignCashCreateApiOutParam_0 = new ApiParamModel();
        alipayMarketingCampaignCashCreateApiOutParamChilds.add(alipayMarketingCampaignCashCreateApiOutParam_0);
        alipayMarketingCampaignCashCreateApiOutParam_0.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashCreateApiOutParam_0.setTitle("生成的现金红包活动号");
        alipayMarketingCampaignCashCreateApiOutParam_0.setDesc("");
        alipayMarketingCampaignCashCreateApiOutParam_0.setDescription("生成的现金红包活动号");
        alipayMarketingCampaignCashCreateApiOutParam_0.setIsMust(1);
        alipayMarketingCampaignCashCreateApiOutParam_0.setIsListType(false);
        alipayMarketingCampaignCashCreateApiOutParam_0.setFullParamName("crowdNo");
        alipayMarketingCampaignCashCreateApiOutParam_0.setEnName("crowd_no");


        ApiParamModel alipayMarketingCampaignCashCreateApiOutParam_1 = new ApiParamModel();
        alipayMarketingCampaignCashCreateApiOutParamChilds.add(alipayMarketingCampaignCashCreateApiOutParam_1);
        alipayMarketingCampaignCashCreateApiOutParam_1.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashCreateApiOutParam_1.setTitle("活动创建后的付款链接");
        alipayMarketingCampaignCashCreateApiOutParam_1.setDesc("返回的是urlencode编码后的字符串。需要先进行urldecode解码，然后在浏览器中进行访问，会先进行支付宝登录引导，然后商户进行付款。");
        alipayMarketingCampaignCashCreateApiOutParam_1.setDescription("活动创建后的付款链接，返回的是urlencode编码后的字符串。需要先进行urldecode解码，然后在浏览器中进行访问，会先进行支付宝登录引导，然后商户进行付款。");
        alipayMarketingCampaignCashCreateApiOutParam_1.setIsMust(1);
        alipayMarketingCampaignCashCreateApiOutParam_1.setIsListType(false);
        alipayMarketingCampaignCashCreateApiOutParam_1.setFullParamName("payUrl");
        alipayMarketingCampaignCashCreateApiOutParam_1.setEnName("pay_url");


        ApiParamModel alipayMarketingCampaignCashCreateApiOutParam_2 = new ApiParamModel();
        alipayMarketingCampaignCashCreateApiOutParamChilds.add(alipayMarketingCampaignCashCreateApiOutParam_2);
        alipayMarketingCampaignCashCreateApiOutParam_2.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashCreateApiOutParam_2.setTitle("原始活动号");
        alipayMarketingCampaignCashCreateApiOutParam_2.setDesc("商户排查问题时提供的活动依据");
        alipayMarketingCampaignCashCreateApiOutParam_2.setDescription("原始活动号,商户排查问题时提供的活动依据");
        alipayMarketingCampaignCashCreateApiOutParam_2.setIsMust(1);
        alipayMarketingCampaignCashCreateApiOutParam_2.setIsListType(false);
        alipayMarketingCampaignCashCreateApiOutParam_2.setFullParamName("originCrowdNo");
        alipayMarketingCampaignCashCreateApiOutParam_2.setEnName("origin_crowd_no");



		ApiInfoModel alipayMarketingCampaignCashListQueryInfoModel = new ApiInfoModel();
		list.add(alipayMarketingCampaignCashListQueryInfoModel);
		alipayMarketingCampaignCashListQueryInfoModel.setApiName("alipay.marketing.campaign.cash.list.query");
		alipayMarketingCampaignCashListQueryInfoModel.setApiZhName("现金活动列表查询");
		alipayMarketingCampaignCashListQueryInfoModel.setInvokeType(ApiInfoModel.INVOKE_TYPE_REQUEST);
		List<ApiParamModel> alipayMarketingCampaignCashListQueryApiInParamChilds = new ArrayList<ApiParamModel>();
		alipayMarketingCampaignCashListQueryInfoModel.setApiInParam(alipayMarketingCampaignCashListQueryApiInParamChilds);
        ApiParamModel alipayMarketingCampaignCashListQueryApiInParam_1 = new ApiParamModel();
        alipayMarketingCampaignCashListQueryApiInParamChilds.add(alipayMarketingCampaignCashListQueryApiInParam_1);
        alipayMarketingCampaignCashListQueryApiInParam_1.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashListQueryApiInParam_1.setTitle("分页查询时每页返回的列表大小");
        alipayMarketingCampaignCashListQueryApiInParam_1.setDesc("最大为50");
        alipayMarketingCampaignCashListQueryApiInParam_1.setDescription("分页查询时每页返回的列表大小,最大为50");
        alipayMarketingCampaignCashListQueryApiInParam_1.setIsMust(1);
        alipayMarketingCampaignCashListQueryApiInParam_1.setIsListType(false);
        alipayMarketingCampaignCashListQueryApiInParam_1.setFullParamName("pageSize");
        alipayMarketingCampaignCashListQueryApiInParam_1.setEnName("page_size");


        ApiParamModel alipayMarketingCampaignCashListQueryApiInParam_2 = new ApiParamModel();
        alipayMarketingCampaignCashListQueryApiInParamChilds.add(alipayMarketingCampaignCashListQueryApiInParam_2);
        alipayMarketingCampaignCashListQueryApiInParam_2.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashListQueryApiInParam_2.setTitle("分页查询时的页码");
        alipayMarketingCampaignCashListQueryApiInParam_2.setDesc("从1开始");
        alipayMarketingCampaignCashListQueryApiInParam_2.setDescription("分页查询时的页码，从1开始");
        alipayMarketingCampaignCashListQueryApiInParam_2.setIsMust(1);
        alipayMarketingCampaignCashListQueryApiInParam_2.setIsListType(false);
        alipayMarketingCampaignCashListQueryApiInParam_2.setFullParamName("pageIndex");
        alipayMarketingCampaignCashListQueryApiInParam_2.setEnName("page_index");


        ApiParamModel alipayMarketingCampaignCashListQueryApiInParam_0 = new ApiParamModel();
        alipayMarketingCampaignCashListQueryApiInParamChilds.add(alipayMarketingCampaignCashListQueryApiInParam_0);
        alipayMarketingCampaignCashListQueryApiInParam_0.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashListQueryApiInParam_0.setTitle("要查询的活动状态");
        alipayMarketingCampaignCashListQueryApiInParam_0.setDesc("不填默认返回所有类型。  可填:  ALL:所有类型的活动  CREATED: 已创建未打款  PAID:已打款  READY:活动已开始  PAUSE:活动已暂停  CLOSED:活动已结束  SETTLE:活动已清算");
        alipayMarketingCampaignCashListQueryApiInParam_0.setDescription("要查询的活动状态,不填默认返回所有类型。  可填:  ALL:所有类型的活动  CREATED: 已创建未打款  PAID:已打款  READY:活动已开始  PAUSE:活动已暂停  CLOSED:活动已结束  SETTLE:活动已清算");
        alipayMarketingCampaignCashListQueryApiInParam_0.setIsMust(3);
        alipayMarketingCampaignCashListQueryApiInParam_0.setIsListType(false);
        alipayMarketingCampaignCashListQueryApiInParam_0.setFullParamName("campStatus");
        alipayMarketingCampaignCashListQueryApiInParam_0.setEnName("camp_status");



		List<ApiParamModel> alipayMarketingCampaignCashListQueryApiOutParamChilds = new ArrayList<ApiParamModel>();
		alipayMarketingCampaignCashListQueryInfoModel.setApiOutParam(alipayMarketingCampaignCashListQueryApiOutParamChilds);
        ApiParamModel alipayMarketingCampaignCashListQueryApiOutParam_0 = new ApiParamModel();
        alipayMarketingCampaignCashListQueryApiOutParamChilds.add(alipayMarketingCampaignCashListQueryApiOutParam_0);
        alipayMarketingCampaignCashListQueryApiOutParam_0.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashListQueryApiOutParam_0.setTitle("分页每页大小");
        alipayMarketingCampaignCashListQueryApiOutParam_0.setDesc("");
        alipayMarketingCampaignCashListQueryApiOutParam_0.setDescription("分页每页大小");
        alipayMarketingCampaignCashListQueryApiOutParam_0.setIsMust(1);
        alipayMarketingCampaignCashListQueryApiOutParam_0.setIsListType(false);
        alipayMarketingCampaignCashListQueryApiOutParam_0.setFullParamName("pageSize");
        alipayMarketingCampaignCashListQueryApiOutParam_0.setEnName("page_size");


        ApiParamModel alipayMarketingCampaignCashListQueryApiOutParam_1 = new ApiParamModel();
        alipayMarketingCampaignCashListQueryApiOutParamChilds.add(alipayMarketingCampaignCashListQueryApiOutParam_1);
        alipayMarketingCampaignCashListQueryApiOutParam_1.setBaseType(ApiParamModel.TYPE_COMPLEXTYPE);
        alipayMarketingCampaignCashListQueryApiOutParam_1.setTitle("活动列表");
        alipayMarketingCampaignCashListQueryApiOutParam_1.setDesc("");
        alipayMarketingCampaignCashListQueryApiOutParam_1.setDescription("活动列表");
        alipayMarketingCampaignCashListQueryApiOutParam_1.setIsMust(1);
        alipayMarketingCampaignCashListQueryApiOutParam_1.setIsListType(true);
        alipayMarketingCampaignCashListQueryApiOutParam_1.setFullParamName("campList[0]");
        alipayMarketingCampaignCashListQueryApiOutParam_1.setEnName("camp_list");

        List<ApiParamModel> alipayMarketingCampaignCashListQueryApiOutParam_1Childs = new ArrayList<ApiParamModel>();
        alipayMarketingCampaignCashListQueryApiOutParam_1.setChilds(alipayMarketingCampaignCashListQueryApiOutParam_1Childs);
        			        ApiParamModel alipayMarketingCampaignCashListQueryApiOutParam_1_0 = new ApiParamModel();
        alipayMarketingCampaignCashListQueryApiOutParam_1Childs.add(alipayMarketingCampaignCashListQueryApiOutParam_1_0);
        alipayMarketingCampaignCashListQueryApiOutParam_1_0.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashListQueryApiOutParam_1_0.setTitle("现金红包活动号");
        alipayMarketingCampaignCashListQueryApiOutParam_1_0.setDesc("");
        alipayMarketingCampaignCashListQueryApiOutParam_1_0.setDescription("现金红包活动号");
        alipayMarketingCampaignCashListQueryApiOutParam_1_0.setIsMust(1);
        alipayMarketingCampaignCashListQueryApiOutParam_1_0.setIsListType(false);
        alipayMarketingCampaignCashListQueryApiOutParam_1_0.setFullParamName("campList[0].crowdNo");
        alipayMarketingCampaignCashListQueryApiOutParam_1_0.setEnName("crowd_no");


        ApiParamModel alipayMarketingCampaignCashListQueryApiOutParam_1_1 = new ApiParamModel();
        alipayMarketingCampaignCashListQueryApiOutParam_1Childs.add(alipayMarketingCampaignCashListQueryApiOutParam_1_1);
        alipayMarketingCampaignCashListQueryApiOutParam_1_1.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashListQueryApiOutParam_1_1.setTitle("原始活动号");
        alipayMarketingCampaignCashListQueryApiOutParam_1_1.setDesc("商户进行问题排查时提供");
        alipayMarketingCampaignCashListQueryApiOutParam_1_1.setDescription("原始活动号,商户进行问题排查时提供");
        alipayMarketingCampaignCashListQueryApiOutParam_1_1.setIsMust(1);
        alipayMarketingCampaignCashListQueryApiOutParam_1_1.setIsListType(false);
        alipayMarketingCampaignCashListQueryApiOutParam_1_1.setFullParamName("campList[0].originCrowdNo");
        alipayMarketingCampaignCashListQueryApiOutParam_1_1.setEnName("origin_crowd_no");


        ApiParamModel alipayMarketingCampaignCashListQueryApiOutParam_1_2 = new ApiParamModel();
        alipayMarketingCampaignCashListQueryApiOutParam_1Childs.add(alipayMarketingCampaignCashListQueryApiOutParam_1_2);
        alipayMarketingCampaignCashListQueryApiOutParam_1_2.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashListQueryApiOutParam_1_2.setTitle("活动状态");
        alipayMarketingCampaignCashListQueryApiOutParam_1_2.setDesc("");
        alipayMarketingCampaignCashListQueryApiOutParam_1_2.setDescription("活动状态");
        alipayMarketingCampaignCashListQueryApiOutParam_1_2.setIsMust(1);
        alipayMarketingCampaignCashListQueryApiOutParam_1_2.setIsListType(false);
        alipayMarketingCampaignCashListQueryApiOutParam_1_2.setFullParamName("campList[0].campStatus");
        alipayMarketingCampaignCashListQueryApiOutParam_1_2.setEnName("camp_status");


        ApiParamModel alipayMarketingCampaignCashListQueryApiOutParam_1_3 = new ApiParamModel();
        alipayMarketingCampaignCashListQueryApiOutParam_1Childs.add(alipayMarketingCampaignCashListQueryApiOutParam_1_3);
        alipayMarketingCampaignCashListQueryApiOutParam_1_3.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashListQueryApiOutParam_1_3.setTitle("现金红包名称");
        alipayMarketingCampaignCashListQueryApiOutParam_1_3.setDesc("");
        alipayMarketingCampaignCashListQueryApiOutParam_1_3.setDescription("现金红包名称");
        alipayMarketingCampaignCashListQueryApiOutParam_1_3.setIsMust(1);
        alipayMarketingCampaignCashListQueryApiOutParam_1_3.setIsListType(false);
        alipayMarketingCampaignCashListQueryApiOutParam_1_3.setFullParamName("campList[0].couponName");
        alipayMarketingCampaignCashListQueryApiOutParam_1_3.setEnName("coupon_name");



        ApiParamModel alipayMarketingCampaignCashListQueryApiOutParam_2 = new ApiParamModel();
        alipayMarketingCampaignCashListQueryApiOutParamChilds.add(alipayMarketingCampaignCashListQueryApiOutParam_2);
        alipayMarketingCampaignCashListQueryApiOutParam_2.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashListQueryApiOutParam_2.setTitle("分页的页码");
        alipayMarketingCampaignCashListQueryApiOutParam_2.setDesc("起始从1开始");
        alipayMarketingCampaignCashListQueryApiOutParam_2.setDescription("分页的页码,起始从1开始");
        alipayMarketingCampaignCashListQueryApiOutParam_2.setIsMust(1);
        alipayMarketingCampaignCashListQueryApiOutParam_2.setIsListType(false);
        alipayMarketingCampaignCashListQueryApiOutParam_2.setFullParamName("pageIndex");
        alipayMarketingCampaignCashListQueryApiOutParam_2.setEnName("page_index");


        ApiParamModel alipayMarketingCampaignCashListQueryApiOutParam_3 = new ApiParamModel();
        alipayMarketingCampaignCashListQueryApiOutParamChilds.add(alipayMarketingCampaignCashListQueryApiOutParam_3);
        alipayMarketingCampaignCashListQueryApiOutParam_3.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashListQueryApiOutParam_3.setTitle("活动总个数");
        alipayMarketingCampaignCashListQueryApiOutParam_3.setDesc("");
        alipayMarketingCampaignCashListQueryApiOutParam_3.setDescription("活动总个数");
        alipayMarketingCampaignCashListQueryApiOutParam_3.setIsMust(1);
        alipayMarketingCampaignCashListQueryApiOutParam_3.setIsListType(false);
        alipayMarketingCampaignCashListQueryApiOutParam_3.setFullParamName("totalSize");
        alipayMarketingCampaignCashListQueryApiOutParam_3.setEnName("total_size");



		ApiInfoModel alipayMarketingCampaignCashDetailQueryInfoModel = new ApiInfoModel();
		list.add(alipayMarketingCampaignCashDetailQueryInfoModel);
		alipayMarketingCampaignCashDetailQueryInfoModel.setApiName("alipay.marketing.campaign.cash.detail.query");
		alipayMarketingCampaignCashDetailQueryInfoModel.setApiZhName("现金活动详情查询");
		alipayMarketingCampaignCashDetailQueryInfoModel.setInvokeType(ApiInfoModel.INVOKE_TYPE_REQUEST);
		List<ApiParamModel> alipayMarketingCampaignCashDetailQueryApiInParamChilds = new ArrayList<ApiParamModel>();
		alipayMarketingCampaignCashDetailQueryInfoModel.setApiInParam(alipayMarketingCampaignCashDetailQueryApiInParamChilds);
        ApiParamModel alipayMarketingCampaignCashDetailQueryApiInParam_0 = new ApiParamModel();
        alipayMarketingCampaignCashDetailQueryApiInParamChilds.add(alipayMarketingCampaignCashDetailQueryApiInParam_0);
        alipayMarketingCampaignCashDetailQueryApiInParam_0.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashDetailQueryApiInParam_0.setTitle("要查询的现金红包活动号");
        alipayMarketingCampaignCashDetailQueryApiInParam_0.setDesc("");
        alipayMarketingCampaignCashDetailQueryApiInParam_0.setDescription("要查询的现金红包活动号");
        alipayMarketingCampaignCashDetailQueryApiInParam_0.setIsMust(1);
        alipayMarketingCampaignCashDetailQueryApiInParam_0.setIsListType(false);
        alipayMarketingCampaignCashDetailQueryApiInParam_0.setFullParamName("crowdNo");
        alipayMarketingCampaignCashDetailQueryApiInParam_0.setEnName("crowd_no");



		List<ApiParamModel> alipayMarketingCampaignCashDetailQueryApiOutParamChilds = new ArrayList<ApiParamModel>();
		alipayMarketingCampaignCashDetailQueryInfoModel.setApiOutParam(alipayMarketingCampaignCashDetailQueryApiOutParamChilds);
        ApiParamModel alipayMarketingCampaignCashDetailQueryApiOutParam_0 = new ApiParamModel();
        alipayMarketingCampaignCashDetailQueryApiOutParamChilds.add(alipayMarketingCampaignCashDetailQueryApiOutParam_0);
        alipayMarketingCampaignCashDetailQueryApiOutParam_0.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashDetailQueryApiOutParam_0.setTitle("活动号");
        alipayMarketingCampaignCashDetailQueryApiOutParam_0.setDesc("");
        alipayMarketingCampaignCashDetailQueryApiOutParam_0.setDescription("活动号");
        alipayMarketingCampaignCashDetailQueryApiOutParam_0.setIsMust(1);
        alipayMarketingCampaignCashDetailQueryApiOutParam_0.setIsListType(false);
        alipayMarketingCampaignCashDetailQueryApiOutParam_0.setFullParamName("crowdNo");
        alipayMarketingCampaignCashDetailQueryApiOutParam_0.setEnName("crowd_no");


        ApiParamModel alipayMarketingCampaignCashDetailQueryApiOutParam_1 = new ApiParamModel();
        alipayMarketingCampaignCashDetailQueryApiOutParamChilds.add(alipayMarketingCampaignCashDetailQueryApiOutParam_1);
        alipayMarketingCampaignCashDetailQueryApiOutParam_1.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashDetailQueryApiOutParam_1.setTitle("红包名称");
        alipayMarketingCampaignCashDetailQueryApiOutParam_1.setDesc("");
        alipayMarketingCampaignCashDetailQueryApiOutParam_1.setDescription("红包名称");
        alipayMarketingCampaignCashDetailQueryApiOutParam_1.setIsMust(1);
        alipayMarketingCampaignCashDetailQueryApiOutParam_1.setIsListType(false);
        alipayMarketingCampaignCashDetailQueryApiOutParam_1.setFullParamName("couponName");
        alipayMarketingCampaignCashDetailQueryApiOutParam_1.setEnName("coupon_name");


        ApiParamModel alipayMarketingCampaignCashDetailQueryApiOutParam_2 = new ApiParamModel();
        alipayMarketingCampaignCashDetailQueryApiOutParamChilds.add(alipayMarketingCampaignCashDetailQueryApiOutParam_2);
        alipayMarketingCampaignCashDetailQueryApiOutParam_2.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashDetailQueryApiOutParam_2.setTitle("活动文案");
        alipayMarketingCampaignCashDetailQueryApiOutParam_2.setDesc("用户在账单中看到的红包描述");
        alipayMarketingCampaignCashDetailQueryApiOutParam_2.setDescription("活动文案,用户在账单中看到的红包描述");
        alipayMarketingCampaignCashDetailQueryApiOutParam_2.setIsMust(1);
        alipayMarketingCampaignCashDetailQueryApiOutParam_2.setIsListType(false);
        alipayMarketingCampaignCashDetailQueryApiOutParam_2.setFullParamName("prizeMsg");
        alipayMarketingCampaignCashDetailQueryApiOutParam_2.setEnName("prize_msg");


        ApiParamModel alipayMarketingCampaignCashDetailQueryApiOutParam_3 = new ApiParamModel();
        alipayMarketingCampaignCashDetailQueryApiOutParamChilds.add(alipayMarketingCampaignCashDetailQueryApiOutParam_3);
        alipayMarketingCampaignCashDetailQueryApiOutParam_3.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashDetailQueryApiOutParam_3.setTitle("现金红包的发放形式");
        alipayMarketingCampaignCashDetailQueryApiOutParam_3.setDesc(" fixed为固定金额,random为随机金额");
        alipayMarketingCampaignCashDetailQueryApiOutParam_3.setDescription("现金红包的发放形式, fixed为固定金额,random为随机金额");
        alipayMarketingCampaignCashDetailQueryApiOutParam_3.setIsMust(1);
        alipayMarketingCampaignCashDetailQueryApiOutParam_3.setIsListType(false);
        alipayMarketingCampaignCashDetailQueryApiOutParam_3.setFullParamName("prizeType");
        alipayMarketingCampaignCashDetailQueryApiOutParam_3.setEnName("prize_type");


        ApiParamModel alipayMarketingCampaignCashDetailQueryApiOutParam_4 = new ApiParamModel();
        alipayMarketingCampaignCashDetailQueryApiOutParamChilds.add(alipayMarketingCampaignCashDetailQueryApiOutParam_4);
        alipayMarketingCampaignCashDetailQueryApiOutParam_4.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashDetailQueryApiOutParam_4.setTitle("活动开始时间");
        alipayMarketingCampaignCashDetailQueryApiOutParam_4.setDesc("");
        alipayMarketingCampaignCashDetailQueryApiOutParam_4.setDescription("活动开始时间");
        alipayMarketingCampaignCashDetailQueryApiOutParam_4.setIsMust(1);
        alipayMarketingCampaignCashDetailQueryApiOutParam_4.setIsListType(false);
        alipayMarketingCampaignCashDetailQueryApiOutParam_4.setFullParamName("startTime");
        alipayMarketingCampaignCashDetailQueryApiOutParam_4.setEnName("start_time");


        ApiParamModel alipayMarketingCampaignCashDetailQueryApiOutParam_5 = new ApiParamModel();
        alipayMarketingCampaignCashDetailQueryApiOutParamChilds.add(alipayMarketingCampaignCashDetailQueryApiOutParam_5);
        alipayMarketingCampaignCashDetailQueryApiOutParam_5.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashDetailQueryApiOutParam_5.setTitle("活动结束时间");
        alipayMarketingCampaignCashDetailQueryApiOutParam_5.setDesc("");
        alipayMarketingCampaignCashDetailQueryApiOutParam_5.setDescription("活动结束时间");
        alipayMarketingCampaignCashDetailQueryApiOutParam_5.setIsMust(1);
        alipayMarketingCampaignCashDetailQueryApiOutParam_5.setIsListType(false);
        alipayMarketingCampaignCashDetailQueryApiOutParam_5.setFullParamName("endTime");
        alipayMarketingCampaignCashDetailQueryApiOutParam_5.setEnName("end_time");


        ApiParamModel alipayMarketingCampaignCashDetailQueryApiOutParam_6 = new ApiParamModel();
        alipayMarketingCampaignCashDetailQueryApiOutParamChilds.add(alipayMarketingCampaignCashDetailQueryApiOutParam_6);
        alipayMarketingCampaignCashDetailQueryApiOutParam_6.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashDetailQueryApiOutParam_6.setTitle("活动总金额");
        alipayMarketingCampaignCashDetailQueryApiOutParam_6.setDesc("");
        alipayMarketingCampaignCashDetailQueryApiOutParam_6.setDescription("活动总金额");
        alipayMarketingCampaignCashDetailQueryApiOutParam_6.setIsMust(1);
        alipayMarketingCampaignCashDetailQueryApiOutParam_6.setIsListType(false);
        alipayMarketingCampaignCashDetailQueryApiOutParam_6.setFullParamName("totalAmount");
        alipayMarketingCampaignCashDetailQueryApiOutParam_6.setEnName("total_amount");


        ApiParamModel alipayMarketingCampaignCashDetailQueryApiOutParam_7 = new ApiParamModel();
        alipayMarketingCampaignCashDetailQueryApiOutParamChilds.add(alipayMarketingCampaignCashDetailQueryApiOutParam_7);
        alipayMarketingCampaignCashDetailQueryApiOutParam_7.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashDetailQueryApiOutParam_7.setTitle("活动已发放金额");
        alipayMarketingCampaignCashDetailQueryApiOutParam_7.setDesc("");
        alipayMarketingCampaignCashDetailQueryApiOutParam_7.setDescription("活动已发放金额");
        alipayMarketingCampaignCashDetailQueryApiOutParam_7.setIsMust(1);
        alipayMarketingCampaignCashDetailQueryApiOutParam_7.setIsListType(false);
        alipayMarketingCampaignCashDetailQueryApiOutParam_7.setFullParamName("sendAmount");
        alipayMarketingCampaignCashDetailQueryApiOutParam_7.setEnName("send_amount");


        ApiParamModel alipayMarketingCampaignCashDetailQueryApiOutParam_8 = new ApiParamModel();
        alipayMarketingCampaignCashDetailQueryApiOutParamChilds.add(alipayMarketingCampaignCashDetailQueryApiOutParam_8);
        alipayMarketingCampaignCashDetailQueryApiOutParam_8.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashDetailQueryApiOutParam_8.setTitle("红包总个数(废弃)");
        alipayMarketingCampaignCashDetailQueryApiOutParam_8.setDesc("");
        alipayMarketingCampaignCashDetailQueryApiOutParam_8.setDescription("红包总个数(废弃)");
        alipayMarketingCampaignCashDetailQueryApiOutParam_8.setIsMust(1);
        alipayMarketingCampaignCashDetailQueryApiOutParam_8.setIsListType(false);
        alipayMarketingCampaignCashDetailQueryApiOutParam_8.setFullParamName("totalNum");
        alipayMarketingCampaignCashDetailQueryApiOutParam_8.setEnName("total_num");


        ApiParamModel alipayMarketingCampaignCashDetailQueryApiOutParam_9 = new ApiParamModel();
        alipayMarketingCampaignCashDetailQueryApiOutParamChilds.add(alipayMarketingCampaignCashDetailQueryApiOutParam_9);
        alipayMarketingCampaignCashDetailQueryApiOutParam_9.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashDetailQueryApiOutParam_9.setTitle("红包总个数");
        alipayMarketingCampaignCashDetailQueryApiOutParam_9.setDesc("");
        alipayMarketingCampaignCashDetailQueryApiOutParam_9.setDescription("红包总个数");
        alipayMarketingCampaignCashDetailQueryApiOutParam_9.setIsMust(1);
        alipayMarketingCampaignCashDetailQueryApiOutParam_9.setIsListType(false);
        alipayMarketingCampaignCashDetailQueryApiOutParam_9.setFullParamName("totalCount");
        alipayMarketingCampaignCashDetailQueryApiOutParam_9.setEnName("total_count");


        ApiParamModel alipayMarketingCampaignCashDetailQueryApiOutParam_10 = new ApiParamModel();
        alipayMarketingCampaignCashDetailQueryApiOutParamChilds.add(alipayMarketingCampaignCashDetailQueryApiOutParam_10);
        alipayMarketingCampaignCashDetailQueryApiOutParam_10.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashDetailQueryApiOutParam_10.setTitle("原始活动号");
        alipayMarketingCampaignCashDetailQueryApiOutParam_10.setDesc("商户排查问题时提供的活动依据");
        alipayMarketingCampaignCashDetailQueryApiOutParam_10.setDescription("原始活动号,商户排查问题时提供的活动依据");
        alipayMarketingCampaignCashDetailQueryApiOutParam_10.setIsMust(1);
        alipayMarketingCampaignCashDetailQueryApiOutParam_10.setIsListType(false);
        alipayMarketingCampaignCashDetailQueryApiOutParam_10.setFullParamName("originCrowdNo");
        alipayMarketingCampaignCashDetailQueryApiOutParam_10.setEnName("origin_crowd_no");


        ApiParamModel alipayMarketingCampaignCashDetailQueryApiOutParam_11 = new ApiParamModel();
        alipayMarketingCampaignCashDetailQueryApiOutParamChilds.add(alipayMarketingCampaignCashDetailQueryApiOutParam_11);
        alipayMarketingCampaignCashDetailQueryApiOutParam_11.setBaseType(ApiParamModel.TYPE_BASETYPE);
        alipayMarketingCampaignCashDetailQueryApiOutParam_11.setTitle("活动状态");
        alipayMarketingCampaignCashDetailQueryApiOutParam_11.setDesc("CREATED: 已创建未打款  PAID:已打款  READY:活动已开始  PAUSE:活动已暂停  CLOSED:活动已结束  SETTLE:活动已清算");
        alipayMarketingCampaignCashDetailQueryApiOutParam_11.setDescription("活动状态，CREATED: 已创建未打款  PAID:已打款  READY:活动已开始  PAUSE:活动已暂停  CLOSED:活动已结束  SETTLE:活动已清算");
        alipayMarketingCampaignCashDetailQueryApiOutParam_11.setIsMust(1);
        alipayMarketingCampaignCashDetailQueryApiOutParam_11.setIsListType(false);
        alipayMarketingCampaignCashDetailQueryApiOutParam_11.setFullParamName("campStatus");
        alipayMarketingCampaignCashDetailQueryApiOutParam_11.setEnName("camp_status");




        return list;

    }
}
