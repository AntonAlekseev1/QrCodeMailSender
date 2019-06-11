package com.qrsender.service;

import com.qrsender.api.dal.IEmailDao;
import com.qrsender.api.dal.IGenericDao;
import com.qrsender.api.service.IEmailService;
import com.qrsender.model.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
@Slf4j
public class EmailService extends AbstractService<Email, Long> implements IEmailService {

    private static final String TEMPLATE_NAME_FORMAT ="%s.%s";
    private final IEmailDao emailDao;
    private final JavaMailSender emailSender;
    private final SpringTemplateEngine templateEngine;

    @Autowired
    public EmailService(IEmailDao emailDao, JavaMailSender emailSender, SpringTemplateEngine templateEngine) {
        this.emailDao = emailDao;
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendEmailUsingTemplate(String toEmail, String templateName, String subject, Map<String, Object> variables){
        MimeMessage message = emailSender.createMimeMessage();
        Multipart multiPart = new MimeMultipart("alternative");
        try {
            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(getTemplate(templateName, variables, "html"), "text/html; charset=utf-8");
            multiPart.addBodyPart(htmlPart);

            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(getTemplate(templateName, variables, "txt"), StandardCharsets.UTF_8.name());
            File attachment = getAttachment(((byte[]) variables.get("qrImage")));
            textPart.attachFile(attachment);
            multiPart.addBodyPart(textPart);

            message.setSubject(subject);
            message.setRecipients(Message.RecipientType.TO, toEmail);
            message.setContent(multiPart);
            emailSender.send(message);
            log.debug("Message successfully send to email {}", toEmail);
            if(!attachment.delete()) {
                log.warn("File {} not deleted", attachment.getName());
            }
        }catch (Exception e) {
            log.warn("Filed send message", e);
        }
    }

    @Override
    public void createAndSave(String toEmail, Long fileId) {
        Email email = new Email();
        email.setCreationDate(LocalDate.now());
        email.setRecipientAddress(toEmail);
        email.setFileId(fileId);
        save(email);
    }

    @Override
    public IGenericDao<Email, Long> getDao() {
        return emailDao;
    }

    private String getTemplate(String templateName, Map<String, Object> variables, String templateFormat) {
        Context context = new Context();
        context.setVariables(variables);
        return templateEngine.process(String.format(TEMPLATE_NAME_FORMAT, templateName, templateFormat), context);
    }

    private File getAttachment(byte[] image) {
        File outputFile = new File("images/" + UUID.randomUUID() +".jpeg");
        try(FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            outputStream.write(image);
            outputStream.flush();
        } catch (IOException e) {
            log.warn("Filed create attachment", e);
        }
        return outputFile;
    }
}
