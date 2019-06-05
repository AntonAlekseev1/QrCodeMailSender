package com.qrsender.controller.response;

import com.fasterxml.jackson.annotation.JsonValue;

public class Response {

    private ResponseStatus status;
    private Object data;
    private ResponseError error;

    public Response(ResponseStatus status) {
        this.status = status;
    }

    public Response(Object data) {
        this.status = ResponseStatus.SUCCESS;
        this.data = data;
    }

    public Response(ResponseError error) {
        this.status = ResponseStatus.ERROR;
        this.error = error;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ResponseError getError() {
        return error;
    }

    public void setError(ResponseError error) {
        this.error = error;
    }

    public enum ResponseStatus {
        SUCCESS("Success"), ERROR("Error");

        private String name;

        ResponseStatus(String name){
            this.name = name;
        }

        @JsonValue
        public String getName() {
            return name;
        }
    }
}
