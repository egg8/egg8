package com.egg8.common.function;

import com.egg8.model.resrvation.TimeDTO;
import com.egg8.model.resrvation.ResDTO;
import com.egg8.model.string.ButtonDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  : 김재일
 * @since   : 2021.01.17
 * @pre     : 예약 버튼 Making 함수
 * */
public class MakeTimeButton {
    ArrayList<ButtonDTO> btnList;
    MakeTime mk;
    List<TimeDTO> listTime;

    public ArrayList<ButtonDTO> MakeBtn(ResDTO dto){
        btnList = new ArrayList<>();
        mk = new MakeTime();
        listTime = mk.MakeTimeToInt(dto.getRES_STR_TIME(), dto.getRES_END_TIME());

        for(int i=0; i<=dto.getBLOCK(); i++) {
            ButtonDTO btn = new ButtonDTO();
            if(dto.getSTR_TIME()+i == dto.getREG_STR_TIME()) {
                btn.setReg("점심시간");
            } else if(dto.getSTR_TIME()+i == listTime.get(i).getStrTime()) {
                btn.setImp("예약완료");
            } else {
                btn.setTime(dto.getSTR_TIME()+i+":00");
            }
            btnList.add(btn);
        }
        return btnList;
    }
}
