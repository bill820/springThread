package com.winchem.log.device.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @program: winchem_log
 * @description:
 * @author: zhanglb
 * @create: 2020-12-14 17:21
 */
@Data
@Accessors(chain = true)
@Document(collection = "use_count_dish")
public class UseCountDish {

    @Id
    private String _id;

    private String serialNumber;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date timeStamp;

    private String uuid;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date modifiedTime;

    private String useCount;
}
