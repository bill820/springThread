package com.winchem.log.db2.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.winchem.log.db2.entity.OrderInfo;
import org.apache.ibatis.annotations.Param;

@DS("db2")
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {
    /**
     * 自定义sql分页
     * @param page
     * @param queryWrapper 看这里看这里，如果自定义的方法中需要用到wrapper查询条件，需要这样写
     * @return
     */
    IPage<OrderInfo> selectMyPage(IPage<OrderInfo> page, @Param(Constants.WRAPPER) Wrapper<OrderInfo> queryWrapper);

}
