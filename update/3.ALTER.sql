/*20181207*/
ALTER TABLE `data_tc_ss_cinema_config` ADD COLUMN `pid`  bigint(20) NOT NULL;



/*禁售时间*/
ALTER TABLE `data_store_info` ADD COLUMN `time_type`  varchar(1);
ALTER TABLE `data_store_info` ADD COLUMN `time_quantity`  varchar(10);