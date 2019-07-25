package com.qrsender.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.qrsender.api.dal.IFileStorageDao;
import com.qrsender.api.dal.IGenericDao;
import com.qrsender.api.service.IFileStorageService;
import com.qrsender.model.FileStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@Slf4j
public class FileStorageService extends AbstractService<FileStorage, Long> implements IFileStorageService {

    private final IFileStorageDao fileStorageDao;

    @Autowired
    public FileStorageService( IFileStorageDao fileStorageDao) {
        this.fileStorageDao = fileStorageDao;
    }

    @Override
    public Long createFileStorage(String qrCodeText, int size, String fileType) throws IOException {
        FileStorage storage = new FileStorage();
        storage.setCreationDate(LocalDate.now());
        storage.setFile(generateQrCodeImage(qrCodeText, size, fileType));
        return fileStorageDao.save(storage);
    }

    /**
     * This method delete images from file storage that were created more than two days ago.
     * The method starts automatically every 'fixedDelay' milliseconds.
     */
    @Scheduled(fixedDelay = 86400000, initialDelay = 10000) //start task a day
    public void deleteOldImages() {
        List<FileStorage> oldImages = fileStorageDao.getFilesOlderThenDate(LocalDate.now().minusDays(1));
        oldImages.forEach(fileStorageDao::delete);
    }

    @Override
    public IGenericDao<FileStorage, Long> getDao() {
        return fileStorageDao;
    }

    private byte [] generateQrCodeImage(String qrCodeText, int size, String fileType)
            throws IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()){
            Map<EncodeHintType, Object> hints = new Hashtable<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            BitMatrix biteMatrix = qrCodeWriter.encode(qrCodeText, BarcodeFormat.QR_CODE, size, size, hints);
            MatrixToImageWriter.writeToStream(biteMatrix, fileType, bos);
            return bos.toByteArray();
        } catch (WriterException e) {
            log.warn("Write exception, {}", e.getMessage(), e);
        }
        return null;
    }
}
