package com.jeancoder.ticketingsys.ready.ssc

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.app.sdk.source.MemSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.ticketingsys.ready.film.FilmHelper
import com.jeancoder.ticketingsys.ready.store.dto.CinemaAuthInfo
import com.jeancoder.ticketingsys.ready.support.TicketingSysTypeHelper
import com.jeancoder.ticketingsys.ready.util.JackSonBeanMapper
import com.milepai.core.ssc.TcSsConstants
import com.piaodaren.ssc.factory.SscOp
import com.piaodaren.ssc.model.CinemaPlanResult
import com.piaodaren.ssc.model.HallSeatResult
import com.piaodaren.ssc.model.PlanSeatStateResult
import com.piaodaren.ssc.model.SscAuthModel

class SssHelper{
	public static final SssHelper INSTANCE = new SssHelper();
	
	private String plan_pre = "hp_plan_key@";
	private String hall_seat_pre = "hp_seat_key@";
	private String seat_stats_pre = "hp_stats_key@";
	//private Long commonExp = 1000L*60L*30L;
	private Long commonExp = 5*60*60L;		//设置为5个小时缓存时间
	
	private Long hall_general_seats_time = 30*24*60*60L;	//设置为30天过期
	
	JCLogger  logger = LoggerSource.getLogger();
	
