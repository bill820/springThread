package com.winchem.log.sys.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @program: aftersale
 * @description: 枚举：是(1) or 否(0)
 * @author: zhanglb
 * @create: 2020-10-26 16:00
 */
public enum YesNotEnum {
    /**
     * 是、存在、肯定
     */
    YES("1", "是", "表示肯定、存在"),
    /**
     * 否、不存在、否定
     */
    NOT("0", "否", "表示否定、不存在");

    @EnumValue
    @JsonValue
    private final String code;
    private final String value;
    private final String desc;

    YesNotEnum(String code, String value, String desc) {
        this.code = code;
        this.value = value;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
