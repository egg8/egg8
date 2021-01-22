package com.egg8.model.user;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
/**
 * 2021-01-22
 * 안성엽
 * 유저가 입력한 값을 받는다.
 **/

@Data
public class UserDTO {
    @SerializedName("USER_ID")
    public String user_id;
    @SerializedName("USER_CODE")
    public String user_code;
    @SerializedName("USER_PWD")
    public String user_pwd;
    @SerializedName("USER_NAME")
    public String user_name;
    @SerializedName("USER_TEL")
    public String user_tel;
    @SerializedName("USER_STATUS")
    public String user_status;
    @SerializedName("RESULT")
    public String result;
}
