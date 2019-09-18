package com.qrsender.api.service;

import com.qrsender.api.exception.EmailException;
import com.qrsender.model.Email;

import java.util.Map;

public interface IEmailService extends IGenericService<Email, Long> {

    void sendEmailUsingTemplate(String toEmail, String templateName, String subject, Map<String, Object> variables) throws EmailException;

    void createAndSave(String toEmail, Long fileId);
}
