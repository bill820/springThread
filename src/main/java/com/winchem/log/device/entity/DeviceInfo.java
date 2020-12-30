package com.winchem.log.device.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: winchem_log
 * @description:
 * @author: zhanglb
 * @create: 2020-12-15 18:07
 */
@Data
@TableName("device_info")
public class DeviceInfo implements Serializable {

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * sim卡
     */
    private String simCode;

}
