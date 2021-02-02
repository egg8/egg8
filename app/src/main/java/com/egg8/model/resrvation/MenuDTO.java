package com.egg8.model.resrvation;


import java.util.List;

import lombok.Data;

@Data
public class MenuDTO {
    private String SUG_IDX;     //시술아이디
    private String SUPP_NAME;
    private String SUPP_CODE;   //매장코드
    private String SUG_NAME;    //시술명
    private String SUG_PRICE;   //시술가격
    private String CREATE_DATE; //생성일
    private String UPDATE_DATE; //수정일
    private String USE_YN;      //사용여부

    private List<MenuDTO> result = null;
}
