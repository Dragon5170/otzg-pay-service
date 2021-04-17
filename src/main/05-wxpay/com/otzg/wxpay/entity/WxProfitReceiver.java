package com.otzg.wxpay.entity;

import com.otzg.pay.entity.PayAccount;
import com.otzg.util.DateUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 微信分账接收者
 * 微信要求的固定格式
 * @author wangzhigang
 *    //MERCHANT_ID：商户ID PERSONAL_WECHATID：个人微信号PERSONAL_OPENID：个人openid（由父商户APPID转换得到）PERSONAL_SUB_OPENID: 个人sub_openid（由子商户APPID转换得到）
 * //          {
 * //         "type": "MERCHANT_ID",
 * //         "account":"190001001",
 * //         "amount":100,
 * //         "description": "分到商户"
 * //};
 */
@Entity
public class WxProfitReceiver implements Serializable {
    @Id
    @Column(name = "id", nullable = false, length = 32)
    String id;
    @Column(name = "type", nullable = false, length = 32)
    String type;
    @Column(name = "account", nullable = false, length = 32)
    String account;
    @Column(name = "name", nullable = false, length = 32)
    String name;
    @Column(name = "relation_type", nullable = false, length = 32)
    String relationType;

    //基本账户
    @ManyToOne
    @JoinColumn(name = "pay_account_id",nullable = false)
    PayAccount payAccount;

    //账户状态{-1:删除,0:冻结,1:正常}
    @Column(name = "status", nullable = false, length = 1)
    Integer status = 1;

    //创建时间
    Date createTime;

    //修改时间
    Date updateTime;


    public WxProfitReceiver() {
    }

    public WxProfitReceiver(PayAccount payAccount,
                            String id,
                            String type,
                            String account,
                            String name,
                            String relationType) {
        if(null == payAccount){
            return;
        }

        this.id = id;
        this.type = type;
        this.account = account;
        this.name = name;
        this.relationType = relationType;

        this.setPayAccount(payAccount);
        this.setCreateTime(DateUtil.now());
        this.setUpdateTime(DateUtil.now());
        this.setStatus(1);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    public PayAccount getPayAccount() {
        return payAccount;
    }

    public void setPayAccount(PayAccount payAccount) {
        this.payAccount = payAccount;
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
}
