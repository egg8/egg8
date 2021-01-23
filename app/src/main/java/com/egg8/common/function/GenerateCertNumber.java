package com.egg8.common.function;

import java.util.Random;
//인증번호 난수발생 메소드
public class GenerateCertNumber {
    public static int certNumLength=4;

    public static String CreatePhoneKey() {
        Random random = new Random(System.currentTimeMillis());

        int range=(int)Math.pow(10,certNumLength);
        int trim=(int)Math.pow(10,certNumLength-1);
        int result=random.nextInt(range)+trim;

        if(result>range){
            result=result-trim;
        }

        return String.valueOf(result);
    }
}