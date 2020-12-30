package com.winchem.log.device.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.winchem.log.device.entity.DeviceWashQuantity;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhanglb
 * @since 2020-12-11
 */
public interface IDeviceWashQuantityService extends IService<DeviceWashQuantity> {

    DeviceWashQuantity getLastLastest(String deviceId);
}
