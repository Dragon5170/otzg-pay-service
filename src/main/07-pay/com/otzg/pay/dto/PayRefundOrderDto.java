package com.otzg.pay.dto;

import java.io.Serializable;

/**
 * v1.0.3
 * @Author G.
 * @Date 2019/12/31 下午 18:10
 */
public class PayRefundOrderDto implements Serializable {
    /**
     * 子系统单位(商户)id
     *
     * 必填
     * length(32)
     */
    String unitId;

    /**
     * 子系统会员(个人)id
     * 业务发起人id
     *
     * length(32)
     */
    String memberId;

    /**
     * 退款金额
     */
    Double amount;

    /**
     * 子系统收款时的业务单号
     * 1.收款业务单号作为退款的依据
     * 2.多次退款可以多次传递
     * 必填
     * length(32)
     */
    String subOrderNo;

    /**
     * 子系统退款业务单号
     * 1.每个退款单号只能发起一次
     * 2.每个支付单可以多次退款
     * 必填
     * length(32)
     */
    String refundOrderNo;

    /**
     * 退款项目名称
     *
     * 必填
     * length(128)
     */
    String subject;

    private PayRefundOrderDto() {
    }

    public PayRefundOrderDto(String unitId, String memberId, Double amount, String subOrderNo, String refundOrderNo, String subject) {
        this.unitId = unitId;
        this.memberId = memberId;
        this.amount = amount;
        this.subOrderNo = subOrderNo;
        this.refundOrderNo = refundOrderNo;
        this.subject = subject;
    }

    public String getUnitId() {
        return unitId;
    }

    public String getMemberId() {
        return memberId;
    }

    public Double getAmount() {
        return amount;
    }

    public String getSubOrderNo() {
        return subOrderNo;
    }

    public String getSubject() {
        return subject;
    }

    public String getRefundOrderNo() {
        return refundOrderNo;
    }
}
