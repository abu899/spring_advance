package study.proxy.jdkdynamic.code;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Slf4j
public class TimeInvocationHandler implements InvocationHandler {

    private final Object target;

    public TimeInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("Time Proxy 실행");
        long start = System.currentTimeMillis();

        Object result = method.invoke(target, args); // 실제 호출 로직

        long end = System.currentTimeMillis();
        long resultTime = end - start;
        log.info("Time Proxy 종료 result = {} ", resultTime);
        return result;
    }
}
