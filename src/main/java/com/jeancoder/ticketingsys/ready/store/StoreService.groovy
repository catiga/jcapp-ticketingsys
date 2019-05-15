package com.jeancoder.ticketingsys.ready.store

import com.jeancoder.app.sdk.source.DatabaseSource
import com.jeancoder.core.power.DatabasePower
import com.jeancoder.ticketingsys.ready.store.dto.CinemaAuthInfo
import com.jeancoder.ticketingsys.ready.store.dto.City
import com.jeancoder.ticketingsys.ready.store.dto.StoreInfo
import com.jeancoder.ticketingsys.ready.store.entity.HallSchema
import com.jeancoder.ticketingsys.ready.system.TicketingSystemService
import com.jeancoder.ticketingsys.ready.system.dto.SystemMinInfo

class StoreService {
	public static final StoreService INSTANCE = new StoreService();

	public static void addOrUpdate(Long id , Long store_basic,
			String cinema_code ,
			String config_id ,
			String cinema_name ,
			String cinema_physics_name ,
			String prov_no ,
			String prov_name ,
			String city_no ,
			String city_name ,
			String area_no ,
			String area_name ,
			String cinema_address,Long projId) {

		DatabasePower sqlpower = DatabaseSource.getDatabasePower();
		if(id == null) {
			String sql = '''
			INSERT INTO `data_store_info`
            (`store_logo`,
			 `store_basic`,
             `store_no`,
             `store_name`,
             `province`,
             `city`,
             `zone`,
             `address`,
             `phone`,
             `manager_id`,
             `latitude`,
             `longitude`,
             `duty_manager_id`,
             `province_no`,
             `city_no`,
             `zone_no`,
             `domain`,
			 `config_id`,
			 `physics_name`,`proj_id`)
		VALUES      
			('',
			 ?,
             ?,
             ?,
             ?,
             ?,
             ?,
             ?,
             '',
             NULL,
             NULL,
             NULL,
             NULL,
             ?,
             ?,
             ?,
             NULL,
			?,
			?,?);''';
			sqlpower.doUpdate(sql, store_basic, cinema_code,cinema_name,prov_name,city_name,area_name,cinema_address,prov_no,city_no,area_no,config_id,cinema_physics_name,projId);
		}
		else {
			String sql = '''
					update
					`data_store_info`
					set `store_no` = ?,
					`store_name` = ?,
					`province` = ?,
					`city` = ?,
					`zone` = ?,
					`address` = ?,
					`province_no` = ?,
					`city_no` = ?,
					`zone_no` = ?,
					`config_id` = ?,
					`physics_name` = ?
					WHERE id = ?
				''';
			sqlpower.doUpdate(sql, cinema_code,cinema_name,prov_name,city_name,area_name,cinema_address,prov_no,city_no,area_no,config_id,cinema_physics_name,id);
		}
	}

	public void del(Long id) {
		DatabasePower sqlpower = DatabaseSource.getDatabasePower();
		String sql = '''
			UPDATE 
			data_store_info
			SET flag = ?
			WHERE id = ?;
		''';
		sqlpower.doUpdate(sql, -1,id);
	}

	public List<StoreInfo> getAll(){
		DatabasePower sqlpower = DatabaseSource.getDatabasePower();
		String sql = '''
			select
			id,
			store_basic,
			store_no,
			store_name,
			province,
			city,
			zone,
			address,
			province_no,
			city_no,
			zone_no,
			config_id,
			c_time
			from
			data_store_info
			where 1=1
			and flag != ?
			''';
		List<StoreInfo> stores = sqlpower.doQueryList(StoreInfo.class,sql,-1);
		for(StoreInfo store : stores) {
			if(store.getConfig_id() != null) {
				def systemInfo = TicketingSystemService.INSTANCE.getMinInfoById(store.getConfig_id());
				store.setSystemInfo(systemInfo);
			}
		}
		return stores;
	}

	public StoreInfo getById(Long id) {
		DatabasePower sqlpower = DatabaseSource.getDatabasePower();
		String sql = '''
			select
			id,
			store_basic,
			store_no,
			store_name,
			province,
			city,
			zone,
			address,
			province_no,
			city_no,
			zone_no,
			config_id,
			c_time,
			proj_id
			from
			data_store_info
			where 1=1
			and flag != ?
			and id = ?
			''';
		StoreInfo store = sqlpower.doQueryUnique(StoreInfo.class,sql,-1,id);
		if(store.getConfig_id() != null) {
			SystemMinInfo systemInfo = TicketingSystemService.INSTANCE.getMinInfoById(store.getConfig_id());
			store.setSystemInfo(systemInfo);
		}
		return store;
	}

