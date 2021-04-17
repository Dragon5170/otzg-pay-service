package com.otzg.util;

import java.util.Map;

/**
 * @Author G.
 * 基本教研工具
 * @Date 2019/11/30 0030 下午 1:42
 */
public abstract class DtoCheckUtil<T> {
    protected T t;
    protected boolean hasErrors = false;
    protected String code = "";
    protected String msg = "";
    //构造函数
    public DtoCheckUtil(T t) {
        this.t = t;
    }

    //校验结果
    public boolean hasErrors() {
        return this.hasErrors;
    }

    //返回参数校验结果
    public Map getMsg() {
        return ResultUtil.getJson(hasErrors, code, msg);
    }

    /**
     * 校验收款业务单参数
     */
    protected abstract void check();

    /**
     * 是否为空
     * @param param
     * @return
     */
    protected boolean isEmpty(Object param) {
        return CheckUtil.isEmpty(param);
    }


    /**
     * 校验参数
     *
     * @param param
     * @param length
     * @return
     */
    protected boolean isCorrect(String param, int length) {
        return CheckUtil.isCorrect(param, length);
    }

    /**
     * 校验金额
     *
     * @param amount
     * @return
     */
    protected boolean checkAmount(Double amount) {
        return CheckUtil.isAmount(amount);
    }

}
