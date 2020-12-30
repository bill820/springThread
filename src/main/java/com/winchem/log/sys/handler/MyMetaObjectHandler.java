package com.winchem.log.sys.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.winchem.log.sys.enums.YesNotEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
*@Description: 填充器
*@Param:  * @param null :
*@return:  * @return : null
*@Author: zhanglb
*@date: 2020/12/3 10:25
*/
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 字段填充的规则：
     * 1.如果对象手动进行设置了值，那么填充的值不会生效
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        //workorder填充是否补单字段，默认为0（非补单）
        this.strictInsertFill(metaObject, "isReplacementOrder", YesNotEnum.class, YesNotEnum.NOT);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
    }
}
