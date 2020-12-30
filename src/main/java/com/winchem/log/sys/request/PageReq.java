package com.winchem.log.sys.request;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @program: winchem_afersale
 * @description: 分页请求参数
 * @author: zhanglb
 * @create: 2020-11-16 14:58
 */
public class PageReq implements Serializable {

    /**
     * 每页显示大小
     */
    @ApiModelProperty(name = "size", value = "每页显示大小", required = true, dataType = "long")
    private long  size;

    /**
     * 当前页码
     */
    @ApiModelProperty(name = "current", value = "当前页码", required = true, dataType = "long")
    private  long current;

    /**
     * 最大页数
     */
    @ApiModelProperty(hidden = true)
    private  long maxCurrent;

    /**
     * 数据总条数
     */
    @ApiModelProperty(hidden = true)
    private long total;

    /**
     * 总页数
     */
    @ApiModelProperty(hidden = true)
    private long totalPage;

    public long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getCurrent() {
        return current;
    }

    public void setCurrent(long current) {
        this.current = current;
    }

    public long getMaxCurrent() {
        return maxCurrent;
    }

    public void setMaxCurrent(long maxCurrent) {
        this.maxCurrent = maxCurrent;
    }

    public long getTotal() {
        return total;
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

    public PageReq() {

    }

    public PageReq(long size, long current, long total) {
        this.size = size;
        this.current = current;
        this.total = total;
        setTotal(total);
    }
}