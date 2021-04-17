package com.otzg.pay.enums;

/**
 * 支付结果的状态
 */
public enum PayStatus {
    FAILED(-1, "失败"),
    WAITING(0, "等待"),
    SUCCESS(1, "成功");

    public int status;
    public String tips;

    PayStatus(int i, String n) {
        this.status = i;
        this.tips = n;
    }

    public final static String getTips(int i) {
        for (PayStatus pt : values()) {
            if (pt.status == i) {
                return pt.tips;
            }
        }
        return null;
    }
}