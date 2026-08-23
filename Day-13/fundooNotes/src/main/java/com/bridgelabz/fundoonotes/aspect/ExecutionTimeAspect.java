package com.bridgelabz.fundoonotes.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Aspect
@Component
public class ExecutionTimeAspect {

    private static final Logger logger = LoggerFactory.getLogger(ExecutionTimeAspect.class);

    // Track invocation counts for automated testing verification
    private static final AtomicInteger executionCount = new AtomicInteger(0);

    @Pointcut("execution(* com.bridgelabz.fundoonotes.service.*.*(..))")
    public void serviceMethods() {
    }

    @Around("serviceMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().toShortString();

        try {
            Object result = joinPoint.proceed();
            long duration = System.currentTimeMillis() - start;
            executionCount.incrementAndGet();
            logger.info("AOP ExecutionTimer: {} executed in {}ms", methodName, duration);
            return result;
        } catch (Throwable ex) {
            long duration = System.currentTimeMillis() - start;
            logger.warn("AOP ExecutionTimer: {} failed with exception in {}ms: {}", methodName, duration,
                    ex.getMessage());
            throw ex;
        }
    }

    public static int getExecutionCount() {
        return executionCount.get();
    }

    public static void resetCount() {
        executionCount.set(0);
    }
}
