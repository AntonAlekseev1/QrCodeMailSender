package com.qrsender.api.service;

import com.qrsender.model.QrCode;

import java.io.IOException;

public interface IQrCodeService extends IGenericService<QrCode, Long> {

    Long createQrCode(String qrCodeText, int size, String fileType) throws IOException;

    String decodeQrCode(byte[] image) throws IOException;
}
