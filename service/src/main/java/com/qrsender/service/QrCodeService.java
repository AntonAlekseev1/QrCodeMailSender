package com.qrsender.service;

import com.qrsender.api.dal.IGenericDao;
import com.qrsender.api.dal.IQrCodeDao;
import com.qrsender.model.QrCode;
import org.springframework.beans.factory.annotation.Autowired;

public class QrCodeService extends AbstractService<QrCode, Long> {

    private final IQrCodeDao qrCodeDao;

    @Autowired
    public QrCodeService(IQrCodeDao qrCodeDao) {
        this.qrCodeDao = qrCodeDao;
    }

    @Override
    public IGenericDao<QrCode, Long> getDao() {
        return qrCodeDao;
    }
}
