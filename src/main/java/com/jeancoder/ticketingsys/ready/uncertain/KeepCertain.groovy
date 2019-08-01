package com.jeancoder.ticketingsys.ready.uncertain

import java.sql.Timestamp
import java.text.SimpleDateFormat

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.entity.LockStatusCode
import com.jeancoder.ticketingsys.ready.entity.SaleOrder
import com.jeancoder.ticketingsys.ready.entity.SaleRemote
import com.jeancoder.ticketingsys.ready.entity.SaleSeat
import com.jeancoder.ticketingsys.ready.sms.Sender
import com.jeancoder.ticketingsys.ready.ssc.TicketingsysFactory
import com.jeancoder.ticketingsys.ready.ssc.SssHelper
import com.jeancoder.ticketingsys.ready.store.StoreService
import com.jeancoder.ticketingsys.ready.store.dto.CinemaAuthInfo
import com.jeancoder.ticketingsys.ready.store.dto.StoreInfo
import com.jeancoder.ticketingsys.ready.support.TicketingSysTypeHelper
import com.piaodaren.ssc.factory.SscOp
import com.piaodaren.ssc.model.CinemaPlan
import com.piaodaren.ssc.model.CinemaPlanResult
import com.piaodaren.ssc.model.SscAuthModel
import com.piaodaren.ssc.model.UnlockModel
import com.piaodaren.ssc.request.SeatBuy

class KeepCertain {

	static Map<BigInteger, String[]> _map_notify_mobiles_ = new HashMap<Long, String[]>();
	
	static {
		_map_notify_mobiles_.put(new BigInteger(2l), ['18031040326'] as String[]);	//邯郸
		_map_notify_mobiles_.put(new BigInteger(6l), ['13847276697'] as String[]);	//包头
	}
	
	static JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
	
	static SimpleDateFormat datesdf = new SimpleDateFormat("yyyy-MM-dd");
	
	static SimpleDateFormat datesdf_yyyyMMddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	static SimpleDateFormat datesdf_HHmmss = new SimpleDateFormat("HH:mm:ss");
	
	def static nextInt(final int min, final int max){
		Random rand= new Random();
		int tmp = Math.abs(rand.nextInt());
		return tmp % (max - min + 1) + min;
	}
	
	
	def static main(def arc) {
		String stand = '20181106221624179625';
		String new_stand = '20181106221624179625888888';
		println new_stand.substring(0, stand.length());
		
		def now_time = datesdf_HHmmss.format(Calendar.getInstance().getTime());
		println now_time;
		def plan_time = '10:00:00';
		plan_time = '22:40:00';
		println now_time>plan_time;
		
	}
	def static generate_new_order_no(String order_no) {
		String stand = '20181106221624179625';
		
		def new_order_no = order_no		//固定增加六位，方便 like ***% 操作
		if(order_no.length() > 20) {
			new_order_no = order_no.substring(0, 20) + nextInt(100000, 999999);
		} else {
			new_order_no = order_no + nextInt(100000, 999999);
		}
		return new_order_no;
	}
	
