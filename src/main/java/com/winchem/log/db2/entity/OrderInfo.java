package com.winchem.log.db2.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单管理(OrderInfo)实体类
 *
 * @author makejava
 * @since 2020-10-23 13:49:20
 */
@Data
@TableName("order_info")
public class OrderInfo implements Serializable {
    private static final long serialVersionUID = 718835317611933953L;
    /**
     * 订单ID
     */
    @TableId
    private Integer orderId;
    /**
     * 订单编号
     */
    private String orderCode;
    /**
     * 客户ID
     */
    private Integer customerId;
    /**
     * 地址区域ID
     */
    private Integer regionId;
    /**
     * SAP编号
     */
    private String sapCode;
    /**
     * 所属分公司
     */
    private Integer groupId;
    /**
     * 订单状态
     */
    private String orderStatus;
    /**
     * 订单来源
     */
    private String orderSource;
    /**
     * 创建时间
     */
    private Date createdDate;
    /**
     * 详细地址
     */
    private String address;
    /**
     * 分派工程师
     */
    private Integer engineer;
    /**
     * 档次信息
     */
    private Integer levelInfo;
    /**
     * 合同时长
     */
    private Double contractTime;
    /**
     * 租赁类型
     */
    private String serviceType;
    /**
     * 每月固定收费
     */
    private Double monthFixedPrice;
    /**
     * 合同签订时间
     */
    private Date orderDate;
    /**
     * 统一/单独 收费  u统一 s单独
     */
    private String payType;

    private String payTypeAbcdef;

    private Double monthfixedpriceU;

    private Double baseamountU;

    private Double basepriceU;

    private Double theoryamountU;

    private Double theorypriceU;

    private Double supertheorypriceU;

    private Double uplimitpriceU;
    /**
     * 用来展示的档次信息
     */
    private Integer levelInfoShow;
    /**
     * 实际起租时间
     */
    private Date actdate;
    /**
     * 物流单生成间隔时间
     */
    private Integer logisticstime;
    /**
     * sap项目任务号
     */
    private String sapproject;
    /**
     * 物流单生成类型
     */
    private String logistictype;
    /**
     * sap公司编号
     */
    private String sapCompanyCode;
    /**
     * 业务类型
     */
    private String bizType;
    /**
     * 修改时间
     */
    private Date modifiedDate;
    /**
     * 修改人
     */
    private Integer modifier;
    /**
     * 创建人
     */
    private Integer creator;
    /**
     * 结算日期
     */
    private Date dueDate;
    /**
     * 最后一次结算日期（yyyyMMdd）
     */
    private String lastPayDateStr;
    /**
     * SAP同步的业务类型
     */
    private String ywlx;
    /**
     * 成本价
     */
    private Double costPrice;

}