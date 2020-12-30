package com.winchem.log.sys.task.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.winchem.log.sys.task.entity.TaskCronJob;

/**
 * @program: aftersale
 * @description: cron service
 * @author: zhanglb
 * @create: 2020-10-30 16:00
 */
public interface ITaskCronJobService extends IService<TaskCronJob> {

    void addTask(TaskCronJob taskCronJob);

    void updateIntervalTime(String task_id, String intervalTime);
}
