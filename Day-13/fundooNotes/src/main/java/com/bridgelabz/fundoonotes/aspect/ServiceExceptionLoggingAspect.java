package com.bridgelabz.fundoonotes.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Aspect
@Component
public class ServiceExceptionLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(ServiceExceptionLoggingAspect.class);

    private static final AtomicInteger exceptionLogCount = new AtomicInteger(0);

    @Pointcut("execution(* com.bridgelabz.fundoonotes.service.*.*(..))")
    public void anyServiceMethod() {
    }

    @AfterThrowing(pointcut = "anyServiceMethod()", throwing = "ex")
    public void logServiceException(JoinPoint joinPoint, Throwable ex) {
        exceptionLogCount.incrementAndGet();
        logger.error("AOP ExceptionLog: Service exception in {} - Exception: {} Message: {}",
                joinPoint.getSignature().toShortString(),
                ex.getClass().getSimpleName(),
                ex.getMessage());
    }

    public static int getExceptionLogCount() {
        return exceptionLogCount.get();
    }

    public static void resetCount() {
        exceptionLogCount.set(0);
    }
}
