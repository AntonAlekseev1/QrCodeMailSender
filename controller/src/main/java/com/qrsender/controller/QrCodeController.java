package com.qrsender.controller;

import com.qrsender.api.service.IFileStorageService;
import com.qrsender.api.service.IQrCodeService;
import com.qrsender.controller.dto.QrCodeDto;
import com.qrsender.controller.response.Response;
import com.qrsender.controller.response.ResponseError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/qr-code")
public class QrCodeController {

    private static final String FILE_TYPE = "jpeg";
    private final IQrCodeService qrCodeService;
    private final IFileStorageService fileStorageService;

    @Autowired
    public QrCodeController(IQrCodeService qrCodeService, IFileStorageService fileStorageService) {
        this.qrCodeService = qrCodeService;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/create")
    public Response createQrCode(@RequestBody QrCodeDto dto) {
        try {
            Long qrCodeId = qrCodeService.createQrCode(dto.getMessage(), dto.getFileSize(), FILE_TYPE);
            return new Response(qrCodeId);
        } catch (IOException e) {
            // todo log exception
            ResponseError error = new ResponseError(ResponseError.ErrorType.UNEXPECTED_ERRORS, e.getMessage());
            return new Response(error);
        }
    }

    @GetMapping("/get-qr-image/{id}")
    public void getQrImage(@PathVariable Long id, HttpServletResponse response) {
        byte [] qrImage = fileStorageService.getById(id).getFile();
        try {
            response.setContentType("image/jpeg");
            response.getOutputStream().write(qrImage);
        } catch (IOException e) {
            // todo log exception
        }
    }

    @PostMapping("/read")
    public Response readeQrCode(@RequestParam("file") MultipartFile file) {
        try {
            return new Response(qrCodeService.decodeQrCode(file.getBytes()));
        } catch (Exception e) {
            // todo log exception
            ResponseError error = new ResponseError(ResponseError.ErrorType.UNEXPECTED_ERRORS, e.getMessage());
            return new Response(error);
        }
    }
}
