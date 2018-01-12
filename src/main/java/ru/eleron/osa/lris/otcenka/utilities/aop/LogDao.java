package ru.eleron.osa.lris.otcenka.utilities.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogDao {

    //private static final Logger log = LogManager.getLogger();

    @Before("execution(* ru.eleron.osa.lris.otcenka..controller..*(..))")
    public void logBefore(JoinPoint joinPoint){
        System.out.println("Hello");
        //log.info("start method " + joinPoint.getSignature().getName() + " for class " + joinPoint.getTarget());
    }
}
