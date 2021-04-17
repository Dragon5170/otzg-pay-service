package com.otzg.pay.dao;

import com.otzg.pay.entity.PayAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * @author G./2018/6/29 11:56
 */
public interface PayAccountDao extends JpaRepository<PayAccount,Long> {

    @Query("select pa from PayAccount pa where pa.unitId=?1")
    Optional<PayAccount> findByUnitId(String unitId);
}
