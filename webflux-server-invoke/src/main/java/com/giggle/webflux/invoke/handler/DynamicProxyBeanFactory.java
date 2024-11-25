package com.giggle.webflux.invoke.handler;

import com.giggle.webflux.annotation.WebFluxClient;
import com.giggle.webflux.invoke.bean.FluxPraseClassBean;
import com.giggle.webflux.invoke.bean.FluxPraseMethodBean;
import com.giggle.webflux.invoke.rest.RestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author guozichen
 * @ClassName:
 * @Description: (动态代理)
 * @date 2020/11/30 14:01
 */
@Slf4j
public class DynamicProxyBeanFactory<T> implements InvocationHandler {

    private Class<T> interfaceType;

    private RestHandler restHandler;

    public DynamicProxyBeanFactory(Class<T> intefaceType,RestHandler restHandler) {
        this.interfaceType = interfaceType;
        this.restHandler = restHandler;

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //解析类
        FluxPraseClassBean fluxPraseClassBean = praseClass(method);
        //初始化调用对象
        restHandler.init(fluxPraseClassBean);
        //解析方法
        FluxPraseMethodBean fluxPraseMethodBean = praseMethod(method,args);

        return restHandler.invokeRest(fluxPraseMethodBean);
    }

    private FluxPraseMethodBean praseMethod(Method method,Object[] args) {
        FluxPraseMethodBean fluxPraseMethodBean = new FluxPraseMethodBean();

        /**解析url和url上的请求参数*/
        parseUrlAndMethod(method, fluxPraseMethodBean);

        /**解析参数和请求体*/
        praseParamAndBody(method, args, fluxPraseMethodBean);

        /**解析返回值和类型*/
        praseReturnType(method,fluxPraseMethodBean);

        return fluxPraseMethodBean;
    }

    /**
     * 解析返回值类型
     * @param method
     * @param fluxPraseMethodBean
     */
    private void praseReturnType(Method method, FluxPraseMethodBean fluxPraseMethodBean) {
        Class<?> returnType = method.getReturnType();
        if(returnType.isAssignableFrom(Flux.class)){
            fluxPraseMethodBean.setReturnFlux(true);
        }
        Type[]  types = ((ParameterizedType)method.getGenericReturnType()).getActualTypeArguments();
        if(types != null && types.length > 0){
            fluxPraseMethodBean.setReturnElementType((Class<?>) types[0]);
        }
    }

    /**
     * 解析参数和请求体
     * @param method
     * @param fluxPraseMethodBean
     */
    private void praseParamAndBody(Method method, Object[] args, FluxPraseMethodBean fluxPraseMethodBean) {
        Parameter[] parameters =  method.getParameters();
        if(parameters != null && parameters.length >0){
            Map<String,Object> params = new LinkedHashMap<>();
            for(int i = 0;i<parameters.length;i++){
                Parameter parameter =  parameters[i];
                //是否带了@PathVariable注解
                PathVariable pathVariable = parameter.getAnnotation(PathVariable.class);
                if(pathVariable != null){
                    params.put(pathVariable.value(),args[i]);
                }

                //是否带了@RequestBody注解
                RequestBody requestBody = parameter.getAnnotation(RequestBody.class);
                if(requestBody != null){
                    fluxPraseMethodBean.setBody((Mono<?>) args[i]);
                    Type[]  types = ((ParameterizedType)parameter.getParameterizedType()).getActualTypeArguments();
                    if(types != null && types.length > 0){
                        fluxPraseMethodBean.setBodyType((Class<?>) types[0]);
                    }
                }

            }
            fluxPraseMethodBean.setParams(params);
        }
    }

    /**
     * 解析方法的url和请求方式
     * @param method
     * @param fluxPraseMethodBean
     */
    private void parseUrlAndMethod(Method method, FluxPraseMethodBean fluxPraseMethodBean) {
        String REQUEST_METHOD = null;
        String methodUrl = "";
        for(Annotation annotation : method.getDeclaredAnnotations()){
            if(annotation instanceof RequestMapping){
                RequestMapping methodRequestMapping = (RequestMapping)annotation;
                if(methodRequestMapping.value() != null && methodRequestMapping.value().length >0){
                    methodUrl = methodRequestMapping.value()[0];
                }
                if(methodRequestMapping.method() != null && methodRequestMapping.method().length >0){
                    RequestMethod requestMethod = methodRequestMapping.method()[0];
                    REQUEST_METHOD = requestMethod.name();
                }
                fluxPraseMethodBean.setUrl(methodUrl);
                fluxPraseMethodBean.setRequestMethod(HttpMethod.valueOf(REQUEST_METHOD));
            }

            if(annotation instanceof GetMapping){
                GetMapping methodRequestMapping = (GetMapping)annotation;
                if(methodRequestMapping.value() != null && methodRequestMapping.value().length >0){
                    methodUrl = methodRequestMapping.value()[0];
                }
                if(methodRequestMapping.path() != null && methodRequestMapping.path().length > 0){
                    methodUrl = methodRequestMapping.path()[0];
                }
                REQUEST_METHOD = RequestMethod.GET.name();
                fluxPraseMethodBean.setUrl(methodUrl);
                fluxPraseMethodBean.setRequestMethod(HttpMethod.GET);
            }

            if(annotation instanceof PostMapping){
                PostMapping methodRequestMapping = (PostMapping)annotation;
                if(methodRequestMapping.value() != null && methodRequestMapping.value().length >0){
                    methodUrl = methodRequestMapping.value()[0];
                }
                REQUEST_METHOD = RequestMethod.POST.name();
                fluxPraseMethodBean.setUrl(methodUrl);
                fluxPraseMethodBean.setRequestMethod(HttpMethod.POST);
            }

            if(annotation instanceof PutMapping){
                PutMapping methodRequestMapping = (PutMapping)annotation;
                if(methodRequestMapping.value() != null && methodRequestMapping.value().length >0){
                    methodUrl = methodRequestMapping.value()[0];
                }
                REQUEST_METHOD = RequestMethod.PUT.name();
                fluxPraseMethodBean.setUrl(methodUrl);
                fluxPraseMethodBean.setRequestMethod(HttpMethod.PUT);
            }

            if(annotation instanceof DeleteMapping){
                DeleteMapping methodRequestMapping = (DeleteMapping)annotation;
                if(methodRequestMapping.value() != null && methodRequestMapping.value().length >0){
                    methodUrl = methodRequestMapping.value()[0];
                }
                if(methodRequestMapping.path() != null && methodRequestMapping.path().length > 0){
                    methodUrl = methodRequestMapping.path()[0];
                }
                REQUEST_METHOD = RequestMethod.DELETE.name();
                fluxPraseMethodBean.setUrl(methodUrl);
                fluxPraseMethodBean.setRequestMethod(HttpMethod.DELETE);
            }
        }
    }

    /**
     * webflux解析类
     * @param method
     * @return
     */
    private FluxPraseClassBean praseClass(Method method) {
        FluxPraseClassBean fluxPraseClassBean = new FluxPraseClassBean();
        RequestMapping classRequstMapping = method.getDeclaringClass().getAnnotation(RequestMapping.class);
        if(classRequstMapping != null){
            if(classRequstMapping.value() != null && classRequstMapping.value().length >0){
                fluxPraseClassBean.setUrlPrefix(classRequstMapping.value()[0]);
            }
        }
        //解析注解上的请求地址
        WebFluxClient webFluxClient = method.getDeclaringClass().getAnnotation(WebFluxClient.class);
        fluxPraseClassBean.setServiceUrl(webFluxClient.value());
        return fluxPraseClassBean;
    }

}
