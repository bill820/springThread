package com.winchem.log.sys.task.entity;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.winchem.log.sys.task.utils.MyBeanUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

/***
 * @program: aftersale
 * @description: cron任务
 * @author: zhanglb
 * @create: 2020-10-30 15:51
 */
@Data
@Accessors(chain = true)
@TableName("task_cron_job")
public class TaskCronJob implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    /**ron表达式*/
    private String cron;

    /**Job名称*/
    private String jobName;

    /**Job相关的类全名*/
    private String jobClassName;

   /** Job描述*/
    private String jobDescription;

    /**Job编号*/
    private Integer jobNumber;

    /**Job是否启用*/
    private Boolean enabled;

    /**
     * 周期时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date intervalTime;

    /**
     * 执行时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date executeTime;

    /**
     * 结束时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /**
     * 备注
     */
    private String comment;
}
