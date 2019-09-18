package com.qrsender.api.exception;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ExceptionType {


    ILLEGAL_ARGUMENTS("Illegal Arguments"),
    UNEXPECTED_ERRORS("UnexpectedErrors"),
    FORBIDDEN("Forbidden"),
    ACCESS_DENIED("AccessDenied"),
    FORBIDDEN_DELETE_ENTITY("Forbidden delete entity"),
    INVALID_EMAIL_DATA("InvalidEmailData"),
    QR_CODE_EXCEPTION("QrCodeException");

    private String name;

    ExceptionType(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }
}
