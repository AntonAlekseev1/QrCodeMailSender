package com.qrsender.dal;

import com.qrsender.api.dal.IEmailDao;
import com.qrsender.model.Email;
import org.springframework.stereotype.Repository;

@Repository
public class EmailDao extends AbstractDao<Email, Long> implements IEmailDao {

    public EmailDao() {
        super(Email.class);
    }
}
