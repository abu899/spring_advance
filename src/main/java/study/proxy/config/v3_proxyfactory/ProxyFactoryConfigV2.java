package study.proxy.config.v3_proxyfactory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.proxy.app.logtrace.LogTrace;
import study.proxy.app.v2.OrderControllerProxyV2;
import study.proxy.app.v2.OrderRepositoryProxyV2;
import study.proxy.app.v2.OrderServiceProxyV2;
import study.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;

@Configuration
@Slf4j
public class ProxyFactoryConfigV2 {

    @Bean
    public OrderControllerProxyV2 orderControllerProxyV1(LogTrace logTrace) {
        OrderControllerProxyV2 orderControllerProxyV2 = new OrderControllerProxyV2(orderServiceProxyV2(logTrace));
        ProxyFactory factory = new ProxyFactory(orderControllerProxyV2);
        factory.addAdvisor(getAdvisor(logTrace));
        OrderControllerProxyV2 proxy = (OrderControllerProxyV2) factory.getProxy();
        log.info("ProxyFactory proxy = {}, target = {}", proxy.getClass(), orderControllerProxyV2.getClass());

        return proxy;
    }

    @Bean
    public OrderServiceProxyV2 orderServiceProxyV2(LogTrace logTrace) {
        OrderServiceProxyV2 orderServiceProxyV2 = new OrderServiceProxyV2(orderRepositoryProxyV2(logTrace));
        ProxyFactory factory = new ProxyFactory(orderServiceProxyV2);
        factory.addAdvisor(getAdvisor(logTrace));
        OrderServiceProxyV2 proxy = (OrderServiceProxyV2) factory.getProxy();

        log.info("ProxyFactory proxy = {}, target = {}", proxy.getClass(), orderServiceProxyV2.getClass());
        return proxy;
    }

    @Bean
    public OrderRepositoryProxyV2 orderRepositoryProxyV2(LogTrace logTrace) {
        OrderRepositoryProxyV2 orderRepositoryProxyV2 = new OrderRepositoryProxyV2();

        ProxyFactory factory = new ProxyFactory(orderRepositoryProxyV2);
        factory.addAdvisor(getAdvisor(logTrace));
        OrderRepositoryProxyV2 proxy = (OrderRepositoryProxyV2) factory.getProxy();

        log.info("ProxyFactory proxy = {}, target = {}", proxy.getClass(), orderRepositoryProxyV2.getClass());
        return proxy;
    }

    private Advisor getAdvisor(LogTrace logTrace) {
        //pointcut
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save");

        //advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);

        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}
