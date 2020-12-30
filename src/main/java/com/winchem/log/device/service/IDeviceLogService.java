package com.winchem.log.device.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.winchem.log.device.entity.DeviceLog;
import com.winchem.log.device.reqvo.ReqDeviceLogVo;
import com.winchem.log.sys.response.PageResult;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 设备日志 服务类
 * </p>
 *
 * @author zhanglb
 * @since 2020-12-10 10:33:50
 */
public interface IDeviceLogService extends IService<DeviceLog> {

    void addLogData();

    List<DeviceLog> listByDate(Date startDate, Date endDate);

    PageResult<DeviceLog> ListByPage(ReqDeviceLogVo vo);

    void saveDeviceLogFromMongodb(String id);

    public void saveDeviceLogFromMongodb(Date startDate, Date endDate);
}
