package com.egg8.common.function;

import com.egg8.model.string.ButtonDTO;

/**
 * @author  : 김재일
 * @since   : 2021.01.17
 * @pre     : 휴대폰번호 하이픈 제거 함수
 * */
public class RemoveHyphen {
    String[]    arr;
    String      rt;
    public String setRemoveHyphen(String num){
        arr = num.split("-");

        for(int i = 0; i <= arr.length; i++) {
            rt += arr[i];
        }
        return rt;
    }



}
