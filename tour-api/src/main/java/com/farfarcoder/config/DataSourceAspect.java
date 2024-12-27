package com.farfarcoder.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceAspect {
    @Pointcut("execution(* com.farfarcoder.domain..*.repository..*.*(..))")
    public void repositoryMethods() {
    }

    @Before("repositoryMethods()")
    public void setDataSourceForRead(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName().toLowerCase();
        if (methodName.startsWith("get") || methodName.startsWith("find") || methodName.startsWith("select")) {
            // 읽기 작업
            RoutingDataSource.setDataSourceType(RoutingDataSource.READ);
        } else {
            // 쓰기 작업
            RoutingDataSource.setDataSourceType(RoutingDataSource.WRITE);
        }
    }

    @After("repositoryMethods()")
    public void clearDataSource() {
        RoutingDataSource.clearDataSourceType();
    }
}
