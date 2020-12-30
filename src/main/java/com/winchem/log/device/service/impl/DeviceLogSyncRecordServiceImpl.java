package com.winchem.log.device.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.winchem.log.device.entity.DeviceLogLatest;
import com.winchem.log.device.entity.DeviceLogSyncRecord;
import com.winchem.log.device.entity.DeviceWashQuantity;
import com.winchem.log.device.mapper.DeviceInfoMapper;
import com.winchem.log.device.mapper.DeviceLogLatestMapper;
import com.winchem.log.device.mapper.DeviceLogSyncRecordMapper;
import com.winchem.log.device.service.IDeviceLatestLogService;
import com.winchem.log.device.service.IDeviceLogSyncRecordService;
import com.winchem.log.device.service.IDeviceWashQuantityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 设备最新最新日志 服务实现类
 * </p>
 *
 * @author zhanglb
 * @since 2020-12-10 10:33:50
 */
@Service("deviceLogSyncRecordService")
public class DeviceLogSyncRecordServiceImpl extends ServiceImpl<DeviceLogSyncRecordMapper, DeviceLogSyncRecord> implements IDeviceLogSyncRecordService {

}
