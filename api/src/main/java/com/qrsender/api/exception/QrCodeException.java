package com.qrsender.api.exception;

public class QrCodeException extends Exception {

    public QrCodeException(String message) {
        super(message);
    }

    public QrCodeException(String message, Throwable cause) {
        super(message, cause);
    }
}
