package com.winchem.log.device.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.winchem.log.device.entity.DeviceLogLatest;
/**
 * <p>
 * 设备最新日志 服务类
 * </p>
 *
 * @author zhanglb
 * @since 2020-12-10 10:33:50
 */
public interface IDeviceLatestLogService extends IService<DeviceLogLatest> {

    void saveWashQuantity();
    void saveDayQuantity();
}
