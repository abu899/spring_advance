package study.proxy.config.v3_proxyfactory.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import study.proxy.app.logtrace.LogTrace;
import study.proxy.app.trace.TraceStatus;

import java.lang.reflect.Method;

public class LogTraceAdvice implements MethodInterceptor {

    private final LogTrace logTrace;

    public LogTraceAdvice(LogTrace logTrace) {
        this.logTrace = logTrace;
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        TraceStatus status = null;
        try {
            Method method = invocation.getMethod();
            String msg = method.getDeclaringClass().getSimpleName() + "." + method.getName() + "()";
            status = logTrace.begin(msg);

            // 실제 객체 호출
            Object result = invocation.proceed();
            logTrace.end(status);

            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
