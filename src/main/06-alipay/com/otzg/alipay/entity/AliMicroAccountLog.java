package com.otzg.alipay.entity;

import com.otzg.util.DateUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.StringJoiner;

/**
 * 保存授权换取token的记录
 *
 * 说明：
 * 支付宝申请账户授权后换取授权authToken
 * 如果授权成功状态为1
 * 否则为0,需要重新授权
 * 如果authToken过期，需要通过刷新token重新获取token
 *
 * @Author G.
 * @Date 2019/12/9 0009 上午10:41
 */

@Entity
@Table
public class AliMicroAccountLog implements Serializable {

    @Id
    Long id;

    //平台的单位id
    @Column(name = "unit_id",nullable = false,length = 32)
    String unitId;
    //日志内容
    @Column(name = "detail",nullable = false,length = 500)
    String detail;

    //创建时间
    @Column(name = "create_time", nullable = false, length = 19)
    Date createTime;

    //状态{0:失败,1:成功}
    @Column(name = "status",nullable = false,length = 1)
    Integer status;

    public AliMicroAccountLog() {
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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

    public String getJson() {
        return new StringJoiner(", ", "{", "}")
                .add("id:" + id)
                .add("unitId:'" + unitId + "'")
                .add("detail:" + detail)
                .add("createTime:" + DateUtil.dateTime2Str(createTime))
                .add("status:" + status)
                .toString();
    }
}
