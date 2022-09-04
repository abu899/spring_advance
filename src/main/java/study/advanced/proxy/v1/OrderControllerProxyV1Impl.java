package study.advanced.proxy.v1;

public class OrderControllerProxyV1Impl implements OrderControllerProxyV1{

    private final OrderServiceProxyV1 orderService;

    public OrderControllerProxyV1Impl(OrderServiceProxyV1 orderServiceV1) {
        this.orderService = orderServiceV1;
    }

    @Override
    public String request(String itemId) {
        orderService.orderItem(itemId);
        return "ok";
    }

    @Override
    public String noLog() {
        return "ok";
    }
}
