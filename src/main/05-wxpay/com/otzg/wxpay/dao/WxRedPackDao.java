package com.otzg.wxpay.dao;

import com.otzg.wxpay.entity.WxRedPack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @Author G.
 * @Date 2019/12/13 0013 下午 2:34
 */
public interface WxRedPackDao extends JpaRepository<WxRedPack,Long> {
    Optional<WxRedPack> findByRedPackOrderNo(String redpackOrderNo);
}
