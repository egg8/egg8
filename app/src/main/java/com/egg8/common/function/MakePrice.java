package com.egg8.common.function;

public class MakePrice {
    public static int Making(String price) {
        // 마지막 문자열 제거
        String subStr = price.substring(0,price.length()-1);
        // ',' 로 문자열을 자른다.
        String[] arrayStr = subStr.split(",");
        // 반복하며 저장할 공간 생성
        StringBuffer result = new StringBuffer();
        // 반복하며 문자열을 붙인다.
        for(int i=0; i < arrayStr.length; i++){
            result.append(arrayStr[i]);
        }
        return Integer.parseInt(result.toString());
    }
}
