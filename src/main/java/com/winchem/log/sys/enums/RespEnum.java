package com.winchem.log.sys.enums;

/**
 * @program: aftersale
 * @description: 返回结果值枚举类
 * @author: zhanglb
 * @create: 2020-10-30 15:02
 */
public enum RespEnum {

    /**
     * 数据操作错误定义
     */
    SUCCESS("200", "请求成功"),
    NO_PERMISSION("403","未取得权限"),
    NO_AUTH("401","未登录"),
    NOT_FOUND("404", "未找到该资源!"),
    INTERNAL_SERVER_ERROR("500", "服务器跑路了");

    private final String msg;
    private final String code;

    RespEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public String getCode() {
        return code;
    }
}
