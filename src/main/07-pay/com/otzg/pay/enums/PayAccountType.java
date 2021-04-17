package com.otzg.pay.enums;

/**
 * 基本账户类型
 */
public enum PayAccountType {
    PLATFORM(0,"平台"),
    MEMBER(1,"会员"),
    UNIT(2,"单位");

    public Integer index;
    public String name;
    PayAccountType(Integer i, String n) {
        this.index = i;
        this.name = n;
    }

    //判断是否在枚举类型内的值
    public final static boolean has(Integer i){
        for (PayType pt : PayType.values()) {
            if(pt.index==i){
                return true;
            }
        }
        return false;
    }
}
