package com.winchem.log.sys.task.listener;

/**
 * @program: winchem_log
 * @description:
 * @author: zhanglb
 * @create: 2020-12-14 14:52
 */
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.winchem.log.common.utils.DateUtils;
import com.winchem.log.sys.task.entity.TaskCronJob;
import com.winchem.log.sys.task.service.ITaskCronJobService;
import com.winchem.log.sys.task.utils.TaskUtils;
import com.winchem.log.sys.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.Trigger.CompletedExecutionInstruction;
import org.quartz.TriggerListener;

import java.util.Date;

@Slf4j
public class TaskTriggerListener implements TriggerListener{

    private ITaskCronJobService taskCronJobService = SpringUtils.getBean(ITaskCronJobService.class);

    private String name;

    public TaskTriggerListener(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        String taskId = context.getJobDetail().getJobDataMap().get("id").toString();
        log.info("定时任务:设备日志同步到本地开始执行,更新定时任务执行的时间(executeTime)");
        LambdaUpdateWrapper<TaskCronJob> wrapper = new LambdaUpdateWrapper<TaskCronJob>();
        wrapper.eq(TaskCronJob::getId, taskId)
                .setSql("execute_time = '"+DateUtils.getNowStr()+"'");
        taskCronJobService.update(wrapper);
    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        String triggerName = trigger.getKey().getName();
        log.info(triggerName + " was not vetoed");
        return false;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {
        String triggerName = trigger.getKey().getName();
        log.info(triggerName + " misfired");
    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext context,
                                CompletedExecutionInstruction triggerInstructionCode) {
        String taskId = context.getJobDetail().getJobDataMap().get("id").toString();
        log.info("定时任务:设备日志同步到本地完成执行,更新定时任务完成的时间(endTime)");
        LambdaUpdateWrapper<TaskCronJob> wrapper = new LambdaUpdateWrapper<TaskCronJob>();
        wrapper.eq(TaskCronJob::getId, taskId)
                .setSql("end_time = '"+DateUtils.getNowStr()+"'");
        taskCronJobService.update(wrapper);
    }
}
