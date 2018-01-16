package ru.eleron.osa.lris.otcenka.utilities.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogDao {

    private static final Logger log = LogManager.getLogger();

    @Around("execution(* ru.eleron.osa.lris.otcenka.service.implementation..*.*(..))")
    public Object logBefore(ProceedingJoinPoint proceedingJoinPoint){
        String methodName = proceedingJoinPoint.getSignature().getName();
        String packageName = proceedingJoinPoint.getTarget().toString();
        Object[] mathodArgs = proceedingJoinPoint.getArgs();
        log.info("call method " + methodName + " with args " + mathodArgs + " in package " + packageName);
        Object result = null;
        try{
            result = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            log.error("method " + proceedingJoinPoint.getSignature().getName() + " execute with error",e);
        }
        log.info("method " + methodName + " return " + result);
        return result;
    }
/*
    @Around("execution(* ru.eleron.osa.lris.otcenka.controllers..*.initialize(..))")
    public Object logInitializeControllers(ProceedingJoinPoint proceedingJoinPoint){
        String packageName = proceedingJoinPoint.getTarget().toString();
        log.info("starting initialize frame " + packageName);
        Object result = null;
        try{
            result = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            log.error("fail initialize frame",e);
        }
        log.info("frame was succussful load");
        return result;
    }*/
}
