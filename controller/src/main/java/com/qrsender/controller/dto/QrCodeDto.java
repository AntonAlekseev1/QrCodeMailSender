package com.qrsender.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QrCodeDto {

    private String message;
    private int fileSize;
}
