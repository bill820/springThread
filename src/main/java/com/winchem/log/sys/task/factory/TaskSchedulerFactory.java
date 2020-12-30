package com.winchem.log.sys.task.factory;

import com.winchem.log.sys.utils.LogUtils;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

/**
 * @program: aftersale
 * @description: 调度工厂类
 * @author: zhanglb
 * @create: 2020-10-30 15:47
 */
@Component
public class TaskSchedulerFactory {

    private volatile Scheduler scheduler;

    /**
     * 获得scheduler实例
     */
    public Scheduler getScheduler() {
        Scheduler s = scheduler;
        if (s == null) {
            synchronized (this) {
                s = scheduler;
                if (s == null) {
                    // 双重检查
                    try {
                        SchedulerFactory sf = new StdSchedulerFactory();
                        s = scheduler = sf.getScheduler();
                    } catch (Exception e) {
                        LogUtils.error("Get scheduler error :" , e.getMessage(), e);
                    }
                }
            }
        }

        return s;
    }
}
