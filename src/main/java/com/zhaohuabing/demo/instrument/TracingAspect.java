package com.zhaohuabing.demo.instrument;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import io.opentracing.Span;
import io.opentracing.Tracer;

/**
 * Huabing Zhao
 */
@Aspect
public class TracingAspect {
    @Autowired
    Tracer tracer;

    @Around("@annotation(com.zhaohuabing.demo.instrument.Traced)")
    public Object aroundAdvice(ProceedingJoinPoint jp) throws Throwable {
        String class_name = jp.getTarget().getClass().getName();
        String method_name = jp.getSignature().getName();

        Span span = tracer.buildSpan(class_name + "." + method_name).withTag("class", class_name)
                .withTag("method", method_name).start();
        Object result = jp.proceed();
        span.finish();
        return result;
    }
}
