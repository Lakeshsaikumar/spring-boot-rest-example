package com.khoubyari.example;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RestControllerAspect {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    // Named pointcut: target only controller methods annotated with @GetMapping (or other mappings)
    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void getMappingMethods() {}

    // Alternatively, if you want *all* controller methods, but within a specific package:
    // @Pointcut("within(com.khoubyari.example.api.rest..*) && execution(public * *(..))")
    // public void allRestControllerMethods() {}

    @Before("getMappingMethods()")
    public void logBeforeRestCall(JoinPoint pjp) {
        // Log more context to help debugging
        log.info("AOP Before REST call → method: {}, signature: {}, thread: {}",
                pjp.getSignature().toShortString(),
                pjp.getSignature().toLongString(),
                Thread.currentThread().getName());
        log.debug("Target class: {}", pjp.getTarget().getClass().getName());
    }
}
