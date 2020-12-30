package com.winchem.log.device.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 设备最新日志(DeviceLog)实体类
 *
 * @author makejava
 * @since 2020-12-10 10:33:50
 */
@Data
@TableName("device_log_latest")
public class DeviceLogLatest implements Serializable {

    /**
     * uuid (sim卡号)
     */
    @TableId(type = IdType.INPUT)
    private String uuid;
    /**
     * 状态
     */
    private String status;
    /**
     * 上传时间
     */
    private Date uploadDate;
    /**
     * 当前洗涤量
     */
    private Integer washedQuantity;

}