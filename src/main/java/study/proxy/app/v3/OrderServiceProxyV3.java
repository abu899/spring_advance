package study.proxy.app.v3;

import org.springframework.stereotype.Service;

@Service
public class OrderServiceProxyV3 {

    private final OrderRepositoryProxyV3 orderRepository;

    public OrderServiceProxyV3(OrderRepositoryProxyV3 orderRepositoryProxyV2) {
        this.orderRepository = orderRepositoryProxyV2;
    }

    public void orderItem(String itemId) {
        orderRepository.save(itemId);
    }
}
