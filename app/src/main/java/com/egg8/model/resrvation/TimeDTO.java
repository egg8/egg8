package com.egg8.model.resrvation;

import lombok.Data;

/**
 * @author  : 김재일
 * @since   : 2021.01.17
 * @pre     : TimeDTO
 * */
@Data
public class TimeDTO {
    private int strTime;
    private int endTime;
    private int block;
}
