package com.jeancoder.ticketingsys.ready.ssc

import java.util.List

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.ticketingsys.ready.common.SimpleAjax
import com.jeancoder.ticketingsys.ready.dto.store.StoreGeneral
import com.jeancoder.ticketingsys.ready.entity.Movie
import com.jeancoder.ticketingsys.ready.util.JackSonBeanMapper
import com.jeancoder.ticketingsys.ready.util.NativeUtil
import com.piaodaren.ssc.constants.SscConstants
import com.piaodaren.ssc.factory.AbstractSscOp
import com.piaodaren.ssc.factory.SeatPub
import com.piaodaren.ssc.factory.SscOp
import com.piaodaren.ssc.model.AppPrintTicketResult
import com.piaodaren.ssc.model.AppSwitchPrintedResult
import com.piaodaren.ssc.model.Cinema
import com.piaodaren.ssc.model.CinemaGoodsResult
import com.piaodaren.ssc.model.CinemaHall
import com.piaodaren.ssc.model.CinemaHallResult
import com.piaodaren.ssc.model.CinemaPlan
import com.piaodaren.ssc.model.CinemaPlanMovie
import com.piaodaren.ssc.model.CinemaPlanResult
import com.piaodaren.ssc.model.CinemaResult
import com.piaodaren.ssc.model.CouponValidateResult
import com.piaodaren.ssc.model.HallArea
import com.piaodaren.ssc.model.HallSeat
import com.piaodaren.ssc.model.HallSeatResult
import com.piaodaren.ssc.model.MCAuthResult
import com.piaodaren.ssc.model.MCDResult
import com.piaodaren.ssc.model.MCGoodsPriceResult
import com.piaodaren.ssc.model.MCGoodsPubResult
import com.piaodaren.ssc.model.MCRechargeResult
import com.piaodaren.ssc.model.MCTicketPriceResult
import com.piaodaren.ssc.model.MCTicketPubResult
import com.piaodaren.ssc.model.MCardLevelResult
import com.piaodaren.ssc.model.PlanSeatState
import com.piaodaren.ssc.model.PlanSeatStateResult
import com.piaodaren.ssc.model.RemoteTicketResult
import com.piaodaren.ssc.model.SscAuthModel
import com.piaodaren.ssc.model.TicketFlagResult
import com.piaodaren.ssc.model.TicketRefundResult
import com.piaodaren.ssc.request.Coupon
import com.piaodaren.ssc.request.GoodsBuy
import com.piaodaren.ssc.request.McCheck
import com.piaodaren.ssc.request.McSscPriceFix
import com.piaodaren.ssc.request.SeatBuy

class PdrSscOp extends AbstractSscOp implements SscOp {
	static final JCLogger logger = LoggerSource.getLogger(PdrSscOp.class)
	
	//private SessionFactory sessionFactory;

	PdrSscOp(SscAuthModel auth_model){
		setAuthModel(auth_model);
	}

	//获取影院排期
	@Override
	public CinemaPlanResult get_cinema_plans(String cinema, Date start_time,Date end_time) {
		CinemaPlanResult result = new CinemaPlanResult();
		try {
			SimpleAjax ajax = JC.internal.call(SimpleAjax, "piaodaren", "movie/get_cinema_plans",[pid:getAuthModel().authNum, sid:cinema,start_time:start_time.getTime().toString(),end_time:end_time.getTime().toString()]);
			if (!ajax.isAvailable()) {
				result.setCode("100001")
				result.setRmCode("100001")
				result.setRmMsg(ajax.messages[0])
				return result;
			}
			List<CinemaPlan> cinemaPlanList = new ArrayList<CinemaPlan>();
			for (def cinemaPlan : ajax.data) {
				CinemaPlan plan = new CinemaPlan();
				
				List<CinemaPlanMovie> movies = new ArrayList<CinemaPlanMovie>();
				for (def movieInfo: cinemaPlan.movieInfo) {
					CinemaPlanMovie movie = new CinemaPlanMovie();
					movie.cineMovieId = movieInfo.cineMovieId;
					movie.cineMovieNum = movieInfo.cineMovieNum;
					movie.movieName = movieInfo.movieName;
					movie.movieLanguage = movieInfo.movieLanguage;
					movie.movieSubtitle = movieInfo.movieSubtitle;
					movie.movieFormat = movieInfo.movieFormat;
					movie.movieDimensional = movieInfo.movieDimensional;
					movie.movieSize = movieInfo.movieSize;
					movies.add(movie);
				}
				plan.movieInfo = movies;
				
				plan.id = cinemaPlan.id;
				plan.hallId = cinemaPlan.hallId
				plan.hallName = cinemaPlan.hallName
				plan.startTime = cinemaPlan.startTime
				plan.endTime = cinemaPlan.endTime
				plan.priceType = cinemaPlan.priceType
				plan.price = cinemaPlan.price
				plan.marketPrice = cinemaPlan.marketPrice
				plan.lowestPrice = cinemaPlan.lowestPrice
				plan.seatTotalNum = cinemaPlan.seatTotalNum
				plan.seatAvailableNum = cinemaPlan.seatAvailableNum;
				plan.allowBook = cinemaPlan.allowBook;
				plan.cineUpdateTime = cinemaPlan.cineUpdateTime;
				plan.businessDate = cinemaPlan.businessDate;
				cinemaPlanList.add(plan);
			}
			result.setResult(cinemaPlanList);
			result.setCode("0");
		} catch (Exception any) {
			logger.error("batch_lock ",any)
			result.setCode("100001")
			result.setRmCode("100001")
			result.setRmMsg("未知错误")
			return result;
		}
		return result;
	}

