package study.proxy.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

@Slf4j
public class ReflectionTest {

    @Test
    void reflection0() {
        Hello target = new Hello();

        // 공통 로직 1 시작
        log.info("start");
        String result1 = target.callA(); // 호출 대상에 대한 동적처리가 필요하다
        log.info("result = {}", result1);
        // 공통 로직 1 종료

        // 공통 로직 2 시작
        log.info("start");
        String result2 = target.callB();
        log.info("result = {}", result2);
        // 공통 로직 1 종료
    }

    @Test
    void reflection1() throws Exception {
        // 클래스 정보 획득
        Class classHello = Class.forName("study.jdkdynamic.ReflectionTest$Hello");
        Hello target = new Hello();

        // callA 메서드 정보 획득
        Method methodCallA = classHello.getMethod("callA");
        Object result1 = methodCallA.invoke(target);
        log.info("result = {}", result1);

        // callB 메서드 정보 획득
        Method methodCallB = classHello.getMethod("callB");
        Object result2 = methodCallB.invoke(target);
        log.info("result = {}", result1);
    }

    @Test
    void reflection2() throws Exception {
        // 클래스 정보 획득
        Class classHello = Class.forName("study.jdkdynamic.ReflectionTest$Hello");
        Hello target = new Hello();

        // callA 메서드 정보 획득
        Method methodCallA = classHello.getMethod("callA");
        dynamicCall(methodCallA, target);

        // callB 메서드 정보 획득
        Method methodCallB = classHello.getMethod("callB");
        dynamicCall(methodCallB, target);

    }

    private void dynamicCall(Method method, Object target) throws Exception {
        log.info("start");
        Object result = method.invoke(target);
        log.info("result = {}", result);
    }

    @Slf4j
    static class Hello {
        public String callA() {
            log.info("call A");
            return "A";
        }
        public String callB() {
            log.info("call B");
            return "B";
        }
    }
}
