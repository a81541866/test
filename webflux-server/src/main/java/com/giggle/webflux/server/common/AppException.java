package com.giggle.webflux.server.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class AppException extends RuntimeException {
    private int code;
    private String message;
    private Map<String, Object> data = new HashMap<>();


    public AppException(int code) {
        this(code, "failed");
    }

    public AppException(String message) {
        this(ResultCode.ERROR, message);
    }

    public AppException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public AppException(int code, String message, Map<String, Object> data) {
        super(message);
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public AppException(ResultData result) {
        this(result.getCode(), result.getMessage(), result.getData());
    }
}
