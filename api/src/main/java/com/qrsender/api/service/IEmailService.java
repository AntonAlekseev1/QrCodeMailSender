package com.qrsender.api.service;

import com.qrsender.model.Email;

import java.util.Map;

public interface IEmailService extends IGenericService<Email, Long> {

    void sendEmailUsingTemplate(String toEmail, String templateName, String subject, Map<String, Object> variables) throws Exception;

    void createAndSave(String toEmail, Long fileId);
}
