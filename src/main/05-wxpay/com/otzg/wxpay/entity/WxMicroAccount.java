package com.otzg.wxpay.entity;

import com.otzg.util.DateUtil;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.StringJoiner;

/**
 * 小微商户基本信息
 *
 * 参考链接
 * https://pay.weixin.qq.com/index.php/core/affiliate/micro_intro
 *
 * 小微商户支付权限
 * JSAPI支付
 * Native支付
 * 付款码支付
 *
 * 普通商户支付权限
 * JSAPI支付
 * Native支付
 * 付款码支付
 * H5支付
 * APP支付
 *
 *
 */
@Entity
@Table
public class WxMicroAccount implements Serializable {

    @Id
    Long id;
    //申请商户id
    @Column(name = "unit_id", nullable = false, length = 32, unique = true)
    String unitId;
    /**
     * 业务申请编号
     * 服务商自定义的商户唯一编号。每个编号对应一个申请单，每个申请单审核通过后会生成一个微信支付商户号。
     */
    @Column(name = "business_code", nullable = false, length = 32)
    String businessCode;

    //身份证人像面照片
    @Column(name = "id_card_copy_src", nullable = false, length = 500)
    String idCardCopySrc;
    //身份证人像面照片
    @Column(name = "id_card_copy", length = 256)
    String idCardCopy;

    //身份证国徽面照片
    @Column(name = "id_card_national_src", nullable = false, length = 500)
    String idCardNationalSrc;

    //身份证国徽面照片
    @Column(name = "id_card_national", length = 256)
    String idCardNational;


    //身份证姓名
    @Column(name = "id_card_name", nullable = false, length = 64)
    String idCardName;
    //身份证号码
    @Column(name = "id_card_number", nullable = false, length = 18)
    String idCardNumber;
    //身份证有效期限
    @Column(name = "id_card_valid_time", nullable = false, length = 50)
    String idCardValidTime;
    //开户名称
    @Column(name = "account_name", nullable = false, length = 50)
    String accountName;
    //开户银行
    @Column(name = "account_bank", nullable = false, length = 50)
    String accountBank;

    //开户银行省市编码
    @Column(name = "bank_address_code", nullable = false, length = 6)
    String bankAddressCode;
    //银行账号
    @Column(name = "account_number", nullable = false, length = 50)
    String accountNumber;
    //门店名称
    @Column(name = "store_name", nullable = false, length = 128)
    String storeName;
    //门店省市编码
    @Column(name = "store_address_code", nullable = false, length = 6)
    String storeAddressCode;
    //门店街道名称
    @Column(name = "store_street", nullable = false, length = 500)
    String storeStreet;
    //门店门口照片
    @Column(name = "store_entrance_pic_src", nullable = false, length = 500)
    String storeEntrancePicSrc;

    //门店门口照片
    @Column(name = "store_entrance_pic",length = 256)
    String storeEntrancePic;

    //店内环境照片
    @Column(name = "indoor_pic_src", nullable = false, length = 500)
    String indoorPicSrc;
    //店内环境照片
    @Column(name = "indoor_pic", length = 256)
    String indoorPic;


    //商户简称
    @Column(name = "merchant_short_name", nullable = false, length = 50)
    String merchantShortName;
    //客服电话
    @Column(name = "service_phone", nullable = false, length = 50)
    String servicePhone;
    //售卖商品/提供服务描述
    @Column(name = "product_desc", nullable = false, length = 50)
    String productDesc;
    //费率枚举值{0.38%、0.39%、0.4%、0.45%、0.48%、0.49%、0.5%、0.55%、0.58%、0.59%、0.6%}
    @Column(name = "rate", nullable = false, length = 50)
    String rate;
    //超级管理员姓名 和身份证姓名一致
    @Column(name = "contact", nullable = false, length = 50)
    String contact;
    //手机号
    @Column(name = "contact_phone", nullable = false, length = 50)
    String contactPhone;

    //联系邮箱
    @Column(name = "contact_email", length = 50)
    String contactEmail;

    //银行账号
    @Column(name = "bank_name", length = 256)
    String bankName;

    //商户申请单号
    @Column(name = "applyment_id", length = 32)
    String applymentId;
    //创建时间
    @Column(name = "create_time", nullable = false, length = 19)
    Date createTime;
    //修改时间
    @Column(name = "update_time", nullable = false, length = 19)
    Date updateTime;
    //账户状态{-1:删除,0:冻结,1:正常}
    @Column(name = "status", nullable = false, length = 1)
    Integer status = 1;


