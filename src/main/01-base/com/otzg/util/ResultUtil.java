package com.otzg.util;

import com.otzg.pay.enums.PayStatus;
import com.otzg.util.FMap;
import com.otzg.util.HashFMap;

/**
 * 返回前端结果数据
 * v1.10
 *
 * @Author G.
 * @Date 2020/1/4 0004 下午 5:17
 */
public class ResultUtil {


    public final static FMap getJson(boolean success, String code, String msg) {
        return getJson(success, code, msg, null);
    }

    public final static FMap getJson(boolean success, String code, String msg, Object result) {
        return new HashFMap()
                .p("success", success)
                .p("code", code)
                .p("msg", msg)
                .p("result", result);
    }

    public final static FMap getPageJson(Long totalPage, Long totalElement, Object content) {
        return new HashFMap()
                .p("totalPage", totalPage)
                .p("totalElement", totalElement)
                .p("content", content);
    }



    public final static FMap payResult(int status,Object body) {
        return new HashFMap()
                .p("result", status)
                .p("msg",PayStatus.getTips(status))
                .p("body",body);
    }

    public final static FMap paySuccess(Object ... body) {
        return new HashFMap()
                .p("result", PayStatus.SUCCESS.status)
                .p("msg",PayStatus.SUCCESS.tips)
                .p("body",body[0]);
    }

    public final static FMap payWaiting(Object ... body) {
        return new HashFMap()
                .p("result", PayStatus.WAITING.status)
                .p("msg",PayStatus.WAITING.tips)
                .p("body",body[0]);
    }

    public final static FMap payFailed(Object ... body) {
        return new HashFMap()
                .p("result", PayStatus.FAILED.status)
                .p("msg",PayStatus.FAILED.tips)
                .p("body",body[0]);
    }
}
