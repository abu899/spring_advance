package study.proxy.config.v2_dynamicproxy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.proxy.app.logtrace.LogTrace;
import study.proxy.app.v1.*;
import study.proxy.config.v2_dynamicproxy.handler.LogTraceFilterHandler;

import java.lang.reflect.Proxy;

@Configuration
public class DynamicProxyFilterConfig {

    private static final String[] PATTERNS = {"request*", "order*", "save*"};
    @Bean
    public OrderControllerProxyV1 orderControllerProxyV1(LogTrace logTrace) {
        OrderControllerProxyV1 orderControllerProxyV1 = new OrderControllerProxyV1Impl(orderServiceProxyV1(logTrace));

        OrderControllerProxyV1 proxy = (OrderControllerProxyV1) Proxy.newProxyInstance(OrderControllerProxyV1.class.getClassLoader(),
                new Class[]{OrderControllerProxyV1.class},
                new LogTraceFilterHandler(orderControllerProxyV1, logTrace, PATTERNS));

        return proxy;
    }

    @Bean
    public OrderServiceProxyV1 orderServiceProxyV1(LogTrace logTrace) {
        OrderServiceProxyV1 orderServiceProxyV1 = new OrderServiceProxyV1Impl(orderRepositoryProxyV1(logTrace));

        OrderServiceProxyV1 proxy = (OrderServiceProxyV1) Proxy.newProxyInstance(OrderServiceProxyV1.class.getClassLoader(),
                new Class[]{OrderServiceProxyV1.class},
                new LogTraceFilterHandler(orderServiceProxyV1, logTrace, PATTERNS));

        return proxy;
    }

    @Bean
    public OrderRepositoryProxyV1 orderRepositoryProxyV1(LogTrace logTrace) {
        OrderRepositoryProxyV1 orderRepositoryProxyV1 = new OrderRepositoryProxyV1Impl();

        OrderRepositoryProxyV1 proxy = (OrderRepositoryProxyV1) Proxy.newProxyInstance(OrderRepositoryProxyV1.class.getClassLoader(),
                new Class[]{OrderRepositoryProxyV1.class},
                new LogTraceFilterHandler(orderRepositoryProxyV1, logTrace, PATTERNS));

        return proxy;
    }
}
