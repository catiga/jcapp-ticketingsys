package com.jeancoder.ticketingsys.ready.sms

import java.sql.Timestamp

import com.jeancoder.app.sdk.source.LoggerSource
import com.jeancoder.core.log.JCLogger
import com.jeancoder.jdbc.JcTemplate
import com.jeancoder.ticketingsys.ready.entity.LockStatusCode

class Sender {
	
	static Map<String, String> _code_msg_ = new HashMap<String, String>();
	
	static {
		_code_msg_.put('0000', '初锁失败');
		_code_msg_.put('1001', '解锁失败');
		_code_msg_.put('2100', '循环锁失败');
		_code_msg_.put('2200', '解锁失败后尝试直接锁');
		_code_msg_.put('9999', '更新锁座码异常');
	}
	
	static JCLogger Logger = LoggerSource.getLogger(this.getClass().getName());
	
	static void main(def argc) {
		def content = '测试通知';
		//Sender.send_msg_by_code('0000', ['13501020884'] as String[], '【嗨票】' + '初锁失败：' + content);
		
		String[] mobiles = ['13501020884', '18601902957'] as String[];
		
		println mobiles.join(',');
	}
	
	def static String send_msg_by_code(String[] mobiles, LockStatusCode content) {
		//首先根据订单id去查询
		Logger.info('enter save or update process:' + content.order_no);
		try {
			def sql = 'select * from LockStatusCode where order_id=? and flag!=?';
			LockStatusCode exist_code = JcTemplate.INSTANCE().get(LockStatusCode, sql, content.order_id, -1);
			
			if(!exist_code) {
				//不存在，则走新创建流程
				exist_code = content;		//直接初始化复制
				exist_code.a_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
				exist_code.c_time = exist_code.a_time;
				exist_code.flag = 0;
				exist_code.prot_info = _code_msg_.get(exist_code.prot_code);
				
				if(mobiles) {
					exist_code.notify_mobiles = mobiles.join(',');
				}
				JcTemplate.INSTANCE().save(exist_code);		//保存
				
				if(mobiles) {
					//第一次则发送短信
					def prot_code = exist_code.prot_code;
					if(prot_code.equals('2100') || prot_code.equals('2200')) {
						//只有在 2100 和 2200 的情况下才发送短信
						def sms_content = '【嗨票】' + _code_msg_.get(exist_code.prot_code) + '：' + exist_code.toString();
						SmsService.sendMessage(mobiles, sms_content);
					}
				}
			} else {
				//只更新时间
				exist_code.c_time = new Timestamp(Calendar.getInstance().getTimeInMillis());
				JcTemplate.INSTANCE().update(exist_code);
			}
		} catch(ex) {
			ex.printStackTrace();
			Logger.error('update info ex:', ex);
		}
	}

//	def static String sendMessage(String[] mobiles, String content) {
//		long code = 0;
//		String Info = null;
//		for(String mobile : mobiles){
//			String userName = "jiuaiyoupin";
//			String passWord = "jiuaiyoupin123";
//			String pwd = MD5Util.getStringMD5(userName + MD5Util.getStringMD5(passWord));
//			String HttpURL = "http://118.178.228.184:9001/";
//			String result = "";
//			OutputStreamWriter out;
//			BufferedReader export;
//			StringBuilder params = new StringBuilder();
//			params.append("username=").append(userName)
//					.append("&password=").append(pwd)
//					.append("&mobile=").append(mobile)
//					.append("&content=").append(content)
//					.append("&ext=").append("");
//			try {
//				URL realUrl = new URL(HttpURL + "smsSend.do");
//				URLConnection conn = realUrl.openConnection();
//				conn.setRequestProperty("accept", "*/*");
//				conn.setRequestProperty("connection", "Keep-Alive");
//				conn.setDoOutput(true);
//				conn.setDoInput(true);
//				out = new OutputStreamWriter(conn.getOutputStream(), "UTF8");
//				out.write(params.toString());
//				out.flush();
//
//				export = new BufferedReader(new InputStreamReader(
//						conn.getInputStream(), "UTF8"));
//				String line = "";
//				while ((line = export.readLine()) != null) {
//					result += line + "\n";
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			//结果
//			String strCode = result.split("\n")[0];
//			code = Long.valueOf(strCode);
//			if (code > 0) {// 成功提交
//				Info = "发送成功";
//			} else if (code == 0) {
//				Info = "发送失败";
//			} else if (code == -1) { // 用户名密码不正确
//				Info = "用户名密码不正确";
//			} else if (code == -2) { // 必填选项为空
//				Info = "必填选项为空";
//			} else if (code == -3) { // 短信内容0个字节
//				Info = "短信内容0个字节";
//			} else if (code == -4) { // 0个有效号码
//				Info = "0个有效号码";
//			} else if (code == -5) { // 余额不够
//				Info = "余额不够";
//			} else if (code == -10) { // 用户被禁用
//				Info = "用户被禁用";
//			} else if (code == -11) { // 短信内容过长
//				Info = "短信内容过长";
//			} else if (code == -12) { // 用户无扩展权限
//				Info = "无扩展权限";
//			} else if (code == -13) { // IP地址校验错
//				Info = "IP校验错误";
//			} else if (code == -14) { // 内容解析异常
//				Info = "内容解析异常";
//			} else {
//				Info = "未知错误";
//			}
//			System.out.println("返回信息：" + Info + "--" + code + "--" + pwd);
//		}
//		return code+":"+Info;
//	}
}
