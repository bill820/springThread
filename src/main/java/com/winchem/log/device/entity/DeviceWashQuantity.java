package com.winchem.log.device.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhanglb
 * @since 2020-12-11
 */
@Data
@TableName("device_wash_quantity")
public class DeviceWashQuantity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String deviceId;

    private Date day;

    private Integer washTotalQuantity;

    private Integer dayWashQuantity;


}