    public WxMicroAccount() {
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

    public String getIdCardCopy() {
        return idCardCopy;
    }

    public void setIdCardCopy(String idCardCopy) {
        this.idCardCopy = idCardCopy;
    }

    public String getIdCardNational() {
        return idCardNational;
    }

    public void setIdCardNational(String idCardNational) {
        this.idCardNational = idCardNational;
    }

    public String getIdCardName() {
        return idCardName;
    }

    public void setIdCardName(String idCardName) {
        this.idCardName = idCardName;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getIdCardValidTime() {
        return idCardValidTime;
    }

    public void setIdCardValidTime(String idCardValidTime) {
        this.idCardValidTime = idCardValidTime;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountBank() {
        return accountBank;
    }

    public void setAccountBank(String accountBank) {
        this.accountBank = accountBank;
    }

    public String getBankAddressCode() {
        return bankAddressCode;
    }

    public void setBankAddressCode(String bankAddressCode) {
        this.bankAddressCode = bankAddressCode;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddressCode() {
        return storeAddressCode;
    }

    public void setStoreAddressCode(String storeAddressCode) {
        this.storeAddressCode = storeAddressCode;
    }

    public String getStoreStreet() {
        return storeStreet;
    }

    public void setStoreStreet(String storeStreet) {
        this.storeStreet = storeStreet;
    }

    public String getStoreEntrancePic() {
        return storeEntrancePic;
    }

    public void setStoreEntrancePic(String storeEntrancePic) {
        this.storeEntrancePic = storeEntrancePic;
    }

    public String getIndoorPic() {
        return indoorPic;
    }

    public void setIndoorPic(String indoorPic) {
        this.indoorPic = indoorPic;
    }

    public String getMerchantShortName() {
        return merchantShortName;
    }

    public void setMerchantShortName(String merchantShortName) {
        this.merchantShortName = merchantShortName;
    }

    public String getServicePhone() {
        return servicePhone;
    }

    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getApplymentId() {
        return applymentId;
    }

    public void setApplymentId(String applymentId) {
        this.applymentId = applymentId;
    }


    public String getIdCardCopySrc() {
        return idCardCopySrc;
    }

    public void setIdCardCopySrc(String idCardCopySrc) {
        this.idCardCopySrc = idCardCopySrc;
    }

    public String getIdCardNationalSrc() {
        return idCardNationalSrc;
    }

    public void setIdCardNationalSrc(String idCardNationalSrc) {
        this.idCardNationalSrc = idCardNationalSrc;
    }

    public String getStoreEntrancePicSrc() {
        return storeEntrancePicSrc;
    }

    public void setStoreEntrancePicSrc(String storeEntrancePicSrc) {
        this.storeEntrancePicSrc = storeEntrancePicSrc;
    }

    public String getIndoorPicSrc() {
        return indoorPicSrc;
    }

    public void setIndoorPicSrc(String indoorPicSrc) {
        this.indoorPicSrc = indoorPicSrc;
    }

    public boolean isMediaRead() {
        if (null == getIdCardCopy()
                || null == getIdCardNational()
                || null == getIndoorPic()
                || null == getStoreEntrancePic()
        ) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", "{", "}")
                .add("id:" + id)
                .add("unitId:'" + unitId + "'")
                .add("businessCode:'" + businessCode + "'")
                .add("idCardCopy:'" + idCardCopy + "'")
                .add("idCardNational:'" + idCardNational + "'")
                .add("idCardName:'" + idCardName + "'")
                .add("idCardNumber:'" + idCardNumber + "'")
                .add("idCardValidTime:'" + idCardValidTime + "'")
                .add("accountName:'" + accountName + "'")
                .add("accountBank:'" + accountBank + "'")
                .add("bankAddressCode:'" + bankAddressCode + "'")
                .add("accountNumber:'" + accountNumber + "'")
                .add("storeName:'" + storeName + "'")
                .add("storeAddressCode:'" + storeAddressCode + "'")
                .add("storeStreet:'" + storeStreet + "'")
                .add("storeEntrancePic:'" + storeEntrancePic + "'")
                .add("indoorPic:'" + indoorPic + "'")
                .add("merchantShortName:'" + merchantShortName + "'")
                .add("servicePhone:'" + servicePhone + "'")
                .add("productDesc:'" + productDesc + "'")
                .add("rate:'" + rate + "'")
                .add("contact:'" + contact + "'")
                .add("contactPhone:'" + contactPhone + "'")
                .add("contactEmail:'" + contactEmail + "'")
                .add("bankName:'" + bankName + "'")
                .add("applymentId:'" + applymentId + "'")
                .add("createTime:" + DateUtil.dateTime2Str(createTime))
                .add("updateTime:" + DateUtil.dateTime2Str(updateTime))
                .add("status:" + status)
                .toString();
    }
}
