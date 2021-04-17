package com.otzg;

import com.otzg.log.util.LogUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 设置系统定时任务
 * @author G./2018/3/1 17:09
 */


@Configuration
@EnableScheduling
public class SchedulingConfig {

    /**
     * 自动执行任务
     * @author G/2018/5/19 17:21
     * @param
     */
    @Scheduled(fixedRate=1000*60*2)
    public void autoRun() {
        //保存访问日志
        LogUtil.saveWebAccessLog(LogUtil.getFileSavePath());
        LogUtil.saveSysErrorLog(LogUtil.getFileSavePath());
    }
}
