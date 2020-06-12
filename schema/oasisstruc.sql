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

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for author
-- ----------------------------
DROP TABLE IF EXISTS `author`;
CREATE TABLE `author`  (
  `aid` int(11) NOT NULL AUTO_INCREMENT,
  `aname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `activity` double(255, 4) NULL DEFAULT NULL,
  `paperamount` int(255) NULL DEFAULT NULL,
  `papercited` int(255) NULL DEFAULT NULL,
  PRIMARY KEY (`aid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2739 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for authorinstitution
-- ----------------------------
DROP TABLE IF EXISTS `authorinstitution`;
CREATE TABLE `authorinstitution`  (
  `aid` int(11) NOT NULL,
  `iid` int(11) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for authorpaper
-- ----------------------------
DROP TABLE IF EXISTS `authorpaper`;
CREATE TABLE `authorpaper`  (
  `aid` int(11) NOT NULL,
  `pid` int(11) NOT NULL,
  `authorrank` int(255) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for conference
-- ----------------------------
DROP TABLE IF EXISTS `conference`;
CREATE TABLE `conference`  (
  `cid` int(11) NOT NULL AUTO_INCREMENT,
  `cname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `paperamount` int(255) NULL DEFAULT NULL,
  PRIMARY KEY (`cid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for conferencepaper
-- ----------------------------
DROP TABLE IF EXISTS `conferencepaper`;
CREATE TABLE `conferencepaper`  (
  `cid` int(11) NOT NULL,
  `pid` int(11) NOT NULL,
  PRIMARY KEY (`cid`, `pid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for institution
-- ----------------------------
DROP TABLE IF EXISTS `institution`;
CREATE TABLE `institution`  (
  `iid` int(11) NOT NULL AUTO_INCREMENT,
  `iname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `activity` double(255, 4) NULL DEFAULT NULL,
  `paperamount` int(255) NULL DEFAULT NULL,
  `papercited` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`iid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1432 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for institutionpaper
-- ----------------------------
DROP TABLE IF EXISTS `institutionpaper`;
CREATE TABLE `institutionpaper`  (
  `iid` int(11) NOT NULL,
  `pid` int(11) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for keyword
-- ----------------------------
DROP TABLE IF EXISTS `keyword`;
CREATE TABLE `keyword`  (
  `kid` int(11) NOT NULL AUTO_INCREMENT,
  `kname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `activity` double(255, 4) NULL DEFAULT NULL,
  `paperamount` int(255) NULL DEFAULT NULL,
  PRIMARY KEY (`kid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2617 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for keywordpaper
-- ----------------------------
DROP TABLE IF EXISTS `keywordpaper`;
CREATE TABLE `keywordpaper`  (
  `kid` int(11) NOT NULL,
  `pid` int(11) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for paper
-- ----------------------------
DROP TABLE IF EXISTS `paper`;
CREATE TABLE `paper`  (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `doctitle` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `authors` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `institutions` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `pubtitle` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `datx` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pubyear` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `volume` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `issue` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `startpage` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `endpage` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `abstract` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `issn` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `isbns` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `doi` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `fundinfo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pdflink` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `keywords` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `ieeeterms` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `inspeccterms` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `inspecnterms` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `meshterms` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `articlecite` int(255) NULL DEFAULT NULL,
  `reference` int(255) NULL DEFAULT NULL,
  `license` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `onlinedate` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `issuedate` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `meetingdate` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `publisher` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `docidentifier` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`pid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1225 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
