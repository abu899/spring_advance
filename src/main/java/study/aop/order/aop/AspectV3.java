package study.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV3 {

    // study.aop.order 패키지와 그 하위 패키지
    @Pointcut("execution(* study.aop.order..*(..))")
    private void allOrder() {} // pointcut signature

    // 클래스 이름 패턴이 *Service 인 것만
    @Pointcut("execution(* *..*Service.*(..))")
    private void allService() {}

    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("log = {} ", joinPoint.getSignature());
        return joinPoint.proceed();
    }

    // order 패키지 하위에 있으면서 이름이 *Service인 것
    @Around("allOrder() && allService()")
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
