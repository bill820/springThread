package com.winchem.log.sys.task.service;

import com.winchem.log.common.utils.DateUtils;
import com.winchem.log.sys.task.entity.TaskCronJob;
import com.winchem.log.sys.task.factory.TaskSchedulerFactory;
import com.winchem.log.sys.task.listener.TaskTriggerListener;
import com.winchem.log.sys.task.utils.MyBeanUtils;
import com.winchem.log.sys.task.utils.TaskUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.*;
import org.quartz.impl.matchers.KeyMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;


import static org.quartz.CronExpression.isValidExpression;

/**
 * @program: aftersale
 * @description: 任务初始化
 * @author: zhanglb
 * @create: 2020-10-30 16:25
 */
@Service("taskInitService")
public class TaskInitService {

    private static final Logger log = LoggerFactory.getLogger(TaskInitService.class);

    @Resource
    private ITaskCronJobService taskCronJobService;

    @Resource
    private TaskSchedulerFactory schedulerFactory;

    /**
     * 初始化
     */
    public void init() {
        Scheduler scheduler = schedulerFactory.getScheduler();
        if (scheduler == null) {
            log.error("初始化定时任务组件失败，Scheduler is null...");
            return;
        }
        // 初始化基于cron时间配置的任务列表
        try {
            initCronJobs(scheduler);
        } catch (Exception e) {
            log.error("init cron tasks error," + e.getMessage(), e);
        }

        try {
            log.info("The scheduler is starting...");
            // start the scheduler
            scheduler.start();
        } catch (Exception e) {
            log.error("The scheduler start is error," + e.getMessage(), e);
        }
    }

    /**
     * 初始化任务（基于cron触发器）
     *
     */
    private void initCronJobs(Scheduler scheduler) throws Exception {
        List<TaskCronJob> jobList = taskCronJobService.list();
        if (CollectionUtils.isNotEmpty(jobList)) {
            for (TaskCronJob job : jobList) {
                scheduleCronJob(job, scheduler);
            }
        }
    }



    /**
     * 安排任务(基于cron触发器)
     *
     * @param job
     * @param scheduler
     */
    private void scheduleCronJob(TaskCronJob job, Scheduler scheduler) {
        if (job != null && StringUtils.isNotBlank(job.getJobName()) && StringUtils.isNotBlank(job.getJobClassName())
                && StringUtils.isNotBlank(job.getCron()) && scheduler != null) {
            if (!job.getEnabled()) {
                return;
            }

            try {
                JobKey jobKey = TaskUtils.genCronJobKey(job);

                if (!scheduler.checkExists(jobKey)) {
                    // This job doesn't exist, then add it to scheduler.
                    log.info("Add new cron job to scheduler, jobName = " + job.getJobName());
                    this.newJobAndNewCronTrigger(job, scheduler, jobKey);
                } else {
                    log.info("Update cron job to scheduler, jobName = " + job.getJobName());
                    this.updateCronTriggerOfJob(job, scheduler, jobKey);
                }
            } catch (Exception e) {
                log.error("ScheduleCronJob is error," + e.getMessage(), e);
            }
        } else {
            log.error("Method scheduleCronJob arguments are invalid.");
        }
    }


    /**
     * 新建job和trigger到scheduler(基于cron触发器)
     *
     * @param job
     * @param scheduler
     * @param jobKey
     * @throws SchedulerException
     * @throws ClassNotFoundException
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void newJobAndNewCronTrigger(TaskCronJob job, Scheduler scheduler, JobKey jobKey)
            throws SchedulerException, ClassNotFoundException {
        TriggerKey triggerKey = TaskUtils.genCronTriggerKey(job);

        String cronExpr = job.getCron();
        if (!isValidExpression(cronExpr)) {
            return;
        }

        JobDataMap dataMap = new JobDataMap();
        dataMap.put("id", job.getId());

        // get a Class object by string class name of job;
        Class jobClass = Class.forName(job.getJobClassName().trim());
        JobDetail jobDetail = JobBuilder.newJob(jobClass)
                                        .withIdentity(jobKey)
                                        .usingJobData(dataMap)
                                        .withDescription(job.getJobDescription())
                                        .build();
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).forJob(jobKey)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpr).withMisfireHandlingInstructionDoNothing())
                .build();

        scheduler.scheduleJob(jobDetail, trigger);

        // 创建并注册一个局部的Trigger Listener
        scheduler.getListenerManager().addTriggerListener(new TaskTriggerListener(TaskUtils.genCronListenerKey(job)), KeyMatcher.keyEquals(triggerKey));
    }

    /**
     * 更新job的trigger(基于cron触发器)
     *
     * @param job
     * @param scheduler
     * @param jobKey
     * @throws SchedulerException
     */
    private void updateCronTriggerOfJob(TaskCronJob job, Scheduler scheduler, JobKey jobKey) throws SchedulerException {
        TriggerKey triggerKey = TaskUtils.genCronTriggerKey(job);
        String cronExpr = job.getCron().trim();

        List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);

        for (int i = 0; triggers != null && i < triggers.size(); i++) {
            Trigger trigger = triggers.get(i);
            TriggerKey curTriggerKey = trigger.getKey();

            if (TaskUtils.isTriggerKeyEqual(triggerKey, curTriggerKey)) {
                if (trigger instanceof CronTrigger
                        && cronExpr.equalsIgnoreCase(((CronTrigger) trigger).getCronExpression())) {
                    // Don't need to do anything.
                } else {
                    if (isValidExpression(job.getCron())) {
                        // Cron expression is valid, build a new trigger and
                        // replace the old one.
                        CronTrigger newTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).forJob(jobKey)
                                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpr)
                                        .withMisfireHandlingInstructionDoNothing())
                                .build();
                        scheduler.rescheduleJob(curTriggerKey, newTrigger);
                    }
                }
            } else {
                // different trigger key ,The trigger key is illegal, unschedule
                // this trigger
                scheduler.unscheduleJob(curTriggerKey);
            }

        }

    }

}
