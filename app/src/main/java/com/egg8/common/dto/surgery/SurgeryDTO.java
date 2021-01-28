package com.egg8.common.dto.surgery;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class SurgeryDTO {
    @SerializedName("SUG_IDX")
    private String sug_idx;
    @SerializedName("SUG_NAME")
    private String sug_name;
    @SerializedName("SUG_PRICE")
    private String sug_price;
    @SerializedName("SUPP_CODE")
    private String supp_code;
    @SerializedName("RESULT")
    private String result;
}
