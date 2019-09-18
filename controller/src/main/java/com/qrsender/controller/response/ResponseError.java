package com.qrsender.controller.response;

import com.qrsender.api.exception.ExceptionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseError {

    private ExceptionType exceptionType;
    private Object data;

    public ResponseError(ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    public ResponseError(ExceptionType exceptionType, Object data) {
        this.exceptionType = exceptionType;
        this.data = data;
    }
}
