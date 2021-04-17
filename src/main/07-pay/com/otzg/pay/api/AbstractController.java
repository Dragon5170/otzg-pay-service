package com.otzg.pay.api;

import com.otzg.base.BaseController;

public abstract class AbstractController extends BaseController {

//	@Autowired
//	protected MemberOnlineRedis memberOnline;
//
//	/**
//	 * 获取当前用户信息
//	 * @author G/2018/6/8 17:41
//	 * @param token
//	 */
//	protected final Member getMember(String token) {
//		return memberOnline.getMember(token);
//	}
//
//	protected final MemberDetails getCurrentMember(String token) {
//		return memberOnline.getCurrentMember(token);
//	}
//
//	protected final boolean updateCurrentMember(String token,String nickName,String name,String headImg) {
//		MemberDetails md = memberOnline.getCurrentMember(token);
//		md.updateMemberInfo(nickName,name,headImg);
//		return memberOnline.update(token,md);
//	}
//
//	protected final Map login(Member member) {
//		String token = TokenUtil.getToken(member.getId());
//		//注册当前客户
//		memberOnline.login(token, member, null);
//		Map jo = member.getBaseJson();
//		//回传当前登录客户token
//		jo.put("token", token);
//		return jo;
//	}

}
