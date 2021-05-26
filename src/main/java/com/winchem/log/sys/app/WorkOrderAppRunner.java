package com.winchem.log.sys.app;

import com.winchem.log.sys.task.service.TaskInitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @program: aftersale
 * @description: 容器启动后加载 工单巡检定时任务
 * @author: zhanglb
 * @create: 2020-10-30 13:47
 */
@Component
public class WorkOrderAppRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(WorkOrderAppRunner.class);

    @Autowired
    private TaskInitService taskInitService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("初始化定时任务---taskInitService---start ");
//        taskInitService.init();
        log.info("初始化定时任务---taskInitService---end ");
    }
}
