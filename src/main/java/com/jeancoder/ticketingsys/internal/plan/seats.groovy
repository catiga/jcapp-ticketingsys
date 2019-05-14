package com.jeancoder.ticketingsys.internal.plan
import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.ticketingsys.ready.order.OrderService
import com.jeancoder.ticketingsys.ready.order.dto.SeatOrderDto
import com.jeancoder.ticketingsys.ready.plan.dto.HpPlanSeatsDto
import com.jeancoder.ticketingsys.ready.plan.dto.PlanSeat
import com.jeancoder.ticketingsys.ready.ssc.SssHelper
import com.jeancoder.ticketingsys.ready.store.StoreService
import com.jeancoder.ticketingsys.ready.store.dto.CinemaAuthInfo
import com.jeancoder.ticketingsys.ready.support.Codes
import com.jeancoder.ticketingsys.ready.support.Res
import com.jeancoder.ticketingsys.ready.util.JackSonBeanMapper
import com.piaodaren.ssc.model.HallSeat
import com.piaodaren.ssc.model.HallSeatResult
import com.piaodaren.ssc.model.PlanSeatState
import com.piaodaren.ssc.model.PlanSeatStateResult

JCLogger logger = LoggerSource.getLogger(this.class);
try {
	Long id = Long.valueOf(JC.internal.param("cinema_id"));
	String plan_id = JC.internal.param("plan_id");
	String last_update_time = JC.internal.param("last_update_time");
	String hall_id = JC.internal.param("hall_id");
	
	CinemaAuthInfo cinemaAuthInfo = StoreService.INSTANCE.getCinemaAuthInfo(id);
	if(cinemaAuthInfo == null) {
		return Res.Failed(Codes.COMMON_CINEMA_CONFIG_ERROR);
	}
	
	
	HallSeatResult hall = SssHelper.INSTANCE.getHallSeats(cinemaAuthInfo, hall_id);
	PlanSeatStateResult remoteResult = SssHelper.INSTANCE.getSeatsSeats(cinemaAuthInfo, plan_id, last_update_time);
	if(!"0".equals(remoteResult.getCode())) {
		return Res.Failed(Codes.CINEMA_CONN_FAILED,remoteResult.getMsg());
	}
	
	int leftSeat = 0;
	int soldSeat = 0;
	
	/**
	 * 0:可用
	 * 3:已售
	 * 7:锁定
	 * 5:其他
	 */
	//logger.info("remoteResult____" + JackSonBeanMapper.toJson(remoteResult));
	for(PlanSeatState seat : remoteResult.getResult()) {
		String os = seat.getSeatState();
		//转换成我们协议的状态
		if(seat.getSeatState().equals("0")) {
			//设为可用状态
			seat.setSeatState("4");
		}else if(seat.getSeatState().equals("7")) {
			//设为锁定状态
			seat.setSeatState("2");
		}else {
			//其他不可用
			seat.setSeatState("0");
		}
		//println("original_state=" + os + "======" + seat.getSeatState());
	}
	
	List<PlanSeat> seats = new ArrayList<PlanSeat>()
	for(HallSeat graphSeat : hall.getResult()) {
		PlanSeatState matchSeat = null;
		loopgraph:
		for(PlanSeatState seat : remoteResult.getResult()) {
			if(seat.getGraphRow().equals(""+graphSeat.getxCoord()) && seat.getGraphCol().equals(""+graphSeat.getyCoord())) {
				//同一个座位
				matchSeat = seat;
				break loopgraph;
			}
		}
		
		PlanSeat resseat = new PlanSeat();
		resseat.setCineSeatId(graphSeat.getCineSeatId())
		resseat.setCinemaId(graphSeat.getCinemaId())
		resseat.setxCoord(graphSeat.getxCoord())
		resseat.setyCoord(graphSeat.getyCoord())
		resseat.setLoveseats(graphSeat.getLoveseats())
		resseat.setRow(graphSeat.getRow())
		resseat.setColumn(graphSeat.getColumn())
		resseat.setStatus(graphSeat.getStatus())
		resseat.setType(graphSeat.getType())
		resseat.setArea_no(graphSeat.getArea_no())
		if(matchSeat != null) {
			resseat.setLoveseats(graphSeat.getLoveseats())
			resseat.setSeatNo(matchSeat.getSeatNo())
			resseat.setSeatPieceNo(matchSeat.getSeatPieceNo())
			resseat.setGraphRow(matchSeat.getGraphRow())
			resseat.setGraphCol(matchSeat.getGraphCol())
			resseat.setSeatRow(matchSeat.getSeatRow())
			resseat.setSeatCol(matchSeat.getSeatCol())
			resseat.setSeatState("road".equalsIgnoreCase(graphSeat.getType()) ? "5" : matchSeat.getSeatState());
		}
		
		if(!"road".equalsIgnoreCase(graphSeat.getType())) {
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
	return Res.Success(data);
}catch(Exception e) {
	logger.error("查询座位失败",e);
	return Res.Failed(Codes.INTERNAL_SERVER_ERROR);
}