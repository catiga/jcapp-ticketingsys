package com.jeancoder.ticketingsys.internal.api

import java.text.SimpleDateFormat

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.entity.SaleOrder
import com.jeancoder.ticketingsys.ready.plan.dto.HpPlanSeatsDto
import com.jeancoder.ticketingsys.ready.plan.dto.PlanSeat
import com.jeancoder.ticketingsys.ready.ssc.SssHelper
import com.jeancoder.ticketingsys.ready.store.StoreService
import com.jeancoder.ticketingsys.ready.store.dto.CinemaAuthInfo
import com.jeancoder.ticketingsys.ready.store.dto.StoreInfo
import com.jeancoder.ticketingsys.ready.support.Codes
import com.jeancoder.ticketingsys.ready.support.Res
import com.jeancoder.ticketingsys.ready.util.JackSonBeanMapper
import com.piaodaren.ssc.model.CinemaPlan
import com.piaodaren.ssc.model.CinemaPlanResult
import com.piaodaren.ssc.model.HallSeat
import com.piaodaren.ssc.model.HallSeatResult
import com.piaodaren.ssc.model.PlanSeatState
import com.piaodaren.ssc.model.PlanSeatStateResult

JCLogger logger = JCLoggerFactory.getLogger(this.class);

try {
	Long id = Long.valueOf(JC.internal.param("cinema_id"));
	String plan_id = JC.internal.param("plan_id");
	String last_update_time = JC.internal.param("last_update_time");
	String hall_id = JC.internal.param("hall_id");
	String plan_date = JC.internal.param("plan_date");
	
	def ap_id = JC.internal.param('ap_id');
	def pid = JC.internal.param('pid');
	
	CinemaAuthInfo cinemaAuthInfo = StoreService.INSTANCE.getCinemaAuthInfo(id);
	
	if(cinemaAuthInfo == null) {
		return Res.Failed(Codes.COMMON_CINEMA_CONFIG_ERROR);
	}
	StoreInfo storeInfo = StoreService.INSTANCE.getById(id);
	
	def start = System.currentTimeMillis()
	HallSeatResult hall = SssHelper.INSTANCE.getHallSeats(cinemaAuthInfo, hall_id);
	def end_1 = System.currentTimeMillis();
	if((end_1-start)/1000>1.5) {
		logger.info('seats::: CINEMA_ID=' + id + ' HALL_ID=' + hall_id + ' hall_full_seats_exhausted_time=' + (end_1 - start)/1000);
	}
	
	PlanSeatStateResult remoteResult = SssHelper.INSTANCE.getSeatsSeats(cinemaAuthInfo, plan_id, last_update_time, hall);
	def end_2 = System.currentTimeMillis();
	
//	if(storeInfo.id.toString()=='13') {
//		logger.info('千幕影城=' + JackSonBeanMapper.toJson(remoteResult))
//	}
	
	
	if((end_2-end_1)/1000>1.5) {
		logger.info('seats::: CINEMA_ID=' + id + ' HALL_ID=' + hall_id + ' hall_seats_status_exhausted_time=' + (end_2 - end_1)/1000);
	}
	
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
	
	if(!"0".equals(remoteResult.getCode())) {
		if(remoteResult.getRmCode()!=null&&remoteResult.getRmCode().startsWith("300505")) {
			//鼎新场次过期，需要删除缓存
			try {
				SssHelper.INSTANCE.remove_cached_plans(cinemaAuthInfo, start_time, end_time);
			} catch(any) {
				logger.error("clear cached plans fail:", any)
			}
		}
		return Res.Failed(Codes.CINEMA_CONN_FAILED,remoteResult.getMsg());
	}
	
	int leftSeat = 0;
	int soldSeat = 0;
	
	for(PlanSeatState seat : remoteResult.getResult()) {
		//转换成我们协议的状态
		if(storeInfo.id.toString()=='13') {
			logger.info(seat.getSeatState().equals("0") + '===' + seat.getSeatState())
		}
		if(seat.getSeatState().equals("0")) {
			seat.setSeatState("4");	//可用
		}else if(seat.getSeatState().equals("7")) {
			seat.setSeatState("2");	//锁定
		}else {
			seat.setSeatState("0");	//?
		}
		
		if(storeInfo.id.toString()=='13') {
			logger.info("so===" + seat.getSeatState());
		}
	}
	List<PlanSeat> seats = new ArrayList<PlanSeat>()
	for(HallSeat graphSeat : hall.getResult()) {
		PlanSeatState matchSeat = null;
		loopgraph:
		for(PlanSeatState seat : remoteResult.getResult()) {
			//if(seat.getGraphRow().equals(""+graphSeat.getxCoord()) && seat.getGraphCol().equals(""+graphSeat.getyCoord())) {
			if(seat.seatNo==graphSeat.cineSeatId) {
				//同一个座位
				matchSeat = seat;
				break loopgraph;
			}
		}
		
		PlanSeat resseat = new PlanSeat();
		def seat_id_encode = URLEncoder.encode(graphSeat.getCineSeatId(), 'UTF-8');
		resseat.setCineSeatId(seat_id_encode)
		resseat.setCinemaId(graphSeat.getCinemaId())
		resseat.setxCoord(graphSeat.getxCoord())
		resseat.setyCoord(graphSeat.getyCoord())
		resseat.setLoveseats(graphSeat.getLoveseats())
		resseat.setRow(graphSeat.getRow())
		resseat.setColumn(graphSeat.getColumn())
		resseat.setStatus(graphSeat.getStatus())
		resseat.setType(graphSeat.getType())
		resseat.setArea_no(graphSeat.getArea_no())
		if(graphSeat.getStatus()=='ok') {
			resseat.setSeatState("0");
		} else {
			resseat.setSeatState("5");	//非座位
		}
		if(matchSeat != null) {
			resseat.setSeatNo(matchSeat.getSeatNo())
			resseat.setSeatPieceNo(matchSeat.getSeatPieceNo())
			resseat.setGraphRow(matchSeat.getGraphRow())
			resseat.setGraphCol(matchSeat.getGraphCol())
			resseat.setSeatRow(matchSeat.getSeatRow())
			resseat.setSeatCol(matchSeat.getSeatCol())
			resseat.setSeatState("road".equalsIgnoreCase(graphSeat.getType()) ? "5" : matchSeat.getSeatState());
		}
		if(!matchSeat) {
			//再做一遍协议转换 ？	//目前是凤凰云志会有这种情况，因为只返回了不可选的座位列表
			if(graphSeat.getStatus()=='ok' && !'road'.equalsIgnoreCase(graphSeat.type)) {
				resseat.setSeatState("4");
			} else {
				resseat.setSeatState("5");	//非座位
			}
		}
		if(!"road".equalsIgnoreCase(graphSeat.getType())&&matchSeat) {
			if("4".equals(matchSeat.getSeatState())) {
				leftSeat++;
			}else {
				soldSeat++;
			}
		}
		seats.add(resseat);
	}
	
	Integer big_col = 1, big_row = 1;
	Integer all_row = 1;
	for(int i = 0; i < seats.size(); i++) {
		if(!seats.get(i).graphCol) {
			seats.get(i).graphCol = seats.get(i).yCoord;
		}
		if(!seats.get(i).graphRow) {
			seats.get(i).graphRow = seats.get(i).xCoord;
		}
		if(!seats.get(i).seatRow) {
			seats.get(i).seatRow = seats.get(i).row;
		}
		if(!seats.get(i).seatCol) {
			seats.get(i).seatCol = seats.get(i).column;
		}
		int row = Integer.valueOf(seats.get(i).getGraphRow());
		int col = Integer.valueOf(seats.get(i).getGraphCol());
		if(row>big_row) {
			big_row = row;
		}
		if(col>big_col) {
			big_col = col;
		}
		if(Integer.valueOf(seats.get(i).getGraphRow())>all_row) {
			all_row = Integer.valueOf(seats.get(i).getGraphRow());
		}
	}
	
	HpPlanSeatsDto data = new HpPlanSeatsDto();
	data.setAll_row(all_row);
	data.setBig_col(big_col);
	data.setBig_row(big_row);
	data.setLeft_seat_num(leftSeat)
	data.setSold_seat_num(soldSeat)
	
	Map<String,ArrayList<PlanSeat>> rows = new LinkedHashMap<String,ArrayList<PlanSeat>>();
	for(PlanSeat seat : seats){
		String row_num = ""+seat.getGraphRow()
		if(rows.get(row_num) == null){
			rows.put(row_num, new ArrayList<HpPlanSeatsDto>());
		}
		/*
		//检查座位是否有预约订单
		SeatOrderDto curord = OrderService.INSTANCE.getSeatCurrentOrder(plan_id, seat.getCineSeatId());
		if(curord != null) {
			seat.setSeatState("6");
			seat.setReserveOrder(curord);
		}
		
		//检查座位是否有锁座订单
		SeatOrderDto lockord = OrderService.INSTANCE.getSeatLockedOrder(plan_id, seat.getCineSeatId());
		if(lockord != null) {
			seat.setSeatState("2");
			seat.setLockOrder(lockord)
		}
		
		//检查座位是否有已购订单
		SeatOrderDto buyord = OrderService.INSTANCE.getSeatBuyedOrder(plan_id, seat.getCineSeatId());
		if(buyord != null) {
			seat.setSeatState("8");
			seat.setBuyOrder(buyord);
		}
		//检查座位是否有已购订单
		SeatOrderDto prelock = OrderService.INSTANCE.getSeatPrelockOrder(plan_id, seat.getCineSeatId());
		if(prelock != null) {
			seat.setSeatState("11");
			seat.setPrelockOrder(prelock)
		}
		*/
		rows.get(row_num).add(seat);
	}
	
	List<Map<String,Object>> all_rows = new ArrayList<Map<String,Object>>();
	int j=1;
	for(String key : rows.keySet()){
		Map<String,Object> seatObj = new HashMap<String,Object>();
		String row_num = key;
		List<PlanSeat> rowseats = rows.get(key);
		int i = 0;
		for(PlanSeat asa : rowseats){
			if("5".equals(asa.getSeatState())){
				i++;
			}
		}
		if(i==rowseats.size()){
			row_num="";
		} else{
			row_num=String.valueOf(j);
			j++;
		}
		seatObj.put("row", row_num);
		seatObj.put("seats", rowseats);
		all_rows.add(seatObj);
	}
	data.setRows(all_rows);
	
	//开始根据apid查找用户是否有订单
	SaleOrder recent_order = null;
	if(ap_id) {
		recent_order = JcTemplate.INSTANCE().get(SaleOrder, 'select * from SaleOrder where flag!=? and user_id=? and order_status=? and store_id=? and plan_id=? order by a_time desc', -1, ap_id, '0000', id, plan_id);
		if(recent_order) {
			//判断时间
			def time_diff = System.currentTimeMillis() - recent_order.a_time.getTime();
			if(time_diff>=15*60*1000l) {
				recent_order = null;
			}
		}
	}
	
	def responseData = [:];
	responseData.put("store", storeInfo)
	responseData.put("plan", matchPlan)
	responseData.put("seat", data)
	responseData.put('recent_order', recent_order);
	
	return Res.Success(responseData);
}catch(Exception e) {
	logger.error("查询座位失败",e);
	return Res.Failed(Codes.INTERNAL_SERVER_ERROR);
}