	@Override
	public RemoteTicketResult process_ticket(String cinema_no, String plan_no, String inner_order_no,String original_no,
			String dx_lock_flag, List<SeatPub> seat_nos, String last_update_time,String coupons,String payYuan) {
		RemoteTicketResult  result = new RemoteTicketResult();
		try {
			SimpleAjax ajax = JC.internal.call(SimpleAjax, "piaodaren", "movie/process_ticket",[pid:getAuthModel().authNum,order_no:original_no,sid:cinema_no,plan_no:plan_no, seats:JackSonBeanMapper.listToJson(seat_nos)]);
			if (!ajax.isAvailable()) {
				result.setCode("1000001");
				result.setRmCode("1000001")
				result.setRmMsg(ajax.messages[0])
				return result;
			}
			result.setValue1(ajax.data.value1);  
			result.setValue2(ajax.data.value2);  
			result.setCode("0")
			return result;
		} catch (Exception any) {
			logger.error("process_ticket ",any)
			result.setCode("1000001");
			result.setRmCode("1000001")
			result.setRmMsg(any.getMessage())
			return result;
		}
		return result;
	}

	
	/**
	 * 锁票
	 */
	@Override
	public String batch_lock(String out_trade_no,String original_no, String cinema_no, String plan_no, String last_update_time, List<SeatBuy> seats) {
		try {
			SimpleAjax ajax = JC.internal.call(SimpleAjax, "piaodaren", "movie/batch_lock",[pid:getAuthModel().authNum,order_no:original_no,sid:cinema_no,plan_no:plan_no,last_update_time:last_update_time,seats:JackSonBeanMapper.listToJson(seats)]);
			if (!ajax.isAvailable()) {
				return null;
			}
			return ajax.data.toString();
		} catch (Exception any) {
			logger.error("batch_lock ",any)
			return null;
		}
		return null;
	}

	/**
	 * 解锁
	 */
	@Override
	public boolean batch_unlock(String cinema_no, String plan_no,
		String dx_lock_flag, List<String> seat_nos,String original_no) {
		try {
			SimpleAjax ajax = JC.internal.call(SimpleAjax, "piaodaren", "movie/batch_unlock",[pid:getAuthModel().authNum,sid:cinema_no,plan_no:plan_no,order_no:original_no,seats:JackSonBeanMapper.listToJson(seat_nos)]);
			return ajax.isAvailable();
		} catch (Exception any) {
			logger.error("batch_lock ",any)
			return false;
		}
	}

