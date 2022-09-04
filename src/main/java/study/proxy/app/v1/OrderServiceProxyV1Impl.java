package study.proxy.app.v1;

public class OrderServiceProxyV1Impl implements OrderServiceProxyV1 {

    private final OrderRepositoryProxyV1 orderRepository;

    public OrderServiceProxyV1Impl(OrderRepositoryProxyV1 orderRepositoryProxyV1) {
        this.orderRepository = orderRepositoryProxyV1;
    }

    @Override
    public void orderItem(String itemId) {
        orderRepository.save(itemId);

    }
}
