package com.winchem.log.sys.task.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.winchem.log.sys.task.entity.TaskCronJob;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 设备分组表	分组：分组不看客户，只看设备	1.同客户多设备	2.外地客户同组 Mapper 接口
 * </p>
 *
 * @author zhanglb
 * @since 2020-10-21
 */
public interface TaskCronJobMapper extends BaseMapper<TaskCronJob> {

    void updateIntervalTime(@Param("taskId") String taskId, @Param("intervalTime") String intervalTime);
}
