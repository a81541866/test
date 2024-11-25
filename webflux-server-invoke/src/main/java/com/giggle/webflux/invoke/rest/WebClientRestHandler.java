package com.giggle.webflux.invoke.rest;

import com.alibaba.fastjson.JSON;
import com.giggle.webflux.invoke.bean.FluxPraseClassBean;
import com.giggle.webflux.invoke.bean.FluxPraseMethodBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author guozichen
 * @ClassName:
 * @Description: (http)
 * @date 2020/12/3 22:17
 */
@Slf4j
public class WebClientRestHandler implements RestHandler {

    private WebClient client;
    private FluxPraseClassBean fluxPraseClassBean;

    /**
     * 初始化
     *
     * @param fluxPraseClassBean
     * @return
     */
    @Override
    public void init(FluxPraseClassBean fluxPraseClassBean) {
        this.fluxPraseClassBean = fluxPraseClassBean;
        this.client = WebClient.create(fluxPraseClassBean.getServiceUrl());
    }

    /**
     * 反射调用方法
     *
     * @param fluxPraseMethodBean
     * @return
     */
    @Override
    public Object invokeRest(FluxPraseMethodBean fluxPraseMethodBean) {
        Object result = null;
        log.info("调用前参数fluxPraseMethodBean：{}", JSON.toJSONString(fluxPraseMethodBean));
        StringBuilder sb = new StringBuilder();
        if(StringUtils.isNotBlank(fluxPraseClassBean.getUrlPrefix())){
            sb.append(fluxPraseClassBean.getUrlPrefix());
        }
        if(StringUtils.isNotBlank(fluxPraseMethodBean.getUrl())){
            sb.append(fluxPraseMethodBean.getUrl());
        }
        //这里可以得到参数数组和方法等，可以通过反射，注解等，进行结果集的处理
        WebClient.RequestBodyUriSpec requestBodyUriSpec = this.client
                //请求方法
                .method(fluxPraseMethodBean.getRequestMethod());
                //请求地址
        WebClient.RequestBodySpec  responseBodySpec = null;
        if(fluxPraseMethodBean.getParams() != null){
            responseBodySpec = requestBodyUriSpec.uri(sb.toString(),fluxPraseMethodBean.getParams());
        }else{
            responseBodySpec = requestBodyUriSpec.uri(sb.toString());
        }
        //接收格式
        WebClient.ResponseSpec responseSpec = null;
        if(fluxPraseMethodBean.getBody() != null){
            responseSpec = responseBodySpec.body(fluxPraseMethodBean.getBody(),fluxPraseMethodBean.getBodyType())
            .accept(MediaType.APPLICATION_JSON)
                    //发出请求
                    .retrieve();
        }else{
            responseSpec  = responseBodySpec .accept(MediaType.APPLICATION_JSON)
                    //发出请求
                    .retrieve();

        }
        //处理请求
        if(fluxPraseMethodBean.getReturnFlux()){
            result = responseSpec.bodyToFlux(fluxPraseMethodBean.getReturnElementType());
        }else {
            result = responseSpec.bodyToMono(fluxPraseMethodBean.getReturnElementType());
        }
        //
        return result;
    }


}
