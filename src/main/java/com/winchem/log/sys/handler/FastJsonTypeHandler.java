package com.winchem.log.sys.handler;

import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.util.List;

/**
 * @program: winchem_afersale
 * @description: json字段映射处理器，mybatis-plus自带json处理器无法使用，必须使用这个处理器
 *              这个处理器本质，也是继承了mybatis-plus的内置处理器FastjsonTypeHandler
 * @author: zhanglb
 * @create: 2020-11-24 14:00
 */
@MappedTypes({List.class})
@MappedJdbcTypes(JdbcType.VARCHAR)
public class FastJsonTypeHandler extends FastjsonTypeHandler {
    public FastJsonTypeHandler(Class<?> type) {
        super(type);
    }
}
