package study.aop.proxyvs.code;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
@Slf4j
public class ProxyDIAspect {

    @Before("execution(* study.aop..*.*(..))")
    public void doTrace(JoinPoint joinPoint) {
        log.info("proxy DI Advice = {}", joinPoint.getSignature());
    }
}
