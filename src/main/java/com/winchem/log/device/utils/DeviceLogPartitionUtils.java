package com.winchem.log.device.utils;

import com.google.common.collect.Lists;
import com.winchem.log.common.utils.DateUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * @program: winchem_log
 * @description: 根据日期计算数据所在分区
 * @author: zhanglb
 * @create: 2020-12-10 17:00
 */
public class DeviceLogPartitionUtils {

    private static final String PARTITION_PREFIX = "P";

    public static String PartitionByDate(Date date) {
        return DeviceLogPartitionUtils.PARTITION_PREFIX + new Double(Math.floor(DateUtils.getDayOfYear(date))).intValue();
    }

    public static String PartitionByDate(Date ...date) {
        List<String> list = Lists.newArrayList();
        for (Date d : date) {
            list.add(DeviceLogPartitionUtils.PARTITION_PREFIX + new Double(Math.floor(DateUtils.getDayOfYear(d))).intValue());
        }
        return StringUtils.join(list, ",");
    }

    public static void main(String[] args) {
        System.out.println(        DeviceLogPartitionUtils.PartitionByDate(new Date())
);
    }
}
