package com.giggle.webflux.invoke.bean;

import lombok.Data;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @author guozichen
 * @ClassName:
 * @Description: (Flux方法解析bean)
 * @date 2020/11/30 16:07
 */
@Data
public class FluxPraseMethodBean {

    /**
     * 映射地址
     */
    private String url;

    /**
     * http请求类型
     */
    private HttpMethod requestMethod;

    /**
     * 请求url上的参数
     */
    private Map<String,Object> params;


    /**
     * RequestBody数据
     */
    private Mono<?> body;


    /**
     * body类型
     */
    private Class<?> bodyType;

    /**
     * 是否返回flux
     */
    private Boolean returnFlux = false;

    /**
     * 返回值类型
     */
    private Class<?> returnElementType;
}
