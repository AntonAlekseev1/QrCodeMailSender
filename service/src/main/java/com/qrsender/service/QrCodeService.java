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
    public IGenericDao<QrCode, Long> getDao() {
        return qrCodeDao;
    }

    private String decodeQrCode(byte[] image) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(image);
        BufferedImage bufferedImage = ImageIO.read(bis);
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        try {
            Result result = new MultiFormatReader().decode(bitmap);
            return result.getText();
        } catch (NotFoundException e) {
            System.out.println("There is no QR code in the image"); // todo change to logger
            return null;
        }
    }
}
