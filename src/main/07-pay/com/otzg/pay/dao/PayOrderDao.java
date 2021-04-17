package com.otzg.pay.dao;

import com.otzg.pay.entity.PayOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author G./2018/7/3 10:46
 */
public interface PayOrderDao extends JpaRepository<PayOrder,Long> {
    //根据支付业务单查询
    Optional<PayOrder> findByPayOrderNo(String payOrderNo);

    //根据子系统收款单查询
    @Query("select po from PayOrder po where po.subOrderNo=?1")
    List<PayOrder> findBySubOrderNo(String subOrderNo);

    Optional<PayOrder> findByUnitIdAndSubOrderNo(String unitId,String subOrderNo);
    Optional<PayOrder> findByUnitIdAndPayOrderNo(String unitId,String payOrderNo);
}
