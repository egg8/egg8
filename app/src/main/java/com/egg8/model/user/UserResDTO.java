package com.egg8.model.user;

import com.egg8.model.resrvation.MenuDTO;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class UserResDTO {
    public String SUPP_CODE;
    public String USER_CODE;
    public String RES_IN_DATE;
    public String RES_IN_NAME;
    public String RES_IN_STR_TIME;
    public String RES_IN_END_TIME;

    private List<UserResDTO> result = null;

}
