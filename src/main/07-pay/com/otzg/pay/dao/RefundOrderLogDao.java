package com.otzg.pay.dao;

import com.otzg.pay.entity.RefundOrderLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author G.
 * @Date 2019/11/27 0027 下午 6:08
 */
public interface RefundOrderLogDao extends JpaRepository<RefundOrderLog,Long> {
}
