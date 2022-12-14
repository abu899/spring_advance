package study.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class AspectV4Pointcut {
    @Around("study.aop.order.aop.Pointcuts.allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("log = {} ", joinPoint.getSignature());
        return joinPoint.proceed();
    }

    @Around("study.aop.order.aop.Pointcuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            log.info("[transaction 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[transaction 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            log.info("[transaction 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            log.info("[Resource release] {}", joinPoint.getSignature());
        }
    }
}
