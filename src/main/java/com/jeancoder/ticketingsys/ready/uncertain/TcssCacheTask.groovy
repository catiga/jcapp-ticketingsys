package com.jeancoder.ticketingsys.ready.uncertain

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.entity.Cinema
import com.jeancoder.ticketingsys.ready.ssc.SssHelper
import com.jeancoder.ticketingsys.ready.store.StoreService
import com.jeancoder.ticketingsys.ready.store.dto.CinemaAuthInfo
import com.jeancoder.ticketingsys.ready.util.JackSonBeanMapper
import com.piaodaren.ssc.model.CinemaPlanResult

class TcssCacheTask {

	static JCLogger Logger = LoggerSource.getLogger(TcssCacheTask.class.getName());
	
	def static sync_data() {
		Logger.info('start tcss cache task');
		//获取影城
		List<Cinema> cinemas = JcTemplate.INSTANCE().find(Cinema, 'select * from Cinema where id!=?', -1);
		if(cinemas && !cinemas.empty) {
			for(cinema in cinemas) {
				def pid = cinema.proj_id;
				CinemaAuthInfo cinemaAuthInfo = StoreService.INSTANCE.getCinemaAuthInfo(cinema.id.longValue());
			
				if(cinemaAuthInfo == null) {
					Logger.info('cinema_id=' + cinema.id +', cinema_name=' + cinema.store_name + ' does not set tcss system, does not sync_data');
					continue;
				}
				
				Calendar now = Calendar.getInstance(TimeZone.getDefault());
				now.setTime(new Date());
			
				now.set(Calendar.HOUR,0);
				now.set(Calendar.MINUTE,0);
				now.set(Calendar.SECOND,0);
				now.set(Calendar.MILLISECOND,0);
			
				Date start_time = now.getTime()
			
				now.add(Calendar.DATE, getData(cinema.config_id.longValue()));
				Date end_time = now.getTime()
				//用异步任务控制超时时间来同步影讯，设置请求超时时间为10s
				JC.thread.run(30*1000L, {
					try {
						CinemaPlanResult planResult = SssHelper.INSTANCE.get_cinema_plans(cinemaAuthInfo, start_time, end_time);
						return planResult;
					}catch(any) {
						return null;
					}
				}, {
					e->
					if(e.success) {
						if(e.data && "0".equals(e.data.getCode()) && e.data.result) {
							//这个是正常的同步了场次
						} else {
							Logger.error(JackSonBeanMapper.toJson(e));
							Logger.error('cinema_id=' + cinema.id +', cinema_name=' + cinema.store_name + ' sync_data_failed_2.');
						}
					} else {
						if(e.code == '10001') {
							//超时任务
							Logger.error('cinema_id=' + cinema.id +', cinema_name=' + cinema.store_name + ' sync_data_TIMEOUT.');
						} else {
							//其他错误
							Logger.error('cinema_id=' + cinema.id +', cinema_name=' + cinema.store_name + ' sync_data_failed...' + JackSonBeanMapper.toJson(e));
						}
					}
				});
			}
		}
	}
	
	public static Integer getData(def c_id) {
		try {
			// 满天星接口 只能查5的排期 但是只返回2天
			def cids = [20,21,22,23]
			for (def c : cids) {
				if (c.toString().equals(c_id.toString())) {
					return 2;
				}
			}
		} catch (any) {
			LoggerSource.getLogger(this.getClass().getName()).error("getData__",any)
		}
		return 60;	//默认返回60天
		
	}
}
