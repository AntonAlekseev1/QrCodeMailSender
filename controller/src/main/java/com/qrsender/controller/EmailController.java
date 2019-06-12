package com.qrsender.controller;

import com.qrsender.api.service.IEmailService;
import com.qrsender.api.service.IFileStorageService;
import com.qrsender.api.service.IQrCodeService;
import com.qrsender.controller.response.Response;
import com.qrsender.model.QrCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/email")
@Slf4j
public class EmailController {

    private static final String APP_NAME = "QR code sender";
    private static final String APP_HOME_PAGE_URL = "http://localhoct:4220";
    private final IEmailService emailService;
    private final IQrCodeService qrCodeService;
    private final IFileStorageService fileStorageService;

    public EmailController(IEmailService emailService, IQrCodeService qrCodeService, IFileStorageService fileStorageService) {
        this.emailService = emailService;
        this.qrCodeService = qrCodeService;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/send")
    public Response sendEmail(@RequestParam("toEmail") String toEmail, @RequestParam("qrCodeId") Long qrCodeId) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("appName", APP_NAME);
        QrCode qrCode = qrCodeService.getById(qrCodeId);
        variables.put("qrImage", fileStorageService.getById(qrCode.getFileId()).getFile());
        variables.put("qrMessage", qrCode.getMessage().getMessage());
        variables.put("appUrl", APP_HOME_PAGE_URL);
        emailService.sendEmailUsingTemplate(toEmail, "email_template", "test qr code service", variables);
        emailService.createAndSave(toEmail, qrCode.getFileId());
        return new Response("message send to " + toEmail);
    }
}
