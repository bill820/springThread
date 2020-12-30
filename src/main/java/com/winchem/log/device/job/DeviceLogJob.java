package com.winchem.log.device.job;

import com.winchem.log.common.utils.DateUtils;
import com.winchem.log.device.service.IDeviceLogService;
import com.winchem.log.sys.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @program: winchem_log
 * @description: 日志存储job
 * @author: zhanglb
 * @create: 2020-12-10 14:04
 */
@Slf4j
public class DeviceLogJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("日志存储job----DeviceLogJob----start"+ DateUtils.getDateTime());
        IDeviceLogService logService = SpringUtils.getBean(IDeviceLogService.class);
        String id = jobExecutionContext.getJobDetail().getJobDataMap().get("id").toString();
        logService.saveDeviceLogFromMongodb(id);
        log.info("日志存储job----DeviceLogJob----end"+ DateUtils.getDateTime());
    }
}
