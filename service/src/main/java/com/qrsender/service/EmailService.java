package com.qrsender.service;

import com.qrsender.api.dal.IEmailDao;
import com.qrsender.api.dal.IGenericDao;
import com.qrsender.model.Email;
import org.springframework.beans.factory.annotation.Autowired;

public class EmailService extends AbstractService<Email, Long> {

    private final IEmailDao emailDao;

    @Autowired
    public EmailService(IEmailDao emailDao) {
        this.emailDao = emailDao;
    }

    @Override
    public IGenericDao<Email, Long> getDao() {
        return emailDao;
    }
}
