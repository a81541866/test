package com.giggle.webflux.invoke.handler;

import com.giggle.webflux.invoke.rest.WebClientRestHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author guozichen
 * @ClassName:
 * @Description: (FactoryBean)
 * @date 2020/11/30 13:57
 */
@Slf4j
@Data
public class HandlerInterfaceFactoryBean<T> implements FactoryBean<T> {

    private Class<T> interfaceClass;

    @Override
    public T getObject() throws Exception {
        //生成远程调用对象
        WebClientRestHandler webClientRestHandler = new WebClientRestHandler();
        //生成调用处理器
        InvocationHandler handler = new  DynamicProxyBeanFactory(interfaceClass,webClientRestHandler);
        //返回代理对象
        return (T)Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class[] {interfaceClass},handler);
    }

    @Override
    public Class<?> getObjectType() {
        return interfaceClass;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
