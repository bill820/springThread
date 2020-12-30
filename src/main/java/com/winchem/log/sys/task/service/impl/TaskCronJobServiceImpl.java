package com.winchem.log.sys.task.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.winchem.log.sys.task.entity.TaskCronJob;
import com.winchem.log.sys.task.factory.TaskSchedulerFactory;
import com.winchem.log.sys.task.mapper.TaskCronJobMapper;
import com.winchem.log.sys.task.service.ITaskCronJobService;
import com.winchem.log.sys.task.utils.TaskUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: aftersale
 * @description:
 * @author: zhanglb
 * @create: 2020-10-30 16:16
 */
@Slf4j
@Service("taskCronJobService")
public class TaskCronJobServiceImpl extends ServiceImpl<TaskCronJobMapper, TaskCronJob> implements ITaskCronJobService {

    @Autowired
    private TaskSchedulerFactory schedulerFactory;

    @Autowired
    private TaskCronJobMapper cronJobMapper;

    @Override
    public void addTask(TaskCronJob taskCronJob) {
        try {
            TaskCronJob job = this.getById(taskCronJob.getId());
            TriggerKey triggerKey = TaskUtils.genCronTriggerKey(job);
            Scheduler scheduler = schedulerFactory.getScheduler();
            JobKey jobKey = TaskUtils.genCronJobKey(job);
            // 如果不同则代表着CRON表达式已经修改
            if (!job.getCron().equals(taskCronJob.getCron())) {
                CronTrigger newTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).forJob(jobKey)
                        .withSchedule(CronScheduleBuilder.cronSchedule(taskCronJob.getCron()).withMisfireHandlingInstructionDoNothing()).build();
                // 更新任务
                log.info("更新任务");
                scheduler.rescheduleJob(triggerKey, newTrigger);
            }
            if (!job.getEnabled().equals(taskCronJob.getEnabled())) {
                // 如果状态为0则停止该任务
                if (!taskCronJob.getEnabled()) {
                     scheduler.unscheduleJob(triggerKey);
                     scheduler.deleteJob(jobKey);
//                    scheduler.pauseJob(jobKey);
                } else {
                    Trigger trigger = scheduler.getTrigger(triggerKey);
                    // trigger如果为null则说明scheduler中并没有创建该任务
                    if (trigger == null) {
                        Class<?> jobClass = Class.forName(job.getJobClassName().trim());
                        @SuppressWarnings("unchecked")
                        JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) jobClass).withIdentity(jobKey)
                                .withDescription(job.getJobDescription()).build();
                        trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).forJob(jobKey)
                                .withSchedule(CronScheduleBuilder.cronSchedule(taskCronJob.getCron()).withMisfireHandlingInstructionDoNothing())
                                .build();

                        scheduler.scheduleJob(jobDetail, trigger);
                    } else {
                        // 不为null则说明scheduler中有创建该任务,更新即可
                        CronTrigger newTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).forJob(jobKey)
                                .withSchedule(CronScheduleBuilder.cronSchedule(taskCronJob.getCron()).withMisfireHandlingInstructionDoNothing())
                                .build();
                        scheduler.rescheduleJob(triggerKey, newTrigger);
                    }
                }
            }
            job.setCron(taskCronJob.getCron());
            job.setEnabled(taskCronJob.getEnabled());

            this.save(job);
        } catch (Exception e) {
            log.error("定时任务刷新失败...");
            log.error(e.getMessage());
        }

    }

    @Override
    public void updateIntervalTime(String taskId, String intervalTime) {
        cronJobMapper.updateIntervalTime(taskId, intervalTime);
    }
}
