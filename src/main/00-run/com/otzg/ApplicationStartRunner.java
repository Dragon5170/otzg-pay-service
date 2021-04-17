package com.otzg;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 启动执行器
 * @author G./2018/8/4 14:48
 */
@Component
public class ApplicationStartRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments var1) throws Exception{
        //加载访问权限

        //加载微信守护线程
//        Thread wx = new Thread(new TokenThread());
//        wx.setDaemon(true);
//        wx.start();
    }

}