package com.egg8.model.string;

import lombok.Data;

@Data
public class ButtonDTO {
    private String btnName;
    private boolean enabled = false;
    private String btnColor;
}
