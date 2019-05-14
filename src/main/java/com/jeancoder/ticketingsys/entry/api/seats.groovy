package com.jeancoder.ticketingsys.entry.api
import java.text.SimpleDateFormat

import com.jeancoder.app.sdk.source.RequestSource
import com.jeancoder.app.sdk.source.ResponseSource
import com.jeancoder.core.http.JCRequest
import com.jeancoder.core.http.JCResponse
import com.jeancoder.core.result.Result
import com.jeancoder.ticketingsys.ready.order.OrderService
import com.jeancoder.ticketingsys.ready.order.dto.SeatOrderDto
import com.jeancoder.ticketingsys.ready.plan.dto.HpPlanSeatsDto
import com.jeancoder.ticketingsys.ready.plan.dto.PlanSeat
import com.jeancoder.ticketingsys.ready.ssc.SssHelper
import com.jeancoder.ticketingsys.ready.store.StoreService
import com.jeancoder.ticketingsys.ready.store.dto.CinemaAuthInfo
import com.jeancoder.ticketingsys.ready.store.dto.StoreInfo
import com.jeancoder.ticketingsys.ready.support.Codes
import com.jeancoder.ticketingsys.ready.support.Res
import com.piaodaren.ssc.model.CinemaPlan
import com.piaodaren.ssc.model.CinemaPlanResult
import com.piaodaren.ssc.model.HallSeat
import com.piaodaren.ssc.model.HallSeatResult
import com.piaodaren.ssc.model.PlanSeatState
import com.piaodaren.ssc.model.PlanSeatStateResult

JCRequest req = RequestSource.getRequest();
JCResponse res = ResponseSource.getResponse();
Result result = new Result();

try {
	Long id = req.getLong("cinema_id");
	String plan_id = req.getParameter("plan_id");
	String last_update_time = req.getParameter("last_update_time");
	String hall_id = req.getParameter("hall_id");
	String plan_date = req.getParameter("plan_date");
	
	CinemaAuthInfo cinemaAuthInfo = StoreService.INSTANCE.getCinemaAuthInfo(id);
	
	if(cinemaAuthInfo == null) {
		result.setData(Res.Failed(Codes.COMMON_CINEMA_CONFIG_ERROR));
		return result;
	}
	
	StoreInfo storeInfo = StoreService.INSTANCE.getById(id);
	HallSeatResult hall = SssHelper.INSTANCE.getHallSeats(cinemaAuthInfo, hall_id);
	PlanSeatStateResult remoteResult = SssHelper.INSTANCE.getSeatsSeats(cinemaAuthInfo, plan_id, last_update_time);
	
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
		result.setData(Res.Failed(Codes.COMMON_PARAM_ERROR,"所选日期不存在该排期"));
		return result;
	}
	
	if(!"0".equals(remoteResult.getCode())) {
		result.setData(Res.Failed(Codes.CINEMA_CONN_FAILED,remoteResult.getMsg()));
		return result;
	}
	
	int leftSeat = 0;
	int soldSeat = 0;
	for(PlanSeatState seat : remoteResult.getResult()) {
		//转换成我们协议的状态
		if(seat.getSeatState().equals("0")) {
			seat.setSeatState("4");
		}else if(seat.getSeatState().equals("7")) {
			seat.setSeatState("2");
		}else {
			seat.setSeatState("0");
		}
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
			resseat.setSeatNo(matchSeat.getSeatNo())
			resseat.setSeatPieceNo(matchSeat.getSeatPieceNo())
			resseat.setGraphRow(matchSeat.getGraphRow())
			resseat.setGraphCol(matchSeat.getGraphCol())
			resseat.setSeatRow(matchSeat.getSeatRow())
			resseat.setSeatCol(matchSeat.getSeatCol())
			resseat.setSeatState("road".equalsIgnoreCase(graphSeat.getType()) ? "5" : matchSeat.getSeatState());
		}
		
		if(!"road".equalsIgnoreCase(graphSeat.getType())) {
			if("0".equals(matchSeat.getSeatState())) {
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
	
	Map<String,Object> responseData = new HashMap<String,Object>();
	responseData.put("store", storeInfo)
	responseData.put("plan", matchPlan)
	responseData.put("seat", data)
	result.setData(Res.Success(responseData));
	return result;
}catch(Exception e) {
	e.printStackTrace()
	result.setData(Res.Failed(Codes.INTERNAL_SERVER_ERROR));
	return result;
}