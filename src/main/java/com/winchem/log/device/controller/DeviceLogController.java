package com.winchem.log.device.controller;


import com.winchem.log.device.entity.DeviceLog;
import com.winchem.log.device.reqvo.ReqDeviceLogVo;
import com.winchem.log.device.service.IDeviceLogService;
import com.winchem.log.sys.response.PageResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhanglb
 * @since 2020-12-11
 */
@RestController
@RequestMapping("/deviceWashQuantity")
public class DeviceLogController {

    @Autowired
    private IDeviceLogService deviceLogService;

    @ApiOperation("分页 设备日志")
    @PostMapping("/page")
    public PageResult<DeviceLog> page(@Validated @RequestBody ReqDeviceLogVo vo) {
        return deviceLogService.ListByPage(vo);
    }

    @ApiOperation("hel")
    @GetMapping("/hel")
    public String page() {
        return "hel test";
    }

}

