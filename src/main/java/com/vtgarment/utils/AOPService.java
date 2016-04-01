package com.vtgarment.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class AOPService {
    private Logger log;

//    @Pointcut("within(com.tmn.rce.module.*.service..*)")
//    public void serviceLayer() {
//    }

//    @Pointcut(")")
//    public void dataAccessLayer() {
//    }

//    @Pointcut("execution(* com.tmn.rce.module.*.service.*.*(..) && @annotation(org.springframework.web.bind.annotation.RequestMapping))")
//    private void pointcut(){}

    @Around("execution(* com.vtgarment.beans.*.*(..))")
    public Object execute(ProceedingJoinPoint proceedingJoinPoint){
        log = LoggerFactory.getLogger(proceedingJoinPoint.getTarget().getClass());
        log.info("===== {} Started =====", proceedingJoinPoint.getSignature().getName());
        Object[] arguments = proceedingJoinPoint.getArgs();
        if (arguments.length != 0) {
            log.debug("Argument : {}", Arrays.asList(arguments));
        }
        Object returnObj = null;
        try {
            returnObj = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            if (returnObj != null) {
                log.debug("Returned : {}", returnObj.toString());
            }
            log.info("===== {} Stopped =====", proceedingJoinPoint.getSignature().getName());
        }
        return returnObj;
    }

    //    @Before("serviceLayer()")
//    public void before(JoinPoint joinPoint){
//        log = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
//        log.info("===== {} Started =====", joinPoint.getSignature().getName());
//
//        if (joinPoint.getArgs().length != 0) {
//            log.debug("Argument : {}", Arrays.asList(joinPoint.getArgs()));
//        }
//    }
//
//    @AfterReturning(pointcut = "serviceLayer()", returning="retVal")
//    public void afterReturning(JoinPoint joinPoint, Object retVal){
//        if (retVal != null) {
//            log.debug("Returned : {}", retVal);
//        }
//        log.info("===== {} Stopped =====", joinPoint.getSignature().getName());
//    }
//
//    @AfterThrowing(pointcut = "serviceLayer()", throwing = "ex")
//    public void afterThrowing(JoinPoint joinPoint, Exception ex) {
//        log.info("===== {} Stopped by Exception =====", joinPoint.getSignature().getName());
//    }

    //    @After("serviceMethod()")
//    public void afterAdvice(){
//        log.debug("After");
//    }
}
