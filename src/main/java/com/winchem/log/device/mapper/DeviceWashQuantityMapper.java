package com.winchem.log.device.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.winchem.log.device.entity.DeviceWashQuantity;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhanglb
 * @since 2020-12-11
 */
public interface DeviceWashQuantityMapper extends BaseMapper<DeviceWashQuantity> {

    DeviceWashQuantity getLastLastest(@Param("deviceId") String deviceId);
}
