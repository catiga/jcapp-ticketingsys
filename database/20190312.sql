ALTER TABLE data_tc_ss_sale_order_info ADD COLUMN store_basic BIGINT(20);

CREATE TABLE `data_tc_ss_handle_fee_setting` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT,
  `store_id` bigint(20) DEFAULT NULL,
  `store_basic` bigint(20) DEFAULT NULL,
  `hall_id` bigint(20) DEFAULT NULL,
  `hall_num` varchar(255) DEFAULT NULL,
  `fee` decimal(10,2) DEFAULT NULL,
  `a_time` datetime DEFAULT NULL,
  `c_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `flag` tinyint(4) NOT NULL DEFAULT '0',
  `pid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;


ALTER TABLE data_tc_ss_sale_order_seat ADD COLUMN service_fee DECIMAL(10,2) NOT NULL DEFAULT 0;

ALTER TABLE `data_tc_ss_sale_order_info` modify column `handle_fee` DECIMAL(10,2) NOT NULL DEFAULT 0;