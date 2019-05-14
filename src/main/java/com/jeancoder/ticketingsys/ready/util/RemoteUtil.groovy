package com.jeancoder.ticketingsys.ready.util

import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.power.CommunicationPower
import com.jeancoder.jdbc.JcPage
import com.jeancoder.ticketingsys.ready.dto.sys.AuthToken
import com.jeancoder.ticketingsys.ready.dto.sys.SysProjectInfo
import com.jeancoder.ticketingsys.ready.holder.GlobalHolder

public class RemoteUtil {

	public static <T> T connect(Class<T> mapclass, def point, def address) {
		List<CommunicationParam> params = new ArrayList<CommunicationParam>();
		CommunicationPower systemCaller = CommunicationSource.getCommunicator(point);
		def ret = systemCaller.doworkAsString(address, params);
		T obj = JackSonBeanMapper.fromJson(ret, mapclass);
		return obj;
	}
	
	public static <T> T connect(Class<T> mapclass, def point, def address, List<CommunicationParam> params) {
		CommunicationPower systemCaller = CommunicationSource.getCommunicator(point);
		def ret = systemCaller.doworkAsString(address, params);
		T obj = JackSonBeanMapper.fromJson(ret, mapclass);
		return obj;
	}
	
	
	public static String connect(def point, def address, List<CommunicationParam> params) {
		CommunicationPower systemCaller = CommunicationSource.getCommunicator(point);
		return systemCaller.doworkAsString(address, params);
	}
	
	public static Map<String,Object> connectMap(def point, def address, List<CommunicationParam> params) {
		CommunicationPower systemCaller = CommunicationSource.getCommunicator(point);
		def ret =  systemCaller.doworkAsString(address, params);
		return JackSonBeanMapper.jsonToMap(ret);
	}
	
	public static <T> List<T>  connectList(Class<T> mapclass, def point, def address) {
		List<CommunicationParam> params = new ArrayList<CommunicationParam>();
		CommunicationPower systemCaller = CommunicationSource.getCommunicator(point);
		def ret = systemCaller.doworkAsString(address, params);
		List<T> obj = JackSonBeanMapper.jsonToList(ret, mapclass)
		return obj;
	}
	
	
	public static <T> List<T>  connectList(Class<T> mapclass, def point, def address, List<CommunicationParam> params) {
		CommunicationPower systemCaller = CommunicationSource.getCommunicator(point);
		def ret = systemCaller.doworkAsString(address, params);
		List<T> obj = JackSonBeanMapper.jsonToList(ret, mapclass)
		return obj;
	}
	
	public static <T> JcPage<T>  connectPage(Class<T> mapclass, def point, def address, List<CommunicationParam> params) {
		CommunicationPower systemCaller = CommunicationSource.getCommunicator(point);
		def ret = systemCaller.doworkAsString(address, params);
		println ret;
		JcPage<T> obj = JackSonBeanMapper.fromJson(ret, new JcPage<T>().getClass());
		return obj;
	}
	
	public static SysProjectInfo getProj() {
		return GlobalHolder.getProj();
	}
	
	public static AuthToken getAuthToken() {
		return GlobalHolder.getToken();
	}
	
	
	
}
