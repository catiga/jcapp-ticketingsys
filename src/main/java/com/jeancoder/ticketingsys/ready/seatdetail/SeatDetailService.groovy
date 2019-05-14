package com.jeancoder.ticketingsys.ready.seatdetail

import com.jeancoder.app.sdk.source.DatabaseSource
import com.jeancoder.core.power.DatabasePower
import com.jeancoder.ticketingsys.ready.seatdetail.entity.HallInformation
import com.jeancoder.ticketingsys.ready.seatdetail.entity.SeatingDetails
import com.jeancoder.ticketingsys.ready.seatdetail.seatdto.SeatDetailDto

class SeatDetailService {
	public static final SeatDetailService INSTANCE = new SeatDetailService();
	
	public List<HallInformation> get_hall_by_hall_id(def hall_id,def cinema_id) {
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		String sql = "SELECT * FROM data_hall_information WHERE hall_id = ? and cinema_id = ? and flag!=?"
		return dbpower.doQueryList(HallInformation.class, sql,hall_id,cinema_id,-1);
	}
	
	public List<SeatingDetails> get_seat_detail(def dhi_id) {
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		String sql = "SELECT * FROM data_seating_details WHERE dhi_id = ? and flag!=?"
		return dbpower.doQueryList(SeatingDetails.class, sql,dhi_id,-1);
	}
	
	public void add_seat_detail(SeatingDetails sd){
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		String sql = "INSERT INTO data_seating_details(dhi_id,seat_no,seat_gr,seat_gc,seat_status,a_time) VALUES(?,?,?,?,?,?)"
		dbpower.doInsert(sql, sd.dhi_id,sd.seat_no,sd.seat_gr,sd.seat_gc,sd.seat_status,sd.a_time)
	}
	
	public void add_hall(HallInformation hif){
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		String sql = "INSERT INTO data_hall_information(hall_id,cinema_id) VALUES(?,?)"
		dbpower.doInsert(sql, hif.hall_id,hif.cinema_id)
	}
	
	public void update_seat_detail(def dhi_id,String seat_no){
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		String sql = "update data_seating_details set flag = -1 WHERE seat_no = ? and dhi_id = ?"
		dbpower.doUpdate(sql, seat_no,dhi_id)
	}
	
	public List<SeatDetailDto> get_seat_detailbyseat(def dhi_id,def seat_gr){
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		String sql = "SELECT * FROM data_seating_details WHERE dhi_id = ? and seat_gr = ? and flag!=? order by seat_gc asc"
		return dbpower.doQueryList(SeatDetailDto.class, sql,dhi_id,seat_gr,-1);
	}
	
	public void update_cv_detail_bycv(def hall_id,def seat_no,def seat_type,def seat_type1){
		if(seat_type1.equals("common_type")){
			seat_type1 = SeatingsysConstant.seat_type_common;
		}else{
			seat_type1 = SeatingsysConstant.seat_type_Vip;
		}
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		String sql = "update data_seating_details set seat_type = ? WHERE seat_no = ? and hall_id = ? and flag!=-1"
		dbpower.doUpdate(sql, seat_type1,seat_no,hall_id)
		if(seat_type.equals("101")){
			dbpower.doUpdate(sql, seat_type1,Integer.parseInt(seat_no)+1,hall_id);
		}
	}
	
	public List<SeatingDetails> get_seat_detail_byseatno(def hall_id,def seat_no,def seat_row){
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		String sql = "SELECT * FROM data_seating_details WHERE hall_id = ? and seat_type!= ? and flag!= -1 and seat_status = 'danren' and seat_gr = ? and seat_no in(?,?) order by seat_gc asc"
		return dbpower.doQueryList(SeatingDetails.class, sql,hall_id,SeatingsysConstant.seat_type_pair,seat_row,Integer.parseInt(seat_no)-1,Integer.parseInt(seat_no)+1);
	}
	
	public void update_clv_detail_byl(def hall_id,def seat_no,def seat_nl,def seat_type1){
		if(seat_type1.equals("love_type")){
			seat_type1 = SeatingsysConstant.seat_type_pair;
		}
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		String sql = "update data_seating_details set seat_type = ? WHERE seat_no = ? and hall_id = ? and flag!=-1"
		dbpower.doUpdate(sql, seat_type1,seat_no,hall_id)
		dbpower.doUpdate(sql, seat_type1,seat_nl,hall_id)
	}
	public List<SeatTypeDato> get_seat_type(def id,def hall_id){
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		String sql = "select id,schema_name from ticket_price_schema where flag!=-1 and id in(select distinct schema_id from hall_schema where store_id = ? and hall_id = ? and flag!=-1)"
		return dbpower.doQueryList(SeatTypeDato.class, sql,id,hall_id);
	}
	
	public void update_ticket_type(def cinema_id,def hall_id,def ticket_type){
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		String sql = "update data_hall_information set ticket_type = ? WHERE cinema_id = ? and hall_id = ? and flag != -1"
		dbpower.doUpdate(sql, ticket_type,cinema_id,hall_id)
	}
	
	public void update_seat_type(def dhi_id,def seats_typesls){
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		String sql = "update data_seating_details set seat_type = ? WHERE seat_no = ? and dhi_id = ? and flag!=-1"
		String[] sss = seats_typesls.split("-");
		for(ss in sss){
			String[] s = ss.split(",");
			dbpower.doUpdate(sql, s[2],s[0],dhi_id)
		}
	}
	
	public HallInformation get_hall_information(){
		DatabasePower dbpower = DatabaseSource.getDatabasePower();
		String sql = "SELECT * FROM data_hall_information ORDER BY id DESC limit 1"
		return dbpower.doQueryUnique(HallInformation.class, sql);
		
	}
}
