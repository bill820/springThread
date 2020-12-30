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
public class PageResult<T> {
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
     * 每页显示大小
     */
    private long  size;

    /**
     * 当前页码
     */
    private  long current;

    /**
     * 最大页数
     */
    private  long maxCurrent;

    /**
     * 数据总条数
     */
    private long total;
    /**
     * 总页数
     */
    private long totalPage;
    /**
     *  数据
     */
    private T data;

    public PageResult() {

    }

    public static PageResult OK() {
        PageResult result = new PageResult();
        result.setSuccess(true);
        result.setCode(RespEnum.SUCCESS.getCode());
        result.setMsg(RespEnum.SUCCESS.getMsg());
        result.setData(null);
        return result;
    }

    public static PageResult successWithNoData(String msg) {
        PageResult result = new PageResult();
        result.setSuccess(true);
        result.setCode(RespEnum.SUCCESS.getCode());
        result.setMsg(RespEnum.SUCCESS.getMsg());
        result.setData(null);
        return result;
    }


    public  static <T> PageResult<T> success(T data) {
        PageResult result = new PageResult();
        result.setSuccess(true);
        result.setCode(RespEnum.SUCCESS.getCode());
        result.setMsg(RespEnum.SUCCESS.getMsg());
        result.setData(data);
        return result;
    }

    public static <T> PageResult<T> page(IPage<?> page) {
        PageResult result = new PageResult();
        result.setSuccess(true);
        result.setCode(RespEnum.SUCCESS.getCode());
        result.setMsg(RespEnum.SUCCESS.getMsg());
        result.setTotal(page.getTotal());
        result.setSize(page.getSize());
        result.setCurrent(page.getCurrent());
        result.setTotalPage(page.getPages());
        result.setData(page.getRecords());
        return result;
    }

    public  static <T> PageResult<T> ValidFailed(T data) {
        PageResult result = new PageResult();
        result.setSuccess(true);
        result.setCode(String.valueOf(HttpStatusEnum.PRECONDITION_FAILED.getCode()));
        result.setMsg(HttpStatusEnum.PRECONDITION_FAILED.getReasonPhraseCN());
        result.setData(data);
        return result;
    }

    public static <T> PageResult<T> success(T data, String msg) {
        PageResult result = new PageResult();
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
    public PageResult(Boolean success, String code, String msg, T data) {
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
    public static PageResult otherError(RespEnum respEnum){
        PageResult result = new PageResult();
        result.setMsg(respEnum.getMsg());
        result.setCode(respEnum.getCode());
        result.setSuccess(false);
        result.setData(null);
        return result;
    }

    public void setTotal(long total) {
        if(size != 0){
            if(total % size != 0){
                maxCurrent = total / size + 1;
            }else {
                maxCurrent = total / size;
            }
        }
    }

}
