package com.otzg.pay.util;

import java.util.Map;

/**
 * @Author G.
 * @Date 2019/12/30 0030 下午 6:23
 */
public interface PayOrderDtoCheck {
    Map getMsg();
    boolean hasErrors();
}
