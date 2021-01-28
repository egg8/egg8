package com.egg8.common.dto.surgery;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;

@Data
public class SurgeryResult {
    @SerializedName("result")
    List<SurgeryDTO> dto;
}
