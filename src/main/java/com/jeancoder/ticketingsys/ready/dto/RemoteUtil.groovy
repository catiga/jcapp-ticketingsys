package com.jeancoder.ticketingsys.ready.dto

import java.util.List

import com.jeancoder.app.sdk.source.CommunicationSource
import com.jeancoder.core.power.CommunicationMethod
import com.jeancoder.core.power.CommunicationParam
import com.jeancoder.core.power.CommunicationPower
import com.jeancoder.core.util.JackSonBeanMapper

class RemoteUtil {
	
	public static def connect(def point, def address, def param_dic) {
		List<CommunicationParam> params = new ArrayList<CommunicationParam>();
		if(param_dic) {
			for(kv in param_dic) {
				CommunicationParam param = new CommunicationParam(kv.key, kv.value);
				params.add(param);
			}
		}
		CommunicationPower systemCaller = CommunicationSource.getCommunicator(point);
		def ret = systemCaller.doworkAsString(address, params, CommunicationMethod.POST);
		return ret;
	}

	public static <T> T connect(Class<T> mapclass, def point, def address, def param_dic) {
		List<CommunicationParam> params = new ArrayList<CommunicationParam>();
		if(param_dic) {
			for(kv in param_dic) {
				CommunicationParam param = new CommunicationParam(kv.key, kv.value);
				params.add(param);
			}
		}
		CommunicationPower systemCaller = CommunicationSource.getCommunicator(point);
		def ret = systemCaller.doworkAsString(address, params);
		
		T obj = JackSonBeanMapper.fromJson(ret, mapclass);
		return obj;
	}
	
	public static <T> T connectAsArray(Class<T> mapclass, def point, def address, def param_dic) {
		List<CommunicationParam> params = new ArrayList<CommunicationParam>();
		if(param_dic) {
			for(kv in param_dic) {
				CommunicationParam param = new CommunicationParam(kv.key, kv.value);
				params.add(param);
			}
		}
		CommunicationPower systemCaller = CommunicationSource.getCommunicator(point);
		def ret = systemCaller.doworkAsString(address, params);
		
		T[] obj = JackSonBeanMapper.jsonToList(ret, mapclass);
		return obj;
	}
	public static <T> List<T>  connectList(Class<T> mapclass, def point, def address) {
		List<CommunicationParam> params = new ArrayList<CommunicationParam>();
		CommunicationPower systemCaller = CommunicationSource.getCommunicator(point);
		def ret = systemCaller.doworkAsString(address, params);
		List<T> obj = JackSonBeanMapper.jsonToList(ret, mapclass)
		return obj;
	}
	
}
