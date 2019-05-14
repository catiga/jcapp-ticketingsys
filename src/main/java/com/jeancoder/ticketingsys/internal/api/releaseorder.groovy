package com.jeancoder.ticketingsys.internal.api
import java.sql.Timestamp
import java.text.SimpleDateFormat

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.DatabaseSource
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.entity.SaleCode
import com.jeancoder.ticketingsys.ready.entity.SaleOrder
import com.jeancoder.ticketingsys.ready.entity.SaleSeat
import com.jeancoder.ticketingsys.ready.order.constants.OrderConstants
import com.jeancoder.ticketingsys.ready.ssc.TicketingsysFactory
import com.jeancoder.ticketingsys.ready.ssc.SssHelper
import com.jeancoder.ticketingsys.ready.store.StoreService
import com.jeancoder.ticketingsys.ready.store.dto.CinemaAuthInfo
import com.jeancoder.ticketingsys.ready.store.dto.StoreInfo
import com.jeancoder.ticketingsys.ready.support.Codes
import com.jeancoder.ticketingsys.ready.support.Res
import com.jeancoder.ticketingsys.ready.support.TicketingSysTypeHelper
import com.piaodaren.ssc.factory.SscOp
import com.piaodaren.ssc.model.CinemaPlan
import com.piaodaren.ssc.model.CinemaPlanResult
import com.piaodaren.ssc.model.SscAuthModel


//根据 apid 查找一下用户信息
def pid = JC.internal.param('pid');
def apid = JC.internal.param('ap_id');
def order_no = JC.internal.param('order_no');
JCLogger logger = LoggerSource.getLogger(this.class);
try {
	
	BigInteger user_id = BigInteger.valueOf(Long.valueOf(apid));
	
	SaleOrder order_info = JcTemplate.INSTANCE().get(SaleOrder, 'select * from SaleOrder where user_id=? and order_no=? and order_status=?', user_id, order_no, '0000');
	if(!order_info) {
		//直接返回
		return Res.Failed(Codes.CINEMA_GENORDER_ORDERNO_NOTFOUND,"订单不存在");
	}
	if((System.currentTimeMillis() - order_info.a_time.getTime()) >= 15*60*1000l) {
		logger.info("releaseorder order_no=" + order_info.order_no);
		order_info.order_status = OrderConstants._order_status_remote_unlock_by_front_;
		order_info.c_time = new Timestamp(new Date().getTime());
		JcTemplate.INSTANCE().update(order_info);
		return Res.Success();
	}
	
	def id = order_info.store_id.longValue();
	StoreInfo store = StoreService.INSTANCE.getById(id);
	CinemaAuthInfo cinemaAuthInfo = StoreService.INSTANCE.getCinemaAuthInfo(id);

	if(cinemaAuthInfo == null) {
		DatabaseSource.getDatabasePower().rollbackTransaction();
		return Res.Failed(Codes.COMMON_CINEMA_CONFIG_ERROR);
	}
	def plan_date = order_info.plan_date;
	def plan_id = order_info.plan_id;
	
	String tc_ss_type = cinemaAuthInfo.getSystemSsCode()
	String channel_url = cinemaAuthInfo.getAuthChannelUrl()
	String channel_num = cinemaAuthInfo.getAuthChannelNo()
	String channel_code = cinemaAuthInfo.getAuthChannelCode()
	SscAuthModel auth_model = TicketingsysFactory.direct_generateAuthModel(TicketingSysTypeHelper.transToOldCode(tc_ss_type) , channel_url, channel_num, channel_code);

	SimpleDateFormat datesdf = new SimpleDateFormat("yyyy-MM-dd");
	Date start_time = datesdf.parse(plan_date);
	Calendar c = Calendar.getInstance(TimeZone.getDefault());
	c.setTime(start_time);
	c.add(Calendar.DATE, 1);
	Date end_time = c.getTime();
	CinemaPlanResult planResult = SssHelper.INSTANCE.get_cinema_plans(cinemaAuthInfo, start_time, end_time);
	CinemaPlan matchPlan = null;
	for(CinemaPlan cplan : planResult.getResult()) {
		if(cplan.getId().equals(plan_id)) {
			matchPlan = cplan;
		}
	}

	if(matchPlan == null) {
		return Res.Failed(Codes.COMMON_PARAM_ERROR,"所选日期不存在该排期");
	}
	
	//开始获取订单相关座位信息
	List<SaleSeat> order_seats = JcTemplate.INSTANCE().find(SaleSeat, 'select * from SaleSeat where order_id=?', order_info.id);
	SaleCode order_remotes = JcTemplate.INSTANCE().get(SaleCode, 'select * from SaleCode where order_id=?', order_info.id);
	if(!order_seats||!order_remotes) {
		return Res.Failed(Codes.CINEMA_GENORDER_ORDERNO_NOTFOUND,"订单不存在对应的座位与锁座信息");
	}
	def dx_lock_flag = order_remotes.lock_flag;
	def seat_nos = [];
	order_seats.each({
		seat_nos += it.seat_no;
	});
	SscOp ssc_op = TicketingsysFactory.generateSscOp(auth_model);
	String remoteResult = ssc_op.batch_unlock(cinemaAuthInfo.getCinemaCode(), plan_id, dx_lock_flag, seat_nos, order_info.order_no);
	logger.info(order_no + '_unlock_result=' + remoteResult + ", and remote lock flag=" + dx_lock_flag);
	if(remoteResult=='true') {
		logger.info("releaseorder order_no=" + order_info.order_no);
		order_info.c_time = new Timestamp(new Date().getTime());
		order_info.order_status = OrderConstants._order_status_remote_unlock_by_front_;
		JcTemplate.INSTANCE().update(order_info);
		return Res.Success();
	} else {
		return Res.Success(dx_lock_flag + ":" + remoteResult);
	}
}catch(Exception e) {
	e.printStackTrace()
	return Res.Failed(Codes.INTERNAL_SERVER_ERROR);
}