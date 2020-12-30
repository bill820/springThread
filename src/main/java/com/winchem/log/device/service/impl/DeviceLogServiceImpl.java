package com.winchem.log.device.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.winchem.log.common.utils.DateUtils;
import com.winchem.log.device.configparam.ConfigParam;
import com.winchem.log.device.dao.MgUseCountDishDao;
import com.winchem.log.device.entity.DeviceLog;
import com.winchem.log.device.entity.DeviceLogLatest;
import com.winchem.log.device.entity.DeviceLogSyncRecord;
import com.winchem.log.device.entity.UseCountDish;
import com.winchem.log.device.mapper.DeviceLogMapper;
import com.winchem.log.device.reqvo.ReqDeviceLogVo;
import com.winchem.log.device.service.IDeviceLatestLogService;
import com.winchem.log.device.service.IDeviceLogService;
import com.winchem.log.device.service.IDeviceLogSyncRecordService;
import com.winchem.log.device.utils.DeviceLogPartitionUtils;
import com.winchem.log.sys.response.PageResult;
import com.winchem.log.sys.task.entity.TaskCronJob;
import com.winchem.log.sys.task.service.ITaskCronJobService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * <p>
 * 设备日志 服务实现类
 * </p>
 *
 * @author zhanglb
 * @since 2020-12-10 10:33:50
 */
@Slf4j
@Service("deviceLogService")
public class DeviceLogServiceImpl extends ServiceImpl<DeviceLogMapper, DeviceLog> implements IDeviceLogService {

    @Autowired
    private DeviceLogMapper deviceLogMapper;

    @Autowired
    private ITaskCronJobService cronJobService;

    @Autowired
    private ConfigParam configParam;

    @Autowired
    private MgUseCountDishDao countDishDao;

    @Autowired
    private IDeviceLatestLogService latestLogService;

    @Autowired
    private IDeviceLogSyncRecordService syncRecordService;

    @Autowired
    private ITaskCronJobService taskCronJobService;

    @Override
    public void addLogData() {
        //1.舒适化 一个月的日志数据进去
        //2.十一月份开始，每十五分钟生成一条数据
        Date november = DateUtils.getDate("2020-11-01 01:00:00", "yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public List<DeviceLog> listByDate(Date startDate, Date endDate) {
        String partition = DeviceLogPartitionUtils.PartitionByDate(startDate);
        LambdaQueryWrapper<DeviceLog> wrapper = new LambdaQueryWrapper<DeviceLog>();
        wrapper.ge(DeviceLog::getUploadDate, DateUtils.getDateStr(startDate))
                .le(DeviceLog::getUploadDate, DateUtils.getDateStr(endDate));
        log.info("根据日期获取日志数据所在分区：listByDate.partition= {}", partition);
        return deviceLogMapper.listByPartition(partition, wrapper);
    }

    @Override
    public PageResult<DeviceLog> ListByPage(ReqDeviceLogVo vo) {
        Page<DeviceLog> pagePram = new Page<>(vo.getCurrent(), vo.getSize());
        QueryWrapper wrapper = new QueryWrapper();
        IPage<DeviceLog> pages = this.page(pagePram, wrapper);
        return PageResult.page(pages);
    }

    private Integer randomInt() {
        Random random = new Random(System.currentTimeMillis());
        return random.nextInt();
    }

    @Override
    public void saveDeviceLogFromMongodb(String id) {
        //1.0 查询定时任务上次周期结束时间，本次任务从上次周期开始
        TaskCronJob job = taskCronJobService.getById(id);
        Date startDate = ObjectUtils.isNotEmpty(job.getIntervalTime()) ? job.getIntervalTime() : DateUtils.getDateNow();
        Date endDate = DateUtils.addMinute(startDate, configParam.getInterval());
        //更新本次数据同步区间的 结束时间，作为下次同步的开始时间
        cronJobService.updateIntervalTime(id, DateUtils.getDateStr(endDate));
        log.info("同步mongodb数据到本地starth： start={}, end={}", new Object[]{DateUtils.getDateStr(startDate), DateUtils.getDateStr(endDate)});
        //2.0 获取mongodb中获取数据， 从上次定时任务的执行时间算起  推后五分钟数据
        List<UseCountDish> countDishes = countDishDao.getCollectionByDate(startDate, endDate);
        Date now = DateUtils.getDateNow();
        countDishes.forEach(dish -> {
            //1.保存洗涤日志
            DeviceLog deviceLog  = new DeviceLog();
            deviceLog.setUuid(dish.getUuid());
            deviceLog.setUploadDate(dish.getModifiedTime());
            deviceLog.setWashedQuantity(Integer.parseInt(dish.getUseCount()));
            deviceLog.setCreateDate(now);
            this.save(deviceLog);
        });
        //保存同步数据的记录
        syncRecordService.save(new DeviceLogSyncRecord(startDate, endDate, DateUtils.getDateNow(), countDishes.size()));
        log.info("同步mongodb数据到本地end：endtime={}", new Date());
    }
    @Override
    public void saveDeviceLogFromMongodb(Date startDate, Date endDate) {
        /*MongoCursor<Document> dto3list= countDishDao.findDTO3(startDate, endDate);
        if(dto3list.hasNext()) {
            DeviceLog deviceLog = null;
            while(dto3list.hasNext()) {
                deviceLog = new DeviceLog();
                Document doc = dto3list.next();
                deviceLog.setUuid(doc.getString("uuid"));
                deviceLog.setUploadDate(DateUtils.getDateWithStr(doc.getString("modifiedTime")));
                deviceLog.setWashedQuantity(doc.getInteger("useCount"));
                this.save(deviceLog);
            }
        }*/
    }
}
