package com.winchem.log.device.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.winchem.log.device.entity.DeviceLog;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * <p>
 * 设备日志 Mapper 接口
 * </p>
 *
 * @author zhanglb
 * @since 2020-12-10 10:33:50
 */
public interface DeviceLogMapper extends BaseMapper<DeviceLog> {

     /**
     *@Description: 根据分区查询设备日志
     *@Param:  * @param partition : 分区
     *@Param:  * @param queryWrapper : wrapper
     *@return:  * @return : List<DeviceLog>
     *@Author: zhanglb
     *@date: 2020/12/10 17:21
     */
    List<DeviceLog> listByPartition(@Param("partition") String partition, @Param(Constants.WRAPPER) Wrapper queryWrapper);
}
