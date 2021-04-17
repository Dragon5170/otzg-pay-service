package com.otzg.base;

import com.otzg.log.util.LogUtil;

public abstract class BaseBean {

    //保存日志
    protected final void P(Object o){
        P(o,"info");
    }
    //支持格式化输出
    protected final void P(String str, Object... args){
        P(String.format(str,args),"info");
    }

    /**
     * 保存业务层日志
     * @author G/2018/2/28 14:37
     * @param level DEBUG,INFO,WARN,ERROR,FATAL
     */
    protected final void P(Object o,String level){
        LogUtil.addSysErrorLog(o,level);
    }

    //支持格式化输出
    protected final void PT(String str, Object... args){
        PT(String.format(str,args));
    }

    protected final void PT(String str){
        LogUtil.saveTradeLog(str);
    }

    /**
     * 调试回显,日志记录
     *
     * @param
     * @return
     * @author:G/2017年12月30日
     */
    protected final void log(Object str) {
        LogUtil.print(str);
    }


}
