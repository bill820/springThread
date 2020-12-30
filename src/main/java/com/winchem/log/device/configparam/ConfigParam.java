package com.winchem.log.device.configparam;

import com.winchem.log.dynamic.config.YamlPropertySourceFactory;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @program: log
 * @description: 日志参数
 * @author: zhanglb
 * @create: 2020-10-27 10:10
 */
@Component
@PropertySource(value = "classpath:config/application-configparam.yml", encoding = "utf-8", factory = YamlPropertySourceFactory.class)
@Data
public class ConfigParam implements Serializable {

    /**
     * 日志同步周期
     */
    @Value("${deviceLog.interval}")
    private Integer interval;

}
