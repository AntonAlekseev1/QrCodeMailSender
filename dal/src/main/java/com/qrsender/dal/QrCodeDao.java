package com.qrsender.dal;

import com.qrsender.api.dal.IQrCodeDao;
import com.qrsender.model.QrCode;
import org.springframework.stereotype.Repository;

@Repository
public class QrCodeDao extends AbstractDao<QrCode, Long> implements IQrCodeDao {

    public QrCodeDao() {
        super(QrCode.class);
    }
}
