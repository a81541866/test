package com.giggle.webflux.invoke.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author guozichen
 * @ClassName:
 * @Description: (WebFluxClient配置)
 * @date 2020/12/8 10:44
 */
@ConfigurationProperties(
        prefix = "webflux.invoke"
)
public class WebFluxClientProperties {

    @Value("${maxTotal:50}")
    private int maxTotal;
}
