package study.proxy.config.v2_dynamicproxy.handler;

import lombok.extern.slf4j.Slf4j;
import study.proxy.app.logtrace.LogTrace;
import study.proxy.app.trace.TraceStatus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Slf4j
public class LogTraceBasicHandler implements InvocationHandler {

    private final Object target;
    private final LogTrace logTrace;

    public LogTraceBasicHandler(Object target, LogTrace logTrace) {
        this.target = target;
        this.logTrace = logTrace;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        TraceStatus status = null;
        try {
            String msg = method.getDeclaringClass().getSimpleName() + "." + method.getName() + "()";
            status = logTrace.begin(msg);
            // 실제 객체 호출
            Object result = method.invoke(target, args);
            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