	@Override
	public PlanSeatStateResult get_remote_seats_status(String cinema_no,String plan_no, String last_update_time,String sectionIds) {
		PlanSeatStateResult rtr = new PlanSeatStateResult();
		try {
			SimpleAjax ajax = JC.internal.call(SimpleAjax, "piaodaren", "movie/get_remote_seats_status",[pid:getAuthModel().authNum,sid:cinema_no,plan_no:plan_no]);
			if (!ajax.isAvailable()) {
				rtr.setCode("100001")
				rtr.setRmMsg(ajax.messages[0])
				return rtr;
			}
			
			List<PlanSeatState> planSeatStates = new ArrayList<PlanSeatState>();
			for (def seat: ajax.data) {
				PlanSeatState planSeatState = new PlanSeatState();
				planSeatState.seatNo = seat.seatNo;
				planSeatState.graphRow = seat.graphRow;
				planSeatState.graphCol = seat.graphCol;
				planSeatState.seatRow = seat.seatRow;
				planSeatState.seatCol = seat.seatCol;
				planSeatState.seatState = seat.seatState;
				planSeatStates.add(planSeatState);
			}
			rtr.result = planSeatStates;
			rtr.setCode("0");
			return rtr;
		} catch (Exception any) {
			logger.error("batch_lock ",any)
			rtr.setCode("100001")
			rtr.setRmMsg(any.getMessage())
			return rtr;
		}
	}

	@Override
	public CinemaResult get_cinema_list() {
		CinemaResult result = new CinemaResult();
		result.result = new ArrayList<Cinema>();
		//SimpleAjax sa = JC.internal.call("project", "incall/stores", [pid:this.getAuthModel().authNum])
		
		List<StoreGeneral> list = NativeUtil.connectAsArray(StoreGeneral, "project", "incall/stores", [pid:this.getAuthModel().authNum])
		for (StoreGeneral general : list) {
			Cinema cinema = new Cinema();
			cinema.cinemaId = general.getId().toString();
			result.result.add(cinema);
		}
		result.setCode("0");
		return result;
	}

	@Override
	public CinemaHallResult get_cinema_hall_list(String cinema_no) {
		CinemaHallResult rtr = new CinemaHallResult();
		try {
			SimpleAjax ajax = JC.internal.call(SimpleAjax, "piaodaren", "movie/get_cinema_hall_list",[pid:getAuthModel().authNum,sid:cinema_no]);
			if (!ajax.isAvailable()) {
				rtr.setCode("100001")
				rtr.setRmMsg(ajax.messages[0])
				return rtr;
			}
			List<CinemaHall> list = new ArrayList<CinemaHall>();
			for (def seat: ajax.data) {
				CinemaHall cinemaHall = new CinemaHall();
				cinemaHall.id = seat.id;
				cinemaHall.name = seat.name;
				cinemaHall.seatNum = seat.seatNum;
				cinemaHall.type = seat.type;
				cinemaHall.audioType = seat.audioType;
				list.add(cinemaHall);
			}
			rtr.result = list;
			rtr.setCode("0");
			return rtr;
		} catch (Exception any) {
			logger.error("batch_lock ",any)
			rtr.setCode("100001")
			rtr.setRmMsg(any.getMessage())
			return rtr;
		}
	}

	@Override
	public HallSeatResult get_hall_seat_list(String cinema_no, String hall_no) {
		HallSeatResult rtr = new HallSeatResult();
		try {
			logger.info("get_hall_seat_list_pdr_" + cinema_no +"_" + hall_no)
			SimpleAjax ajax = JC.internal.call(SimpleAjax, "piaodaren", "movie/get_hall_seat_list",[pid:getAuthModel().authNum,sid:cinema_no,hall_no:hall_no]);
			if (!ajax.isAvailable()) {
				rtr.setCode("100001")
				rtr.setRmMsg(ajax.messages[0])
				return rtr;
			}
			List<HallSeat> seats =  new ArrayList<HallSeat>();
			List<HallArea> areas = new ArrayList<HallArea>();;
			
			for (def seatInfo : ajax.data[0]) {
				HallSeat seat = new HallSeat();
				seat.cineSeatId = seatInfo.cineSeatId;
				seat.cinemaId = seatInfo.cinemaId;
				seat.xCoord = seatInfo.xCoord;
				seat.yCoord = seatInfo.yCoord;
				seat.loveseats = seatInfo.loveseats;
				seat.row = seatInfo.row;
				seat.column = seatInfo.column;
				seat.status = seatInfo.status;
				seat.type = seatInfo.type;
				seat.area_no = seatInfo.area_no;
				seats.add(seat)
			}
			for (def seatArea : ajax.data[1]) {
				HallArea area = new HallArea();
				area.name = seatArea.name;
				area.areaNo = seatArea.areaNo;
			}
			rtr.setCode("0");
			rtr.setAreas(areas)
			rtr.setResult(seats);
			return rtr;
		} catch (Exception any) {
			logger.error("batch_lock ",any)
			rtr.setCode("100001")
			rtr.setRmMsg(any.getMessage())
			return rtr;
		}
	}


