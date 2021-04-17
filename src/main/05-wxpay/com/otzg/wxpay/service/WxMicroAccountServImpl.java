package com.otzg.wxpay.service;

import com.otzg.base.AbstractServ;
import com.otzg.log.util.LogUtil;
import com.otzg.util.CheckUtil;
import com.otzg.util.DateUtil;
import com.otzg.util.FastJsonUtil;
import com.otzg.util.FuncUtil;
import com.otzg.wxpay.dao.WxMicroAccountDao;
import com.otzg.wxpay.dao.WxMicroAccountLogDao;
import com.otzg.wxpay.entity.WxMicroAccount;
import com.otzg.wxpay.entity.WxMicroAccountLog;
import com.otzg.wxpay.util.service.WxMicroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Map;
import java.util.Optional;

/**
 * @Author G.
 * @Date 2019/11/30 0030 上午 11:25
 */
@Service
public class /**/WxMicroAccountServImpl extends AbstractServ implements WxMicroAccountServ {
    @Autowired
    WxMicroAccountDao wxMicroAccountDao;
    @Autowired
    WxMicroAccountLogDao wxMicroAccountLogDao;

    @Override
    @Transactional
    public int saveMicroAccount(WxMicroAccount wxMicroAccount) {
        Optional<WxMicroAccount> op = wxMicroAccountDao.findByUnitId(wxMicroAccount.getUnitId());
        if(op.isPresent()){
            wxMicroAccount.setCreateTime(op.get().getCreateTime());
            wxMicroAccount.setId(op.get().getId());
        }else {
            wxMicroAccount.setId(getId());
            wxMicroAccount.setCreateTime(DateUtil.now());
        }

        wxMicroAccount.setBusinessCode(getBusinessCode(wxMicroAccount.getUnitId()));
        wxMicroAccount.setUpdateTime(DateUtil.now());
        wxMicroAccount.setStatus(0);
        wxMicroAccountDao.save(wxMicroAccount);
        //提交微信审核
        new Thread(() -> doMicroSubmit(wxMicroAccount)).start();
        return 0;
    }

    //自动生成订单号
    String getBusinessCode(String unitId) {
        return DateUtil.yearMonthDayTimeShort() + FuncUtil.getRandInt(0001, 9999) + Math.abs(unitId.hashCode());
    }

    //提交到微信
    void doMediaSubmit(WxMicroAccount wxMicroAccount) {
        if (null != wxMicroAccount.getIdCardCopySrc()
                && null == wxMicroAccount.getIdCardCopy()) {

            File file = new File(LogUtil.getFileSavePath() +wxMicroAccount.getIdCardCopySrc().substring(wxMicroAccount.getIdCardCopySrc().indexOf("upload")));
            if (null == file)
                return;

            String mediaId = new WxMicroUtil().doUploadImg(file);
            if (null == mediaId) {
                return;
            }
            wxMicroAccount.setIdCardCopy(mediaId);
        }

        if (null != wxMicroAccount.getIdCardNationalSrc()
                && null == wxMicroAccount.getIdCardNational()) {

            File file = new File(LogUtil.getFileSavePath() +wxMicroAccount.getIdCardNationalSrc().substring(wxMicroAccount.getIdCardNationalSrc().indexOf("upload")));
            if (null == file)
                return;

            String mediaId = new WxMicroUtil().doUploadImg(file);
            if (null == mediaId) {
                return;
            }
            wxMicroAccount.setIdCardNational(mediaId);
        }

        if (null != wxMicroAccount.getStoreEntrancePicSrc()
                && null == wxMicroAccount.getStoreEntrancePic()) {

            File file = new File(LogUtil.getFileSavePath() +wxMicroAccount.getStoreEntrancePicSrc().substring(wxMicroAccount.getStoreEntrancePicSrc().indexOf("upload")));
            if (null == file)
                return;

            String mediaId = new WxMicroUtil().doUploadImg(file);
            if (null == mediaId) {
                return;
            }
            wxMicroAccount.setStoreEntrancePic(mediaId);
        }

        if (null != wxMicroAccount.getIndoorPicSrc()
                && null == wxMicroAccount.getIndoorPic()) {

            File file = new File(LogUtil.getFileSavePath() +wxMicroAccount.getIndoorPicSrc().substring(wxMicroAccount.getIndoorPicSrc().indexOf("upload")));
            if (null == file)
                return;

            String mediaId = new WxMicroUtil().doUploadImg(file);
            if (null == mediaId) {
                return;
            }
            wxMicroAccount.setIndoorPic(mediaId);
        }

        wxMicroAccount.setUpdateTime(DateUtil.now());
        wxMicroAccountDao.save(wxMicroAccount);
    }

