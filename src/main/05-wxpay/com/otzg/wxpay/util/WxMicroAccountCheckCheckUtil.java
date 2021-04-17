package com.otzg.wxpay.util;

import com.otzg.util.DtoCheckUtil;
import com.otzg.util.RespTips;
import com.otzg.wxpay.dto.WxMicroAccountDto;
import com.otzg.wxpay.entity.WxMicroAccount;

/**
 * @Author G.
 * @Date 2019/11/30 0030 上午 11:51
 */
public class WxMicroAccountCheckCheckUtil extends DtoCheckUtil<WxMicroAccountDto> {
    public WxMicroAccountCheckCheckUtil(WxMicroAccountDto wxMicroAccountDto) {
        super(wxMicroAccountDto);
    }

    /**
     * 校验收款业务单参数
     */
    protected void check() {
        //开户银行必填
        if (isCorrect(t.getAccountBank(), 50)) {
            msg = "开户银行(accountBank,50)参数错误";
            code = RespTips.PARAM_ERROR.code;
            hasErrors = true;
            return;
        }
        if (isCorrect(t.getAccountName(), 50)) {
            msg = "开户名称(accountName,50)参数错误";
            code = RespTips.PARAM_ERROR.code;
            hasErrors = true;
            return;
        }
        if (isCorrect(t.getAccountNumber(), 50)) {
            msg = "银行账号(accountNumber,50)参数错误";
            code = RespTips.PARAM_ERROR.code;
            hasErrors = true;
            return;
        }

        if (isCorrect(t.getBankAddressCode(), 6)) {
            msg = "开户银行省市编码(bankAddressCode,6)参数错误";
            code = RespTips.PARAM_ERROR.code;
            hasErrors = true;
            return;
        }
        if (isCorrect(t.getBankName(), 256)) {
            msg = "开户银行全称（含支行）(bankName)参数错误";
            code = RespTips.PARAM_ERROR.code;
            hasErrors = true;
            return;
        }
        if (isCorrect(t.getContact(), 50)) {
            msg = "超级管理员姓名(contact,50)参数错误";
            code = RespTips.PARAM_ERROR.code;
            hasErrors = true;
            return;
        }
        if (isCorrect(t.getContactEmail(), 50)) {
            msg = "联系邮箱(contactEmail)参数错误";
            code = RespTips.PARAM_ERROR.code;
            hasErrors = true;
            return;
        }

        if (isCorrect(t.getContactPhone(), 50)) {
            msg = "手机号(contactPhone)参数错误";
            code = RespTips.PARAM_ERROR.code;
            hasErrors = true;
            return;
        }
        if (isCorrect(t.getIdCardCopySrc(), 500)) {
            msg = "身份证人像面照片(idCardCopySrc)参数错误";
            code = RespTips.PARAM_ERROR.code;
            hasErrors = true;
            return;
        }
        if (isCorrect(t.getIdCardName(), 64)) {
            msg = "身份证姓名(idCardName)参数错误";
            code = RespTips.PARAM_ERROR.code;
            hasErrors = true;
            return;
        }
        if (isCorrect(t.getIdCardNationalSrc(), 500)) {
            msg = "身份证国徽面照片(idCardNationalSrc)参数错误";
            code = RespTips.PARAM_ERROR.code;
            hasErrors = true;
            return;
        }
        if (isCorrect(t.getIdCardNumber(), 18)) {
            msg = "身份证号码(idCardNumber)参数错误";
            code = RespTips.PARAM_ERROR.code;
            hasErrors = true;
            return;
        }
        if (isCorrect(t.getIdCardValidTime(), 50)) {
            msg = "身份证有效期限(idCardValidTime)参数错误";
            code = RespTips.PARAM_ERROR.code;
            hasErrors = true;
            return;
        }
        if (isCorrect(t.getIndoorPicSrc(), 500)) {
            msg = "店内环境照片(indoorPicSrc)参数错误";
            code = RespTips.PARAM_ERROR.code;
            hasErrors = true;
            return;
        }
        if (isCorrect(t.getMerchantShortName(), 50)) {
            msg = "商户简称(merchantShortName)参数错误";
            code = RespTips.PARAM_ERROR.code;
            hasErrors = true;
            return;
        }
        if (isCorrect(t.getProductDesc(), 50)) {
            msg = "售卖商品/提供服务描述(productDesc)参数错误";
            code = RespTips.PARAM_ERROR.code;
            hasErrors = true;
            return;
        }
        if (isCorrect(t.getRate(), 50)) {
            msg = "费率(rate)参数错误";
            code = RespTips.PARAM_ERROR.code;
            hasErrors = true;
            return;
        }
        if (isCorrect(t.getServicePhone(), 50)) {
            msg = "客服电话(servicePhone)参数错误";
            code = RespTips.PARAM_ERROR.code;
            hasErrors = true;
            return;
        }
        if (isCorrect(t.getStoreAddressCode(), 6)) {
            msg = "门店省市编码(storeAddressCode)参数错误";
            code = RespTips.PARAM_ERROR.code;
            hasErrors = true;
            return;
        }
        if (isCorrect(t.getStoreEntrancePicSrc(), 500)) {
            msg = "门店门口照片(storeEntrancePicSrc)参数错误";
            code = RespTips.PARAM_ERROR.code;
            hasErrors = true;
            return;
        }
        if (isCorrect(t.getStoreName(), 128)) {
            msg = "门店名称(storeName)参数错误";
            code = RespTips.PARAM_ERROR.code;
            hasErrors = true;
            return;
        }
        if (isCorrect(t.getStoreStreet(), 500)) {
            msg = "门店街道名称(storeStreet)参数错误";
            code = RespTips.PARAM_ERROR.code;
            hasErrors = true;
            return;
        }

        code = RespTips.SUCCESS_CODE.code;
        hasErrors = true;
    }

    /**
     * 返回持久化对象
     * @return
     */
    public WxMicroAccount getPojo() {
        WxMicroAccount wxMicroAccount = new WxMicroAccount();
        wxMicroAccount.setAccountBank(t.getAccountBank());
        wxMicroAccount.setAccountName(t.getAccountName());
        wxMicroAccount.setAccountNumber(t.getAccountNumber());
        wxMicroAccount.setBankAddressCode(t.getBankAddressCode());
        wxMicroAccount.setContact(t.getContact());
        wxMicroAccount.setContactPhone(t.getContactPhone());
        wxMicroAccount.setContactEmail(t.getContactEmail());
        wxMicroAccount.setIdCardCopySrc(t.getIdCardCopySrc());
        wxMicroAccount.setIdCardName(t.getIdCardName());
        wxMicroAccount.setIdCardNumber(t.getIdCardNumber());
        wxMicroAccount.setIdCardNationalSrc(t.getIdCardNationalSrc());
        wxMicroAccount.setIdCardValidTime(t.getIdCardValidTime());
        wxMicroAccount.setIndoorPicSrc(t.getIndoorPicSrc());
        wxMicroAccount.setMerchantShortName(t.getMerchantShortName());
        wxMicroAccount.setProductDesc(t.getProductDesc());
        wxMicroAccount.setRate(t.getRate());
        wxMicroAccount.setServicePhone(t.getServicePhone());
        wxMicroAccount.setStoreAddressCode(t.getStoreAddressCode());
        wxMicroAccount.setStoreEntrancePicSrc(t.getStoreEntrancePicSrc());
        wxMicroAccount.setStoreName(t.getStoreName());
        wxMicroAccount.setStoreStreet(t.getStoreStreet());
        return wxMicroAccount;
    }

}
