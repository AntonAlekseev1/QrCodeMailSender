package com.qrsender.service;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.qrsender.api.dal.IGenericDao;
import com.qrsender.api.dal.IQrCodeDao;
import com.qrsender.api.service.IFileStorageService;
import com.qrsender.api.service.IQrCodeService;
import com.qrsender.model.FileStorage;
import com.qrsender.model.Message;
import com.qrsender.model.QrCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;

@Service
@Transactional
public class QrCodeService extends AbstractService<QrCode, Long> implements IQrCodeService {

    private final IQrCodeDao qrCodeDao;
    private final IFileStorageService fileStorageService;

    @Autowired
    public QrCodeService(IQrCodeDao qrCodeDao, IFileStorageService fileStorageService) {
        this.qrCodeDao = qrCodeDao;
        this.fileStorageService = fileStorageService;
    }

    @Override
    public Long createQrCode(String qrCodeText, int size, String fileType) throws IOException {
        Message message = new Message();
        message.setMessage(qrCodeText);
        message.setCreationDate(LocalDate.now());

        QrCode qrCode = new QrCode();
        qrCode.setFileId(fileStorageService.createFileStorage(qrCodeText, size, fileType));
        qrCode.setMessage(message);
        qrCode.setCreationDate(LocalDate.now());
        return qrCodeDao.save(qrCode);
    }

    @Override
    public QrCode createQrCode(byte[] image) throws Exception {
        String qrMessage = decodeQrCode(image);

        FileStorage fileStorage = new FileStorage();
        fileStorage.setCreationDate(LocalDate.now());
        fileStorage.setFile(image);
        Long fileStorageId = fileStorageService.save(fileStorage);

        Message message = new Message();
        message.setMessage(qrMessage);
        message.setCreationDate(LocalDate.now());

        QrCode qrCode = new QrCode();
        qrCode.setCreationDate(LocalDate.now());
        qrCode.setMessage(message);
        qrCode.setFileId(fileStorageId);
        save(qrCode);
        return qrCode;
    }

    @Override
    public String decodeQrCode(byte[] image) throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream(image);
        BufferedImage bufferedImage = ImageIO.read(bis);
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        try {
            Result result = new MultiFormatReader().decode(bitmap);
            return result.getText();
        } catch (NotFoundException e) {
            throw new Exception("There is no QR code in the image");
        }
    }

    @Override
    public IGenericDao<QrCode, Long> getDao() {
        return qrCodeDao;
    }
}
