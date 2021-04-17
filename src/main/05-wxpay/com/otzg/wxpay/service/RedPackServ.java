package com.otzg.wxpay.service;

import com.otzg.base.Finder;
import com.otzg.wxpay.dto.WxRedPackDto;

import java.util.Map;

/**
 * 微信红包相关
 *
 * @author G
 */
public interface RedPackServ {

    /**
     * 商户发红包
     *
     * 1.发送微信红包，24小时内不接受，会退回账户
     * 2.所以最终的发放结果需要调用查询接口获取
     * 3.根据查询返回的结果，处理支付渠道账户的增减
     *
     * 微信红包接口说明
     *  1.商户订单号必须唯一
     *  2.商户订单号支持重入。
     *
     *  操作：
     *  1.查看红包是否已经创建
     *  2.红包是否已经支付完成
     *  3.冻结账户金额
     *  4.调支付接口
     *  5.保存返回状态
     */
    int sendRedPack(Long payAccountId,WxRedPackDto wxRedPackDto);

    /**
     *  自动执行查询任务
     *
     *  红包发送的结果最终以查询的结果确定
     *  如果有退回的应该返回支付渠道账户
     *  SENDING:发放中
     *  SENT:已发放待领取
     *  FAILED：发放失败
     *  RECEIVED:已领取
     *  RFUND_ING:退款中
     *  REFUND:已退款
     * @param redPackOrderNo
     * @return
     */
    int query(String redPackOrderNo);


    Map findWxRedPackOrderByUnit(Finder finder, String unitId);
}
