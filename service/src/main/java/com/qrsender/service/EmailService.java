package com.qrsender.service;

import com.qrsender.api.dal.IEmailDao;
import com.qrsender.api.dal.IGenericDao;
import com.qrsender.api.service.IEmailService;
import com.qrsender.model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmailService extends AbstractService<Email, Long> implements IEmailService {

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
