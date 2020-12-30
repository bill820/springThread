package com.winchem.log.sys.task.listener;

/**
 * @program: winchem_log
 * @description:
 * @author: zhanglb
 * @create: 2020-12-11 18:15
 */
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.winchem.log.sys.task.entity.TaskCronJob;
import com.winchem.log.sys.task.service.ITaskCronJobService;
import com.winchem.log.sys.task.utils.TaskUtils;
import com.winchem.log.sys.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

import java.util.Date;

@Slf4j
public class TaskJobListener implements JobListener{

    private ITaskCronJobService taskCronJobService = SpringUtils.getBean(ITaskCronJobService.class);

    @Override
    public String getName() {
        String name = getClass().getSimpleName();
        log.info(" listener name is:"+name);
        return name;
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        String jobName = context.getJobDetail().getKey().getName();

        log.info(jobName + " is going to be executed");
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        String jobName = context.getJobDetail().getKey().getName();
        log.info(jobName + " was vetoed and not executed");
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        String jobName = context.getJobDetail().getKey().getName();
        log.info(jobName + " was executed");

    }
}
