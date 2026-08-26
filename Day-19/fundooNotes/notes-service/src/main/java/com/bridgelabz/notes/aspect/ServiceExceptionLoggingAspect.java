package com.bridgelabz.notes.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceExceptionLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(ServiceExceptionLoggingAspect.class);

    @AfterThrowing(pointcut = "execution(* com.bridgelabz.notes.service.*.*(..))", throwing = "ex")
    public void logServiceException(JoinPoint joinPoint, Throwable ex) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();

        logger.error("AOP Exception Logger: Exception thrown in {}.{}() -> Type: {} | Message: {}",
                className, methodName, ex.getClass().getSimpleName(), ex.getMessage());
    }
}
