package com.otzg.pay.enums;

import com.otzg.util.FuncUtil;

public enum PayType {
    CHANNEL_PAY(0,"充值"),
    YEAR_FEE(1,"交年费"),
    BALANCE_FEE(11,"余额缴费"),
    CASH_GET(2,"提现"),
    PROFIT_INCOME(3,"返利收入"),
    PROFIT_EXPENSE(4,"返利支出"),
    BIND_ACCOUNT(10,"绑定账户");

    public int index;
    public String name;
    PayType(int i, String n) {
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

    //判断是否在枚举类型内的值
    public final static String getName(int i){
        for (PayType pt : PayType.values()) {
            if(pt.index==i){
                return pt.name;
            }
        }
        return null;
    }

    public final static String getName(String value){
        try{
            return PayType.valueOf(value).name;
        }catch (Exception e){
            return null;
        }
    }

    public final static String getNames() {
        String names="";
        for(PayType pt:PayType.values()){
            names += ","+pt.index+":"+pt.name;
        }
        return FuncUtil.CleanComma(names);
    }

}
