package com.giggle.webflux.invoke.bean;

import lombok.Data;

/**
 * @author guozichen
 * @ClassName:
 * @Description: (Flux类解析bean)
 * @date 2020/11/30 16:05
 */
@Data
public class FluxPraseClassBean {

    /**
     * 类上的地址前缀 即类上的@RequestMapping
     */
    private String urlPrefix;


    /**
     * 服务的请求地址 具体调用的远程地址 http://ip:port/
     */
    private String serviceUrl;
}
