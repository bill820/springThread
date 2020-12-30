package com.winchem.log.device.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.winchem.log.device.entity.DeviceWashQuantity;
import com.winchem.log.device.mapper.DeviceWashQuantityMapper;
import com.winchem.log.device.service.IDeviceWashQuantityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhanglb
 * @since 2020-12-11
 */
@Service("deviceWashQuantityService")
public class DeviceWashQuantityServiceImpl extends ServiceImpl<DeviceWashQuantityMapper, DeviceWashQuantity> implements IDeviceWashQuantityService {

    @Autowired
    private DeviceWashQuantityMapper quantityMapper;

    @Override
    public DeviceWashQuantity getLastLastest(String deviceId) {
        return quantityMapper.getLastLastest(deviceId);
    }
}