    //提交到微信
    void doMicroSubmit(WxMicroAccount wxMicroAccount) {
        if (!wxMicroAccount.isMediaRead()) {
            doMediaSubmit(wxMicroAccount);
        }

        if (!wxMicroAccount.isMediaRead()) {
            doMediaSubmit(wxMicroAccount);
        }
        if (!wxMicroAccount.isMediaRead()) {
            return;
        }

        String applyment_id = new WxMicroUtil().doMicroSubmit(wxMicroAccount);
        if (null != applyment_id) {
            return;
        }

        wxMicroAccount.setApplymentId(applyment_id);
        wxMicroAccountDao.save(wxMicroAccount);
    }

    @Override
    public Map query(String unitId) {
        Optional<WxMicroAccount> op = wxMicroAccountDao.findByUnitId(unitId);
        if (!op.isPresent()) {
            return null;
        }

        return microAccountQuery(op.get().getUnitId(), op.get().getBusinessCode());
    }

    //查询审批结果
    Map microAccountQuery(String unitId, String businessCode) {
        Optional<WxMicroAccountLog> op = wxMicroAccountLogDao.findByBusinessCode(businessCode);
        if (op.isPresent()) {
            return op.get().getJson();
        }

        Map map = new WxMicroUtil().doMicroSubmitQuery(businessCode);
        //{nonce_str=zciinAeKFho1agXy, applyment_state=REJECTED, sign=6D9A2C9C7B20453F16AD14DC76F3EDACE138D41E781B71BD571C4AD740A60F72,
        // applyment_state_desc=已驳回, return_msg=OK, result_code=SUCCESS,
        // audit_detail={"audit_detail":[{"param_name":"id_card_copy","reject_reason":"身份证正面识别失败，请重新上传"}]},
        // return_code=SUCCESS, applyment_id=2000002132954441}

        //创建一个结果对象
        WxMicroAccountLog wxMicroAccountLog = new WxMicroAccountLog();
        wxMicroAccountLog.setBusinessCode(businessCode);
        wxMicroAccountLog.setId(getId());

        wxMicroAccountLog.setCreateTime(DateUtil.now());

        wxMicroAccountLog.setUnitId(unitId);

        Map result = (Map) map.get("data");
        P("小微进件审批结果 businessCode=>" + businessCode);

        wxMicroAccountLog.setApplymentId(result.get("applyment_id").toString());
        wxMicroAccountLog.setApplymentState(result.get("applyment_state").toString());
        wxMicroAccountLog.setApplymentStateDesc(result.get("applyment_state_desc").toString());

        //如果失败
        if (map.get("success").equals(false)
                && !CheckUtil.isEmpty(result.get("audit_detail"))) {
            wxMicroAccountLog.setAuditDetail(FastJsonUtil.getJson(result.get("audit_detail")).get("audit_detail").toString());
            wxMicroAccountLog.setStatus(0);
        }
        //如果成功
        if (map.get("success").equals(true)
                && null != result.get("sub_mch_id")
                && null != result.get("sign_url")) {
            wxMicroAccountLog.setSubMchId(result.get("sub_mch_id").toString());
            wxMicroAccountLog.setSignUrl(result.get("sign_url").toString());
            wxMicroAccountLog.setStatus(1);
        }
        //保存结果
        wxMicroAccountLogDao.save(wxMicroAccountLog);
        return wxMicroAccountLog.getJson();
    }
}
