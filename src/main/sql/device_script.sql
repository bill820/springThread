/*
 Navicat Premium Data Transfer

 Source Server         : BIngo
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : winchem_test

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 22/12/2020 17:13:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for device_log
-- ----------------------------
CREATE TABLE `device_log`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '设备ID',
  `STATUS` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '状态',
  `UPLOAD_DATE` datetime NOT NULL COMMENT '设备数据上传时间',
  `WASHED_QUANTITY` int(0) NOT NULL DEFAULT 0 COMMENT '当前洗涤量',
  `create_date` datetime NOT NULL COMMENT '数据入库时间',
  PRIMARY KEY (`id`, `UPLOAD_DATE`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 145376 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '设备管理日志';

-- ----------------------------
-- Table structure for device_log_latest
-- ----------------------------
CREATE TABLE `device_log_latest`  (
  `uuid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `status` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `UPLOAD_DATE` datetime DEFAULT NULL,
  `WASHED_QUANTITY` int(0) DEFAULT NULL,
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Table structure for device_log_sync_record
-- ----------------------------
CREATE TABLE `device_log_sync_record`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `start_date` datetime DEFAULT NULL COMMENT '数据获取开始时间',
  `end_date` datetime DEFAULT NULL COMMENT '数据获取结束时间',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `total_count` int(0) DEFAULT NULL COMMENT '获取数据条数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 125 CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Table structure for device_wash_quantity
-- ----------------------------
CREATE TABLE `device_wash_quantity`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `device_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `day` date DEFAULT NULL,
  `wash_total_quantity` int(0) DEFAULT NULL,
  `day_wash_quantity` int(0) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci;

-- ----------------------------
-- Table structure for task_cron_job
-- ----------------------------
CREATE TABLE `task_cron_job`  (
  `id` bigint(0) NOT NULL COMMENT 'id',
  `cron` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'ron表达式',
  `job_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'Job名称',
  `job_class_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'Job相关的类全名',
  `job_description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Job描述',
  `job_number` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'Job编号',
  `enabled` tinyint(1) DEFAULT NULL COMMENT 'Job是否启用',
  `interval_time` datetime DEFAULT NULL COMMENT '周期时间',
  `execute_time` datetime DEFAULT NULL COMMENT '执行时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `comment` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'cron定时任务表';

-- ----------------------------
-- Records of task_cron_job
-- ----------------------------
BEGIN;
INSERT INTO `task_cron_job` VALUES (1, '0 0/5 * * * ? ', 'log_job', 'com.winchem.log.device.job.DeviceLogJob', '设备日志同步任务', '1', 0, '2020-12-20 02:48:16', '2020-12-22 11:10:00', '2020-12-22 11:10:04', '五分钟同步一次洗涤日志'), (2, '0 0 2 * * ? ', 'latest_job', 'com.winchem.log.device.job.DeviceLatestLogJob', '设备最新洗涤量同步', '2', 1, '2020-12-19 09:38:16', '2020-12-22 12:10:00', '2020-12-22 12:10:07', '每日两点钟同步昨天的日洗涤量和洗涤总量');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
