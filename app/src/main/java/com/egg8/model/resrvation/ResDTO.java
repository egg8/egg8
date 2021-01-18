package com.egg8.model.resrvation;

import lombok.Data;

@Data
public class ResDTO {
    private int STR_TIME;
    private int END_TIME;
    private int REG_STR_TIME;
    private int REG_END_TIME;
    private int BLOCK;
    private String RES_STR_TIME;
    private String RES_END_TIME;
    private int RES_OK;
}
