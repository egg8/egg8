package com.egg8.model.supp;

import com.egg8.model.user.UserResDTO;

import java.util.List;

import lombok.Data;

@Data
public class SuppListDTO {
    String SUPP_CODE;
    String USER_CODE;
    String SUPP_NO;
    String SUPP_NAME;
    String CEO;
    String CATEGORY;
    String EMAIL;
    String PON_NO;
    String TEL_NO;
    String ADDR_STATE;
    String ADDR_STREET1;
    String ADDR_CITY;
    String ADDR_STREET2;
    String FULL_ADDRESS;
    String POST_CODE;
    String LATITUDE;
    String LONGITUDE;
    String OPEN_TIME;
    String CLOSED_TIME;
    String CREATE_DATE;
    String UPDATE_DATE;
    private List<SuppListDTO> result;



}
