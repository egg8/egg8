package com.egg8.ui.user;

import java.util.Random;
//인증번호 난수발생 메소드
public class GenerateCertNumber {
    private int certNumLength=4;

    public String excuteGenerate() {
        Random random = new Random(System.currentTimeMillis());

        int range=(int)Math.pow(10,certNumLength);
        int trim=(int)Math.pow(10,certNumLength-1);
        int result=random.nextInt(range)+trim;

        if(result>range){
            result=result-trim;
        }

        return String.valueOf(result);
    }
    public int getCertNumLength() {
        return certNumLength;
    }
    public  void setCertNumLength(int certNumLength) {
        this.certNumLength=certNumLength;
    }
    public static void main(String[] args) {
        GenerateCertNumber ge=new GenerateCertNumber();
        ge.setCertNumLength(5);
        System.out.println(ge.excuteGenerate());
    }
}