	/**
	 * 直接取场次，不走缓存，为锁座使用
	 * @param cinemaAuthInfo
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public CinemaPlanResult get_cinema_plans_without_cache(CinemaAuthInfo cinemaAuthInfo,Date startDate,Date endDate) {
		String tc_ss_type = cinemaAuthInfo.getSystemSsCode()
		String channel_url = cinemaAuthInfo.getAuthChannelUrl()
		String channel_num = cinemaAuthInfo.getAuthChannelNo()
		String channel_code = cinemaAuthInfo.getAuthChannelCode()
		SscAuthModel auth_model = TicketingsysFactory.direct_generateAuthModel(TicketingSysTypeHelper.transToOldCode(tc_ss_type) , channel_url, channel_num, channel_code);
		
		SscOp ssc_op = TicketingsysFactory.generateSscOp(auth_model);
		
		CinemaPlanResult planResult = ssc_op.get_cinema_plans(cinemaAuthInfo.getCinemaCode(), startDate, endDate);
		
		if("0".equals(planResult.getCode())) {
			FilmHelper.INSTANCE.syncFilmPoster(planResult.getResult());
		}
		if(TicketingSysTypeHelper.transToOldCode(tc_ss_type)==TcSsConstants._tc_ss_oristar) {
			//晨星系统特殊处理
			def halls = ssc_op.get_cinema_hall_list(cinemaAuthInfo.getCinemaCode());
			for(x in planResult.getResult()) {
				for(y in halls.getResult()) {
					if(x.hallId==y.id) {
						x.hallName = y.name;
					}
				}
			}
		}//
		
		return planResult;
	}
	
	public boolean remove_cached_plans(CinemaAuthInfo cinemaAuthInfo,Date startDate,Date endDate) {
		String cacheKey = plan_pre+cinemaAuthInfo.getCinemaCode()+startDate.getTime()+endDate.getTime();
		MemSource.getMemPower().delete(cacheKey);
		return true;
	}
	
	/**
	 * TODO 可以考虑加缓存
	 * 获取影城全部排期
	 * 自动爬取影讯
	 * @param cinemaAuthInfo
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public CinemaPlanResult get_cinema_plans(CinemaAuthInfo cinemaAuthInfo,Date startDate,Date endDate) {
		//cache option
		String cacheKey = plan_pre+cinemaAuthInfo.getCinemaCode()+startDate.getTime()+endDate.getTime();
		def cacheObj = MemSource.getMemPower().getAsString(cacheKey);
		if(cacheObj != null) {
			cacheObj = JackSonBeanMapper.fromJson(cacheObj, CinemaPlanResult);
			return cacheObj;
		}
		//cache option
		String tc_ss_type = cinemaAuthInfo.getSystemSsCode()
		String channel_url = cinemaAuthInfo.getAuthChannelUrl()
		String channel_num = cinemaAuthInfo.getAuthChannelNo()
		String channel_code = cinemaAuthInfo.getAuthChannelCode()
		SscAuthModel auth_model = TicketingsysFactory.direct_generateAuthModel(TicketingSysTypeHelper.transToOldCode(tc_ss_type) , channel_url, channel_num, channel_code);
		
		SscOp ssc_op = TicketingsysFactory.generateSscOp(auth_model);
		
		def start = System.currentTimeMillis();
		CinemaPlanResult planResult = ssc_op.get_cinema_plans(cinemaAuthInfo.getCinemaCode(), startDate, endDate);
		def end = System.currentTimeMillis();
		if("0".equals(planResult.getCode())) {
			//同步影讯信息
			FilmHelper.INSTANCE.syncFilmPoster(planResult.getResult(),cinemaAuthInfo.pid);
		}
		if(TicketingSysTypeHelper.transToOldCode(tc_ss_type)==TcSsConstants._tc_ss_oristar) {
			//晨星系统特殊处理
			def halls = ssc_op.get_cinema_hall_list(cinemaAuthInfo.getCinemaCode());
			for(x in planResult.getResult()) {
				for(y in halls.getResult()) {
					if(x.hallId==y.id) {
						x.hallName = y.name;
					}
				}
			}
		}//
		
		if("0".equals(planResult.getCode()) && planResult.result) {
			//返回正常情况下才加入缓存
			//cache option
			def add_result = MemSource.getMemPower().setUntilAsString(cacheKey,JackSonBeanMapper.toJson(planResult) , commonExp);
			//cache option
		}
		
		return planResult;
	}
	
	public HallSeatResult getHallSeats(CinemaAuthInfo cinemaAuthInfo,String hall_id) {
		//cache option
		String cacheKey = hall_seat_pre+cinemaAuthInfo.getCinemaCode()+hall_id;
		def cacheObj = MemSource.getMemPower().getAsString(cacheKey);
		if(cacheObj != null) {
			cacheObj = JackSonBeanMapper.fromJson(cacheObj, HallSeatResult);
			return cacheObj;
		}
		//cache option
		
		String tc_ss_type = cinemaAuthInfo.getSystemSsCode()
		String channel_url = cinemaAuthInfo.getAuthChannelUrl()
		String channel_num = cinemaAuthInfo.getAuthChannelNo()
		String channel_code = cinemaAuthInfo.getAuthChannelCode()
		SscAuthModel auth_model = TicketingsysFactory.direct_generateAuthModel(TicketingSysTypeHelper.transToOldCode(tc_ss_type) , channel_url, channel_num, channel_code);
		
		SscOp ssc_op = TicketingsysFactory.generateSscOp(auth_model);
		
		HallSeatResult hall = ssc_op.get_hall_seat_list(cinemaAuthInfo.getCinemaCode(), hall_id);
		
		if("0".equals(hall.getCode())) {
			//返回正确数据情况下才缓存
			//cache option
			MemSource.getMemPower().setUntil(cacheKey, JackSonBeanMapper.toJson(hall), hall_general_seats_time);
			//cache option
		}
		
		return hall;
	}
	
	public PlanSeatStateResult getSeatsSeats(CinemaAuthInfo cinemaAuthInfo,String plan_id,String last_update_time, HallSeatResult hall_seats = null) {
		//座位状态不用缓存
		String tc_ss_type = cinemaAuthInfo.getSystemSsCode()
		String channel_url = cinemaAuthInfo.getAuthChannelUrl()
		String channel_num = cinemaAuthInfo.getAuthChannelNo()
		String channel_code = cinemaAuthInfo.getAuthChannelCode()
		SscAuthModel auth_model = TicketingsysFactory.direct_generateAuthModel(TicketingSysTypeHelper.transToOldCode(tc_ss_type) , channel_url, channel_num, channel_code);
		
		SscOp ssc_op = TicketingsysFactory.generateSscOp(auth_model);
		String section_ids = null;
		if(hall_seats && hall_seats.areas) {
			StringBuffer buf = new StringBuffer();
			for(x in hall_seats.areas) {
				buf.append(x.areaNo + ',');
			}
			section_ids = buf.substring(0, buf.length() - 1);
		}
		
		PlanSeatStateResult remoteResult = ssc_op.get_remote_seats_status(cinemaAuthInfo.getCinemaCode(), plan_id, last_update_time,section_ids);
		
		return remoteResult;
	}
}