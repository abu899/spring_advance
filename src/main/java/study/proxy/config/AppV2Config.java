package study.proxy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.proxy.app.v2.OrderControllerProxyV2;
import study.proxy.app.v2.OrderRepositoryProxyV2;
import study.proxy.app.v2.OrderServiceProxyV2;

@Configuration
public class AppV2Config {

    @Bean
    public OrderControllerProxyV2 orderControllerProxyV2() {
        return new OrderControllerProxyV2(orderServiceProxyV2());
    }

    @Bean
    public OrderServiceProxyV2 orderServiceProxyV2() {
        return new OrderServiceProxyV2(orderRepositoryProxyV2());
    }

    @Bean
    public OrderRepositoryProxyV2 orderRepositoryProxyV2() {
        return new OrderRepositoryProxyV2();
    }
}
