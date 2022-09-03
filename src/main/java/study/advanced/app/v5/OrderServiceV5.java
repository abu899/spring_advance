package study.advanced.app.v5;

import org.springframework.stereotype.Service;
import study.advanced.app.logtrace.LogTrace;
import study.advanced.app.trace.callback.TraceTemplate;

@Service
public class OrderServiceV5 {

    private final OrderRepositoryV5 orderRepository;
    private final TraceTemplate traceTemplate;

    public OrderServiceV5(OrderRepositoryV5 orderRepository, LogTrace logTrace) {
        this.orderRepository = orderRepository;
        this.traceTemplate = new TraceTemplate(logTrace);
    }

    public void orderItem(String itemId) {
        traceTemplate.execute("OrderService.orderItem()",
                () -> {
                    orderRepository.save(itemId);
                    return null;
                });
    }
}
