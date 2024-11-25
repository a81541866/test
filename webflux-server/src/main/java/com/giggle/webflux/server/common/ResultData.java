package com.giggle.webflux.server.common;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Data
public class ResultData {
    static private String DEFAULT_MESSAGE_SUCCESS = "success";
    static private String DEFAULT_MESSAGE_FAILED = "failed";

    private Integer code;
    private String message;
    private Map<String, Object> data = new HashMap<>();

    private ResultData() {
    }

    public static ResultData ok() {
        ResultData resultData = new ResultData();
        resultData.setCode(ResultCode.SUCCESS);
        resultData.setMessage(DEFAULT_MESSAGE_SUCCESS);
        return resultData;
    }

    public static ResultData ok(String message) {
        ResultData resultData = new ResultData();
        resultData.setCode(ResultCode.SUCCESS);
        resultData.setMessage(message);
        return resultData;
    }

    public ResultData put(String key, Object value) {
        data.put(key, value);
        return this;
    }

    public ResultData put(BindingResult result) {
        Map<String, Object> errorMap = new HashMap<>();
        for (FieldError err : result.getFieldErrors()) {
            errorMap.put(err.getField(), err.getDefaultMessage());
        }
        this.code = ResultCode.ERROR;
        this.message = "invalid params.";
        this.data = errorMap;
        return this;
    }

    public ResultData data(Object beanObj) {
        if(beanObj instanceof Collection<?>){
            this.put("list",beanObj);
        } else {
            Map<String, Object> beanToMap = BeanUtil.beanToMap(beanObj);
            this.setData(beanToMap);
        }
        return this;
    }

    public static ResultData fail() {
        return fail(ResultCode.ERROR, DEFAULT_MESSAGE_FAILED);
    }

    public static ResultData fail(int code) {
        return fail(code, DEFAULT_MESSAGE_FAILED);
    }

    public static ResultData fail(String message) {
        return fail(ResultCode.ERROR, message);
    }

    public static ResultData fail(int code, String message) {
        ResultData resultData = new ResultData();
        resultData.setMessage(message);
        resultData.setCode(code);
        return resultData;
    }

    public static ResultData fail(int code, String message, Map<String, Object> data) {
        ResultData resultData = new ResultData();
        resultData.setMessage(message);
        resultData.setCode(code);
        resultData.setData(data);
        return resultData;
    }
}
