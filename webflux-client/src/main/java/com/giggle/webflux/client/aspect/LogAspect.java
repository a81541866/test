package com.giggle.webflux.client.aspect;

import com.alibaba.fastjson.JSON;

import com.giggle.webflux.client.wrapper.RequestWrapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Configuration
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Pointcut("execution(* com.giggle.webflux.client.controller.*.*(..))")
    public void excudeService() {
    }


    @Before("excudeService()")
    public void doBefore(){
    }

    @AfterReturning(returning = "ret",pointcut = "excudeService()")
    public void doAfter(JoinPoint joinPoint,Object ret) {
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Map<String,Object> infoMap = new HashMap<>();

        infoMap.put("请求接口",method.getDeclaringClass().getName());
        infoMap.put("请求方法",method.getName());
        infoMap.put("返回数据",ret);
        infoMap.put("请求方式",request.getMethod());
        String requestStr;
        if (RequestMethod.POST.name().equals(request.getMethod())){
            requestStr = new RequestWrapper(request).getBody();
        }else{
            requestStr = request.getQueryString();
        }
        logger.info("请求成功:{}",JSON.toJSONString(infoMap).replaceAll("/",""));
        logger.info("请求数据:{}",requestStr);
    }

}
