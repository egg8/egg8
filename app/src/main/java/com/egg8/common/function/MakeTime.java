package com.egg8.common.function;


import com.egg8.model.resrvation.TimeDTO;
import java.util.ArrayList;
import java.util.List;

/**
 * @author  : 김재일
 * @since   : 2021.01.17
 * @pre     : 당일 예약 시간 Making 함수
 * */
public class MakeTime {
    String[]    sTime;
    String[]    eTime;

    public List<TimeDTO> MakeTimeToInt(String strTime, String endTime){
        sTime = strTime.split("^");
        eTime = endTime.split("^");
        List<TimeDTO> dtoList = new ArrayList<>();

        for(int i = 0; i <= sTime.length; i++) {
            TimeDTO dto = new TimeDTO();
            dto.setStrTime(Integer.parseInt(sTime[i]));
            dto.setEndTime(Integer.parseInt(eTime[i]));
            dtoList.add(dto);
        }
        return dtoList;
    }
}
