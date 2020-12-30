package com.winchem.log.device.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.winchem.log.device.entity.DeviceInfo;
import com.winchem.log.device.entity.DeviceLogLatest;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 设备信息 Mapper 接口
 * </p>
 *
 * @author zhanglb
 * @since 2020-12-10 10:33:50
 */
public interface DeviceInfoMapper extends BaseMapper<DeviceInfo> {

    String selectDeviceIdBySim(@Param("simCode") String simCode);
}