	/**
	 * 获取影城 售票系统调用所需授权信息
	 * @param id
	 * @return
	 */
	public CinemaAuthInfo getCinemaAuthInfo(Long cinemaId) {
		DatabasePower sqlpower = DatabaseSource.getDatabasePower();
		String sql = '''
			select
			a.id as id,
			a.store_no as cinemaCode,
			b.ss_code as systemSsCode,
			b.auth_chan_num as authChannelNo,
			b.auth_chan_code as authChannelCode,
			b.auth_chan_url as authChannelUrl,
			b.store_cinema_num as cinemaId,
			a.proj_id as pid
			from
			data_store_info a
			join
			data_tc_ss_cinema_config b
			on a.config_id = b.id
			WHERE 1=1
			AND a.flag != ?
			AND b.flag != ?
			and a.id = ?
				''';
		CinemaAuthInfo cinemaAuthInfo = sqlpower.doQueryUnique(CinemaAuthInfo.class, sql,-1,-1,cinemaId);
		return cinemaAuthInfo;
	}

	/**
	 * 获取影城 售票系统调用所需授权信息
	 * @param id
	 * @return
	 */
	public List<CinemaAuthInfo> getCinemaAuthInfo() {
		DatabasePower sqlpower = DatabaseSource.getDatabasePower();
		String sql = '''
			select
			a.store_no as cinemaCode,
			b.ss_code as systemSsCode,
			b.auth_chan_num as authChannelNo,
			b.auth_chan_code as authChannelCode,
			b.auth_chan_url as authChannelUrl,
			b.store_cinema_num as cinemaId,
			a.proj_id as pid
			from
			data_store_info a
			join
			data_tc_ss_cinema_config b
			on a.config_id = b.id
			WHERE 1=1
			AND a.flag != ?
			AND b.flag != ?
				''';
		List<CinemaAuthInfo> cinemaAuthInfos = sqlpower.doQueryList(CinemaAuthInfo.class, sql,-1,-1);
		return cinemaAuthInfos;
	}

	public void saveHallSchemas(Long cinemaId,String hallId,List<HallSchema> schemas) {
		DatabasePower sqlpower = DatabaseSource.getDatabasePower();

		String delSql = "UPDATE hall_schema SET flag = -1 WHERE flag != -1 AND store_id = ? AND hall_id = ?";
		sqlpower.doUpdate(delSql, cinemaId,hallId);

		for(HallSchema schema : schemas) {
			sqlpower.doUpdateSerialize(schema, "id");
		}
	}

	public List<StoreInfo> getStoresByProjId(Long projId){
		DatabasePower sqlpower = DatabaseSource.getDatabasePower();
		String sql = '''
			select
			id,
			store_no,
			store_name,
			province,
			city,
			zone,
			address,
			province_no,
			city_no,
			zone_no,
			config_id,
			c_time
			from
			data_store_info
			where 1=1
			and flag != ? and proj_id = ?
			''';
		List<StoreInfo> stores = sqlpower.doQueryList(StoreInfo.class,sql,-1,projId);
		for(StoreInfo store : stores) {
			if(store.getConfig_id() != null) {
				SystemMinInfo systemInfo = TicketingSystemService.INSTANCE.getMinInfoById(store.getConfig_id());
				store.setSystemInfo(systemInfo);
			}
		}
		return stores;
	}

	public List<StoreInfo> getStoresByCity(String cityNo,String cityName){
		cityNo = cityNo == null ? "" : cityNo;
		cityName = cityName == null ? "" : cityName;
		String sql = '''
		select
		a.*
		from
		data_store_info a
		join
		dic_city b
		on a.city_no = b.`city_no`
		where a.flag != ? and (b.`city_no` = ? or b.`city_name` = ?) order by b.city_no asc
		''';
		List<StoreInfo> stores = DatabaseSource.getDatabasePower().doQueryList(StoreInfo.class,sql,-1,cityNo,cityName);
	}

	public List<City> getProjCitys(BigInteger projId){
		String sql = '''
		select
		b.*
		from
		data_store_info a
		join
		dic_city b
		on a.city_no = b.`city_no`
		where a.flag != ? and a.proj_id = ?
'''
		return DatabaseSource.getDatabasePower().doQueryList(City.class,sql,-1,projId);
	}
	public List<StoreInfo> find_stores( def pid) {
		def sql = "select * from data_store_info where flag!=-1";
		sql += ' and proj_id=?';
		sql += ' order by c_time desc';
		return DatabaseSource.getDatabasePower().doQueryList(StoreInfo.class,sql, pid);
	}
}
