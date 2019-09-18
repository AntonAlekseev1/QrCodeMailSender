package com.qrsender.controller;

import com.qrsender.api.exception.ExceptionType;
import com.qrsender.api.exception.QrCodeException;
import com.qrsender.api.service.IFileStorageService;
import com.qrsender.api.service.IQrCodeService;
import com.qrsender.controller.dto.QrCodeDto;
import com.qrsender.controller.response.Response;
import com.qrsender.controller.response.ResponseError;
import com.qrsender.model.FileStorage;
import com.qrsender.model.QrCode;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
            log.info("qr code created, id {}", qrCodeId);
            return new Response(qrCodeId);
        } catch (IOException e) {
            log.warn("Can't create qr code, exception: {}", e.getMessage());
            ResponseError error = new ResponseError(ExceptionType.QR_CODE_EXCEPTION, "Can't create qr code, exception:" + e.getMessage());
            return new Response(error);
        }
    }

    @GetMapping("/get-qr-image/{id}")
    public void getQrImage(@PathVariable Long id, HttpServletResponse response) throws IOException {
        try {
            QrCode qrCode = qrCodeService.getById(id);
            if (qrCode != null) {
                Long fileId = qrCode.getFileId();
                FileStorage storage = fileStorageService.getById(fileId);
                if (storage != null) {
                    byte[] qrImage = storage.getFile();
                    response.setContentType("image/jpeg");
                    response.getOutputStream().write(qrImage);
                    log.info("success get qr image, qr code id {}, file id {}", id, fileId);
                    return;
                }
                response.setContentType("application/json");
                response.sendError(404, "Image not found");
                return;
            }
            response.setContentType("application/json");
            response.sendError(404, "QrCode not found");
        } catch (Exception e) {
            response.setContentType("application/json");
            response.sendError(400, e.getMessage());
            log.warn("Qr code with id {} don't exist", id, e);
        }
    }

    @PostMapping("/read")
    public Response readeQrCode(@RequestParam("file") MultipartFile file) {
        try {
            return new Response(qrCodeService.decodeQrCode(file.getBytes()));
        } catch (QrCodeException e) {
            log.warn(e.getMessage());
            ResponseError error = new ResponseError(ExceptionType.QR_CODE_EXCEPTION, e.getMessage());
            return new Response(error);
        } catch (IOException ex) {
            log.warn(ex.getMessage());
            ResponseError error = new ResponseError(ExceptionType.UNEXPECTED_ERRORS, ex.getMessage());
            return new Response(error);
        }
    }
}
