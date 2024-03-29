/*
 Navicat Premium Data Transfer

 Source Server         : 本地mysql
 Source Server Type    : MySQL
 Source Server Version : 80013
 Source Host           : localhost:3306
 Source Schema         : oasis

 Target Server Type    : MySQL
 Target Server Version : 80013
 File Encoding         : 65001

 Date: 28/03/2020 21:53:09
*/
DROP DATABASE IF EXISTS iot;
CREATE DATABASE iot;
USE iot;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for data_source
-- ----------------------------
DROP TABLE IF EXISTS `data_source`;
CREATE TABLE `data_source`  (
  `did` int(11) NOT NULL AUTO_INCREMENT COMMENT '数据源id',
  `pid` int(11) NOT NULL COMMENT '产品id',
  `storage_cycle` int(11) NOT NULL DEFAULT 0 COMMENT '存储周期，按月存储',
  `start_time` timestamp NOT NULL COMMENT '创建时间',
  `status` int(3) NOT NULL DEFAULT 0 COMMENT '状态，默认0是存储中，1是已结束，2是已停止',
  PRIMARY KEY (`did`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic ;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `data_source` VALUES (1,1,3,'2020-06-18 10:04:21',0);
INSERT INTO `data_source` VALUES (2,2,6,'2020-06-18 10:05:21',0);
INSERT INTO `data_source` VALUES (3,3,12,'2020-06-18 10:06:21',0);

-- ----------------------------
-- Table structure for equipment
-- ----------------------------
DROP TABLE IF EXISTS `equipment`;
CREATE TABLE `equipment`  (
  `eid` int(11) NOT NULL AUTO_INCREMENT COMMENT '设备id',
  `pid` int(11) NOT NULL COMMENT '产品id',
  `ename` varchar(225) NOT NULL COMMENT '设备名称',
  `remark_name` varchar(225) COMMENT '备注名称',
  `status` int(3) NOT NULL DEFAULT 0 COMMENT '状态，默认0是未激活，1是已激活，2是已禁用',
  `last_online` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp() COMMENT '最后上线时间',
  PRIMARY KEY (`eid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic ;

-- ----------------------------
-- Records of equipment
-- ----------------------------
INSERT INTO `equipment` (`pid`,`ename`,`remark_name`,`status`) VALUES (1, 'shebei1', '', 1);
INSERT INTO `equipment` (`pid`,`ename`,`remark_name`,`status`) VALUES (2, 'shebei2', '', 1);
INSERT INTO `equipment` (`pid`,`ename`,`remark_name`,`status`) VALUES (3, 'shebei3', '', 1);
INSERT INTO `equipment` (`pid`,`ename`,`remark_name`,`status`) VALUES (4, 'shebei4', '', 1);
INSERT INTO `equipment` (`pid`,`ename`,`remark_name`,`status`) VALUES (5, 'shebei5', '', 1);


-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `pid` int(11) NOT NULL AUTO_INCREMENT COMMENT '产品/模型id',
  `pname` varchar(225) NOT NULL COMMENT '产品名称',
  `node_type` int(3) NOT NULL DEFAULT 0 COMMENT '节点类型，默认0是直连设备，1是网关子设备，2是网关设备',
  `connect_method` int(3) NOT NULL DEFAULT 0 COMMENT '连网方式，默认0是Wi-Fi，1是蜂窝(2G/3G/4G/5G)，2是以太网，3是LoRaWAN，4是其他',
  `data_format` int(3) NOT NULL DEFAULT 0 COMMENT '数据格式，默认0是ICA标准数据格式，1是透传/自定义',
  `verification_method` int(3) NOT NULL DEFAULT 0 COMMENT '认证方式，默认0是设备秘钥，1是ID²，2是X.509证书',
  `product_description` varchar(225) COMMENT '产品描述',
  PRIMARY KEY (`pid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic ;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES (1,'chanpin1',0,0,0,0,'wu');
INSERT INTO `product` VALUES (2,'chanpin2',0,0,0,0,'wu');
INSERT INTO `product` VALUES (3,'chanpin3',0,0,0,0,'wu');
INSERT INTO `product` VALUES (4,'chanpin4',0,0,0,0,'wu');


-- ----------------------------
-- Table structure for attribute
-- ----------------------------
DROP TABLE IF EXISTS `attribute`;
CREATE TABLE `attribute`  (
  `aid` int(11) NOT NULL AUTO_INCREMENT COMMENT '属性id',
  `func_type` int(3) NOT NULL DEFAULT 0 COMMENT '功能类型，默认0是属性，1是事件，2是服务',
  `aname` varchar(225) NOT NULL COMMENT '属性名称',
  `identifier` varchar(225) NOT NULL COMMENT '标识符',
  `data_type` varchar(225) NOT NULL COMMENT '数据类型',
  `range_type` int(3) NOT NULL DEFAULT 0 COMMENT '数据定义类型，默认0是范围，1是长度',
  `data_len` int(11) NOT NULL DEFAULT 0 COMMENT '数据长度',
  `lower_limit` int(11) NOT NULL DEFAULT 0 COMMENT '范围下限',
  `higher_limit` int(11) NOT NULL DEFAULT 0 COMMENT '范围上限',
  PRIMARY KEY (`aid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of attribute
-- ----------------------------
INSERT INTO `attribute` VALUES (1,0,'length','len','integer',1,'100',0,0);
INSERT INTO `attribute` VALUES (2,0,'size','size','double',0,0,1,200);
INSERT INTO `attribute` VALUES (3,0,'color','col','text',1,'2',0,0);

-- ----------------------------
-- Table structure for TSL
-- ----------------------------
DROP TABLE IF EXISTS `TSL`;
CREATE TABLE `TSL`  (
  `pid` int(11) NOT NULL COMMENT '产品/模型id',
  `aid` int(11) NOT NULL COMMENT '属性id',
  PRIMARY KEY (`pid`, `aid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of TSL
-- ----------------------------
INSERT INTO `TSL` VALUES (1,1);
INSERT INTO `TSL` VALUES (1,2);
INSERT INTO `TSL` VALUES (2,2);
INSERT INTO `TSL` VALUES (2,3);
INSERT INTO `TSL` VALUES (3,3);
INSERT INTO `TSL` VALUES (3,1);
INSERT INTO `TSL` VALUES (4,1);

-- ----------------------------
-- Table structure for datas
-- ----------------------------
DROP TABLE IF EXISTS `datas`;
CREATE TABLE `datas`  (
  `did` int(11) NOT NULL COMMENT '数据源id',
  `aid` int(11) NOT NULL COMMENT '属性id',
  `arr_value` varchar(225) NOT NULL COMMENT '属性值',
  `data_time` timestamp NOT NULL COMMENT '数据产生时间'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of datas
-- ----------------------------
INSERT INTO `datas` VALUES (2,2,'10','2020-05-18 10:04:21');
INSERT INTO `datas` VALUES (2,3,'0','2020-05-18 10:04:21');
INSERT INTO `datas` VALUES (2,2,'30','2020-05-18 20:04:21');
INSERT INTO `datas` VALUES (2,3,'1','2020-05-18 20:04:21');
INSERT INTO `datas` VALUES (2,2,'100','2020-05-19 10:04:21');
INSERT INTO `datas` VALUES (2,3,'2','2020-05-19 10:04:21');
INSERT INTO `datas` VALUES (2,2,'130','2020-05-19 20:04:21');
INSERT INTO `datas` VALUES (2,3,'0.5','2020-05-19 20:04:21');
INSERT INTO `datas` VALUES (2,2,'150','2020-05-20 10:04:21');
INSERT INTO `datas` VALUES (2,3,'1','2020-05-20 10:04:21');
INSERT INTO `datas` VALUES (2,2,'30','2020-05-10 20:04:21');
INSERT INTO `datas` VALUES (2,3,'0','2020-05-10 20:04:21');

-- ----------------------------
-- Table structure for api
-- ----------------------------
DROP TABLE IF EXISTS `api`;
CREATE TABLE `api`  (
  `api_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '属性id',
  `name` varchar(225) NOT NULL DEFAULT 0 COMMENT '功能类型，默认0是属性，1是事件，2是服务',
  `api_type` varchar(225) NOT NULL COMMENT '类型',
  `reference` int(11) NOT NULL COMMENT '调用次数',
  `start_time` timestamp NOT NULL COMMENT '创建时间',
  `description` varchar(225) NOT NULL COMMENT '描述',
  `api_srn` varchar(225) NOT NULL COMMENT '地址',
  `request_type` varchar(225) NOT NULL COMMENT '请求方式',
  `return_type` varchar(225) NOT NULL COMMENT '返回类型',
  PRIMARY KEY (`api_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `api` VALUES (10595,'设备数离线指标查询API','基础服务API','7094','2020-05-18 10:04:21','获取用户多天的产品总数，月激活设备数，日激活设备数，日新增设备数，最多查询30天的数据','acs:iot:*:f16a1ae1a8707cdc4ed67a217e40868b:serveapi/queryDeviceOfflineCalParm','POST','JSON');

-- ----------------------------
-- Table structure for data_parameter
-- ----------------------------
DROP TABLE IF EXISTS `data_parameter`;
CREATE TABLE `data_parameter`  (
  `dpid` int(11) NOT NULL AUTO_INCREMENT COMMENT '参数id',
  `name` varchar(225) NOT NULL COMMENT '参数名称',
  `data_type` varchar(225) NOT NULL DEFAULT 'VARCHAR' COMMENT '类型',
  `example` varchar(225) NOT NULL COMMENT '示例值',
  `is_ness` int(3) NOT NULL DEFAULT 1 COMMENT '是否必选，默认1是必选，0是不必选',
  `description` varchar(225) NOT NULL COMMENT '描述',
  PRIMARY KEY (`dpid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of data_parameter
-- ----------------------------
INSERT INTO `data_parameter` VALUES (1,'endDate','VARCHAR','20200110',1,'查询的结束日期');
INSERT INTO `data_parameter` VALUES (2,'startDate','VARCHAR','20200101',1,'查询的开始日期');

INSERT INTO `data_parameter` VALUES (3,'statDate','VARCHAR','20200110',0,'统计日期');
INSERT INTO `data_parameter` VALUES (4,'productCnt','VARCHAR','15',0,'产品数');
INSERT INTO `data_parameter` VALUES (5,'deviceMonthlyActiveCnt','VARCHAR','15',0,'月激活设备数');
INSERT INTO `data_parameter` VALUES (6,'deleteDailyActiveCnt','VARCHAR','15',0,'日激活设备数');
INSERT INTO `data_parameter` VALUES (7,'deviceDailyCnt','VARCHAR','15',0,'日新增设备数');


-- ----------------------------
-- Table structure for request_link
-- ----------------------------
DROP TABLE IF EXISTS `request_link`;
CREATE TABLE `request_link`  (
  `api_id` int(11) NOT NULL COMMENT 'api模型id',
  `dpid` int(11) NOT NULL COMMENT '参数id',
  PRIMARY KEY (`api_id`, `dpid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of request_link
-- ----------------------------
INSERT INTO `request_link` VALUES (10595,1);
INSERT INTO `request_link` VALUES (10595,2);

-- ----------------------------
-- Table structure for return_link
-- ----------------------------
DROP TABLE IF EXISTS `return_link`;
CREATE TABLE `return_link`  (
  `api_id` int(11) NOT NULL COMMENT 'api模型id',
  `dpid` int(11) NOT NULL COMMENT '参数id',
  PRIMARY KEY (`api_id`, `dpid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of request_link
-- ----------------------------
INSERT INTO `return_link` VALUES (10595,3);
INSERT INTO `return_link` VALUES (10595,4);
INSERT INTO `return_link` VALUES (10595,5);
INSERT INTO `return_link` VALUES (10595,6);
INSERT INTO `return_link` VALUES (10595,7);

SET FOREIGN_KEY_CHECKS = 1;







