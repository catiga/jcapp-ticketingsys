package com.jeancoder.ticketingsys.ready.uncertain;

import java.util.ArrayList
import java.util.List

import com.jeancoder.app.sdk.JC
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.dto.SimpleAjax
import com.jeancoder.ticketingsys.ready.entity.Cinema
import com.piaodaren.ssc.factory.SeatPub
import com.piaodaren.ssc.factory.SscOp
import com.piaodaren.ssc.model.AppPrintTicketResult;
import com.piaodaren.ssc.model.AppSwitchPrintedResult;
import com.piaodaren.ssc.model.CinemaGoodsResult;
import com.piaodaren.ssc.model.CinemaHallResult;
import com.piaodaren.ssc.model.CinemaPlanResult;
import com.piaodaren.ssc.model.CinemaResult;
import com.piaodaren.ssc.model.CouponValidateResult;
import com.piaodaren.ssc.model.HallSeatResult;
import com.piaodaren.ssc.model.MCAuthResult;
import com.piaodaren.ssc.model.MCDResult;
import com.piaodaren.ssc.model.MCGoodsPriceResult;
import com.piaodaren.ssc.model.MCGoodsPubResult;
import com.piaodaren.ssc.model.MCRechargeResult;
import com.piaodaren.ssc.model.MCTicketPriceResult;
import com.piaodaren.ssc.model.MCTicketPubResult;
import com.piaodaren.ssc.model.MCardLevelResult;
import com.piaodaren.ssc.model.PlanSeatStateResult;
import com.piaodaren.ssc.model.RemoteTicketResult;
import com.piaodaren.ssc.model.SscAuthModel;
import com.piaodaren.ssc.model.TicketFlagResult;
import com.piaodaren.ssc.model.TicketRefundResult;
import com.piaodaren.ssc.model.UnlockModel
import com.piaodaren.ssc.request.Coupon;
import com.piaodaren.ssc.request.GoodsBuy;
import com.piaodaren.ssc.request.McCheck;
import com.piaodaren.ssc.request.McSscPriceFix;
import com.piaodaren.ssc.request.SeatBuy;

public class SscOpProxy implements SscOp {
	
	JCLogger LOGGER = JCLoggerFactory.getLogger(SscOpProxy.class);
	
	def nextInt(final int min, final int max){
		Random rand= new Random();
		int tmp = Math.abs(rand.nextInt());
		return tmp % (max - min + 1) + min;
	}
	
	SscOp instance = null;
	
	def user_id;	//apid

	@Override
	public SscAuthModel getAuthModel() {
		return instance.getAuthModel();
	}

	@Override
	public void setAuthModel(SscAuthModel authModel) {
		instance.setAuthModel(authModel);
	}

	@Override
	public CinemaPlanResult get_cinema_plans(String cinema, Date start_time, Date end_time) {
		return instance.get_cinema_plans(cinema, start_time, end_time);
	}

	static def real_pub_stores = ['402','31','20005114','20005042','20003053','20003047','20003043','20003041','20004066','20003049', '15100108', '15101021', '15051541', '15101491', '15101751', '20002825', '20004552'];
	
	@Override
	public RemoteTicketResult process_ticket(String cinema_no, String plan_no, String inner_order_no,
			String original_no, String lock_flag, List<SeatPub> seat_nos, String last_update_time, String coupons,
			String payYuan) {
		def need_cut = true;
		def pid = null;
		if(user_id) {
			try {
				SimpleAjax user_info =  JC.internal.call(SimpleAjax, 'crm', '/h5/p/info_by_apid', [apid:user_id]);
				if(user_info&&user_info.available&&user_info.data['mobile']) {
					need_cut = WhiteListHolder.judge(user_info.data['mobile']);	//不能拦截
					pid = user_info.data['pid'];
				}
			} catch(any) {}
		}
//		for(s in real_pub_stores) {
//			if(cinema_no==s) {
//				need_cut = false;
//			}
//		}
		//获取本地影城列表
		def cinema_sql = 'select * from Cinema where flag!=?';
		def params = []; params.add(-1);
		if(pid) {
			cinema_sql += ' and proj_id=?';
			params.add(pid);
		}
		List<Cinema> cinemas = JcTemplate.INSTANCE().find(Cinema, cinema_sql, params.toArray());
		if(cinemas) {
			for(x in cinemas) {
				if(x.store_no==cinema_no) {
					if(x.pubflag==1) {
						//改为需要出票
						LOGGER.info('TICKETSYS:::exe pub ticket real status.' + x.store_no);
						need_cut = false;
					}
					break;
				}
			}
		}
		
		if(need_cut) {
			String vlid_code1 = this.nextInt(100000, 999999) + "";
			String vlid_code2 = this.nextInt(100000, 999999) + "";
			
			RemoteTicketResult ret_result = new RemoteTicketResult();
			ret_result.setCode("0");
			ret_result.setValue1(vlid_code1);
			ret_result.setValue2(vlid_code2);
			
			//设置循环锁座标识为
			ret_result.setRmCode('000001');	//在前台需要做处理
			LOGGER.info("锁票" +cinema_no);
			return ret_result;
		} else {
			LOGGER.info("出票");
			return instance.process_ticket(cinema_no, plan_no, inner_order_no, original_no, lock_flag, seat_nos, last_update_time, coupons, payYuan);
		}
	}

