package com.winchem.log.sys.task.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.converters.DateConverter;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @program: winchem_log
 * @description:
 * @author: zhanglb
 * @create: 2020-12-14 15:26
 */
public class MyBeanUtils extends BeanUtils {

    private MyBeanUtils() {
    }

    static {
        // 注册sql.date的转换器，即允许BeanUtils.copyProperties时的源目标的sql类型的值允许为空
        ConvertUtils.register(new org.apache.commons.beanutils.converters.SqlDateConverter(null), java.sql.Date.class);
        ConvertUtils.register(new org.apache.commons.beanutils.converters.SqlDateConverter(null), java.util.Date.class);
        ConvertUtils.register(new org.apache.commons.beanutils.converters.SqlTimestampConverter(null),
                java.sql.Timestamp.class);
        DateConverter dateConverter = new DateConverter();
        dateConverter.setPattern("yyyy-MM-dd HH:mm:ss");
        ConvertUtils.register(dateConverter, String.class);
        // 注册util.date的转换器，即允许BeanUtils.copyProperties时的源目标的util类型的值允许为空
    }

    public static void copyProperties(Object target, Object source) throws InvocationTargetException,
            IllegalAccessException {
        // 支持对日期copy
        org.apache.commons.beanutils.BeanUtils.copyProperties(target, source);
    }

    public static Map<String, String> describe(Object bean) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return BeanUtilsBean.getInstance().describe(bean);
    }

    public static void populate(Object bean, Map<String, ? extends Object> properties) throws IllegalAccessException, InvocationTargetException {
        BeanUtilsBean.getInstance().populate(bean, properties);
    }
}
