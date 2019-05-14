package com.jeancoder.ticketingsys.ready.location

import com.jeancoder.app.sdk.source.DatabaseSource
import com.jeancoder.core.power.DatabasePower
import com.jeancoder.ticketingsys.ready.location.dto.Extensive
import java.util.List

class LocationService {
	private static final LocationService INSTANCE = new LocationService();
	public List<Extensive> getAllProvinces(){
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		String sql = '''
				SELECT
				id
				,city_no as no
				,city_name as name
				,m_level as level
				FROM
				dic_city
				WHERE m_level = 0;
		''';
		List<Extensive> provinces = dbpower.doQueryList(Extensive.class, sql);
		return provinces;
	}
	
	public List<Extensive> getAllCitysByProvinceId(Long provinceId){
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		String sql = '''
				SELECT
				id
				,city_no as no
				,city_name as name
				,m_level as level
				FROM
				dic_city
				WHERE pid = ?;
		''';
		List<Extensive> citys = dbpower.doQueryList(Extensive.class, sql,provinceId);
		return citys;
	}
	
	public List<Extensive> getAllAreasByCityId(Long cityId){
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		String sql = '''
				SELECT
				id
				,city_no as no
				,city_name as name
				,m_level as level
				FROM
				dic_city
				WHERE pid = ?;
				''';
		List<Extensive> areas = dbpower.doQueryList(Extensive.class, sql,cityId);
		return areas;
	}
}
