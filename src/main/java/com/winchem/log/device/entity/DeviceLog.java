package com.winchem.log.device.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 设备管理日志(device_log_partition)实体类
 *
 * @author makejava
 * @since 2020-12-10 10:33:50
 */
@Data
@TableName("device_log_partition")
public class DeviceLog implements Serializable {

    private static final long serialVersionUID = -82918168419020090L;

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * uuid (sim卡号)
     */
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

    /**
     * 入库时间
     */
    private Date createDate;

}