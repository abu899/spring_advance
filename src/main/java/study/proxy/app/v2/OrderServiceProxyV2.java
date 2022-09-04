package study.proxy.app.v2;

public class OrderServiceProxyV2 {

    private final OrderRepositoryProxyV2 orderRepository;

    public OrderServiceProxyV2(OrderRepositoryProxyV2 orderRepositoryProxyV2) {
        this.orderRepository = orderRepositoryProxyV2;
    }

    public void orderItem(String itemId) {
        orderRepository.save(itemId);
    }
}
