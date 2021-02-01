package com.egg8.common.dto.Res;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class ResResult {
    @SerializedName("responseBody")
    @Expose
    List<ResDTO> result = null;
}
