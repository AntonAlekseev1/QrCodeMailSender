package com.qrsender.controller;

import com.qrsender.api.service.IEmailService;
import com.qrsender.api.service.IFileStorageService;
import com.qrsender.api.service.IQrCodeService;
import com.qrsender.controller.response.Response;
import com.qrsender.controller.response.ResponseError;
import com.qrsender.model.QrCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/email")
@Slf4j
@PropertySource("classpath:qrCodeApp.properties")
public class EmailController {

    private final IEmailService emailService;
    private final IQrCodeService qrCodeService;
    private final IFileStorageService fileStorageService;

    @Value("${app.name}")
    private String APP_NAME;
    @Value("${app.home.page.url}")
    private String APP_HOME_PAGE_URL;

    public EmailController(IEmailService emailService, IQrCodeService qrCodeService, IFileStorageService fileStorageService) {
        this.emailService = emailService;
        this.qrCodeService = qrCodeService;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/send")
    public Response sendEmail(@RequestParam("toEmail") String toEmail, @RequestParam("qrCodeId") Long qrCodeId) {
        try {
            Map<String, Object> variables = new HashMap<>();
            variables.put("appName", APP_NAME);
            QrCode qrCode = qrCodeService.getById(qrCodeId);
            variables.put("qrImage", fileStorageService.getById(qrCode.getFileId()).getFile());
            variables.put("qrMessage", qrCode.getMessage().getMessage());
            variables.put("appUrl", APP_HOME_PAGE_URL);
            emailService.sendEmailUsingTemplate(toEmail, "email_template", "QR codes generation service", variables);
            emailService.createAndSave(toEmail, qrCode.getFileId());
            return new Response("message send to " + toEmail);
        }catch (Exception e) {
            log.warn("Email not sent to {}", toEmail, e);
            ResponseError error = new ResponseError(ResponseError.ErrorType.ILLEGAL_ARGUMENTS, "Email not sent to " + toEmail);
            return new Response(error);
        }

    }
}
