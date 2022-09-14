package study.proxy.config.v6_aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import study.proxy.app.logtrace.LogTrace;
import study.proxy.app.trace.TraceStatus;

@Slf4j
@Aspect
public class LogTraceAspect {

    private final LogTrace logTrace;

    public LogTraceAspect(LogTrace logTrace) {
        this.logTrace = logTrace;
    }

    @Around("execution(* study.proxy.app..*(..))") // pointcut
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        //advice
        TraceStatus status = null;
        try {
            String msg = joinPoint.getSignature().toShortString();
            status = logTrace.begin(msg);

            // 실제 객체 호출
            Object result = joinPoint.proceed();
            logTrace.end(status);

            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
