package com.winchem.log.device.dao;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.winchem.log.common.utils.DateUtils;
import com.winchem.log.device.entity.DeviceLog;
import com.winchem.log.device.entity.DeviceLogLatest;
import com.winchem.log.device.entity.UseCountDish;
import com.winchem.log.device.service.IDeviceLatestLogService;
import com.winchem.log.device.service.IDeviceLogService;
import com.winchem.log.sys.mongodb.dao.MongoDbBaseDao;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @program: winchem_afersale
 * @description: 设备日志dao
 * @author: zhanglb
 * @create: 2020-12-11 10:22
 */
@Repository
public class MgUseCountDishDao extends MongoDbBaseDao<UseCountDish> {

    @Override
    protected Class<UseCountDish> getEntityClass() {
        return UseCountDish.class;
    }

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private IDeviceLatestLogService latestLogService;

    public void batchData(IDeviceLogService logService) {

    }

    public List<UseCountDish> getCollectionByDate(Date startDate, Date endDate ) {
        // 执行获取集合名称列表
        Query query = new Query(Criteria.where("modifiedTime").gte(startDate).lt(endDate));
        List<UseCountDish> countDishes = mongoTemplate.find(query, UseCountDish.class);
        return countDishes;
    }

    public void findDTO3(Date startDate, Date endDate, IDeviceLogService deviceLogService) {
        MongoCollection<Document> coll = mongoTemplate.getCollection("use_count_dish");
        Document document = new Document();
        document.append("modifiedTime", new Document("$gte", startDate).append("$lt", endDate));
        MongoCursor<Document> mongoCursor = null;
        try {
            FindIterable<Document> findIterable = coll.find(document).batchSize(500);
            mongoCursor = findIterable.iterator();
            List<DeviceLog> deviceLogs = Lists.newArrayList();
            while (mongoCursor.hasNext()){
                //1.保存洗涤日志
                DeviceLog deviceLog = new DeviceLog();
                Document doc = mongoCursor.next();
                deviceLog.setUuid(doc.getString("uuid"));
                deviceLog.setUploadDate(doc.getDate("modifiedTime"));
                deviceLog.setWashedQuantity(Integer.parseInt(String.valueOf(doc.getLong("useCount"))));
                deviceLogs.add(deviceLog);

                //2.每次同步到 更新最新的洗涤数据
                DeviceLogLatest logLatest = new DeviceLogLatest();
                BeanUtils.copyProperties(deviceLog, logLatest);
                latestLogService.saveOrUpdate(logLatest);
            }
            deviceLogService.saveBatch(deviceLogs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(mongoCursor!=null) {
                mongoCursor.close();
            }
        }

    }
}
