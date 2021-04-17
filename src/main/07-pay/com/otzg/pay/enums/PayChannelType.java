package com.otzg.pay.enums;

import com.otzg.util.FuncUtil;

/**
 * 支付渠道{alipay:支付宝,wxpay:微信支付,ycpay:邮储支付}
 * @author wangzhigang
 */
public enum PayChannelType {
    alipay,wxpay,ycpay;

    //判断是否在枚举类型内的值
    public final static boolean has(String value){
        try{
            PayChannelType.valueOf(value);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public final static String getName(String value){
        try{
            return PayChannelType.valueOf(value).name();
        }catch (Exception e){
            return null;
        }
    }

    public final static String getNames() {
        String names="";
        for(PayChannelType ct:PayChannelType.values()){
            names += ","+ct.name();
        }
        return FuncUtil.CleanComma(names);
    }

}
