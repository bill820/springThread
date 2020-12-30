package com.winchem.log.dynamic.enums;

/**
 * @program: aftersale
 * @description: 工单编号前缀枚举
 * @author: zhanglb
 * @create: 2020-10-28 14:21
 */
public enum OrderCodePrefixEnum {
    /**
     * 安装工单
     */
    INSTALL("AZ", "INSTALL", ""),
    TEARDOWN("CJ", "TEARDOWN", ""),
    CHECK("XJ", "CHECK", ""),
    URGENT_CHECK("JX", "URGENT_CHECK", "");

    private final String code;
    private final String value;
    private final String desc;

    OrderCodePrefixEnum(String code, String value, String desc) {
        this.code = code;
        this.value = value;
        this.desc = desc;
    }

    public static String getCode(String value) {
        for (OrderCodePrefixEnum ele : values()) {
            if (ele.getValue().equals(value)) {
                return ele.getCode();
            }
        }
        return null;
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