	@Override
	public MCDResult doCardDetailRequest(String c_id, String card_no, String plan_id, Integer max_num, McCheck check_obj) {
		return null;
	}

	@Override
	public MCAuthResult doCardAuthRequest(McCheck check_info) {
		return null;
	}

	@Override
	public MCRechargeResult doCardRechargeRequest(String c_id, String card, String money, String serial_num) {
		return null;
	}

	@Override
	public TicketFlagResult query_ticket_code(String c_id, String inner_order_no) {
		return null;
	}

	@Override
	public TicketFlagResult query_ticket_code_by_mc(String c_id,
			String inner_order_no,String lock_flag) {
		return null;
	}

	/**
	 * 非会员卡退单
	 */
	@Override
	public TicketRefundResult ticket_refund(String c_id, String inner_order_no,
			String ticket_flag1, String ticket_flag2, String seats) {
		return null;
	}

	/**
	 * type=1 非会员退单， type=2 会员退单
	 */
	@Override
	public TicketRefundResult ticket_refund(String c_id, String inner_order_no, String type,String ticket_flag1, String ticket_flag2, String seats,String lock_flag) {
		TicketRefundResult result = new TicketRefundResult();
		try {
			SimpleAjax ajax = JC.internal.call(SimpleAjax, "piaodaren", "movie/ticket_refund",[pid:getAuthModel().authNum,sid:c_id,order_no:inner_order_no]);
			if (!ajax.isAvailable()) {
				result.setCode("100001")
				result.setRmMsg(ajax.messages[0])
			}
			result.setCode("0");
			return result;
		} catch (Exception any) {
			logger.error("batch_lock ",any)
			return any.getMessage();
			result.setCode("100001")
			result.setRmMsg(any.getMessage())
		}
	}

	@Override
	public CouponValidateResult coupon_validate(String c_id, String play_id,
			String single_settle_price, Integer seat_num, List<Coupon> coupons) {
		return null;
	}

	/**************************************** 卖品相关 ****************************************/
	@Override
	public CinemaGoodsResult query_goods_by_cinema(String c_id) {
		return null;
	}

	@Override
	public MCardLevelResult query_levels(String c_id) {
		return null;
	}

	/********* protected method **********/
	protected TicketRefundResult refund_mc(String c_id, String inner_order_no) {
		return null;
	}
	
	/********* protected method **********/
	protected TicketRefundResult refund_normal(String c_id, String inner_order_no) {
		return null;
	}

	
	/**
	 * 计算会员影票价格
	 */
	@Override
	public MCTicketPriceResult compute_mc_ticket_price(String c_id,
			String card_no, String flag_key, McSscPriceFix fix) {
		return null;
	}

	/**
	 * 计算会员商品价格
	 */
	@Override
	public MCGoodsPriceResult compute_mc_goods_price(String c_id, String card_no, String goods_key_str) {
		return null;
	}

	@Override
	public MCTicketPubResult pay_ticket_by_mc(String c_id, String card, String play_id, String default_handle_fee,
			String lock_flag, List<SeatBuy> seats, String out_trade_no, String update_time, McCheck fix) {
		return null;
	}

	/**
	 * 会员卖品出货
	 */
	@Override
	public MCGoodsPubResult pay_goods_by_mc(String c_id, String card,String pwd,
			String out_trade_no, List<GoodsBuy> goods,
			String balance_pay_amount, String cash_amount) {
		return null;
	}

	@Override
	public MCGoodsPubResult pay_goods_by_cash(String c_id, String out_trade_no,
			List<GoodsBuy> goods, String cash_amount) {
		return null;
	}
	 
	@Override
	public AppPrintTicketResult fetchPringInfo(String cinema_no, String ticketFlag1, String ticketFlag2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AppSwitchPrintedResult ticketPrint(String cinema_no, String ticketFlag1, String ticketFlag2) {
		// TODO Auto-generated method stub
		return null;
	}

}
