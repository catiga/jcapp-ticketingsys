package com.jeancoder.ticketingsys.internal.ticketing

import java.net.URLDecoder
import java.util.List

import com.jeancoder.app.sdk.JC
import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.core.log.JCLoggerFactory
import com.jeancoder.core.util.JackSonBeanMapper
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.dto.SimpleAjax
import com.jeancoder.ticketingsys.ready.entity.SaleOrder
import com.jeancoder.ticketingsys.ready.entity.SaleSeat
import com.jeancoder.ticketingsys.ready.support.MoneyUtil
import com.jeancoder.ticketingsys.ready.util.DateUtil

JCLogger Logger = LoggerSource.getLogger();
def get_code = JC.internal.param('get_code');
def token_key = JC.internal.param('token');
if(!get_code) {
	return SimpleAjax.notAvailable('param_error,取票验证码为空');
}
def flag_1 = get_code.substring(0, 6);
def flag_2 = get_code.substring(6);

def sql = 'select * from SaleOrder where id in (select order_id from SaleCode where ticket_flag_1=? and ticket_flag_2=?)';
List<SaleOrder> result = JcTemplate.INSTANCE().find(SaleOrder, sql, flag_1, flag_2);
if(!result) {
	return SimpleAjax.notAvailable('obj_not_found,订单信息不存在');
}
SaleOrder order = result.get(0);//查询到订单信息
def order_seats = JcTemplate.INSTANCE().find(SaleSeat, 'select * from SaleSeat where order_id=?', order.id);//座位
if(!order_seats) {
	return SimpleAjax.notAvailable('obj_not_found,订单座位信息为空');
}
if(!order.order_status.startsWith('2')) {
	return SimpleAjax.notAvailable('status_invalid,订单状态不支持取票操作');
}
//去检查这笔订单的交易状态
SimpleAjax trade = JC.internal.call(SimpleAjax.class, 'trade', '/incall/take_trade_order', [oid:order.id,onum:order.order_no,oc:'2000', token:token_key]);

if(trade==null) {
	return SimpleAjax.notAvailable('net_work_error,应用通讯异常，请稍后再试');
}
if(!trade.available) {
	return trade;
}
//开始修改订单状态，并通知交易中心订单状态修改
if(order.order_status=='2900' || order.order_status=='2901') {
	order.order_status = '3900';
} else if(order.order_status=='2000') {
	order.order_status = '3000';
}
JcTemplate.INSTANCE().update(order);

return SimpleAjax.available('', trade.data);	//返回小票模版

