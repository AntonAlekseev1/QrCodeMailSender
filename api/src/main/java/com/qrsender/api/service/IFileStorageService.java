package com.qrsender.api.service;

import com.qrsender.model.FileStorage;

import java.io.IOException;

public interface IFileStorageService extends IGenericService<FileStorage, Long> {

    Long createFileStorage(String qrCodeText, int size, String fileType) throws IOException;
}
