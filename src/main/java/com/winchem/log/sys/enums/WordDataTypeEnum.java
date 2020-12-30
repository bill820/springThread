package com.winchem.log.sys.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @program: aftersale
 * @description: 枚举：是(1) or 否(0)
 * @author: zhanglb
 * @create: 2020-10-26 16:00
 */
public enum WordDataTypeEnum {
    /**
     * 数组
     */
    LIST("LIST", "数组", ""),
    OBJ("OBJ", "对象", ""),
    /**
     * 字符串
     */
    STRING("STRING", "字符串", "");

    @EnumValue
    @JsonValue
    private final String code;
    private final String value;
    private final String desc;

    WordDataTypeEnum(String code, String value, String desc) {
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
