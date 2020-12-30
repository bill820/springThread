package com.winchem.log.device.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.winchem.log.common.math.IntegerUtils;
import com.winchem.log.common.utils.DateUtils;
import com.winchem.log.device.entity.DeviceLogLatest;
import com.winchem.log.device.entity.DeviceWashQuantity;
import com.winchem.log.device.entity.UseCountDish;
import com.winchem.log.device.mapper.DeviceInfoMapper;
import com.winchem.log.device.mapper.DeviceLogLatestMapper;
import com.winchem.log.device.service.IDeviceLatestLogService;
import com.winchem.log.device.service.IDeviceWashQuantityService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 设备最新最新日志 服务实现类
 * </p>
 *
 * @author zhanglb
 * @since 2020-12-10 10:33:50
 */
@Slf4j
@Service("deviceLatestLogService")
public class DeviceLatestLogServiceImpl extends ServiceImpl<DeviceLogLatestMapper, DeviceLogLatest> implements IDeviceLatestLogService {

    @Autowired
    private DeviceInfoMapper deviceInfoMapper;

    @Autowired
    private IDeviceWashQuantityService washQuantityService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void saveWashQuantity() {
        //1.获取所有的最新日志信息
        List<DeviceLogLatest> logLatests = this.list();
        //2.0 更新最新洗涤量日志 到 洗涤量表中
        logLatests.forEach(log -> {
            String deviceId = deviceInfoMapper.selectDeviceIdBySim(log.getUuid());
            DeviceWashQuantity washQuantity = washQuantityService.getById(deviceId);
            washQuantity.setDay(log.getUploadDate());
            washQuantity.setDayWashQuantity(log.getWashedQuantity());
            washQuantity.setWashTotalQuantity(washQuantity.getWashTotalQuantity()+log.getWashedQuantity());
            washQuantityService.update(washQuantity, null);
        });
    }

    /**
     * 先将日期转化成 yyyy-mm-dd格式, 再用日期分组count
     */
    @Override
    public void saveDayQuantity() {
        Date yesterday = DateUtils.minusDay(new Date(), 1);
        Date startDate = DateUtils.getOfDayFirst(yesterday);
        Date endDate = DateUtils.getOfDayLast(yesterday);
        log.info("DeviceLatestLogServiceImpl.communitySecurityStatis start startDate={}, endDate={}", DateUtils.getDateStr(startDate), DateUtils.getDateStr(endDate));

        Criteria criteria = Criteria.where("modifiedTime").gte(startDate).lte(endDate);
        //聚合查询转换格式时需要增加8小时 Aggregation.project().andExpression("{$dateToString:{format:'%Y-%m-%d',date: {$add:{'$createDate',8*60*60000}}}}").as("date"),
        Aggregation aggregation1 = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("uuid")
                            .first("uuid").as("uuid")
                            .max("useCount").as("useCount")
                            .max("modifiedTime").as("modifiedTime"));

        AggregationResults<UseCountDish> outputTypeCount1 = mongoTemplate.aggregate(aggregation1, "use_count_dish",
                UseCountDish.class);
        if (ObjectUtils.isNotEmpty(outputTypeCount1) || CollectionUtils.isNotEmpty(outputTypeCount1.getMappedResults())) {
            List<UseCountDish> lists = outputTypeCount1.getMappedResults();
            log.info("当天的洗涤量 size={}", lists.size());
            lists.forEach(lis -> {
                //根据UUID(sn_code),老的设备sim_code 和 uuid一样，新的设备都改成sn_code 获取deviceid
                String deviceId = deviceInfoMapper.selectDeviceIdBySim(lis.getUuid());
                if (StringUtils.isNotBlank(deviceId)) {
                    DeviceWashQuantity washQuantity = new DeviceWashQuantity();
                    washQuantity.setDeviceId(deviceId);
                    washQuantity.setDay(DateUtils.getDateYmd(lis.getModifiedTime()));
                    Integer useCount = Integer.parseInt(lis.getUseCount());
                    washQuantity.setWashTotalQuantity(useCount);
                    //日洗涤量计算 = 今日洗涤总量 - 上一次的洗涤总量(不一定是昨天，而是上一次)
                    DeviceWashQuantity lastWash = washQuantityService.getLastLastest(deviceId);
                    if (ObjectUtils.isNotEmpty(lastWash) && IntegerUtils.isNotEmpty(lastWash.getWashTotalQuantity())) {
                        washQuantity.setDayWashQuantity(useCount - lastWash.getWashTotalQuantity());
                    } else {
                        //如果之前没有日洗涤记录 那么日洗涤量为
                        washQuantity.setDayWashQuantity(useCount);
                    }
                    washQuantityService.save(washQuantity);
                }
            });
            log.info("DeviceLatestLogServiceImpl.communitySecurityStatis endTime={}", DateUtils.getDateStr(new Date()));

        }
    }
}
