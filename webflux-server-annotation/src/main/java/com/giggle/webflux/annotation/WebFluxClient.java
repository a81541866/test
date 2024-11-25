package com.giggle.webflux.annotation;

import java.lang.annotation.*;

/**
 * @author guozichen
 * @ClassName:
 * @Description: webflux 远程调用 类似FeignClient
 * @date 2020/11/30 12:35
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebFluxClient {

    /**
     * 注册的应用名，有注册中心的时候
     * @return
     */
    String value() default "";
}
