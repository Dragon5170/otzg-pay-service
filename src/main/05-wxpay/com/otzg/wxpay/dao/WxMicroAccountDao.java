package com.otzg.wxpay.dao;

import com.otzg.wxpay.entity.WxMicroAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @Author G.
 * @Date 2019/11/30 0030 上午 9:33
 */
public interface WxMicroAccountDao extends JpaRepository<WxMicroAccount, Long> {
    Optional<WxMicroAccount> findByUnitId(String unitId);
}
