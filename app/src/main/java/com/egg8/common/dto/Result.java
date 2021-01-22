package com.egg8.common.dto;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class Result {
    @SerializedName("RESULT")
    private String result;

    @SerializedName("USER_STATUS")
    private String status;
}
