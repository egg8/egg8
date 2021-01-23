package com.egg8.common.function;

import android.util.Log;

import com.egg8.model.resrvation.TimeDTO;
import com.egg8.model.resrvation.ResDTO;
import com.egg8.model.string.ButtonDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author  : 김재일
 * @since   : 2021.01.17
 * @pre     : 예약 버튼 Making 함수
 * */
public class MakeTimeButton {
    public static ArrayList<ButtonDTO> btnList;
    public static MakeTime mk;
    public static List<TimeDTO> listTime;
    public static  String[] Time;

    public static ArrayList<ButtonDTO> MakeTimeBtn(String time){
        Time = time.split(",");
        btnList = new ArrayList<>();
        for(int i = 0; i < Time.length; i++) {
            ButtonDTO dto = new ButtonDTO();
            dto.setBtnName(Time[i]);
            if(Time[i].equals("예약불가")){
                dto.setEnabled(false);
            } else if(Time[i].equals("점심시간")){
                dto.setEnabled(false);
            } else {
                dto.setEnabled(true);
            }

            btnList.add(dto);
        }
        return btnList;
    }

    public ArrayList<ButtonDTO> MakeBtn(ResDTO dto){
        btnList = new ArrayList<>();

        if (dto.getRES_OK() > 0) {
            mk = new MakeTime();
            listTime = mk.MakeTimeToInt(dto.getRES_STR_TIME(), dto.getRES_END_TIME());
            if(dto.getRES_OK() == 1) {
                for(int i=0; i<=dto.getBLOCK(); i++) {
                    ButtonDTO btn = new ButtonDTO();
                    if(dto.getSTR_TIME()+i == listTime.get(0).getStrTime()) {
                        btn.setBtnName("예약불가");
                    } else if(dto.getSTR_TIME()+i == dto.getREG_STR_TIME()) {
                        btn.setBtnName("휴게시간");
                    } else {
                        btn.setBtnName(dto.getSTR_TIME()+i+":00");
                    }
                    btnList.add(btn);
                }
            } else {
                try{
                    for(int i=0; i<=dto.getBLOCK(); i++){
                        ButtonDTO btn = new ButtonDTO();

                        if(dto.getSTR_TIME()+i != dto.getREG_STR_TIME()) {
                            btn.setBtnName(dto.getSTR_TIME()+i+":00");
                            if(dto.getSTR_TIME()+i == dto.getREG_STR_TIME()) {
                                btn.setBtnName("휴게시간");
                            }
                        } else {
                            for(int j=0; j<=i; j++) {
                                for(int k=0; k<dto.getRES_OK(); k++){
                                    if(dto.getSTR_TIME()+i == listTime.get(k).getStrTime()){
                                        btn.setBtnName("예약불가");
                                        if(k==dto.getRES_OK()-1){
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        btnList.add(btn);
                    }
                } catch (IndexOutOfBoundsException e) {
                    Log.d("ee", e.getMessage());
                }
            }
        } else {
            BaseBtn(dto);
        }
        return btnList;
    }

    public ArrayList<ButtonDTO> BaseBtn(ResDTO dto){
        btnList = new ArrayList<>();

        for(int i=0; i<=dto.getBLOCK(); i++) {
            ButtonDTO btn = new ButtonDTO();
            if(dto.getSTR_TIME()+i == dto.getREG_STR_TIME()) {
                btn.setBtnName("휴게시간");
            } else {
                btn.setBtnName(dto.getSTR_TIME()+i+":00");
            }
            btnList.add(btn);
        }
        return btnList;
    }
}
