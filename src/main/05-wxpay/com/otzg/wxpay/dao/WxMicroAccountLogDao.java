package com.otzg.wxpay.dao;

import com.otzg.wxpay.entity.WxMicroAccountLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @Author G.
 * @Date 2019/11/30 0030 下午 4:33
 */
public interface WxMicroAccountLogDao extends JpaRepository<WxMicroAccountLog,Long> {
    Optional<WxMicroAccountLog> findByUnitId(String unitId);

    Optional<WxMicroAccountLog> findByBusinessCode(String businessCode);
}
