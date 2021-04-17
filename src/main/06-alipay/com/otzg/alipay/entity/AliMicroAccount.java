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
 * @Author G.
 * @Date 2019/12/5 0005 下午 4:17
 */
@Entity
@Table
public class AliMicroAccount implements Serializable {

    @Id
    Long id;

    //平台的单位id
    @Column(name = "unit_id",nullable = false,length = 32,unique = true)
    String unitId;

    /**
     * 绑定服务商的appId
     */
    @Column(name = "app_id",nullable = false,length = 32)
    String appId;




    //支付宝商户号(授权商户的pid)
    @Column(name = "user_id",nullable = false,length = 32)
    String userId;


    /**
     * 商户授权token
     * app_auth_token令牌信息String是授权令牌信息
     */
    @Column(name = "app_auth_token",nullable = false,length = 50)
    String appAuthToken;

    /**
     * 授权方应用id
     */
    @Column(name = "auth_app_id",nullable = false,length = 50)
    String authAppId;

    /**
     * app_refresh_token刷新令牌
     */
    @Column(name = "app_refresh_token",nullable = false,length = 50)
    String appRefreshToken;


    /**
     * 授权开始时间
     */
    @Column(name = "auth_start", nullable = false, length = 19)
    Long authStart;
    /**
     * 授权结束时间
     */
    @Column(name = "auth_end",length = 19)
    Long authEnd;



    //创建时间
    @Column(name = "create_time", nullable = false, length = 19)
    Date createTime;

    //更新时间
    @Column(name = "update_time", nullable = false, length = 19)
    Date updateTime;


    //账户状态{-1:删除,0:冻结,1:正常}
    @Column(name = "status",nullable = false,length = 1)
    Integer status=1;

    //re_expires_in刷新令牌有效时间String是刷新令牌有效期
    //userid支付宝用户标识String是支付宝用户标识


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

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAppAuthToken() {
        return appAuthToken;
    }

    public void setAppAuthToken(String appAuthToken) {
        this.appAuthToken = appAuthToken;
    }

    public String getAuthAppId() {
        return authAppId;
    }

    public void setAuthAppId(String authAppId) {
        this.authAppId = authAppId;
    }

    public String getAppRefreshToken() {
        return appRefreshToken;
    }

    public void setAppRefreshToken(String appRefreshToken) {
        this.appRefreshToken = appRefreshToken;
    }


    public Long getAuthStart() {
        return authStart;
    }

    public void setAuthStart(Long authStart) {
        this.authStart = authStart;
    }

    public Long getAuthEnd() {
        return authEnd;
    }

    public void setAuthEnd(Long authEnd) {
        this.authEnd = authEnd;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public AliMicroAccount() {
    }

    public StringJoiner getJson() {
        return new StringJoiner(", ", "{", "}")
                .add("id:" + id)
                .add("unitId:'" + unitId + "'")
                .add("appId:'" + appId + "'")
                .add("userId:'" + userId + "'")
                .add("appAuthToken:'" + appAuthToken + "'")
                .add("authAppId:'" + authAppId + "'")
                .add("appRefreshToken:'" + appRefreshToken + "'")
                .add("authStart:" + authStart)
                .add("authEnd:" + authEnd)
                .add("createTime:" + DateUtil.dateTime2Str(createTime))
                .add("updateTime:" + DateUtil.dateTime2Str(updateTime))
                .add("status:" + status);
    }
}
