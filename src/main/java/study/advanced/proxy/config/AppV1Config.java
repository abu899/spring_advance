package study.advanced.proxy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.advanced.proxy.v1.*;

@Configuration
public class AppV1Config {

    @Bean
    public OrderControllerProxyV1 orderControllerProxyV1() {
        return new OrderControllerProxyV1Impl(orderServiceProxyV1());
    }

    @Bean
    public OrderServiceProxyV1 orderServiceProxyV1() {
        return new OrderServiceProxyV1Impl(orderRepositoryProxyV1());
    }

    @Bean
    public OrderRepositoryProxyV1 orderRepositoryProxyV1() {
        return new OrderRepositoryProxyV1Impl();
    }
}
