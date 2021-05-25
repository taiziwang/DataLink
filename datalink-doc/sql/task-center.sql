/*
 Navicat Premium Data Transfer

 Source Server         : hetl
 Source Server Type    : MySQL
 Source Server Version : 80015
 Source Host           : localhost:3306
 Source Schema         : datalink

 Target Server Type    : MySQL
 Target Server Version : 80015
 File Encoding         : 65001

 Date: 24/05/2021 23:51:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for dlink_flink_sql
-- ----------------------------
DROP TABLE IF EXISTS `dlink_flink_sql`;
CREATE TABLE `dlink_flink_sql`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `alias` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '别名',
  `type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型',
  `sql_index` int(2) NULL DEFAULT NULL COMMENT '次序',
  `statement` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '语句',
  `note` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `enabled` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否启用',
  `create_user` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` int(11) NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `task_id` int(11) NOT NULL COMMENT '任务ID',
  `tenant_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_task_id`(`task_id`, `tenant_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'FlinkSql' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for dlink_task
-- ----------------------------
DROP TABLE IF EXISTS `dlink_task`;
CREATE TABLE `dlink_task`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `alias` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '别名',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类型',
  `note` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '注释',
  `enabled` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否启用',
  `create_user` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` int(11) NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `tenant_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '租户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '作业' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
