package com.winchem.log.device.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.winchem.log.common.BaseConst;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: winchem_log
 * @description:
 * @author: zhanglb
 * @create: 2020-12-15 18:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("device_log_sync_record")
public class DeviceLogSyncRecord implements Serializable {

    @TableId(type = IdType.AUTO)
    private int id;
    /**
     * 数据获取开始时间
     */
    @JSONField(format = BaseConst.DATE_FORMAT)
    private Date startDate;

    /**
     * 数据获取结束时间
     */
    @JSONField(format = BaseConst.DATE_FORMAT)
    private Date endDate;

    /**
     * 创建时间
     */
    @JSONField(format = BaseConst.DATE_FORMAT)
    private Date createDate;

    /**
     * 获取数据总条数
     */
    private Integer totalCount;

    public DeviceLogSyncRecord(Date startDate, Date endDate, Date createDate, Integer totalCount) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.createDate = createDate;
        this.totalCount = totalCount;
    }
}
