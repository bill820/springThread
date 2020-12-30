package com.winchem.log.device.job;

import com.winchem.log.common.utils.DateUtils;
import com.winchem.log.device.service.IDeviceLatestLogService;
import com.winchem.log.device.service.IDeviceLogService;
import com.winchem.log.sys.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @program: winchem_log
 * @description: 最新洗涤量同步 job
 * @author: zhanglb
 * @create: 2020-12-10 14:04
 */
@Slf4j
public class DeviceLatestLogJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("最新洗涤量同步job----DeviceLatestLogJob----start"+ DateUtils.getDateTime());
        IDeviceLatestLogService latestLogService =  SpringUtils.getBean(IDeviceLatestLogService.class);
        latestLogService.saveDayQuantity();
        log.info("最新洗涤量同步job----DeviceLatestLogJob----end"+ DateUtils.getDateTime());
    }
}
