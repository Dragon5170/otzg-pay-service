package com.otzg.pay.dao;

import com.otzg.pay.entity.PayChannelAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @Author G.
 * @Date 2019/11/25 0025 上午 11:07
 */
public interface PayChannelAccountDao  extends JpaRepository<PayChannelAccount,Long> {
    Optional<PayChannelAccount> findByPayAccountIdAndPayChannel(Long payAccountId, String payChannel);

    Optional<PayChannelAccount>  findByUnitIdAndPayChannel(String unitId, String payChannel);
}
