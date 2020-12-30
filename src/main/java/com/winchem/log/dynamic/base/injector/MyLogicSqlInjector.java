package com.winchem.log.dynamic.base.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.injector.methods.SelectById;
import com.winchem.log.dynamic.base.methods.DeleteAll;
import com.winchem.log.dynamic.base.methods.MyInsertAll;
import com.winchem.log.dynamic.base.methods.MysqlInsertAllBatch;

import java.util.List;

/**
 * 自定义 SqlInjector
 * @author zhanglb
 * @since 2020-10-23
 */
public class MyLogicSqlInjector extends DefaultSqlInjector {

    /**
     * 如果只需增加方法，保留MP自带方法
     * 可以super.getMethodList() 再add
     * @return
     */
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        methodList.add(new DeleteAll());
        methodList.add(new MyInsertAll());
        methodList.add(new MysqlInsertAllBatch());
        methodList.add(new SelectById());
        return methodList;
    }
}
