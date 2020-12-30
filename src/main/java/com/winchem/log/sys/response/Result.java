package com.winchem.log.sys.response;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.winchem.log.sys.enums.HttpStatusEnum;
import com.winchem.log.sys.enums.RespEnum;
import lombok.Data;

/**
 * @program: winchem_afersale
 * @description: 统一返回值
 * @author: zhanglb
 * @create: 2020-11-02 18:17
 */
@Data
public class Result<T>{
    /**
     * 是否成功
     */
    private Boolean success;
    /**
     * 状态码
     */
    private String code;
    /**
     *  提示信息
     */
    private String msg;
    /**
     *  数据
     */
    private T data;

    public Result() {

    }

    public static Result OK() {
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(RespEnum.SUCCESS.getCode());
        result.setMsg(RespEnum.SUCCESS.getMsg());
        result.setData(null);
        return result;
    }

    public static Result successWithNoData(String msg) {
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(RespEnum.SUCCESS.getCode());
        result.setMsg(RespEnum.SUCCESS.getMsg());
        result.setData(null);
        return result;
    }


    public  static <T>  Result<T> success(T data) {
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(RespEnum.SUCCESS.getCode());
        result.setMsg(RespEnum.SUCCESS.getMsg());
        result.setData(data);
        return result;
    }

    public static <T> Result<T> page(IPage<?> page) {
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(RespEnum.SUCCESS.getCode());
        result.setMsg(RespEnum.SUCCESS.getMsg());
        result.setData(page.getRecords());
        return result;
    }

    public  static <T>  Result<T> ValidFailed(T data) {
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(String.valueOf(HttpStatusEnum.PRECONDITION_FAILED.getCode()));
        result.setMsg(HttpStatusEnum.PRECONDITION_FAILED.getReasonPhraseCN());
        result.setData(data);
        return result;
    }

    public static <T>  Result<T>  success(T data, String msg) {
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(RespEnum.SUCCESS.getCode());
        result.setMsg(msg);
        result.setData(data);
        return result;
    }


    /**
     * 自定义返回结果的构造方法
     * @param success
     * @param code
     * @param msg
     * @param data
     */
    public Result(Boolean success,String code, String msg,T data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 其他异常处理方法返回的结果
     * @param respEnum
     * @return
     */
    public static Result otherError(RespEnum respEnum){
        Result result = new Result();
        result.setMsg(respEnum.getMsg());
        result.setCode(respEnum.getCode());
        result.setSuccess(false);
        result.setData(null);
        return result;
    }


}