	@Override
	public String batch_lock(String out_trade_no, String original_no, String cinema_no, String plan_no,
			String last_update_time, List<SeatBuy> seats) {
		return instance.batch_lock(out_trade_no, original_no, cinema_no, plan_no, last_update_time, seats);
	}

	@Override
	public boolean batch_unlock(String cinema_no, String plan_no, String dx_lock_flag, List<String> seat_nos,
			String original_no) {
		return instance.batch_unlock(cinema_no, plan_no, dx_lock_flag, seat_nos, original_no);
	}

	@Override
	public PlanSeatStateResult get_remote_seats_status(String cinema_no, String plan_no, String last_update_time,
			String sectionIds) {
		return instance.get_remote_seats_status(cinema_no, plan_no, last_update_time, sectionIds);
	}

	@Override
	public CinemaResult get_cinema_list() {
		return instance.get_cinema_list();
	}

	@Override
	public CinemaHallResult get_cinema_hall_list(String cinema_no) {
		return instance.get_cinema_hall_list(cinema_no);
	}

	@Override
	public HallSeatResult get_hall_seat_list(String cinema_no, String hall_no) {
		return instance.get_hall_seat_list(cinema_no, hall_no);
	}

	@Override
	public TicketFlagResult query_ticket_code(String c_id, String inner_order_no) {
		return instance.query_ticket_code(c_id, inner_order_no);
	}

	@Override
	public TicketRefundResult ticket_refund(String c_id, String inner_order_no, String ticket_flag1,
			String ticket_flag2, String seats) {
		return instance.ticket_refund(c_id, inner_order_no, ticket_flag1, ticket_flag2, seats);
	}

	@Override
	public CouponValidateResult coupon_validate(String c_id, String play_id, String single_settle_price,
			Integer seat_num, List<Coupon> coupons) {
		return instance.coupon_validate(c_id, play_id, single_settle_price, seat_num, coupons);
	}

	@Override
	public MCGoodsPubResult pay_goods_by_cash(String c_id, String out_trade_no, List<GoodsBuy> goods,
			String cash_amount) {
		return instance.pay_goods_by_cash(c_id, out_trade_no, goods, cash_amount);
	}

	@Override
	public MCDResult doCardDetailRequest(String c_id, String card_no, String plan_id, Integer max_num,
			McCheck check_obj) {
		return instance.doCardDetailRequest(c_id, card_no, plan_id, max_num, check_obj);
	}

	@Override
	public MCAuthResult doCardAuthRequest(McCheck check_info) {
		return instance.doCardAuthRequest(check_info);
	}

	@Override
	public MCRechargeResult doCardRechargeRequest(String c_id, String card, String money, String serial_num) {
		return instance.doCardRechargeRequest(c_id, card, money, serial_num);
	}

	@Override
	public TicketFlagResult query_ticket_code_by_mc(String c_id, String inner_order_no, String lock_flag) {
		return instance.query_ticket_code_by_mc(c_id, inner_order_no, lock_flag);
	}

	@Override
	public MCardLevelResult query_levels(String c_id) {
		return instance.query_levels(c_id);
	}

	@Override
	public TicketRefundResult ticket_refund(String c_id, String inner_order_no, String type, String ticket_flag1,
			String ticket_flag2, String seats, String lock_flag) {
		return instance.ticket_refund(c_id, inner_order_no, type, ticket_flag1, ticket_flag2, seats, lock_flag);
	}

	@Override
	public MCTicketPriceResult compute_mc_ticket_price(String c_id, String card_no, String flag_key,
			McSscPriceFix fix) {
		return instance.compute_mc_ticket_price(c_id, card_no, flag_key, fix);
	}

	@Override
	public MCGoodsPriceResult compute_mc_goods_price(String c_id, String card_no, String flag_key) {
		return instance.compute_mc_goods_price(c_id, card_no, flag_key);
	}

	@Override
	public MCTicketPubResult pay_ticket_by_mc(String c_id, String card, String play_id, String handle_fee,
			String lock_key_str, List<SeatBuy> seats, String out_trade_no, String update_time, McCheck fix) {
		return instance.pay_ticket_by_mc(c_id, card, play_id, handle_fee, lock_key_str, seats, out_trade_no, update_time, fix);
	}

	@Override
	public MCGoodsPubResult pay_goods_by_mc(String c_id, String card, String pwd, String out_trade_no,
			List<GoodsBuy> goods, String balance_pay_amount, String cash_amount) {
		return instance.pay_goods_by_mc(c_id, card, pwd, out_trade_no, goods, balance_pay_amount, cash_amount);
	}

	@Override
	public CinemaGoodsResult query_goods_by_cinema(String c_id) {
		return instance.query_goods_by_cinema(c_id);
	}

	@Override
	public AppPrintTicketResult fetchPringInfo(String cinema_no, String ticketFlag1, String ticketFlag2) {
		return instance.fetchPringInfo(cinema_no, ticketFlag1, ticketFlag2);
	}

	@Override
	public AppSwitchPrintedResult ticketPrint(String cinema_no, String ticketFlag1, String ticketFlag2) {
		return instance.ticketPrint(cinema_no, ticketFlag1, ticketFlag2);
	}

	@Override
	public UnlockModel batch_unlock_new(String cinema_no, String plan_no, String dx_lock_flag, List<String> seat_nos,
			String original_no) {
		return instance.batch_unlock_new(cinema_no, plan_no, dx_lock_flag, seat_nos, original_no);
	}

}
