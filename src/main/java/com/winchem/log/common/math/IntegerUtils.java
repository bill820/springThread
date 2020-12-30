package com.winchem.log.common.math;

import org.springframework.lang.Nullable;

import java.util.Random;

/**
 * @program: aftersale
 * @description: 整数操作utils
 * @author: zhanglb
 * @create: 2020-10-27 16:02
 */
public class IntegerUtils {

    /**
     * 判断是否为空
     * @param integ
     * @return
     */
    public static boolean isEmpty(@Nullable Object integ) {
        return integ == null || Integer.valueOf(0) == integ;
    }

    /**
     * 判断不为为空
     * @param integ
     * @return
     */
    public static boolean isNotEmpty(@Nullable Object integ) {
        return !isEmpty(integ);
    }

    /**
     * 随机数生成
     *  @param bound the upper bound (exclusive).  Must be positive.
     * @return
     */
    private Integer randomInt(Integer bound) {
        Random random = new Random(System.currentTimeMillis());
        return random.nextInt(bound);
    }
}
