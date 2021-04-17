package com.otzg.pay.dao;

import com.otzg.pay.entity.PayOrderLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author G./2018/7/11 19:09
 */
public interface PayOrderLogDao extends JpaRepository<PayOrderLog,Long> {
}

