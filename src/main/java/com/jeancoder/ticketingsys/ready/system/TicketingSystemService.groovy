package com.jeancoder.ticketingsys.ready.system

import com.jeancoder.app.sdk.source.DatabaseSource
import com.jeancoder.core.power.DatabasePower
import com.jeancoder.ticketingsys.ready.system.dto.SystemCodeInfo
import com.jeancoder.ticketingsys.ready.system.dto.SystemMinInfo

import java.util.List;
import java.util.Date;

class TicketingSystemService {
	public static final TicketingSystemService INSTANCE = new TicketingSystemService();
	
	public void addOrUpdate(Long id,String config_name,String channel_code,String channel_key,String channel_address,String system_code,String system_name,def pid) {
		String sql = null;
		DatabasePower sqlpower = DatabaseSource.getDatabasePower();
		if(id == null) {
			sql = "INSERT INTO data_tc_ss_cinema_config(config_name,ss_code,ss_name,auth_chan_num,auth_chan_code,auth_chan_url,pid) VALUES (?,?,?,?,?,?,?)";
			sqlpower.doUpdate(sql, config_name,system_code,system_name,channel_code,channel_key,channel_address,pid);
		}else {
			sql = "UPDATE data_tc_ss_cinema_config set config_name = ?, ss_code = ?,ss_name = ?,auth_chan_num = ?,auth_chan_code = ?,auth_chan_url = ?, pid=? WHERE id = ?";
			sqlpower.doUpdate(sql, config_name,system_code,system_name,channel_code,channel_key,channel_address,id,pid);
		}
	}
	
	public List<SystemCodeInfo> getAll(def pid){
		DatabasePower sqlpower = DatabaseSource.getDatabasePower();
		String sql = '''
			select 
			id
			,config_name
			,ss_code
			,ss_name
			,auth_chan_num
			,auth_chan_code
			,auth_chan_url
			,c_time 
			from 
			data_tc_ss_cinema_config 
			where flag != ? and pid=?
		''';
		List<SystemCodeInfo> objectResult = sqlpower.doQueryList(SystemCodeInfo.class, sql, -1,pid);
		return objectResult;
	}
	
	public List<SystemCodeInfo> getAll(){
		DatabasePower sqlpower = DatabaseSource.getDatabasePower();
		String sql = '''
			select 
			id
			,config_name
			,ss_code
			,ss_name
			,auth_chan_num
			,auth_chan_code
			,auth_chan_url
			,c_time 
			from 
			data_tc_ss_cinema_config 
			where flag != ? 
		''';
		List<SystemCodeInfo> objectResult = sqlpower.doQueryList(SystemCodeInfo.class, sql, -1);
		return objectResult;
	}
	
	public List<SystemCodeInfo> getAllInfo(){
		DatabasePower sqlpower = DatabaseSource.getDatabasePower();
		String sql = '''
			select 
			id
			,config_name
			,ss_code
			,ss_name
			,auth_chan_num
			,auth_chan_code
			,auth_chan_url
			,c_time 
			from 
			data_tc_ss_cinema_config 
			where flag != ?
		''';
		List<SystemCodeInfo> objectResult = sqlpower.doQueryList(SystemCodeInfo.class, sql, -1);
		return objectResult;
	}
	
	public List<SystemMinInfo> getAllMinInfo(){
		DatabasePower sqlpower = DatabaseSource.getDatabasePower();
		String sql = '''
			select 
			id
			,config_name
			from 
			data_tc_ss_cinema_config 
			where flag != ?
		''';
		List<SystemMinInfo> objectResult = sqlpower.doQueryList(SystemMinInfo.class, sql, -1);
		return objectResult;
	}
	
	public SystemMinInfo getMinInfoById(Long id) {
		DatabasePower sqlpower = DatabaseSource.getDatabasePower();
		String sql = '''
			select 
			id
			,config_name
			from 
			data_tc_ss_cinema_config 
			where flag != ?
			and id = ?
		''';
		SystemMinInfo info = sqlpower.doQueryUnique(SystemMinInfo.class, sql, -1,id);
		return info;
	}
	
	public void delete(Long id) {
		DatabasePower sqlpower = DatabaseSource.getDatabasePower();
		String sql = "UPDATE data_tc_ss_cinema_config SET flag = -1 WHERE id = ?";
		sqlpower.doUpdate(sql, id);
	}
	
	public SystemCodeInfo getById(Long id) {
		DatabasePower sqlpower = DatabaseSource.getDatabasePower();
		String sql = '''
			select 
			id
			,config_name
			,ss_code
			,ss_name
			,auth_chan_num
			,auth_chan_code
			,auth_chan_url
			,c_time 
			from 
			data_tc_ss_cinema_config 
			where id = ? AND flag != ?
		''';
		return sqlpower.doQueryUnique(SystemCodeInfo.class,sql, id,-1);
	}
}
