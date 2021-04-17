package com.otzg.alipay.dao;

import com.otzg.alipay.entity.AliMicroAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @Author G.
 * @Date 2019/12/5 0005 下午 5:44
 */
public interface AliMicroAccountDao extends JpaRepository<AliMicroAccount,Long> {
    Optional<AliMicroAccount> findByUnitId(String unitId);
}
