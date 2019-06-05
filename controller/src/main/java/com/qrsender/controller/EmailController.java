package com.qrsender.controller;

import com.qrsender.api.service.IEmailService;
import com.qrsender.controller.response.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/email")
public class EmailController {

    private final IEmailService emailService;

    public EmailController(IEmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public Response sendEmail(@RequestParam("email") String email, @RequestParam("file") MultipartFile file) {
        return new Response("not work now");
    }
}
