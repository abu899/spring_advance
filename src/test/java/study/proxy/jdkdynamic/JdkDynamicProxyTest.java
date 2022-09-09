package study.proxy.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import study.jdkdynamic.code.*;
import study.proxy.jdkdynamic.code.*;

import java.lang.reflect.Proxy;

@Slf4j
public class JdkDynamicProxyTest {

    @Test
    void dynamicA() {
        InterfaceA target = new ImplA();
        TimeInvocationHandler handler = new TimeInvocationHandler(target);

        InterfaceA proxy = (InterfaceA) Proxy.newProxyInstance(InterfaceA.class.getClassLoader(), new Class[]{InterfaceA.class}, handler);

        proxy.call();
        log.info("targetClass ={} ", target.getClass());
        log.info("proxyClass ={} ", proxy.getClass());
    }

    @Test
    void dynamicB() {
        InterfaceB target = new ImplB();
        TimeInvocationHandler handler = new TimeInvocationHandler(target);

        InterfaceB proxy = (InterfaceB) Proxy.newProxyInstance(InterfaceB.class.getClassLoader(), new Class[]{InterfaceB.class}, handler);

        proxy.call();
        log.info("targetClass ={} ", target.getClass());
        log.info("proxyClass ={} ", proxy.getClass());
    }
}
