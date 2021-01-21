package com.egg8.common.function;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author  : 김재일
 * @since   : 2021.01.17
 * @pre     : 당일 날짜 포멧 함수
 * */
public class GetDayFormat {
    public static String MakeToday(int year, int month, int dayOfMonth){
        String m,rs;
        if(month >= 10) {
            m = Integer.toString(month);
        } else {
            m = "0"+ month;
        }
        rs = year+m+dayOfMonth;
        return rs;
    }
}
