package com.jeancoder.ticketingsys.ready.entity

import com.jeancoder.jdbc.bean.JCBean
import com.jeancoder.jdbc.bean.JCID

@JCBean(tbname = 'data_tc_ss_cinema_hall')
class CinemaHall {

    @JCID
    BigInteger id;

    BigInteger store_id;

    String hall_id;

    String hall_name;

    Integer seat_num;

    String hall_type;

    String audio_type;

    String screen_code;

    String cine_hall_id;
}
