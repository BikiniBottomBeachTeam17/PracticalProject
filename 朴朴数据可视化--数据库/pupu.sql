/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : pupu

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 01/05/2022 20:10:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for datas
-- ----------------------------
DROP TABLE IF EXISTS `datas`;
CREATE TABLE `datas`  (
  `uuid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '物品唯一标识',
  `price` double(10, 2) NULL DEFAULT NULL COMMENT '物品现价',
  `querytime` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '查询时间'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of datas
-- ----------------------------
INSERT INTO `datas` VALUES ('714c63f8-075d-4d20-b69c-246ac48d9da1', 49.90, '2022/04/29');
INSERT INTO `datas` VALUES ('714c63f8-075d-4d20-b69c-246ac48d9da1', 46.90, '2022/04/28');
INSERT INTO `datas` VALUES ('714c63f8-075d-4d20-b69c-246ac48d9da1', 50.90, '2022/04/26');
INSERT INTO `datas` VALUES ('714c63f8-075d-4d20-b69c-246ac48d9da1', 46.90, '2022/04/25');
INSERT INTO `datas` VALUES ('714c63f8-075d-4d20-b69c-246ac48d9da1', 49.90, '2022/04/24');
INSERT INTO `datas` VALUES ('714c63f8-075d-4d20-b69c-246ac48d9da1', 52.90, '2022/04/23');
INSERT INTO `datas` VALUES ('714c63f8-075d-4d20-b69c-246ac48d9da1', 56.90, '2022/04/22');
INSERT INTO `datas` VALUES ('714c63f8-075d-4d20-b69c-246ac48d9da1', 41.90, '2022/04/27');
INSERT INTO `datas` VALUES ('714c63f8-075d-4d20-b69c-246ac48d9da1', 49.90, '2022/04/21');
INSERT INTO `datas` VALUES ('714c63f8-075d-4d20-b69c-246ac48d9da1', 39.90, '2022/5/1');
INSERT INTO `datas` VALUES ('a65ac3eb-9894-41fc-961a-538492f59327', 9.90, '2022/05/01');

-- ----------------------------
-- Table structure for items
-- ----------------------------
DROP TABLE IF EXISTS `items`;
CREATE TABLE `items`  (
  `uuid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '物品id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '物品名称',
  `subTitle` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '物品简介',
  `origin` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '产地',
  `img` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '图片地址',
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of items
-- ----------------------------
INSERT INTO `items` VALUES ('714c63f8-075d-4d20-b69c-246ac48d9da1', '美早樱桃（大果）240g-250g/份', '甜脆可口 肉厚核小', '辽宁省大连市', 'https://imgs.static.pupumall.com/STORE_PRODUCT/ec29a07ccbb5451b9fb46b71f2a38057/1649417293748.png');
INSERT INTO `items` VALUES ('a65ac3eb-9894-41fc-961a-538492f59327', ' 精品金银玉米800g（4根）', '清甜脆嫩，精致可人', '广东省湛江市', 'https://imgs.pupuapi.com/STORE_PRODUCT/a3d844176f6c416f86ffd8d6eda5d02a/1646824330936.png');

SET FOREIGN_KEY_CHECKS = 1;
