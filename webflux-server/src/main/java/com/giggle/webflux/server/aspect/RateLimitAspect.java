package com.giggle.webflux.server.aspect;

import com.giggle.webflux.server.common.AppException;
import com.giggle.webflux.server.common.BusinessException;
import com.giggle.webflux.server.common.ResultData;
import com.giggle.webflux.server.interceptor.Limiting;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;


@Aspect
@Component
@Slf4j
public class RateLimitAspect {
    private ConcurrentHashMap<String, RateLimiter> RATE_LIMITER  = new ConcurrentHashMap<>();
    private RateLimiter rateLimiter;

    @Pointcut("@annotation(com.giggle.webflux.server.interceptor.Limiting)")
    public void serviceLimit() {
    }

    @Around("serviceLimit()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        //获取拦截的方法名
        Signature sig = point.getSignature();
        //获取拦截的方法名
        MethodSignature msig = (MethodSignature) sig;
        //返回被织入增加处理目标对象
        Object target = point.getTarget();
        //为了获取注解信息
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        //获取注解信息
        Limiting annotation = currentMethod.getAnnotation(Limiting.class);
        double limitNum = annotation.limitNum();
        String functionName = msig.getName();

        if(RATE_LIMITER.containsKey(functionName)){
            rateLimiter=RATE_LIMITER.get(functionName);
        }else {
            RATE_LIMITER.put(functionName, RateLimiter.create(limitNum));
            rateLimiter=RATE_LIMITER.get(functionName);
        }
        if(rateLimiter.tryAcquire()) {
            log.info("处理完成");
            return point.proceed();
        } else {
            throw new AppException(429,"超过数量......");
        }
    }
}