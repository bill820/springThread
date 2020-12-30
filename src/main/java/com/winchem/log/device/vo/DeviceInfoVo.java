package com.winchem.log.device.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @program: winchem_log
 * @description:
 * @author: zhanglb
 * @create: 2020-12-18 11:21
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class DeviceInfoVo {

    /**
     * 设备id
     */
    private String deviceId;

    /**
     * sim卡
     */
    private String simCode;

    /**
     * 产品id
     */
    private String productId;

    /**
     * 系列id
     */
    private String seriesId;

    /**
     * 洗涤量
     */
    /**
     * 设备洗涤总量
     */
    private Integer washTotalQuantity;
}
