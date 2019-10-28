/*
 Navicat MySQL Data Transfer

 Source Server         : 10.3.66.160
 Source Server Type    : MySQL
 Source Server Version : 50643
 Source Host           : 10.3.66.160:3306
 Source Schema         : hp_ticketingsys

 Target Server Type    : MySQL
 Target Server Version : 50643
 File Encoding         : 65001

 Date: 28/04/2019 22:17:47
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for data_spec_sp_rule
-- ----------------------------
DROP TABLE IF EXISTS `data_spec_sp_rule`;
CREATE TABLE `data_spec_sp_rule`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `aptype` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `apstatus` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `p_id` bigint(20) NULL DEFAULT NULL,
  `a_time` datetime(0) NOT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `allow_low_price` tinyint(4) NOT NULL DEFAULT 0,
  `apindex` int(11) NOT NULL DEFAULT 1,
  `price_streg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '价格策略',
  `movie_type` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '限制影片',
  `store_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '限制影城',
  `time_streg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '时间策略',
  `movie_type_name` varchar(2048) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `store_type_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `time_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `hall_id` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for data_store_info
-- ----------------------------
DROP TABLE IF EXISTS `data_store_info`;
CREATE TABLE `data_store_info`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `store_basic` bigint(20) NULL DEFAULT NULL,
  `store_logo` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `store_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `store_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `province` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `city` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `zone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `introduction` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `manager_id` bigint(20) NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `latitude` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `longitude` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `utimekey` bigint(20) NOT NULL DEFAULT 0,
  `duty_manager_id` bigint(20) NULL DEFAULT NULL,
  `proj_id` bigint(20) NOT NULL DEFAULT 2,
  `province_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `city_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `zone_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `domain` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `config_id` bigint(20) NULL DEFAULT NULL,
  `physics_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `time_type` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `time_quantity` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pubflag` tinyint(4) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `store_no`(`store_no`) USING BTREE,
  INDEX `store_no_2`(`store_no`) USING BTREE,
  INDEX `store_basic`(`store_basic`) USING BTREE,
  INDEX `city`(`city`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of data_store_info
-- ----------------------------
INSERT INTO `data_store_info` VALUES (1, 1, '', '1', '花果山球幕', '江苏省', '连云港市', '连云区', '花果山', '', NULL, NULL, '2019-04-22 23:51:12', 0, NULL, NULL, 0, NULL, 1, '320000', '320700', '320703', NULL, 1, '', NULL, NULL, 1);

-- ----------------------------
-- Table structure for data_tc_ss_cinema_config
-- ----------------------------
DROP TABLE IF EXISTS `data_tc_ss_cinema_config`;
CREATE TABLE `data_tc_ss_cinema_config`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `store_id` bigint(20) NULL DEFAULT NULL,
  `store_cinema_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tc_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pid` bigint(20) NOT NULL,
  `tc_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ss_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ss_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `utimekey` bigint(20) NULL DEFAULT NULL,
  `auth_chan_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `auth_chan_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `auth_chan_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pp_rule_type` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pp_rule_value` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pp_rule_cht` char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '10',
  `handle_fee` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0',
  `pt_rule` int(11) NULL DEFAULT 1,
  `httime` int(11) NOT NULL DEFAULT 30,
  `config_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `store_id`(`store_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of data_tc_ss_cinema_config
-- ----------------------------
INSERT INTO `data_tc_ss_cinema_config` VALUES (1, NULL, '1', NULL, 1, NULL, 'pdr', '嗨票', '2019-04-22 23:43:28', 0, NULL, '0001', '001', '0001', NULL, NULL, '10', '0', 1, 30, 'haipiao');

-- ----------------------------
-- Table structure for data_tc_ss_handle_fee_setting
-- ----------------------------
DROP TABLE IF EXISTS `data_tc_ss_handle_fee_setting`;
CREATE TABLE `data_tc_ss_handle_fee_setting`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `store_id` bigint(20) NULL DEFAULT NULL,
  `store_basic` bigint(20) NULL DEFAULT NULL,
  `hall_id` bigint(20) NULL DEFAULT NULL,
  `hall_num` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `fee` decimal(10, 2) NULL DEFAULT NULL,
  `a_time` datetime(0) NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `pid` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for data_tc_ss_lock_order_info
-- ----------------------------
DROP TABLE IF EXISTS `data_tc_ss_lock_order_info`;
CREATE TABLE `data_tc_ss_lock_order_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `original_no` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` bigint(20) NULL DEFAULT NULL,
  `mobile` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `order_status` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0000',
  `total_amount` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0',
  `pay_amount` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `a_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `pay_time` datetime(0) NULL DEFAULT NULL,
  `check_time` datetime(0) NULL DEFAULT NULL,
  `deliver_time` datetime(0) NULL DEFAULT NULL,
  `ticket_sum` int(11) NOT NULL DEFAULT 0,
  `store_id` bigint(20) NOT NULL,
  `store_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `hall_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `hall_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `plan_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `plan_date` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `plan_time` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `film_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `pic_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pay_type` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0000',
  `proj_id` bigint(16) NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `o_c` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '2000',
  `com_order` bigint(20) NULL DEFAULT NULL,
  `handle_fee` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0',
  `acmid` bigint(20) NULL DEFAULT NULL,
  `drawback_time` datetime(0) NULL DEFAULT NULL,
  `refund_time` datetime(0) NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tclass_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `create_time`(`a_time`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `proj_id`(`proj_id`) USING BTREE,
  INDEX `order_no`(`order_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for data_tc_ss_lock_order_remote
-- ----------------------------
DROP TABLE IF EXISTS `data_tc_ss_lock_order_remote`;
CREATE TABLE `data_tc_ss_lock_order_remote`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NULL DEFAULT NULL,
  `lock_flag` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ticket_flag_1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ticket_flag_2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ticket_refund` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `order_id`(`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for data_tc_ss_lock_order_seat
-- ----------------------------
DROP TABLE IF EXISTS `data_tc_ss_lock_order_seat`;
CREATE TABLE `data_tc_ss_lock_order_seat`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NULL DEFAULT NULL,
  `seat_id` bigint(20) NULL DEFAULT NULL,
  `seat_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `seat_gr` int(11) NULL DEFAULT NULL,
  `seat_gc` int(11) NULL DEFAULT NULL,
  `seat_sr` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `seat_sc` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `went_status` char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '00',
  `handle_fee` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sale_fee` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pub_fee` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `order_id`(`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for data_tc_ss_movie_attach
-- ----------------------------
DROP TABLE IF EXISTS `data_tc_ss_movie_attach`;
CREATE TABLE `data_tc_ss_movie_attach`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `film_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `offline_date` date NULL DEFAULT NULL,
  `flag` int(11) NULL DEFAULT 0,
  `a_time` datetime(0) NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `cinema_movie_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pid` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `film_no`(`film_no`) USING BTREE,
  INDEX `offline_date`(`offline_date`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14140 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for data_tc_ss_movie_celebrity
-- ----------------------------
DROP TABLE IF EXISTS `data_tc_ss_movie_celebrity`;
CREATE TABLE `data_tc_ss_movie_celebrity`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `movie_id` bigint(20) NULL DEFAULT NULL,
  `cele_id` bigint(20) NULL DEFAULT NULL,
  `role_type` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1000',
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `a_time` datetime(0) NOT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `title_img` varchar(2048) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cele_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `cele_name_en` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `movie_id`(`movie_id`) USING BTREE,
  INDEX `cele_id`(`cele_id`) USING BTREE,
  INDEX `flag`(`flag`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for data_tc_ss_movie_info
-- ----------------------------
DROP TABLE IF EXISTS `data_tc_ss_movie_info`;
CREATE TABLE `data_tc_ss_movie_info`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `film_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `film_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `film_subtitle` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `film_brief` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `film_content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `film_language` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `film_format` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `film_dimensional` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `film_size` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `film_region` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '影片国家',
  `release_date` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pic_entry` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pic_small` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `prevue` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `film_score` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `time_diff` int(11) NULL DEFAULT 0,
  `film_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `film_alias_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `film_source` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '影片来源 0000 院线 0001 本地添加',
  `proj_id` bigint(20) NULL DEFAULT NULL,
  `a_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `proj_id`(`proj_id`) USING BTREE,
  INDEX `release_date`(`release_date`) USING BTREE,
  INDEX `film_no`(`film_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for data_tc_ss_movie_picture
-- ----------------------------
DROP TABLE IF EXISTS `data_tc_ss_movie_picture`;
CREATE TABLE `data_tc_ss_movie_picture`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `m_id` bigint(20) NULL DEFAULT NULL,
  `pic_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pic_type` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `utimekey` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `flag`(`flag`) USING BTREE,
  INDEX `m_id`(`m_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for data_tc_ss_reserve_order_info
-- ----------------------------
DROP TABLE IF EXISTS `data_tc_ss_reserve_order_info`;
CREATE TABLE `data_tc_ss_reserve_order_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `original_no` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` bigint(20) NULL DEFAULT NULL,
  `mobile` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `order_status` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0000',
  `total_amount` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0',
  `pay_amount` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `a_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `pay_time` datetime(0) NULL DEFAULT NULL,
  `check_time` datetime(0) NULL DEFAULT NULL,
  `deliver_time` datetime(0) NULL DEFAULT NULL,
  `ticket_sum` int(11) NOT NULL DEFAULT 0,
  `store_id` bigint(20) NOT NULL,
  `store_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `hall_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `hall_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `plan_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `plan_date` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `plan_time` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `film_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `pic_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pay_type` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0000',
  `proj_id` bigint(16) NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `o_c` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '2000',
  `com_order` bigint(20) NULL DEFAULT NULL,
  `handle_fee` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0',
  `acmid` bigint(20) NULL DEFAULT NULL,
  `drawback_time` datetime(0) NULL DEFAULT NULL,
  `refund_time` datetime(0) NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tclass_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `create_time`(`a_time`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `proj_id`(`proj_id`) USING BTREE,
  INDEX `order_no`(`order_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for data_tc_ss_reserve_order_remote
-- ----------------------------
DROP TABLE IF EXISTS `data_tc_ss_reserve_order_remote`;
CREATE TABLE `data_tc_ss_reserve_order_remote`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NULL DEFAULT NULL,
  `lock_flag` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ticket_flag_1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ticket_flag_2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ticket_refund` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `order_id`(`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for data_tc_ss_reserve_order_seat
-- ----------------------------
DROP TABLE IF EXISTS `data_tc_ss_reserve_order_seat`;
CREATE TABLE `data_tc_ss_reserve_order_seat`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NULL DEFAULT NULL,
  `seat_id` bigint(20) NULL DEFAULT NULL,
  `seat_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `seat_gr` int(11) NULL DEFAULT NULL,
  `seat_gc` int(11) NULL DEFAULT NULL,
  `seat_sr` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `seat_sc` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `went_status` char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '00',
  `handle_fee` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sale_fee` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pub_fee` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `order_id`(`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for data_tc_ss_sale_order_info
-- ----------------------------
DROP TABLE IF EXISTS `data_tc_ss_sale_order_info`;
CREATE TABLE `data_tc_ss_sale_order_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `original_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` bigint(20) NULL DEFAULT NULL,
  `mobile` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `order_status` varchar(4) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0000',
  `total_amount` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0',
  `pay_amount` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `a_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `pay_time` datetime(0) NULL DEFAULT NULL,
  `check_time` datetime(0) NULL DEFAULT NULL,
  `deliver_time` datetime(0) NULL DEFAULT NULL,
  `ticket_sum` int(11) NOT NULL DEFAULT 0,
  `store_id` bigint(20) NOT NULL,
  `store_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `hall_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `hall_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `plan_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `plan_date` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `plan_time` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `film_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `pic_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pay_type` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `proj_id` bigint(16) NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `o_c` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '2000',
  `com_order` bigint(20) NULL DEFAULT NULL,
  `handle_fee` decimal(10, 2) NOT NULL DEFAULT 0.00,
  `acmid` bigint(20) NULL DEFAULT NULL,
  `drawback_time` datetime(0) NULL DEFAULT NULL,
  `refund_time` datetime(0) NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tclass_id` bigint(20) NULL DEFAULT NULL,
  `film_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `film_dimensional` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `store_basic` bigint(20) NULL DEFAULT NULL,
  `service_fee` decimal(10, 2) NOT NULL DEFAULT 0.00,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `create_time`(`a_time`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `proj_id`(`proj_id`) USING BTREE,
  INDEX `order_no`(`order_no`) USING BTREE,
  INDEX `plan_date`(`plan_date`) USING BTREE,
  INDEX `plan_time`(`plan_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 162 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for data_tc_ss_sale_order_remote
-- ----------------------------
DROP TABLE IF EXISTS `data_tc_ss_sale_order_remote`;
CREATE TABLE `data_tc_ss_sale_order_remote`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NULL DEFAULT NULL,
  `lock_flag` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ticket_flag_1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ticket_flag_2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `ticket_refund` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `order_id`(`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 146 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for data_tc_ss_sale_order_seat
-- ----------------------------
DROP TABLE IF EXISTS `data_tc_ss_sale_order_seat`;
CREATE TABLE `data_tc_ss_sale_order_seat`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` bigint(20) NULL DEFAULT NULL,
  `seat_id` bigint(20) NULL DEFAULT NULL,
  `seat_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `seat_gr` int(11) NULL DEFAULT NULL,
  `seat_gc` int(11) NULL DEFAULT NULL,
  `seat_sr` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `seat_sc` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `went_status` char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '00',
  `handle_fee` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `sale_fee` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pub_fee` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `service_fee` decimal(10, 2) NOT NULL DEFAULT 0.00,
  `tclass_id` bigint(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `order_id`(`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 177 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for dic_city
-- ----------------------------
DROP TABLE IF EXISTS `dic_city`;
CREATE TABLE `dic_city`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `city_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `city_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `m_level` tinyint(4) NOT NULL DEFAULT 1,
  `pid` int(11) NULL DEFAULT NULL,
  `s_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `city_no`(`city_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3382 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of dic_city
-- ----------------------------
INSERT INTO `dic_city` VALUES (1, '110000', '北京市', 0, NULL, '北京', 0);
INSERT INTO `dic_city` VALUES (2, '140100', '太原市', 1, 16, '太原', 0);
INSERT INTO `dic_city` VALUES (6, '140200', '大同市', 1, 16, '大同', 0);
INSERT INTO `dic_city` VALUES (7, '140600', '朔州市', 1, 16, '塑州', 0);
INSERT INTO `dic_city` VALUES (8, '140900', '忻州市', 1, 16, '忻州', 0);
INSERT INTO `dic_city` VALUES (9, '140300', '阳泉市', 1, 16, '阳泉', 0);
INSERT INTO `dic_city` VALUES (10, '142300', '吕梁市', 1, 16, '吕梁', 0);
INSERT INTO `dic_city` VALUES (11, '140700', '晋中市', 1, 16, '晋中', 0);
INSERT INTO `dic_city` VALUES (12, '141000', '临汾市', 1, 16, '临汾', 0);
INSERT INTO `dic_city` VALUES (13, '140800', '运城市', 1, 16, '运城', 0);
INSERT INTO `dic_city` VALUES (14, '140400', '长治市', 1, 16, '长治', 0);
INSERT INTO `dic_city` VALUES (15, '140500', '晋城市', 1, 16, '晋城', 0);
INSERT INTO `dic_city` VALUES (16, '140000', '山西省', 0, NULL, '山西', 0);
INSERT INTO `dic_city` VALUES (17, '130000', '河北省', 0, NULL, '河北', 0);
INSERT INTO `dic_city` VALUES (18, '130100', '石家庄市', 1, 17, '石家庄', 0);
INSERT INTO `dic_city` VALUES (19, '130200', '唐山市', 1, 17, '唐山', 0);
INSERT INTO `dic_city` VALUES (20, '130400', '邯郸市', 1, 17, '邯郸', 0);
INSERT INTO `dic_city` VALUES (21, '130300', '秦皇岛市', 1, 17, '秦皇岛', 0);
INSERT INTO `dic_city` VALUES (22, '130600', '保定市', 1, 17, '保定', 0);
INSERT INTO `dic_city` VALUES (23, '130700', '张家口市', 1, 17, '张家口', 0);
INSERT INTO `dic_city` VALUES (24, '130800', '承德市', 1, 17, '承德', 0);
INSERT INTO `dic_city` VALUES (25, '131000', '廊坊市', 1, 17, '廊坊', 0);
INSERT INTO `dic_city` VALUES (26, '130900', '沧州市', 1, 17, '沧州', 0);
INSERT INTO `dic_city` VALUES (27, '131100', '衡水市', 1, 17, '衡水', 0);
INSERT INTO `dic_city` VALUES (28, '130500', '邢台市', 1, 17, '邢台', 0);
INSERT INTO `dic_city` VALUES (29, '120000', '天津市', 0, NULL, '天津市', 0);
INSERT INTO `dic_city` VALUES (30, '150000', '内蒙古自治区', 0, NULL, '内蒙古自治区', 0);
INSERT INTO `dic_city` VALUES (31, '210000', '辽宁省', 0, NULL, '辽宁省', 0);
INSERT INTO `dic_city` VALUES (32, '220000', '吉林省', 0, NULL, '吉林省', 0);
INSERT INTO `dic_city` VALUES (33, '230000', '黑龙江省', 0, NULL, '黑龙江省', 0);
INSERT INTO `dic_city` VALUES (34, '310000', '上海市', 0, NULL, '上海市', 0);
INSERT INTO `dic_city` VALUES (35, '320000', '江苏省', 0, NULL, '江苏省', 0);
INSERT INTO `dic_city` VALUES (36, '330000', '浙江省', 0, NULL, '浙江省', 0);
INSERT INTO `dic_city` VALUES (37, '340000', '安徽省', 0, NULL, '安徽省', 0);
INSERT INTO `dic_city` VALUES (38, '350000', '福建省', 0, NULL, '福建省', 0);
INSERT INTO `dic_city` VALUES (39, '360000', '江西省', 0, NULL, '江西省', 0);
INSERT INTO `dic_city` VALUES (40, '370000', '山东省', 0, NULL, '山东省', 0);
INSERT INTO `dic_city` VALUES (41, '410000', '河南省', 0, NULL, '河南省', 0);
INSERT INTO `dic_city` VALUES (42, '420000', '湖北省', 0, NULL, '湖北省', 0);
INSERT INTO `dic_city` VALUES (43, '430000', '湖南省', 0, NULL, '湖南省', 0);
INSERT INTO `dic_city` VALUES (44, '440000', '广东省', 0, NULL, '广东省', 0);
INSERT INTO `dic_city` VALUES (45, '450000', '广西藏族自治区', 0, NULL, '广西藏族自治区', 0);
INSERT INTO `dic_city` VALUES (46, '460000', '海南省', 0, NULL, '海南省', 0);
INSERT INTO `dic_city` VALUES (47, '500000', '重庆市', 0, NULL, '重庆市', 0);
INSERT INTO `dic_city` VALUES (48, '510000', '四川省', 0, NULL, '四川省', 0);
INSERT INTO `dic_city` VALUES (49, '520000', '贵州省', 0, NULL, '贵州省', 0);
INSERT INTO `dic_city` VALUES (50, '530000', '云南省', 0, NULL, '云南省', 0);
INSERT INTO `dic_city` VALUES (51, '540000', '西藏自治区', 0, NULL, '西藏自治区', 0);
INSERT INTO `dic_city` VALUES (52, '610000', '陕西省', 0, NULL, '陕西省', 0);
INSERT INTO `dic_city` VALUES (53, '620000', '甘肃省', 0, NULL, '甘肃省', 0);
INSERT INTO `dic_city` VALUES (54, '630000', '青海省', 0, NULL, '青海省', 0);
INSERT INTO `dic_city` VALUES (55, '640000', '宁夏回族自治区', 0, NULL, '宁夏回族自治区', 0);
INSERT INTO `dic_city` VALUES (56, '650000', '新疆维吾尔自治区', 0, NULL, '新疆维吾尔自治区', 0);
INSERT INTO `dic_city` VALUES (57, '710000', '台湾省', 0, NULL, '台湾省', 0);
INSERT INTO `dic_city` VALUES (58, '810000', '香港特别行政区', 0, NULL, '香港特别行政区', 0);
INSERT INTO `dic_city` VALUES (59, '820000', '澳门特别行政区', 0, NULL, '澳门特别行政区', 0);
INSERT INTO `dic_city` VALUES (60, '110100', '北京市', 1, 1, '北京市', 0);
INSERT INTO `dic_city` VALUES (61, '120100', '天津市', 1, 29, '天津市', 0);
INSERT INTO `dic_city` VALUES (62, '310100', '上海市', 1, 34, '上海市', 0);
INSERT INTO `dic_city` VALUES (63, '500100', '重庆市', 1, 47, '重庆市', 0);
INSERT INTO `dic_city` VALUES (64, '150100', '呼和浩特市', 1, 30, '呼和浩特市', 0);
INSERT INTO `dic_city` VALUES (65, '150200', '包头市', 1, 30, '包头市', 0);
INSERT INTO `dic_city` VALUES (66, '150300', '乌海市', 1, 30, '乌海市', 0);
INSERT INTO `dic_city` VALUES (67, '150400', '赤峰市', 1, 30, '赤峰市', 0);
INSERT INTO `dic_city` VALUES (68, '150500', '通辽市', 1, 30, '通辽市', 0);
INSERT INTO `dic_city` VALUES (69, '150600', '鄂尔多斯市', 1, 30, '鄂尔多斯市', 0);
INSERT INTO `dic_city` VALUES (70, '150700', '呼伦贝尔市', 1, 30, '呼伦贝尔市', 0);
INSERT INTO `dic_city` VALUES (71, '152200', '兴安盟', 1, 30, '兴安盟', 0);
INSERT INTO `dic_city` VALUES (72, '152500', '锡林郭勒盟', 1, 30, '锡林郭勒盟', 0);
INSERT INTO `dic_city` VALUES (73, '152600', '乌兰察布盟', 1, 30, '乌兰察布盟', 0);
INSERT INTO `dic_city` VALUES (74, '152700', '巴彦淖尔盟', 1, 30, '巴彦淖尔盟', 0);
INSERT INTO `dic_city` VALUES (75, '152900', '阿拉善盟', 1, 30, '阿拉善盟', 0);
INSERT INTO `dic_city` VALUES (76, '210100', '沈阳市', 1, 31, '沈阳市', 0);
INSERT INTO `dic_city` VALUES (77, '210200', '大连市', 1, 31, '大连市', 0);
INSERT INTO `dic_city` VALUES (78, '210300', '鞍山市', 1, 31, '鞍山市', 0);
INSERT INTO `dic_city` VALUES (79, '210400', '抚顺市', 1, 31, '抚顺市', 0);
INSERT INTO `dic_city` VALUES (80, '210500', '本溪市', 1, 31, '本溪市', 0);
INSERT INTO `dic_city` VALUES (81, '210600', '丹东市', 1, 31, '丹东市', 0);
INSERT INTO `dic_city` VALUES (82, '210700', '锦州市', 1, 31, '锦州市', 0);
INSERT INTO `dic_city` VALUES (83, '210800', '营口市', 1, 31, '营口市', 0);
INSERT INTO `dic_city` VALUES (84, '210900', '阜新市', 1, 31, '阜新市', 0);
INSERT INTO `dic_city` VALUES (85, '211000', '辽阳市', 1, 31, '辽阳市', 0);
INSERT INTO `dic_city` VALUES (86, '211100', '盘锦市', 1, 31, '盘锦市', 0);
INSERT INTO `dic_city` VALUES (87, '211200', '铁岭市', 1, 31, '铁岭市', 0);
INSERT INTO `dic_city` VALUES (88, '211300', '朝阳市', 1, 31, '朝阳市', 0);
INSERT INTO `dic_city` VALUES (89, '211400', '葫芦岛市', 1, 31, '葫芦岛市', 0);
INSERT INTO `dic_city` VALUES (90, '220100', '长春市', 1, 32, '长春市', 0);
INSERT INTO `dic_city` VALUES (91, '220200', '吉林市', 1, 32, '吉林市', 0);
INSERT INTO `dic_city` VALUES (92, '220300', '四平市', 1, 32, '四平市', 0);
INSERT INTO `dic_city` VALUES (93, '220400', '辽源市', 1, 32, '辽源市', 0);
INSERT INTO `dic_city` VALUES (94, '220500', '通化市', 1, 32, '通化市', 0);
INSERT INTO `dic_city` VALUES (95, '220600', '白山市', 1, 32, '白山市', 0);
INSERT INTO `dic_city` VALUES (96, '220700', '松原市', 1, 32, '松原市', 0);
INSERT INTO `dic_city` VALUES (97, '220800', '白城市', 1, 32, '白城市', 0);
INSERT INTO `dic_city` VALUES (98, '222400', '延边州', 1, 32, '延边州', 0);
INSERT INTO `dic_city` VALUES (99, '230100', '哈尔滨市', 1, 33, '哈尔滨市', 0);
INSERT INTO `dic_city` VALUES (100, '230200', '齐齐哈尔市', 1, 33, '齐齐哈尔市', 0);
INSERT INTO `dic_city` VALUES (101, '230300', '鸡西市', 1, 33, '鸡西市', 0);
INSERT INTO `dic_city` VALUES (102, '230400', '鹤岗市', 1, 33, '鹤岗市', 0);
INSERT INTO `dic_city` VALUES (103, '230500', '双鸭山市', 1, 33, '双鸭山市', 0);
INSERT INTO `dic_city` VALUES (104, '230600', '大庆市', 1, 33, '大庆市', 0);
INSERT INTO `dic_city` VALUES (105, '230700', '伊春市', 1, 33, '伊春市', 0);
INSERT INTO `dic_city` VALUES (106, '230800', '佳木斯市', 1, 33, '佳木斯市', 0);
INSERT INTO `dic_city` VALUES (107, '230900', '七台河市', 1, 33, '七台河市', 0);
INSERT INTO `dic_city` VALUES (108, '231000', '牡丹江市', 1, 33, '牡丹江市', 0);
INSERT INTO `dic_city` VALUES (109, '231100', '黑河市', 1, 33, '黑河市', 0);
INSERT INTO `dic_city` VALUES (110, '231200', '绥化市', 1, 33, '绥化市', 0);
INSERT INTO `dic_city` VALUES (111, '232700', '大兴安岭地区', 1, 33, '大兴安岭地区', 0);
INSERT INTO `dic_city` VALUES (112, '310101', '黄浦区', 1, 34, '黄浦区', 0);
INSERT INTO `dic_city` VALUES (113, '310103', '卢湾区', 1, 34, '卢湾区', 0);
INSERT INTO `dic_city` VALUES (114, '310104', '徐汇区', 1, 34, '徐汇区', 0);
INSERT INTO `dic_city` VALUES (115, '310105', '长宁区', 1, 34, '长宁区', 0);
INSERT INTO `dic_city` VALUES (116, '310106', '静安区', 1, 34, '静安区', 0);
INSERT INTO `dic_city` VALUES (117, '310107', '普陀区', 1, 34, '普陀区', 0);
INSERT INTO `dic_city` VALUES (118, '310108', '闸北区', 1, 34, '闸北区', 0);
INSERT INTO `dic_city` VALUES (119, '310109', '虹口区', 1, 34, '虹口区', 0);
INSERT INTO `dic_city` VALUES (120, '310110', '杨浦区', 1, 34, '杨浦区', 0);
INSERT INTO `dic_city` VALUES (121, '310112', '闵行区', 1, 34, '闵行区', 0);
INSERT INTO `dic_city` VALUES (122, '310113', '宝山区', 1, 34, '宝山区', 0);
INSERT INTO `dic_city` VALUES (123, '310114', '嘉定区', 1, 34, '嘉定区', 0);
INSERT INTO `dic_city` VALUES (124, '310115', '浦东新区', 1, 34, '浦东新区', 0);
INSERT INTO `dic_city` VALUES (125, '310116', '金山区', 1, 34, '金山区', 0);
INSERT INTO `dic_city` VALUES (126, '310117', '松江区', 1, 34, '松江区', 0);
INSERT INTO `dic_city` VALUES (127, '310118', '青浦区', 1, 34, '青浦区', 0);
INSERT INTO `dic_city` VALUES (128, '310119', '南汇区', 1, 34, '南汇区', 0);
INSERT INTO `dic_city` VALUES (129, '310120', '奉贤区', 1, 34, '奉贤区', 0);
INSERT INTO `dic_city` VALUES (130, '310230', '崇明县', 1, 34, '崇明县', 0);
INSERT INTO `dic_city` VALUES (131, '320100', '南京市', 1, 35, '南京市', 0);
INSERT INTO `dic_city` VALUES (132, '320200', '无锡市', 1, 35, '无锡市', 0);
INSERT INTO `dic_city` VALUES (133, '320300', '徐州市', 1, 35, '徐州市', 0);
INSERT INTO `dic_city` VALUES (134, '320400', '常州市', 1, 35, '常州市', 0);
INSERT INTO `dic_city` VALUES (135, '320500', '苏州市', 1, 35, '苏州市', 0);
INSERT INTO `dic_city` VALUES (136, '320600', '南通市', 1, 35, '南通市', 0);
INSERT INTO `dic_city` VALUES (137, '320700', '连云港市', 1, 35, '连云港市', 0);
INSERT INTO `dic_city` VALUES (138, '320800', '淮安市', 1, 35, '淮安市', 0);
INSERT INTO `dic_city` VALUES (139, '320900', '盐城市', 1, 35, '盐城市', 0);
INSERT INTO `dic_city` VALUES (140, '321000', '扬州市', 1, 35, '扬州市', 0);
INSERT INTO `dic_city` VALUES (141, '321100', '镇江市', 1, 35, '镇江市', 0);
INSERT INTO `dic_city` VALUES (142, '321200', '泰州市', 1, 35, '泰州市', 0);
INSERT INTO `dic_city` VALUES (143, '321300', '宿迁市', 1, 35, '宿迁市', 0);
INSERT INTO `dic_city` VALUES (144, '330100', '杭州市', 1, 36, '杭州市', 0);
INSERT INTO `dic_city` VALUES (145, '330200', '宁波市', 1, 36, '宁波市', 0);
INSERT INTO `dic_city` VALUES (146, '330300', '温州市', 1, 36, '温州市', 0);
INSERT INTO `dic_city` VALUES (147, '330400', '嘉兴市', 1, 36, '嘉兴市', 0);
INSERT INTO `dic_city` VALUES (148, '330500', '湖州市', 1, 36, '湖州市', 0);
INSERT INTO `dic_city` VALUES (149, '330600', '绍兴市', 1, 36, '绍兴市', 0);
INSERT INTO `dic_city` VALUES (150, '330700', '金华市', 1, 36, '金华市', 0);
INSERT INTO `dic_city` VALUES (151, '330800', '衢州市', 1, 36, '衢州市', 0);
INSERT INTO `dic_city` VALUES (152, '330900', '舟山市', 1, 36, '舟山市', 0);
INSERT INTO `dic_city` VALUES (153, '331000', '台州市', 1, 36, '台州市', 0);
INSERT INTO `dic_city` VALUES (154, '331100', '丽水市', 1, 36, '丽水市', 0);
INSERT INTO `dic_city` VALUES (155, '340100', '合肥市', 1, 37, '合肥市', 0);
INSERT INTO `dic_city` VALUES (156, '340200', '芜湖市', 1, 37, '芜湖市', 0);
INSERT INTO `dic_city` VALUES (157, '340300', '蚌埠市', 1, 37, '蚌埠市', 0);
INSERT INTO `dic_city` VALUES (158, '340400', '淮南市', 1, 37, '淮南市', 0);
INSERT INTO `dic_city` VALUES (159, '340500', '马鞍山市', 1, 37, '马鞍山市', 0);
INSERT INTO `dic_city` VALUES (160, '340600', '淮北市', 1, 37, '淮北市', 0);
INSERT INTO `dic_city` VALUES (161, '340700', '铜陵市', 1, 37, '铜陵市', 0);
INSERT INTO `dic_city` VALUES (162, '340800', '安庆市', 1, 37, '安庆市', 0);
INSERT INTO `dic_city` VALUES (163, '341000', '黄山市', 1, 37, '黄山市', 0);
INSERT INTO `dic_city` VALUES (164, '341100', '滁州市', 1, 37, '滁州市', 0);
INSERT INTO `dic_city` VALUES (165, '341200', '阜阳市', 1, 37, '阜阳市', 0);
INSERT INTO `dic_city` VALUES (166, '341300', '宿州市', 1, 37, '宿州市', 0);
INSERT INTO `dic_city` VALUES (167, '341400', '巢湖市', 1, 37, '巢湖市', 0);
INSERT INTO `dic_city` VALUES (168, '341500', '六安市', 1, 37, '六安市', 0);
INSERT INTO `dic_city` VALUES (169, '341600', '亳州市', 1, 37, '亳州市', 0);
INSERT INTO `dic_city` VALUES (170, '341700', '池州市', 1, 37, '池州市', 0);
INSERT INTO `dic_city` VALUES (171, '341800', '宣城市', 1, 37, '宣城市', 0);
INSERT INTO `dic_city` VALUES (172, '350100', '福州市', 1, 38, '福州市', 0);
INSERT INTO `dic_city` VALUES (173, '350200', '厦门市', 1, 38, '厦门市', 0);
INSERT INTO `dic_city` VALUES (174, '350300', '莆田市', 1, 38, '莆田市', 0);
INSERT INTO `dic_city` VALUES (175, '350400', '三明市', 1, 38, '三明市', 0);
INSERT INTO `dic_city` VALUES (176, '350500', '泉州市', 1, 38, '泉州市', 0);
INSERT INTO `dic_city` VALUES (177, '350600', '漳州市', 1, 38, '漳州市', 0);
INSERT INTO `dic_city` VALUES (178, '350700', '南平市', 1, 38, '南平市', 0);
INSERT INTO `dic_city` VALUES (179, '350800', '龙岩市', 1, 38, '龙岩市', 0);
INSERT INTO `dic_city` VALUES (180, '350900', '宁德市', 1, 38, '宁德市', 0);
INSERT INTO `dic_city` VALUES (181, '360100', '南昌市', 1, 39, '南昌市', 0);
INSERT INTO `dic_city` VALUES (182, '360200', '景德镇市', 1, 39, '景德镇市', 0);
INSERT INTO `dic_city` VALUES (183, '360300', '萍乡市', 1, 39, '萍乡市', 0);
INSERT INTO `dic_city` VALUES (184, '360400', '九江市', 1, 39, '九江市', 0);
INSERT INTO `dic_city` VALUES (185, '360500', '新余市', 1, 39, '新余市', 0);
INSERT INTO `dic_city` VALUES (186, '360600', '鹰潭市', 1, 39, '鹰潭市', 0);
INSERT INTO `dic_city` VALUES (187, '360700', '赣州市', 1, 39, '赣州市', 0);
INSERT INTO `dic_city` VALUES (188, '360800', '吉安市', 1, 39, '吉安市', 0);
INSERT INTO `dic_city` VALUES (189, '360900', '宜春市', 1, 39, '宜春市', 0);
INSERT INTO `dic_city` VALUES (190, '361000', '抚州市', 1, 39, '抚州市', 0);
INSERT INTO `dic_city` VALUES (191, '361100', '上饶市', 1, 39, '上饶市', 0);
INSERT INTO `dic_city` VALUES (192, '370100', '济南市', 1, 40, '济南市', 0);
INSERT INTO `dic_city` VALUES (193, '370200', '青岛市', 1, 40, '青岛市', 0);
INSERT INTO `dic_city` VALUES (194, '370300', '淄博市', 1, 40, '淄博市', 0);
INSERT INTO `dic_city` VALUES (195, '370400', '枣庄市', 1, 40, '枣庄市', 0);
INSERT INTO `dic_city` VALUES (196, '370500', '东营市', 1, 40, '东营市', 0);
INSERT INTO `dic_city` VALUES (197, '370600', '烟台市', 1, 40, '烟台市', 0);
INSERT INTO `dic_city` VALUES (198, '370700', '潍坊市', 1, 40, '潍坊市', 0);
INSERT INTO `dic_city` VALUES (199, '370800', '济宁市', 1, 40, '济宁市', 0);
INSERT INTO `dic_city` VALUES (200, '370900', '泰安市', 1, 40, '泰安市', 0);
INSERT INTO `dic_city` VALUES (201, '371000', '威海市', 1, 40, '威海市', 0);
INSERT INTO `dic_city` VALUES (202, '371100', '日照市', 1, 40, '日照市', 0);
INSERT INTO `dic_city` VALUES (203, '371200', '莱芜市', 1, 40, '莱芜市', 0);
INSERT INTO `dic_city` VALUES (204, '371300', '临沂市', 1, 40, '临沂市', 0);
INSERT INTO `dic_city` VALUES (205, '371400', '德州市', 1, 40, '德州市', 0);
INSERT INTO `dic_city` VALUES (206, '371500', '聊城市', 1, 40, '聊城市', 0);
INSERT INTO `dic_city` VALUES (207, '371600', '滨州市', 1, 40, '滨州市', 0);
INSERT INTO `dic_city` VALUES (208, '371700', '菏泽市', 1, 40, '菏泽市', 0);
INSERT INTO `dic_city` VALUES (209, '410100', '郑州市', 1, 41, '郑州市', 0);
INSERT INTO `dic_city` VALUES (210, '410200', '开封市', 1, 41, '开封市', 0);
INSERT INTO `dic_city` VALUES (211, '410300', '洛阳市', 1, 41, '洛阳市', 0);
INSERT INTO `dic_city` VALUES (212, '410400', '平顶山市', 1, 41, '平顶山市', 0);
INSERT INTO `dic_city` VALUES (213, '410500', '安阳市', 1, 41, '安阳市', 0);
INSERT INTO `dic_city` VALUES (214, '410600', '鹤壁市', 1, 41, '鹤壁市', 0);
INSERT INTO `dic_city` VALUES (215, '410700', '新乡市', 1, 41, '新乡市', 0);
INSERT INTO `dic_city` VALUES (216, '410800', '焦作市', 1, 41, '焦作市', 0);
INSERT INTO `dic_city` VALUES (217, '410881', '济源市', 1, 41, '济源市', 0);
INSERT INTO `dic_city` VALUES (218, '410900', '濮阳市', 1, 41, '濮阳市', 0);
INSERT INTO `dic_city` VALUES (219, '411000', '许昌市', 1, 41, '许昌市', 0);
INSERT INTO `dic_city` VALUES (220, '411100', '漯河市', 1, 41, '漯河市', 0);
INSERT INTO `dic_city` VALUES (221, '411200', '三门峡市', 1, 41, '三门峡市', 0);
INSERT INTO `dic_city` VALUES (222, '411300', '南阳市', 1, 41, '南阳市', 0);
INSERT INTO `dic_city` VALUES (223, '411400', '商丘市', 1, 41, '商丘市', 0);
INSERT INTO `dic_city` VALUES (224, '411500', '信阳市', 1, 41, '信阳市', 0);
INSERT INTO `dic_city` VALUES (225, '411600', '周口市', 1, 41, '周口市', 0);
INSERT INTO `dic_city` VALUES (226, '411700', '驻马店市', 1, 41, '驻马店市', 0);
INSERT INTO `dic_city` VALUES (227, '420100', '武汉市', 1, 42, '武汉市', 0);
INSERT INTO `dic_city` VALUES (228, '420200', '黄石市', 1, 42, '黄石市', 0);
INSERT INTO `dic_city` VALUES (229, '420300', '十堰市', 1, 42, '十堰市', 0);
INSERT INTO `dic_city` VALUES (230, '420500', '宜昌市', 1, 42, '宜昌市', 0);
INSERT INTO `dic_city` VALUES (231, '420600', '襄樊市', 1, 42, '襄樊市', 0);
INSERT INTO `dic_city` VALUES (232, '420700', '鄂州市', 1, 42, '鄂州市', 0);
INSERT INTO `dic_city` VALUES (233, '420800', '荆门市', 1, 42, '荆门市', 0);
INSERT INTO `dic_city` VALUES (234, '420900', '孝感市', 1, 42, '孝感市', 0);
INSERT INTO `dic_city` VALUES (235, '421000', '荆州市', 1, 42, '荆州市', 0);
INSERT INTO `dic_city` VALUES (236, '421100', '黄冈市', 1, 42, '黄冈市', 0);
INSERT INTO `dic_city` VALUES (237, '421200', '咸宁市', 1, 42, '咸宁市', 0);
INSERT INTO `dic_city` VALUES (238, '421300', '随州市', 1, 42, '随州市', 0);
INSERT INTO `dic_city` VALUES (239, '422800', '恩施州', 1, 42, '恩施州', 0);
INSERT INTO `dic_city` VALUES (240, '429004', '仙桃市', 1, 42, '仙桃市', 0);
INSERT INTO `dic_city` VALUES (241, '429005', '潜江市', 1, 42, '潜江市', 0);
INSERT INTO `dic_city` VALUES (242, '429006', '天门市', 1, 42, '天门市', 0);
INSERT INTO `dic_city` VALUES (243, '429021', '神农架林区', 1, 42, '神农架林区', 0);
INSERT INTO `dic_city` VALUES (244, '430100', '长沙市', 1, 43, '长沙市', 0);
INSERT INTO `dic_city` VALUES (245, '430200', '株洲市', 1, 43, '株洲市', 0);
INSERT INTO `dic_city` VALUES (246, '430300', '湘潭市', 1, 43, '湘潭市', 0);
INSERT INTO `dic_city` VALUES (247, '430400', '衡阳市', 1, 43, '衡阳市', 0);
INSERT INTO `dic_city` VALUES (248, '430500', '邵阳市', 1, 43, '邵阳市', 0);
INSERT INTO `dic_city` VALUES (249, '430600', '岳阳市', 1, 43, '岳阳市', 0);
INSERT INTO `dic_city` VALUES (250, '430700', '常德市', 1, 43, '常德市', 0);
INSERT INTO `dic_city` VALUES (251, '430800', '张家界市', 1, 43, '张家界市', 0);
INSERT INTO `dic_city` VALUES (252, '430900', '益阳市', 1, 43, '益阳市', 0);
INSERT INTO `dic_city` VALUES (253, '431000', '郴州市', 1, 43, '郴州市', 0);
INSERT INTO `dic_city` VALUES (254, '431100', '永州市', 1, 43, '永州市', 0);
INSERT INTO `dic_city` VALUES (255, '431200', '怀化市', 1, 43, '怀化市', 0);
INSERT INTO `dic_city` VALUES (256, '431300', '娄底市', 1, 43, '娄底市', 0);
INSERT INTO `dic_city` VALUES (257, '433100', '湘西土家族苗族自治州', 1, 43, '湘西土家族苗族自治州', 0);
INSERT INTO `dic_city` VALUES (258, '440100', '广州市', 1, 44, '广州市', 0);
INSERT INTO `dic_city` VALUES (259, '440200', '韶关市', 1, 44, '韶关市', 0);
INSERT INTO `dic_city` VALUES (260, '440300', '深圳市', 1, 44, '深圳市', 0);
INSERT INTO `dic_city` VALUES (261, '440400', '珠海市', 1, 44, '珠海市', 0);
INSERT INTO `dic_city` VALUES (262, '440500', '汕头市', 1, 44, '汕头市', 0);
INSERT INTO `dic_city` VALUES (263, '440600', '佛山市', 1, 44, '佛山市', 0);
INSERT INTO `dic_city` VALUES (264, '440700', '江门市', 1, 44, '江门市', 0);
INSERT INTO `dic_city` VALUES (265, '440800', '湛江市', 1, 44, '湛江市', 0);
INSERT INTO `dic_city` VALUES (266, '440900', '茂名市', 1, 44, '茂名市', 0);
INSERT INTO `dic_city` VALUES (267, '441200', '肇庆市', 1, 44, '肇庆市', 0);
INSERT INTO `dic_city` VALUES (268, '441300', '惠州市', 1, 44, '惠州市', 0);
INSERT INTO `dic_city` VALUES (269, '441400', '梅州市', 1, 44, '梅州市', 0);
INSERT INTO `dic_city` VALUES (270, '441500', '汕尾市', 1, 44, '汕尾市', 0);
INSERT INTO `dic_city` VALUES (271, '441600', '河源市', 1, 44, '河源市', 0);
INSERT INTO `dic_city` VALUES (272, '441700', '阳江市', 1, 44, '阳江市', 0);
INSERT INTO `dic_city` VALUES (273, '441800', '清远市', 1, 44, '清远市', 0);
INSERT INTO `dic_city` VALUES (274, '441900', '东莞市', 1, 44, '东莞市', 0);
INSERT INTO `dic_city` VALUES (275, '442000', '中山市', 1, 44, '中山市', 0);
INSERT INTO `dic_city` VALUES (276, '445100', '潮州市', 1, 44, '潮州市', 0);
INSERT INTO `dic_city` VALUES (277, '445200', '揭阳市', 1, 44, '揭阳市', 0);
INSERT INTO `dic_city` VALUES (278, '445300', '云浮市', 1, 44, '云浮市', 0);
INSERT INTO `dic_city` VALUES (279, '450100', '南宁市', 1, 45, '南宁市', 0);
INSERT INTO `dic_city` VALUES (280, '450200', '柳州市', 1, 45, '柳州市', 0);
INSERT INTO `dic_city` VALUES (281, '450300', '桂林市', 1, 45, '桂林市', 0);
INSERT INTO `dic_city` VALUES (282, '450400', '梧州市', 1, 45, '梧州市', 0);
INSERT INTO `dic_city` VALUES (283, '450500', '北海市', 1, 45, '北海市', 0);
INSERT INTO `dic_city` VALUES (284, '450600', '防城港市', 1, 45, '防城港市', 0);
INSERT INTO `dic_city` VALUES (285, '450700', '钦州市', 1, 45, '钦州市', 0);
INSERT INTO `dic_city` VALUES (286, '450800', '贵港市', 1, 45, '贵港市', 0);
INSERT INTO `dic_city` VALUES (287, '450900', '玉林市', 1, 45, '玉林市', 0);
INSERT INTO `dic_city` VALUES (288, '451000', '百色市', 1, 45, '百色市', 0);
INSERT INTO `dic_city` VALUES (289, '451100', '贺州市', 1, 45, '贺州市', 0);
INSERT INTO `dic_city` VALUES (290, '451200', '河池市', 1, 45, '河池市', 0);
INSERT INTO `dic_city` VALUES (291, '451300', '来宾市', 1, 45, '来宾市', 0);
INSERT INTO `dic_city` VALUES (292, '451400', '崇左市', 1, 45, '崇左市', 0);
INSERT INTO `dic_city` VALUES (293, '460100', '海口市', 1, 46, '海口市', 0);
INSERT INTO `dic_city` VALUES (294, '460200', '三亚市', 1, 46, '三亚市', 0);
INSERT INTO `dic_city` VALUES (295, '469001', '五指山市', 1, 46, '五指山市', 0);
INSERT INTO `dic_city` VALUES (296, '469002', '琼海市', 1, 46, '琼海市', 0);
INSERT INTO `dic_city` VALUES (297, '469003', '儋州市', 1, 46, '儋州市', 0);
INSERT INTO `dic_city` VALUES (298, '469005', '文昌市', 1, 46, '文昌市', 0);
INSERT INTO `dic_city` VALUES (299, '469006', '万宁市', 1, 46, '万宁市', 0);
INSERT INTO `dic_city` VALUES (300, '469007', '东方市', 1, 46, '东方市', 0);
INSERT INTO `dic_city` VALUES (301, '469025', '定安县', 1, 46, '定安县', 0);
INSERT INTO `dic_city` VALUES (302, '469026', '屯昌县', 1, 46, '屯昌县', 0);
INSERT INTO `dic_city` VALUES (303, '469027', '澄迈县', 1, 46, '澄迈县', 0);
INSERT INTO `dic_city` VALUES (304, '469028', '临高县', 1, 46, '临高县', 0);
INSERT INTO `dic_city` VALUES (305, '469030', '白沙黎族自治县', 1, 46, '白沙黎族自治县', 0);
INSERT INTO `dic_city` VALUES (306, '469031', '昌江黎族自治县', 1, 46, '昌江黎族自治县', 0);
INSERT INTO `dic_city` VALUES (307, '469033', '乐东黎族自治县', 1, 46, '乐东黎族自治县', 0);
INSERT INTO `dic_city` VALUES (308, '469034', '陵水黎族自治县', 1, 46, '陵水黎族自治县', 0);
INSERT INTO `dic_city` VALUES (309, '469035', '保亭黎族苗族自治县', 1, 46, '保亭黎族苗族自治县', 0);
INSERT INTO `dic_city` VALUES (310, '469036', '琼中黎族苗族自治县', 1, 46, '琼中黎族苗族自治县', 0);
INSERT INTO `dic_city` VALUES (311, '469037', '西沙群岛', 1, 46, '西沙群岛', 0);
INSERT INTO `dic_city` VALUES (312, '469038', '南沙群岛', 1, 46, '南沙群岛', 0);
INSERT INTO `dic_city` VALUES (313, '469039', '中沙群岛的岛礁及其海域', 1, 46, '中沙群岛的岛礁及其海', 0);
INSERT INTO `dic_city` VALUES (314, '500101', '万州区', 1, 47, '万州区', 0);
INSERT INTO `dic_city` VALUES (315, '500102', '涪陵区', 1, 47, '涪陵区', 0);
INSERT INTO `dic_city` VALUES (316, '500103', '渝中区', 1, 47, '渝中区', 0);
INSERT INTO `dic_city` VALUES (317, '500104', '大渡口区', 1, 47, '大渡口区', 0);
INSERT INTO `dic_city` VALUES (318, '500105', '江北区', 1, 47, '江北区', 0);
INSERT INTO `dic_city` VALUES (319, '500106', '沙坪坝区', 1, 47, '沙坪坝区', 0);
INSERT INTO `dic_city` VALUES (320, '500107', '九龙坡区', 1, 47, '九龙坡区', 0);
INSERT INTO `dic_city` VALUES (321, '500108', '南岸区', 1, 47, '南岸区', 0);
INSERT INTO `dic_city` VALUES (322, '500109', '北碚区', 1, 47, '北碚区', 0);
INSERT INTO `dic_city` VALUES (323, '500110', '万盛区', 1, 47, '万盛区', 0);
INSERT INTO `dic_city` VALUES (324, '500111', '双桥区', 1, 47, '双桥区', 0);
INSERT INTO `dic_city` VALUES (325, '500112', '渝北区', 1, 47, '渝北区', 0);
INSERT INTO `dic_city` VALUES (326, '500113', '巴南区', 1, 47, '巴南区', 0);
INSERT INTO `dic_city` VALUES (327, '500114', '黔江区', 1, 47, '黔江区', 0);
INSERT INTO `dic_city` VALUES (328, '500115', '长寿区', 1, 47, '长寿区', 0);
INSERT INTO `dic_city` VALUES (329, '500222', '綦江县', 1, 47, '綦江县', 0);
INSERT INTO `dic_city` VALUES (330, '500223', '潼南县', 1, 47, '潼南县', 0);
INSERT INTO `dic_city` VALUES (331, '500224', '铜梁县', 1, 47, '铜梁县', 0);
INSERT INTO `dic_city` VALUES (332, '500225', '大足县', 1, 47, '大足县', 0);
INSERT INTO `dic_city` VALUES (333, '500226', '荣昌县', 1, 47, '荣昌县', 0);
INSERT INTO `dic_city` VALUES (334, '500227', '璧山县', 1, 47, '璧山县', 0);
INSERT INTO `dic_city` VALUES (335, '500228', '梁平县', 1, 47, '梁平县', 0);
INSERT INTO `dic_city` VALUES (336, '500229', '城口县', 1, 47, '城口县', 0);
INSERT INTO `dic_city` VALUES (337, '500230', '丰都县', 1, 47, '丰都县', 0);
INSERT INTO `dic_city` VALUES (338, '500231', '垫江县', 1, 47, '垫江县', 0);
INSERT INTO `dic_city` VALUES (339, '500232', '武隆县', 1, 47, '武隆县', 0);
INSERT INTO `dic_city` VALUES (340, '500233', '忠县', 1, 47, '忠县', 0);
INSERT INTO `dic_city` VALUES (341, '500234', '开县', 1, 47, '开县', 0);
INSERT INTO `dic_city` VALUES (342, '500235', '云阳县', 1, 47, '云阳县', 0);
INSERT INTO `dic_city` VALUES (343, '500236', '奉节县', 1, 47, '奉节县', 0);
INSERT INTO `dic_city` VALUES (344, '500237', '巫山县', 1, 47, '巫山县', 0);
INSERT INTO `dic_city` VALUES (345, '500238', '巫溪县', 1, 47, '巫溪县', 0);
INSERT INTO `dic_city` VALUES (346, '500240', '石柱土家族自治县', 1, 47, '石柱土家族自治县', 0);
INSERT INTO `dic_city` VALUES (347, '500241', '秀山土家族苗族自治县', 1, 47, '秀山土家族苗族自治县', 0);
INSERT INTO `dic_city` VALUES (348, '500242', '酉阳土家族苗族自治县', 1, 47, '酉阳土家族苗族自治县', 0);
INSERT INTO `dic_city` VALUES (349, '500243', '彭水苗族土家族自治县', 1, 47, '彭水苗族土家族自治县', 0);
INSERT INTO `dic_city` VALUES (350, '500381', '江津市', 1, 47, '江津市', 0);
INSERT INTO `dic_city` VALUES (351, '500382', '合川市', 1, 47, '合川市', 0);
INSERT INTO `dic_city` VALUES (352, '500383', '永川市', 1, 47, '永川市', 0);
INSERT INTO `dic_city` VALUES (353, '500384', '南川市', 1, 47, '南川市', 0);
INSERT INTO `dic_city` VALUES (354, '510100', '成都市', 1, 48, '成都市', 0);
INSERT INTO `dic_city` VALUES (355, '510300', '自贡市', 1, 48, '自贡市', 0);
INSERT INTO `dic_city` VALUES (356, '510400', '攀枝花市', 1, 48, '攀枝花市', 0);
INSERT INTO `dic_city` VALUES (357, '510500', '泸州市', 1, 48, '泸州市', 0);
INSERT INTO `dic_city` VALUES (358, '510600', '德阳市', 1, 48, '德阳市', 0);
INSERT INTO `dic_city` VALUES (359, '510700', '绵阳市', 1, 48, '绵阳市', 0);
INSERT INTO `dic_city` VALUES (360, '510800', '广元市', 1, 48, '广元市', 0);
INSERT INTO `dic_city` VALUES (361, '510900', '遂宁市', 1, 48, '遂宁市', 0);
INSERT INTO `dic_city` VALUES (362, '511000', '内江市', 1, 48, '内江市', 0);
INSERT INTO `dic_city` VALUES (363, '511100', '乐山市', 1, 48, '乐山市', 0);
INSERT INTO `dic_city` VALUES (364, '511300', '南充市', 1, 48, '南充市', 0);
INSERT INTO `dic_city` VALUES (365, '511400', '眉山市', 1, 48, '眉山市', 0);
INSERT INTO `dic_city` VALUES (366, '511500', '宜宾市', 1, 48, '宜宾市', 0);
INSERT INTO `dic_city` VALUES (367, '511600', '广安市', 1, 48, '广安市', 0);
INSERT INTO `dic_city` VALUES (368, '511700', '达州市', 1, 48, '达州市', 0);
INSERT INTO `dic_city` VALUES (369, '511800', '雅安市', 1, 48, '雅安市', 0);
INSERT INTO `dic_city` VALUES (370, '511900', '巴中市', 1, 48, '巴中市', 0);
INSERT INTO `dic_city` VALUES (371, '512000', '资阳市', 1, 48, '资阳市', 0);
INSERT INTO `dic_city` VALUES (372, '513200', '阿坝藏族羌族自治州', 1, 48, '阿坝藏族羌族自治州', 0);
INSERT INTO `dic_city` VALUES (373, '513300', '甘孜藏族自治州', 1, 48, '甘孜藏族自治州', 0);
INSERT INTO `dic_city` VALUES (374, '513400', '凉山彝族自治州', 1, 48, '凉山彝族自治州', 0);
INSERT INTO `dic_city` VALUES (375, '520100', '贵阳市', 1, 49, '贵阳市', 0);
INSERT INTO `dic_city` VALUES (376, '520200', '六盘水市', 1, 49, '六盘水市', 0);
INSERT INTO `dic_city` VALUES (377, '520300', '遵义市', 1, 49, '遵义市', 0);
INSERT INTO `dic_city` VALUES (378, '520400', '安顺市', 1, 49, '安顺市', 0);
INSERT INTO `dic_city` VALUES (379, '522200', '铜仁市', 1, 49, '铜仁市', 0);
INSERT INTO `dic_city` VALUES (380, '522300', '黔西南布依族苗族自治州', 1, 49, '黔西南布依族苗族自治', 0);
INSERT INTO `dic_city` VALUES (381, '522400', '毕节市', 1, 49, '毕节市', 0);
INSERT INTO `dic_city` VALUES (382, '522600', '黔东南苗族侗族自治州', 1, 49, '黔东南苗族侗族自治州', 0);
INSERT INTO `dic_city` VALUES (383, '522700', '黔南布依族苗族自治州', 1, 49, '黔南布依族苗族自治州', 0);
INSERT INTO `dic_city` VALUES (384, '530100', '昆明市', 1, 50, '昆明市', 0);
INSERT INTO `dic_city` VALUES (385, '530300', '曲靖市', 1, 50, '曲靖市', 0);
INSERT INTO `dic_city` VALUES (386, '530400', '玉溪市', 1, 50, '玉溪市', 0);
INSERT INTO `dic_city` VALUES (387, '530500', '保山市', 1, 50, '保山市', 0);
INSERT INTO `dic_city` VALUES (388, '530600', '昭通市', 1, 50, '昭通市', 0);
INSERT INTO `dic_city` VALUES (389, '530700', '丽江市', 1, 50, '丽江市', 0);
INSERT INTO `dic_city` VALUES (390, '532300', '楚雄彝族自治州', 1, 50, '楚雄彝族自治州', 0);
INSERT INTO `dic_city` VALUES (391, '532500', '红河哈尼族彝族自治州', 1, 50, '红河哈尼族彝族自治州', 0);
INSERT INTO `dic_city` VALUES (392, '532600', '文山州', 1, 50, '文山州', 0);
INSERT INTO `dic_city` VALUES (393, '532700', '思茅地区', 1, 50, '思茅地区', 0);
INSERT INTO `dic_city` VALUES (394, '532800', '西双版纳傣族自治州', 1, 50, '西双版纳傣族自治州', 0);
INSERT INTO `dic_city` VALUES (395, '532900', '大理白族自治州', 1, 50, '大理白族自治州', 0);
INSERT INTO `dic_city` VALUES (396, '533100', '德宏傣族景颇族自治州', 1, 50, '德宏傣族景颇族自治州', 0);
INSERT INTO `dic_city` VALUES (397, '533300', '怒江傈僳族自治州', 1, 50, '怒江傈僳族自治州', 0);
INSERT INTO `dic_city` VALUES (398, '533400', '迪庆州', 1, 50, '迪庆州', 0);
INSERT INTO `dic_city` VALUES (399, '533500', '临沧地区', 1, 50, '临沧地区', 0);
INSERT INTO `dic_city` VALUES (400, '540100', '拉萨市', 1, 51, '拉萨市', 0);
INSERT INTO `dic_city` VALUES (401, '542100', '昌都地区', 1, 51, '昌都地区', 0);
INSERT INTO `dic_city` VALUES (402, '542200', '山南地区', 1, 51, '山南地区', 0);
INSERT INTO `dic_city` VALUES (403, '542300', '日喀则地区', 1, 51, '日喀则地区', 0);
INSERT INTO `dic_city` VALUES (404, '542400', '那曲地区', 1, 51, '那曲地区', 0);
INSERT INTO `dic_city` VALUES (405, '542500', '阿里地区', 1, 51, '阿里地区', 0);
INSERT INTO `dic_city` VALUES (406, '542600', '林芝地区', 1, 51, '林芝地区', 0);
INSERT INTO `dic_city` VALUES (407, '610100', '西安市', 1, 52, '西安市', 0);
INSERT INTO `dic_city` VALUES (408, '610200', '铜川市', 1, 52, '铜川市', 0);
INSERT INTO `dic_city` VALUES (409, '610300', '宝鸡市', 1, 52, '宝鸡市', 0);
INSERT INTO `dic_city` VALUES (410, '610400', '咸阳市', 1, 52, '咸阳市', 0);
INSERT INTO `dic_city` VALUES (411, '610500', '渭南市', 1, 52, '渭南市', 0);
INSERT INTO `dic_city` VALUES (412, '610600', '延安市', 1, 52, '延安市', 0);
INSERT INTO `dic_city` VALUES (413, '610700', '汉中市', 1, 52, '汉中市', 0);
INSERT INTO `dic_city` VALUES (414, '610800', '榆林市', 1, 52, '榆林市', 0);
INSERT INTO `dic_city` VALUES (415, '610900', '安康市', 1, 52, '安康市', 0);
INSERT INTO `dic_city` VALUES (416, '611000', '商洛市', 1, 52, '商洛市', 0);
INSERT INTO `dic_city` VALUES (417, '620100', '兰州市', 1, 53, '兰州市', 0);
INSERT INTO `dic_city` VALUES (418, '620200', '嘉峪关市', 1, 53, '嘉峪关市', 0);
INSERT INTO `dic_city` VALUES (419, '620300', '金昌市', 1, 53, '金昌市', 0);
INSERT INTO `dic_city` VALUES (420, '620400', '白银市', 1, 53, '白银市', 0);
INSERT INTO `dic_city` VALUES (421, '620500', '天水市', 1, 53, '天水市', 0);
INSERT INTO `dic_city` VALUES (422, '620600', '武威市', 1, 53, '武威市', 0);
INSERT INTO `dic_city` VALUES (423, '620700', '张掖市', 1, 53, '张掖市', 0);
INSERT INTO `dic_city` VALUES (424, '620800', '平凉市', 1, 53, '平凉市', 0);
INSERT INTO `dic_city` VALUES (425, '620900', '酒泉市', 1, 53, '酒泉市', 0);
INSERT INTO `dic_city` VALUES (426, '621000', '庆阳市', 1, 53, '庆阳市', 0);
INSERT INTO `dic_city` VALUES (427, '622400', '定西地区', 1, 53, '定西地区', 0);
INSERT INTO `dic_city` VALUES (428, '622600', '陇南地区', 1, 53, '陇南地区', 0);
INSERT INTO `dic_city` VALUES (429, '622900', '临夏回族自治州', 1, 53, '临夏回族自治州', 0);
INSERT INTO `dic_city` VALUES (430, '623000', '甘南藏族自治州', 1, 53, '甘南藏族自治州', 0);
INSERT INTO `dic_city` VALUES (431, '630100', '西宁市', 1, 54, '西宁市', 0);
INSERT INTO `dic_city` VALUES (432, '632100', '海东地区', 1, 54, '海东地区', 0);
INSERT INTO `dic_city` VALUES (433, '632200', '海北藏族自治州', 1, 54, '海北藏族自治州', 0);
INSERT INTO `dic_city` VALUES (434, '632300', '黄南藏族自治州', 1, 54, '黄南藏族自治州', 0);
INSERT INTO `dic_city` VALUES (435, '632500', '海南藏族自治州', 1, 54, '海南藏族自治州', 0);
INSERT INTO `dic_city` VALUES (436, '632600', '果洛藏族自治州', 1, 54, '果洛藏族自治州', 0);
INSERT INTO `dic_city` VALUES (437, '632700', '玉树州', 1, 54, '玉树州', 0);
INSERT INTO `dic_city` VALUES (438, '632800', '海西州', 1, 54, '海西州', 0);
INSERT INTO `dic_city` VALUES (439, '640100', '银川市', 1, 55, '银川市', 0);
INSERT INTO `dic_city` VALUES (440, '640200', '石嘴山市', 1, 55, '石嘴山市', 0);
INSERT INTO `dic_city` VALUES (441, '640300', '吴忠市', 1, 55, '吴忠市', 0);
INSERT INTO `dic_city` VALUES (442, '640400', '固原市', 1, 55, '固原市', 0);
INSERT INTO `dic_city` VALUES (443, '640500', '中卫市', 1, 55, '中卫市', 0);
INSERT INTO `dic_city` VALUES (444, '650100', '乌鲁木齐市', 1, 56, '乌鲁木齐市', 0);
INSERT INTO `dic_city` VALUES (445, '650200', '克拉玛依市', 1, 56, '克拉玛依市', 0);
INSERT INTO `dic_city` VALUES (446, '652100', '吐鲁番地区', 1, 56, '吐鲁番地区', 0);
INSERT INTO `dic_city` VALUES (447, '652200', '哈密地区', 1, 56, '哈密地区', 0);
INSERT INTO `dic_city` VALUES (448, '652300', '昌吉回族自治州', 1, 56, '昌吉回族自治州', 0);
INSERT INTO `dic_city` VALUES (449, '652700', '博尔塔拉蒙古自治州', 1, 56, '博尔塔拉蒙古自治州', 0);
INSERT INTO `dic_city` VALUES (450, '652800', '巴音郭楞蒙古自治州', 1, 56, '巴音郭楞蒙古自治州', 0);
INSERT INTO `dic_city` VALUES (451, '652900', '阿克苏地区', 1, 56, '阿克苏地区', 0);
INSERT INTO `dic_city` VALUES (452, '653000', '克孜勒苏柯尔克孜自治州', 1, 56, '克孜勒苏柯尔克孜自治', 0);
INSERT INTO `dic_city` VALUES (453, '653100', '喀什地区', 1, 56, '喀什地区', 0);
INSERT INTO `dic_city` VALUES (454, '653200', '和田地区', 1, 56, '和田地区', 0);
INSERT INTO `dic_city` VALUES (455, '654000', '伊犁哈萨克自治州', 1, 56, '伊犁哈萨克自治州', 0);
INSERT INTO `dic_city` VALUES (456, '654200', '塔城地区', 1, 56, '塔城地区', 0);
INSERT INTO `dic_city` VALUES (457, '654300', '阿勒泰地区', 1, 56, '阿勒泰地区', 0);
INSERT INTO `dic_city` VALUES (458, '659001', '石河子市', 1, 56, '石河子市', 0);
INSERT INTO `dic_city` VALUES (459, '659002', '阿拉尔市', 1, 56, '阿拉尔市', 0);
INSERT INTO `dic_city` VALUES (460, '659003', '图木舒克市', 1, 56, '图木舒克市', 0);
INSERT INTO `dic_city` VALUES (461, '659004', '五家渠市', 1, 56, '五家渠市', 0);
INSERT INTO `dic_city` VALUES (462, '710100', '台北市', 1, 57, '台北市', 0);
INSERT INTO `dic_city` VALUES (463, '710200', '高雄市', 1, 57, '高雄市', 0);
INSERT INTO `dic_city` VALUES (464, '710300', '基隆市', 1, 57, '基隆市', 0);
INSERT INTO `dic_city` VALUES (465, '710400', '台中市', 1, 57, '台中市', 0);
INSERT INTO `dic_city` VALUES (466, '710500', '台南市', 1, 57, '台南市', 0);
INSERT INTO `dic_city` VALUES (467, '710600', '新竹市', 1, 57, '新竹市', 0);
INSERT INTO `dic_city` VALUES (468, '710700', '嘉义市', 1, 57, '嘉义市', 0);
INSERT INTO `dic_city` VALUES (469, '710801', '台北县(板桥市)', 1, 57, '台北县(板桥市)', 0);
INSERT INTO `dic_city` VALUES (470, '710802', '宜兰县(宜兰市)', 1, 57, '宜兰县(宜兰市)', 0);
INSERT INTO `dic_city` VALUES (471, '710803', '新竹县(竹北市)', 1, 57, '新竹县(竹北市)', 0);
INSERT INTO `dic_city` VALUES (472, '710804', '桃园县(桃园市)', 1, 57, '桃园县(桃园市)', 0);
INSERT INTO `dic_city` VALUES (473, '710805', '苗栗县(苗栗市)', 1, 57, '苗栗县(苗栗市)', 0);
INSERT INTO `dic_city` VALUES (474, '710806', '台中县(丰原市)', 1, 57, '台中县(丰原市)', 0);
INSERT INTO `dic_city` VALUES (475, '710807', '彰化县(彰化市)', 1, 57, '彰化县(彰化市)', 0);
INSERT INTO `dic_city` VALUES (476, '710808', '南投县(南投市)', 1, 57, '南投县(南投市)', 0);
INSERT INTO `dic_city` VALUES (477, '710809', '嘉义县(太保市)', 1, 57, '嘉义县(太保市)', 0);
INSERT INTO `dic_city` VALUES (478, '710810', '云林县(斗六市)', 1, 57, '云林县(斗六市)', 0);
INSERT INTO `dic_city` VALUES (479, '710811', '台南县(新营市)', 1, 57, '台南县(新营市)', 0);
INSERT INTO `dic_city` VALUES (480, '710812', '高雄县(凤山市)', 1, 57, '高雄县(凤山市)', 0);
INSERT INTO `dic_city` VALUES (481, '710813', '屏东县(屏东市)', 1, 57, '屏东县(屏东市)', 0);
INSERT INTO `dic_city` VALUES (482, '710814', '台东县(台东市)', 1, 57, '台东县(台东市)', 0);
INSERT INTO `dic_city` VALUES (483, '710815', '花莲县(花莲市)', 1, 57, '花莲县(花莲市)', 0);
INSERT INTO `dic_city` VALUES (484, '710816', '澎湖县(马公市)', 1, 57, '澎湖县(马公市)', 0);
INSERT INTO `dic_city` VALUES (485, '810001', '中西区', 1, 58, '中西区', 0);
INSERT INTO `dic_city` VALUES (486, '810002', '东区', 1, 58, '东区', 0);
INSERT INTO `dic_city` VALUES (487, '810003', '九龙城区', 1, 58, '九龙城区', 0);
INSERT INTO `dic_city` VALUES (488, '810004', '观塘区', 1, 58, '观塘区', 0);
INSERT INTO `dic_city` VALUES (489, '810005', '南区', 1, 58, '南区', 0);
INSERT INTO `dic_city` VALUES (490, '810006', '深水埗区', 1, 58, '深水埗区', 0);
INSERT INTO `dic_city` VALUES (491, '810007', '黄大仙区', 1, 58, '黄大仙区', 0);
INSERT INTO `dic_city` VALUES (492, '810008', '湾仔区', 1, 58, '湾仔区', 0);
INSERT INTO `dic_city` VALUES (493, '810009', '油尖旺区', 1, 58, '油尖旺区', 0);
INSERT INTO `dic_city` VALUES (494, '810010', '离岛区', 1, 58, '离岛区', 0);
INSERT INTO `dic_city` VALUES (495, '810011', '葵青区', 1, 58, '葵青区', 0);
INSERT INTO `dic_city` VALUES (496, '810012', '北区', 1, 58, '北区', 0);
INSERT INTO `dic_city` VALUES (497, '810013', '西贡区', 1, 58, '西贡区', 0);
INSERT INTO `dic_city` VALUES (498, '810014', '沙田区', 1, 58, '沙田区', 0);
INSERT INTO `dic_city` VALUES (499, '810015', '屯门区', 1, 58, '屯门区', 0);
INSERT INTO `dic_city` VALUES (500, '810016', '大埔区', 1, 58, '大埔区', 0);
INSERT INTO `dic_city` VALUES (501, '810017', '荃湾区', 1, 58, '荃湾区', 0);
INSERT INTO `dic_city` VALUES (502, '810018', '元朗区', 1, 58, '元朗区', 0);
INSERT INTO `dic_city` VALUES (503, '820001', '花地玛堂区(北区)', 1, 59, '花地玛堂区(北区)', 0);
INSERT INTO `dic_city` VALUES (504, '820002', '圣安多尼堂区(花王堂区)', 1, 59, '圣安多尼堂区(花王堂', 0);
INSERT INTO `dic_city` VALUES (505, '820003', '大堂区(中区)', 1, 59, '大堂区(中区)', 0);
INSERT INTO `dic_city` VALUES (506, '820004', '望德堂区', 1, 59, '望德堂区', 0);
INSERT INTO `dic_city` VALUES (507, '820005', '风顺堂区(圣老愣佐堂区)', 1, 59, '风顺堂区(圣老愣佐堂', 0);
INSERT INTO `dic_city` VALUES (508, '820006', '嘉模堂区(氹仔)', 1, 59, '嘉模堂区(氹仔)', 0);
INSERT INTO `dic_city` VALUES (509, '820007', '圣方济各堂区(路环)', 1, 59, '圣方济各堂区(路环)', 0);
INSERT INTO `dic_city` VALUES (510, '820008', '氹仔城', 1, 59, '氹仔城', 0);
INSERT INTO `dic_city` VALUES (511, '110101', '东城区', 2, 60, '东城区', 0);
INSERT INTO `dic_city` VALUES (512, '110102', '西城区', 2, 60, '西城区', 0);
INSERT INTO `dic_city` VALUES (513, '110103', '崇文区', 2, 60, '崇文区', 0);
INSERT INTO `dic_city` VALUES (514, '110104', '宣武区', 2, 60, '宣武区', 0);
INSERT INTO `dic_city` VALUES (515, '220104', '朝阳区', 2, 60, '朝阳区', 0);
INSERT INTO `dic_city` VALUES (516, '110106', '丰台区', 2, 60, '丰台区', 0);
INSERT INTO `dic_city` VALUES (517, '110107', '石景山区', 2, 60, '石景山区', 0);
INSERT INTO `dic_city` VALUES (518, '110108', '海淀区', 2, 60, '海淀区', 0);
INSERT INTO `dic_city` VALUES (519, '110109', '门头沟区', 2, 60, '门头沟区', 0);
INSERT INTO `dic_city` VALUES (520, '110111', '房山区', 2, 60, '房山区', 0);
INSERT INTO `dic_city` VALUES (521, '110112', '通州区', 2, 60, '通州区', 0);
INSERT INTO `dic_city` VALUES (522, '110113', '顺义区', 2, 60, '顺义区', 0);
INSERT INTO `dic_city` VALUES (523, '110114', '昌平区', 2, 60, '昌平区', 0);
INSERT INTO `dic_city` VALUES (524, '110115', '大兴区', 2, 60, '大兴区', 0);
INSERT INTO `dic_city` VALUES (525, '110116', '怀柔区', 2, 60, '怀柔区', 0);
INSERT INTO `dic_city` VALUES (526, '110117', '平谷区', 2, 60, '平谷区', 0);
INSERT INTO `dic_city` VALUES (527, '110228', '密云县', 2, 60, '密云县', 0);
INSERT INTO `dic_city` VALUES (528, '110229', '延庆县', 2, 60, '延庆县', 0);
INSERT INTO `dic_city` VALUES (529, '210102', '和平区', 2, 61, '和平区', 0);
INSERT INTO `dic_city` VALUES (530, '371312', '河东区', 2, 61, '河东区', 0);
INSERT INTO `dic_city` VALUES (531, '120103', '河西区', 2, 61, '河西区', 0);
INSERT INTO `dic_city` VALUES (532, '120104', '南开区', 2, 61, '南开区', 0);
INSERT INTO `dic_city` VALUES (533, '120105', '河北区', 2, 61, '河北区', 0);
INSERT INTO `dic_city` VALUES (534, '120106', '红桥区', 2, 61, '红桥区', 0);
INSERT INTO `dic_city` VALUES (535, '120107', '塘沽区', 2, 61, '塘沽区', 0);
INSERT INTO `dic_city` VALUES (536, '120108', '汉沽区', 2, 61, '汉沽区', 0);
INSERT INTO `dic_city` VALUES (537, '120109', '大港区', 2, 61, '大港区', 0);
INSERT INTO `dic_city` VALUES (538, '120110', '东丽区', 2, 61, '东丽区', 0);
INSERT INTO `dic_city` VALUES (539, '120111', '西青区', 2, 61, '西青区', 0);
INSERT INTO `dic_city` VALUES (540, '120112', '津南区', 2, 61, '津南区', 0);
INSERT INTO `dic_city` VALUES (541, '120113', '北辰区', 2, 61, '北辰区', 0);
INSERT INTO `dic_city` VALUES (542, '120114', '武清区', 2, 61, '武清区', 0);
INSERT INTO `dic_city` VALUES (543, '120115', '宝坻区', 2, 61, '宝坻区', 0);
INSERT INTO `dic_city` VALUES (544, '120221', '宁河县', 2, 61, '宁河县', 0);
INSERT INTO `dic_city` VALUES (545, '120223', '静海县', 2, 61, '静海县', 0);
INSERT INTO `dic_city` VALUES (546, '310101', '黄浦区', 2, 62, '黄浦区', 0);
INSERT INTO `dic_city` VALUES (547, '310103', '卢湾区', 2, 62, '卢湾区', 0);
INSERT INTO `dic_city` VALUES (548, '310104', '徐汇区', 2, 62, '徐汇区', 0);
INSERT INTO `dic_city` VALUES (549, '310105', '长宁区', 2, 62, '长宁区', 0);
INSERT INTO `dic_city` VALUES (550, '310106', '静安区', 2, 62, '静安区', 0);
INSERT INTO `dic_city` VALUES (551, '330903', '普陀区', 2, 62, '普陀区', 0);
INSERT INTO `dic_city` VALUES (552, '310108', '闸北区', 2, 62, '闸北区', 0);
INSERT INTO `dic_city` VALUES (553, '310109', '虹口区', 2, 62, '虹口区', 0);
INSERT INTO `dic_city` VALUES (554, '310110', '杨浦区', 2, 62, '杨浦区', 0);
INSERT INTO `dic_city` VALUES (555, '310112', '闵行区', 2, 62, '闵行区', 0);
INSERT INTO `dic_city` VALUES (556, '230506', '宝山区', 2, 62, '宝山区', 0);
INSERT INTO `dic_city` VALUES (557, '310114', '嘉定区', 2, 62, '嘉定区', 0);
INSERT INTO `dic_city` VALUES (558, '310115', '浦东新区', 2, 62, '浦东新区', 0);
INSERT INTO `dic_city` VALUES (559, '310116', '金山区', 2, 62, '金山区', 0);
INSERT INTO `dic_city` VALUES (560, '310117', '松江区', 2, 62, '松江区', 0);
INSERT INTO `dic_city` VALUES (561, '310118', '青浦区', 2, 62, '青浦区', 0);
INSERT INTO `dic_city` VALUES (562, '310119', '南汇区', 2, 62, '南汇区', 0);
INSERT INTO `dic_city` VALUES (563, '310120', '奉贤区', 2, 62, '奉贤区', 0);
INSERT INTO `dic_city` VALUES (564, '310230', '崇明县', 2, 62, '崇明县', 0);
INSERT INTO `dic_city` VALUES (565, '500101', '万州区', 2, 63, '万州区', 0);
INSERT INTO `dic_city` VALUES (566, '500102', '涪陵区', 2, 63, '涪陵区', 0);
INSERT INTO `dic_city` VALUES (567, '500103', '渝中区', 2, 63, '渝中区', 0);
INSERT INTO `dic_city` VALUES (568, '500104', '大渡口区', 2, 63, '大渡口区', 0);
INSERT INTO `dic_city` VALUES (569, '330205', '江北区', 2, 63, '江北区', 0);
INSERT INTO `dic_city` VALUES (570, '500106', '沙坪坝区', 2, 63, '沙坪坝区', 0);
INSERT INTO `dic_city` VALUES (571, '500107', '九龙坡区', 2, 63, '九龙坡区', 0);
INSERT INTO `dic_city` VALUES (572, '500108', '南岸区', 2, 63, '南岸区', 0);
INSERT INTO `dic_city` VALUES (573, '500109', '北碚区', 2, 63, '北碚区', 0);
INSERT INTO `dic_city` VALUES (574, '500110', '万盛区', 2, 63, '万盛区', 0);
INSERT INTO `dic_city` VALUES (575, '130802', '双桥区', 2, 63, '双桥区', 0);
INSERT INTO `dic_city` VALUES (576, '500112', '渝北区', 2, 63, '渝北区', 0);
INSERT INTO `dic_city` VALUES (577, '500113', '巴南区', 2, 63, '巴南区', 0);
INSERT INTO `dic_city` VALUES (578, '500114', '黔江区', 2, 63, '黔江区', 0);
INSERT INTO `dic_city` VALUES (579, '500115', '长寿区', 2, 63, '长寿区', 0);
INSERT INTO `dic_city` VALUES (580, '500222', '綦江县', 2, 63, '綦江县', 0);
INSERT INTO `dic_city` VALUES (581, '500223', '潼南县', 2, 63, '潼南县', 0);
INSERT INTO `dic_city` VALUES (582, '500224', '铜梁县', 2, 63, '铜梁县', 0);
INSERT INTO `dic_city` VALUES (583, '500225', '大足县', 2, 63, '大足县', 0);
INSERT INTO `dic_city` VALUES (584, '500226', '荣昌县', 2, 63, '荣昌县', 0);
INSERT INTO `dic_city` VALUES (585, '500227', '璧山县', 2, 63, '璧山县', 0);
INSERT INTO `dic_city` VALUES (586, '500228', '梁平县', 2, 63, '梁平县', 0);
INSERT INTO `dic_city` VALUES (587, '500229', '城口县', 2, 63, '城口县', 0);
INSERT INTO `dic_city` VALUES (588, '500230', '丰都县', 2, 63, '丰都县', 0);
INSERT INTO `dic_city` VALUES (589, '500231', '垫江县', 2, 63, '垫江县', 0);
INSERT INTO `dic_city` VALUES (590, '500232', '武隆县', 2, 63, '武隆县', 0);
INSERT INTO `dic_city` VALUES (591, '500233', '忠县', 2, 63, '忠县', 0);
INSERT INTO `dic_city` VALUES (592, '500234', '开县', 2, 63, '开县', 0);
INSERT INTO `dic_city` VALUES (593, '500235', '云阳县', 2, 63, '云阳县', 0);
INSERT INTO `dic_city` VALUES (594, '500236', '奉节县', 2, 63, '奉节县', 0);
INSERT INTO `dic_city` VALUES (595, '500237', '巫山县', 2, 63, '巫山县', 0);
INSERT INTO `dic_city` VALUES (596, '500238', '巫溪县', 2, 63, '巫溪县', 0);
INSERT INTO `dic_city` VALUES (597, '500240', '石柱土家族自治县', 2, 63, '石柱土家族自治县', 0);
INSERT INTO `dic_city` VALUES (598, '500241', '秀山土家族苗族自治县', 2, 63, '秀山土家族苗族自治县', 0);
INSERT INTO `dic_city` VALUES (599, '500242', '酉阳土家族苗族自治县', 2, 63, '酉阳土家族苗族自治县', 0);
INSERT INTO `dic_city` VALUES (600, '500243', '彭水苗族土家族自治县', 2, 63, '彭水苗族土家族自治县', 0);
INSERT INTO `dic_city` VALUES (601, '500381', '江津市', 2, 63, '江津市', 0);
INSERT INTO `dic_city` VALUES (602, '500382', '合川市', 2, 63, '合川市', 0);
INSERT INTO `dic_city` VALUES (603, '500383', '永川市', 2, 63, '永川市', 0);
INSERT INTO `dic_city` VALUES (604, '500384', '南川市', 2, 63, '南川市', 0);
INSERT INTO `dic_city` VALUES (605, '610116', '长安区', 2, 18, '长安区', 0);
INSERT INTO `dic_city` VALUES (607, '130703', '桥西区', 2, 18, '桥西区', 0);
INSERT INTO `dic_city` VALUES (608, '410703', '新华区', 2, 18, '新华区', 0);
INSERT INTO `dic_city` VALUES (609, '130107', '井陉矿区', 2, 18, '井陉矿区', 0);
INSERT INTO `dic_city` VALUES (610, '130108', '裕华区', 2, 18, '裕华区', 0);
INSERT INTO `dic_city` VALUES (611, '130121', '井陉县', 2, 18, '井陉县', 0);
INSERT INTO `dic_city` VALUES (612, '130123', '正定县', 2, 18, '正定县', 0);
INSERT INTO `dic_city` VALUES (613, '130124', '栾城县', 2, 18, '栾城县', 0);
INSERT INTO `dic_city` VALUES (614, '130125', '行唐县', 2, 18, '行唐县', 0);
INSERT INTO `dic_city` VALUES (615, '130126', '灵寿县', 2, 18, '灵寿县', 0);
INSERT INTO `dic_city` VALUES (616, '130127', '高邑县', 2, 18, '高邑县', 0);
INSERT INTO `dic_city` VALUES (617, '130128', '深泽县', 2, 18, '深泽县', 0);
INSERT INTO `dic_city` VALUES (618, '130129', '赞皇县', 2, 18, '赞皇县', 0);
INSERT INTO `dic_city` VALUES (619, '130130', '无极县', 2, 18, '无极县', 0);
INSERT INTO `dic_city` VALUES (620, '130131', '平山县', 2, 18, '平山县', 0);
INSERT INTO `dic_city` VALUES (621, '130132', '元氏县', 2, 18, '元氏县', 0);
INSERT INTO `dic_city` VALUES (622, '130133', '赵县', 2, 18, '赵县', 0);
INSERT INTO `dic_city` VALUES (623, '130181', '辛集市', 2, 18, '辛集市', 0);
INSERT INTO `dic_city` VALUES (624, '130182', '藁城市', 2, 18, '藁城市', 0);
INSERT INTO `dic_city` VALUES (625, '130183', '晋州市', 2, 18, '晋州市', 0);
INSERT INTO `dic_city` VALUES (626, '130184', '新乐市', 2, 18, '新乐市', 0);
INSERT INTO `dic_city` VALUES (627, '130185', '鹿泉市', 2, 18, '鹿泉市', 0);
INSERT INTO `dic_city` VALUES (628, '130202', '路南区', 2, 19, '路南区', 0);
INSERT INTO `dic_city` VALUES (629, '130203', '路北区', 2, 19, '路北区', 0);
INSERT INTO `dic_city` VALUES (630, '130204', '古冶区', 2, 19, '古冶区', 0);
INSERT INTO `dic_city` VALUES (631, '130205', '开平区', 2, 19, '开平区', 0);
INSERT INTO `dic_city` VALUES (632, '130207', '丰南区', 2, 19, '丰南区', 0);
INSERT INTO `dic_city` VALUES (633, '130208', '丰润区', 2, 19, '丰润区', 0);
INSERT INTO `dic_city` VALUES (634, '130223', '滦县', 2, 19, '滦县', 0);
INSERT INTO `dic_city` VALUES (635, '130224', '滦南县', 2, 19, '滦南县', 0);
INSERT INTO `dic_city` VALUES (636, '130225', '乐亭县', 2, 19, '乐亭县', 0);
INSERT INTO `dic_city` VALUES (637, '130227', '迁西县', 2, 19, '迁西县', 0);
INSERT INTO `dic_city` VALUES (638, '130229', '玉田县', 2, 19, '玉田县', 0);
INSERT INTO `dic_city` VALUES (639, '130230', '唐海县', 2, 19, '唐海县', 0);
INSERT INTO `dic_city` VALUES (640, '130281', '遵化市', 2, 19, '遵化市', 0);
INSERT INTO `dic_city` VALUES (641, '130283', '迁安市', 2, 19, '迁安市', 0);
INSERT INTO `dic_city` VALUES (642, '130302', '海港区', 2, 21, '海港区', 0);
INSERT INTO `dic_city` VALUES (643, '130303', '山海关区', 2, 21, '山海关区', 0);
INSERT INTO `dic_city` VALUES (644, '130304', '北戴河区', 2, 21, '北戴河区', 0);
INSERT INTO `dic_city` VALUES (645, '130321', '青龙满族自治县', 2, 21, '青龙满族自治县', 0);
INSERT INTO `dic_city` VALUES (646, '130322', '昌黎县', 2, 21, '昌黎县', 0);
INSERT INTO `dic_city` VALUES (647, '130323', '抚宁县', 2, 21, '抚宁县', 0);
INSERT INTO `dic_city` VALUES (648, '130324', '卢龙县', 2, 21, '卢龙县', 0);
INSERT INTO `dic_city` VALUES (649, '130402', '邯山区', 2, 20, '邯山区', 0);
INSERT INTO `dic_city` VALUES (650, '130403', '丛台区', 2, 20, '丛台区', 0);
INSERT INTO `dic_city` VALUES (651, '130404', '复兴区', 2, 20, '复兴区', 0);
INSERT INTO `dic_city` VALUES (652, '130406', '峰峰矿区', 2, 20, '峰峰矿区', 0);
INSERT INTO `dic_city` VALUES (653, '130421', '邯郸县', 2, 20, '邯郸县', 0);
INSERT INTO `dic_city` VALUES (654, '130423', '临漳县', 2, 20, '临漳县', 0);
INSERT INTO `dic_city` VALUES (655, '130424', '成安县', 2, 20, '成安县', 0);
INSERT INTO `dic_city` VALUES (656, '130425', '大名县', 2, 20, '大名县', 0);
INSERT INTO `dic_city` VALUES (657, '130426', '涉县', 2, 20, '涉县', 0);
INSERT INTO `dic_city` VALUES (658, '130427', '磁县', 2, 20, '磁县', 0);
INSERT INTO `dic_city` VALUES (659, '130428', '肥乡县', 2, 20, '肥乡县', 0);
INSERT INTO `dic_city` VALUES (660, '130429', '永年县', 2, 20, '永年县', 0);
INSERT INTO `dic_city` VALUES (661, '130430', '邱县', 2, 20, '邱县', 0);
INSERT INTO `dic_city` VALUES (662, '130431', '鸡泽县', 2, 20, '鸡泽县', 0);
INSERT INTO `dic_city` VALUES (663, '130432', '广平县', 2, 20, '广平县', 0);
INSERT INTO `dic_city` VALUES (664, '130433', '馆陶县', 2, 20, '馆陶县', 0);
INSERT INTO `dic_city` VALUES (665, '130434', '魏县', 2, 20, '魏县', 0);
INSERT INTO `dic_city` VALUES (666, '130435', '曲周县', 2, 20, '曲周县', 0);
INSERT INTO `dic_city` VALUES (667, '130481', '武安市', 2, 20, '武安市', 0);
INSERT INTO `dic_city` VALUES (668, '130502', '桥东区', 2, 28, '桥东区', 0);
INSERT INTO `dic_city` VALUES (669, '130503', '桥西区', 2, 28, '桥西区', 0);
INSERT INTO `dic_city` VALUES (670, '130521', '邢台县', 2, 28, '邢台县', 0);
INSERT INTO `dic_city` VALUES (671, '130522', '临城县', 2, 28, '临城县', 0);
INSERT INTO `dic_city` VALUES (672, '130523', '内丘县', 2, 28, '内丘县', 0);
INSERT INTO `dic_city` VALUES (673, '130524', '柏乡县', 2, 28, '柏乡县', 0);
INSERT INTO `dic_city` VALUES (674, '130525', '隆尧县', 2, 28, '隆尧县', 0);
INSERT INTO `dic_city` VALUES (675, '130526', '任县', 2, 28, '任县', 0);
INSERT INTO `dic_city` VALUES (676, '130527', '南和县', 2, 28, '南和县', 0);
INSERT INTO `dic_city` VALUES (677, '130528', '宁晋县', 2, 28, '宁晋县', 0);
INSERT INTO `dic_city` VALUES (678, '130529', '巨鹿县', 2, 28, '巨鹿县', 0);
INSERT INTO `dic_city` VALUES (679, '130530', '新河县', 2, 28, '新河县', 0);
INSERT INTO `dic_city` VALUES (680, '130531', '广宗县', 2, 28, '广宗县', 0);
INSERT INTO `dic_city` VALUES (681, '130532', '平乡县', 2, 28, '平乡县', 0);
INSERT INTO `dic_city` VALUES (682, '130533', '威县', 2, 28, '威县', 0);
INSERT INTO `dic_city` VALUES (683, '130534', '清河县', 2, 28, '清河县', 0);
INSERT INTO `dic_city` VALUES (684, '130535', '临西县', 2, 28, '临西县', 0);
INSERT INTO `dic_city` VALUES (685, '130581', '南宫市', 2, 28, '南宫市', 0);
INSERT INTO `dic_city` VALUES (686, '130582', '沙河市', 2, 28, '沙河市', 0);
INSERT INTO `dic_city` VALUES (687, '650104', '新市区', 2, 22, '新市区', 0);
INSERT INTO `dic_city` VALUES (688, '130603', '北市区', 2, 22, '北市区', 0);
INSERT INTO `dic_city` VALUES (689, '130604', '南市区', 2, 22, '南市区', 0);
INSERT INTO `dic_city` VALUES (690, '130621', '满城县', 2, 22, '满城县', 0);
INSERT INTO `dic_city` VALUES (691, '130622', '清苑县', 2, 22, '清苑县', 0);
INSERT INTO `dic_city` VALUES (692, '130623', '涞水县', 2, 22, '涞水县', 0);
INSERT INTO `dic_city` VALUES (693, '130624', '阜平县', 2, 22, '阜平县', 0);
INSERT INTO `dic_city` VALUES (694, '130625', '徐水县', 2, 22, '徐水县', 0);
INSERT INTO `dic_city` VALUES (695, '130626', '定兴县', 2, 22, '定兴县', 0);
INSERT INTO `dic_city` VALUES (696, '130627', '唐县', 2, 22, '唐县', 0);
INSERT INTO `dic_city` VALUES (697, '130628', '高阳县', 2, 22, '高阳县', 0);
INSERT INTO `dic_city` VALUES (698, '130629', '容城县', 2, 22, '容城县', 0);
INSERT INTO `dic_city` VALUES (699, '130630', '涞源县', 2, 22, '涞源县', 0);
INSERT INTO `dic_city` VALUES (700, '130631', '望都县', 2, 22, '望都县', 0);
INSERT INTO `dic_city` VALUES (701, '130632', '安新县', 2, 22, '安新县', 0);
INSERT INTO `dic_city` VALUES (702, '130633', '易县', 2, 22, '易县', 0);
INSERT INTO `dic_city` VALUES (703, '130634', '曲阳县', 2, 22, '曲阳县', 0);
INSERT INTO `dic_city` VALUES (704, '130635', '蠡县', 2, 22, '蠡县', 0);
INSERT INTO `dic_city` VALUES (705, '130636', '顺平县', 2, 22, '顺平县', 0);
INSERT INTO `dic_city` VALUES (706, '130637', '博野县', 2, 22, '博野县', 0);
INSERT INTO `dic_city` VALUES (707, '130638', '雄县', 2, 22, '雄县', 0);
INSERT INTO `dic_city` VALUES (708, '130681', '涿州市', 2, 22, '涿州市', 0);
INSERT INTO `dic_city` VALUES (709, '130682', '定州市', 2, 22, '定州市', 0);
INSERT INTO `dic_city` VALUES (710, '130683', '安国市', 2, 22, '安国市', 0);
INSERT INTO `dic_city` VALUES (711, '130684', '高碑店市', 2, 22, '高碑店市', 0);
INSERT INTO `dic_city` VALUES (712, '130702', '桥东区', 2, 23, '桥东区', 0);
INSERT INTO `dic_city` VALUES (713, '130703', '桥西区', 2, 23, '桥西区', 0);
INSERT INTO `dic_city` VALUES (714, '130705', '宣化区', 2, 23, '宣化区', 0);
INSERT INTO `dic_city` VALUES (715, '130706', '下花园区', 2, 23, '下花园区', 0);
INSERT INTO `dic_city` VALUES (716, '130721', '宣化县', 2, 23, '宣化县', 0);
INSERT INTO `dic_city` VALUES (717, '130722', '张北县', 2, 23, '张北县', 0);
INSERT INTO `dic_city` VALUES (718, '130723', '康保县', 2, 23, '康保县', 0);
INSERT INTO `dic_city` VALUES (719, '130724', '沽源县', 2, 23, '沽源县', 0);
INSERT INTO `dic_city` VALUES (720, '130725', '尚义县', 2, 23, '尚义县', 0);
INSERT INTO `dic_city` VALUES (721, '130726', '蔚县', 2, 23, '蔚县', 0);
INSERT INTO `dic_city` VALUES (722, '130727', '阳原县', 2, 23, '阳原县', 0);
INSERT INTO `dic_city` VALUES (723, '130728', '怀安县', 2, 23, '怀安县', 0);
INSERT INTO `dic_city` VALUES (724, '130729', '万全县', 2, 23, '万全县', 0);
INSERT INTO `dic_city` VALUES (725, '130730', '怀来县', 2, 23, '怀来县', 0);
INSERT INTO `dic_city` VALUES (726, '130731', '涿鹿县', 2, 23, '涿鹿县', 0);
INSERT INTO `dic_city` VALUES (727, '130732', '赤城县', 2, 23, '赤城县', 0);
INSERT INTO `dic_city` VALUES (728, '130733', '崇礼县', 2, 23, '崇礼县', 0);
INSERT INTO `dic_city` VALUES (729, '130802', '双桥区', 2, 24, '双桥区', 0);
INSERT INTO `dic_city` VALUES (730, '130803', '双滦区', 2, 24, '双滦区', 0);
INSERT INTO `dic_city` VALUES (731, '130804', '鹰手营子矿区', 2, 24, '鹰手营子矿区', 0);
INSERT INTO `dic_city` VALUES (732, '130821', '承德县', 2, 24, '承德县', 0);
INSERT INTO `dic_city` VALUES (733, '130822', '兴隆县', 2, 24, '兴隆县', 0);
INSERT INTO `dic_city` VALUES (734, '130823', '平泉县', 2, 24, '平泉县', 0);
INSERT INTO `dic_city` VALUES (735, '130824', '滦平县', 2, 24, '滦平县', 0);
INSERT INTO `dic_city` VALUES (736, '130825', '隆化县', 2, 24, '隆化县', 0);
INSERT INTO `dic_city` VALUES (737, '130826', '丰宁满族自治县', 2, 24, '丰宁满族自治县', 0);
INSERT INTO `dic_city` VALUES (738, '130827', '宽城满族自治县', 2, 24, '宽城满族自治县', 0);
INSERT INTO `dic_city` VALUES (739, '130828', '围场满族蒙古族自治县', 2, 24, '围场满族蒙古族自治县', 0);
INSERT INTO `dic_city` VALUES (740, '130902', '新华区', 2, 26, '新华区', 0);
INSERT INTO `dic_city` VALUES (741, '130903', '运河区', 2, 26, '运河区', 0);
INSERT INTO `dic_city` VALUES (742, '130921', '沧县', 2, 26, '沧县', 0);
INSERT INTO `dic_city` VALUES (743, '130922', '青县', 2, 26, '青县', 0);
INSERT INTO `dic_city` VALUES (744, '130923', '东光县', 2, 26, '东光县', 0);
INSERT INTO `dic_city` VALUES (745, '130924', '海兴县', 2, 26, '海兴县', 0);
INSERT INTO `dic_city` VALUES (746, '130925', '盐山县', 2, 26, '盐山县', 0);
INSERT INTO `dic_city` VALUES (747, '130926', '肃宁县', 2, 26, '肃宁县', 0);
INSERT INTO `dic_city` VALUES (748, '130927', '南皮县', 2, 26, '南皮县', 0);
INSERT INTO `dic_city` VALUES (749, '130928', '吴桥县', 2, 26, '吴桥县', 0);
INSERT INTO `dic_city` VALUES (750, '130929', '献县', 2, 26, '献县', 0);
INSERT INTO `dic_city` VALUES (751, '130930', '孟村回族自治县', 2, 26, '孟村回族自治县', 0);
INSERT INTO `dic_city` VALUES (752, '130981', '泊头市', 2, 26, '泊头市', 0);
INSERT INTO `dic_city` VALUES (753, '130982', '任丘市', 2, 26, '任丘市', 0);
INSERT INTO `dic_city` VALUES (754, '130983', '黄骅市', 2, 26, '黄骅市', 0);
INSERT INTO `dic_city` VALUES (755, '130984', '河间市', 2, 26, '河间市', 0);
INSERT INTO `dic_city` VALUES (756, '131002', '安次区', 2, 25, '安次区', 0);
INSERT INTO `dic_city` VALUES (757, '131003', '广阳区', 2, 25, '广阳区', 0);
INSERT INTO `dic_city` VALUES (758, '131022', '固安县', 2, 25, '固安县', 0);
INSERT INTO `dic_city` VALUES (759, '131023', '永清县', 2, 25, '永清县', 0);
INSERT INTO `dic_city` VALUES (760, '131024', '香河县', 2, 25, '香河县', 0);
INSERT INTO `dic_city` VALUES (761, '131025', '大城县', 2, 25, '大城县', 0);
INSERT INTO `dic_city` VALUES (762, '131026', '文安县', 2, 25, '文安县', 0);
INSERT INTO `dic_city` VALUES (763, '131028', '大厂回族自治县', 2, 25, '大厂回族自治县', 0);
INSERT INTO `dic_city` VALUES (764, '131081', '霸州市', 2, 25, '霸州市', 0);
INSERT INTO `dic_city` VALUES (765, '131082', '三河市', 2, 25, '三河市', 0);
INSERT INTO `dic_city` VALUES (766, '131102', '桃城区', 2, 27, '桃城区', 0);
INSERT INTO `dic_city` VALUES (767, '131121', '枣强县', 2, 27, '枣强县', 0);
INSERT INTO `dic_city` VALUES (768, '131122', '武邑县', 2, 27, '武邑县', 0);
INSERT INTO `dic_city` VALUES (769, '131123', '武强县', 2, 27, '武强县', 0);
INSERT INTO `dic_city` VALUES (770, '131124', '饶阳县', 2, 27, '饶阳县', 0);
INSERT INTO `dic_city` VALUES (771, '131125', '安平县', 2, 27, '安平县', 0);
INSERT INTO `dic_city` VALUES (772, '131126', '故城县', 2, 27, '故城县', 0);
INSERT INTO `dic_city` VALUES (773, '131127', '景县', 2, 27, '景县', 0);
INSERT INTO `dic_city` VALUES (774, '131128', '阜城县', 2, 27, '阜城县', 0);
INSERT INTO `dic_city` VALUES (775, '131181', '冀州市', 2, 27, '冀州市', 0);
INSERT INTO `dic_city` VALUES (776, '131182', '深州市', 2, 27, '深州市', 0);
INSERT INTO `dic_city` VALUES (777, '140105', '小店区', 2, 2, '小店区', 0);
INSERT INTO `dic_city` VALUES (778, '140106', '迎泽区', 2, 2, '迎泽区', 0);
INSERT INTO `dic_city` VALUES (779, '140107', '杏花岭区', 2, 2, '杏花岭区', 0);
INSERT INTO `dic_city` VALUES (780, '140108', '尖草坪区', 2, 2, '尖草坪区', 0);
INSERT INTO `dic_city` VALUES (781, '140109', '万柏林区', 2, 2, '万柏林区', 0);
INSERT INTO `dic_city` VALUES (782, '140110', '晋源区', 2, 2, '晋源区', 0);
INSERT INTO `dic_city` VALUES (783, '140121', '清徐县', 2, 2, '清徐县', 0);
INSERT INTO `dic_city` VALUES (784, '140122', '阳曲县', 2, 2, '阳曲县', 0);
INSERT INTO `dic_city` VALUES (785, '140123', '娄烦县', 2, 2, '娄烦县', 0);
INSERT INTO `dic_city` VALUES (786, '140181', '古交市', 2, 2, '古交市', 0);
INSERT INTO `dic_city` VALUES (787, '441502', '城区', 2, 6, '城区', 0);
INSERT INTO `dic_city` VALUES (788, '140303', '矿区', 2, 6, '矿区', 0);
INSERT INTO `dic_city` VALUES (789, '140211', '南郊区', 2, 6, '南郊区', 0);
INSERT INTO `dic_city` VALUES (790, '140212', '新荣区', 2, 6, '新荣区', 0);
INSERT INTO `dic_city` VALUES (791, '140221', '阳高县', 2, 6, '阳高县', 0);
INSERT INTO `dic_city` VALUES (792, '140222', '天镇县', 2, 6, '天镇县', 0);
INSERT INTO `dic_city` VALUES (793, '140223', '广灵县', 2, 6, '广灵县', 0);
INSERT INTO `dic_city` VALUES (794, '140224', '灵丘县', 2, 6, '灵丘县', 0);
INSERT INTO `dic_city` VALUES (795, '140225', '浑源县', 2, 6, '浑源县', 0);
INSERT INTO `dic_city` VALUES (796, '140226', '左云县', 2, 6, '左云县', 0);
INSERT INTO `dic_city` VALUES (797, '140227', '大同县', 2, 6, '大同县', 0);
INSERT INTO `dic_city` VALUES (798, '140302', '城区', 2, 9, '城区', 0);
INSERT INTO `dic_city` VALUES (799, '140303', '矿区', 2, 9, '矿区', 0);
INSERT INTO `dic_city` VALUES (800, '410711', '郊区', 2, 9, '郊区', 0);
INSERT INTO `dic_city` VALUES (801, '140321', '平定县', 2, 9, '平定县', 0);
INSERT INTO `dic_city` VALUES (802, '140322', '盂县', 2, 9, '盂县', 0);
INSERT INTO `dic_city` VALUES (803, '140402', '城区', 2, 14, '城区', 0);
INSERT INTO `dic_city` VALUES (804, '140411', '郊区', 2, 14, '郊区', 0);
INSERT INTO `dic_city` VALUES (805, '140421', '长治县', 2, 14, '长治县', 0);
INSERT INTO `dic_city` VALUES (806, '140423', '襄垣县', 2, 14, '襄垣县', 0);
INSERT INTO `dic_city` VALUES (807, '140424', '屯留县', 2, 14, '屯留县', 0);
INSERT INTO `dic_city` VALUES (808, '140425', '平顺县', 2, 14, '平顺县', 0);
INSERT INTO `dic_city` VALUES (809, '140426', '黎城县', 2, 14, '黎城县', 0);
INSERT INTO `dic_city` VALUES (810, '140427', '壶关县', 2, 14, '壶关县', 0);
INSERT INTO `dic_city` VALUES (811, '140428', '长子县', 2, 14, '长子县', 0);
INSERT INTO `dic_city` VALUES (812, '140429', '武乡县', 2, 14, '武乡县', 0);
INSERT INTO `dic_city` VALUES (813, '140430', '沁县', 2, 14, '沁县', 0);
INSERT INTO `dic_city` VALUES (814, '140431', '沁源县', 2, 14, '沁源县', 0);
INSERT INTO `dic_city` VALUES (815, '140481', '潞城市', 2, 14, '潞城市', 0);
INSERT INTO `dic_city` VALUES (816, '140502', '城区', 2, 15, '城区', 0);
INSERT INTO `dic_city` VALUES (817, '140521', '沁水县', 2, 15, '沁水县', 0);
INSERT INTO `dic_city` VALUES (818, '140522', '阳城县', 2, 15, '阳城县', 0);
INSERT INTO `dic_city` VALUES (819, '140524', '陵川县', 2, 15, '陵川县', 0);
INSERT INTO `dic_city` VALUES (820, '140525', '泽州县', 2, 15, '泽州县', 0);
INSERT INTO `dic_city` VALUES (821, '140581', '高平市', 2, 15, '高平市', 0);
INSERT INTO `dic_city` VALUES (822, '140602', '朔城区', 2, 7, '朔城区', 0);
INSERT INTO `dic_city` VALUES (823, '140603', '平鲁区', 2, 7, '平鲁区', 0);
INSERT INTO `dic_city` VALUES (824, '140621', '山阴县', 2, 7, '山阴县', 0);
INSERT INTO `dic_city` VALUES (825, '140622', '应县', 2, 7, '应县', 0);
INSERT INTO `dic_city` VALUES (826, '140623', '右玉县', 2, 7, '右玉县', 0);
INSERT INTO `dic_city` VALUES (827, '140624', '怀仁县', 2, 7, '怀仁县', 0);
INSERT INTO `dic_city` VALUES (828, '140702', '榆次区', 2, 11, '榆次区', 0);
INSERT INTO `dic_city` VALUES (829, '140721', '榆社县', 2, 11, '榆社县', 0);
INSERT INTO `dic_city` VALUES (830, '140722', '左权县', 2, 11, '左权县', 0);
INSERT INTO `dic_city` VALUES (831, '140723', '和顺县', 2, 11, '和顺县', 0);
INSERT INTO `dic_city` VALUES (832, '140724', '昔阳县', 2, 11, '昔阳县', 0);
INSERT INTO `dic_city` VALUES (833, '140725', '寿阳县', 2, 11, '寿阳县', 0);
INSERT INTO `dic_city` VALUES (834, '140726', '太谷县', 2, 11, '太谷县', 0);
INSERT INTO `dic_city` VALUES (835, '140727', '祁县', 2, 11, '祁县', 0);
INSERT INTO `dic_city` VALUES (836, '140728', '平遥县', 2, 11, '平遥县', 0);
INSERT INTO `dic_city` VALUES (837, '140729', '灵石县', 2, 11, '灵石县', 0);
INSERT INTO `dic_city` VALUES (838, '140781', '介休市', 2, 11, '介休市', 0);
INSERT INTO `dic_city` VALUES (839, '140802', '盐湖区', 2, 13, '盐湖区', 0);
INSERT INTO `dic_city` VALUES (840, '140821', '临猗县', 2, 13, '临猗县', 0);
INSERT INTO `dic_city` VALUES (841, '140822', '万荣县', 2, 13, '万荣县', 0);
INSERT INTO `dic_city` VALUES (842, '140823', '闻喜县', 2, 13, '闻喜县', 0);
INSERT INTO `dic_city` VALUES (843, '140824', '稷山县', 2, 13, '稷山县', 0);
INSERT INTO `dic_city` VALUES (844, '140825', '新绛县', 2, 13, '新绛县', 0);
INSERT INTO `dic_city` VALUES (845, '140826', '绛县', 2, 13, '绛县', 0);
INSERT INTO `dic_city` VALUES (846, '140827', '垣曲县', 2, 13, '垣曲县', 0);
INSERT INTO `dic_city` VALUES (847, '140828', '夏县', 2, 13, '夏县', 0);
INSERT INTO `dic_city` VALUES (848, '140829', '平陆县', 2, 13, '平陆县', 0);
INSERT INTO `dic_city` VALUES (849, '140830', '芮城县', 2, 13, '芮城县', 0);
INSERT INTO `dic_city` VALUES (850, '140881', '永济市', 2, 13, '永济市', 0);
INSERT INTO `dic_city` VALUES (851, '140882', '河津市', 2, 13, '河津市', 0);
INSERT INTO `dic_city` VALUES (852, '140902', '忻府区', 2, 8, '忻府区', 0);
INSERT INTO `dic_city` VALUES (853, '140921', '定襄县', 2, 8, '定襄县', 0);
INSERT INTO `dic_city` VALUES (854, '140922', '五台县', 2, 8, '五台县', 0);
INSERT INTO `dic_city` VALUES (855, '140923', '代县', 2, 8, '代县', 0);
INSERT INTO `dic_city` VALUES (856, '140924', '繁峙县', 2, 8, '繁峙县', 0);
INSERT INTO `dic_city` VALUES (857, '140925', '宁武县', 2, 8, '宁武县', 0);
INSERT INTO `dic_city` VALUES (858, '140926', '静乐县', 2, 8, '静乐县', 0);
INSERT INTO `dic_city` VALUES (859, '140927', '神池县', 2, 8, '神池县', 0);
INSERT INTO `dic_city` VALUES (860, '140928', '五寨县', 2, 8, '五寨县', 0);
INSERT INTO `dic_city` VALUES (861, '140929', '岢岚县', 2, 8, '岢岚县', 0);
INSERT INTO `dic_city` VALUES (862, '140930', '河曲县', 2, 8, '河曲县', 0);
INSERT INTO `dic_city` VALUES (863, '140931', '保德县', 2, 8, '保德县', 0);
INSERT INTO `dic_city` VALUES (864, '140932', '偏关县', 2, 8, '偏关县', 0);
INSERT INTO `dic_city` VALUES (865, '140981', '原平市', 2, 8, '原平市', 0);
INSERT INTO `dic_city` VALUES (866, '141002', '尧都区', 2, 12, '尧都区', 0);
INSERT INTO `dic_city` VALUES (867, '141021', '曲沃县', 2, 12, '曲沃县', 0);
INSERT INTO `dic_city` VALUES (868, '141022', '翼城县', 2, 12, '翼城县', 0);
INSERT INTO `dic_city` VALUES (869, '141023', '襄汾县', 2, 12, '襄汾县', 0);
INSERT INTO `dic_city` VALUES (870, '141024', '洪洞县', 2, 12, '洪洞县', 0);
INSERT INTO `dic_city` VALUES (871, '141025', '古县', 2, 12, '古县', 0);
INSERT INTO `dic_city` VALUES (872, '141026', '安泽县', 2, 12, '安泽县', 0);
INSERT INTO `dic_city` VALUES (873, '141027', '浮山县', 2, 12, '浮山县', 0);
INSERT INTO `dic_city` VALUES (874, '141028', '吉县', 2, 12, '吉县', 0);
INSERT INTO `dic_city` VALUES (875, '141029', '乡宁县', 2, 12, '乡宁县', 0);
INSERT INTO `dic_city` VALUES (876, '141030', '大宁县', 2, 12, '大宁县', 0);
INSERT INTO `dic_city` VALUES (877, '141031', '隰县', 2, 12, '隰县', 0);
INSERT INTO `dic_city` VALUES (878, '141032', '永和县', 2, 12, '永和县', 0);
INSERT INTO `dic_city` VALUES (879, '141033', '蒲县', 2, 12, '蒲县', 0);
INSERT INTO `dic_city` VALUES (880, '141034', '汾西县', 2, 12, '汾西县', 0);
INSERT INTO `dic_city` VALUES (881, '141081', '侯马市', 2, 12, '侯马市', 0);
INSERT INTO `dic_city` VALUES (882, '141082', '霍州市', 2, 12, '霍州市', 0);
INSERT INTO `dic_city` VALUES (883, '142301', '孝义市', 2, 10, '孝义市', 0);
INSERT INTO `dic_city` VALUES (884, '142302', '离石市', 2, 10, '离石市', 0);
INSERT INTO `dic_city` VALUES (885, '142303', '汾阳市', 2, 10, '汾阳市', 0);
INSERT INTO `dic_city` VALUES (886, '142322', '文水县', 2, 10, '文水县', 0);
INSERT INTO `dic_city` VALUES (887, '142323', '交城县', 2, 10, '交城县', 0);
INSERT INTO `dic_city` VALUES (888, '142325', '兴县', 2, 10, '兴县', 0);
INSERT INTO `dic_city` VALUES (889, '142326', '临县', 2, 10, '临县', 0);
INSERT INTO `dic_city` VALUES (890, '142327', '柳林县', 2, 10, '柳林县', 0);
INSERT INTO `dic_city` VALUES (891, '142328', '石楼县', 2, 10, '石楼县', 0);
INSERT INTO `dic_city` VALUES (892, '142329', '岚县', 2, 10, '岚县', 0);
INSERT INTO `dic_city` VALUES (893, '142330', '方山县', 2, 10, '方山县', 0);
INSERT INTO `dic_city` VALUES (894, '142332', '中阳县', 2, 10, '中阳县', 0);
INSERT INTO `dic_city` VALUES (895, '142333', '交口县', 2, 10, '交口县', 0);
INSERT INTO `dic_city` VALUES (896, '610102', '新城区', 2, 64, '新城区', 0);
INSERT INTO `dic_city` VALUES (897, '150103', '回民区', 2, 64, '回民区', 0);
INSERT INTO `dic_city` VALUES (898, '150104', '玉泉区', 2, 64, '玉泉区', 0);
INSERT INTO `dic_city` VALUES (899, '150105', '赛罕区', 2, 64, '赛罕区', 0);
INSERT INTO `dic_city` VALUES (900, '150121', '土默特左旗', 2, 64, '土默特左旗', 0);
INSERT INTO `dic_city` VALUES (901, '150122', '托克托县', 2, 64, '托克托县', 0);
INSERT INTO `dic_city` VALUES (902, '150123', '和林格尔县', 2, 64, '和林格尔县', 0);
INSERT INTO `dic_city` VALUES (903, '150124', '清水河县', 2, 64, '清水河县', 0);
INSERT INTO `dic_city` VALUES (904, '150125', '武川县', 2, 64, '武川县', 0);
INSERT INTO `dic_city` VALUES (905, '150202', '东河区', 2, 65, '东河区', 0);
INSERT INTO `dic_city` VALUES (906, '150203', '昆都仑区', 2, 65, '昆都仑区', 0);
INSERT INTO `dic_city` VALUES (907, '420107', '青山区', 2, 65, '青山区', 0);
INSERT INTO `dic_city` VALUES (908, '150205', '石拐区', 2, 65, '石拐区', 0);
INSERT INTO `dic_city` VALUES (909, '150206', '白云矿区', 2, 65, '白云矿区', 0);
INSERT INTO `dic_city` VALUES (910, '150207', '九原区', 2, 65, '九原区', 0);
INSERT INTO `dic_city` VALUES (911, '150221', '土默特右旗', 2, 65, '土默特右旗', 0);
INSERT INTO `dic_city` VALUES (912, '150222', '固阳县', 2, 65, '固阳县', 0);
INSERT INTO `dic_city` VALUES (913, '150223', '达尔罕茂明安联合旗', 2, 65, '达尔罕茂明安联合旗', 0);
INSERT INTO `dic_city` VALUES (914, '150302', '海勃湾区', 2, 66, '海勃湾区', 0);
INSERT INTO `dic_city` VALUES (915, '150303', '海南区', 2, 66, '海南区', 0);
INSERT INTO `dic_city` VALUES (916, '150304', '乌达区', 2, 66, '乌达区', 0);
INSERT INTO `dic_city` VALUES (917, '150402', '红山区', 2, 67, '红山区', 0);
INSERT INTO `dic_city` VALUES (918, '150403', '元宝山区', 2, 67, '元宝山区', 0);
INSERT INTO `dic_city` VALUES (919, '710104', '松山区', 2, 67, '松山区', 0);
INSERT INTO `dic_city` VALUES (920, '150421', '阿鲁科尔沁旗', 2, 67, '阿鲁科尔沁旗', 0);
INSERT INTO `dic_city` VALUES (921, '150422', '巴林左旗', 2, 67, '巴林左旗', 0);
INSERT INTO `dic_city` VALUES (922, '150423', '巴林右旗', 2, 67, '巴林右旗', 0);
INSERT INTO `dic_city` VALUES (923, '150424', '林西县', 2, 67, '林西县', 0);
INSERT INTO `dic_city` VALUES (924, '150425', '克什克腾旗', 2, 67, '克什克腾旗', 0);
INSERT INTO `dic_city` VALUES (925, '150426', '翁牛特旗', 2, 67, '翁牛特旗', 0);
INSERT INTO `dic_city` VALUES (926, '150428', '喀喇沁旗', 2, 67, '喀喇沁旗', 0);
INSERT INTO `dic_city` VALUES (927, '150429', '宁城县', 2, 67, '宁城县', 0);
INSERT INTO `dic_city` VALUES (928, '150430', '敖汉旗', 2, 67, '敖汉旗', 0);
INSERT INTO `dic_city` VALUES (929, '150502', '科尔沁区', 2, 68, '科尔沁区', 0);
INSERT INTO `dic_city` VALUES (930, '150521', '科尔沁左翼中旗', 2, 68, '科尔沁左翼中旗', 0);
INSERT INTO `dic_city` VALUES (931, '150522', '科尔沁左翼后旗', 2, 68, '科尔沁左翼后旗', 0);
INSERT INTO `dic_city` VALUES (932, '150523', '开鲁县', 2, 68, '开鲁县', 0);
INSERT INTO `dic_city` VALUES (933, '150524', '库伦旗', 2, 68, '库伦旗', 0);
INSERT INTO `dic_city` VALUES (934, '150525', '奈曼旗', 2, 68, '奈曼旗', 0);
INSERT INTO `dic_city` VALUES (935, '150526', '扎鲁特旗', 2, 68, '扎鲁特旗', 0);
INSERT INTO `dic_city` VALUES (936, '150581', '霍林郭勒市', 2, 68, '霍林郭勒市', 0);
INSERT INTO `dic_city` VALUES (937, '150602', '东胜区', 2, 69, '东胜区', 0);
INSERT INTO `dic_city` VALUES (938, '150621', '达拉特旗', 2, 69, '达拉特旗', 0);
INSERT INTO `dic_city` VALUES (939, '150622', '准格尔旗', 2, 69, '准格尔旗', 0);
INSERT INTO `dic_city` VALUES (940, '150623', '鄂托克前旗', 2, 69, '鄂托克前旗', 0);
INSERT INTO `dic_city` VALUES (941, '150624', '鄂托克旗', 2, 69, '鄂托克旗', 0);
INSERT INTO `dic_city` VALUES (942, '150625', '杭锦旗', 2, 69, '杭锦旗', 0);
INSERT INTO `dic_city` VALUES (943, '150626', '乌审旗', 2, 69, '乌审旗', 0);
INSERT INTO `dic_city` VALUES (944, '150627', '伊金霍洛旗', 2, 69, '伊金霍洛旗', 0);
INSERT INTO `dic_city` VALUES (945, '150702', '海拉尔区', 2, 70, '海拉尔区', 0);
INSERT INTO `dic_city` VALUES (946, '150721', '阿荣旗', 2, 70, '阿荣旗', 0);
INSERT INTO `dic_city` VALUES (947, '150722', '莫力达瓦达斡尔族自治旗', 2, 70, '莫力达瓦达斡尔族自治', 0);
INSERT INTO `dic_city` VALUES (948, '150723', '鄂伦春自治旗', 2, 70, '鄂伦春自治旗', 0);
INSERT INTO `dic_city` VALUES (949, '150724', '鄂温克族自治旗', 2, 70, '鄂温克族自治旗', 0);
INSERT INTO `dic_city` VALUES (950, '150725', '陈巴尔虎旗', 2, 70, '陈巴尔虎旗', 0);
INSERT INTO `dic_city` VALUES (951, '150726', '新巴尔虎左旗', 2, 70, '新巴尔虎左旗', 0);
INSERT INTO `dic_city` VALUES (952, '150727', '新巴尔虎右旗', 2, 70, '新巴尔虎右旗', 0);
INSERT INTO `dic_city` VALUES (953, '150781', '满洲里市', 2, 70, '满洲里市', 0);
INSERT INTO `dic_city` VALUES (954, '150782', '牙克石市', 2, 70, '牙克石市', 0);
INSERT INTO `dic_city` VALUES (955, '150783', '扎兰屯市', 2, 70, '扎兰屯市', 0);
INSERT INTO `dic_city` VALUES (956, '150784', '额尔古纳市', 2, 70, '额尔古纳市', 0);
INSERT INTO `dic_city` VALUES (957, '150785', '根河市', 2, 70, '根河市', 0);
INSERT INTO `dic_city` VALUES (958, '152201', '乌兰浩特市', 2, 71, '乌兰浩特市', 0);
INSERT INTO `dic_city` VALUES (959, '152202', '阿尔山市', 2, 71, '阿尔山市', 0);
INSERT INTO `dic_city` VALUES (960, '152221', '科尔沁右翼前旗', 2, 71, '科尔沁右翼前旗', 0);
INSERT INTO `dic_city` VALUES (961, '152222', '科尔沁右翼中旗', 2, 71, '科尔沁右翼中旗', 0);
INSERT INTO `dic_city` VALUES (962, '152223', '扎赉特旗', 2, 71, '扎赉特旗', 0);
INSERT INTO `dic_city` VALUES (963, '152224', '突泉县', 2, 71, '突泉县', 0);
INSERT INTO `dic_city` VALUES (964, '152501', '二连浩特市', 2, 71, '二连浩特市', 0);
INSERT INTO `dic_city` VALUES (965, '152502', '锡林浩特市', 2, 71, '锡林浩特市', 0);
INSERT INTO `dic_city` VALUES (966, '152522', '阿巴嘎旗', 2, 71, '阿巴嘎旗', 0);
INSERT INTO `dic_city` VALUES (967, '152523', '苏尼特左旗', 2, 71, '苏尼特左旗', 0);
INSERT INTO `dic_city` VALUES (968, '152524', '苏尼特右旗', 2, 71, '苏尼特右旗', 0);
INSERT INTO `dic_city` VALUES (969, '152525', '东乌珠穆沁旗', 2, 71, '东乌珠穆沁旗', 0);
INSERT INTO `dic_city` VALUES (970, '152526', '西乌珠穆沁旗', 2, 71, '西乌珠穆沁旗', 0);
INSERT INTO `dic_city` VALUES (971, '152527', '太仆寺旗', 2, 71, '太仆寺旗', 0);
INSERT INTO `dic_city` VALUES (972, '152528', '镶黄旗', 2, 71, '镶黄旗', 0);
INSERT INTO `dic_city` VALUES (973, '152529', '正镶白旗', 2, 71, '正镶白旗', 0);
INSERT INTO `dic_city` VALUES (974, '152530', '正蓝旗', 2, 71, '正蓝旗', 0);
INSERT INTO `dic_city` VALUES (975, '152531', '多伦县', 2, 71, '多伦县', 0);
INSERT INTO `dic_city` VALUES (976, '152601', '集宁市', 2, 71, '集宁市', 0);
INSERT INTO `dic_city` VALUES (977, '152602', '丰镇市', 2, 71, '丰镇市', 0);
INSERT INTO `dic_city` VALUES (978, '152624', '卓资县', 2, 71, '卓资县', 0);
INSERT INTO `dic_city` VALUES (979, '152625', '化德县', 2, 71, '化德县', 0);
INSERT INTO `dic_city` VALUES (980, '152626', '商都县', 2, 71, '商都县', 0);
INSERT INTO `dic_city` VALUES (981, '152627', '兴和县', 2, 71, '兴和县', 0);
INSERT INTO `dic_city` VALUES (982, '152629', '凉城县', 2, 71, '凉城县', 0);
INSERT INTO `dic_city` VALUES (983, '152630', '察哈尔右翼前旗', 2, 71, '察哈尔右翼前旗', 0);
INSERT INTO `dic_city` VALUES (984, '152631', '察哈尔右翼中旗', 2, 71, '察哈尔右翼中旗', 0);
INSERT INTO `dic_city` VALUES (985, '152632', '察哈尔右翼后旗', 2, 71, '察哈尔右翼后旗', 0);
INSERT INTO `dic_city` VALUES (986, '152634', '四子王旗', 2, 71, '四子王旗', 0);
INSERT INTO `dic_city` VALUES (987, '152801', '临河市', 2, 71, '临河市', 0);
INSERT INTO `dic_city` VALUES (988, '152822', '五原县', 2, 71, '五原县', 0);
INSERT INTO `dic_city` VALUES (989, '152823', '磴口县', 2, 71, '磴口县', 0);
INSERT INTO `dic_city` VALUES (990, '152824', '乌拉特前旗', 2, 71, '乌拉特前旗', 0);
INSERT INTO `dic_city` VALUES (991, '152825', '乌拉特中旗', 2, 71, '乌拉特中旗', 0);
INSERT INTO `dic_city` VALUES (992, '152826', '乌拉特后旗', 2, 71, '乌拉特后旗', 0);
INSERT INTO `dic_city` VALUES (993, '152827', '杭锦后旗', 2, 71, '杭锦后旗', 0);
INSERT INTO `dic_city` VALUES (994, '152921', '阿拉善左旗', 2, 71, '阿拉善左旗', 0);
INSERT INTO `dic_city` VALUES (995, '152922', '阿拉善右旗', 2, 71, '阿拉善右旗', 0);
INSERT INTO `dic_city` VALUES (996, '152923', '额济纳旗', 2, 71, '额济纳旗', 0);
INSERT INTO `dic_city` VALUES (997, '210102', '和平区', 2, 76, '和平区', 0);
INSERT INTO `dic_city` VALUES (998, '210103', '沈河区', 2, 76, '沈河区', 0);
INSERT INTO `dic_city` VALUES (999, '210104', '大东区', 2, 76, '大东区', 0);
INSERT INTO `dic_city` VALUES (1000, '210105', '皇姑区', 2, 76, '皇姑区', 0);
INSERT INTO `dic_city` VALUES (1001, '220302', '铁西区', 2, 76, '铁西区', 0);
INSERT INTO `dic_city` VALUES (1002, '210111', '苏家屯区', 2, 76, '苏家屯区', 0);
INSERT INTO `dic_city` VALUES (1003, '210112', '东陵区', 2, 76, '东陵区', 0);
INSERT INTO `dic_city` VALUES (1004, '210113', '新城子区', 2, 76, '新城子区', 0);
INSERT INTO `dic_city` VALUES (1005, '210114', '于洪区', 2, 76, '于洪区', 0);
INSERT INTO `dic_city` VALUES (1006, '210122', '辽中县', 2, 76, '辽中县', 0);
INSERT INTO `dic_city` VALUES (1007, '210123', '康平县', 2, 76, '康平县', 0);
INSERT INTO `dic_city` VALUES (1008, '210124', '法库县', 2, 76, '法库县', 0);
INSERT INTO `dic_city` VALUES (1009, '210181', '新民市', 2, 76, '新民市', 0);
INSERT INTO `dic_city` VALUES (1010, '710304', '中山区', 2, 77, '中山区', 0);
INSERT INTO `dic_city` VALUES (1011, '210203', '西岗区', 2, 77, '西岗区', 0);
INSERT INTO `dic_city` VALUES (1012, '210204', '沙河口区', 2, 77, '沙河口区', 0);
INSERT INTO `dic_city` VALUES (1013, '210211', '甘井子区', 2, 77, '甘井子区', 0);
INSERT INTO `dic_city` VALUES (1014, '210212', '旅顺口区', 2, 77, '旅顺口区', 0);
INSERT INTO `dic_city` VALUES (1015, '210213', '金州区', 2, 77, '金州区', 0);
INSERT INTO `dic_city` VALUES (1016, '210224', '长海县', 2, 77, '长海县', 0);
INSERT INTO `dic_city` VALUES (1017, '210281', '瓦房店市', 2, 77, '瓦房店市', 0);
INSERT INTO `dic_city` VALUES (1018, '210282', '普兰店市', 2, 77, '普兰店市', 0);
INSERT INTO `dic_city` VALUES (1019, '210283', '庄河市', 2, 77, '庄河市', 0);
INSERT INTO `dic_city` VALUES (1020, '220303', '铁东区', 2, 78, '铁东区', 0);
INSERT INTO `dic_city` VALUES (1021, '210303', '铁西区', 2, 78, '铁西区', 0);
INSERT INTO `dic_city` VALUES (1022, '210304', '立山区', 2, 78, '立山区', 0);
INSERT INTO `dic_city` VALUES (1023, '210311', '千山区', 2, 78, '千山区', 0);
INSERT INTO `dic_city` VALUES (1024, '210321', '台安县', 2, 78, '台安县', 0);
INSERT INTO `dic_city` VALUES (1025, '210323', '岫岩满族自治县', 2, 78, '岫岩满族自治县', 0);
INSERT INTO `dic_city` VALUES (1026, '210381', '海城市', 2, 78, '海城市', 0);
INSERT INTO `dic_city` VALUES (1027, '210402', '新抚区', 2, 79, '新抚区', 0);
INSERT INTO `dic_city` VALUES (1028, '210403', '东洲区', 2, 79, '东洲区', 0);
INSERT INTO `dic_city` VALUES (1029, '210404', '望花区', 2, 79, '望花区', 0);
INSERT INTO `dic_city` VALUES (1030, '210411', '顺城区', 2, 79, '顺城区', 0);
INSERT INTO `dic_city` VALUES (1031, '210421', '抚顺县', 2, 79, '抚顺县', 0);
INSERT INTO `dic_city` VALUES (1032, '210422', '新宾满族自治县', 2, 79, '新宾满族自治县', 0);
INSERT INTO `dic_city` VALUES (1033, '210423', '清原满族自治县', 2, 79, '清原满族自治县', 0);
INSERT INTO `dic_city` VALUES (1034, '210502', '平山区', 2, 80, '平山区', 0);
INSERT INTO `dic_city` VALUES (1035, '210503', '溪湖区', 2, 80, '溪湖区', 0);
INSERT INTO `dic_city` VALUES (1036, '210504', '明山区', 2, 80, '明山区', 0);
INSERT INTO `dic_city` VALUES (1037, '210505', '南芬区', 2, 80, '南芬区', 0);
INSERT INTO `dic_city` VALUES (1038, '210521', '本溪满族自治县', 2, 80, '本溪满族自治县', 0);
INSERT INTO `dic_city` VALUES (1039, '210522', '桓仁满族自治县', 2, 80, '桓仁满族自治县', 0);
INSERT INTO `dic_city` VALUES (1040, '210602', '元宝区', 2, 81, '元宝区', 0);
INSERT INTO `dic_city` VALUES (1041, '210603', '振兴区', 2, 81, '振兴区', 0);
INSERT INTO `dic_city` VALUES (1042, '210604', '振安区', 2, 81, '振安区', 0);
INSERT INTO `dic_city` VALUES (1043, '210624', '宽甸满族自治县', 2, 81, '宽甸满族自治县', 0);
INSERT INTO `dic_city` VALUES (1044, '210681', '东港市', 2, 81, '东港市', 0);
INSERT INTO `dic_city` VALUES (1045, '210682', '凤城市', 2, 81, '凤城市', 0);
INSERT INTO `dic_city` VALUES (1046, '210702', '古塔区', 2, 82, '古塔区', 0);
INSERT INTO `dic_city` VALUES (1047, '210703', '凌河区', 2, 82, '凌河区', 0);
INSERT INTO `dic_city` VALUES (1048, '210711', '太和区', 2, 82, '太和区', 0);
INSERT INTO `dic_city` VALUES (1049, '210726', '黑山县', 2, 82, '黑山县', 0);
INSERT INTO `dic_city` VALUES (1050, '210727', '义县', 2, 82, '义县', 0);
INSERT INTO `dic_city` VALUES (1051, '210781', '凌海市', 2, 82, '凌海市', 0);
INSERT INTO `dic_city` VALUES (1052, '210782', '北宁市', 2, 82, '北宁市', 0);
INSERT INTO `dic_city` VALUES (1053, '210802', '站前区', 2, 83, '站前区', 0);
INSERT INTO `dic_city` VALUES (1054, '340304', '西市区', 2, 83, '西市区', 0);
INSERT INTO `dic_city` VALUES (1055, '210804', '鲅鱼圈区', 2, 83, '鲅鱼圈区', 0);
INSERT INTO `dic_city` VALUES (1056, '210811', '老边区', 2, 83, '老边区', 0);
INSERT INTO `dic_city` VALUES (1057, '210881', '盖州市', 2, 83, '盖州市', 0);
INSERT INTO `dic_city` VALUES (1058, '210882', '大石桥市', 2, 83, '大石桥市', 0);
INSERT INTO `dic_city` VALUES (1059, '320706', '海州区', 2, 84, '海州区', 0);
INSERT INTO `dic_city` VALUES (1060, '210903', '新邱区', 2, 84, '新邱区', 0);
INSERT INTO `dic_city` VALUES (1061, '230105', '太平区', 2, 84, '太平区', 0);
INSERT INTO `dic_city` VALUES (1062, '210905', '清河门区', 2, 84, '清河门区', 0);
INSERT INTO `dic_city` VALUES (1063, '210911', '细河区', 2, 84, '细河区', 0);
INSERT INTO `dic_city` VALUES (1064, '210921', '阜新蒙古族自治县', 2, 84, '阜新蒙古族自治县', 0);
INSERT INTO `dic_city` VALUES (1065, '210922', '彰武县', 2, 84, '彰武县', 0);
INSERT INTO `dic_city` VALUES (1066, '211002', '白塔区', 2, 85, '白塔区', 0);
INSERT INTO `dic_city` VALUES (1067, '211003', '文圣区', 2, 85, '文圣区', 0);
INSERT INTO `dic_city` VALUES (1068, '211004', '宏伟区', 2, 85, '宏伟区', 0);
INSERT INTO `dic_city` VALUES (1069, '211005', '弓长岭区', 2, 85, '弓长岭区', 0);
INSERT INTO `dic_city` VALUES (1070, '211011', '太子河区', 2, 85, '太子河区', 0);
INSERT INTO `dic_city` VALUES (1071, '211021', '辽阳县', 2, 85, '辽阳县', 0);
INSERT INTO `dic_city` VALUES (1072, '211081', '灯塔市', 2, 85, '灯塔市', 0);
INSERT INTO `dic_city` VALUES (1073, '211102', '双台子区', 2, 86, '双台子区', 0);
INSERT INTO `dic_city` VALUES (1074, '211103', '兴隆台区', 2, 86, '兴隆台区', 0);
INSERT INTO `dic_city` VALUES (1075, '211121', '大洼县', 2, 86, '大洼县', 0);
INSERT INTO `dic_city` VALUES (1076, '211122', '盘山县', 2, 86, '盘山县', 0);
INSERT INTO `dic_city` VALUES (1077, '211202', '银州区', 2, 87, '银州区', 0);
INSERT INTO `dic_city` VALUES (1078, '320802', '清河区', 2, 87, '清河区', 0);
INSERT INTO `dic_city` VALUES (1079, '211221', '铁岭县', 2, 87, '铁岭县', 0);
INSERT INTO `dic_city` VALUES (1080, '211223', '西丰县', 2, 87, '西丰县', 0);
INSERT INTO `dic_city` VALUES (1081, '211224', '昌图县', 2, 87, '昌图县', 0);
INSERT INTO `dic_city` VALUES (1082, '211281', '调兵山市', 2, 87, '调兵山市', 0);
INSERT INTO `dic_city` VALUES (1083, '211282', '开原市', 2, 87, '开原市', 0);
INSERT INTO `dic_city` VALUES (1084, '211302', '双塔区', 2, 88, '双塔区', 0);
INSERT INTO `dic_city` VALUES (1085, '211303', '龙城区', 2, 88, '龙城区', 0);
INSERT INTO `dic_city` VALUES (1086, '211321', '朝阳县', 2, 88, '朝阳县', 0);
INSERT INTO `dic_city` VALUES (1087, '211322', '建平县', 2, 88, '建平县', 0);
INSERT INTO `dic_city` VALUES (1088, '211324', '喀喇沁左翼蒙古族自治县', 2, 88, '喀喇沁左翼蒙古族自治', 0);
INSERT INTO `dic_city` VALUES (1089, '211381', '北票市', 2, 88, '北票市', 0);
INSERT INTO `dic_city` VALUES (1090, '211382', '凌源市', 2, 88, '凌源市', 0);
INSERT INTO `dic_city` VALUES (1091, '211402', '连山区', 2, 89, '连山区', 0);
INSERT INTO `dic_city` VALUES (1092, '211403', '龙港区', 2, 89, '龙港区', 0);
INSERT INTO `dic_city` VALUES (1093, '211404', '南票区', 2, 89, '南票区', 0);
INSERT INTO `dic_city` VALUES (1094, '211421', '绥中县', 2, 89, '绥中县', 0);
INSERT INTO `dic_city` VALUES (1095, '211422', '建昌县', 2, 89, '建昌县', 0);
INSERT INTO `dic_city` VALUES (1096, '211481', '兴城市', 2, 89, '兴城市', 0);
INSERT INTO `dic_city` VALUES (1097, '220102', '南关区', 2, 90, '南关区', 0);
INSERT INTO `dic_city` VALUES (1098, '220103', '宽城区', 2, 90, '宽城区', 0);
INSERT INTO `dic_city` VALUES (1099, '220104', '朝阳区', 2, 90, '朝阳区', 0);
INSERT INTO `dic_city` VALUES (1100, '220105', '二道区', 2, 90, '二道区', 0);
INSERT INTO `dic_city` VALUES (1101, '220106', '绿园区', 2, 90, '绿园区', 0);
INSERT INTO `dic_city` VALUES (1102, '220112', '双阳区', 2, 90, '双阳区', 0);
INSERT INTO `dic_city` VALUES (1103, '220122', '农安县', 2, 90, '农安县', 0);
INSERT INTO `dic_city` VALUES (1104, '220181', '九台市', 2, 90, '九台市', 0);
INSERT INTO `dic_city` VALUES (1105, '220182', '榆树市', 2, 90, '榆树市', 0);
INSERT INTO `dic_city` VALUES (1106, '220183', '德惠市', 2, 90, '德惠市', 0);
INSERT INTO `dic_city` VALUES (1107, '220202', '昌邑区', 2, 91, '昌邑区', 0);
INSERT INTO `dic_city` VALUES (1108, '220203', '龙潭区', 2, 91, '龙潭区', 0);
INSERT INTO `dic_city` VALUES (1109, '220204', '船营区', 2, 91, '船营区', 0);
INSERT INTO `dic_city` VALUES (1110, '220211', '丰满区', 2, 91, '丰满区', 0);
INSERT INTO `dic_city` VALUES (1111, '220221', '永吉县', 2, 91, '永吉县', 0);
INSERT INTO `dic_city` VALUES (1112, '220281', '蛟河市', 2, 91, '蛟河市', 0);
INSERT INTO `dic_city` VALUES (1113, '220282', '桦甸市', 2, 91, '桦甸市', 0);
INSERT INTO `dic_city` VALUES (1114, '220283', '舒兰市', 2, 91, '舒兰市', 0);
INSERT INTO `dic_city` VALUES (1115, '220284', '磐石市', 2, 91, '磐石市', 0);
INSERT INTO `dic_city` VALUES (1116, '220302', '铁西区', 2, 92, '铁西区', 0);
INSERT INTO `dic_city` VALUES (1117, '220303', '铁东区', 2, 92, '铁东区', 0);
INSERT INTO `dic_city` VALUES (1118, '220322', '梨树县', 2, 92, '梨树县', 0);
INSERT INTO `dic_city` VALUES (1119, '220323', '伊通满族自治县', 2, 92, '伊通满族自治县', 0);
INSERT INTO `dic_city` VALUES (1120, '220381', '公主岭市', 2, 92, '公主岭市', 0);
INSERT INTO `dic_city` VALUES (1121, '220382', '双辽市', 2, 92, '双辽市', 0);
INSERT INTO `dic_city` VALUES (1122, '220402', '龙山区', 2, 93, '龙山区', 0);
INSERT INTO `dic_city` VALUES (1123, '231005', '西安区', 2, 93, '西安区', 0);
INSERT INTO `dic_city` VALUES (1124, '220421', '东丰县', 2, 93, '东丰县', 0);
INSERT INTO `dic_city` VALUES (1125, '220422', '东辽县', 2, 93, '东辽县', 0);
INSERT INTO `dic_city` VALUES (1126, '220502', '东昌区', 2, 94, '东昌区', 0);
INSERT INTO `dic_city` VALUES (1127, '220503', '二道江区', 2, 94, '二道江区', 0);
INSERT INTO `dic_city` VALUES (1128, '220521', '通化县', 2, 94, '通化县', 0);
INSERT INTO `dic_city` VALUES (1129, '220523', '辉南县', 2, 94, '辉南县', 0);
INSERT INTO `dic_city` VALUES (1130, '220524', '柳河县', 2, 94, '柳河县', 0);
INSERT INTO `dic_city` VALUES (1131, '220581', '梅河口市', 2, 94, '梅河口市', 0);
INSERT INTO `dic_city` VALUES (1132, '220582', '集安市', 2, 94, '集安市', 0);
INSERT INTO `dic_city` VALUES (1133, '220602', '八道江区', 2, 95, '八道江区', 0);
INSERT INTO `dic_city` VALUES (1134, '220621', '抚松县', 2, 95, '抚松县', 0);
INSERT INTO `dic_city` VALUES (1135, '220622', '靖宇县', 2, 95, '靖宇县', 0);
INSERT INTO `dic_city` VALUES (1136, '220623', '长白朝鲜族自治县', 2, 95, '长白朝鲜族自治县', 0);
INSERT INTO `dic_city` VALUES (1137, '220625', '江源县', 2, 95, '江源县', 0);
INSERT INTO `dic_city` VALUES (1138, '220681', '临江市', 2, 95, '临江市', 0);
INSERT INTO `dic_city` VALUES (1139, '220702', '宁江区', 2, 96, '宁江区', 0);
INSERT INTO `dic_city` VALUES (1140, '220721', '前郭尔罗斯蒙古族自治县', 2, 96, '前郭尔罗斯蒙古族自治', 0);
INSERT INTO `dic_city` VALUES (1141, '220722', '长岭县', 2, 96, '长岭县', 0);
INSERT INTO `dic_city` VALUES (1142, '220723', '乾安县', 2, 96, '乾安县', 0);
INSERT INTO `dic_city` VALUES (1143, '220724', '扶余县', 2, 96, '扶余县', 0);
INSERT INTO `dic_city` VALUES (1144, '220802', '洮北区', 2, 97, '洮北区', 0);
INSERT INTO `dic_city` VALUES (1145, '220821', '镇赉县', 2, 97, '镇赉县', 0);
INSERT INTO `dic_city` VALUES (1146, '220822', '通榆县', 2, 97, '通榆县', 0);
INSERT INTO `dic_city` VALUES (1147, '220881', '洮南市', 2, 97, '洮南市', 0);
INSERT INTO `dic_city` VALUES (1148, '220882', '大安市', 2, 97, '大安市', 0);
INSERT INTO `dic_city` VALUES (1149, '222401', '延吉市', 2, 98, '延吉市', 0);
INSERT INTO `dic_city` VALUES (1150, '222402', '图们市', 2, 98, '图们市', 0);
INSERT INTO `dic_city` VALUES (1151, '222403', '敦化市', 2, 98, '敦化市', 0);
INSERT INTO `dic_city` VALUES (1152, '222404', '珲春市', 2, 98, '珲春市', 0);
INSERT INTO `dic_city` VALUES (1153, '222405', '龙井市', 2, 98, '龙井市', 0);
INSERT INTO `dic_city` VALUES (1154, '222406', '和龙市', 2, 98, '和龙市', 0);
INSERT INTO `dic_city` VALUES (1155, '222424', '汪清县', 2, 98, '汪清县', 0);
INSERT INTO `dic_city` VALUES (1156, '222426', '安图县', 2, 98, '安图县', 0);
INSERT INTO `dic_city` VALUES (1157, '230102', '道里区', 2, 99, '道里区', 0);
INSERT INTO `dic_city` VALUES (1158, '230103', '南岗区', 2, 99, '南岗区', 0);
INSERT INTO `dic_city` VALUES (1159, '230104', '道外区', 2, 99, '道外区', 0);
INSERT INTO `dic_city` VALUES (1160, '230105', '太平区', 2, 99, '太平区', 0);
INSERT INTO `dic_city` VALUES (1161, '230106', '香坊区', 2, 99, '香坊区', 0);
INSERT INTO `dic_city` VALUES (1162, '230107', '动力区', 2, 99, '动力区', 0);
INSERT INTO `dic_city` VALUES (1163, '230108', '平房区', 2, 99, '平房区', 0);
INSERT INTO `dic_city` VALUES (1164, '230121', '呼兰县', 2, 99, '呼兰县', 0);
INSERT INTO `dic_city` VALUES (1165, '230123', '依兰县', 2, 99, '依兰县', 0);
INSERT INTO `dic_city` VALUES (1166, '230124', '方正县', 2, 99, '方正县', 0);
INSERT INTO `dic_city` VALUES (1167, '230125', '宾县', 2, 99, '宾县', 0);
INSERT INTO `dic_city` VALUES (1168, '230126', '巴彦县', 2, 99, '巴彦县', 0);
INSERT INTO `dic_city` VALUES (1169, '230127', '木兰县', 2, 99, '木兰县', 0);
INSERT INTO `dic_city` VALUES (1170, '230128', '通河县', 2, 99, '通河县', 0);
INSERT INTO `dic_city` VALUES (1171, '230129', '延寿县', 2, 99, '延寿县', 0);
INSERT INTO `dic_city` VALUES (1172, '230181', '阿城市', 2, 99, '阿城市', 0);
INSERT INTO `dic_city` VALUES (1173, '230182', '双城市', 2, 99, '双城市', 0);
INSERT INTO `dic_city` VALUES (1174, '230183', '尚志市', 2, 99, '尚志市', 0);
INSERT INTO `dic_city` VALUES (1175, '230184', '五常市', 2, 99, '五常市', 0);
INSERT INTO `dic_city` VALUES (1176, '230202', '龙沙区', 2, 100, '龙沙区', 0);
INSERT INTO `dic_city` VALUES (1177, '230203', '建华区', 2, 100, '建华区', 0);
INSERT INTO `dic_city` VALUES (1178, '230204', '铁锋区', 2, 100, '铁锋区', 0);
INSERT INTO `dic_city` VALUES (1179, '230205', '昂昂溪区', 2, 100, '昂昂溪区', 0);
INSERT INTO `dic_city` VALUES (1180, '230206', '富拉尔基区', 2, 100, '富拉尔基区', 0);
INSERT INTO `dic_city` VALUES (1181, '230207', '碾子山区', 2, 100, '碾子山区', 0);
INSERT INTO `dic_city` VALUES (1182, '230208', '梅里斯达斡尔族区', 2, 100, '梅里斯达斡尔族区', 0);
INSERT INTO `dic_city` VALUES (1183, '230221', '龙江县', 2, 100, '龙江县', 0);
INSERT INTO `dic_city` VALUES (1184, '230223', '依安县', 2, 100, '依安县', 0);
INSERT INTO `dic_city` VALUES (1185, '230224', '泰来县', 2, 100, '泰来县', 0);
INSERT INTO `dic_city` VALUES (1186, '230225', '甘南县', 2, 100, '甘南县', 0);
INSERT INTO `dic_city` VALUES (1187, '230227', '富裕县', 2, 100, '富裕县', 0);
INSERT INTO `dic_city` VALUES (1188, '230229', '克山县', 2, 100, '克山县', 0);
INSERT INTO `dic_city` VALUES (1189, '230230', '克东县', 2, 100, '克东县', 0);
INSERT INTO `dic_city` VALUES (1190, '230231', '拜泉县', 2, 100, '拜泉县', 0);
INSERT INTO `dic_city` VALUES (1191, '230281', '讷河市', 2, 100, '讷河市', 0);
INSERT INTO `dic_city` VALUES (1192, '230302', '鸡冠区', 2, 101, '鸡冠区', 0);
INSERT INTO `dic_city` VALUES (1193, '230303', '恒山区', 2, 101, '恒山区', 0);
INSERT INTO `dic_city` VALUES (1194, '230304', '滴道区', 2, 101, '滴道区', 0);
INSERT INTO `dic_city` VALUES (1195, '230305', '梨树区', 2, 101, '梨树区', 0);
INSERT INTO `dic_city` VALUES (1196, '230306', '城子河区', 2, 101, '城子河区', 0);
INSERT INTO `dic_city` VALUES (1197, '230307', '麻山区', 2, 101, '麻山区', 0);
INSERT INTO `dic_city` VALUES (1198, '230321', '鸡东县', 2, 101, '鸡东县', 0);
INSERT INTO `dic_city` VALUES (1199, '230381', '虎林市', 2, 101, '虎林市', 0);
INSERT INTO `dic_city` VALUES (1200, '230382', '密山市', 2, 101, '密山市', 0);
INSERT INTO `dic_city` VALUES (1201, '230803', '向阳区', 2, 102, '向阳区', 0);
INSERT INTO `dic_city` VALUES (1202, '230403', '工农区', 2, 102, '工农区', 0);
INSERT INTO `dic_city` VALUES (1203, '440305', '南山区', 2, 102, '南山区', 0);
INSERT INTO `dic_city` VALUES (1204, '230405', '兴安区', 2, 102, '兴安区', 0);
INSERT INTO `dic_city` VALUES (1205, '650108', '东山区', 2, 102, '东山区', 0);
INSERT INTO `dic_city` VALUES (1206, '230407', '兴山区', 2, 102, '兴山区', 0);
INSERT INTO `dic_city` VALUES (1207, '230421', '萝北县', 2, 102, '萝北县', 0);
INSERT INTO `dic_city` VALUES (1208, '230422', '绥滨县', 2, 102, '绥滨县', 0);
INSERT INTO `dic_city` VALUES (1209, '230502', '尖山区', 2, 103, '尖山区', 0);
INSERT INTO `dic_city` VALUES (1210, '230503', '岭东区', 2, 103, '岭东区', 0);
INSERT INTO `dic_city` VALUES (1211, '230505', '四方台区', 2, 103, '四方台区', 0);
INSERT INTO `dic_city` VALUES (1212, '230506', '宝山区', 2, 103, '宝山区', 0);
INSERT INTO `dic_city` VALUES (1213, '230521', '集贤县', 2, 103, '集贤县', 0);
INSERT INTO `dic_city` VALUES (1214, '230522', '友谊县', 2, 103, '友谊县', 0);
INSERT INTO `dic_city` VALUES (1215, '230523', '宝清县', 2, 103, '宝清县', 0);
INSERT INTO `dic_city` VALUES (1216, '230524', '饶河县', 2, 103, '饶河县', 0);
INSERT INTO `dic_city` VALUES (1217, '230602', '萨尔图区', 2, 104, '萨尔图区', 0);
INSERT INTO `dic_city` VALUES (1218, '230603', '龙凤区', 2, 104, '龙凤区', 0);
INSERT INTO `dic_city` VALUES (1219, '230604', '让胡路区', 2, 104, '让胡路区', 0);
INSERT INTO `dic_city` VALUES (1220, '230605', '红岗区', 2, 104, '红岗区', 0);
INSERT INTO `dic_city` VALUES (1221, '710102', '大同区', 2, 104, '大同区', 0);
INSERT INTO `dic_city` VALUES (1222, '230621', '肇州县', 2, 104, '肇州县', 0);
INSERT INTO `dic_city` VALUES (1223, '230622', '肇源县', 2, 104, '肇源县', 0);
INSERT INTO `dic_city` VALUES (1224, '230623', '林甸县', 2, 104, '林甸县', 0);
INSERT INTO `dic_city` VALUES (1225, '230624', '杜尔伯特蒙古族自治县', 2, 104, '杜尔伯特蒙古族自治县', 0);
INSERT INTO `dic_city` VALUES (1226, '230702', '伊春区', 2, 105, '伊春区', 0);
INSERT INTO `dic_city` VALUES (1227, '230703', '南岔区', 2, 105, '南岔区', 0);
INSERT INTO `dic_city` VALUES (1228, '230704', '友好区', 2, 105, '友好区', 0);
INSERT INTO `dic_city` VALUES (1229, '230705', '西林区', 2, 105, '西林区', 0);
INSERT INTO `dic_city` VALUES (1230, '230706', '翠峦区', 2, 105, '翠峦区', 0);
INSERT INTO `dic_city` VALUES (1231, '230707', '新青区', 2, 105, '新青区', 0);
INSERT INTO `dic_city` VALUES (1232, '230708', '美溪区', 2, 105, '美溪区', 0);
INSERT INTO `dic_city` VALUES (1233, '230709', '金山屯区', 2, 105, '金山屯区', 0);
INSERT INTO `dic_city` VALUES (1234, '230710', '五营区', 2, 105, '五营区', 0);
INSERT INTO `dic_city` VALUES (1235, '230711', '乌马河区', 2, 105, '乌马河区', 0);
INSERT INTO `dic_city` VALUES (1236, '230712', '汤旺河区', 2, 105, '汤旺河区', 0);
INSERT INTO `dic_city` VALUES (1237, '230713', '带岭区', 2, 105, '带岭区', 0);
INSERT INTO `dic_city` VALUES (1238, '230714', '乌伊岭区', 2, 105, '乌伊岭区', 0);
INSERT INTO `dic_city` VALUES (1239, '230715', '红星区', 2, 105, '红星区', 0);
INSERT INTO `dic_city` VALUES (1240, '230716', '上甘岭区', 2, 105, '上甘岭区', 0);
INSERT INTO `dic_city` VALUES (1241, '230722', '嘉荫县', 2, 105, '嘉荫县', 0);
INSERT INTO `dic_city` VALUES (1242, '230781', '铁力市', 2, 105, '铁力市', 0);
INSERT INTO `dic_city` VALUES (1243, '230802', '永红区', 2, 106, '永红区', 0);
INSERT INTO `dic_city` VALUES (1244, '230803', '向阳区', 2, 106, '向阳区', 0);
INSERT INTO `dic_city` VALUES (1245, '230804', '前进区', 2, 106, '前进区', 0);
INSERT INTO `dic_city` VALUES (1246, '230805', '东风区', 2, 106, '东风区', 0);
INSERT INTO `dic_city` VALUES (1247, '230811', '郊区', 2, 106, '郊区', 0);
INSERT INTO `dic_city` VALUES (1248, '230822', '桦南县', 2, 106, '桦南县', 0);
INSERT INTO `dic_city` VALUES (1249, '230826', '桦川县', 2, 106, '桦川县', 0);
INSERT INTO `dic_city` VALUES (1250, '230828', '汤原县', 2, 106, '汤原县', 0);
INSERT INTO `dic_city` VALUES (1251, '230833', '抚远县', 2, 106, '抚远县', 0);
INSERT INTO `dic_city` VALUES (1252, '230881', '同江市', 2, 106, '同江市', 0);
INSERT INTO `dic_city` VALUES (1253, '230882', '富锦市', 2, 106, '富锦市', 0);
INSERT INTO `dic_city` VALUES (1254, '710201', '新兴区', 2, 107, '新兴区', 0);
INSERT INTO `dic_city` VALUES (1255, '230903', '桃山区', 2, 107, '桃山区', 0);
INSERT INTO `dic_city` VALUES (1256, '230904', '茄子河区', 2, 107, '茄子河区', 0);
INSERT INTO `dic_city` VALUES (1257, '230921', '勃利县', 2, 107, '勃利县', 0);
INSERT INTO `dic_city` VALUES (1258, '231002', '东安区', 2, 108, '东安区', 0);
INSERT INTO `dic_city` VALUES (1259, '231003', '阳明区', 2, 108, '阳明区', 0);
INSERT INTO `dic_city` VALUES (1260, '231004', '爱民区', 2, 108, '爱民区', 0);
INSERT INTO `dic_city` VALUES (1261, '231005', '西安区', 2, 108, '西安区', 0);
INSERT INTO `dic_city` VALUES (1262, '231024', '东宁县', 2, 108, '东宁县', 0);
INSERT INTO `dic_city` VALUES (1263, '231025', '林口县', 2, 108, '林口县', 0);
INSERT INTO `dic_city` VALUES (1264, '231081', '绥芬河市', 2, 108, '绥芬河市', 0);
INSERT INTO `dic_city` VALUES (1265, '231083', '海林市', 2, 108, '海林市', 0);
INSERT INTO `dic_city` VALUES (1266, '231084', '宁安市', 2, 108, '宁安市', 0);
INSERT INTO `dic_city` VALUES (1267, '231085', '穆棱市', 2, 108, '穆棱市', 0);
INSERT INTO `dic_city` VALUES (1268, '231102', '爱辉区', 2, 109, '爱辉区', 0);
INSERT INTO `dic_city` VALUES (1269, '231121', '嫩江县', 2, 109, '嫩江县', 0);
INSERT INTO `dic_city` VALUES (1270, '231123', '逊克县', 2, 109, '逊克县', 0);
INSERT INTO `dic_city` VALUES (1271, '231124', '孙吴县', 2, 109, '孙吴县', 0);
INSERT INTO `dic_city` VALUES (1272, '231181', '北安市', 2, 109, '北安市', 0);
INSERT INTO `dic_city` VALUES (1273, '231182', '五大连池市', 2, 109, '五大连池市', 0);
INSERT INTO `dic_city` VALUES (1274, '231202', '北林区', 2, 110, '北林区', 0);
INSERT INTO `dic_city` VALUES (1275, '231221', '望奎县', 2, 110, '望奎县', 0);
INSERT INTO `dic_city` VALUES (1276, '231222', '兰西县', 2, 110, '兰西县', 0);
INSERT INTO `dic_city` VALUES (1277, '231223', '青冈县', 2, 110, '青冈县', 0);
INSERT INTO `dic_city` VALUES (1278, '231224', '庆安县', 2, 110, '庆安县', 0);
INSERT INTO `dic_city` VALUES (1279, '231225', '明水县', 2, 110, '明水县', 0);
INSERT INTO `dic_city` VALUES (1280, '231226', '绥棱县', 2, 110, '绥棱县', 0);
INSERT INTO `dic_city` VALUES (1281, '231281', '安达市', 2, 110, '安达市', 0);
INSERT INTO `dic_city` VALUES (1282, '231282', '肇东市', 2, 110, '肇东市', 0);
INSERT INTO `dic_city` VALUES (1283, '231283', '海伦市', 2, 110, '海伦市', 0);
INSERT INTO `dic_city` VALUES (1284, '232721', '呼玛县', 2, 111, '呼玛县', 0);
INSERT INTO `dic_city` VALUES (1285, '232722', '塔河县', 2, 111, '塔河县', 0);
INSERT INTO `dic_city` VALUES (1286, '232723', '漠河县', 2, 111, '漠河县', 0);
INSERT INTO `dic_city` VALUES (1287, '320102', '玄武区', 2, 131, '玄武区', 0);
INSERT INTO `dic_city` VALUES (1288, '320103', '白下区', 2, 131, '白下区', 0);
INSERT INTO `dic_city` VALUES (1289, '320104', '秦淮区', 2, 131, '秦淮区', 0);
INSERT INTO `dic_city` VALUES (1290, '320105', '建邺区', 2, 131, '建邺区', 0);
INSERT INTO `dic_city` VALUES (1291, '410204', '鼓楼区', 2, 131, '鼓楼区', 0);
INSERT INTO `dic_city` VALUES (1292, '320107', '下关区', 2, 131, '下关区', 0);
INSERT INTO `dic_city` VALUES (1293, '320111', '浦口区', 2, 131, '浦口区', 0);
INSERT INTO `dic_city` VALUES (1294, '320113', '栖霞区', 2, 131, '栖霞区', 0);
INSERT INTO `dic_city` VALUES (1295, '320114', '雨花台区', 2, 131, '雨花台区', 0);
INSERT INTO `dic_city` VALUES (1296, '320115', '江宁区', 2, 131, '江宁区', 0);
INSERT INTO `dic_city` VALUES (1297, '320116', '六合区', 2, 131, '六合区', 0);
INSERT INTO `dic_city` VALUES (1298, '320124', '溧水县', 2, 131, '溧水县', 0);
INSERT INTO `dic_city` VALUES (1299, '320125', '高淳县', 2, 131, '高淳县', 0);
INSERT INTO `dic_city` VALUES (1300, '320202', '崇安区', 2, 132, '崇安区', 0);
INSERT INTO `dic_city` VALUES (1301, '320203', '南长区', 2, 132, '南长区', 0);
INSERT INTO `dic_city` VALUES (1302, '320204', '北塘区', 2, 132, '北塘区', 0);
INSERT INTO `dic_city` VALUES (1303, '320205', '锡山区', 2, 132, '锡山区', 0);
INSERT INTO `dic_city` VALUES (1304, '320206', '惠山区', 2, 132, '惠山区', 0);
INSERT INTO `dic_city` VALUES (1305, '320211', '滨湖区', 2, 132, '滨湖区', 0);
INSERT INTO `dic_city` VALUES (1306, '320212', '高新区', 2, 132, '高新区', 0);
INSERT INTO `dic_city` VALUES (1307, '320281', '江阴市', 2, 132, '江阴市', 0);
INSERT INTO `dic_city` VALUES (1308, '320282', '宜兴市', 2, 132, '宜兴市', 0);
INSERT INTO `dic_city` VALUES (1309, '320302', '鼓楼区', 2, 133, '鼓楼区', 0);
INSERT INTO `dic_city` VALUES (1310, '320303', '云龙区', 2, 133, '云龙区', 0);
INSERT INTO `dic_city` VALUES (1311, '320304', '九里区', 2, 133, '九里区', 0);
INSERT INTO `dic_city` VALUES (1312, '320305', '贾汪区', 2, 133, '贾汪区', 0);
INSERT INTO `dic_city` VALUES (1313, '320311', '泉山区', 2, 133, '泉山区', 0);
INSERT INTO `dic_city` VALUES (1314, '320321', '丰县', 2, 133, '丰县', 0);
INSERT INTO `dic_city` VALUES (1315, '320322', '沛县', 2, 133, '沛县', 0);
INSERT INTO `dic_city` VALUES (1316, '320323', '铜山县', 2, 133, '铜山县', 0);
INSERT INTO `dic_city` VALUES (1317, '320324', '睢宁县', 2, 133, '睢宁县', 0);
INSERT INTO `dic_city` VALUES (1318, '320381', '新沂市', 2, 133, '新沂市', 0);
INSERT INTO `dic_city` VALUES (1319, '320382', '邳州市', 2, 133, '邳州市', 0);
INSERT INTO `dic_city` VALUES (1320, '320402', '天宁区', 2, 134, '天宁区', 0);
INSERT INTO `dic_city` VALUES (1321, '320404', '钟楼区', 2, 134, '钟楼区', 0);
INSERT INTO `dic_city` VALUES (1322, '320405', '戚墅堰区', 2, 134, '戚墅堰区', 0);
INSERT INTO `dic_city` VALUES (1323, '320411', '新北区', 2, 134, '新北区', 0);
INSERT INTO `dic_city` VALUES (1324, '320412', '武进区', 2, 134, '武进区', 0);
INSERT INTO `dic_city` VALUES (1325, '320481', '溧阳市', 2, 134, '溧阳市', 0);
INSERT INTO `dic_city` VALUES (1326, '320482', '金坛市', 2, 134, '金坛市', 0);
INSERT INTO `dic_city` VALUES (1327, '320502', '沧浪区', 2, 135, '沧浪区', 0);
INSERT INTO `dic_city` VALUES (1328, '320503', '平江区', 2, 135, '平江区', 0);
INSERT INTO `dic_city` VALUES (1329, '320504', '金阊区', 2, 135, '金阊区', 0);
INSERT INTO `dic_city` VALUES (1330, '320505', '虎丘区', 2, 135, '虎丘区', 0);
INSERT INTO `dic_city` VALUES (1331, '320506', '吴中区', 2, 135, '吴中区', 0);
INSERT INTO `dic_city` VALUES (1332, '320507', '相城区', 2, 135, '相城区', 0);
INSERT INTO `dic_city` VALUES (1333, '320581', '常熟市', 2, 135, '常熟市', 0);
INSERT INTO `dic_city` VALUES (1334, '320582', '张家港市', 2, 135, '张家港市', 0);
INSERT INTO `dic_city` VALUES (1335, '320583', '昆山市', 2, 135, '昆山市', 0);
INSERT INTO `dic_city` VALUES (1336, '320584', '吴江市', 2, 135, '吴江市', 0);
INSERT INTO `dic_city` VALUES (1337, '320585', '太仓市', 2, 135, '太仓市', 0);
INSERT INTO `dic_city` VALUES (1338, '320602', '崇川区', 2, 136, '崇川区', 0);
INSERT INTO `dic_city` VALUES (1339, '320611', '港闸区', 2, 136, '港闸区', 0);
INSERT INTO `dic_city` VALUES (1340, '320621', '海安县', 2, 136, '海安县', 0);
INSERT INTO `dic_city` VALUES (1341, '320623', '如东县', 2, 136, '如东县', 0);
INSERT INTO `dic_city` VALUES (1342, '320681', '启东市', 2, 136, '启东市', 0);
INSERT INTO `dic_city` VALUES (1343, '320682', '如皋市', 2, 136, '如皋市', 0);
INSERT INTO `dic_city` VALUES (1344, '320683', '通州市', 2, 136, '通州市', 0);
INSERT INTO `dic_city` VALUES (1345, '320684', '海门市', 2, 136, '海门市', 0);
INSERT INTO `dic_city` VALUES (1346, '320703', '连云区', 2, 137, '连云区', 0);
INSERT INTO `dic_city` VALUES (1347, '320705', '新浦区', 2, 137, '新浦区', 0);
INSERT INTO `dic_city` VALUES (1348, '320706', '海州区', 2, 137, '海州区', 0);
INSERT INTO `dic_city` VALUES (1349, '320721', '赣榆县', 2, 137, '赣榆县', 0);
INSERT INTO `dic_city` VALUES (1350, '320722', '东海县', 2, 137, '东海县', 0);
INSERT INTO `dic_city` VALUES (1351, '320723', '灌云县', 2, 137, '灌云县', 0);
INSERT INTO `dic_city` VALUES (1352, '320724', '灌南县', 2, 137, '灌南县', 0);
INSERT INTO `dic_city` VALUES (1353, '320802', '清河区', 2, 138, '清河区', 0);
INSERT INTO `dic_city` VALUES (1354, '320803', '楚州区', 2, 138, '楚州区', 0);
INSERT INTO `dic_city` VALUES (1355, '320804', '淮阴区', 2, 138, '淮阴区', 0);
INSERT INTO `dic_city` VALUES (1356, '320811', '清浦区', 2, 138, '清浦区', 0);
INSERT INTO `dic_city` VALUES (1357, '320826', '涟水县', 2, 138, '涟水县', 0);
INSERT INTO `dic_city` VALUES (1358, '320829', '洪泽县', 2, 138, '洪泽县', 0);
INSERT INTO `dic_city` VALUES (1359, '320830', '盱眙县', 2, 138, '盱眙县', 0);
INSERT INTO `dic_city` VALUES (1360, '320831', '金湖县', 2, 138, '金湖县', 0);
INSERT INTO `dic_city` VALUES (1361, '320902', '城区', 2, 139, '城区', 0);
INSERT INTO `dic_city` VALUES (1362, '320921', '响水县', 2, 139, '响水县', 0);
INSERT INTO `dic_city` VALUES (1363, '320922', '滨海县', 2, 139, '滨海县', 0);
INSERT INTO `dic_city` VALUES (1364, '320923', '阜宁县', 2, 139, '阜宁县', 0);
INSERT INTO `dic_city` VALUES (1365, '320924', '射阳县', 2, 139, '射阳县', 0);
INSERT INTO `dic_city` VALUES (1366, '320925', '建湖县', 2, 139, '建湖县', 0);
INSERT INTO `dic_city` VALUES (1367, '320928', '盐都县', 2, 139, '盐都县', 0);
INSERT INTO `dic_city` VALUES (1368, '320981', '东台市', 2, 139, '东台市', 0);
INSERT INTO `dic_city` VALUES (1369, '320982', '大丰市', 2, 139, '大丰市', 0);
INSERT INTO `dic_city` VALUES (1370, '321002', '广陵区', 2, 140, '广陵区', 0);
INSERT INTO `dic_city` VALUES (1371, '321003', '邗江区', 2, 140, '邗江区', 0);
INSERT INTO `dic_city` VALUES (1372, '321011', '维扬区', 2, 140, '维扬区', 0);
INSERT INTO `dic_city` VALUES (1373, '321023', '宝应县', 2, 140, '宝应县', 0);
INSERT INTO `dic_city` VALUES (1374, '321081', '仪征市', 2, 140, '仪征市', 0);
INSERT INTO `dic_city` VALUES (1375, '321084', '高邮市', 2, 140, '高邮市', 0);
INSERT INTO `dic_city` VALUES (1376, '321088', '江都市', 2, 140, '江都市', 0);
INSERT INTO `dic_city` VALUES (1377, '321102', '京口区', 2, 141, '京口区', 0);
INSERT INTO `dic_city` VALUES (1378, '321111', '润州区', 2, 141, '润州区', 0);
INSERT INTO `dic_city` VALUES (1379, '321112', '丹徒区', 2, 141, '丹徒区', 0);
INSERT INTO `dic_city` VALUES (1380, '321181', '丹阳市', 2, 141, '丹阳市', 0);
INSERT INTO `dic_city` VALUES (1381, '321182', '扬中市', 2, 141, '扬中市', 0);
INSERT INTO `dic_city` VALUES (1382, '321183', '句容市', 2, 141, '句容市', 0);
INSERT INTO `dic_city` VALUES (1383, '321202', '海陵区', 2, 142, '海陵区', 0);
INSERT INTO `dic_city` VALUES (1384, '321203', '高港区', 2, 142, '高港区', 0);
INSERT INTO `dic_city` VALUES (1385, '321281', '兴化市', 2, 142, '兴化市', 0);
INSERT INTO `dic_city` VALUES (1386, '321282', '靖江市', 2, 142, '靖江市', 0);
INSERT INTO `dic_city` VALUES (1387, '321283', '泰兴市', 2, 142, '泰兴市', 0);
INSERT INTO `dic_city` VALUES (1388, '321284', '姜堰市', 2, 142, '姜堰市', 0);
INSERT INTO `dic_city` VALUES (1389, '321302', '宿城区', 2, 143, '宿城区', 0);
INSERT INTO `dic_city` VALUES (1390, '321321', '宿豫县', 2, 143, '宿豫县', 0);
INSERT INTO `dic_city` VALUES (1391, '321322', '沭阳县', 2, 143, '沭阳县', 0);
INSERT INTO `dic_city` VALUES (1392, '321323', '泗阳县', 2, 143, '泗阳县', 0);
INSERT INTO `dic_city` VALUES (1393, '321324', '泗洪县', 2, 143, '泗洪县', 0);
INSERT INTO `dic_city` VALUES (1394, '330102', '上城区', 2, 144, '上城区', 0);
INSERT INTO `dic_city` VALUES (1395, '330103', '下城区', 2, 144, '下城区', 0);
INSERT INTO `dic_city` VALUES (1396, '330104', '江干区', 2, 144, '江干区', 0);
INSERT INTO `dic_city` VALUES (1397, '330105', '拱墅区', 2, 144, '拱墅区', 0);
INSERT INTO `dic_city` VALUES (1398, '360103', '西湖区', 2, 144, '西湖区', 0);
INSERT INTO `dic_city` VALUES (1399, '330108', '滨江区', 2, 144, '滨江区', 0);
INSERT INTO `dic_city` VALUES (1400, '330109', '萧山区', 2, 144, '萧山区', 0);
INSERT INTO `dic_city` VALUES (1401, '330110', '余杭区', 2, 144, '余杭区', 0);
INSERT INTO `dic_city` VALUES (1402, '330122', '桐庐县', 2, 144, '桐庐县', 0);
INSERT INTO `dic_city` VALUES (1403, '330127', '淳安县', 2, 144, '淳安县', 0);
INSERT INTO `dic_city` VALUES (1404, '330182', '建德市', 2, 144, '建德市', 0);
INSERT INTO `dic_city` VALUES (1405, '330183', '富阳市', 2, 144, '富阳市', 0);
INSERT INTO `dic_city` VALUES (1406, '330185', '临安市', 2, 144, '临安市', 0);
INSERT INTO `dic_city` VALUES (1407, '330203', '海曙区', 2, 145, '海曙区', 0);
INSERT INTO `dic_city` VALUES (1408, '330204', '江东区', 2, 145, '江东区', 0);
INSERT INTO `dic_city` VALUES (1409, '330205', '江北区', 2, 145, '江北区', 0);
INSERT INTO `dic_city` VALUES (1410, '330206', '北仑区', 2, 145, '北仑区', 0);
INSERT INTO `dic_city` VALUES (1411, '330211', '镇海区', 2, 145, '镇海区', 0);
INSERT INTO `dic_city` VALUES (1412, '330212', '鄞州区', 2, 145, '鄞州区', 0);
INSERT INTO `dic_city` VALUES (1413, '330225', '象山县', 2, 145, '象山县', 0);
INSERT INTO `dic_city` VALUES (1414, '330226', '宁海县', 2, 145, '宁海县', 0);
INSERT INTO `dic_city` VALUES (1415, '330281', '余姚市', 2, 145, '余姚市', 0);
INSERT INTO `dic_city` VALUES (1416, '330282', '慈溪市', 2, 145, '慈溪市', 0);
INSERT INTO `dic_city` VALUES (1417, '330283', '奉化市', 2, 145, '奉化市', 0);
INSERT INTO `dic_city` VALUES (1418, '330302', '鹿城区', 2, 146, '鹿城区', 0);
INSERT INTO `dic_city` VALUES (1419, '330303', '龙湾区', 2, 146, '龙湾区', 0);
INSERT INTO `dic_city` VALUES (1420, '330304', '瓯海区', 2, 146, '瓯海区', 0);
INSERT INTO `dic_city` VALUES (1421, '330322', '洞头县', 2, 146, '洞头县', 0);
INSERT INTO `dic_city` VALUES (1422, '330324', '永嘉县', 2, 146, '永嘉县', 0);
INSERT INTO `dic_city` VALUES (1423, '330326', '平阳县', 2, 146, '平阳县', 0);
INSERT INTO `dic_city` VALUES (1424, '330327', '苍南县', 2, 146, '苍南县', 0);
INSERT INTO `dic_city` VALUES (1425, '330328', '文成县', 2, 146, '文成县', 0);
INSERT INTO `dic_city` VALUES (1426, '330329', '泰顺县', 2, 146, '泰顺县', 0);
INSERT INTO `dic_city` VALUES (1427, '330381', '瑞安市', 2, 146, '瑞安市', 0);
INSERT INTO `dic_city` VALUES (1428, '330382', '乐清市', 2, 146, '乐清市', 0);
INSERT INTO `dic_city` VALUES (1429, '330402', '秀城区', 2, 147, '秀城区', 0);
INSERT INTO `dic_city` VALUES (1430, '330411', '秀洲区', 2, 147, '秀洲区', 0);
INSERT INTO `dic_city` VALUES (1431, '330421', '嘉善县', 2, 147, '嘉善县', 0);
INSERT INTO `dic_city` VALUES (1432, '330424', '海盐县', 2, 147, '海盐县', 0);
INSERT INTO `dic_city` VALUES (1433, '330481', '海宁市', 2, 147, '海宁市', 0);
INSERT INTO `dic_city` VALUES (1434, '330482', '平湖市', 2, 147, '平湖市', 0);
INSERT INTO `dic_city` VALUES (1435, '330483', '桐乡市', 2, 147, '桐乡市', 0);
INSERT INTO `dic_city` VALUES (1436, '330521', '德清县', 2, 148, '德清县', 0);
INSERT INTO `dic_city` VALUES (1437, '330522', '长兴县', 2, 148, '长兴县', 0);
INSERT INTO `dic_city` VALUES (1438, '330523', '安吉县', 2, 148, '安吉县', 0);
INSERT INTO `dic_city` VALUES (1439, '330602', '越城区', 2, 149, '越城区', 0);
INSERT INTO `dic_city` VALUES (1440, '330621', '绍兴县', 2, 149, '绍兴县', 0);
INSERT INTO `dic_city` VALUES (1441, '330624', '新昌县', 2, 149, '新昌县', 0);
INSERT INTO `dic_city` VALUES (1442, '330681', '诸暨市', 2, 149, '诸暨市', 0);
INSERT INTO `dic_city` VALUES (1443, '330682', '上虞市', 2, 149, '上虞市', 0);
INSERT INTO `dic_city` VALUES (1444, '330683', '嵊州市', 2, 149, '嵊州市', 0);
INSERT INTO `dic_city` VALUES (1445, '330702', '婺城区', 2, 150, '婺城区', 0);
INSERT INTO `dic_city` VALUES (1446, '330703', '金东区', 2, 150, '金东区', 0);
INSERT INTO `dic_city` VALUES (1447, '330723', '武义县', 2, 150, '武义县', 0);
INSERT INTO `dic_city` VALUES (1448, '330726', '浦江县', 2, 150, '浦江县', 0);
INSERT INTO `dic_city` VALUES (1449, '330727', '磐安县', 2, 150, '磐安县', 0);
INSERT INTO `dic_city` VALUES (1450, '330781', '兰溪市', 2, 150, '兰溪市', 0);
INSERT INTO `dic_city` VALUES (1451, '330782', '义乌市', 2, 150, '义乌市', 0);
INSERT INTO `dic_city` VALUES (1452, '330783', '东阳市', 2, 150, '东阳市', 0);
INSERT INTO `dic_city` VALUES (1453, '330784', '永康市', 2, 150, '永康市', 0);
INSERT INTO `dic_city` VALUES (1454, '330802', '柯城区', 2, 151, '柯城区', 0);
INSERT INTO `dic_city` VALUES (1455, '330803', '衢江区', 2, 151, '衢江区', 0);
INSERT INTO `dic_city` VALUES (1456, '330822', '常山县', 2, 151, '常山县', 0);
INSERT INTO `dic_city` VALUES (1457, '330824', '开化县', 2, 151, '开化县', 0);
INSERT INTO `dic_city` VALUES (1458, '330825', '龙游县', 2, 151, '龙游县', 0);
INSERT INTO `dic_city` VALUES (1459, '330881', '江山市', 2, 151, '江山市', 0);
INSERT INTO `dic_city` VALUES (1460, '330902', '定海区', 2, 152, '定海区', 0);
INSERT INTO `dic_city` VALUES (1461, '330903', '普陀区', 2, 152, '普陀区', 0);
INSERT INTO `dic_city` VALUES (1462, '330921', '岱山县', 2, 152, '岱山县', 0);
INSERT INTO `dic_city` VALUES (1463, '330922', '嵊泗县', 2, 152, '嵊泗县', 0);
INSERT INTO `dic_city` VALUES (1464, '331002', '椒江区', 2, 153, '椒江区', 0);
INSERT INTO `dic_city` VALUES (1465, '331003', '黄岩区', 2, 153, '黄岩区', 0);
INSERT INTO `dic_city` VALUES (1466, '331004', '路桥区', 2, 153, '路桥区', 0);
INSERT INTO `dic_city` VALUES (1467, '331021', '玉环县', 2, 153, '玉环县', 0);
INSERT INTO `dic_city` VALUES (1468, '331022', '三门县', 2, 153, '三门县', 0);
INSERT INTO `dic_city` VALUES (1469, '331023', '天台县', 2, 153, '天台县', 0);
INSERT INTO `dic_city` VALUES (1470, '331024', '仙居县', 2, 153, '仙居县', 0);
INSERT INTO `dic_city` VALUES (1471, '331081', '温岭市', 2, 153, '温岭市', 0);
INSERT INTO `dic_city` VALUES (1472, '331082', '临海市', 2, 153, '临海市', 0);
INSERT INTO `dic_city` VALUES (1473, '331102', '莲都区', 2, 154, '莲都区', 0);
INSERT INTO `dic_city` VALUES (1474, '331121', '青田县', 2, 154, '青田县', 0);
INSERT INTO `dic_city` VALUES (1475, '331122', '缙云县', 2, 154, '缙云县', 0);
INSERT INTO `dic_city` VALUES (1476, '331123', '遂昌县', 2, 154, '遂昌县', 0);
INSERT INTO `dic_city` VALUES (1477, '331124', '松阳县', 2, 154, '松阳县', 0);
INSERT INTO `dic_city` VALUES (1478, '331125', '云和县', 2, 154, '云和县', 0);
INSERT INTO `dic_city` VALUES (1479, '331126', '庆元县', 2, 154, '庆元县', 0);
INSERT INTO `dic_city` VALUES (1480, '331127', '景宁畲族自治县', 2, 154, '景宁畲族自治县', 0);
INSERT INTO `dic_city` VALUES (1481, '331181', '龙泉市', 2, 154, '龙泉市', 0);
INSERT INTO `dic_city` VALUES (1482, '340102', '瑶海区', 2, 155, '瑶海区', 0);
INSERT INTO `dic_city` VALUES (1483, '340103', '庐阳区', 2, 155, '庐阳区', 0);
INSERT INTO `dic_city` VALUES (1484, '340104', '蜀山区', 2, 155, '蜀山区', 0);
INSERT INTO `dic_city` VALUES (1485, '340111', '包河区', 2, 155, '包河区', 0);
INSERT INTO `dic_city` VALUES (1486, '340121', '长丰县', 2, 155, '长丰县', 0);
INSERT INTO `dic_city` VALUES (1487, '340122', '肥东县', 2, 155, '肥东县', 0);
INSERT INTO `dic_city` VALUES (1488, '340123', '肥西县', 2, 155, '肥西县', 0);
INSERT INTO `dic_city` VALUES (1489, '340202', '镜湖区', 2, 156, '镜湖区', 0);
INSERT INTO `dic_city` VALUES (1490, '340203', '马塘区', 2, 156, '马塘区', 0);
INSERT INTO `dic_city` VALUES (1491, '340204', '新芜区', 2, 156, '新芜区', 0);
INSERT INTO `dic_city` VALUES (1492, '340207', '鸠江区', 2, 156, '鸠江区', 0);
INSERT INTO `dic_city` VALUES (1493, '340221', '芜湖县', 2, 156, '芜湖县', 0);
INSERT INTO `dic_city` VALUES (1494, '340222', '繁昌县', 2, 156, '繁昌县', 0);
INSERT INTO `dic_city` VALUES (1495, '340223', '南陵县', 2, 156, '南陵县', 0);
INSERT INTO `dic_city` VALUES (1496, '340302', '东市区', 2, 157, '东市区', 0);
INSERT INTO `dic_city` VALUES (1497, '340303', '中市区', 2, 157, '中市区', 0);
INSERT INTO `dic_city` VALUES (1498, '340304', '西市区', 2, 157, '西市区', 0);
INSERT INTO `dic_city` VALUES (1499, '340311', '郊区', 2, 157, '郊区', 0);
INSERT INTO `dic_city` VALUES (1500, '340321', '怀远县', 2, 157, '怀远县', 0);
INSERT INTO `dic_city` VALUES (1501, '340322', '五河县', 2, 157, '五河县', 0);
INSERT INTO `dic_city` VALUES (1502, '340323', '固镇县', 2, 157, '固镇县', 0);
INSERT INTO `dic_city` VALUES (1503, '340402', '大通区', 2, 158, '大通区', 0);
INSERT INTO `dic_city` VALUES (1504, '340403', '田家庵区', 2, 158, '田家庵区', 0);
INSERT INTO `dic_city` VALUES (1505, '340404', '谢家集区', 2, 158, '谢家集区', 0);
INSERT INTO `dic_city` VALUES (1506, '340405', '八公山区', 2, 158, '八公山区', 0);
INSERT INTO `dic_city` VALUES (1507, '340406', '潘集区', 2, 158, '潘集区', 0);
INSERT INTO `dic_city` VALUES (1508, '340421', '凤台县', 2, 158, '凤台县', 0);
INSERT INTO `dic_city` VALUES (1509, '340502', '金家庄区', 2, 159, '金家庄区', 0);
INSERT INTO `dic_city` VALUES (1510, '340503', '花山区', 2, 159, '花山区', 0);
INSERT INTO `dic_city` VALUES (1511, '340504', '雨山区', 2, 159, '雨山区', 0);
INSERT INTO `dic_city` VALUES (1512, '340521', '当涂县', 2, 159, '当涂县', 0);
INSERT INTO `dic_city` VALUES (1513, '340602', '杜集区', 2, 160, '杜集区', 0);
INSERT INTO `dic_city` VALUES (1514, '340603', '相山区', 2, 160, '相山区', 0);
INSERT INTO `dic_city` VALUES (1515, '340604', '烈山区', 2, 160, '烈山区', 0);
INSERT INTO `dic_city` VALUES (1516, '340621', '濉溪县', 2, 160, '濉溪县', 0);
INSERT INTO `dic_city` VALUES (1517, '340702', '铜官山区', 2, 161, '铜官山区', 0);
INSERT INTO `dic_city` VALUES (1518, '340703', '狮子山区', 2, 161, '狮子山区', 0);
INSERT INTO `dic_city` VALUES (1519, '340711', '郊区', 2, 161, '郊区', 0);
INSERT INTO `dic_city` VALUES (1520, '340721', '铜陵县', 2, 161, '铜陵县', 0);
INSERT INTO `dic_city` VALUES (1521, '340802', '迎江区', 2, 162, '迎江区', 0);
INSERT INTO `dic_city` VALUES (1522, '340803', '大观区', 2, 162, '大观区', 0);
INSERT INTO `dic_city` VALUES (1523, '340811', '郊区', 2, 162, '郊区', 0);
INSERT INTO `dic_city` VALUES (1524, '340822', '怀宁县', 2, 162, '怀宁县', 0);
INSERT INTO `dic_city` VALUES (1525, '340823', '枞阳县', 2, 162, '枞阳县', 0);
INSERT INTO `dic_city` VALUES (1526, '340824', '潜山县', 2, 162, '潜山县', 0);
INSERT INTO `dic_city` VALUES (1527, '340825', '太湖县', 2, 162, '太湖县', 0);
INSERT INTO `dic_city` VALUES (1528, '340826', '宿松县', 2, 162, '宿松县', 0);
INSERT INTO `dic_city` VALUES (1529, '340827', '望江县', 2, 162, '望江县', 0);
INSERT INTO `dic_city` VALUES (1530, '340828', '岳西县', 2, 162, '岳西县', 0);
INSERT INTO `dic_city` VALUES (1531, '340881', '桐城市', 2, 162, '桐城市', 0);
INSERT INTO `dic_city` VALUES (1532, '341002', '屯溪区', 2, 163, '屯溪区', 0);
INSERT INTO `dic_city` VALUES (1533, '341003', '黄山区', 2, 163, '黄山区', 0);
INSERT INTO `dic_city` VALUES (1534, '341004', '徽州区', 2, 163, '徽州区', 0);
INSERT INTO `dic_city` VALUES (1535, '341021', '歙县', 2, 163, '歙县', 0);
INSERT INTO `dic_city` VALUES (1536, '341022', '休宁县', 2, 163, '休宁县', 0);
INSERT INTO `dic_city` VALUES (1537, '341023', '黟县', 2, 163, '黟县', 0);
INSERT INTO `dic_city` VALUES (1538, '341024', '祁门县', 2, 163, '祁门县', 0);
INSERT INTO `dic_city` VALUES (1539, '341102', '琅琊区', 2, 164, '琅琊区', 0);
INSERT INTO `dic_city` VALUES (1540, '341103', '南谯区', 2, 164, '南谯区', 0);
INSERT INTO `dic_city` VALUES (1541, '341122', '来安县', 2, 164, '来安县', 0);
INSERT INTO `dic_city` VALUES (1542, '341124', '全椒县', 2, 164, '全椒县', 0);
INSERT INTO `dic_city` VALUES (1543, '341125', '定远县', 2, 164, '定远县', 0);
INSERT INTO `dic_city` VALUES (1544, '341126', '凤阳县', 2, 164, '凤阳县', 0);
INSERT INTO `dic_city` VALUES (1545, '341181', '天长市', 2, 164, '天长市', 0);
INSERT INTO `dic_city` VALUES (1546, '341182', '明光市', 2, 164, '明光市', 0);
INSERT INTO `dic_city` VALUES (1547, '341202', '颍州区', 2, 165, '颍州区', 0);
INSERT INTO `dic_city` VALUES (1548, '341203', '颍东区', 2, 165, '颍东区', 0);
INSERT INTO `dic_city` VALUES (1549, '341204', '颍泉区', 2, 165, '颍泉区', 0);
INSERT INTO `dic_city` VALUES (1550, '341221', '临泉县', 2, 165, '临泉县', 0);
INSERT INTO `dic_city` VALUES (1551, '341222', '太和县', 2, 165, '太和县', 0);
INSERT INTO `dic_city` VALUES (1552, '341225', '阜南县', 2, 165, '阜南县', 0);
INSERT INTO `dic_city` VALUES (1553, '341226', '颍上县', 2, 165, '颍上县', 0);
INSERT INTO `dic_city` VALUES (1554, '341282', '界首市', 2, 165, '界首市', 0);
INSERT INTO `dic_city` VALUES (1555, '341302', '埇桥区', 2, 166, '埇桥区', 0);
INSERT INTO `dic_city` VALUES (1556, '341321', '砀山县', 2, 166, '砀山县', 0);
INSERT INTO `dic_city` VALUES (1557, '341322', '萧县', 2, 166, '萧县', 0);
INSERT INTO `dic_city` VALUES (1558, '341323', '灵璧县', 2, 166, '灵璧县', 0);
INSERT INTO `dic_city` VALUES (1559, '341324', '泗县', 2, 166, '泗县', 0);
INSERT INTO `dic_city` VALUES (1560, '341402', '居巢区', 2, 167, '居巢区', 0);
INSERT INTO `dic_city` VALUES (1561, '341421', '庐江县', 2, 167, '庐江县', 0);
INSERT INTO `dic_city` VALUES (1562, '341422', '无为县', 2, 167, '无为县', 0);
INSERT INTO `dic_city` VALUES (1563, '341423', '含山县', 2, 167, '含山县', 0);
INSERT INTO `dic_city` VALUES (1564, '341424', '和县', 2, 167, '和县', 0);
INSERT INTO `dic_city` VALUES (1565, '341502', '金安区', 2, 168, '金安区', 0);
INSERT INTO `dic_city` VALUES (1566, '341503', '裕安区', 2, 168, '裕安区', 0);
INSERT INTO `dic_city` VALUES (1567, '341521', '寿县', 2, 168, '寿县', 0);
INSERT INTO `dic_city` VALUES (1568, '341522', '霍邱县', 2, 168, '霍邱县', 0);
INSERT INTO `dic_city` VALUES (1569, '341523', '舒城县', 2, 168, '舒城县', 0);
INSERT INTO `dic_city` VALUES (1570, '341524', '金寨县', 2, 168, '金寨县', 0);
INSERT INTO `dic_city` VALUES (1571, '341525', '霍山县', 2, 168, '霍山县', 0);
INSERT INTO `dic_city` VALUES (1572, '341602', '谯城区', 2, 169, '谯城区', 0);
INSERT INTO `dic_city` VALUES (1573, '341621', '涡阳县', 2, 169, '涡阳县', 0);
INSERT INTO `dic_city` VALUES (1574, '341622', '蒙城县', 2, 169, '蒙城县', 0);
INSERT INTO `dic_city` VALUES (1575, '341623', '利辛县', 2, 169, '利辛县', 0);
INSERT INTO `dic_city` VALUES (1576, '341702', '贵池区', 2, 170, '贵池区', 0);
INSERT INTO `dic_city` VALUES (1577, '341721', '东至县', 2, 170, '东至县', 0);
INSERT INTO `dic_city` VALUES (1578, '341722', '石台县', 2, 170, '石台县', 0);
INSERT INTO `dic_city` VALUES (1579, '341723', '青阳县', 2, 170, '青阳县', 0);
INSERT INTO `dic_city` VALUES (1580, '341802', '宣州区', 2, 171, '宣州区', 0);
INSERT INTO `dic_city` VALUES (1581, '341821', '郎溪县', 2, 171, '郎溪县', 0);
INSERT INTO `dic_city` VALUES (1582, '341822', '广德县', 2, 171, '广德县', 0);
INSERT INTO `dic_city` VALUES (1583, '341823', '泾县', 2, 171, '泾县', 0);
INSERT INTO `dic_city` VALUES (1584, '341824', '绩溪县', 2, 171, '绩溪县', 0);
INSERT INTO `dic_city` VALUES (1585, '341825', '旌德县', 2, 171, '旌德县', 0);
INSERT INTO `dic_city` VALUES (1586, '341881', '宁国市', 2, 171, '宁国市', 0);
INSERT INTO `dic_city` VALUES (1587, '350102', '鼓楼区', 2, 172, '鼓楼区', 0);
INSERT INTO `dic_city` VALUES (1588, '350103', '台江区', 2, 172, '台江区', 0);
INSERT INTO `dic_city` VALUES (1589, '350104', '仓山区', 2, 172, '仓山区', 0);
INSERT INTO `dic_city` VALUES (1590, '350105', '马尾区', 2, 172, '马尾区', 0);
INSERT INTO `dic_city` VALUES (1591, '350111', '晋安区', 2, 172, '晋安区', 0);
INSERT INTO `dic_city` VALUES (1592, '350121', '闽侯县', 2, 172, '闽侯县', 0);
INSERT INTO `dic_city` VALUES (1593, '350122', '连江县', 2, 172, '连江县', 0);
INSERT INTO `dic_city` VALUES (1594, '350123', '罗源县', 2, 172, '罗源县', 0);
INSERT INTO `dic_city` VALUES (1595, '350124', '闽清县', 2, 172, '闽清县', 0);
INSERT INTO `dic_city` VALUES (1596, '350125', '永泰县', 2, 172, '永泰县', 0);
INSERT INTO `dic_city` VALUES (1597, '350128', '平潭县', 2, 172, '平潭县', 0);
INSERT INTO `dic_city` VALUES (1598, '350181', '福清市', 2, 172, '福清市', 0);
INSERT INTO `dic_city` VALUES (1599, '350182', '长乐市', 2, 172, '长乐市', 0);
INSERT INTO `dic_city` VALUES (1600, '350202', '鼓浪屿区', 2, 173, '鼓浪屿区', 0);
INSERT INTO `dic_city` VALUES (1601, '350203', '思明区', 2, 173, '思明区', 0);
INSERT INTO `dic_city` VALUES (1602, '350204', '开元区', 2, 173, '开元区', 0);
INSERT INTO `dic_city` VALUES (1603, '350205', '杏林区', 2, 173, '杏林区', 0);
INSERT INTO `dic_city` VALUES (1604, '350206', '湖里区', 2, 173, '湖里区', 0);
INSERT INTO `dic_city` VALUES (1605, '350211', '集美区', 2, 173, '集美区', 0);
INSERT INTO `dic_city` VALUES (1606, '350212', '同安区', 2, 173, '同安区', 0);
INSERT INTO `dic_city` VALUES (1607, '350302', '城厢区', 2, 174, '城厢区', 0);
INSERT INTO `dic_city` VALUES (1608, '350303', '涵江区', 2, 174, '涵江区', 0);
INSERT INTO `dic_city` VALUES (1609, '350304', '荔城区', 2, 174, '荔城区', 0);
INSERT INTO `dic_city` VALUES (1610, '350305', '秀屿区', 2, 174, '秀屿区', 0);
INSERT INTO `dic_city` VALUES (1611, '350322', '仙游县', 2, 174, '仙游县', 0);
INSERT INTO `dic_city` VALUES (1612, '350402', '梅列区', 2, 175, '梅列区', 0);
INSERT INTO `dic_city` VALUES (1613, '350403', '三元区', 2, 175, '三元区', 0);
INSERT INTO `dic_city` VALUES (1614, '350421', '明溪县', 2, 175, '明溪县', 0);
INSERT INTO `dic_city` VALUES (1615, '350423', '清流县', 2, 175, '清流县', 0);
INSERT INTO `dic_city` VALUES (1616, '350424', '宁化县', 2, 175, '宁化县', 0);
INSERT INTO `dic_city` VALUES (1617, '350425', '大田县', 2, 175, '大田县', 0);
INSERT INTO `dic_city` VALUES (1618, '350426', '尤溪县', 2, 175, '尤溪县', 0);
INSERT INTO `dic_city` VALUES (1619, '350427', '沙县', 2, 175, '沙县', 0);
INSERT INTO `dic_city` VALUES (1620, '350428', '将乐县', 2, 175, '将乐县', 0);
INSERT INTO `dic_city` VALUES (1621, '350429', '泰宁县', 2, 175, '泰宁县', 0);
INSERT INTO `dic_city` VALUES (1622, '350430', '建宁县', 2, 175, '建宁县', 0);
INSERT INTO `dic_city` VALUES (1623, '350481', '永安市', 2, 175, '永安市', 0);
INSERT INTO `dic_city` VALUES (1624, '350502', '鲤城区', 2, 176, '鲤城区', 0);
INSERT INTO `dic_city` VALUES (1625, '350503', '丰泽区', 2, 176, '丰泽区', 0);
INSERT INTO `dic_city` VALUES (1626, '350504', '洛江区', 2, 176, '洛江区', 0);
INSERT INTO `dic_city` VALUES (1627, '350505', '泉港区', 2, 176, '泉港区', 0);
INSERT INTO `dic_city` VALUES (1628, '350521', '惠安县', 2, 176, '惠安县', 0);
INSERT INTO `dic_city` VALUES (1629, '350524', '安溪县', 2, 176, '安溪县', 0);
INSERT INTO `dic_city` VALUES (1630, '350525', '永春县', 2, 176, '永春县', 0);
INSERT INTO `dic_city` VALUES (1631, '350526', '德化县', 2, 176, '德化县', 0);
INSERT INTO `dic_city` VALUES (1632, '350527', '金门县', 2, 176, '金门县', 0);
INSERT INTO `dic_city` VALUES (1633, '350581', '石狮市', 2, 176, '石狮市', 0);
INSERT INTO `dic_city` VALUES (1634, '350582', '晋江市', 2, 176, '晋江市', 0);
INSERT INTO `dic_city` VALUES (1635, '350583', '南安市', 2, 176, '南安市', 0);
INSERT INTO `dic_city` VALUES (1636, '350602', '芗城区', 2, 177, '芗城区', 0);
INSERT INTO `dic_city` VALUES (1637, '350603', '龙文区', 2, 177, '龙文区', 0);
INSERT INTO `dic_city` VALUES (1638, '350622', '云霄县', 2, 177, '云霄县', 0);
INSERT INTO `dic_city` VALUES (1639, '350623', '漳浦县', 2, 177, '漳浦县', 0);
INSERT INTO `dic_city` VALUES (1640, '350624', '诏安县', 2, 177, '诏安县', 0);
INSERT INTO `dic_city` VALUES (1641, '350625', '长泰县', 2, 177, '长泰县', 0);
INSERT INTO `dic_city` VALUES (1642, '350626', '东山县', 2, 177, '东山县', 0);
INSERT INTO `dic_city` VALUES (1643, '350627', '南靖县', 2, 177, '南靖县', 0);
INSERT INTO `dic_city` VALUES (1644, '350628', '平和县', 2, 177, '平和县', 0);
INSERT INTO `dic_city` VALUES (1645, '350629', '华安县', 2, 177, '华安县', 0);
INSERT INTO `dic_city` VALUES (1646, '350681', '龙海市', 2, 177, '龙海市', 0);
INSERT INTO `dic_city` VALUES (1647, '350702', '延平区', 2, 178, '延平区', 0);
INSERT INTO `dic_city` VALUES (1648, '350721', '顺昌县', 2, 178, '顺昌县', 0);
INSERT INTO `dic_city` VALUES (1649, '350722', '浦城县', 2, 178, '浦城县', 0);
INSERT INTO `dic_city` VALUES (1650, '350723', '光泽县', 2, 178, '光泽县', 0);
INSERT INTO `dic_city` VALUES (1651, '350724', '松溪县', 2, 178, '松溪县', 0);
INSERT INTO `dic_city` VALUES (1652, '350725', '政和县', 2, 178, '政和县', 0);
INSERT INTO `dic_city` VALUES (1653, '350781', '邵武市', 2, 178, '邵武市', 0);
INSERT INTO `dic_city` VALUES (1654, '350782', '武夷山市', 2, 178, '武夷山市', 0);
INSERT INTO `dic_city` VALUES (1655, '350783', '建瓯市', 2, 178, '建瓯市', 0);
INSERT INTO `dic_city` VALUES (1656, '350784', '建阳市', 2, 178, '建阳市', 0);
INSERT INTO `dic_city` VALUES (1657, '350802', '新罗区', 2, 179, '新罗区', 0);
INSERT INTO `dic_city` VALUES (1658, '350821', '长汀县', 2, 179, '长汀县', 0);
INSERT INTO `dic_city` VALUES (1659, '350822', '永定县', 2, 179, '永定县', 0);
INSERT INTO `dic_city` VALUES (1660, '350823', '上杭县', 2, 179, '上杭县', 0);
INSERT INTO `dic_city` VALUES (1661, '350824', '武平县', 2, 179, '武平县', 0);
INSERT INTO `dic_city` VALUES (1662, '350825', '连城县', 2, 179, '连城县', 0);
INSERT INTO `dic_city` VALUES (1663, '350881', '漳平市', 2, 179, '漳平市', 0);
INSERT INTO `dic_city` VALUES (1664, '350902', '蕉城区', 2, 180, '蕉城区', 0);
INSERT INTO `dic_city` VALUES (1665, '350921', '霞浦县', 2, 180, '霞浦县', 0);
INSERT INTO `dic_city` VALUES (1666, '350922', '古田县', 2, 180, '古田县', 0);
INSERT INTO `dic_city` VALUES (1667, '350923', '屏南县', 2, 180, '屏南县', 0);
INSERT INTO `dic_city` VALUES (1668, '350924', '寿宁县', 2, 180, '寿宁县', 0);
INSERT INTO `dic_city` VALUES (1669, '350925', '周宁县', 2, 180, '周宁县', 0);
INSERT INTO `dic_city` VALUES (1670, '350926', '柘荣县', 2, 180, '柘荣县', 0);
INSERT INTO `dic_city` VALUES (1671, '350981', '福安市', 2, 180, '福安市', 0);
INSERT INTO `dic_city` VALUES (1672, '350982', '福鼎市', 2, 180, '福鼎市', 0);
INSERT INTO `dic_city` VALUES (1673, '360102', '东湖区', 2, 181, '东湖区', 0);
INSERT INTO `dic_city` VALUES (1674, '360103', '西湖区', 2, 181, '西湖区', 0);
INSERT INTO `dic_city` VALUES (1675, '360104', '青云谱区', 2, 181, '青云谱区', 0);
INSERT INTO `dic_city` VALUES (1676, '360105', '湾里区', 2, 181, '湾里区', 0);
INSERT INTO `dic_city` VALUES (1677, '360111', '青山湖区', 2, 181, '青山湖区', 0);
INSERT INTO `dic_city` VALUES (1678, '360121', '南昌县', 2, 181, '南昌县', 0);
INSERT INTO `dic_city` VALUES (1679, '360122', '新建县', 2, 181, '新建县', 0);
INSERT INTO `dic_city` VALUES (1680, '360123', '安义县', 2, 181, '安义县', 0);
INSERT INTO `dic_city` VALUES (1681, '360124', '进贤县', 2, 181, '进贤县', 0);
INSERT INTO `dic_city` VALUES (1682, '360202', '昌江区', 2, 182, '昌江区', 0);
INSERT INTO `dic_city` VALUES (1683, '360203', '珠山区', 2, 182, '珠山区', 0);
INSERT INTO `dic_city` VALUES (1684, '360222', '浮梁县', 2, 182, '浮梁县', 0);
INSERT INTO `dic_city` VALUES (1685, '360281', '乐平市', 2, 182, '乐平市', 0);
INSERT INTO `dic_city` VALUES (1686, '360302', '安源区', 2, 183, '安源区', 0);
INSERT INTO `dic_city` VALUES (1687, '360313', '湘东区', 2, 183, '湘东区', 0);
INSERT INTO `dic_city` VALUES (1688, '360321', '莲花县', 2, 183, '莲花县', 0);
INSERT INTO `dic_city` VALUES (1689, '360322', '上栗县', 2, 183, '上栗县', 0);
INSERT INTO `dic_city` VALUES (1690, '360323', '芦溪县', 2, 183, '芦溪县', 0);
INSERT INTO `dic_city` VALUES (1691, '360402', '庐山区', 2, 184, '庐山区', 0);
INSERT INTO `dic_city` VALUES (1692, '360403', '浔阳区', 2, 184, '浔阳区', 0);
INSERT INTO `dic_city` VALUES (1693, '360421', '九江县', 2, 184, '九江县', 0);
INSERT INTO `dic_city` VALUES (1694, '360423', '武宁县', 2, 184, '武宁县', 0);
INSERT INTO `dic_city` VALUES (1695, '360424', '修水县', 2, 184, '修水县', 0);
INSERT INTO `dic_city` VALUES (1696, '360425', '永修县', 2, 184, '永修县', 0);
INSERT INTO `dic_city` VALUES (1697, '360426', '德安县', 2, 184, '德安县', 0);
INSERT INTO `dic_city` VALUES (1698, '360427', '星子县', 2, 184, '星子县', 0);
INSERT INTO `dic_city` VALUES (1699, '360428', '都昌县', 2, 184, '都昌县', 0);
INSERT INTO `dic_city` VALUES (1700, '360429', '湖口县', 2, 184, '湖口县', 0);
INSERT INTO `dic_city` VALUES (1701, '360430', '彭泽县', 2, 184, '彭泽县', 0);
INSERT INTO `dic_city` VALUES (1702, '360481', '瑞昌市', 2, 184, '瑞昌市', 0);
INSERT INTO `dic_city` VALUES (1703, '360502', '渝水区', 2, 185, '渝水区', 0);
INSERT INTO `dic_city` VALUES (1704, '360521', '分宜县', 2, 185, '分宜县', 0);
INSERT INTO `dic_city` VALUES (1705, '360602', '月湖区', 2, 186, '月湖区', 0);
INSERT INTO `dic_city` VALUES (1706, '360622', '余江县', 2, 186, '余江县', 0);
INSERT INTO `dic_city` VALUES (1707, '360681', '贵溪市', 2, 186, '贵溪市', 0);
INSERT INTO `dic_city` VALUES (1708, '360702', '章贡区', 2, 187, '章贡区', 0);
INSERT INTO `dic_city` VALUES (1709, '360721', '赣县', 2, 187, '赣县', 0);
INSERT INTO `dic_city` VALUES (1710, '360722', '信丰县', 2, 187, '信丰县', 0);
INSERT INTO `dic_city` VALUES (1711, '360723', '大余县', 2, 187, '大余县', 0);
INSERT INTO `dic_city` VALUES (1712, '360724', '上犹县', 2, 187, '上犹县', 0);
INSERT INTO `dic_city` VALUES (1713, '360725', '崇义县', 2, 187, '崇义县', 0);
INSERT INTO `dic_city` VALUES (1714, '360726', '安远县', 2, 187, '安远县', 0);
INSERT INTO `dic_city` VALUES (1715, '360727', '龙南县', 2, 187, '龙南县', 0);
INSERT INTO `dic_city` VALUES (1716, '360728', '定南县', 2, 187, '定南县', 0);
INSERT INTO `dic_city` VALUES (1717, '360729', '全南县', 2, 187, '全南县', 0);
INSERT INTO `dic_city` VALUES (1718, '360730', '宁都县', 2, 187, '宁都县', 0);
INSERT INTO `dic_city` VALUES (1719, '360731', '于都县', 2, 187, '于都县', 0);
INSERT INTO `dic_city` VALUES (1720, '360732', '兴国县', 2, 187, '兴国县', 0);
INSERT INTO `dic_city` VALUES (1721, '360733', '会昌县', 2, 187, '会昌县', 0);
INSERT INTO `dic_city` VALUES (1722, '360734', '寻乌县', 2, 187, '寻乌县', 0);
INSERT INTO `dic_city` VALUES (1723, '360735', '石城县', 2, 187, '石城县', 0);
INSERT INTO `dic_city` VALUES (1724, '360781', '瑞金市', 2, 187, '瑞金市', 0);
INSERT INTO `dic_city` VALUES (1725, '360782', '南康市', 2, 187, '南康市', 0);
INSERT INTO `dic_city` VALUES (1726, '360802', '吉州区', 2, 188, '吉州区', 0);
INSERT INTO `dic_city` VALUES (1727, '360803', '青原区', 2, 188, '青原区', 0);
INSERT INTO `dic_city` VALUES (1728, '360821', '吉安县', 2, 188, '吉安县', 0);
INSERT INTO `dic_city` VALUES (1729, '360822', '吉水县', 2, 188, '吉水县', 0);
INSERT INTO `dic_city` VALUES (1730, '360823', '峡江县', 2, 188, '峡江县', 0);
INSERT INTO `dic_city` VALUES (1731, '360824', '新干县', 2, 188, '新干县', 0);
INSERT INTO `dic_city` VALUES (1732, '360825', '永丰县', 2, 188, '永丰县', 0);
INSERT INTO `dic_city` VALUES (1733, '360826', '泰和县', 2, 188, '泰和县', 0);
INSERT INTO `dic_city` VALUES (1734, '360827', '遂川县', 2, 188, '遂川县', 0);
INSERT INTO `dic_city` VALUES (1735, '360828', '万安县', 2, 188, '万安县', 0);
INSERT INTO `dic_city` VALUES (1736, '360829', '安福县', 2, 188, '安福县', 0);
INSERT INTO `dic_city` VALUES (1737, '360830', '永新县', 2, 188, '永新县', 0);
INSERT INTO `dic_city` VALUES (1738, '360881', '井冈山市', 2, 188, '井冈山市', 0);
INSERT INTO `dic_city` VALUES (1739, '360902', '袁州区', 2, 189, '袁州区', 0);
INSERT INTO `dic_city` VALUES (1740, '360921', '奉新县', 2, 189, '奉新县', 0);
INSERT INTO `dic_city` VALUES (1741, '360922', '万载县', 2, 189, '万载县', 0);
INSERT INTO `dic_city` VALUES (1742, '360923', '上高县', 2, 189, '上高县', 0);
INSERT INTO `dic_city` VALUES (1743, '360924', '宜丰县', 2, 189, '宜丰县', 0);
INSERT INTO `dic_city` VALUES (1744, '360925', '靖安县', 2, 189, '靖安县', 0);
INSERT INTO `dic_city` VALUES (1745, '360926', '铜鼓县', 2, 189, '铜鼓县', 0);
INSERT INTO `dic_city` VALUES (1746, '360981', '丰城市', 2, 189, '丰城市', 0);
INSERT INTO `dic_city` VALUES (1747, '360982', '樟树市', 2, 189, '樟树市', 0);
INSERT INTO `dic_city` VALUES (1748, '360983', '高安市', 2, 189, '高安市', 0);
INSERT INTO `dic_city` VALUES (1749, '361002', '临川区', 2, 190, '临川区', 0);
INSERT INTO `dic_city` VALUES (1750, '361021', '南城县', 2, 190, '南城县', 0);
INSERT INTO `dic_city` VALUES (1751, '361022', '黎川县', 2, 190, '黎川县', 0);
INSERT INTO `dic_city` VALUES (1752, '361023', '南丰县', 2, 190, '南丰县', 0);
INSERT INTO `dic_city` VALUES (1753, '361024', '崇仁县', 2, 190, '崇仁县', 0);
INSERT INTO `dic_city` VALUES (1754, '361025', '乐安县', 2, 190, '乐安县', 0);
INSERT INTO `dic_city` VALUES (1755, '361026', '宜黄县', 2, 190, '宜黄县', 0);
INSERT INTO `dic_city` VALUES (1756, '361027', '金溪县', 2, 190, '金溪县', 0);
INSERT INTO `dic_city` VALUES (1757, '361028', '资溪县', 2, 190, '资溪县', 0);
INSERT INTO `dic_city` VALUES (1758, '361029', '东乡县', 2, 190, '东乡县', 0);
INSERT INTO `dic_city` VALUES (1759, '361030', '广昌县', 2, 190, '广昌县', 0);
INSERT INTO `dic_city` VALUES (1760, '361102', '信州区', 2, 191, '信州区', 0);
INSERT INTO `dic_city` VALUES (1761, '361121', '上饶县', 2, 191, '上饶县', 0);
INSERT INTO `dic_city` VALUES (1762, '361122', '广丰县', 2, 191, '广丰县', 0);
INSERT INTO `dic_city` VALUES (1763, '361123', '玉山县', 2, 191, '玉山县', 0);
INSERT INTO `dic_city` VALUES (1764, '361124', '铅山县', 2, 191, '铅山县', 0);
INSERT INTO `dic_city` VALUES (1765, '361125', '横峰县', 2, 191, '横峰县', 0);
INSERT INTO `dic_city` VALUES (1766, '361126', '弋阳县', 2, 191, '弋阳县', 0);
INSERT INTO `dic_city` VALUES (1767, '361127', '余干县', 2, 191, '余干县', 0);
INSERT INTO `dic_city` VALUES (1768, '361128', '波阳县', 2, 191, '波阳县', 0);
INSERT INTO `dic_city` VALUES (1769, '361129', '万年县', 2, 191, '万年县', 0);
INSERT INTO `dic_city` VALUES (1770, '361130', '婺源县', 2, 191, '婺源县', 0);
INSERT INTO `dic_city` VALUES (1771, '361181', '德兴市', 2, 191, '德兴市', 0);
INSERT INTO `dic_city` VALUES (1772, '370102', '历下区', 2, 192, '历下区', 0);
INSERT INTO `dic_city` VALUES (1773, '511102', '市中区', 2, 192, '市中区', 0);
INSERT INTO `dic_city` VALUES (1774, '370104', '槐荫区', 2, 192, '槐荫区', 0);
INSERT INTO `dic_city` VALUES (1775, '370105', '天桥区', 2, 192, '天桥区', 0);
INSERT INTO `dic_city` VALUES (1776, '370112', '历城区', 2, 192, '历城区', 0);
INSERT INTO `dic_city` VALUES (1777, '370113', '长清区', 2, 192, '长清区', 0);
INSERT INTO `dic_city` VALUES (1778, '370124', '平阴县', 2, 192, '平阴县', 0);
INSERT INTO `dic_city` VALUES (1779, '370125', '济阳县', 2, 192, '济阳县', 0);
INSERT INTO `dic_city` VALUES (1780, '370126', '商河县', 2, 192, '商河县', 0);
INSERT INTO `dic_city` VALUES (1781, '370181', '章丘市', 2, 192, '章丘市', 0);
INSERT INTO `dic_city` VALUES (1782, '370202', '市南区', 2, 193, '市南区', 0);
INSERT INTO `dic_city` VALUES (1783, '370203', '市北区', 2, 193, '市北区', 0);
INSERT INTO `dic_city` VALUES (1784, '370205', '四方区', 2, 193, '四方区', 0);
INSERT INTO `dic_city` VALUES (1785, '370211', '黄岛区', 2, 193, '黄岛区', 0);
INSERT INTO `dic_city` VALUES (1786, '370212', '崂山区', 2, 193, '崂山区', 0);
INSERT INTO `dic_city` VALUES (1787, '370213', '李沧区', 2, 193, '李沧区', 0);
INSERT INTO `dic_city` VALUES (1788, '370214', '城阳区', 2, 193, '城阳区', 0);
INSERT INTO `dic_city` VALUES (1789, '370281', '胶州市', 2, 193, '胶州市', 0);
INSERT INTO `dic_city` VALUES (1790, '370282', '即墨市', 2, 193, '即墨市', 0);
INSERT INTO `dic_city` VALUES (1791, '370283', '平度市', 2, 193, '平度市', 0);
INSERT INTO `dic_city` VALUES (1792, '370284', '胶南市', 2, 193, '胶南市', 0);
INSERT INTO `dic_city` VALUES (1793, '370285', '莱西市', 2, 193, '莱西市', 0);
INSERT INTO `dic_city` VALUES (1794, '370302', '淄川区', 2, 194, '淄川区', 0);
INSERT INTO `dic_city` VALUES (1795, '370303', '张店区', 2, 194, '张店区', 0);
INSERT INTO `dic_city` VALUES (1796, '370304', '博山区', 2, 194, '博山区', 0);
INSERT INTO `dic_city` VALUES (1797, '370305', '临淄区', 2, 194, '临淄区', 0);
INSERT INTO `dic_city` VALUES (1798, '370306', '周村区', 2, 194, '周村区', 0);
INSERT INTO `dic_city` VALUES (1799, '370321', '桓台县', 2, 194, '桓台县', 0);
INSERT INTO `dic_city` VALUES (1800, '370322', '高青县', 2, 194, '高青县', 0);
INSERT INTO `dic_city` VALUES (1801, '370323', '沂源县', 2, 194, '沂源县', 0);
INSERT INTO `dic_city` VALUES (1802, '370402', '市中区', 2, 195, '市中区', 0);
INSERT INTO `dic_city` VALUES (1803, '370403', '薛城区', 2, 195, '薛城区', 0);
INSERT INTO `dic_city` VALUES (1804, '370404', '峄城区', 2, 195, '峄城区', 0);
INSERT INTO `dic_city` VALUES (1805, '370405', '台儿庄区', 2, 195, '台儿庄区', 0);
INSERT INTO `dic_city` VALUES (1806, '370406', '山亭区', 2, 195, '山亭区', 0);
INSERT INTO `dic_city` VALUES (1807, '370481', '滕州市', 2, 195, '滕州市', 0);
INSERT INTO `dic_city` VALUES (1808, '370502', '东营区', 2, 196, '东营区', 0);
INSERT INTO `dic_city` VALUES (1809, '370503', '河口区', 2, 196, '河口区', 0);
INSERT INTO `dic_city` VALUES (1810, '370521', '垦利县', 2, 196, '垦利县', 0);
INSERT INTO `dic_city` VALUES (1811, '370522', '利津县', 2, 196, '利津县', 0);
INSERT INTO `dic_city` VALUES (1812, '370523', '广饶县', 2, 196, '广饶县', 0);
INSERT INTO `dic_city` VALUES (1813, '370602', '芝罘区', 2, 197, '芝罘区', 0);
INSERT INTO `dic_city` VALUES (1814, '370611', '福山区', 2, 197, '福山区', 0);
INSERT INTO `dic_city` VALUES (1815, '370612', '牟平区', 2, 197, '牟平区', 0);
INSERT INTO `dic_city` VALUES (1816, '370613', '莱山区', 2, 197, '莱山区', 0);
INSERT INTO `dic_city` VALUES (1817, '370634', '长岛县', 2, 197, '长岛县', 0);
INSERT INTO `dic_city` VALUES (1818, '370681', '龙口市', 2, 197, '龙口市', 0);
INSERT INTO `dic_city` VALUES (1819, '370682', '莱阳市', 2, 197, '莱阳市', 0);
INSERT INTO `dic_city` VALUES (1820, '370683', '莱州市', 2, 197, '莱州市', 0);
INSERT INTO `dic_city` VALUES (1821, '370684', '蓬莱市', 2, 197, '蓬莱市', 0);
INSERT INTO `dic_city` VALUES (1822, '370685', '招远市', 2, 197, '招远市', 0);
INSERT INTO `dic_city` VALUES (1823, '370686', '栖霞市', 2, 197, '栖霞市', 0);
INSERT INTO `dic_city` VALUES (1824, '370687', '海阳市', 2, 197, '海阳市', 0);
INSERT INTO `dic_city` VALUES (1825, '370702', '潍城区', 2, 198, '潍城区', 0);
INSERT INTO `dic_city` VALUES (1826, '370703', '寒亭区', 2, 198, '寒亭区', 0);
INSERT INTO `dic_city` VALUES (1827, '370704', '坊子区', 2, 198, '坊子区', 0);
INSERT INTO `dic_city` VALUES (1828, '370705', '奎文区', 2, 198, '奎文区', 0);
INSERT INTO `dic_city` VALUES (1829, '370724', '临朐县', 2, 198, '临朐县', 0);
INSERT INTO `dic_city` VALUES (1830, '370725', '昌乐县', 2, 198, '昌乐县', 0);
INSERT INTO `dic_city` VALUES (1831, '370781', '青州市', 2, 198, '青州市', 0);
INSERT INTO `dic_city` VALUES (1832, '370782', '诸城市', 2, 198, '诸城市', 0);
INSERT INTO `dic_city` VALUES (1833, '370783', '寿光市', 2, 198, '寿光市', 0);
INSERT INTO `dic_city` VALUES (1834, '370784', '安丘市', 2, 198, '安丘市', 0);
INSERT INTO `dic_city` VALUES (1835, '370785', '高密市', 2, 198, '高密市', 0);
INSERT INTO `dic_city` VALUES (1836, '370786', '昌邑市', 2, 198, '昌邑市', 0);
INSERT INTO `dic_city` VALUES (1837, '370802', '市中区', 2, 199, '市中区', 0);
INSERT INTO `dic_city` VALUES (1838, '370811', '任城区', 2, 199, '任城区', 0);
INSERT INTO `dic_city` VALUES (1839, '370826', '微山县', 2, 199, '微山县', 0);
INSERT INTO `dic_city` VALUES (1840, '370827', '鱼台县', 2, 199, '鱼台县', 0);
INSERT INTO `dic_city` VALUES (1841, '370828', '金乡县', 2, 199, '金乡县', 0);
INSERT INTO `dic_city` VALUES (1842, '370829', '嘉祥县', 2, 199, '嘉祥县', 0);
INSERT INTO `dic_city` VALUES (1843, '370830', '汶上县', 2, 199, '汶上县', 0);
INSERT INTO `dic_city` VALUES (1844, '370831', '泗水县', 2, 199, '泗水县', 0);
INSERT INTO `dic_city` VALUES (1845, '370832', '梁山县', 2, 199, '梁山县', 0);
INSERT INTO `dic_city` VALUES (1846, '370881', '曲阜市', 2, 199, '曲阜市', 0);
INSERT INTO `dic_city` VALUES (1847, '370882', '兖州市', 2, 199, '兖州市', 0);
INSERT INTO `dic_city` VALUES (1848, '370883', '邹城市', 2, 199, '邹城市', 0);
INSERT INTO `dic_city` VALUES (1849, '370902', '泰山区', 2, 200, '泰山区', 0);
INSERT INTO `dic_city` VALUES (1850, '370903', '岱岳区', 2, 200, '岱岳区', 0);
INSERT INTO `dic_city` VALUES (1851, '370921', '宁阳县', 2, 200, '宁阳县', 0);
INSERT INTO `dic_city` VALUES (1852, '370923', '东平县', 2, 200, '东平县', 0);
INSERT INTO `dic_city` VALUES (1853, '370982', '新泰市', 2, 200, '新泰市', 0);
INSERT INTO `dic_city` VALUES (1854, '370983', '肥城市', 2, 200, '肥城市', 0);
INSERT INTO `dic_city` VALUES (1855, '371002', '环翠区', 2, 201, '环翠区', 0);
INSERT INTO `dic_city` VALUES (1856, '371081', '文登市', 2, 201, '文登市', 0);
INSERT INTO `dic_city` VALUES (1857, '371082', '荣成市', 2, 201, '荣成市', 0);
INSERT INTO `dic_city` VALUES (1858, '371083', '乳山市', 2, 201, '乳山市', 0);
INSERT INTO `dic_city` VALUES (1859, '371102', '东港区', 2, 202, '东港区', 0);
INSERT INTO `dic_city` VALUES (1860, '371121', '五莲县', 2, 202, '五莲县', 0);
INSERT INTO `dic_city` VALUES (1861, '371122', '莒县', 2, 202, '莒县', 0);
INSERT INTO `dic_city` VALUES (1862, '371202', '莱城区', 2, 203, '莱城区', 0);
INSERT INTO `dic_city` VALUES (1863, '371203', '钢城区', 2, 203, '钢城区', 0);
INSERT INTO `dic_city` VALUES (1864, '371302', '兰山区', 2, 204, '兰山区', 0);
INSERT INTO `dic_city` VALUES (1865, '371311', '罗庄区', 2, 204, '罗庄区', 0);
INSERT INTO `dic_city` VALUES (1866, '371312', '河东区', 2, 204, '河东区', 0);
INSERT INTO `dic_city` VALUES (1867, '371321', '沂南县', 2, 204, '沂南县', 0);
INSERT INTO `dic_city` VALUES (1868, '371322', '郯城县', 2, 204, '郯城县', 0);
INSERT INTO `dic_city` VALUES (1869, '371323', '沂水县', 2, 204, '沂水县', 0);
INSERT INTO `dic_city` VALUES (1870, '371324', '苍山县', 2, 204, '苍山县', 0);
INSERT INTO `dic_city` VALUES (1871, '371325', '费县', 2, 204, '费县', 0);
INSERT INTO `dic_city` VALUES (1872, '371326', '平邑县', 2, 204, '平邑县', 0);
INSERT INTO `dic_city` VALUES (1873, '371327', '莒南县', 2, 204, '莒南县', 0);
INSERT INTO `dic_city` VALUES (1874, '371328', '蒙阴县', 2, 204, '蒙阴县', 0);
INSERT INTO `dic_city` VALUES (1875, '371329', '临沭县', 2, 204, '临沭县', 0);
INSERT INTO `dic_city` VALUES (1876, '371402', '德城区', 2, 205, '德城区', 0);
INSERT INTO `dic_city` VALUES (1877, '371421', '陵县', 2, 205, '陵县', 0);
INSERT INTO `dic_city` VALUES (1878, '371422', '宁津县', 2, 205, '宁津县', 0);
INSERT INTO `dic_city` VALUES (1879, '371423', '庆云县', 2, 205, '庆云县', 0);
INSERT INTO `dic_city` VALUES (1880, '371424', '临邑县', 2, 205, '临邑县', 0);
INSERT INTO `dic_city` VALUES (1881, '371425', '齐河县', 2, 205, '齐河县', 0);
INSERT INTO `dic_city` VALUES (1882, '371426', '平原县', 2, 205, '平原县', 0);
INSERT INTO `dic_city` VALUES (1883, '371427', '夏津县', 2, 205, '夏津县', 0);
INSERT INTO `dic_city` VALUES (1884, '371428', '武城县', 2, 205, '武城县', 0);
INSERT INTO `dic_city` VALUES (1885, '371481', '乐陵市', 2, 205, '乐陵市', 0);
INSERT INTO `dic_city` VALUES (1886, '371482', '禹城市', 2, 205, '禹城市', 0);
INSERT INTO `dic_city` VALUES (1887, '371502', '东昌府区', 2, 206, '东昌府区', 0);
INSERT INTO `dic_city` VALUES (1888, '371521', '阳谷县', 2, 206, '阳谷县', 0);
INSERT INTO `dic_city` VALUES (1889, '371522', '莘县', 2, 206, '莘县', 0);
INSERT INTO `dic_city` VALUES (1890, '371523', '茌平县', 2, 206, '茌平县', 0);
INSERT INTO `dic_city` VALUES (1891, '371524', '东阿县', 2, 206, '东阿县', 0);
INSERT INTO `dic_city` VALUES (1892, '371525', '冠县', 2, 206, '冠县', 0);
INSERT INTO `dic_city` VALUES (1893, '371526', '高唐县', 2, 206, '高唐县', 0);
INSERT INTO `dic_city` VALUES (1894, '371581', '临清市', 2, 206, '临清市', 0);
INSERT INTO `dic_city` VALUES (1895, '371602', '滨城区', 2, 207, '滨城区', 0);
INSERT INTO `dic_city` VALUES (1896, '371621', '惠民县', 2, 207, '惠民县', 0);
INSERT INTO `dic_city` VALUES (1897, '371622', '阳信县', 2, 207, '阳信县', 0);
INSERT INTO `dic_city` VALUES (1898, '371623', '无棣县', 2, 207, '无棣县', 0);
INSERT INTO `dic_city` VALUES (1899, '371624', '沾化县', 2, 207, '沾化县', 0);
INSERT INTO `dic_city` VALUES (1900, '371625', '博兴县', 2, 207, '博兴县', 0);
INSERT INTO `dic_city` VALUES (1901, '371626', '邹平县', 2, 207, '邹平县', 0);
INSERT INTO `dic_city` VALUES (1902, '371702', '牡丹区', 2, 208, '牡丹区', 0);
INSERT INTO `dic_city` VALUES (1903, '371721', '曹县', 2, 208, '曹县', 0);
INSERT INTO `dic_city` VALUES (1904, '371722', '单县', 2, 208, '单县', 0);
INSERT INTO `dic_city` VALUES (1905, '371723', '成武县', 2, 208, '成武县', 0);
INSERT INTO `dic_city` VALUES (1906, '371724', '巨野县', 2, 208, '巨野县', 0);
INSERT INTO `dic_city` VALUES (1907, '371725', '郓城县', 2, 208, '郓城县', 0);
INSERT INTO `dic_city` VALUES (1908, '371726', '鄄城县', 2, 208, '鄄城县', 0);
INSERT INTO `dic_city` VALUES (1909, '371727', '定陶县', 2, 208, '定陶县', 0);
INSERT INTO `dic_city` VALUES (1910, '371728', '东明县', 2, 208, '东明县', 0);
INSERT INTO `dic_city` VALUES (1911, '410102', '中原区', 2, 209, '中原区', 0);
INSERT INTO `dic_city` VALUES (1912, '410103', '二七区', 2, 209, '二七区', 0);
INSERT INTO `dic_city` VALUES (1913, '410104', '管城回族区', 2, 209, '管城回族区', 0);
INSERT INTO `dic_city` VALUES (1914, '410105', '金水区', 2, 209, '金水区', 0);
INSERT INTO `dic_city` VALUES (1915, '410106', '上街区', 2, 209, '上街区', 0);
INSERT INTO `dic_city` VALUES (1916, '410108', '邙山区', 2, 209, '邙山区', 0);
INSERT INTO `dic_city` VALUES (1917, '410122', '中牟县', 2, 209, '中牟县', 0);
INSERT INTO `dic_city` VALUES (1918, '410181', '巩义市', 2, 209, '巩义市', 0);
INSERT INTO `dic_city` VALUES (1919, '410182', '荥阳市', 2, 209, '荥阳市', 0);
INSERT INTO `dic_city` VALUES (1920, '410183', '新密市', 2, 209, '新密市', 0);
INSERT INTO `dic_city` VALUES (1921, '410184', '新郑市', 2, 209, '新郑市', 0);
INSERT INTO `dic_city` VALUES (1922, '410185', '登封市', 2, 209, '登封市', 0);
INSERT INTO `dic_city` VALUES (1923, '410202', '龙亭区', 2, 210, '龙亭区', 0);
INSERT INTO `dic_city` VALUES (1924, '410203', '顺河回族区', 2, 210, '顺河回族区', 0);
INSERT INTO `dic_city` VALUES (1925, '410204', '鼓楼区', 2, 210, '鼓楼区', 0);
INSERT INTO `dic_city` VALUES (1926, '410205', '禹王台区', 2, 210, '禹王台区', 0);
INSERT INTO `dic_city` VALUES (1927, '410211', '郊区', 2, 210, '郊区', 0);
INSERT INTO `dic_city` VALUES (1928, '410221', '杞县', 2, 210, '杞县', 0);
INSERT INTO `dic_city` VALUES (1929, '410222', '通许县', 2, 210, '通许县', 0);
INSERT INTO `dic_city` VALUES (1930, '410223', '尉氏县', 2, 210, '尉氏县', 0);
INSERT INTO `dic_city` VALUES (1931, '410224', '开封县', 2, 210, '开封县', 0);
INSERT INTO `dic_city` VALUES (1932, '410225', '兰考县', 2, 210, '兰考县', 0);
INSERT INTO `dic_city` VALUES (1933, '410302', '老城区', 2, 211, '老城区', 0);
INSERT INTO `dic_city` VALUES (1934, '410303', '西工区', 2, 211, '西工区', 0);
INSERT INTO `dic_city` VALUES (1935, '410304', '瀍河回族区', 2, 211, '瀍河回族区', 0);
INSERT INTO `dic_city` VALUES (1936, '410305', '涧西区', 2, 211, '涧西区', 0);
INSERT INTO `dic_city` VALUES (1937, '410306', '吉利区', 2, 211, '吉利区', 0);
INSERT INTO `dic_city` VALUES (1938, '410307', '洛龙区', 2, 211, '洛龙区', 0);
INSERT INTO `dic_city` VALUES (1939, '410322', '孟津县', 2, 211, '孟津县', 0);
INSERT INTO `dic_city` VALUES (1940, '410323', '新安县', 2, 211, '新安县', 0);
INSERT INTO `dic_city` VALUES (1941, '410324', '栾川县', 2, 211, '栾川县', 0);
INSERT INTO `dic_city` VALUES (1942, '410325', '嵩县', 2, 211, '嵩县', 0);
INSERT INTO `dic_city` VALUES (1943, '410326', '汝阳县', 2, 211, '汝阳县', 0);
INSERT INTO `dic_city` VALUES (1944, '410327', '宜阳县', 2, 211, '宜阳县', 0);
INSERT INTO `dic_city` VALUES (1945, '410328', '洛宁县', 2, 211, '洛宁县', 0);
INSERT INTO `dic_city` VALUES (1946, '410329', '伊川县', 2, 211, '伊川县', 0);
INSERT INTO `dic_city` VALUES (1947, '410381', '偃师市', 2, 211, '偃师市', 0);
INSERT INTO `dic_city` VALUES (1948, '410402', '新华区', 2, 212, '新华区', 0);
INSERT INTO `dic_city` VALUES (1949, '410403', '卫东区', 2, 212, '卫东区', 0);
INSERT INTO `dic_city` VALUES (1950, '410404', '石龙区', 2, 212, '石龙区', 0);
INSERT INTO `dic_city` VALUES (1951, '410411', '湛河区', 2, 212, '湛河区', 0);
INSERT INTO `dic_city` VALUES (1952, '410421', '宝丰县', 2, 212, '宝丰县', 0);
INSERT INTO `dic_city` VALUES (1953, '410422', '叶县', 2, 212, '叶县', 0);
INSERT INTO `dic_city` VALUES (1954, '410423', '鲁山县', 2, 212, '鲁山县', 0);
INSERT INTO `dic_city` VALUES (1955, '410425', '郏县', 2, 212, '郏县', 0);
INSERT INTO `dic_city` VALUES (1956, '410481', '舞钢市', 2, 212, '舞钢市', 0);
INSERT INTO `dic_city` VALUES (1957, '410482', '汝州市', 2, 212, '汝州市', 0);
INSERT INTO `dic_city` VALUES (1958, '410502', '文峰区', 2, 213, '文峰区', 0);
INSERT INTO `dic_city` VALUES (1959, '410503', '北关区', 2, 213, '北关区', 0);
INSERT INTO `dic_city` VALUES (1960, '410505', '殷都区', 2, 213, '殷都区', 0);
INSERT INTO `dic_city` VALUES (1961, '410506', '龙安区', 2, 213, '龙安区', 0);
INSERT INTO `dic_city` VALUES (1962, '410522', '安阳县', 2, 213, '安阳县', 0);
INSERT INTO `dic_city` VALUES (1963, '410523', '汤阴县', 2, 213, '汤阴县', 0);
INSERT INTO `dic_city` VALUES (1964, '410526', '滑县', 2, 213, '滑县', 0);
INSERT INTO `dic_city` VALUES (1965, '410527', '内黄县', 2, 213, '内黄县', 0);
INSERT INTO `dic_city` VALUES (1966, '410581', '林州市', 2, 213, '林州市', 0);
INSERT INTO `dic_city` VALUES (1967, '410602', '鹤山区', 2, 214, '鹤山区', 0);
INSERT INTO `dic_city` VALUES (1968, '410603', '山城区', 2, 214, '山城区', 0);
INSERT INTO `dic_city` VALUES (1969, '410611', '淇滨区', 2, 214, '淇滨区', 0);
INSERT INTO `dic_city` VALUES (1970, '410621', '浚县', 2, 214, '浚县', 0);
INSERT INTO `dic_city` VALUES (1971, '410622', '淇县', 2, 214, '淇县', 0);
INSERT INTO `dic_city` VALUES (1972, '410702', '红旗区', 2, 215, '红旗区', 0);
INSERT INTO `dic_city` VALUES (1973, '410703', '新华区', 2, 215, '新华区', 0);
INSERT INTO `dic_city` VALUES (1974, '410704', '北站区', 2, 215, '北站区', 0);
INSERT INTO `dic_city` VALUES (1975, '410711', '郊区', 2, 215, '郊区', 0);
INSERT INTO `dic_city` VALUES (1976, '410721', '新乡县', 2, 215, '新乡县', 0);
INSERT INTO `dic_city` VALUES (1977, '410724', '获嘉县', 2, 215, '获嘉县', 0);
INSERT INTO `dic_city` VALUES (1978, '410725', '原阳县', 2, 215, '原阳县', 0);
INSERT INTO `dic_city` VALUES (1979, '410726', '延津县', 2, 215, '延津县', 0);
INSERT INTO `dic_city` VALUES (1980, '410727', '封丘县', 2, 215, '封丘县', 0);
INSERT INTO `dic_city` VALUES (1981, '410728', '长垣县', 2, 215, '长垣县', 0);
INSERT INTO `dic_city` VALUES (1982, '410781', '卫辉市', 2, 215, '卫辉市', 0);
INSERT INTO `dic_city` VALUES (1983, '410782', '辉县市', 2, 215, '辉县市', 0);
INSERT INTO `dic_city` VALUES (1984, '410802', '解放区', 2, 216, '解放区', 0);
INSERT INTO `dic_city` VALUES (1985, '410803', '中站区', 2, 216, '中站区', 0);
INSERT INTO `dic_city` VALUES (1986, '410804', '马村区', 2, 216, '马村区', 0);
INSERT INTO `dic_city` VALUES (1987, '410811', '山阳区', 2, 216, '山阳区', 0);
INSERT INTO `dic_city` VALUES (1988, '410821', '修武县', 2, 216, '修武县', 0);
INSERT INTO `dic_city` VALUES (1989, '410822', '博爱县', 2, 216, '博爱县', 0);
INSERT INTO `dic_city` VALUES (1990, '410823', '武陟县', 2, 216, '武陟县', 0);
INSERT INTO `dic_city` VALUES (1991, '410825', '温县', 2, 216, '温县', 0);
INSERT INTO `dic_city` VALUES (1992, '410882', '沁阳市', 2, 216, '沁阳市', 0);
INSERT INTO `dic_city` VALUES (1993, '410883', '孟州市', 2, 216, '孟州市', 0);
INSERT INTO `dic_city` VALUES (1994, '410902', '华龙区', 2, 218, '华龙区', 0);
INSERT INTO `dic_city` VALUES (1995, '410922', '清丰县', 2, 218, '清丰县', 0);
INSERT INTO `dic_city` VALUES (1996, '410923', '南乐县', 2, 218, '南乐县', 0);
INSERT INTO `dic_city` VALUES (1997, '410926', '范县', 2, 218, '范县', 0);
INSERT INTO `dic_city` VALUES (1998, '410927', '台前县', 2, 218, '台前县', 0);
INSERT INTO `dic_city` VALUES (1999, '410928', '濮阳县', 2, 218, '濮阳县', 0);
INSERT INTO `dic_city` VALUES (2000, '411002', '魏都区', 2, 219, '魏都区', 0);
INSERT INTO `dic_city` VALUES (2001, '411023', '许昌县', 2, 219, '许昌县', 0);
INSERT INTO `dic_city` VALUES (2002, '411024', '鄢陵县', 2, 219, '鄢陵县', 0);
INSERT INTO `dic_city` VALUES (2003, '411025', '襄城县', 2, 219, '襄城县', 0);
INSERT INTO `dic_city` VALUES (2004, '411081', '禹州市', 2, 219, '禹州市', 0);
INSERT INTO `dic_city` VALUES (2005, '411082', '长葛市', 2, 219, '长葛市', 0);
INSERT INTO `dic_city` VALUES (2006, '411102', '源汇区', 2, 220, '源汇区', 0);
INSERT INTO `dic_city` VALUES (2007, '411121', '舞阳县', 2, 220, '舞阳县', 0);
INSERT INTO `dic_city` VALUES (2008, '411122', '临颍县', 2, 220, '临颍县', 0);
INSERT INTO `dic_city` VALUES (2009, '411123', '郾城县', 2, 220, '郾城县', 0);
INSERT INTO `dic_city` VALUES (2010, '411202', '湖滨区', 2, 221, '湖滨区', 0);
INSERT INTO `dic_city` VALUES (2011, '411221', '渑池县', 2, 221, '渑池县', 0);
INSERT INTO `dic_city` VALUES (2012, '411222', '陕县', 2, 221, '陕县', 0);
INSERT INTO `dic_city` VALUES (2013, '411224', '卢氏县', 2, 221, '卢氏县', 0);
INSERT INTO `dic_city` VALUES (2014, '411281', '义马市', 2, 221, '义马市', 0);
INSERT INTO `dic_city` VALUES (2015, '411282', '灵宝市', 2, 221, '灵宝市', 0);
INSERT INTO `dic_city` VALUES (2016, '411302', '宛城区', 2, 222, '宛城区', 0);
INSERT INTO `dic_city` VALUES (2017, '411303', '卧龙区', 2, 222, '卧龙区', 0);
INSERT INTO `dic_city` VALUES (2018, '411321', '南召县', 2, 222, '南召县', 0);
INSERT INTO `dic_city` VALUES (2019, '411322', '方城县', 2, 222, '方城县', 0);
INSERT INTO `dic_city` VALUES (2020, '411323', '西峡县', 2, 222, '西峡县', 0);
INSERT INTO `dic_city` VALUES (2021, '411324', '镇平县', 2, 222, '镇平县', 0);
INSERT INTO `dic_city` VALUES (2022, '411325', '内乡县', 2, 222, '内乡县', 0);
INSERT INTO `dic_city` VALUES (2023, '411326', '淅川县', 2, 222, '淅川县', 0);
INSERT INTO `dic_city` VALUES (2024, '411327', '社旗县', 2, 222, '社旗县', 0);
INSERT INTO `dic_city` VALUES (2025, '411328', '唐河县', 2, 222, '唐河县', 0);
INSERT INTO `dic_city` VALUES (2026, '411329', '新野县', 2, 222, '新野县', 0);
INSERT INTO `dic_city` VALUES (2027, '411330', '桐柏县', 2, 222, '桐柏县', 0);
INSERT INTO `dic_city` VALUES (2028, '411381', '邓州市', 2, 222, '邓州市', 0);
INSERT INTO `dic_city` VALUES (2029, '411402', '梁园区', 2, 223, '梁园区', 0);
INSERT INTO `dic_city` VALUES (2030, '411403', '睢阳区', 2, 223, '睢阳区', 0);
INSERT INTO `dic_city` VALUES (2031, '411421', '民权县', 2, 223, '民权县', 0);
INSERT INTO `dic_city` VALUES (2032, '411422', '睢县', 2, 223, '睢县', 0);
INSERT INTO `dic_city` VALUES (2033, '411423', '宁陵县', 2, 223, '宁陵县', 0);
INSERT INTO `dic_city` VALUES (2034, '411424', '柘城县', 2, 223, '柘城县', 0);
INSERT INTO `dic_city` VALUES (2035, '411425', '虞城县', 2, 223, '虞城县', 0);
INSERT INTO `dic_city` VALUES (2036, '411426', '夏邑县', 2, 223, '夏邑县', 0);
INSERT INTO `dic_city` VALUES (2037, '411481', '永城市', 2, 223, '永城市', 0);
INSERT INTO `dic_city` VALUES (2038, '411502', '浉河区', 2, 224, '浉河区', 0);
INSERT INTO `dic_city` VALUES (2039, '411503', '平桥区', 2, 224, '平桥区', 0);
INSERT INTO `dic_city` VALUES (2040, '411521', '罗山县', 2, 224, '罗山县', 0);
INSERT INTO `dic_city` VALUES (2041, '411522', '光山县', 2, 224, '光山县', 0);
INSERT INTO `dic_city` VALUES (2042, '411523', '新县', 2, 224, '新县', 0);
INSERT INTO `dic_city` VALUES (2043, '411524', '商城县', 2, 224, '商城县', 0);
INSERT INTO `dic_city` VALUES (2044, '411525', '固始县', 2, 224, '固始县', 0);
INSERT INTO `dic_city` VALUES (2045, '411526', '潢川县', 2, 224, '潢川县', 0);
INSERT INTO `dic_city` VALUES (2046, '411527', '淮滨县', 2, 224, '淮滨县', 0);
INSERT INTO `dic_city` VALUES (2047, '411528', '息县', 2, 224, '息县', 0);
INSERT INTO `dic_city` VALUES (2048, '411602', '川汇区', 2, 225, '川汇区', 0);
INSERT INTO `dic_city` VALUES (2049, '411621', '扶沟县', 2, 225, '扶沟县', 0);
INSERT INTO `dic_city` VALUES (2050, '411622', '西华县', 2, 225, '西华县', 0);
INSERT INTO `dic_city` VALUES (2051, '411623', '商水县', 2, 225, '商水县', 0);
INSERT INTO `dic_city` VALUES (2052, '411624', '沈丘县', 2, 225, '沈丘县', 0);
INSERT INTO `dic_city` VALUES (2053, '411625', '郸城县', 2, 225, '郸城县', 0);
INSERT INTO `dic_city` VALUES (2054, '411626', '淮阳县', 2, 225, '淮阳县', 0);
INSERT INTO `dic_city` VALUES (2055, '411627', '太康县', 2, 225, '太康县', 0);
INSERT INTO `dic_city` VALUES (2056, '411628', '鹿邑县', 2, 225, '鹿邑县', 0);
INSERT INTO `dic_city` VALUES (2057, '411681', '项城市', 2, 225, '项城市', 0);
INSERT INTO `dic_city` VALUES (2058, '411702', '驿城区', 2, 226, '驿城区', 0);
INSERT INTO `dic_city` VALUES (2059, '411721', '西平县', 2, 226, '西平县', 0);
INSERT INTO `dic_city` VALUES (2060, '411722', '上蔡县', 2, 226, '上蔡县', 0);
INSERT INTO `dic_city` VALUES (2061, '411723', '平舆县', 2, 226, '平舆县', 0);
INSERT INTO `dic_city` VALUES (2062, '411724', '正阳县', 2, 226, '正阳县', 0);
INSERT INTO `dic_city` VALUES (2063, '411725', '确山县', 2, 226, '确山县', 0);
INSERT INTO `dic_city` VALUES (2064, '411726', '泌阳县', 2, 226, '泌阳县', 0);
INSERT INTO `dic_city` VALUES (2065, '411727', '汝南县', 2, 226, '汝南县', 0);
INSERT INTO `dic_city` VALUES (2066, '411728', '遂平县', 2, 226, '遂平县', 0);
INSERT INTO `dic_city` VALUES (2067, '411729', '新蔡县', 2, 226, '新蔡县', 0);
INSERT INTO `dic_city` VALUES (2068, '420102', '江岸区', 2, 227, '江岸区', 0);
INSERT INTO `dic_city` VALUES (2069, '420103', '江汉区', 2, 227, '江汉区', 0);
INSERT INTO `dic_city` VALUES (2070, '420104', '硚口区', 2, 227, '硚口区', 0);
INSERT INTO `dic_city` VALUES (2071, '420105', '汉阳区', 2, 227, '汉阳区', 0);
INSERT INTO `dic_city` VALUES (2072, '420106', '武昌区', 2, 227, '武昌区', 0);
INSERT INTO `dic_city` VALUES (2073, '420107', '青山区', 2, 227, '青山区', 0);
INSERT INTO `dic_city` VALUES (2074, '420111', '洪山区', 2, 227, '洪山区', 0);
INSERT INTO `dic_city` VALUES (2075, '420112', '东西湖区', 2, 227, '东西湖区', 0);
INSERT INTO `dic_city` VALUES (2076, '420113', '汉南区', 2, 227, '汉南区', 0);
INSERT INTO `dic_city` VALUES (2077, '420114', '蔡甸区', 2, 227, '蔡甸区', 0);
INSERT INTO `dic_city` VALUES (2078, '420115', '江夏区', 2, 227, '江夏区', 0);
INSERT INTO `dic_city` VALUES (2079, '420116', '黄陂区', 2, 227, '黄陂区', 0);
INSERT INTO `dic_city` VALUES (2080, '420117', '新洲区', 2, 227, '新洲区', 0);
INSERT INTO `dic_city` VALUES (2081, '420202', '黄石港区', 2, 228, '黄石港区', 0);
INSERT INTO `dic_city` VALUES (2082, '420203', '西塞山区', 2, 228, '西塞山区', 0);
INSERT INTO `dic_city` VALUES (2083, '420204', '下陆区', 2, 228, '下陆区', 0);
INSERT INTO `dic_city` VALUES (2084, '420205', '铁山区', 2, 228, '铁山区', 0);
INSERT INTO `dic_city` VALUES (2085, '420222', '阳新县', 2, 228, '阳新县', 0);
INSERT INTO `dic_city` VALUES (2086, '420281', '大冶市', 2, 228, '大冶市', 0);
INSERT INTO `dic_city` VALUES (2087, '420302', '茅箭区', 2, 229, '茅箭区', 0);
INSERT INTO `dic_city` VALUES (2088, '420303', '张湾区', 2, 229, '张湾区', 0);
INSERT INTO `dic_city` VALUES (2089, '420321', '郧县', 2, 229, '郧县', 0);
INSERT INTO `dic_city` VALUES (2090, '420322', '郧西县', 2, 229, '郧西县', 0);
INSERT INTO `dic_city` VALUES (2091, '420323', '竹山县', 2, 229, '竹山县', 0);
INSERT INTO `dic_city` VALUES (2092, '420324', '竹溪县', 2, 229, '竹溪县', 0);
INSERT INTO `dic_city` VALUES (2093, '420325', '房县', 2, 229, '房县', 0);
INSERT INTO `dic_city` VALUES (2094, '420381', '丹江口市', 2, 229, '丹江口市', 0);
INSERT INTO `dic_city` VALUES (2095, '420502', '西陵区', 2, 230, '西陵区', 0);
INSERT INTO `dic_city` VALUES (2096, '420503', '伍家岗区', 2, 230, '伍家岗区', 0);
INSERT INTO `dic_city` VALUES (2097, '420504', '点军区', 2, 230, '点军区', 0);
INSERT INTO `dic_city` VALUES (2098, '420505', '猇亭区', 2, 230, '猇亭区', 0);
INSERT INTO `dic_city` VALUES (2099, '420506', '夷陵区', 2, 230, '夷陵区', 0);
INSERT INTO `dic_city` VALUES (2100, '420525', '远安县', 2, 230, '远安县', 0);
INSERT INTO `dic_city` VALUES (2101, '420526', '兴山县', 2, 230, '兴山县', 0);
INSERT INTO `dic_city` VALUES (2102, '420527', '秭归县', 2, 230, '秭归县', 0);
INSERT INTO `dic_city` VALUES (2103, '420528', '长阳土家族自治县', 2, 230, '长阳土家族自治县', 0);
INSERT INTO `dic_city` VALUES (2104, '420529', '五峰土家族自治县', 2, 230, '五峰土家族自治县', 0);
INSERT INTO `dic_city` VALUES (2105, '420581', '宜都市', 2, 230, '宜都市', 0);
INSERT INTO `dic_city` VALUES (2106, '420582', '当阳市', 2, 230, '当阳市', 0);
INSERT INTO `dic_city` VALUES (2107, '420583', '枝江市', 2, 230, '枝江市', 0);
INSERT INTO `dic_city` VALUES (2108, '420602', '襄城区', 2, 231, '襄城区', 0);
INSERT INTO `dic_city` VALUES (2109, '420606', '樊城区', 2, 231, '樊城区', 0);
INSERT INTO `dic_city` VALUES (2110, '420607', '襄阳区', 2, 231, '襄阳区', 0);
INSERT INTO `dic_city` VALUES (2111, '420624', '南漳县', 2, 231, '南漳县', 0);
INSERT INTO `dic_city` VALUES (2112, '420625', '谷城县', 2, 231, '谷城县', 0);
INSERT INTO `dic_city` VALUES (2113, '420626', '保康县', 2, 231, '保康县', 0);
INSERT INTO `dic_city` VALUES (2114, '420682', '老河口市', 2, 231, '老河口市', 0);
INSERT INTO `dic_city` VALUES (2115, '420683', '枣阳市', 2, 231, '枣阳市', 0);
INSERT INTO `dic_city` VALUES (2116, '420684', '宜城市', 2, 231, '宜城市', 0);
INSERT INTO `dic_city` VALUES (2117, '420702', '梁子湖区', 2, 232, '梁子湖区', 0);
INSERT INTO `dic_city` VALUES (2118, '420703', '华容区', 2, 232, '华容区', 0);
INSERT INTO `dic_city` VALUES (2119, '420704', '鄂城区', 2, 232, '鄂城区', 0);
INSERT INTO `dic_city` VALUES (2120, '420802', '东宝区', 2, 233, '东宝区', 0);
INSERT INTO `dic_city` VALUES (2121, '420804', '掇刀区', 2, 233, '掇刀区', 0);
INSERT INTO `dic_city` VALUES (2122, '420821', '京山县', 2, 233, '京山县', 0);
INSERT INTO `dic_city` VALUES (2123, '420822', '沙洋县', 2, 233, '沙洋县', 0);
INSERT INTO `dic_city` VALUES (2124, '420881', '钟祥市', 2, 233, '钟祥市', 0);
INSERT INTO `dic_city` VALUES (2125, '420902', '孝南区', 2, 234, '孝南区', 0);
INSERT INTO `dic_city` VALUES (2126, '420921', '孝昌县', 2, 234, '孝昌县', 0);
INSERT INTO `dic_city` VALUES (2127, '420922', '大悟县', 2, 234, '大悟县', 0);
INSERT INTO `dic_city` VALUES (2128, '420923', '云梦县', 2, 234, '云梦县', 0);
INSERT INTO `dic_city` VALUES (2129, '420981', '应城市', 2, 234, '应城市', 0);
INSERT INTO `dic_city` VALUES (2130, '420982', '安陆市', 2, 234, '安陆市', 0);
INSERT INTO `dic_city` VALUES (2131, '420984', '汉川市', 2, 234, '汉川市', 0);
INSERT INTO `dic_city` VALUES (2132, '421002', '沙市区', 2, 235, '沙市区', 0);
INSERT INTO `dic_city` VALUES (2133, '421003', '荆州区', 2, 235, '荆州区', 0);
INSERT INTO `dic_city` VALUES (2134, '421022', '公安县', 2, 235, '公安县', 0);
INSERT INTO `dic_city` VALUES (2135, '421023', '监利县', 2, 235, '监利县', 0);
INSERT INTO `dic_city` VALUES (2136, '421024', '江陵县', 2, 235, '江陵县', 0);
INSERT INTO `dic_city` VALUES (2137, '421081', '石首市', 2, 235, '石首市', 0);
INSERT INTO `dic_city` VALUES (2138, '421083', '洪湖市', 2, 235, '洪湖市', 0);
INSERT INTO `dic_city` VALUES (2139, '421087', '松滋市', 2, 235, '松滋市', 0);
INSERT INTO `dic_city` VALUES (2140, '421102', '黄州区', 2, 236, '黄州区', 0);
INSERT INTO `dic_city` VALUES (2141, '421121', '团风县', 2, 236, '团风县', 0);
INSERT INTO `dic_city` VALUES (2142, '421122', '红安县', 2, 236, '红安县', 0);
INSERT INTO `dic_city` VALUES (2143, '421123', '罗田县', 2, 236, '罗田县', 0);
INSERT INTO `dic_city` VALUES (2144, '421124', '英山县', 2, 236, '英山县', 0);
INSERT INTO `dic_city` VALUES (2145, '421125', '浠水县', 2, 236, '浠水县', 0);
INSERT INTO `dic_city` VALUES (2146, '421126', '蕲春县', 2, 236, '蕲春县', 0);
INSERT INTO `dic_city` VALUES (2147, '421127', '黄梅县', 2, 236, '黄梅县', 0);
INSERT INTO `dic_city` VALUES (2148, '421181', '麻城市', 2, 236, '麻城市', 0);
INSERT INTO `dic_city` VALUES (2149, '421182', '武穴市', 2, 236, '武穴市', 0);
INSERT INTO `dic_city` VALUES (2150, '421202', '咸安区', 2, 237, '咸安区', 0);
INSERT INTO `dic_city` VALUES (2151, '421221', '嘉鱼县', 2, 237, '嘉鱼县', 0);
INSERT INTO `dic_city` VALUES (2152, '421222', '通城县', 2, 237, '通城县', 0);
INSERT INTO `dic_city` VALUES (2153, '421223', '崇阳县', 2, 237, '崇阳县', 0);
INSERT INTO `dic_city` VALUES (2154, '421224', '通山县', 2, 237, '通山县', 0);
INSERT INTO `dic_city` VALUES (2155, '421281', '赤壁市', 2, 237, '赤壁市', 0);
INSERT INTO `dic_city` VALUES (2156, '421302', '曾都区', 2, 238, '曾都区', 0);
INSERT INTO `dic_city` VALUES (2157, '421381', '广水市', 2, 238, '广水市', 0);
INSERT INTO `dic_city` VALUES (2158, '422801', '恩施市', 2, 239, '恩施市', 0);
INSERT INTO `dic_city` VALUES (2159, '422802', '利川市', 2, 239, '利川市', 0);
INSERT INTO `dic_city` VALUES (2160, '422822', '建始县', 2, 239, '建始县', 0);
INSERT INTO `dic_city` VALUES (2161, '422823', '巴东县', 2, 239, '巴东县', 0);
INSERT INTO `dic_city` VALUES (2162, '422825', '宣恩县', 2, 239, '宣恩县', 0);
INSERT INTO `dic_city` VALUES (2163, '422826', '咸丰县', 2, 239, '咸丰县', 0);
INSERT INTO `dic_city` VALUES (2164, '422827', '来凤县', 2, 239, '来凤县', 0);
INSERT INTO `dic_city` VALUES (2165, '422828', '鹤峰县', 2, 239, '鹤峰县', 0);
INSERT INTO `dic_city` VALUES (2166, '430102', '芙蓉区', 2, 244, '芙蓉区', 0);
INSERT INTO `dic_city` VALUES (2167, '430103', '天心区', 2, 244, '天心区', 0);
INSERT INTO `dic_city` VALUES (2168, '430104', '岳麓区', 2, 244, '岳麓区', 0);
INSERT INTO `dic_city` VALUES (2169, '430105', '开福区', 2, 244, '开福区', 0);
INSERT INTO `dic_city` VALUES (2170, '430111', '雨花区', 2, 244, '雨花区', 0);
INSERT INTO `dic_city` VALUES (2171, '430121', '长沙县', 2, 244, '长沙县', 0);
INSERT INTO `dic_city` VALUES (2172, '430122', '望城县', 2, 244, '望城县', 0);
INSERT INTO `dic_city` VALUES (2173, '430124', '宁乡县', 2, 244, '宁乡县', 0);
INSERT INTO `dic_city` VALUES (2174, '430181', '浏阳市', 2, 244, '浏阳市', 0);
INSERT INTO `dic_city` VALUES (2175, '430202', '荷塘区', 2, 245, '荷塘区', 0);
INSERT INTO `dic_city` VALUES (2176, '430203', '芦淞区', 2, 245, '芦淞区', 0);
INSERT INTO `dic_city` VALUES (2177, '430204', '石峰区', 2, 245, '石峰区', 0);
INSERT INTO `dic_city` VALUES (2178, '430211', '天元区', 2, 245, '天元区', 0);
INSERT INTO `dic_city` VALUES (2179, '430221', '株洲县', 2, 245, '株洲县', 0);
INSERT INTO `dic_city` VALUES (2180, '430223', '攸县', 2, 245, '攸县', 0);
INSERT INTO `dic_city` VALUES (2181, '430224', '茶陵县', 2, 245, '茶陵县', 0);
INSERT INTO `dic_city` VALUES (2182, '430225', '炎陵县', 2, 245, '炎陵县', 0);
INSERT INTO `dic_city` VALUES (2183, '430281', '醴陵市', 2, 245, '醴陵市', 0);
INSERT INTO `dic_city` VALUES (2184, '430302', '雨湖区', 2, 246, '雨湖区', 0);
INSERT INTO `dic_city` VALUES (2185, '430304', '岳塘区', 2, 246, '岳塘区', 0);
INSERT INTO `dic_city` VALUES (2186, '430321', '湘潭县', 2, 246, '湘潭县', 0);
INSERT INTO `dic_city` VALUES (2187, '430381', '湘乡市', 2, 246, '湘乡市', 0);
INSERT INTO `dic_city` VALUES (2188, '430382', '韶山市', 2, 246, '韶山市', 0);
INSERT INTO `dic_city` VALUES (2189, '430405', '珠晖区', 2, 247, '珠晖区', 0);
INSERT INTO `dic_city` VALUES (2190, '430406', '雁峰区', 2, 247, '雁峰区', 0);
INSERT INTO `dic_city` VALUES (2191, '430407', '石鼓区', 2, 247, '石鼓区', 0);
INSERT INTO `dic_city` VALUES (2192, '430408', '蒸湘区', 2, 247, '蒸湘区', 0);
INSERT INTO `dic_city` VALUES (2193, '430412', '南岳区', 2, 247, '南岳区', 0);
INSERT INTO `dic_city` VALUES (2194, '430421', '衡阳县', 2, 247, '衡阳县', 0);
INSERT INTO `dic_city` VALUES (2195, '430422', '衡南县', 2, 247, '衡南县', 0);
INSERT INTO `dic_city` VALUES (2196, '430423', '衡山县', 2, 247, '衡山县', 0);
INSERT INTO `dic_city` VALUES (2197, '430424', '衡东县', 2, 247, '衡东县', 0);
INSERT INTO `dic_city` VALUES (2198, '430426', '祁东县', 2, 247, '祁东县', 0);
INSERT INTO `dic_city` VALUES (2199, '430481', '耒阳市', 2, 247, '耒阳市', 0);
INSERT INTO `dic_city` VALUES (2200, '430482', '常宁市', 2, 247, '常宁市', 0);
INSERT INTO `dic_city` VALUES (2201, '430502', '双清区', 2, 248, '双清区', 0);
INSERT INTO `dic_city` VALUES (2202, '430503', '大祥区', 2, 248, '大祥区', 0);
INSERT INTO `dic_city` VALUES (2203, '430511', '北塔区', 2, 248, '北塔区', 0);
INSERT INTO `dic_city` VALUES (2204, '430521', '邵东县', 2, 248, '邵东县', 0);
INSERT INTO `dic_city` VALUES (2205, '430522', '新邵县', 2, 248, '新邵县', 0);
INSERT INTO `dic_city` VALUES (2206, '430523', '邵阳县', 2, 248, '邵阳县', 0);
INSERT INTO `dic_city` VALUES (2207, '430524', '隆回县', 2, 248, '隆回县', 0);
INSERT INTO `dic_city` VALUES (2208, '430525', '洞口县', 2, 248, '洞口县', 0);
INSERT INTO `dic_city` VALUES (2209, '430527', '绥宁县', 2, 248, '绥宁县', 0);
INSERT INTO `dic_city` VALUES (2210, '430528', '新宁县', 2, 248, '新宁县', 0);
INSERT INTO `dic_city` VALUES (2211, '430529', '城步苗族自治县', 2, 248, '城步苗族自治县', 0);
INSERT INTO `dic_city` VALUES (2212, '430581', '武冈市', 2, 248, '武冈市', 0);
INSERT INTO `dic_city` VALUES (2213, '430602', '岳阳楼区', 2, 249, '岳阳楼区', 0);
INSERT INTO `dic_city` VALUES (2214, '430603', '云溪区', 2, 249, '云溪区', 0);
INSERT INTO `dic_city` VALUES (2215, '430611', '君山区', 2, 249, '君山区', 0);
INSERT INTO `dic_city` VALUES (2216, '430621', '岳阳县', 2, 249, '岳阳县', 0);
INSERT INTO `dic_city` VALUES (2217, '430623', '华容县', 2, 249, '华容县', 0);
INSERT INTO `dic_city` VALUES (2218, '430624', '湘阴县', 2, 249, '湘阴县', 0);
INSERT INTO `dic_city` VALUES (2219, '430626', '平江县', 2, 249, '平江县', 0);
INSERT INTO `dic_city` VALUES (2220, '430681', '汨罗市', 2, 249, '汨罗市', 0);
INSERT INTO `dic_city` VALUES (2221, '430682', '临湘市', 2, 249, '临湘市', 0);
INSERT INTO `dic_city` VALUES (2222, '430702', '武陵区', 2, 250, '武陵区', 0);
INSERT INTO `dic_city` VALUES (2223, '430703', '鼎城区', 2, 250, '鼎城区', 0);
INSERT INTO `dic_city` VALUES (2224, '430721', '安乡县', 2, 250, '安乡县', 0);
INSERT INTO `dic_city` VALUES (2225, '430722', '汉寿县', 2, 250, '汉寿县', 0);
INSERT INTO `dic_city` VALUES (2226, '430723', '澧县', 2, 250, '澧县', 0);
INSERT INTO `dic_city` VALUES (2227, '430724', '临澧县', 2, 250, '临澧县', 0);
INSERT INTO `dic_city` VALUES (2228, '430725', '桃源县', 2, 250, '桃源县', 0);
INSERT INTO `dic_city` VALUES (2229, '430726', '石门县', 2, 250, '石门县', 0);
INSERT INTO `dic_city` VALUES (2230, '430781', '津市市', 2, 250, '津市市', 0);
INSERT INTO `dic_city` VALUES (2231, '430802', '永定区', 2, 251, '永定区', 0);
INSERT INTO `dic_city` VALUES (2232, '430811', '武陵源区', 2, 251, '武陵源区', 0);
INSERT INTO `dic_city` VALUES (2233, '430821', '慈利县', 2, 251, '慈利县', 0);
INSERT INTO `dic_city` VALUES (2234, '430822', '桑植县', 2, 251, '桑植县', 0);
INSERT INTO `dic_city` VALUES (2235, '430902', '资阳区', 2, 252, '资阳区', 0);
INSERT INTO `dic_city` VALUES (2236, '430903', '赫山区', 2, 252, '赫山区', 0);
INSERT INTO `dic_city` VALUES (2237, '430921', '南县', 2, 252, '南县', 0);
INSERT INTO `dic_city` VALUES (2238, '430922', '桃江县', 2, 252, '桃江县', 0);
INSERT INTO `dic_city` VALUES (2239, '430923', '安化县', 2, 252, '安化县', 0);
INSERT INTO `dic_city` VALUES (2240, '430981', '沅江市', 2, 252, '沅江市', 0);
INSERT INTO `dic_city` VALUES (2241, '431002', '北湖区', 2, 253, '北湖区', 0);
INSERT INTO `dic_city` VALUES (2242, '431003', '苏仙区', 2, 253, '苏仙区', 0);
INSERT INTO `dic_city` VALUES (2243, '431021', '桂阳县', 2, 253, '桂阳县', 0);
INSERT INTO `dic_city` VALUES (2244, '431022', '宜章县', 2, 253, '宜章县', 0);
INSERT INTO `dic_city` VALUES (2245, '431023', '永兴县', 2, 253, '永兴县', 0);
INSERT INTO `dic_city` VALUES (2246, '431024', '嘉禾县', 2, 253, '嘉禾县', 0);
INSERT INTO `dic_city` VALUES (2247, '431025', '临武县', 2, 253, '临武县', 0);
INSERT INTO `dic_city` VALUES (2248, '431026', '汝城县', 2, 253, '汝城县', 0);
INSERT INTO `dic_city` VALUES (2249, '431027', '桂东县', 2, 253, '桂东县', 0);
INSERT INTO `dic_city` VALUES (2250, '431028', '安仁县', 2, 253, '安仁县', 0);
INSERT INTO `dic_city` VALUES (2251, '431081', '资兴市', 2, 253, '资兴市', 0);
INSERT INTO `dic_city` VALUES (2252, '431102', '芝山区', 2, 254, '芝山区', 0);
INSERT INTO `dic_city` VALUES (2253, '431103', '冷水滩区', 2, 254, '冷水滩区', 0);
INSERT INTO `dic_city` VALUES (2254, '431121', '祁阳县', 2, 254, '祁阳县', 0);
INSERT INTO `dic_city` VALUES (2255, '431122', '东安县', 2, 254, '东安县', 0);
INSERT INTO `dic_city` VALUES (2256, '431123', '双牌县', 2, 254, '双牌县', 0);
INSERT INTO `dic_city` VALUES (2257, '431124', '道县', 2, 254, '道县', 0);
INSERT INTO `dic_city` VALUES (2258, '431125', '江永县', 2, 254, '江永县', 0);
INSERT INTO `dic_city` VALUES (2259, '431126', '宁远县', 2, 254, '宁远县', 0);
INSERT INTO `dic_city` VALUES (2260, '431127', '蓝山县', 2, 254, '蓝山县', 0);
INSERT INTO `dic_city` VALUES (2261, '431128', '新田县', 2, 254, '新田县', 0);
INSERT INTO `dic_city` VALUES (2262, '431129', '江华瑶族自治县', 2, 254, '江华瑶族自治县', 0);
INSERT INTO `dic_city` VALUES (2263, '431202', '鹤城区', 2, 255, '鹤城区', 0);
INSERT INTO `dic_city` VALUES (2264, '431221', '中方县', 2, 255, '中方县', 0);
INSERT INTO `dic_city` VALUES (2265, '431222', '沅陵县', 2, 255, '沅陵县', 0);
INSERT INTO `dic_city` VALUES (2266, '431223', '辰溪县', 2, 255, '辰溪县', 0);
INSERT INTO `dic_city` VALUES (2267, '431224', '溆浦县', 2, 255, '溆浦县', 0);
INSERT INTO `dic_city` VALUES (2268, '431225', '会同县', 2, 255, '会同县', 0);
INSERT INTO `dic_city` VALUES (2269, '431226', '麻阳苗族自治县', 2, 255, '麻阳苗族自治县', 0);
INSERT INTO `dic_city` VALUES (2270, '431227', '新晃侗族自治县', 2, 255, '新晃侗族自治县', 0);
INSERT INTO `dic_city` VALUES (2271, '431228', '芷江侗族自治县', 2, 255, '芷江侗族自治县', 0);
INSERT INTO `dic_city` VALUES (2272, '431229', '靖州苗族侗族自治县', 2, 255, '靖州苗族侗族自治县', 0);
INSERT INTO `dic_city` VALUES (2273, '431230', '通道侗族自治县', 2, 255, '通道侗族自治县', 0);
INSERT INTO `dic_city` VALUES (2274, '431281', '洪江市', 2, 255, '洪江市', 0);
INSERT INTO `dic_city` VALUES (2275, '431302', '娄星区', 2, 256, '娄星区', 0);
INSERT INTO `dic_city` VALUES (2276, '431321', '双峰县', 2, 256, '双峰县', 0);
INSERT INTO `dic_city` VALUES (2277, '431322', '新化县', 2, 256, '新化县', 0);
INSERT INTO `dic_city` VALUES (2278, '431381', '冷水江市', 2, 256, '冷水江市', 0);
INSERT INTO `dic_city` VALUES (2279, '431382', '涟源市', 2, 256, '涟源市', 0);
INSERT INTO `dic_city` VALUES (2280, '433101', '吉首市', 2, 257, '吉首市', 0);
INSERT INTO `dic_city` VALUES (2281, '433122', '泸溪县', 2, 257, '泸溪县', 0);
INSERT INTO `dic_city` VALUES (2282, '433123', '凤凰县', 2, 257, '凤凰县', 0);
INSERT INTO `dic_city` VALUES (2283, '433124', '花垣县', 2, 257, '花垣县', 0);
INSERT INTO `dic_city` VALUES (2284, '433125', '保靖县', 2, 257, '保靖县', 0);
INSERT INTO `dic_city` VALUES (2285, '433126', '古丈县', 2, 257, '古丈县', 0);
INSERT INTO `dic_city` VALUES (2286, '433127', '永顺县', 2, 257, '永顺县', 0);
INSERT INTO `dic_city` VALUES (2287, '433130', '龙山县', 2, 257, '龙山县', 0);
INSERT INTO `dic_city` VALUES (2288, '440102', '东山区', 2, 258, '东山区', 0);
INSERT INTO `dic_city` VALUES (2289, '440103', '荔湾区', 2, 258, '荔湾区', 0);
INSERT INTO `dic_city` VALUES (2290, '440104', '越秀区', 2, 258, '越秀区', 0);
INSERT INTO `dic_city` VALUES (2291, '440105', '海珠区', 2, 258, '海珠区', 0);
INSERT INTO `dic_city` VALUES (2292, '440106', '天河区', 2, 258, '天河区', 0);
INSERT INTO `dic_city` VALUES (2293, '440107', '芳村区', 2, 258, '芳村区', 0);
INSERT INTO `dic_city` VALUES (2294, '520113', '白云区', 2, 258, '白云区', 0);
INSERT INTO `dic_city` VALUES (2295, '440112', '黄埔区', 2, 258, '黄埔区', 0);
INSERT INTO `dic_city` VALUES (2296, '440113', '番禺区', 2, 258, '番禺区', 0);
INSERT INTO `dic_city` VALUES (2297, '440114', '花都区', 2, 258, '花都区', 0);
INSERT INTO `dic_city` VALUES (2298, '440183', '增城市', 2, 258, '增城市', 0);
INSERT INTO `dic_city` VALUES (2299, '440184', '从化市', 2, 258, '从化市', 0);
INSERT INTO `dic_city` VALUES (2300, '440202', '北江区', 2, 259, '北江区', 0);
INSERT INTO `dic_city` VALUES (2301, '440203', '武江区', 2, 259, '武江区', 0);
INSERT INTO `dic_city` VALUES (2302, '440204', '浈江区', 2, 259, '浈江区', 0);
INSERT INTO `dic_city` VALUES (2303, '440221', '曲江县', 2, 259, '曲江县', 0);
INSERT INTO `dic_city` VALUES (2304, '440222', '始兴县', 2, 259, '始兴县', 0);
INSERT INTO `dic_city` VALUES (2305, '440224', '仁化县', 2, 259, '仁化县', 0);
INSERT INTO `dic_city` VALUES (2306, '440229', '翁源县', 2, 259, '翁源县', 0);
INSERT INTO `dic_city` VALUES (2307, '440232', '乳源瑶族自治县', 2, 259, '乳源瑶族自治县', 0);
INSERT INTO `dic_city` VALUES (2308, '440233', '新丰县', 2, 259, '新丰县', 0);
INSERT INTO `dic_city` VALUES (2309, '440281', '乐昌市', 2, 259, '乐昌市', 0);
INSERT INTO `dic_city` VALUES (2310, '440282', '南雄市', 2, 259, '南雄市', 0);
INSERT INTO `dic_city` VALUES (2311, '440303', '罗湖区', 2, 260, '罗湖区', 0);
INSERT INTO `dic_city` VALUES (2312, '440304', '福田区', 2, 260, '福田区', 0);
INSERT INTO `dic_city` VALUES (2313, '440305', '南山区', 2, 260, '南山区', 0);
INSERT INTO `dic_city` VALUES (2314, '440306', '宝安区', 2, 260, '宝安区', 0);
INSERT INTO `dic_city` VALUES (2315, '440307', '龙岗区', 2, 260, '龙岗区', 0);
INSERT INTO `dic_city` VALUES (2316, '440308', '盐田区', 2, 260, '盐田区', 0);
INSERT INTO `dic_city` VALUES (2317, '440402', '香洲区', 2, 261, '香洲区', 0);
INSERT INTO `dic_city` VALUES (2318, '440403', '斗门区', 2, 261, '斗门区', 0);
INSERT INTO `dic_city` VALUES (2319, '440404', '金湾区', 2, 261, '金湾区', 0);
INSERT INTO `dic_city` VALUES (2320, '440506', '达濠区', 2, 262, '达濠区', 0);
INSERT INTO `dic_city` VALUES (2321, '440507', '龙湖区', 2, 262, '龙湖区', 0);
INSERT INTO `dic_city` VALUES (2322, '440508', '金园区', 2, 262, '金园区', 0);
INSERT INTO `dic_city` VALUES (2323, '440509', '升平区', 2, 262, '升平区', 0);
INSERT INTO `dic_city` VALUES (2324, '440510', '河浦区', 2, 262, '河浦区', 0);
INSERT INTO `dic_city` VALUES (2325, '440523', '南澳县', 2, 262, '南澳县', 0);
INSERT INTO `dic_city` VALUES (2326, '440582', '潮阳市', 2, 262, '潮阳市', 0);
INSERT INTO `dic_city` VALUES (2327, '440583', '澄海市', 2, 262, '澄海市', 0);
INSERT INTO `dic_city` VALUES (2328, '440604', '禅城区', 2, 263, '禅城区', 0);
INSERT INTO `dic_city` VALUES (2329, '440605', '南海区', 2, 263, '南海区', 0);
INSERT INTO `dic_city` VALUES (2330, '440606', '顺德区', 2, 263, '顺德区', 0);
INSERT INTO `dic_city` VALUES (2331, '440607', '三水区', 2, 263, '三水区', 0);
INSERT INTO `dic_city` VALUES (2332, '440608', '高明区', 2, 263, '高明区', 0);
INSERT INTO `dic_city` VALUES (2333, '440703', '蓬江区', 2, 264, '蓬江区', 0);
INSERT INTO `dic_city` VALUES (2334, '440704', '江海区', 2, 264, '江海区', 0);
INSERT INTO `dic_city` VALUES (2335, '440705', '新会区', 2, 264, '新会区', 0);
INSERT INTO `dic_city` VALUES (2336, '440781', '台山市', 2, 264, '台山市', 0);
INSERT INTO `dic_city` VALUES (2337, '440783', '开平市', 2, 264, '开平市', 0);
INSERT INTO `dic_city` VALUES (2338, '440784', '鹤山市', 2, 264, '鹤山市', 0);
INSERT INTO `dic_city` VALUES (2339, '440785', '恩平市', 2, 264, '恩平市', 0);
INSERT INTO `dic_city` VALUES (2340, '440802', '赤坎区', 2, 265, '赤坎区', 0);
INSERT INTO `dic_city` VALUES (2341, '440803', '霞山区', 2, 265, '霞山区', 0);
INSERT INTO `dic_city` VALUES (2342, '440804', '坡头区', 2, 265, '坡头区', 0);
INSERT INTO `dic_city` VALUES (2343, '440811', '麻章区', 2, 265, '麻章区', 0);
INSERT INTO `dic_city` VALUES (2344, '440823', '遂溪县', 2, 265, '遂溪县', 0);
INSERT INTO `dic_city` VALUES (2345, '440825', '徐闻县', 2, 265, '徐闻县', 0);
INSERT INTO `dic_city` VALUES (2346, '440881', '廉江市', 2, 265, '廉江市', 0);
INSERT INTO `dic_city` VALUES (2347, '440882', '雷州市', 2, 265, '雷州市', 0);
INSERT INTO `dic_city` VALUES (2348, '440883', '吴川市', 2, 265, '吴川市', 0);
INSERT INTO `dic_city` VALUES (2349, '440902', '茂南区', 2, 266, '茂南区', 0);
INSERT INTO `dic_city` VALUES (2350, '440903', '茂港区', 2, 266, '茂港区', 0);
INSERT INTO `dic_city` VALUES (2351, '440923', '电白县', 2, 266, '电白县', 0);
INSERT INTO `dic_city` VALUES (2352, '440981', '高州市', 2, 266, '高州市', 0);
INSERT INTO `dic_city` VALUES (2353, '440982', '化州市', 2, 266, '化州市', 0);
INSERT INTO `dic_city` VALUES (2354, '440983', '信宜市', 2, 266, '信宜市', 0);
INSERT INTO `dic_city` VALUES (2355, '441202', '端州区', 2, 267, '端州区', 0);
INSERT INTO `dic_city` VALUES (2356, '441203', '鼎湖区', 2, 267, '鼎湖区', 0);
INSERT INTO `dic_city` VALUES (2357, '441223', '广宁县', 2, 267, '广宁县', 0);
INSERT INTO `dic_city` VALUES (2358, '441224', '怀集县', 2, 267, '怀集县', 0);
INSERT INTO `dic_city` VALUES (2359, '441225', '封开县', 2, 267, '封开县', 0);
INSERT INTO `dic_city` VALUES (2360, '441226', '德庆县', 2, 267, '德庆县', 0);
INSERT INTO `dic_city` VALUES (2361, '441283', '高要市', 2, 267, '高要市', 0);
INSERT INTO `dic_city` VALUES (2362, '441284', '四会市', 2, 267, '四会市', 0);
INSERT INTO `dic_city` VALUES (2363, '441302', '惠城区', 2, 268, '惠城区', 0);
INSERT INTO `dic_city` VALUES (2364, '441322', '博罗县', 2, 268, '博罗县', 0);
INSERT INTO `dic_city` VALUES (2365, '441323', '惠东县', 2, 268, '惠东县', 0);
INSERT INTO `dic_city` VALUES (2366, '441324', '龙门县', 2, 268, '龙门县', 0);
INSERT INTO `dic_city` VALUES (2367, '441381', '惠阳市', 2, 268, '惠阳市', 0);
INSERT INTO `dic_city` VALUES (2368, '441402', '梅江区', 2, 269, '梅江区', 0);
INSERT INTO `dic_city` VALUES (2369, '441421', '梅县', 2, 269, '梅县', 0);
INSERT INTO `dic_city` VALUES (2370, '441422', '大埔县', 2, 269, '大埔县', 0);
INSERT INTO `dic_city` VALUES (2371, '441423', '丰顺县', 2, 269, '丰顺县', 0);
INSERT INTO `dic_city` VALUES (2372, '441424', '五华县', 2, 269, '五华县', 0);
INSERT INTO `dic_city` VALUES (2373, '441426', '平远县', 2, 269, '平远县', 0);
INSERT INTO `dic_city` VALUES (2374, '441427', '蕉岭县', 2, 269, '蕉岭县', 0);
INSERT INTO `dic_city` VALUES (2375, '441481', '兴宁市', 2, 269, '兴宁市', 0);
INSERT INTO `dic_city` VALUES (2376, '441502', '城区', 2, 270, '城区', 0);
INSERT INTO `dic_city` VALUES (2377, '441521', '海丰县', 2, 270, '海丰县', 0);
INSERT INTO `dic_city` VALUES (2378, '441523', '陆河县', 2, 270, '陆河县', 0);
INSERT INTO `dic_city` VALUES (2379, '441581', '陆丰市', 2, 270, '陆丰市', 0);
INSERT INTO `dic_city` VALUES (2380, '441602', '源城区', 2, 271, '源城区', 0);
INSERT INTO `dic_city` VALUES (2381, '441621', '紫金县', 2, 271, '紫金县', 0);
INSERT INTO `dic_city` VALUES (2382, '441622', '龙川县', 2, 271, '龙川县', 0);
INSERT INTO `dic_city` VALUES (2383, '441623', '连平县', 2, 271, '连平县', 0);
INSERT INTO `dic_city` VALUES (2384, '441624', '和平县', 2, 271, '和平县', 0);
INSERT INTO `dic_city` VALUES (2385, '441625', '东源县', 2, 271, '东源县', 0);
INSERT INTO `dic_city` VALUES (2386, '441702', '江城区', 2, 272, '江城区', 0);
INSERT INTO `dic_city` VALUES (2387, '441721', '阳西县', 2, 272, '阳西县', 0);
INSERT INTO `dic_city` VALUES (2388, '441723', '阳东县', 2, 272, '阳东县', 0);
INSERT INTO `dic_city` VALUES (2389, '441781', '阳春市', 2, 272, '阳春市', 0);
INSERT INTO `dic_city` VALUES (2390, '441802', '清城区', 2, 273, '清城区', 0);
INSERT INTO `dic_city` VALUES (2391, '441821', '佛冈县', 2, 273, '佛冈县', 0);
INSERT INTO `dic_city` VALUES (2392, '441823', '阳山县', 2, 273, '阳山县', 0);
INSERT INTO `dic_city` VALUES (2393, '441825', '连山壮族瑶族自治县', 2, 273, '连山壮族瑶族自治县', 0);
INSERT INTO `dic_city` VALUES (2394, '441826', '连南瑶族自治县', 2, 273, '连南瑶族自治县', 0);
INSERT INTO `dic_city` VALUES (2395, '441827', '清新县', 2, 273, '清新县', 0);
INSERT INTO `dic_city` VALUES (2396, '441881', '英德市', 2, 273, '英德市', 0);
INSERT INTO `dic_city` VALUES (2397, '441882', '连州市', 2, 273, '连州市', 0);
INSERT INTO `dic_city` VALUES (2398, '445102', '湘桥区', 2, 276, '湘桥区', 0);
INSERT INTO `dic_city` VALUES (2399, '445121', '潮安县', 2, 276, '潮安县', 0);
INSERT INTO `dic_city` VALUES (2400, '445122', '饶平县', 2, 276, '饶平县', 0);
INSERT INTO `dic_city` VALUES (2401, '445202', '榕城区', 2, 277, '榕城区', 0);
INSERT INTO `dic_city` VALUES (2402, '445221', '揭东县', 2, 277, '揭东县', 0);
INSERT INTO `dic_city` VALUES (2403, '445222', '揭西县', 2, 277, '揭西县', 0);
INSERT INTO `dic_city` VALUES (2404, '445224', '惠来县', 2, 277, '惠来县', 0);
INSERT INTO `dic_city` VALUES (2405, '445281', '普宁市', 2, 277, '普宁市', 0);
INSERT INTO `dic_city` VALUES (2406, '445302', '云城区', 2, 278, '云城区', 0);
INSERT INTO `dic_city` VALUES (2407, '445321', '新兴县', 2, 278, '新兴县', 0);
INSERT INTO `dic_city` VALUES (2408, '445322', '郁南县', 2, 278, '郁南县', 0);
INSERT INTO `dic_city` VALUES (2409, '445323', '云安县', 2, 278, '云安县', 0);
INSERT INTO `dic_city` VALUES (2410, '445381', '罗定市', 2, 278, '罗定市', 0);
INSERT INTO `dic_city` VALUES (2411, '450102', '兴宁区', 2, 279, '兴宁区', 0);
INSERT INTO `dic_city` VALUES (2412, '450103', '新城区', 2, 279, '新城区', 0);
INSERT INTO `dic_city` VALUES (2413, '630105', '城北区', 2, 279, '城北区', 0);
INSERT INTO `dic_city` VALUES (2414, '450105', '江南区', 2, 279, '江南区', 0);
INSERT INTO `dic_city` VALUES (2415, '450106', '永新区', 2, 279, '永新区', 0);
INSERT INTO `dic_city` VALUES (2416, '450121', '邕宁县', 2, 279, '邕宁县', 0);
INSERT INTO `dic_city` VALUES (2417, '450122', '武鸣县', 2, 279, '武鸣县', 0);
INSERT INTO `dic_city` VALUES (2418, '450123', '隆安县', 2, 279, '隆安县', 0);
INSERT INTO `dic_city` VALUES (2419, '450124', '马山县', 2, 279, '马山县', 0);
INSERT INTO `dic_city` VALUES (2420, '450125', '上林县', 2, 279, '上林县', 0);
INSERT INTO `dic_city` VALUES (2421, '450126', '宾阳县', 2, 279, '宾阳县', 0);
INSERT INTO `dic_city` VALUES (2422, '450127', '横县', 2, 279, '横县', 0);
INSERT INTO `dic_city` VALUES (2423, '630103', '城中区', 2, 280, '城中区', 0);
INSERT INTO `dic_city` VALUES (2424, '450203', '鱼峰区', 2, 280, '鱼峰区', 0);
INSERT INTO `dic_city` VALUES (2425, '450204', '柳南区', 2, 280, '柳南区', 0);
INSERT INTO `dic_city` VALUES (2426, '450205', '柳北区', 2, 280, '柳北区', 0);
INSERT INTO `dic_city` VALUES (2427, '450221', '柳江县', 2, 280, '柳江县', 0);
INSERT INTO `dic_city` VALUES (2428, '450222', '柳城县', 2, 280, '柳城县', 0);
INSERT INTO `dic_city` VALUES (2429, '450223', '鹿寨县', 2, 280, '鹿寨县', 0);
INSERT INTO `dic_city` VALUES (2430, '450224', '融安县', 2, 280, '融安县', 0);
INSERT INTO `dic_city` VALUES (2431, '450225', '融水苗族自治县', 2, 280, '融水苗族自治县', 0);
INSERT INTO `dic_city` VALUES (2432, '450226', '三江侗族自治县', 2, 280, '三江侗族自治县', 0);
INSERT INTO `dic_city` VALUES (2433, '450302', '秀峰区', 2, 281, '秀峰区', 0);
INSERT INTO `dic_city` VALUES (2434, '450303', '叠彩区', 2, 281, '叠彩区', 0);
INSERT INTO `dic_city` VALUES (2435, '450304', '象山区', 2, 281, '象山区', 0);
INSERT INTO `dic_city` VALUES (2436, '450305', '七星区', 2, 281, '七星区', 0);
INSERT INTO `dic_city` VALUES (2437, '450311', '雁山区', 2, 281, '雁山区', 0);
INSERT INTO `dic_city` VALUES (2438, '450321', '阳朔县', 2, 281, '阳朔县', 0);
INSERT INTO `dic_city` VALUES (2439, '450322', '临桂县', 2, 281, '临桂县', 0);
INSERT INTO `dic_city` VALUES (2440, '450323', '灵川县', 2, 281, '灵川县', 0);
INSERT INTO `dic_city` VALUES (2441, '450324', '全州县', 2, 281, '全州县', 0);
INSERT INTO `dic_city` VALUES (2442, '450325', '兴安县', 2, 281, '兴安县', 0);
INSERT INTO `dic_city` VALUES (2443, '450326', '永福县', 2, 281, '永福县', 0);
INSERT INTO `dic_city` VALUES (2444, '450327', '灌阳县', 2, 281, '灌阳县', 0);
INSERT INTO `dic_city` VALUES (2445, '450328', '龙胜各族自治县', 2, 281, '龙胜各族自治县', 0);
INSERT INTO `dic_city` VALUES (2446, '450329', '资源县', 2, 281, '资源县', 0);
INSERT INTO `dic_city` VALUES (2447, '450330', '平乐县', 2, 281, '平乐县', 0);
INSERT INTO `dic_city` VALUES (2448, '450331', '荔浦县', 2, 281, '荔浦县', 0);
INSERT INTO `dic_city` VALUES (2449, '450332', '恭城瑶族自治县', 2, 281, '恭城瑶族自治县', 0);
INSERT INTO `dic_city` VALUES (2450, '450403', '万秀区', 2, 282, '万秀区', 0);
INSERT INTO `dic_city` VALUES (2451, '450404', '蝶山区', 2, 282, '蝶山区', 0);
INSERT INTO `dic_city` VALUES (2452, '450411', '市郊区', 2, 282, '市郊区', 0);
INSERT INTO `dic_city` VALUES (2453, '450421', '苍梧县', 2, 282, '苍梧县', 0);
INSERT INTO `dic_city` VALUES (2454, '450422', '藤县', 2, 282, '藤县', 0);
INSERT INTO `dic_city` VALUES (2455, '450423', '蒙山县', 2, 282, '蒙山县', 0);
INSERT INTO `dic_city` VALUES (2456, '450481', '岑溪市', 2, 282, '岑溪市', 0);
INSERT INTO `dic_city` VALUES (2457, '450502', '海城区', 2, 283, '海城区', 0);
INSERT INTO `dic_city` VALUES (2458, '450503', '银海区', 2, 283, '银海区', 0);
INSERT INTO `dic_city` VALUES (2459, '450512', '铁山港区', 2, 283, '铁山港区', 0);
INSERT INTO `dic_city` VALUES (2460, '450521', '合浦县', 2, 283, '合浦县', 0);
INSERT INTO `dic_city` VALUES (2461, '450602', '港口区', 2, 284, '港口区', 0);
INSERT INTO `dic_city` VALUES (2462, '450603', '防城区', 2, 284, '防城区', 0);
INSERT INTO `dic_city` VALUES (2463, '450621', '上思县', 2, 284, '上思县', 0);
INSERT INTO `dic_city` VALUES (2464, '450681', '东兴市', 2, 284, '东兴市', 0);
INSERT INTO `dic_city` VALUES (2465, '450702', '钦南区', 2, 285, '钦南区', 0);
INSERT INTO `dic_city` VALUES (2466, '450703', '钦北区', 2, 285, '钦北区', 0);
INSERT INTO `dic_city` VALUES (2467, '450721', '灵山县', 2, 285, '灵山县', 0);
INSERT INTO `dic_city` VALUES (2468, '450722', '浦北县', 2, 285, '浦北县', 0);
INSERT INTO `dic_city` VALUES (2469, '450802', '港北区', 2, 286, '港北区', 0);
INSERT INTO `dic_city` VALUES (2470, '450803', '港南区', 2, 286, '港南区', 0);
INSERT INTO `dic_city` VALUES (2471, '450821', '平南县', 2, 286, '平南县', 0);
INSERT INTO `dic_city` VALUES (2472, '450881', '桂平市', 2, 286, '桂平市', 0);
INSERT INTO `dic_city` VALUES (2473, '450902', '玉州区', 2, 287, '玉州区', 0);
INSERT INTO `dic_city` VALUES (2474, '450921', '容县', 2, 287, '容县', 0);
INSERT INTO `dic_city` VALUES (2475, '450922', '陆川县', 2, 287, '陆川县', 0);
INSERT INTO `dic_city` VALUES (2476, '450923', '博白县', 2, 287, '博白县', 0);
INSERT INTO `dic_city` VALUES (2477, '450924', '兴业县', 2, 287, '兴业县', 0);
INSERT INTO `dic_city` VALUES (2478, '450981', '北流市', 2, 287, '北流市', 0);
INSERT INTO `dic_city` VALUES (2479, '451002', '右江区', 2, 288, '右江区', 0);
INSERT INTO `dic_city` VALUES (2480, '451021', '田阳县', 2, 288, '田阳县', 0);
INSERT INTO `dic_city` VALUES (2481, '451022', '田东县', 2, 288, '田东县', 0);
INSERT INTO `dic_city` VALUES (2482, '451023', '平果县', 2, 288, '平果县', 0);
INSERT INTO `dic_city` VALUES (2483, '451024', '德保县', 2, 288, '德保县', 0);
INSERT INTO `dic_city` VALUES (2484, '451025', '靖西县', 2, 288, '靖西县', 0);
INSERT INTO `dic_city` VALUES (2485, '451026', '那坡县', 2, 288, '那坡县', 0);
INSERT INTO `dic_city` VALUES (2486, '451027', '凌云县', 2, 288, '凌云县', 0);
INSERT INTO `dic_city` VALUES (2487, '451028', '乐业县', 2, 288, '乐业县', 0);
INSERT INTO `dic_city` VALUES (2488, '451029', '田林县', 2, 288, '田林县', 0);
INSERT INTO `dic_city` VALUES (2489, '451030', '西林县', 2, 288, '西林县', 0);
INSERT INTO `dic_city` VALUES (2490, '451031', '隆林各族自治县', 2, 288, '隆林各族自治县', 0);
INSERT INTO `dic_city` VALUES (2491, '451102', '八步区', 2, 289, '八步区', 0);
INSERT INTO `dic_city` VALUES (2492, '451121', '昭平县', 2, 289, '昭平县', 0);
INSERT INTO `dic_city` VALUES (2493, '451122', '钟山县', 2, 289, '钟山县', 0);
INSERT INTO `dic_city` VALUES (2494, '451123', '富川瑶族自治县', 2, 289, '富川瑶族自治县', 0);
INSERT INTO `dic_city` VALUES (2495, '451202', '金城江区', 2, 290, '金城江区', 0);
INSERT INTO `dic_city` VALUES (2496, '451221', '南丹县', 2, 290, '南丹县', 0);
INSERT INTO `dic_city` VALUES (2497, '451222', '天峨县', 2, 290, '天峨县', 0);
INSERT INTO `dic_city` VALUES (2498, '451223', '凤山县', 2, 290, '凤山县', 0);
INSERT INTO `dic_city` VALUES (2499, '451224', '东兰县', 2, 290, '东兰县', 0);
INSERT INTO `dic_city` VALUES (2500, '451225', '罗城仫佬族自治县', 2, 290, '罗城仫佬族自治县', 0);
INSERT INTO `dic_city` VALUES (2501, '451226', '环江毛南族自治县', 2, 290, '环江毛南族自治县', 0);
INSERT INTO `dic_city` VALUES (2502, '451227', '巴马瑶族自治县', 2, 290, '巴马瑶族自治县', 0);
INSERT INTO `dic_city` VALUES (2503, '451228', '都安瑶族自治县', 2, 290, '都安瑶族自治县', 0);
INSERT INTO `dic_city` VALUES (2504, '451229', '大化瑶族自治县', 2, 290, '大化瑶族自治县', 0);
INSERT INTO `dic_city` VALUES (2505, '451281', '宜州市', 2, 290, '宜州市', 0);
INSERT INTO `dic_city` VALUES (2506, '451302', '兴宾区', 2, 291, '兴宾区', 0);
INSERT INTO `dic_city` VALUES (2507, '451321', '忻城县', 2, 291, '忻城县', 0);
INSERT INTO `dic_city` VALUES (2508, '451322', '象州县', 2, 291, '象州县', 0);
INSERT INTO `dic_city` VALUES (2509, '451323', '武宣县', 2, 291, '武宣县', 0);
INSERT INTO `dic_city` VALUES (2510, '451324', '金秀瑶族自治县', 2, 291, '金秀瑶族自治县', 0);
INSERT INTO `dic_city` VALUES (2511, '451381', '合山市', 2, 291, '合山市', 0);
INSERT INTO `dic_city` VALUES (2512, '451421', '扶绥县', 2, 292, '扶绥县', 0);
INSERT INTO `dic_city` VALUES (2513, '451422', '宁明县', 2, 292, '宁明县', 0);
INSERT INTO `dic_city` VALUES (2514, '451423', '龙州县', 2, 292, '龙州县', 0);
INSERT INTO `dic_city` VALUES (2515, '451424', '大新县', 2, 292, '大新县', 0);
INSERT INTO `dic_city` VALUES (2516, '451425', '天等县', 2, 292, '天等县', 0);
INSERT INTO `dic_city` VALUES (2517, '451429', '江洲区', 2, 292, '江洲区', 0);
INSERT INTO `dic_city` VALUES (2518, '451481', '凭祥市', 2, 292, '凭祥市', 0);
INSERT INTO `dic_city` VALUES (2519, '460107', '琼山区', 2, 293, '琼山区', 0);
INSERT INTO `dic_city` VALUES (2520, '460105', '秀英区', 2, 293, '秀英区', 0);
INSERT INTO `dic_city` VALUES (2521, '460106', '龙华区', 2, 293, '龙华区', 0);
INSERT INTO `dic_city` VALUES (2522, '460108', '美兰区', 2, 293, '美兰区', 0);
INSERT INTO `dic_city` VALUES (2523, '510104', '锦江区', 2, 354, '锦江区', 0);
INSERT INTO `dic_city` VALUES (2524, '510105', '青羊区', 2, 354, '青羊区', 0);
INSERT INTO `dic_city` VALUES (2525, '510106', '金牛区', 2, 354, '金牛区', 0);
INSERT INTO `dic_city` VALUES (2526, '510107', '武侯区', 2, 354, '武侯区', 0);
INSERT INTO `dic_city` VALUES (2527, '510108', '成华区', 2, 354, '成华区', 0);
INSERT INTO `dic_city` VALUES (2528, '510112', '龙泉驿区', 2, 354, '龙泉驿区', 0);
INSERT INTO `dic_city` VALUES (2529, '510113', '青白江区', 2, 354, '青白江区', 0);
INSERT INTO `dic_city` VALUES (2530, '510114', '新都区', 2, 354, '新都区', 0);
INSERT INTO `dic_city` VALUES (2531, '510115', '温江区', 2, 354, '温江区', 0);
INSERT INTO `dic_city` VALUES (2532, '510121', '金堂县', 2, 354, '金堂县', 0);
INSERT INTO `dic_city` VALUES (2533, '510122', '双流县', 2, 354, '双流县', 0);
INSERT INTO `dic_city` VALUES (2534, '510124', '郫县', 2, 354, '郫县', 0);
INSERT INTO `dic_city` VALUES (2535, '510129', '大邑县', 2, 354, '大邑县', 0);
INSERT INTO `dic_city` VALUES (2536, '510131', '蒲江县', 2, 354, '蒲江县', 0);
INSERT INTO `dic_city` VALUES (2537, '510132', '新津县', 2, 354, '新津县', 0);
INSERT INTO `dic_city` VALUES (2538, '510181', '都江堰市', 2, 354, '都江堰市', 0);
INSERT INTO `dic_city` VALUES (2539, '510182', '彭州市', 2, 354, '彭州市', 0);
INSERT INTO `dic_city` VALUES (2540, '510183', '邛崃市', 2, 354, '邛崃市', 0);
INSERT INTO `dic_city` VALUES (2541, '510184', '崇州市', 2, 354, '崇州市', 0);
INSERT INTO `dic_city` VALUES (2542, '510302', '自流井区', 2, 355, '自流井区', 0);
INSERT INTO `dic_city` VALUES (2543, '510303', '贡井区', 2, 355, '贡井区', 0);
INSERT INTO `dic_city` VALUES (2544, '710105', '大安区', 2, 355, '大安区', 0);
INSERT INTO `dic_city` VALUES (2545, '510311', '沿滩区', 2, 355, '沿滩区', 0);
INSERT INTO `dic_city` VALUES (2546, '510321', '荣县', 2, 355, '荣县', 0);
INSERT INTO `dic_city` VALUES (2547, '510322', '富顺县', 2, 355, '富顺县', 0);
INSERT INTO `dic_city` VALUES (2548, '710701', '东区', 2, 356, '东区', 0);
INSERT INTO `dic_city` VALUES (2549, '710702', '西区', 2, 356, '西区', 0);
INSERT INTO `dic_city` VALUES (2550, '510411', '仁和区', 2, 356, '仁和区', 0);
INSERT INTO `dic_city` VALUES (2551, '510421', '米易县', 2, 356, '米易县', 0);
INSERT INTO `dic_city` VALUES (2552, '510422', '盐边县', 2, 356, '盐边县', 0);
INSERT INTO `dic_city` VALUES (2553, '510502', '江阳区', 2, 357, '江阳区', 0);
INSERT INTO `dic_city` VALUES (2554, '510503', '纳溪区', 2, 357, '纳溪区', 0);
INSERT INTO `dic_city` VALUES (2555, '510504', '龙马潭区', 2, 357, '龙马潭区', 0);
INSERT INTO `dic_city` VALUES (2556, '510521', '泸县', 2, 357, '泸县', 0);
INSERT INTO `dic_city` VALUES (2557, '510522', '合江县', 2, 357, '合江县', 0);
INSERT INTO `dic_city` VALUES (2558, '510524', '叙永县', 2, 357, '叙永县', 0);
INSERT INTO `dic_city` VALUES (2559, '510525', '古蔺县', 2, 357, '古蔺县', 0);
INSERT INTO `dic_city` VALUES (2560, '510603', '旌阳区', 2, 358, '旌阳区', 0);
INSERT INTO `dic_city` VALUES (2561, '510623', '中江县', 2, 358, '中江县', 0);
INSERT INTO `dic_city` VALUES (2562, '510626', '罗江县', 2, 358, '罗江县', 0);
INSERT INTO `dic_city` VALUES (2563, '510681', '广汉市', 2, 358, '广汉市', 0);
INSERT INTO `dic_city` VALUES (2564, '510682', '什邡市', 2, 358, '什邡市', 0);
INSERT INTO `dic_city` VALUES (2565, '510683', '绵竹市', 2, 358, '绵竹市', 0);
INSERT INTO `dic_city` VALUES (2566, '510703', '涪城区', 2, 359, '涪城区', 0);
INSERT INTO `dic_city` VALUES (2567, '510704', '游仙区', 2, 359, '游仙区', 0);
INSERT INTO `dic_city` VALUES (2568, '510722', '三台县', 2, 359, '三台县', 0);
INSERT INTO `dic_city` VALUES (2569, '510723', '盐亭县', 2, 359, '盐亭县', 0);
INSERT INTO `dic_city` VALUES (2570, '510724', '安县', 2, 359, '安县', 0);
INSERT INTO `dic_city` VALUES (2571, '510725', '梓潼县', 2, 359, '梓潼县', 0);
INSERT INTO `dic_city` VALUES (2572, '510726', '北川县', 2, 359, '北川县', 0);
INSERT INTO `dic_city` VALUES (2573, '510727', '平武县', 2, 359, '平武县', 0);
INSERT INTO `dic_city` VALUES (2574, '510781', '江油市', 2, 359, '江油市', 0);
INSERT INTO `dic_city` VALUES (2575, '510811', '元坝区', 2, 360, '元坝区', 0);
INSERT INTO `dic_city` VALUES (2576, '510812', '朝天区', 2, 360, '朝天区', 0);
INSERT INTO `dic_city` VALUES (2577, '510821', '旺苍县', 2, 360, '旺苍县', 0);
INSERT INTO `dic_city` VALUES (2578, '510822', '青川县', 2, 360, '青川县', 0);
INSERT INTO `dic_city` VALUES (2579, '510823', '剑阁县', 2, 360, '剑阁县', 0);
INSERT INTO `dic_city` VALUES (2580, '510824', '苍溪县', 2, 360, '苍溪县', 0);
INSERT INTO `dic_city` VALUES (2581, '510921', '蓬溪县', 2, 361, '蓬溪县', 0);
INSERT INTO `dic_city` VALUES (2582, '510922', '射洪县', 2, 361, '射洪县', 0);
INSERT INTO `dic_city` VALUES (2583, '510923', '大英县', 2, 361, '大英县', 0);
INSERT INTO `dic_city` VALUES (2584, '511011', '东兴区', 2, 362, '东兴区', 0);
INSERT INTO `dic_city` VALUES (2585, '511024', '威远县', 2, 362, '威远县', 0);
INSERT INTO `dic_city` VALUES (2586, '511025', '资中县', 2, 362, '资中县', 0);
INSERT INTO `dic_city` VALUES (2587, '511028', '隆昌县', 2, 362, '隆昌县', 0);
INSERT INTO `dic_city` VALUES (2588, '511111', '沙湾区', 2, 363, '沙湾区', 0);
INSERT INTO `dic_city` VALUES (2589, '511112', '五通桥区', 2, 363, '五通桥区', 0);
INSERT INTO `dic_city` VALUES (2590, '511113', '金口河区', 2, 363, '金口河区', 0);
INSERT INTO `dic_city` VALUES (2591, '511123', '犍为县', 2, 363, '犍为县', 0);
INSERT INTO `dic_city` VALUES (2592, '511124', '井研县', 2, 363, '井研县', 0);
INSERT INTO `dic_city` VALUES (2593, '511126', '夹江县', 2, 363, '夹江县', 0);
INSERT INTO `dic_city` VALUES (2594, '511129', '沐川县', 2, 363, '沐川县', 0);
INSERT INTO `dic_city` VALUES (2595, '511132', '峨边彝族自治县', 2, 363, '峨边彝族自治县', 0);
INSERT INTO `dic_city` VALUES (2596, '511133', '马边彝族自治县', 2, 363, '马边彝族自治县', 0);
INSERT INTO `dic_city` VALUES (2597, '511181', '峨眉山市', 2, 363, '峨眉山市', 0);
INSERT INTO `dic_city` VALUES (2598, '511302', '顺庆区', 2, 364, '顺庆区', 0);
INSERT INTO `dic_city` VALUES (2599, '511303', '高坪区', 2, 364, '高坪区', 0);
INSERT INTO `dic_city` VALUES (2600, '511304', '嘉陵区', 2, 364, '嘉陵区', 0);
INSERT INTO `dic_city` VALUES (2601, '511321', '南部县', 2, 364, '南部县', 0);
INSERT INTO `dic_city` VALUES (2602, '511322', '营山县', 2, 364, '营山县', 0);
INSERT INTO `dic_city` VALUES (2603, '511323', '蓬安县', 2, 364, '蓬安县', 0);
INSERT INTO `dic_city` VALUES (2604, '511324', '仪陇县', 2, 364, '仪陇县', 0);
INSERT INTO `dic_city` VALUES (2605, '511325', '西充县', 2, 364, '西充县', 0);
INSERT INTO `dic_city` VALUES (2606, '511381', '阆中市', 2, 364, '阆中市', 0);
INSERT INTO `dic_city` VALUES (2607, '511402', '东坡区', 2, 365, '东坡区', 0);
INSERT INTO `dic_city` VALUES (2608, '511421', '仁寿县', 2, 365, '仁寿县', 0);
INSERT INTO `dic_city` VALUES (2609, '511422', '彭山县', 2, 365, '彭山县', 0);
INSERT INTO `dic_city` VALUES (2610, '511423', '洪雅县', 2, 365, '洪雅县', 0);
INSERT INTO `dic_city` VALUES (2611, '511424', '丹棱县', 2, 365, '丹棱县', 0);
INSERT INTO `dic_city` VALUES (2612, '511425', '青神县', 2, 365, '青神县', 0);
INSERT INTO `dic_city` VALUES (2613, '511502', '翠屏区', 2, 366, '翠屏区', 0);
INSERT INTO `dic_city` VALUES (2614, '511521', '宜宾县', 2, 366, '宜宾县', 0);
INSERT INTO `dic_city` VALUES (2615, '511522', '南溪县', 2, 366, '南溪县', 0);
INSERT INTO `dic_city` VALUES (2616, '511523', '江安县', 2, 366, '江安县', 0);
INSERT INTO `dic_city` VALUES (2617, '511524', '长宁县', 2, 366, '长宁县', 0);
INSERT INTO `dic_city` VALUES (2618, '511525', '高县', 2, 366, '高县', 0);
INSERT INTO `dic_city` VALUES (2619, '511526', '珙县', 2, 366, '珙县', 0);
INSERT INTO `dic_city` VALUES (2620, '511527', '筠连县', 2, 366, '筠连县', 0);
INSERT INTO `dic_city` VALUES (2621, '511528', '兴文县', 2, 366, '兴文县', 0);
INSERT INTO `dic_city` VALUES (2622, '511529', '屏山县', 2, 366, '屏山县', 0);
INSERT INTO `dic_city` VALUES (2623, '511602', '广安区', 2, 367, '广安区', 0);
INSERT INTO `dic_city` VALUES (2624, '511621', '岳池县', 2, 367, '岳池县', 0);
INSERT INTO `dic_city` VALUES (2625, '511622', '武胜县', 2, 367, '武胜县', 0);
INSERT INTO `dic_city` VALUES (2626, '511623', '邻水县', 2, 367, '邻水县', 0);
INSERT INTO `dic_city` VALUES (2627, '511681', '华蓥市', 2, 367, '华蓥市', 0);
INSERT INTO `dic_city` VALUES (2628, '511702', '通川区', 2, 368, '通川区', 0);
INSERT INTO `dic_city` VALUES (2629, '511721', '达县', 2, 368, '达县', 0);
INSERT INTO `dic_city` VALUES (2630, '511722', '宣汉县', 2, 368, '宣汉县', 0);
INSERT INTO `dic_city` VALUES (2631, '511723', '开江县', 2, 368, '开江县', 0);
INSERT INTO `dic_city` VALUES (2632, '511724', '大竹县', 2, 368, '大竹县', 0);
INSERT INTO `dic_city` VALUES (2633, '511725', '渠县', 2, 368, '渠县', 0);
INSERT INTO `dic_city` VALUES (2634, '511781', '万源市', 2, 368, '万源市', 0);
INSERT INTO `dic_city` VALUES (2635, '511802', '雨城区', 2, 369, '雨城区', 0);
INSERT INTO `dic_city` VALUES (2636, '511821', '名山县', 2, 369, '名山县', 0);
INSERT INTO `dic_city` VALUES (2637, '511822', '荥经县', 2, 369, '荥经县', 0);
INSERT INTO `dic_city` VALUES (2638, '511823', '汉源县', 2, 369, '汉源县', 0);
INSERT INTO `dic_city` VALUES (2639, '511824', '石棉县', 2, 369, '石棉县', 0);
INSERT INTO `dic_city` VALUES (2640, '511825', '天全县', 2, 369, '天全县', 0);
INSERT INTO `dic_city` VALUES (2641, '511826', '芦山县', 2, 369, '芦山县', 0);
INSERT INTO `dic_city` VALUES (2642, '511827', '宝兴县', 2, 369, '宝兴县', 0);
INSERT INTO `dic_city` VALUES (2643, '511902', '巴州区', 2, 370, '巴州区', 0);
INSERT INTO `dic_city` VALUES (2644, '511921', '通江县', 2, 370, '通江县', 0);
INSERT INTO `dic_city` VALUES (2645, '511922', '南江县', 2, 370, '南江县', 0);
INSERT INTO `dic_city` VALUES (2646, '511923', '平昌县', 2, 370, '平昌县', 0);
INSERT INTO `dic_city` VALUES (2647, '512002', '雁江区', 2, 371, '雁江区', 0);
INSERT INTO `dic_city` VALUES (2648, '512021', '安岳县', 2, 371, '安岳县', 0);
INSERT INTO `dic_city` VALUES (2649, '512022', '乐至县', 2, 371, '乐至县', 0);
INSERT INTO `dic_city` VALUES (2650, '512081', '简阳市', 2, 371, '简阳市', 0);
INSERT INTO `dic_city` VALUES (2651, '513221', '汶川县', 2, 372, '汶川县', 0);
INSERT INTO `dic_city` VALUES (2652, '513222', '理县', 2, 372, '理县', 0);
INSERT INTO `dic_city` VALUES (2653, '513223', '茂县', 2, 372, '茂县', 0);
INSERT INTO `dic_city` VALUES (2654, '513224', '松潘县', 2, 372, '松潘县', 0);
INSERT INTO `dic_city` VALUES (2655, '513225', '九寨沟县', 2, 372, '九寨沟县', 0);
INSERT INTO `dic_city` VALUES (2656, '513226', '金川县', 2, 372, '金川县', 0);
INSERT INTO `dic_city` VALUES (2657, '513227', '小金县', 2, 372, '小金县', 0);
INSERT INTO `dic_city` VALUES (2658, '513228', '黑水县', 2, 372, '黑水县', 0);
INSERT INTO `dic_city` VALUES (2659, '513229', '马尔康县', 2, 372, '马尔康县', 0);
INSERT INTO `dic_city` VALUES (2660, '513230', '壤塘县', 2, 372, '壤塘县', 0);
INSERT INTO `dic_city` VALUES (2661, '513231', '阿坝县', 2, 372, '阿坝县', 0);
INSERT INTO `dic_city` VALUES (2662, '513232', '若尔盖县', 2, 372, '若尔盖县', 0);
INSERT INTO `dic_city` VALUES (2663, '513233', '红原县', 2, 372, '红原县', 0);
INSERT INTO `dic_city` VALUES (2664, '513321', '康定县', 2, 373, '康定县', 0);
INSERT INTO `dic_city` VALUES (2665, '513322', '泸定县', 2, 373, '泸定县', 0);
INSERT INTO `dic_city` VALUES (2666, '513323', '丹巴县', 2, 373, '丹巴县', 0);
INSERT INTO `dic_city` VALUES (2667, '513324', '九龙县', 2, 373, '九龙县', 0);
INSERT INTO `dic_city` VALUES (2668, '513325', '雅江县', 2, 373, '雅江县', 0);
INSERT INTO `dic_city` VALUES (2669, '513326', '道孚县', 2, 373, '道孚县', 0);
INSERT INTO `dic_city` VALUES (2670, '513327', '炉霍县', 2, 373, '炉霍县', 0);
INSERT INTO `dic_city` VALUES (2671, '513328', '甘孜县', 2, 373, '甘孜县', 0);
INSERT INTO `dic_city` VALUES (2672, '513329', '新龙县', 2, 373, '新龙县', 0);
INSERT INTO `dic_city` VALUES (2673, '513330', '德格县', 2, 373, '德格县', 0);
INSERT INTO `dic_city` VALUES (2674, '513331', '白玉县', 2, 373, '白玉县', 0);
INSERT INTO `dic_city` VALUES (2675, '513332', '石渠县', 2, 373, '石渠县', 0);
INSERT INTO `dic_city` VALUES (2676, '513333', '色达县', 2, 373, '色达县', 0);
INSERT INTO `dic_city` VALUES (2677, '513334', '理塘县', 2, 373, '理塘县', 0);
INSERT INTO `dic_city` VALUES (2678, '513335', '巴塘县', 2, 373, '巴塘县', 0);
INSERT INTO `dic_city` VALUES (2679, '513336', '乡城县', 2, 373, '乡城县', 0);
INSERT INTO `dic_city` VALUES (2680, '513337', '稻城县', 2, 373, '稻城县', 0);
INSERT INTO `dic_city` VALUES (2681, '513338', '得荣县', 2, 373, '得荣县', 0);
INSERT INTO `dic_city` VALUES (2682, '513401', '西昌市', 2, 374, '西昌市', 0);
INSERT INTO `dic_city` VALUES (2683, '513422', '木里藏族自治县', 2, 374, '木里藏族自治县', 0);
INSERT INTO `dic_city` VALUES (2684, '513423', '盐源县', 2, 374, '盐源县', 0);
INSERT INTO `dic_city` VALUES (2685, '513424', '德昌县', 2, 374, '德昌县', 0);
INSERT INTO `dic_city` VALUES (2686, '513425', '会理县', 2, 374, '会理县', 0);
INSERT INTO `dic_city` VALUES (2687, '513426', '会东县', 2, 374, '会东县', 0);
INSERT INTO `dic_city` VALUES (2688, '513427', '宁南县', 2, 374, '宁南县', 0);
INSERT INTO `dic_city` VALUES (2689, '513428', '普格县', 2, 374, '普格县', 0);
INSERT INTO `dic_city` VALUES (2690, '513429', '布拖县', 2, 374, '布拖县', 0);
INSERT INTO `dic_city` VALUES (2691, '513430', '金阳县', 2, 374, '金阳县', 0);
INSERT INTO `dic_city` VALUES (2692, '513431', '昭觉县', 2, 374, '昭觉县', 0);
INSERT INTO `dic_city` VALUES (2693, '513432', '喜德县', 2, 374, '喜德县', 0);
INSERT INTO `dic_city` VALUES (2694, '513433', '冕宁县', 2, 374, '冕宁县', 0);
INSERT INTO `dic_city` VALUES (2695, '513434', '越西县', 2, 374, '越西县', 0);
INSERT INTO `dic_city` VALUES (2696, '513435', '甘洛县', 2, 374, '甘洛县', 0);
INSERT INTO `dic_city` VALUES (2697, '513436', '美姑县', 2, 374, '美姑县', 0);
INSERT INTO `dic_city` VALUES (2698, '513437', '雷波县', 2, 374, '雷波县', 0);
INSERT INTO `dic_city` VALUES (2699, '520102', '南明区', 2, 375, '南明区', 0);
INSERT INTO `dic_city` VALUES (2700, '520103', '云岩区', 2, 375, '云岩区', 0);
INSERT INTO `dic_city` VALUES (2701, '520111', '花溪区', 2, 375, '花溪区', 0);
INSERT INTO `dic_city` VALUES (2702, '520112', '乌当区', 2, 375, '乌当区', 0);
INSERT INTO `dic_city` VALUES (2703, '520114', '小河区', 2, 375, '小河区', 0);
INSERT INTO `dic_city` VALUES (2704, '520121', '开阳县', 2, 375, '开阳县', 0);
INSERT INTO `dic_city` VALUES (2705, '520122', '息烽县', 2, 375, '息烽县', 0);
INSERT INTO `dic_city` VALUES (2706, '520123', '修文县', 2, 375, '修文县', 0);
INSERT INTO `dic_city` VALUES (2707, '520181', '清镇市', 2, 375, '清镇市', 0);
INSERT INTO `dic_city` VALUES (2708, '520202', '钟山区', 2, 376, '钟山区', 0);
INSERT INTO `dic_city` VALUES (2709, '520203', '六枝特区', 2, 376, '六枝特区', 0);
INSERT INTO `dic_city` VALUES (2710, '520221', '水城县', 2, 376, '水城县', 0);
INSERT INTO `dic_city` VALUES (2711, '520222', '盘县', 2, 376, '盘县', 0);
INSERT INTO `dic_city` VALUES (2712, '520302', '红花岗区', 2, 377, '红花岗区', 0);
INSERT INTO `dic_city` VALUES (2713, '520321', '遵义县', 2, 377, '遵义县', 0);
INSERT INTO `dic_city` VALUES (2714, '520322', '桐梓县', 2, 377, '桐梓县', 0);
INSERT INTO `dic_city` VALUES (2715, '520323', '绥阳县', 2, 377, '绥阳县', 0);
INSERT INTO `dic_city` VALUES (2716, '520324', '正安县', 2, 377, '正安县', 0);
INSERT INTO `dic_city` VALUES (2717, '520325', '道真仡佬族苗族自治县', 2, 377, '道真仡佬族苗族自治县', 0);
INSERT INTO `dic_city` VALUES (2718, '520326', '务川仡佬族苗族自治县', 2, 377, '务川仡佬族苗族自治县', 0);
INSERT INTO `dic_city` VALUES (2719, '520327', '凤冈县', 2, 377, '凤冈县', 0);
INSERT INTO `dic_city` VALUES (2720, '520328', '湄潭县', 2, 377, '湄潭县', 0);
INSERT INTO `dic_city` VALUES (2721, '520329', '余庆县', 2, 377, '余庆县', 0);
INSERT INTO `dic_city` VALUES (2722, '520330', '习水县', 2, 377, '习水县', 0);
INSERT INTO `dic_city` VALUES (2723, '520381', '赤水市', 2, 377, '赤水市', 0);
INSERT INTO `dic_city` VALUES (2724, '520382', '仁怀市', 2, 377, '仁怀市', 0);
INSERT INTO `dic_city` VALUES (2725, '520402', '西秀区', 2, 378, '西秀区', 0);
INSERT INTO `dic_city` VALUES (2726, '520421', '平坝县', 2, 378, '平坝县', 0);
INSERT INTO `dic_city` VALUES (2727, '520422', '普定县', 2, 378, '普定县', 0);
INSERT INTO `dic_city` VALUES (2728, '520423', '镇宁布依族苗族自治县', 2, 378, '镇宁布依族苗族自治县', 0);
INSERT INTO `dic_city` VALUES (2729, '520424', '关岭布依族苗族自治县', 2, 378, '关岭布依族苗族自治县', 0);
INSERT INTO `dic_city` VALUES (2730, '520425', '紫云苗族布依族自治县', 2, 378, '紫云苗族布依族自治县', 0);
INSERT INTO `dic_city` VALUES (2731, '522201', '铜仁市', 2, 379, '铜仁市', 0);
INSERT INTO `dic_city` VALUES (2732, '522222', '江口县', 2, 379, '江口县', 0);
INSERT INTO `dic_city` VALUES (2733, '522223', '玉屏侗族自治县', 2, 379, '玉屏侗族自治县', 0);
INSERT INTO `dic_city` VALUES (2734, '522224', '石阡县', 2, 379, '石阡县', 0);
INSERT INTO `dic_city` VALUES (2735, '522225', '思南县', 2, 379, '思南县', 0);
INSERT INTO `dic_city` VALUES (2736, '522226', '印江土家族苗族自治县', 2, 379, '印江土家族苗族自治县', 0);
INSERT INTO `dic_city` VALUES (2737, '522227', '德江县', 2, 379, '德江县', 0);
INSERT INTO `dic_city` VALUES (2738, '522228', '沿河土家族自治县', 2, 379, '沿河土家族自治县', 0);
INSERT INTO `dic_city` VALUES (2739, '522229', '松桃苗族自治县', 2, 379, '松桃苗族自治县', 0);
INSERT INTO `dic_city` VALUES (2740, '522230', '万山特区', 2, 379, '万山特区', 0);
INSERT INTO `dic_city` VALUES (2741, '522301', '兴义市', 2, 380, '兴义市', 0);
INSERT INTO `dic_city` VALUES (2742, '522322', '兴仁县', 2, 380, '兴仁县', 0);
INSERT INTO `dic_city` VALUES (2743, '522323', '普安县', 2, 380, '普安县', 0);
INSERT INTO `dic_city` VALUES (2744, '522324', '晴隆县', 2, 380, '晴隆县', 0);
INSERT INTO `dic_city` VALUES (2745, '522325', '贞丰县', 2, 380, '贞丰县', 0);
INSERT INTO `dic_city` VALUES (2746, '522326', '望谟县', 2, 380, '望谟县', 0);
INSERT INTO `dic_city` VALUES (2747, '522327', '册亨县', 2, 380, '册亨县', 0);
INSERT INTO `dic_city` VALUES (2748, '522328', '安龙县', 2, 380, '安龙县', 0);
INSERT INTO `dic_city` VALUES (2749, '522401', '毕节市', 2, 381, '毕节市', 0);
INSERT INTO `dic_city` VALUES (2750, '522422', '大方县', 2, 381, '大方县', 0);
INSERT INTO `dic_city` VALUES (2751, '522423', '黔西县', 2, 381, '黔西县', 0);
INSERT INTO `dic_city` VALUES (2752, '522424', '金沙县', 2, 381, '金沙县', 0);
INSERT INTO `dic_city` VALUES (2753, '522425', '织金县', 2, 381, '织金县', 0);
INSERT INTO `dic_city` VALUES (2754, '522426', '纳雍县', 2, 381, '纳雍县', 0);
INSERT INTO `dic_city` VALUES (2755, '522427', '威宁彝族回族苗族自治县', 2, 381, '威宁彝族回族苗族自治', 0);
INSERT INTO `dic_city` VALUES (2756, '522428', '赫章县', 2, 381, '赫章县', 0);
INSERT INTO `dic_city` VALUES (2757, '522601', '凯里市', 2, 382, '凯里市', 0);
INSERT INTO `dic_city` VALUES (2758, '522622', '黄平县', 2, 382, '黄平县', 0);
INSERT INTO `dic_city` VALUES (2759, '522623', '施秉县', 2, 382, '施秉县', 0);
INSERT INTO `dic_city` VALUES (2760, '522624', '三穗县', 2, 382, '三穗县', 0);
INSERT INTO `dic_city` VALUES (2761, '522625', '镇远县', 2, 382, '镇远县', 0);
INSERT INTO `dic_city` VALUES (2762, '522626', '岑巩县', 2, 382, '岑巩县', 0);
INSERT INTO `dic_city` VALUES (2763, '522627', '天柱县', 2, 382, '天柱县', 0);
INSERT INTO `dic_city` VALUES (2764, '522628', '锦屏县', 2, 382, '锦屏县', 0);
INSERT INTO `dic_city` VALUES (2765, '522629', '剑河县', 2, 382, '剑河县', 0);
INSERT INTO `dic_city` VALUES (2766, '522630', '台江县', 2, 382, '台江县', 0);
INSERT INTO `dic_city` VALUES (2767, '522631', '黎平县', 2, 382, '黎平县', 0);
INSERT INTO `dic_city` VALUES (2768, '522632', '榕江县', 2, 382, '榕江县', 0);
INSERT INTO `dic_city` VALUES (2769, '522633', '从江县', 2, 382, '从江县', 0);
INSERT INTO `dic_city` VALUES (2770, '522634', '雷山县', 2, 382, '雷山县', 0);
INSERT INTO `dic_city` VALUES (2771, '522635', '麻江县', 2, 382, '麻江县', 0);
INSERT INTO `dic_city` VALUES (2772, '522636', '丹寨县', 2, 382, '丹寨县', 0);
INSERT INTO `dic_city` VALUES (2773, '522701', '都匀市', 2, 383, '都匀市', 0);
INSERT INTO `dic_city` VALUES (2774, '522702', '福泉市', 2, 383, '福泉市', 0);
INSERT INTO `dic_city` VALUES (2775, '522722', '荔波县', 2, 383, '荔波县', 0);
INSERT INTO `dic_city` VALUES (2776, '522723', '贵定县', 2, 383, '贵定县', 0);
INSERT INTO `dic_city` VALUES (2777, '522725', '瓮安县', 2, 383, '瓮安县', 0);
INSERT INTO `dic_city` VALUES (2778, '522726', '独山县', 2, 383, '独山县', 0);
INSERT INTO `dic_city` VALUES (2779, '522727', '平塘县', 2, 383, '平塘县', 0);
INSERT INTO `dic_city` VALUES (2780, '522728', '罗甸县', 2, 383, '罗甸县', 0);
INSERT INTO `dic_city` VALUES (2781, '522729', '长顺县', 2, 383, '长顺县', 0);
INSERT INTO `dic_city` VALUES (2782, '522730', '龙里县', 2, 383, '龙里县', 0);
INSERT INTO `dic_city` VALUES (2783, '522731', '惠水县', 2, 383, '惠水县', 0);
INSERT INTO `dic_city` VALUES (2784, '522732', '三都水族自治县', 2, 383, '三都水族自治县', 0);
INSERT INTO `dic_city` VALUES (2785, '530102', '五华区', 2, 384, '五华区', 0);
INSERT INTO `dic_city` VALUES (2786, '530103', '盘龙区', 2, 384, '盘龙区', 0);
INSERT INTO `dic_city` VALUES (2787, '530111', '官渡区', 2, 384, '官渡区', 0);
INSERT INTO `dic_city` VALUES (2788, '530112', '西山区', 2, 384, '西山区', 0);
INSERT INTO `dic_city` VALUES (2789, '530113', '东川区', 2, 384, '东川区', 0);
INSERT INTO `dic_city` VALUES (2790, '530121', '呈贡县', 2, 384, '呈贡县', 0);
INSERT INTO `dic_city` VALUES (2791, '530122', '晋宁县', 2, 384, '晋宁县', 0);
INSERT INTO `dic_city` VALUES (2792, '530124', '富民县', 2, 384, '富民县', 0);
INSERT INTO `dic_city` VALUES (2793, '530125', '宜良县', 2, 384, '宜良县', 0);
INSERT INTO `dic_city` VALUES (2794, '530126', '石林彝族自治县', 2, 384, '石林彝族自治县', 0);
INSERT INTO `dic_city` VALUES (2795, '530127', '嵩明县', 2, 384, '嵩明县', 0);
INSERT INTO `dic_city` VALUES (2796, '530128', '禄劝彝族苗族自治县', 2, 384, '禄劝彝族苗族自治县', 0);
INSERT INTO `dic_city` VALUES (2797, '530129', '寻甸回族彝族自治县', 2, 384, '寻甸回族彝族自治县', 0);
INSERT INTO `dic_city` VALUES (2798, '530181', '安宁市', 2, 384, '安宁市', 0);
INSERT INTO `dic_city` VALUES (2799, '530302', '麒麟区', 2, 385, '麒麟区', 0);
INSERT INTO `dic_city` VALUES (2800, '530321', '马龙县', 2, 385, '马龙县', 0);
INSERT INTO `dic_city` VALUES (2801, '530322', '陆良县', 2, 385, '陆良县', 0);
INSERT INTO `dic_city` VALUES (2802, '530323', '师宗县', 2, 385, '师宗县', 0);
INSERT INTO `dic_city` VALUES (2803, '530324', '罗平县', 2, 385, '罗平县', 0);
INSERT INTO `dic_city` VALUES (2804, '530325', '富源县', 2, 385, '富源县', 0);
INSERT INTO `dic_city` VALUES (2805, '530326', '会泽县', 2, 385, '会泽县', 0);
INSERT INTO `dic_city` VALUES (2806, '530328', '沾益县', 2, 385, '沾益县', 0);
INSERT INTO `dic_city` VALUES (2807, '530381', '宣威市', 2, 385, '宣威市', 0);
INSERT INTO `dic_city` VALUES (2808, '530402', '红塔区', 2, 386, '红塔区', 0);
INSERT INTO `dic_city` VALUES (2809, '530421', '江川县', 2, 386, '江川县', 0);
INSERT INTO `dic_city` VALUES (2810, '530422', '澄江县', 2, 386, '澄江县', 0);
INSERT INTO `dic_city` VALUES (2811, '530423', '通海县', 2, 386, '通海县', 0);
INSERT INTO `dic_city` VALUES (2812, '530424', '华宁县', 2, 386, '华宁县', 0);
INSERT INTO `dic_city` VALUES (2813, '530425', '易门县', 2, 386, '易门县', 0);
INSERT INTO `dic_city` VALUES (2814, '530426', '峨山彝族自治县', 2, 386, '峨山彝族自治县', 0);
INSERT INTO `dic_city` VALUES (2815, '530427', '新平彝族傣族自治县', 2, 386, '新平彝族傣族自治县', 0);
INSERT INTO `dic_city` VALUES (2816, '530428', '元江哈尼族彝族傣族自治县', 2, 386, '元江哈尼族彝族傣族自', 0);
INSERT INTO `dic_city` VALUES (2817, '530502', '隆阳区', 2, 387, '隆阳区', 0);
INSERT INTO `dic_city` VALUES (2818, '530521', '施甸县', 2, 387, '施甸县', 0);
INSERT INTO `dic_city` VALUES (2819, '530522', '腾冲县', 2, 387, '腾冲县', 0);
INSERT INTO `dic_city` VALUES (2820, '530523', '龙陵县', 2, 387, '龙陵县', 0);
INSERT INTO `dic_city` VALUES (2821, '530524', '昌宁县', 2, 387, '昌宁县', 0);
INSERT INTO `dic_city` VALUES (2822, '530602', '昭阳区', 2, 388, '昭阳区', 0);
INSERT INTO `dic_city` VALUES (2823, '530621', '鲁甸县', 2, 388, '鲁甸县', 0);
INSERT INTO `dic_city` VALUES (2824, '530622', '巧家县', 2, 388, '巧家县', 0);
INSERT INTO `dic_city` VALUES (2825, '530623', '盐津县', 2, 388, '盐津县', 0);
INSERT INTO `dic_city` VALUES (2826, '530624', '大关县', 2, 388, '大关县', 0);
INSERT INTO `dic_city` VALUES (2827, '530625', '永善县', 2, 388, '永善县', 0);
INSERT INTO `dic_city` VALUES (2828, '530626', '绥江县', 2, 388, '绥江县', 0);
INSERT INTO `dic_city` VALUES (2829, '530627', '镇雄县', 2, 388, '镇雄县', 0);
INSERT INTO `dic_city` VALUES (2830, '530628', '彝良县', 2, 388, '彝良县', 0);
INSERT INTO `dic_city` VALUES (2831, '530629', '威信县', 2, 388, '威信县', 0);
INSERT INTO `dic_city` VALUES (2832, '530630', '水富县', 2, 388, '水富县', 0);
INSERT INTO `dic_city` VALUES (2833, '530702', '古城区', 2, 389, '古城区', 0);
INSERT INTO `dic_city` VALUES (2834, '530721', '玉龙纳西族自治县', 2, 389, '玉龙纳西族自治县', 0);
INSERT INTO `dic_city` VALUES (2835, '530722', '永胜县', 2, 389, '永胜县', 0);
INSERT INTO `dic_city` VALUES (2836, '530723', '华坪县', 2, 389, '华坪县', 0);
INSERT INTO `dic_city` VALUES (2837, '530724', '宁蒗彝族自治县', 2, 389, '宁蒗彝族自治县', 0);
INSERT INTO `dic_city` VALUES (2838, '532301', '楚雄市', 2, 390, '楚雄市', 0);
INSERT INTO `dic_city` VALUES (2839, '532322', '双柏县', 2, 390, '双柏县', 0);
INSERT INTO `dic_city` VALUES (2840, '532323', '牟定县', 2, 390, '牟定县', 0);
INSERT INTO `dic_city` VALUES (2841, '532324', '南华县', 2, 390, '南华县', 0);
INSERT INTO `dic_city` VALUES (2842, '532325', '姚安县', 2, 390, '姚安县', 0);
INSERT INTO `dic_city` VALUES (2843, '532326', '大姚县', 2, 390, '大姚县', 0);
INSERT INTO `dic_city` VALUES (2844, '532327', '永仁县', 2, 390, '永仁县', 0);
INSERT INTO `dic_city` VALUES (2845, '532328', '元谋县', 2, 390, '元谋县', 0);
INSERT INTO `dic_city` VALUES (2846, '532329', '武定县', 2, 390, '武定县', 0);
INSERT INTO `dic_city` VALUES (2847, '532331', '禄丰县', 2, 390, '禄丰县', 0);
INSERT INTO `dic_city` VALUES (2848, '532501', '个旧市', 2, 391, '个旧市', 0);
INSERT INTO `dic_city` VALUES (2849, '532502', '开远市', 2, 391, '开远市', 0);
INSERT INTO `dic_city` VALUES (2850, '532522', '蒙自县', 2, 391, '蒙自县', 0);
INSERT INTO `dic_city` VALUES (2851, '532523', '屏边苗族自治县', 2, 391, '屏边苗族自治县', 0);
INSERT INTO `dic_city` VALUES (2852, '532524', '建水县', 2, 391, '建水县', 0);
INSERT INTO `dic_city` VALUES (2853, '532525', '石屏县', 2, 391, '石屏县', 0);
INSERT INTO `dic_city` VALUES (2854, '532526', '弥勒县', 2, 391, '弥勒县', 0);
INSERT INTO `dic_city` VALUES (2855, '532527', '泸西县', 2, 391, '泸西县', 0);
INSERT INTO `dic_city` VALUES (2856, '532528', '元阳县', 2, 391, '元阳县', 0);
INSERT INTO `dic_city` VALUES (2857, '532529', '红河县', 2, 391, '红河县', 0);
INSERT INTO `dic_city` VALUES (2858, '532530', '金平苗族瑶族傣族自治县', 2, 391, '金平苗族瑶族傣族自治', 0);
INSERT INTO `dic_city` VALUES (2859, '532531', '绿春县', 2, 391, '绿春县', 0);
INSERT INTO `dic_city` VALUES (2860, '532532', '河口瑶族自治县', 2, 391, '河口瑶族自治县', 0);
INSERT INTO `dic_city` VALUES (2861, '532621', '文山县', 2, 392, '文山县', 0);
INSERT INTO `dic_city` VALUES (2862, '532622', '砚山县', 2, 392, '砚山县', 0);
INSERT INTO `dic_city` VALUES (2863, '532623', '西畴县', 2, 392, '西畴县', 0);
INSERT INTO `dic_city` VALUES (2864, '532624', '麻栗坡县', 2, 392, '麻栗坡县', 0);
INSERT INTO `dic_city` VALUES (2865, '532625', '马关县', 2, 392, '马关县', 0);
INSERT INTO `dic_city` VALUES (2866, '532626', '丘北县', 2, 392, '丘北县', 0);
INSERT INTO `dic_city` VALUES (2867, '532627', '广南县', 2, 392, '广南县', 0);
INSERT INTO `dic_city` VALUES (2868, '532628', '富宁县', 2, 392, '富宁县', 0);
INSERT INTO `dic_city` VALUES (2869, '532701', '思茅市', 2, 393, '思茅市', 0);
INSERT INTO `dic_city` VALUES (2870, '532722', '普洱哈尼族彝族自治县', 2, 393, '普洱哈尼族彝族自治县', 0);
INSERT INTO `dic_city` VALUES (2871, '532723', '墨江哈尼族自治县', 2, 393, '墨江哈尼族自治县', 0);
INSERT INTO `dic_city` VALUES (2872, '532724', '景东彝族自治县', 2, 393, '景东彝族自治县', 0);
INSERT INTO `dic_city` VALUES (2873, '532725', '景谷傣族彝族自治县', 2, 393, '景谷傣族彝族自治县', 0);
INSERT INTO `dic_city` VALUES (2874, '532726', '镇沅彝族哈尼族拉祜族自治县', 2, 393, '镇沅彝族哈尼族拉祜族', 0);
INSERT INTO `dic_city` VALUES (2875, '532727', '江城哈尼族彝族自治县', 2, 393, '江城哈尼族彝族自治县', 0);
INSERT INTO `dic_city` VALUES (2876, '532728', '孟连傣族拉祜族佤族自治县', 2, 393, '孟连傣族拉祜族佤族自', 0);
INSERT INTO `dic_city` VALUES (2877, '532729', '澜沧拉祜族自治县', 2, 393, '澜沧拉祜族自治县', 0);
INSERT INTO `dic_city` VALUES (2878, '532730', '西盟佤族自治县', 2, 393, '西盟佤族自治县', 0);
INSERT INTO `dic_city` VALUES (2879, '532801', '景洪市', 2, 394, '景洪市', 0);
INSERT INTO `dic_city` VALUES (2880, '532822', '勐海县', 2, 394, '勐海县', 0);
INSERT INTO `dic_city` VALUES (2881, '532823', '勐腊县', 2, 394, '勐腊县', 0);
INSERT INTO `dic_city` VALUES (2882, '532901', '大理市', 2, 395, '大理市', 0);
INSERT INTO `dic_city` VALUES (2883, '532922', '漾濞彝族自治县', 2, 395, '漾濞彝族自治县', 0);
INSERT INTO `dic_city` VALUES (2884, '532923', '祥云县', 2, 395, '祥云县', 0);
INSERT INTO `dic_city` VALUES (2885, '532924', '宾川县', 2, 395, '宾川县', 0);
INSERT INTO `dic_city` VALUES (2886, '532925', '弥渡县', 2, 395, '弥渡县', 0);
INSERT INTO `dic_city` VALUES (2887, '532926', '南涧彝族自治县', 2, 395, '南涧彝族自治县', 0);
INSERT INTO `dic_city` VALUES (2888, '532927', '巍山彝族回族自治县', 2, 395, '巍山彝族回族自治县', 0);
INSERT INTO `dic_city` VALUES (2889, '532928', '永平县', 2, 395, '永平县', 0);
INSERT INTO `dic_city` VALUES (2890, '532929', '云龙县', 2, 395, '云龙县', 0);
INSERT INTO `dic_city` VALUES (2891, '532930', '洱源县', 2, 395, '洱源县', 0);
INSERT INTO `dic_city` VALUES (2892, '532931', '剑川县', 2, 395, '剑川县', 0);
INSERT INTO `dic_city` VALUES (2893, '532932', '鹤庆县', 2, 395, '鹤庆县', 0);
INSERT INTO `dic_city` VALUES (2894, '533102', '瑞丽市', 2, 396, '瑞丽市', 0);
INSERT INTO `dic_city` VALUES (2895, '533103', '潞西市', 2, 396, '潞西市', 0);
INSERT INTO `dic_city` VALUES (2896, '533122', '梁河县', 2, 396, '梁河县', 0);
INSERT INTO `dic_city` VALUES (2897, '533123', '盈江县', 2, 396, '盈江县', 0);
INSERT INTO `dic_city` VALUES (2898, '533124', '陇川县', 2, 396, '陇川县', 0);
INSERT INTO `dic_city` VALUES (2899, '533321', '泸水县', 2, 397, '泸水县', 0);
INSERT INTO `dic_city` VALUES (2900, '533323', '福贡县', 2, 397, '福贡县', 0);
INSERT INTO `dic_city` VALUES (2901, '533324', '贡山独龙族怒族自治县', 2, 397, '贡山独龙族怒族自治县', 0);
INSERT INTO `dic_city` VALUES (2902, '533325', '兰坪白族普米族自治县', 2, 397, '兰坪白族普米族自治县', 0);
INSERT INTO `dic_city` VALUES (2903, '533421', '香格里拉县', 2, 398, '香格里拉县', 0);
INSERT INTO `dic_city` VALUES (2904, '533422', '德钦县', 2, 398, '德钦县', 0);
INSERT INTO `dic_city` VALUES (2905, '533423', '维西傈僳族自治县', 2, 398, '维西傈僳族自治县', 0);
INSERT INTO `dic_city` VALUES (2906, '533521', '临沧县', 2, 399, '临沧县', 0);
INSERT INTO `dic_city` VALUES (2907, '533522', '凤庆县', 2, 399, '凤庆县', 0);
INSERT INTO `dic_city` VALUES (2908, '533523', '云县', 2, 399, '云县', 0);
INSERT INTO `dic_city` VALUES (2909, '533524', '永德县', 2, 399, '永德县', 0);
INSERT INTO `dic_city` VALUES (2910, '533525', '镇康县', 2, 399, '镇康县', 0);
INSERT INTO `dic_city` VALUES (2911, '533526', '双江拉祜族佤族布朗族傣族自治县', 2, 399, '双江拉祜族佤族布朗族', 0);
INSERT INTO `dic_city` VALUES (2912, '533527', '耿马傣族佤族自治县', 2, 399, '耿马傣族佤族自治县', 0);
INSERT INTO `dic_city` VALUES (2913, '533528', '沧源佤族自治县', 2, 399, '沧源佤族自治县', 0);
INSERT INTO `dic_city` VALUES (2914, '620102', '城关区', 2, 400, '城关区', 0);
INSERT INTO `dic_city` VALUES (2915, '540121', '林周县', 2, 400, '林周县', 0);
INSERT INTO `dic_city` VALUES (2916, '540122', '当雄县', 2, 400, '当雄县', 0);
INSERT INTO `dic_city` VALUES (2917, '540123', '尼木县', 2, 400, '尼木县', 0);
INSERT INTO `dic_city` VALUES (2918, '540124', '曲水县', 2, 400, '曲水县', 0);
INSERT INTO `dic_city` VALUES (2919, '540125', '堆龙德庆县', 2, 400, '堆龙德庆县', 0);
INSERT INTO `dic_city` VALUES (2920, '540126', '达孜县', 2, 400, '达孜县', 0);
INSERT INTO `dic_city` VALUES (2921, '540127', '墨竹工卡县', 2, 400, '墨竹工卡县', 0);
INSERT INTO `dic_city` VALUES (2922, '542121', '昌都县', 2, 401, '昌都县', 0);
INSERT INTO `dic_city` VALUES (2923, '542122', '江达县', 2, 401, '江达县', 0);
INSERT INTO `dic_city` VALUES (2924, '542123', '贡觉县', 2, 401, '贡觉县', 0);
INSERT INTO `dic_city` VALUES (2925, '542124', '类乌齐县', 2, 401, '类乌齐县', 0);
INSERT INTO `dic_city` VALUES (2926, '542125', '丁青县', 2, 401, '丁青县', 0);
INSERT INTO `dic_city` VALUES (2927, '542126', '察雅县', 2, 401, '察雅县', 0);
INSERT INTO `dic_city` VALUES (2928, '542127', '八宿县', 2, 401, '八宿县', 0);
INSERT INTO `dic_city` VALUES (2929, '542128', '左贡县', 2, 401, '左贡县', 0);
INSERT INTO `dic_city` VALUES (2930, '542129', '芒康县', 2, 401, '芒康县', 0);
INSERT INTO `dic_city` VALUES (2931, '542132', '洛隆县', 2, 401, '洛隆县', 0);
INSERT INTO `dic_city` VALUES (2932, '542133', '边坝县', 2, 401, '边坝县', 0);
INSERT INTO `dic_city` VALUES (2933, '542221', '乃东县', 2, 402, '乃东县', 0);
INSERT INTO `dic_city` VALUES (2934, '542222', '扎囊县', 2, 402, '扎囊县', 0);
INSERT INTO `dic_city` VALUES (2935, '542223', '贡嘎县', 2, 402, '贡嘎县', 0);
INSERT INTO `dic_city` VALUES (2936, '542224', '桑日县', 2, 402, '桑日县', 0);
INSERT INTO `dic_city` VALUES (2937, '542225', '琼结县', 2, 402, '琼结县', 0);
INSERT INTO `dic_city` VALUES (2938, '542226', '曲松县', 2, 402, '曲松县', 0);
INSERT INTO `dic_city` VALUES (2939, '542227', '措美县', 2, 402, '措美县', 0);
INSERT INTO `dic_city` VALUES (2940, '542228', '洛扎县', 2, 402, '洛扎县', 0);
INSERT INTO `dic_city` VALUES (2941, '542229', '加查县', 2, 402, '加查县', 0);
INSERT INTO `dic_city` VALUES (2942, '542231', '隆子县', 2, 402, '隆子县', 0);
INSERT INTO `dic_city` VALUES (2943, '542232', '错那县', 2, 402, '错那县', 0);
INSERT INTO `dic_city` VALUES (2944, '542233', '浪卡子县', 2, 402, '浪卡子县', 0);
INSERT INTO `dic_city` VALUES (2945, '542301', '日喀则市', 2, 403, '日喀则市', 0);
INSERT INTO `dic_city` VALUES (2946, '542322', '南木林县', 2, 403, '南木林县', 0);
INSERT INTO `dic_city` VALUES (2947, '542323', '江孜县', 2, 403, '江孜县', 0);
INSERT INTO `dic_city` VALUES (2948, '542324', '定日县', 2, 403, '定日县', 0);
INSERT INTO `dic_city` VALUES (2949, '542325', '萨迦县', 2, 403, '萨迦县', 0);
INSERT INTO `dic_city` VALUES (2950, '542326', '拉孜县', 2, 403, '拉孜县', 0);
INSERT INTO `dic_city` VALUES (2951, '542327', '昂仁县', 2, 403, '昂仁县', 0);
INSERT INTO `dic_city` VALUES (2952, '542328', '谢通门县', 2, 403, '谢通门县', 0);
INSERT INTO `dic_city` VALUES (2953, '542329', '白朗县', 2, 403, '白朗县', 0);
INSERT INTO `dic_city` VALUES (2954, '542330', '仁布县', 2, 403, '仁布县', 0);
INSERT INTO `dic_city` VALUES (2955, '542331', '康马县', 2, 403, '康马县', 0);
INSERT INTO `dic_city` VALUES (2956, '542332', '定结县', 2, 403, '定结县', 0);
INSERT INTO `dic_city` VALUES (2957, '542333', '仲巴县', 2, 403, '仲巴县', 0);
INSERT INTO `dic_city` VALUES (2958, '542334', '亚东县', 2, 403, '亚东县', 0);
INSERT INTO `dic_city` VALUES (2959, '542335', '吉隆县', 2, 403, '吉隆县', 0);
INSERT INTO `dic_city` VALUES (2960, '542336', '聂拉木县', 2, 403, '聂拉木县', 0);
INSERT INTO `dic_city` VALUES (2961, '542337', '萨嘎县', 2, 403, '萨嘎县', 0);
INSERT INTO `dic_city` VALUES (2962, '542338', '岗巴县', 2, 403, '岗巴县', 0);
INSERT INTO `dic_city` VALUES (2963, '542421', '那曲县', 2, 404, '那曲县', 0);
INSERT INTO `dic_city` VALUES (2964, '542422', '嘉黎县', 2, 404, '嘉黎县', 0);
INSERT INTO `dic_city` VALUES (2965, '542423', '比如县', 2, 404, '比如县', 0);
INSERT INTO `dic_city` VALUES (2966, '542424', '聂荣县', 2, 404, '聂荣县', 0);
INSERT INTO `dic_city` VALUES (2967, '542425', '安多县', 2, 404, '安多县', 0);
INSERT INTO `dic_city` VALUES (2968, '542426', '申扎县', 2, 404, '申扎县', 0);
INSERT INTO `dic_city` VALUES (2969, '542427', '索县', 2, 404, '索县', 0);
INSERT INTO `dic_city` VALUES (2970, '542428', '班戈县', 2, 404, '班戈县', 0);
INSERT INTO `dic_city` VALUES (2971, '542429', '巴青县', 2, 404, '巴青县', 0);
INSERT INTO `dic_city` VALUES (2972, '542430', '尼玛县', 2, 404, '尼玛县', 0);
INSERT INTO `dic_city` VALUES (2973, '542521', '普兰县', 2, 405, '普兰县', 0);
INSERT INTO `dic_city` VALUES (2974, '542522', '札达县', 2, 405, '札达县', 0);
INSERT INTO `dic_city` VALUES (2975, '542523', '噶尔县', 2, 405, '噶尔县', 0);
INSERT INTO `dic_city` VALUES (2976, '542524', '日土县', 2, 405, '日土县', 0);
INSERT INTO `dic_city` VALUES (2977, '542525', '革吉县', 2, 405, '革吉县', 0);
INSERT INTO `dic_city` VALUES (2978, '542526', '改则县', 2, 405, '改则县', 0);
INSERT INTO `dic_city` VALUES (2979, '542527', '措勤县', 2, 405, '措勤县', 0);
INSERT INTO `dic_city` VALUES (2980, '542621', '林芝县', 2, 406, '林芝县', 0);
INSERT INTO `dic_city` VALUES (2981, '542622', '工布江达县', 2, 406, '工布江达县', 0);
INSERT INTO `dic_city` VALUES (2982, '542623', '米林县', 2, 406, '米林县', 0);
INSERT INTO `dic_city` VALUES (2983, '542624', '墨脱县', 2, 406, '墨脱县', 0);
INSERT INTO `dic_city` VALUES (2984, '542625', '波密县', 2, 406, '波密县', 0);
INSERT INTO `dic_city` VALUES (2985, '542626', '察隅县', 2, 406, '察隅县', 0);
INSERT INTO `dic_city` VALUES (2986, '542627', '朗县', 2, 406, '朗县', 0);
INSERT INTO `dic_city` VALUES (2987, '610103', '碑林区', 2, 407, '碑林区', 0);
INSERT INTO `dic_city` VALUES (2988, '610104', '莲湖区', 2, 407, '莲湖区', 0);
INSERT INTO `dic_city` VALUES (2989, '610111', '灞桥区', 2, 407, '灞桥区', 0);
INSERT INTO `dic_city` VALUES (2990, '610112', '未央区', 2, 407, '未央区', 0);
INSERT INTO `dic_city` VALUES (2991, '610113', '雁塔区', 2, 407, '雁塔区', 0);
INSERT INTO `dic_city` VALUES (2992, '610114', '阎良区', 2, 407, '阎良区', 0);
INSERT INTO `dic_city` VALUES (2993, '610115', '临潼区', 2, 407, '临潼区', 0);
INSERT INTO `dic_city` VALUES (2994, '610122', '蓝田县', 2, 407, '蓝田县', 0);
INSERT INTO `dic_city` VALUES (2995, '610124', '周至县', 2, 407, '周至县', 0);
INSERT INTO `dic_city` VALUES (2996, '610125', '户县', 2, 407, '户县', 0);
INSERT INTO `dic_city` VALUES (2997, '610126', '高陵县', 2, 407, '高陵县', 0);
INSERT INTO `dic_city` VALUES (2998, '610202', '王益区', 2, 408, '王益区', 0);
INSERT INTO `dic_city` VALUES (2999, '610203', '印台区', 2, 408, '印台区', 0);
INSERT INTO `dic_city` VALUES (3000, '610204', '耀州区', 2, 408, '耀州区', 0);
INSERT INTO `dic_city` VALUES (3001, '610222', '宜君县', 2, 408, '宜君县', 0);
INSERT INTO `dic_city` VALUES (3002, '610302', '渭滨区', 2, 409, '渭滨区', 0);
INSERT INTO `dic_city` VALUES (3003, '610303', '金台区', 2, 409, '金台区', 0);
INSERT INTO `dic_city` VALUES (3004, '610321', '宝鸡县', 2, 409, '宝鸡县', 0);
INSERT INTO `dic_city` VALUES (3005, '610322', '凤翔县', 2, 409, '凤翔县', 0);
INSERT INTO `dic_city` VALUES (3006, '610323', '岐山县', 2, 409, '岐山县', 0);
INSERT INTO `dic_city` VALUES (3007, '610324', '扶风县', 2, 409, '扶风县', 0);
INSERT INTO `dic_city` VALUES (3008, '610326', '眉县', 2, 409, '眉县', 0);
INSERT INTO `dic_city` VALUES (3009, '610327', '陇县', 2, 409, '陇县', 0);
INSERT INTO `dic_city` VALUES (3010, '610328', '千阳县', 2, 409, '千阳县', 0);
INSERT INTO `dic_city` VALUES (3011, '610329', '麟游县', 2, 409, '麟游县', 0);
INSERT INTO `dic_city` VALUES (3012, '610330', '凤县', 2, 409, '凤县', 0);
INSERT INTO `dic_city` VALUES (3013, '610331', '太白县', 2, 409, '太白县', 0);
INSERT INTO `dic_city` VALUES (3014, '610402', '秦都区', 2, 410, '秦都区', 0);
INSERT INTO `dic_city` VALUES (3015, '610403', '杨凌区', 2, 410, '杨凌区', 0);
INSERT INTO `dic_city` VALUES (3016, '610404', '渭城区', 2, 410, '渭城区', 0);
INSERT INTO `dic_city` VALUES (3017, '610422', '三原县', 2, 410, '三原县', 0);
INSERT INTO `dic_city` VALUES (3018, '610423', '泾阳县', 2, 410, '泾阳县', 0);
INSERT INTO `dic_city` VALUES (3019, '610424', '乾县', 2, 410, '乾县', 0);
INSERT INTO `dic_city` VALUES (3020, '610425', '礼泉县', 2, 410, '礼泉县', 0);
INSERT INTO `dic_city` VALUES (3021, '610426', '永寿县', 2, 410, '永寿县', 0);
INSERT INTO `dic_city` VALUES (3022, '610427', '彬县', 2, 410, '彬县', 0);
INSERT INTO `dic_city` VALUES (3023, '610428', '长武县', 2, 410, '长武县', 0);
INSERT INTO `dic_city` VALUES (3024, '610429', '旬邑县', 2, 410, '旬邑县', 0);
INSERT INTO `dic_city` VALUES (3025, '610430', '淳化县', 2, 410, '淳化县', 0);
INSERT INTO `dic_city` VALUES (3026, '610431', '武功县', 2, 410, '武功县', 0);
INSERT INTO `dic_city` VALUES (3027, '610481', '兴平市', 2, 410, '兴平市', 0);
INSERT INTO `dic_city` VALUES (3028, '610502', '临渭区', 2, 411, '临渭区', 0);
INSERT INTO `dic_city` VALUES (3029, '610521', '华县', 2, 411, '华县', 0);
INSERT INTO `dic_city` VALUES (3030, '610522', '潼关县', 2, 411, '潼关县', 0);
INSERT INTO `dic_city` VALUES (3031, '610523', '大荔县', 2, 411, '大荔县', 0);
INSERT INTO `dic_city` VALUES (3032, '610524', '合阳县', 2, 411, '合阳县', 0);
INSERT INTO `dic_city` VALUES (3033, '610525', '澄城县', 2, 411, '澄城县', 0);
INSERT INTO `dic_city` VALUES (3034, '610526', '蒲城县', 2, 411, '蒲城县', 0);
INSERT INTO `dic_city` VALUES (3035, '610527', '白水县', 2, 411, '白水县', 0);
INSERT INTO `dic_city` VALUES (3036, '610528', '富平县', 2, 411, '富平县', 0);
INSERT INTO `dic_city` VALUES (3037, '610581', '韩城市', 2, 411, '韩城市', 0);
INSERT INTO `dic_city` VALUES (3038, '610582', '华阴市', 2, 411, '华阴市', 0);
INSERT INTO `dic_city` VALUES (3039, '610602', '宝塔区', 2, 412, '宝塔区', 0);
INSERT INTO `dic_city` VALUES (3040, '610621', '延长县', 2, 412, '延长县', 0);
INSERT INTO `dic_city` VALUES (3041, '610622', '延川县', 2, 412, '延川县', 0);
INSERT INTO `dic_city` VALUES (3042, '610623', '子长县', 2, 412, '子长县', 0);
INSERT INTO `dic_city` VALUES (3043, '610624', '安塞县', 2, 412, '安塞县', 0);
INSERT INTO `dic_city` VALUES (3044, '610625', '志丹县', 2, 412, '志丹县', 0);
INSERT INTO `dic_city` VALUES (3045, '610626', '吴旗县', 2, 412, '吴旗县', 0);
INSERT INTO `dic_city` VALUES (3046, '610627', '甘泉县', 2, 412, '甘泉县', 0);
INSERT INTO `dic_city` VALUES (3047, '610628', '富县', 2, 412, '富县', 0);
INSERT INTO `dic_city` VALUES (3048, '610629', '洛川县', 2, 412, '洛川县', 0);
INSERT INTO `dic_city` VALUES (3049, '610630', '宜川县', 2, 412, '宜川县', 0);
INSERT INTO `dic_city` VALUES (3050, '610631', '黄龙县', 2, 412, '黄龙县', 0);
INSERT INTO `dic_city` VALUES (3051, '610632', '黄陵县', 2, 412, '黄陵县', 0);
INSERT INTO `dic_city` VALUES (3052, '610702', '汉台区', 2, 413, '汉台区', 0);
INSERT INTO `dic_city` VALUES (3053, '610721', '南郑县', 2, 413, '南郑县', 0);
INSERT INTO `dic_city` VALUES (3054, '610722', '城固县', 2, 413, '城固县', 0);
INSERT INTO `dic_city` VALUES (3055, '610723', '洋县', 2, 413, '洋县', 0);
INSERT INTO `dic_city` VALUES (3056, '610724', '西乡县', 2, 413, '西乡县', 0);
INSERT INTO `dic_city` VALUES (3057, '610725', '勉县', 2, 413, '勉县', 0);
INSERT INTO `dic_city` VALUES (3058, '610726', '宁强县', 2, 413, '宁强县', 0);
INSERT INTO `dic_city` VALUES (3059, '610727', '略阳县', 2, 413, '略阳县', 0);
INSERT INTO `dic_city` VALUES (3060, '610728', '镇巴县', 2, 413, '镇巴县', 0);
INSERT INTO `dic_city` VALUES (3061, '610729', '留坝县', 2, 413, '留坝县', 0);
INSERT INTO `dic_city` VALUES (3062, '610730', '佛坪县', 2, 413, '佛坪县', 0);
INSERT INTO `dic_city` VALUES (3063, '610802', '榆阳区', 2, 414, '榆阳区', 0);
INSERT INTO `dic_city` VALUES (3064, '610821', '神木县', 2, 414, '神木县', 0);
INSERT INTO `dic_city` VALUES (3065, '610822', '府谷县', 2, 414, '府谷县', 0);
INSERT INTO `dic_city` VALUES (3066, '610823', '横山县', 2, 414, '横山县', 0);
INSERT INTO `dic_city` VALUES (3067, '610824', '靖边县', 2, 414, '靖边县', 0);
INSERT INTO `dic_city` VALUES (3068, '610825', '定边县', 2, 414, '定边县', 0);
INSERT INTO `dic_city` VALUES (3069, '610826', '绥德县', 2, 414, '绥德县', 0);
INSERT INTO `dic_city` VALUES (3070, '610827', '米脂县', 2, 414, '米脂县', 0);
INSERT INTO `dic_city` VALUES (3071, '610828', '佳县', 2, 414, '佳县', 0);
INSERT INTO `dic_city` VALUES (3072, '610829', '吴堡县', 2, 414, '吴堡县', 0);
INSERT INTO `dic_city` VALUES (3073, '610830', '清涧县', 2, 414, '清涧县', 0);
INSERT INTO `dic_city` VALUES (3074, '610831', '子洲县', 2, 414, '子洲县', 0);
INSERT INTO `dic_city` VALUES (3075, '610902', '汉滨区', 2, 415, '汉滨区', 0);
INSERT INTO `dic_city` VALUES (3076, '610921', '汉阴县', 2, 415, '汉阴县', 0);
INSERT INTO `dic_city` VALUES (3077, '610922', '石泉县', 2, 415, '石泉县', 0);
INSERT INTO `dic_city` VALUES (3078, '610923', '宁陕县', 2, 415, '宁陕县', 0);
INSERT INTO `dic_city` VALUES (3079, '610924', '紫阳县', 2, 415, '紫阳县', 0);
INSERT INTO `dic_city` VALUES (3080, '610925', '岚皋县', 2, 415, '岚皋县', 0);
INSERT INTO `dic_city` VALUES (3081, '610926', '平利县', 2, 415, '平利县', 0);
INSERT INTO `dic_city` VALUES (3082, '610927', '镇坪县', 2, 415, '镇坪县', 0);
INSERT INTO `dic_city` VALUES (3083, '610928', '旬阳县', 2, 415, '旬阳县', 0);
INSERT INTO `dic_city` VALUES (3084, '610929', '白河县', 2, 415, '白河县', 0);
INSERT INTO `dic_city` VALUES (3085, '611002', '商州区', 2, 416, '商州区', 0);
INSERT INTO `dic_city` VALUES (3086, '611021', '洛南县', 2, 416, '洛南县', 0);
INSERT INTO `dic_city` VALUES (3087, '611022', '丹凤县', 2, 416, '丹凤县', 0);
INSERT INTO `dic_city` VALUES (3088, '611023', '商南县', 2, 416, '商南县', 0);
INSERT INTO `dic_city` VALUES (3089, '611024', '山阳县', 2, 416, '山阳县', 0);
INSERT INTO `dic_city` VALUES (3090, '611025', '镇安县', 2, 416, '镇安县', 0);
INSERT INTO `dic_city` VALUES (3091, '611026', '柞水县', 2, 416, '柞水县', 0);
INSERT INTO `dic_city` VALUES (3092, '620102', '城关区', 2, 417, '城关区', 0);
INSERT INTO `dic_city` VALUES (3093, '620103', '七里河区', 2, 417, '七里河区', 0);
INSERT INTO `dic_city` VALUES (3094, '620104', '西固区', 2, 417, '西固区', 0);
INSERT INTO `dic_city` VALUES (3095, '620105', '安宁区', 2, 417, '安宁区', 0);
INSERT INTO `dic_city` VALUES (3096, '620111', '红古区', 2, 417, '红古区', 0);
INSERT INTO `dic_city` VALUES (3097, '620121', '永登县', 2, 417, '永登县', 0);
INSERT INTO `dic_city` VALUES (3098, '620122', '皋兰县', 2, 417, '皋兰县', 0);
INSERT INTO `dic_city` VALUES (3099, '620123', '榆中县', 2, 417, '榆中县', 0);
INSERT INTO `dic_city` VALUES (3100, '620302', '金川区', 2, 419, '金川区', 0);
INSERT INTO `dic_city` VALUES (3101, '620321', '永昌县', 2, 419, '永昌县', 0);
INSERT INTO `dic_city` VALUES (3102, '620402', '白银区', 2, 420, '白银区', 0);
INSERT INTO `dic_city` VALUES (3103, '620403', '平川区', 2, 420, '平川区', 0);
INSERT INTO `dic_city` VALUES (3104, '620421', '靖远县', 2, 420, '靖远县', 0);
INSERT INTO `dic_city` VALUES (3105, '620422', '会宁县', 2, 420, '会宁县', 0);
INSERT INTO `dic_city` VALUES (3106, '620423', '景泰县', 2, 420, '景泰县', 0);
INSERT INTO `dic_city` VALUES (3107, '620502', '秦城区', 2, 421, '秦城区', 0);
INSERT INTO `dic_city` VALUES (3108, '620503', '北道区', 2, 421, '北道区', 0);
INSERT INTO `dic_city` VALUES (3109, '620521', '清水县', 2, 421, '清水县', 0);
INSERT INTO `dic_city` VALUES (3110, '620522', '秦安县', 2, 421, '秦安县', 0);
INSERT INTO `dic_city` VALUES (3111, '620523', '甘谷县', 2, 421, '甘谷县', 0);
INSERT INTO `dic_city` VALUES (3112, '620524', '武山县', 2, 421, '武山县', 0);
INSERT INTO `dic_city` VALUES (3113, '620525', '张家川回族自治县', 2, 421, '张家川回族自治县', 0);
INSERT INTO `dic_city` VALUES (3114, '620602', '凉州区', 2, 422, '凉州区', 0);
INSERT INTO `dic_city` VALUES (3115, '620621', '民勤县', 2, 422, '民勤县', 0);
INSERT INTO `dic_city` VALUES (3116, '620622', '古浪县', 2, 422, '古浪县', 0);
INSERT INTO `dic_city` VALUES (3117, '620623', '天祝藏族自治县', 2, 422, '天祝藏族自治县', 0);
INSERT INTO `dic_city` VALUES (3118, '620702', '甘州区', 2, 423, '甘州区', 0);
INSERT INTO `dic_city` VALUES (3119, '620721', '肃南裕固族自治县', 2, 423, '肃南裕固族自治县', 0);
INSERT INTO `dic_city` VALUES (3120, '620722', '民乐县', 2, 423, '民乐县', 0);
INSERT INTO `dic_city` VALUES (3121, '620723', '临泽县', 2, 423, '临泽县', 0);
INSERT INTO `dic_city` VALUES (3122, '620724', '高台县', 2, 423, '高台县', 0);
INSERT INTO `dic_city` VALUES (3123, '620725', '山丹县', 2, 423, '山丹县', 0);
INSERT INTO `dic_city` VALUES (3124, '620802', '崆峒区', 2, 424, '崆峒区', 0);
INSERT INTO `dic_city` VALUES (3125, '620821', '泾川县', 2, 424, '泾川县', 0);
INSERT INTO `dic_city` VALUES (3126, '620822', '灵台县', 2, 424, '灵台县', 0);
INSERT INTO `dic_city` VALUES (3127, '620823', '崇信县', 2, 424, '崇信县', 0);
INSERT INTO `dic_city` VALUES (3128, '620824', '华亭县', 2, 424, '华亭县', 0);
INSERT INTO `dic_city` VALUES (3129, '620825', '庄浪县', 2, 424, '庄浪县', 0);
INSERT INTO `dic_city` VALUES (3130, '620826', '静宁县', 2, 424, '静宁县', 0);
INSERT INTO `dic_city` VALUES (3131, '620902', '肃州区', 2, 425, '肃州区', 0);
INSERT INTO `dic_city` VALUES (3132, '620921', '金塔县', 2, 425, '金塔县', 0);
INSERT INTO `dic_city` VALUES (3133, '620922', '安西县', 2, 425, '安西县', 0);
INSERT INTO `dic_city` VALUES (3134, '620923', '肃北蒙古族自治县', 2, 425, '肃北蒙古族自治县', 0);
INSERT INTO `dic_city` VALUES (3135, '620924', '阿克塞哈萨克族自治县', 2, 425, '阿克塞哈萨克族自治县', 0);
INSERT INTO `dic_city` VALUES (3136, '620981', '玉门市', 2, 425, '玉门市', 0);
INSERT INTO `dic_city` VALUES (3137, '620982', '敦煌市', 2, 425, '敦煌市', 0);
INSERT INTO `dic_city` VALUES (3138, '621002', '西峰区', 2, 426, '西峰区', 0);
INSERT INTO `dic_city` VALUES (3139, '621021', '庆城县', 2, 426, '庆城县', 0);
INSERT INTO `dic_city` VALUES (3140, '621022', '环县', 2, 426, '环县', 0);
INSERT INTO `dic_city` VALUES (3141, '621023', '华池县', 2, 426, '华池县', 0);
INSERT INTO `dic_city` VALUES (3142, '621024', '合水县', 2, 426, '合水县', 0);
INSERT INTO `dic_city` VALUES (3143, '621025', '正宁县', 2, 426, '正宁县', 0);
INSERT INTO `dic_city` VALUES (3144, '621026', '宁县', 2, 426, '宁县', 0);
INSERT INTO `dic_city` VALUES (3145, '621027', '镇原县', 2, 426, '镇原县', 0);
INSERT INTO `dic_city` VALUES (3146, '622421', '定西县', 2, 427, '定西县', 0);
INSERT INTO `dic_city` VALUES (3147, '622424', '通渭县', 2, 427, '通渭县', 0);
INSERT INTO `dic_city` VALUES (3148, '622425', '陇西县', 2, 427, '陇西县', 0);
INSERT INTO `dic_city` VALUES (3149, '622426', '渭源县', 2, 427, '渭源县', 0);
INSERT INTO `dic_city` VALUES (3150, '622427', '临洮县', 2, 427, '临洮县', 0);
INSERT INTO `dic_city` VALUES (3151, '622428', '漳县', 2, 427, '漳县', 0);
INSERT INTO `dic_city` VALUES (3152, '622429', '岷县', 2, 427, '岷县', 0);
INSERT INTO `dic_city` VALUES (3153, '622621', '武都县', 2, 428, '武都县', 0);
INSERT INTO `dic_city` VALUES (3154, '622623', '宕昌县', 2, 428, '宕昌县', 0);
INSERT INTO `dic_city` VALUES (3155, '622624', '成县', 2, 428, '成县', 0);
INSERT INTO `dic_city` VALUES (3156, '622625', '康县', 2, 428, '康县', 0);
INSERT INTO `dic_city` VALUES (3157, '622626', '文县', 2, 428, '文县', 0);
INSERT INTO `dic_city` VALUES (3158, '622627', '西和县', 2, 428, '西和县', 0);
INSERT INTO `dic_city` VALUES (3159, '622628', '礼县', 2, 428, '礼县', 0);
INSERT INTO `dic_city` VALUES (3160, '622629', '两当县', 2, 428, '两当县', 0);
INSERT INTO `dic_city` VALUES (3161, '622630', '徽县', 2, 428, '徽县', 0);
INSERT INTO `dic_city` VALUES (3162, '622901', '临夏市', 2, 429, '临夏市', 0);
INSERT INTO `dic_city` VALUES (3163, '622921', '临夏县', 2, 429, '临夏县', 0);
INSERT INTO `dic_city` VALUES (3164, '622922', '康乐县', 2, 429, '康乐县', 0);
INSERT INTO `dic_city` VALUES (3165, '622923', '永靖县', 2, 429, '永靖县', 0);
INSERT INTO `dic_city` VALUES (3166, '622924', '广河县', 2, 429, '广河县', 0);
INSERT INTO `dic_city` VALUES (3167, '622925', '和政县', 2, 429, '和政县', 0);
INSERT INTO `dic_city` VALUES (3168, '622926', '东乡族自治县', 2, 429, '东乡族自治县', 0);
INSERT INTO `dic_city` VALUES (3169, '622927', '积石山保安族东乡族撒拉族自治县', 2, 429, '积石山保安族东乡族撒', 0);
INSERT INTO `dic_city` VALUES (3170, '623001', '合作市', 2, 430, '合作市', 0);
INSERT INTO `dic_city` VALUES (3171, '623021', '临潭县', 2, 430, '临潭县', 0);
INSERT INTO `dic_city` VALUES (3172, '623022', '卓尼县', 2, 430, '卓尼县', 0);
INSERT INTO `dic_city` VALUES (3173, '623023', '舟曲县', 2, 430, '舟曲县', 0);
INSERT INTO `dic_city` VALUES (3174, '623024', '迭部县', 2, 430, '迭部县', 0);
INSERT INTO `dic_city` VALUES (3175, '623025', '玛曲县', 2, 430, '玛曲县', 0);
INSERT INTO `dic_city` VALUES (3176, '623026', '碌曲县', 2, 430, '碌曲县', 0);
INSERT INTO `dic_city` VALUES (3177, '623027', '夏河县', 2, 430, '夏河县', 0);
INSERT INTO `dic_city` VALUES (3178, '630102', '城东区', 2, 431, '城东区', 0);
INSERT INTO `dic_city` VALUES (3179, '630104', '城西区', 2, 431, '城西区', 0);
INSERT INTO `dic_city` VALUES (3180, '630121', '大通回族土族自治县', 2, 431, '大通回族土族自治县', 0);
INSERT INTO `dic_city` VALUES (3181, '630122', '湟中县', 2, 431, '湟中县', 0);
INSERT INTO `dic_city` VALUES (3182, '630123', '湟源县', 2, 431, '湟源县', 0);
INSERT INTO `dic_city` VALUES (3183, '632121', '平安县', 2, 432, '平安县', 0);
INSERT INTO `dic_city` VALUES (3184, '632122', '民和回族土族自治县', 2, 432, '民和回族土族自治县', 0);
INSERT INTO `dic_city` VALUES (3185, '632123', '乐都县', 2, 432, '乐都县', 0);
INSERT INTO `dic_city` VALUES (3186, '632126', '互助土族自治县', 2, 432, '互助土族自治县', 0);
INSERT INTO `dic_city` VALUES (3187, '632127', '化隆回族自治县', 2, 432, '化隆回族自治县', 0);
INSERT INTO `dic_city` VALUES (3188, '632128', '循化撒拉族自治县', 2, 432, '循化撒拉族自治县', 0);
INSERT INTO `dic_city` VALUES (3189, '632221', '门源回族自治县', 2, 433, '门源回族自治县', 0);
INSERT INTO `dic_city` VALUES (3190, '632222', '祁连县', 2, 433, '祁连县', 0);
INSERT INTO `dic_city` VALUES (3191, '632223', '海晏县', 2, 433, '海晏县', 0);
INSERT INTO `dic_city` VALUES (3192, '632224', '刚察县', 2, 433, '刚察县', 0);
INSERT INTO `dic_city` VALUES (3193, '632321', '同仁县', 2, 434, '同仁县', 0);
INSERT INTO `dic_city` VALUES (3194, '632322', '尖扎县', 2, 434, '尖扎县', 0);
INSERT INTO `dic_city` VALUES (3195, '632323', '泽库县', 2, 434, '泽库县', 0);
INSERT INTO `dic_city` VALUES (3196, '632324', '河南蒙古族自治县', 2, 434, '河南蒙古族自治县', 0);
INSERT INTO `dic_city` VALUES (3197, '632521', '共和县', 2, 435, '共和县', 0);
INSERT INTO `dic_city` VALUES (3198, '632522', '同德县', 2, 435, '同德县', 0);
INSERT INTO `dic_city` VALUES (3199, '632523', '贵德县', 2, 435, '贵德县', 0);
INSERT INTO `dic_city` VALUES (3200, '632524', '兴海县', 2, 435, '兴海县', 0);
INSERT INTO `dic_city` VALUES (3201, '632525', '贵南县', 2, 435, '贵南县', 0);
INSERT INTO `dic_city` VALUES (3202, '632621', '玛沁县', 2, 436, '玛沁县', 0);
INSERT INTO `dic_city` VALUES (3203, '632622', '班玛县', 2, 436, '班玛县', 0);
INSERT INTO `dic_city` VALUES (3204, '632623', '甘德县', 2, 436, '甘德县', 0);
INSERT INTO `dic_city` VALUES (3205, '632624', '达日县', 2, 436, '达日县', 0);
INSERT INTO `dic_city` VALUES (3206, '632625', '久治县', 2, 436, '久治县', 0);
INSERT INTO `dic_city` VALUES (3207, '632626', '玛多县', 2, 436, '玛多县', 0);
INSERT INTO `dic_city` VALUES (3208, '632721', '玉树县', 2, 437, '玉树县', 0);
INSERT INTO `dic_city` VALUES (3209, '632722', '杂多县', 2, 437, '杂多县', 0);
INSERT INTO `dic_city` VALUES (3210, '632723', '称多县', 2, 437, '称多县', 0);
INSERT INTO `dic_city` VALUES (3211, '632724', '治多县', 2, 437, '治多县', 0);
INSERT INTO `dic_city` VALUES (3212, '632725', '囊谦县', 2, 437, '囊谦县', 0);
INSERT INTO `dic_city` VALUES (3213, '632726', '曲麻莱县', 2, 437, '曲麻莱县', 0);
INSERT INTO `dic_city` VALUES (3214, '632801', '格尔木市', 2, 438, '格尔木市', 0);
INSERT INTO `dic_city` VALUES (3215, '632802', '德令哈市', 2, 438, '德令哈市', 0);
INSERT INTO `dic_city` VALUES (3216, '632821', '乌兰县', 2, 438, '乌兰县', 0);
INSERT INTO `dic_city` VALUES (3217, '632822', '都兰县', 2, 438, '都兰县', 0);
INSERT INTO `dic_city` VALUES (3218, '632823', '天峻县', 2, 438, '天峻县', 0);
INSERT INTO `dic_city` VALUES (3219, '640104', '兴庆区', 2, 439, '兴庆区', 0);
INSERT INTO `dic_city` VALUES (3220, '640105', '西夏区', 2, 439, '西夏区', 0);
INSERT INTO `dic_city` VALUES (3221, '640106', '金凤区', 2, 439, '金凤区', 0);
INSERT INTO `dic_city` VALUES (3222, '640121', '永宁县', 2, 439, '永宁县', 0);
INSERT INTO `dic_city` VALUES (3223, '640122', '贺兰县', 2, 439, '贺兰县', 0);
INSERT INTO `dic_city` VALUES (3224, '640202', '大武口区', 2, 440, '大武口区', 0);
INSERT INTO `dic_city` VALUES (3225, '640203', '石嘴山区', 2, 440, '石嘴山区', 0);
INSERT INTO `dic_city` VALUES (3226, '640221', '平罗县', 2, 440, '平罗县', 0);
INSERT INTO `dic_city` VALUES (3227, '640222', '陶乐县', 2, 440, '陶乐县', 0);
INSERT INTO `dic_city` VALUES (3228, '640223', '惠农县', 2, 440, '惠农县', 0);
INSERT INTO `dic_city` VALUES (3229, '640302', '利通区', 2, 441, '利通区', 0);
INSERT INTO `dic_city` VALUES (3230, '640321', '中卫县', 2, 441, '中卫县', 0);
INSERT INTO `dic_city` VALUES (3231, '640502', '中宁县', 2, 441, '中宁县', 0);
INSERT INTO `dic_city` VALUES (3232, '640323', '盐池县', 2, 441, '盐池县', 0);
INSERT INTO `dic_city` VALUES (3233, '640324', '同心县', 2, 441, '同心县', 0);
INSERT INTO `dic_city` VALUES (3234, '640381', '青铜峡市', 2, 441, '青铜峡市', 0);
INSERT INTO `dic_city` VALUES (3235, '640382', '灵武市', 2, 441, '灵武市', 0);
INSERT INTO `dic_city` VALUES (3236, '640402', '原州区', 2, 442, '原州区', 0);
INSERT INTO `dic_city` VALUES (3237, '640503', '海原县', 2, 442, '海原县', 0);
INSERT INTO `dic_city` VALUES (3238, '640422', '西吉县', 2, 442, '西吉县', 0);
INSERT INTO `dic_city` VALUES (3239, '640423', '隆德县', 2, 442, '隆德县', 0);
INSERT INTO `dic_city` VALUES (3240, '640424', '泾源县', 2, 442, '泾源县', 0);
INSERT INTO `dic_city` VALUES (3241, '640425', '彭阳县', 2, 442, '彭阳县', 0);
INSERT INTO `dic_city` VALUES (3242, '640501', '沙坡头区', 2, 443, '沙坡头区', 0);
INSERT INTO `dic_city` VALUES (3243, '640502', '中宁县', 2, 443, '中宁县', 0);
INSERT INTO `dic_city` VALUES (3244, '640503', '海原县', 2, 443, '海原县', 0);
INSERT INTO `dic_city` VALUES (3245, '650102', '天山区', 2, 444, '天山区', 0);
INSERT INTO `dic_city` VALUES (3246, '650103', '沙依巴克区', 2, 444, '沙依巴克区', 0);
INSERT INTO `dic_city` VALUES (3247, '650105', '水磨沟区', 2, 444, '水磨沟区', 0);
INSERT INTO `dic_city` VALUES (3248, '650106', '头屯河区', 2, 444, '头屯河区', 0);
INSERT INTO `dic_city` VALUES (3249, '650107', '达坂城区', 2, 444, '达坂城区', 0);
INSERT INTO `dic_city` VALUES (3250, '650121', '乌鲁木齐县', 2, 444, '乌鲁木齐县', 0);
INSERT INTO `dic_city` VALUES (3251, '650202', '独山子区', 2, 445, '独山子区', 0);
INSERT INTO `dic_city` VALUES (3252, '650203', '克拉玛依区', 2, 445, '克拉玛依区', 0);
INSERT INTO `dic_city` VALUES (3253, '650204', '白碱滩区', 2, 445, '白碱滩区', 0);
INSERT INTO `dic_city` VALUES (3254, '650205', '乌尔禾区', 2, 445, '乌尔禾区', 0);
INSERT INTO `dic_city` VALUES (3255, '652101', '吐鲁番市', 2, 446, '吐鲁番市', 0);
INSERT INTO `dic_city` VALUES (3256, '652122', '鄯善县', 2, 446, '鄯善县', 0);
INSERT INTO `dic_city` VALUES (3257, '652123', '托克逊县', 2, 446, '托克逊县', 0);
INSERT INTO `dic_city` VALUES (3258, '652201', '哈密市', 2, 447, '哈密市', 0);
INSERT INTO `dic_city` VALUES (3259, '652222', '巴里坤哈萨克自治县', 2, 447, '巴里坤哈萨克自治县', 0);
INSERT INTO `dic_city` VALUES (3260, '652223', '伊吾县', 2, 447, '伊吾县', 0);
INSERT INTO `dic_city` VALUES (3261, '652301', '昌吉市', 2, 448, '昌吉市', 0);
INSERT INTO `dic_city` VALUES (3262, '652302', '阜康市', 2, 448, '阜康市', 0);
INSERT INTO `dic_city` VALUES (3263, '652303', '米泉市', 2, 448, '米泉市', 0);
INSERT INTO `dic_city` VALUES (3264, '652323', '呼图壁县', 2, 448, '呼图壁县', 0);
INSERT INTO `dic_city` VALUES (3265, '652324', '玛纳斯县', 2, 448, '玛纳斯县', 0);
INSERT INTO `dic_city` VALUES (3266, '652325', '奇台县', 2, 448, '奇台县', 0);
INSERT INTO `dic_city` VALUES (3267, '652327', '吉木萨尔县', 2, 448, '吉木萨尔县', 0);
INSERT INTO `dic_city` VALUES (3268, '652328', '木垒哈萨克自治县', 2, 448, '木垒哈萨克自治县', 0);
INSERT INTO `dic_city` VALUES (3269, '652701', '博乐市', 2, 449, '博乐市', 0);
INSERT INTO `dic_city` VALUES (3270, '652722', '精河县', 2, 449, '精河县', 0);
INSERT INTO `dic_city` VALUES (3271, '652723', '温泉县', 2, 449, '温泉县', 0);
INSERT INTO `dic_city` VALUES (3272, '652801', '库尔勒市', 2, 450, '库尔勒市', 0);
INSERT INTO `dic_city` VALUES (3273, '652822', '轮台县', 2, 450, '轮台县', 0);
INSERT INTO `dic_city` VALUES (3274, '652823', '尉犁县', 2, 450, '尉犁县', 0);
INSERT INTO `dic_city` VALUES (3275, '652824', '若羌县', 2, 450, '若羌县', 0);
INSERT INTO `dic_city` VALUES (3276, '652825', '且末县', 2, 450, '且末县', 0);
INSERT INTO `dic_city` VALUES (3277, '652826', '焉耆回族自治县', 2, 450, '焉耆回族自治县', 0);
INSERT INTO `dic_city` VALUES (3278, '652827', '和静县', 2, 450, '和静县', 0);
INSERT INTO `dic_city` VALUES (3279, '652828', '和硕县', 2, 450, '和硕县', 0);
INSERT INTO `dic_city` VALUES (3280, '652829', '博湖县', 2, 450, '博湖县', 0);
INSERT INTO `dic_city` VALUES (3281, '652901', '阿克苏市', 2, 451, '阿克苏市', 0);
INSERT INTO `dic_city` VALUES (3282, '652922', '温宿县', 2, 451, '温宿县', 0);
INSERT INTO `dic_city` VALUES (3283, '652923', '库车县', 2, 451, '库车县', 0);
INSERT INTO `dic_city` VALUES (3284, '652924', '沙雅县', 2, 451, '沙雅县', 0);
INSERT INTO `dic_city` VALUES (3285, '652925', '新和县', 2, 451, '新和县', 0);
INSERT INTO `dic_city` VALUES (3286, '652926', '拜城县', 2, 451, '拜城县', 0);
INSERT INTO `dic_city` VALUES (3287, '652927', '乌什县', 2, 451, '乌什县', 0);
INSERT INTO `dic_city` VALUES (3288, '652928', '阿瓦提县', 2, 451, '阿瓦提县', 0);
INSERT INTO `dic_city` VALUES (3289, '652929', '柯坪县', 2, 451, '柯坪县', 0);
INSERT INTO `dic_city` VALUES (3290, '653001', '阿图什市', 2, 452, '阿图什市', 0);
INSERT INTO `dic_city` VALUES (3291, '653022', '阿克陶县', 2, 452, '阿克陶县', 0);
INSERT INTO `dic_city` VALUES (3292, '653023', '阿合奇县', 2, 452, '阿合奇县', 0);
INSERT INTO `dic_city` VALUES (3293, '653024', '乌恰县', 2, 452, '乌恰县', 0);
INSERT INTO `dic_city` VALUES (3294, '653101', '喀什市', 2, 453, '喀什市', 0);
INSERT INTO `dic_city` VALUES (3295, '653121', '疏附县', 2, 453, '疏附县', 0);
INSERT INTO `dic_city` VALUES (3296, '653122', '疏勒县', 2, 453, '疏勒县', 0);
INSERT INTO `dic_city` VALUES (3297, '653123', '英吉沙县', 2, 453, '英吉沙县', 0);
INSERT INTO `dic_city` VALUES (3298, '653124', '泽普县', 2, 453, '泽普县', 0);
INSERT INTO `dic_city` VALUES (3299, '653125', '莎车县', 2, 453, '莎车县', 0);
INSERT INTO `dic_city` VALUES (3300, '653126', '叶城县', 2, 453, '叶城县', 0);
INSERT INTO `dic_city` VALUES (3301, '653127', '麦盖提县', 2, 453, '麦盖提县', 0);
INSERT INTO `dic_city` VALUES (3302, '653128', '岳普湖县', 2, 453, '岳普湖县', 0);
INSERT INTO `dic_city` VALUES (3303, '653129', '伽师县', 2, 453, '伽师县', 0);
INSERT INTO `dic_city` VALUES (3304, '653130', '巴楚县', 2, 453, '巴楚县', 0);
INSERT INTO `dic_city` VALUES (3305, '653131', '塔什库尔干塔吉克自治县', 2, 453, '塔什库尔干塔吉克自治', 0);
INSERT INTO `dic_city` VALUES (3306, '653201', '和田市', 2, 454, '和田市', 0);
INSERT INTO `dic_city` VALUES (3307, '653221', '和田县', 2, 454, '和田县', 0);
INSERT INTO `dic_city` VALUES (3308, '653222', '墨玉县', 2, 454, '墨玉县', 0);
INSERT INTO `dic_city` VALUES (3309, '653223', '皮山县', 2, 454, '皮山县', 0);
INSERT INTO `dic_city` VALUES (3310, '653224', '洛浦县', 2, 454, '洛浦县', 0);
INSERT INTO `dic_city` VALUES (3311, '653225', '策勒县', 2, 454, '策勒县', 0);
INSERT INTO `dic_city` VALUES (3312, '653226', '于田县', 2, 454, '于田县', 0);
INSERT INTO `dic_city` VALUES (3313, '653227', '民丰县', 2, 454, '民丰县', 0);
INSERT INTO `dic_city` VALUES (3314, '654002', '伊宁市', 2, 455, '伊宁市', 0);
INSERT INTO `dic_city` VALUES (3315, '654003', '奎屯市', 2, 455, '奎屯市', 0);
INSERT INTO `dic_city` VALUES (3316, '654021', '伊宁县', 2, 455, '伊宁县', 0);
INSERT INTO `dic_city` VALUES (3317, '654022', '察布查尔锡伯自治县', 2, 455, '察布查尔锡伯自治县', 0);
INSERT INTO `dic_city` VALUES (3318, '654023', '霍城县', 2, 455, '霍城县', 0);
INSERT INTO `dic_city` VALUES (3319, '654024', '巩留县', 2, 455, '巩留县', 0);
INSERT INTO `dic_city` VALUES (3320, '654025', '新源县', 2, 455, '新源县', 0);
INSERT INTO `dic_city` VALUES (3321, '654026', '昭苏县', 2, 455, '昭苏县', 0);
INSERT INTO `dic_city` VALUES (3322, '654027', '特克斯县', 2, 455, '特克斯县', 0);
INSERT INTO `dic_city` VALUES (3323, '654028', '尼勒克县', 2, 455, '尼勒克县', 0);
INSERT INTO `dic_city` VALUES (3324, '654201', '塔城市', 2, 456, '塔城市', 0);
INSERT INTO `dic_city` VALUES (3325, '654202', '乌苏市', 2, 456, '乌苏市', 0);
INSERT INTO `dic_city` VALUES (3326, '654221', '额敏县', 2, 456, '额敏县', 0);
INSERT INTO `dic_city` VALUES (3327, '654223', '沙湾县', 2, 456, '沙湾县', 0);
INSERT INTO `dic_city` VALUES (3328, '654224', '托里县', 2, 456, '托里县', 0);
INSERT INTO `dic_city` VALUES (3329, '654225', '裕民县', 2, 456, '裕民县', 0);
INSERT INTO `dic_city` VALUES (3330, '654226', '和布克赛尔蒙古自治县', 2, 456, '和布克赛尔蒙古自治县', 0);
INSERT INTO `dic_city` VALUES (3331, '654301', '阿勒泰市', 2, 457, '阿勒泰市', 0);
INSERT INTO `dic_city` VALUES (3332, '654321', '布尔津县', 2, 457, '布尔津县', 0);
INSERT INTO `dic_city` VALUES (3333, '654322', '富蕴县', 2, 457, '富蕴县', 0);
INSERT INTO `dic_city` VALUES (3334, '654323', '福海县', 2, 457, '福海县', 0);
INSERT INTO `dic_city` VALUES (3335, '654324', '哈巴河县', 2, 457, '哈巴河县', 0);
INSERT INTO `dic_city` VALUES (3336, '654325', '青河县', 2, 457, '青河县', 0);
INSERT INTO `dic_city` VALUES (3337, '654326', '吉木乃县', 2, 457, '吉木乃县', 0);
INSERT INTO `dic_city` VALUES (3338, '710303', '中正区', 2, 462, '中正区', 0);
INSERT INTO `dic_city` VALUES (3339, '710105', '大安区', 2, 462, '大安区', 0);
INSERT INTO `dic_city` VALUES (3340, '710106', '万华区', 2, 462, '万华区', 0);
INSERT INTO `dic_city` VALUES (3341, '710302', '信义区', 2, 462, '信义区', 0);
INSERT INTO `dic_city` VALUES (3342, '710108', '士林区', 2, 462, '士林区', 0);
INSERT INTO `dic_city` VALUES (3343, '710109', '北投区', 2, 462, '北投区', 0);
INSERT INTO `dic_city` VALUES (3344, '710110', '内湖区', 2, 462, '内湖区', 0);
INSERT INTO `dic_city` VALUES (3345, '710111', '南港区', 2, 462, '南港区', 0);
INSERT INTO `dic_city` VALUES (3346, '710112', '文山区', 2, 462, '文山区', 0);
INSERT INTO `dic_city` VALUES (3347, '710202', '前金区', 2, 463, '前金区', 0);
INSERT INTO `dic_city` VALUES (3348, '710203', '芩雅区', 2, 463, '芩雅区', 0);
INSERT INTO `dic_city` VALUES (3349, '710204', '盐埕区', 2, 463, '盐埕区', 0);
INSERT INTO `dic_city` VALUES (3350, '710205', '鼓山区', 2, 463, '鼓山区', 0);
INSERT INTO `dic_city` VALUES (3351, '710206', '旗津区', 2, 463, '旗津区', 0);
INSERT INTO `dic_city` VALUES (3352, '710207', '前镇区', 2, 463, '前镇区', 0);
INSERT INTO `dic_city` VALUES (3353, '710208', '三民区', 2, 463, '三民区', 0);
INSERT INTO `dic_city` VALUES (3354, '710209', '左营区', 2, 463, '左营区', 0);
INSERT INTO `dic_city` VALUES (3355, '710210', '楠梓区', 2, 463, '楠梓区', 0);
INSERT INTO `dic_city` VALUES (3356, '710211', '小港区', 2, 463, '小港区', 0);
INSERT INTO `dic_city` VALUES (3357, '710301', '仁爱区', 2, 464, '仁爱区', 0);
INSERT INTO `dic_city` VALUES (3358, '710302', '信义区', 2, 464, '信义区', 0);
INSERT INTO `dic_city` VALUES (3359, '710303', '中正区', 2, 464, '中正区', 0);
INSERT INTO `dic_city` VALUES (3360, '710305', '安乐区', 2, 464, '安乐区', 0);
INSERT INTO `dic_city` VALUES (3361, '710306', '暖暖区', 2, 464, '暖暖区', 0);
INSERT INTO `dic_city` VALUES (3362, '710307', '七堵区', 2, 464, '七堵区', 0);
INSERT INTO `dic_city` VALUES (3363, '710401', '中区', 2, 465, '中区', 0);
INSERT INTO `dic_city` VALUES (3364, '710402', '东区', 2, 465, '东区', 0);
INSERT INTO `dic_city` VALUES (3365, '710503', '南区', 2, 465, '南区', 0);
INSERT INTO `dic_city` VALUES (3366, '710404', '西区', 2, 465, '西区', 0);
INSERT INTO `dic_city` VALUES (3367, '710602', '北区', 2, 465, '北区', 0);
INSERT INTO `dic_city` VALUES (3368, '710406', '北屯区', 2, 465, '北屯区', 0);
INSERT INTO `dic_city` VALUES (3369, '710407', '西屯区', 2, 465, '西屯区', 0);
INSERT INTO `dic_city` VALUES (3370, '710408', '南屯区', 2, 465, '南屯区', 0);
INSERT INTO `dic_city` VALUES (3371, '710501', '中西区', 2, 466, '中西区', 0);
INSERT INTO `dic_city` VALUES (3372, '710502', '东区', 2, 466, '东区', 0);
INSERT INTO `dic_city` VALUES (3373, '710503', '南区', 2, 466, '南区', 0);
INSERT INTO `dic_city` VALUES (3374, '710504', '北区', 2, 466, '北区', 0);
INSERT INTO `dic_city` VALUES (3375, '710505', '安平区', 2, 466, '安平区', 0);
INSERT INTO `dic_city` VALUES (3376, '710506', '安南区', 2, 466, '安南区', 0);
INSERT INTO `dic_city` VALUES (3377, '710601', '东区', 2, 467, '东区', 0);
INSERT INTO `dic_city` VALUES (3378, '710602', '北区', 2, 467, '北区', 0);
INSERT INTO `dic_city` VALUES (3379, '710603', '香山区', 2, 467, '香山区', 0);
INSERT INTO `dic_city` VALUES (3380, '710701', '东区', 2, 468, '东区', 0);
INSERT INTO `dic_city` VALUES (3381, '710702', '西区', 2, 468, '西区', 0);

-- ----------------------------
-- Table structure for hall_schema
-- ----------------------------
DROP TABLE IF EXISTS `hall_schema`;
CREATE TABLE `hall_schema`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `store_id` bigint(20) NOT NULL,
  `hall_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `schema_id` bigint(20) NOT NULL,
  `a_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 45 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;


-- ----------------------------
-- Table structure for ticket_price_schema
-- ----------------------------
DROP TABLE IF EXISTS `ticket_price_schema`;
CREATE TABLE `ticket_price_schema`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_id` bigint(20) NOT NULL DEFAULT -1,
  `schema_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sort_num` int(11) NOT NULL DEFAULT 0,
  `schema_status` char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '00' COMMENT '票类启用非启用状态',
  `by_permission` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否需要授权',
  `week_rule` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '-' COMMENT '每周几有效',
  `time_rule` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '-' COMMENT '每天什么时候有效',
  `month_rule` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '-' COMMENT '每月什么时候有效',
  `a_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` int(11) NOT NULL DEFAULT 0,
  `pid` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '票类(比如成人票儿童票等)' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for ticket_price_schema_group
-- ----------------------------
DROP TABLE IF EXISTS `ticket_price_schema_group`;
CREATE TABLE `ticket_price_schema_group`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `a_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` int(11) NOT NULL DEFAULT 0,
  `group_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pid` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '票类分组方便查看' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for ticket_price_schema_item
-- ----------------------------
DROP TABLE IF EXISTS `ticket_price_schema_item`;
CREATE TABLE `ticket_price_schema_item`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `schema_id` bigint(20) NOT NULL,
  `movie_dimensional` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '-',
  `movie_size` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '-',
  `price` bigint(20) NOT NULL COMMENT '单价',
  `is_custom` bit(1) NOT NULL DEFAULT b'0',
  `sort_num` int(11) NOT NULL DEFAULT 0,
  `a_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0),
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `delete_time` datetime(0) NULL DEFAULT NULL,
  `flag` int(11) NOT NULL DEFAULT 0,
  `pid` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;


-- ----------------------------
-- Table structure for unregu_record
-- ----------------------------
DROP TABLE IF EXISTS `unregu_record`;
CREATE TABLE `unregu_record`  (
  `id` bigint(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `pid` bigint(20) NULL DEFAULT NULL,
  `store_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `hall_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `film_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `plan_id` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `plan_date` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `plan_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `seats` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `order_id` bigint(20) NULL DEFAULT NULL,
  `order_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `prot_code` char(4) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `prot_info` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `a_time` datetime(0) NULL DEFAULT NULL,
  `c_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `flag` tinyint(4) NOT NULL DEFAULT 0,
  `notify_mobiles` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
