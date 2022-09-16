package study.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

@Slf4j
@Aspect
public class AspectV6Advice {
//    @Around("study.aop.order.aop.Pointcuts.orderAndService()")
//    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
//        try {
//            // @Before
//            log.info("[transaction 시작] {}", joinPoint.getSignature());
//            Object result = joinPoint.proceed();
//            // @AfterReturning
//            log.info("[transaction 커밋] {}", joinPoint.getSignature());
//            return result;
//        } catch (Exception e) {
//            // @AfterThrowing
//            log.info("[transaction 롤백] {}", joinPoint.getSignature());
//            throw e;
//        } finally {
//            // @After
//            log.info("[Resource release] {}", joinPoint.getSignature());
//        }
//    }

    @Before("study.aop.order.aop.Pointcuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("[Before] {}", joinPoint.getSignature());
    }

    @AfterReturning(value = "study.aop.order.aop.Pointcuts.orderAndService()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        log.info("[AfterReturning] {}, result = {}", joinPoint.getSignature(), result);
    }

    @AfterThrowing(value = "study.aop.order.aop.Pointcuts.orderAndService()", throwing = "ex")
    public void afterThrowing(JoinPoint joinPoint, Exception ex) {
        log.info("[AfterThrowing] {}, ex = {}", joinPoint.getSignature(), ex);
    }

    @After("study.aop.order.aop.Pointcuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("[After] {}", joinPoint.getSignature());
    }
}
