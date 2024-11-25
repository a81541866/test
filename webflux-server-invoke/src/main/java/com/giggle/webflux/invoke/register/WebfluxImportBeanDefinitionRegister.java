package com.giggle.webflux.invoke.register;

import com.giggle.webflux.invoke.annotation.EnableWebfluxClientInvoke;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * @author guozichen
 * @ClassName:
 * @Description: (webflux导入bean)
 * @date 2020/12/8 14:29
 */
@Slf4j
public class WebfluxImportBeanDefinitionRegister implements ImportBeanDefinitionRegistrar {

    private static String componentScan;

    private final static String COMPONENT_SCAN = "componentScan";


    /**
     * 获取到包扫描内容
     * @param importingClassMetadata
     * @param registry
     */
    @Override
    public void registerBeanDefinitions(
            AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        Map<String, Object> map = importingClassMetadata.getAnnotationAttributes(EnableWebfluxClientInvoke.class.getName());
        String componentScan = (String) map.get(COMPONENT_SCAN);
        log.info("webflux包扫描："+componentScan);
        WebfluxImportBeanDefinitionRegister.componentScan = componentScan;
    }


    public static String getComponentScan() {
        return componentScan;
    }

    public static void setComponentScan(String componentScan) {
        WebfluxImportBeanDefinitionRegister.componentScan = componentScan;
    }
}
