package com.winchem.log.device.vo;

import com.winchem.log.common.utils.DateUtils;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @program: spring_thread
 * @description:
 * @author: zhanglb
 * @create: 2021-01-04 10:54
 */
@Data
public class DeviceLastestInfoVo {
    private String errno;
    private Data data;

    static class Data {
        private List<LastestInfo> devices;
    }

    static class LastestInfo {
        private String title;
        private String id;
        private List<DateStream> datastreams;
    }

    static class DateStream {
        private String at;
        private String id;
        private String value;
    }

}
