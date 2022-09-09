package study.proxy.config.v3_proxyfactory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.proxy.app.logtrace.LogTrace;
import study.proxy.app.v1.*;
import study.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;

@Configuration
@Slf4j
public class ProxyFactoryConfigV1 {

    @Bean
    public OrderControllerProxyV1 orderControllerProxyV1(LogTrace logTrace) {
        OrderControllerProxyV1 orderControllerProxyV1 = new OrderControllerProxyV1Impl(orderServiceProxyV1(logTrace));
        ProxyFactory factory = new ProxyFactory(orderControllerProxyV1);
        factory.addAdvisor(getAdvisor(logTrace));
        OrderControllerProxyV1 proxy = (OrderControllerProxyV1) factory.getProxy();
        log.info("ProxyFactory proxy = {}, target = {}", proxy.getClass(), orderControllerProxyV1.getClass());

        return proxy;
    }

    @Bean
    public OrderServiceProxyV1 orderServiceProxyV1(LogTrace logTrace) {
        OrderServiceProxyV1 orderServiceProxyV1 = new OrderServiceProxyV1Impl(orderRepositoryProxyV1(logTrace));
        ProxyFactory factory = new ProxyFactory(orderServiceProxyV1);
        factory.addAdvisor(getAdvisor(logTrace));
        OrderServiceProxyV1 proxy = (OrderServiceProxyV1) factory.getProxy();

        log.info("ProxyFactory proxy = {}, target = {}", proxy.getClass(), orderServiceProxyV1.getClass());
        return proxy;
    }

    @Bean
    public OrderRepositoryProxyV1 orderRepositoryProxyV1(LogTrace logTrace) {
        OrderRepositoryProxyV1 orderRepositoryProxyV1 = new OrderRepositoryProxyV1Impl();

        ProxyFactory factory = new ProxyFactory(orderRepositoryProxyV1);
        factory.addAdvisor(getAdvisor(logTrace));
        OrderRepositoryProxyV1 proxy = (OrderRepositoryProxyV1) factory.getProxy();

        log.info("ProxyFactory proxy = {}, target = {}", proxy.getClass(), orderRepositoryProxyV1.getClass());
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
