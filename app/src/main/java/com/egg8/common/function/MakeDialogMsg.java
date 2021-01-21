package com.egg8.common.function;

public class MakeDialogMsg {
    public static String MakeMsg(String SuppName, String date, String time, String ResName){
        String msg;

        msg = SuppName+"\n"+date+" ["+time+"]\n["+ResName+"] 예약입니다.\n예약하시겠습니까?";
        return msg;
    }
}
