package study.proxy.config.v1_proxy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.proxy.app.logtrace.LogTrace;
import study.proxy.app.v1.*;
import study.proxy.config.v1_proxy.interface_proxy.OrderControllerInterfaceProxy;
import study.proxy.config.v1_proxy.interface_proxy.OrderRepositoryInterfaceProxy;
import study.proxy.config.v1_proxy.interface_proxy.OrderServiceInterfaceProxy;

@Configuration
public class InterfaceProxyConfig {

    @Bean
    public OrderControllerProxyV1 orderController(LogTrace logTrace) {
        return new OrderControllerInterfaceProxy(new OrderControllerProxyV1Impl(orderService(logTrace)), logTrace);
    }

    @Bean
    public OrderServiceProxyV1 orderService(LogTrace logTrace) {
        return new OrderServiceInterfaceProxy(new OrderServiceProxyV1Impl(orderRepository(logTrace)), logTrace);
    }

    @Bean
    public OrderRepositoryProxyV1 orderRepository(LogTrace logTrace) {
        return new OrderRepositoryInterfaceProxy(new OrderRepositoryProxyV1Impl(), logTrace);
    }
}
