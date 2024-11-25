package com.giggle.webflux.server.common;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException{
    private int code; // 异常码

    private String msg; // 异常信息

    public BusinessException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
