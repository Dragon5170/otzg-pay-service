package com.otzg.pay.dao;

import com.otzg.pay.entity.RefundOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @Author G.
 * @Date 2019/11/27 0027 上午 11:32
 */
public interface RefundOrderDao extends JpaRepository<RefundOrder,Long> {

    List<RefundOrder> findByPayOrderNo(String payOrderNo);
    Optional<RefundOrder> findByRefundOrderNo(String refundOrderNo);
}
