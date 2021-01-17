package com.egg8.common.function;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author  : 김재일
 * @since   : 2021.01.17
 * @pre     : 당일 날짜 포멧 함수
 * */
public class GetDayFormat {
    SimpleDateFormat    sf;
    Date                dt;
    String              rt;

    private String MakeToday(){
        sf      = new SimpleDateFormat("yyyyMMdd");
        dt      = new Date();
        rt      = sf.format(dt);
        return rt;
    }
}