	def static fuckoff(TimerTask obj) {
		Logger.info(datesdf_yyyyMMddHHmmss.format(Calendar.getInstance().getTime()) + 'checking starting......')
		
		def now_date = datesdf.format(Calendar.getInstance().getTime());
		def now_time = datesdf_HHmmss.format(Calendar.getInstance().getTime());
		
		def sql = 'select * from SaleOrder where flag!=? and (order_status=? or order_status=? or order_status=? or order_status=?) and plan_date>=? and proj_id!=';
		Logger.info('LOOP_LOCK_SQL:::' + sql + ', param=' + now_date);
		try {
			List<SaleOrder> result = JcTemplate.INSTANCE().find(SaleOrder, sql, -1, '2900', '2901', '3900', '3901', now_date, 2);	//先把邯郸的锁座关了
			if(result) {
				Logger.info('need check order result size=' + result.size());
				for(SaleOrder x in result) {
					def id = x.store_id;
					def order_no = x.order_no;
					def plan_id = x.plan_id;
					def plan_date = x.plan_date;
					
					StoreInfo store = StoreService.INSTANCE.getById(new Long(id.toString()));
					CinemaAuthInfo cinemaAuthInfo = StoreService.INSTANCE.getCinemaAuthInfo(new Long(id.toString()));
					String tc_ss_type = cinemaAuthInfo.getSystemSsCode()
					String channel_url = cinemaAuthInfo.getAuthChannelUrl()
					String channel_num = cinemaAuthInfo.getAuthChannelNo()
					String channel_code = cinemaAuthInfo.getAuthChannelCode()
					SscAuthModel auth_model = TicketingsysFactory.direct_generateAuthModel(TicketingSysTypeHelper.transToOldCode(tc_ss_type) , channel_url, channel_num, channel_code);
					
					SscOp ssc_op = TicketingsysFactory.generateSscOp(auth_model);
					
					//首先获取场次
					Date start_time = datesdf.parse(plan_date);
					Calendar c = Calendar.getInstance(TimeZone.getDefault());
					c.setTime(start_time);
					c.add(Calendar.DATE, 1);
					Date end_time = c.getTime();
					
					def start = Calendar.getInstance().getTimeInMillis();
					Logger.info('start_to_check_plan_with_cache');
					CinemaPlanResult planResult = SssHelper.INSTANCE.get_cinema_plans(cinemaAuthInfo, start_time, end_time);	//走缓存读取的代码
					//CinemaPlanResult planResult = SssHelper.INSTANCE.get_cinema_plans_without_cache(cinemaAuthInfo, start_time, end_time);	//不走缓存读取的代码
					def end = Calendar.getInstance().getTimeInMillis();
					Logger.info('end_to_check_plan_with_cache:' + (end - start)/1000);
					
					CinemaPlan matchPlan = null;
					for(CinemaPlan cplan : planResult.getResult()) {
						if(cplan.getId().equals(plan_id)) {
							matchPlan = cplan;
						}
					}
					
					if(!matchPlan) {
						//重新循环
						for(CinemaPlan cplan : planResult.getResult()) {
							
							def plan_time = cplan.startTime;
							def plan_hall = cplan.hallId;
							
							def order_plan_date = x.plan_date;
							def order_plan_time = x.plan_time;
							def order_plan_hall = x.hall_id;
							if(plan_time.indexOf(order_plan_date)>-1 && plan_time.indexOf(order_plan_time)>-1 && plan_hall.equals(order_plan_hall)) {
								matchPlan = cplan;
							}
						}
					}
					
					if(!matchPlan) {
						Logger.error(x.order_no + ' not matched plan.');
						continue;
					}
					//获取座位信息
					List<SaleSeat> order_seats = JcTemplate.INSTANCE().find(SaleSeat, 'select * from SaleSeat where order_id=?', x.id);
					if(!order_seats) {
						continue;
					}
					//获取订单的锁座信息
					SaleRemote order_remote = JcTemplate.INSTANCE().get(SaleRemote, 'select * from SaleRemote where order_id=?', x.id);
					
					def seats = [];
					def seat_nos = [];
					StringBuffer seat_infos = new StringBuffer();
					for(y in order_seats) {
						SeatBuy seat_buy_obj = new SeatBuy();
						seat_buy_obj.setSeat_no(y.seat_no);
						seat_buy_obj.setBuy_amount(y.sale_fee);
						seat_buy_obj.setHandle_fee('0');
						seat_buy_obj.setSubmitPrice(y.pub_fee);
						seats.add(seat_buy_obj);
						
						seat_nos.add(y.seat_no);
						
						seat_infos.append(y.seat_sr + '排' + y.seat_sc + '座');
					}
					def last_update_time = matchPlan.cineUpdateTime;
					
					//重置plan_id
					plan_id = matchPlan.getId();
					
					//通知模版，失败时候触发
					//String content = x.store_name + ',' + x.hall_name + ',' + x.film_name + ',' + x.plan_date + ' ' + x.plan_time + ',' + seat_infos.toString() + ',（' + x.order_no + '）';
					
					LockStatusCode status_code = new LockStatusCode();
					status_code.order_id = x.id;
					status_code.order_no = x.order_no;
					status_code.pid = x.proj_id;
					status_code.store_name = x.store_name;
					status_code.hall_name = x.hall_name;
					status_code.film_name = x.film_name;
					status_code.plan_id = x.plan_id;
					status_code.plan_date = x.plan_date;
					status_code.plan_time = x.plan_time;
					status_code.seats = seat_infos.toString();
					
					def notify_mobiles = _map_notify_mobiles_.get(x.proj_id);
					
					if(order_remote==null||order_remote.lock_flag==null) {
						//订单没有锁座类相关信息，走直接锁座的模式
						String remoteResult = ssc_op.batch_lock(order_no, order_no, cinemaAuthInfo.getCinemaCode(), plan_id, last_update_time, seats);
						if(remoteResult==null) {
							//循环锁座失败，需要做特殊处理
							Logger.info(x.order_no + ' first_lock_failed');
							
							//Sender.sendMessage(notify_mobiles, '【嗨票】' + '初锁失败：' + content)
							
							status_code.prot_code = '0000';
							Sender.send_msg_by_code(notify_mobiles, status_code);
						} else {
							Logger.info(x.order_no + ' first_lock_success');	//打印日志并更新锁座码
							//更新订单的锁座码
							try {
								if(order_remote!=null) {
									order_remote.lock_flag = remoteResult;
									order_remote.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
									JcTemplate.INSTANCE().update(order_remote);
								} else {
									order_remote = new SaleRemote();
									order_remote.order_id = x.id;
									order_remote.flag = 0;
									order_remote.lock_flag = remoteResult;
									order_remote.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
									JcTemplate.INSTANCE().save(order_remote);
								}
							} catch(any) {
								Logger.error(x.order_no + ' update lock exception', any);
								//Sender.sendMessage(notify_mobiles, '【嗨票】' + '更新锁座码异常：' + any.toString());
								
								status_code.prot_code = '9999';
								Sender.send_msg_by_code(notify_mobiles, status_code);
							}
						}
					} else {
						//要进行先解锁再锁座操作
						def dx_lock_flag = order_remote.lock_flag;
						//用老订单号进行解锁
						//boolean lock_success = ssc_op.batch_unlock(cinemaAuthInfo.getCinemaCode(), plan_id, dx_lock_flag, seat_nos, order_no);
						boolean lock_success = false;
						UnlockModel unlock_model = null;
						try {
							unlock_model = ssc_op.batch_unlock_new(cinemaAuthInfo.getCinemaCode(), plan_id, dx_lock_flag, seat_nos, order_no);
							if(unlock_model.code=='0') {
								lock_success = true;
							} else {
								//打印异常
								Logger.error(x.order_no + '::: UNLOCK FAILED');
							}
						}catch(Exception unsuppex) {
							Logger.error('EX::' + unsuppex.getMessage());
							lock_success = ssc_op.batch_unlock(cinemaAuthInfo.getCinemaCode(), plan_id, dx_lock_flag, seat_nos, order_no);
						}
						
						if(!lock_success) {
							//解锁失败，发送通知消息
							String unlock_err_msg = x.order_no + '::: UNLOCK FAILED';
							if(unlock_model!=null) {
								unlock_err_msg = unlock_err_msg + "=======LOCAL MSG:::" + unlock_model.getMsg() + ", RMCODE=" + unlock_model.getRmCode() + ", RMMSG=" + unlock_model.getRmMsg();
							}
							Logger.error(unlock_err_msg);
							//Sender.sendMessage(notify_mobiles, '【嗨票】' + '解锁失败：' + content)
							
							status_code.prot_code = '1001';
							Sender.send_msg_by_code(notify_mobiles, status_code);
						} else {
							//解锁成功，直接锁
							//这里需要做特殊处理，需要生成新的订单编号
							//def new_order_no = order_no + nextInt(100000, 999999);		//固定增加六位，方便 like ***% 操作
							def new_order_no = generate_new_order_no(order_no);
							String remoteResult = ssc_op.batch_lock(order_no, new_order_no, cinemaAuthInfo.getCinemaCode(), plan_id, last_update_time, seats);
							if(remoteResult==null) {
								Logger.error(x.order_no + ' keep lock failed');
								//Sender.sendMessage(notify_mobiles, '【嗨票】' + '循环锁失败：' + content)
								
								status_code.prot_code = '2100';
								Sender.send_msg_by_code(notify_mobiles, status_code);
							} else {
								Logger.info(x.order_no + ' keep lock success');	//打印日志并更新锁座码
								//更新订单的锁座码
								try {
									if(order_remote!=null) {
										order_remote.lock_flag = remoteResult;
										order_remote.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
										JcTemplate.INSTANCE().update(order_remote);
									} else {
										order_remote = new SaleRemote();
										order_remote.order_id = x.id;
										order_remote.flag = 0;
										order_remote.lock_flag = remoteResult;
										order_remote.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
										JcTemplate.INSTANCE().save(order_remote);
									}
									//更新订单信息
									try {
										x.order_no = new_order_no;
										JcTemplate.INSTANCE().update(x);
									} catch(any) {
										Logger.error('order_no=' + order_no + ' and new_order_no=' + new_order_no + ' exception:', any);
									}
								} catch(any) {
									Logger.error(x.order_no + ' update lock exception', any);
									//Sender.sendMessage(notify_mobiles, '【嗨票】' + '更新锁座码异常：' + any.toString());
									
									status_code.prot_code = '9999';
									Sender.send_msg_by_code(notify_mobiles, status_code);
								}
							}
						}
						
						if(!lock_success) {
							//这里再进行一次锁座尝试
							//def new_order_no = order_no + nextInt(100000, 999999);		//固定增加六位，方便 like ***% 操作
							def new_order_no = generate_new_order_no(order_no);
							String remoteResult = ssc_op.batch_lock(order_no, new_order_no, cinemaAuthInfo.getCinemaCode(), plan_id, last_update_time, seats);
							if(remoteResult==null) {
								Logger.error(x.order_no + ' keep lock failed');
								//Sender.sendMessage(notify_mobiles, '【嗨票】' + '解锁失败后尝试直接锁：' + content)
								
								status_code.prot_code = '2200';
								Sender.send_msg_by_code(notify_mobiles, status_code);
							} else {
								Logger.info(x.order_no + ' keep lock success');	//打印日志并更新锁座码
								//更新订单的锁座码
								try {
									if(order_remote!=null) {
										order_remote.lock_flag = remoteResult;
										order_remote.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
										JcTemplate.INSTANCE().update(order_remote);
									} else {
										order_remote = new SaleRemote();
										order_remote.order_id = x.id;
										order_remote.flag = 0;
										order_remote.lock_flag = remoteResult;
										order_remote.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
										JcTemplate.INSTANCE().save(order_remote);
									}
									//更新订单信息
									try {
										x.order_no = new_order_no;
										JcTemplate.INSTANCE().update(x);
									} catch(any) {
										Logger.error('order_no=' + order_no + ' and new_order_no=' + new_order_no + ' exception:', any);
									}
								} catch(any) {
									Logger.error(x.order_no + ' update lock exception', any);
									//Sender.sendMessage(notify_mobiles, '【嗨票】' + '更新锁座码异常：' + any.toString());
									
									status_code.prot_code = '9999';
									Sender.send_msg_by_code(notify_mobiles, status_code);
								}
							}
						}
					}
					
					
					
//					String remoteResult = ssc_op.batch_lock(order_no, order_no, cinemaAuthInfo.getCinemaCode(), plan_id, last_update_time, seats);
//					
//					if(remoteResult==null) {
//						//循环锁座失败，需要做特殊处理
//					} else {
//						Logger.info(x.order_no + ' keep lock success');	//仅打印日志
//					}
				}
			}
			Logger.info('need check order result  end');
		} catch(any) {
			//any.printStackTrace();
			Logger.error("keep lock program exception:", any);
			if(obj)
				obj.cancel();
		}
	}
}
