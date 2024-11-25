package com.giggle.webflux.invoke.annotation;

import com.giggle.webflux.invoke.handler.FluxHandlerBeanDefinitionRegistry;
import com.giggle.webflux.invoke.register.WebfluxImportBeanDefinitionRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author guozichen
 * @ClassName:
 * @Description: (自动开启WebfluxClientInvoke)
 * @date 2020/12/8 10:53
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({WebfluxImportBeanDefinitionRegister.class,FluxHandlerBeanDefinitionRegistry.class})
public @interface EnableWebfluxClientInvoke {

    /**
     * 包扫描路径，扫描@WebFluxClient
     * @return
     */
    String componentScan()  default "";
}
