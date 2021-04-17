package com.otzg.wxpay.entity;

import com.alibaba.fastjson.JSON;
import com.otzg.util.DateUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import java.util.StringJoiner;

/**
 * @Author G.
 * @Date 2019/11/30 0030 下午 3:47
 */
@Entity
@Table
public class WxMicroAccountLog implements Serializable {
    @Id
    Long id;
    //申请商户id
    @Column(name = "unit_id", nullable = false, length = 32)
    String unitId;
    /**
     * 业务申请编号
     * 服务商自定义的商户唯一编号。每个编号对应一个申请单，每个申请单审核通过后会生成一个微信支付商户号。
     */
    @Column(name = "business_code", nullable = false, length = 32)
    String businessCode;

    //待审批Wid
    @Column(name = "applyment_id", length = 32)
    String applymentId;

    //AUDITING:审核中,REJECTED:已驳回,FROZEN:已冻结,TO_BE_SIGNED:待签约,FINISH:完成
    @Column(name = "applyment_state", length = 32)
    String applymentState;

    //申请状态描述
    @Column(name = "applyment_state_desc", length = 32)
    String applymentStateDesc;
    //审核详情(各项被驳回资料的驳回理由。当申请状态为REJECTED时才返回。字段值为json格式，字段详细说明见下拉列表（点击行前“+”打开下拉列表）)
    @Column(name = "audit_detail", length = 500)
    String auditDetail;

    //小微商户号(当申请状态为TO_BE_SIGNED或FINISH时才返回)
    @Column(name = "sub_mch_id", length = 32)
    String subMchId;
    //签约链接 (当申请状态为TO_BE_SIGNED或FINISH时才返回)
    @Column(name = "sign_url", length = 32)
    String signUrl;

    //创建时间
    @Column(name = "create_time", nullable = false, length = 19)
    Date createTime;

    //账户状态{0:失败,1:成功}
    @Column(name = "status",nullable = false,length = 1)
    Integer status;

    public WxMicroAccountLog() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public String getApplymentId() {
        return applymentId;
    }

    public void setApplymentId(String applymentId) {
        this.applymentId = applymentId;
    }

    public String getApplymentState() {
        return applymentState;
    }

    public void setApplymentState(String applymentState) {
        this.applymentState = applymentState;
    }

    public String getApplymentStateDesc() {
        return applymentStateDesc;
    }

    public void setApplymentStateDesc(String applymentStateDesc) {
        this.applymentStateDesc = applymentStateDesc;
    }

    public String getAuditDetail() {
        return auditDetail;
    }

    public void setAuditDetail(String auditDetail) {
        this.auditDetail = auditDetail;
    }

    public String getSubMchId() {
        return subMchId;
    }

    public void setSubMchId(String subMchId) {
        this.subMchId = subMchId;
    }

    public String getSignUrl() {
        return signUrl;
    }

    public void setSignUrl(String signUrl) {
        this.signUrl = signUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Map getJson() {
        String jsonStr = new StringJoiner(", ", "{", "}")
                .add("id:" + id)
                .add("unitId:'" + unitId + "'")
                .add("businessCode:'" + businessCode + "'")
                .add("applymentId:'" + applymentId + "'")
                .add("applymentState:'" + applymentState + "'")
                .add("applymentStateDesc:'" + applymentStateDesc + "'")
                .add("auditDetail:" + auditDetail)
                .add("subMchId:'" + subMchId + "'")
                .add("signUrl:'" + signUrl + "'")
                .add("createTime:'" + DateUtil.dateTime2Str(createTime) + "'")
                .add("status:'" + status + "'")
                .toString();
        return (Map) JSON.parse(jsonStr);
    }

}
