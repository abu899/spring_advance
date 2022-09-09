package study.proxy.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@Slf4j
public class TimeAdvice implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("Time Proxy Advice 실행");
        long start = System.currentTimeMillis();

//        Object result = methodProxy.invoke(target, args);
        Object result = invocation.proceed();

        long end = System.currentTimeMillis();
        long resultTime = end - start;
        log.info("Time Proxy Advice 종료 result = {} ", resultTime);
        return result;
    }
}
