package study.proxy.config.v1_proxy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.proxy.app.logtrace.LogTrace;
import study.proxy.app.v2.OrderControllerProxyV2;
import study.proxy.app.v2.OrderRepositoryProxyV2;
import study.proxy.app.v2.OrderServiceProxyV2;
import study.proxy.config.v1_proxy.concrete_proxy.OrderControllerConcreteProxy;
import study.proxy.config.v1_proxy.concrete_proxy.OrderRepositoryConcreteProxy;
import study.proxy.config.v1_proxy.concrete_proxy.OrderServiceConcreteProxy;

@Configuration
public class ConcreteProxyConfig {

    @Bean
    public OrderControllerProxyV2 orderController(LogTrace logTrace) {
        return new OrderControllerConcreteProxy(new OrderControllerProxyV2(orderService(logTrace)),logTrace);
    }

    @Bean
    public OrderServiceProxyV2 orderService(LogTrace logTrace) {
        return new OrderServiceConcreteProxy(new OrderServiceProxyV2(orderRepository(logTrace)), logTrace);
    }

    @Bean
    public OrderRepositoryProxyV2 orderRepository(LogTrace logTrace) {
        return new OrderRepositoryConcreteProxy(new OrderRepositoryProxyV2(), logTrace);
    }
}