/*
def  plan_date = order.plan_date;// 日期
def  plan_time = order.plan_time.subSequence(0, 5);//时间
plan_date = plan_date + " " + plan_time;
def  hall_name = order.hall_name;//影厅名称
def ticket_sum = order.ticket_sum;// 数量
def sale_time =DateUtil.getDate(order.a_time.getTime()); // 售票时间
def total_amount = order.total_amount;
def pay_amount = order.pay_amount;
def film_name = order.film_name;
def each = MoneyUtil.divide(pay_amount.toString(), ticket_sum.toString());
def unm = "20180102301203102"; // 票号
List<String> dalist = new ArrayList<String>();
for(def seats : order_seats){
	def handle_fee = seats.handle_fee;
	def seat_sr = seats.seat_sr;
	def seat_sc = seats.seat_sc;
	seat = seat_sr + "排" + seat_sc+"座";
	def sale_fee = seats.sale_fee;
	if (new BigDecimal(each).compareTo(new BigDecimal(seats.pub_fee.toString())) >= 0 ) {
		sale_fee = each.toString();
	} else {
		sale_fee = seats.pub_fee.toString();
	}
	sale_fee = MoneyUtil.get_yuan_from_cent(sale_fee);
	def html = """
			<!DOCTYPE html>
		    <html>
		      <head>
		        <meta charset="utf-8">
		        <title>影票打印</title>
		      </head>
		      <body style="width:80mm">

		     <div style="height:80mm;margin-top:5mm;">
		        <div style="padding-left:5mm;float:left;width:50mm;">
		           <div style="width:50mm;height:10mm;font-size:10pt;">
		             <div style="float: left;width:20mm">
		               <span style="font-size:10pt;font-family:黑体;text-align:left;word-break:break-all;word-wrap:break-word;">${hall_name}</span>
		             </div>
		              <div style="float: left;width:25mm">
		                 <span style="font-size:10pt;font-family:黑体;margin-top: 5mm;text-align:left;word-break:break-all;word-wrap:break-word;">${plan_date}</span><br>
		              </div>
		           </div>
		           <div style="width:60mm;height:5mm;font-size:10pt;clear:both;">
		              <span style="padding-top:3mm;font-size:10pt;font-family:黑体;text-align:left;word-break:break-all;word-wrap:break-word;">${film_name}</span>
		           </div>

		           <div style=" margin-top:1mm;width:60mm;height:10mm;font-size:10pt">

		              <div style="float:left;width:20mm;height:10mm;font-size:10pt;">
		                 <span style="font-size:10pt;font-family:黑体;text-align:left;word-break:break-all;word-wrap:break-word;">${seat}</span><div style="height:2mm;"></div>
		                 <span style="padding-top:5mm;font-size:10pt;font-family:黑体;text-align:left;word-break:break-all;word-wrap:break-word;">标准票</span>
		              </div>

		              <div style=" float:left;width:20mm;height:10mm;font-size:10pt;margin-top:-2mm;">
		                   <p style="font-size:10pt;font-family:黑体;text-align:left;padding-left: 12mm;height:2mm">${sale_fee}</p>
		                   <p style="font-size:10pt;font-family:黑体;padding-left: 12mm;text-align:left;height:2mm">0.00</p>
		              </div>

		           </div>

		           <div style=" padding-top:4mm; width:60mm;height:10mm;font-size:10pt">
		             <div style="float: left; width:20mm;">
		               <span style="font-size:10pt;font-family:黑体;text-align:left;word-break:break-all;word-wrap:break-word;">${sale_time}</span>
		             </div>
		             <div style="float: left;width:20mm">
		               <span style="padding-left:5mm;font-size:10pt;font-family:黑体;text-align:left;word-break:break-all;word-wrap:break-word;"></span><br>
		             </div>
		           </div>

		          <div style="display:table-cell;vertical-align:middle;text-align: center;padding-left:10px;padding-top:15px;align:middle;">
		             <img style="width:100px;height:100px;" src="http://pe1s.static.pdr365.com/ticket.png"/>
		          </div>

		          <div style=" padding-top:5mm; width:60mm;height:5mm;font-size:10pt;margin-top:7mm">
		              <span style="font-size:10pt;font-family:黑体;text-align:left;word-break:break-all;word-wrap:break-word;"></span>
		           </div>

		        </div>
		        <div  style="float:left;width:20mm;margin-top: -3mm;">
		            <div style="font-size:10pt">
		               <p style="float:left;width:22mm;padding-left:7mm;text-align: left">${hall_name}</p >
		            </div>
		             <div style="font-size:10pt">
		               <p style="float:left;width:22mm;padding-left:10mm;text-align: left">${plan_date}</p >
		            </div>
		             <div style="font-size:10pt">
		               <p style="float:left;width:22mm;padding-left:10mm; text-align: left">${seat}</p >
		            </div>
		             <div style="font-size:10pt;word-wrap:break-word">
		               <p style="float:left;width:22mm;padding-left:10mm;padding-top:5mm;text-align: left"></p >
		            </div>
		             <div style="font-size:10pt">
		               <p style="float:left;width:22mm;padding-left:10mm;padding-top:3mm;text-align: left">${sale_fee}</p>
		            </div>
		        </div>
		      </div>
		      </body>
		    </html>
	""";
	dalist.add(html.toString());
}
println JackSonBeanMapper.listToJson(dalist);

Logger.info("take_by_code_" +JackSonBeanMapper.listToJson(dalist));
trade.data = URLEncoder.encode(URLEncoder.encode(JackSonBeanMapper.listToJson(dalist)));
return trade;
*/
