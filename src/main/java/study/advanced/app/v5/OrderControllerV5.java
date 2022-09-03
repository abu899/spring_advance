package study.advanced.app.v5;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import study.advanced.app.logtrace.LogTrace;
import study.advanced.app.trace.callback.TraceCallback;
import study.advanced.app.trace.callback.TraceTemplate;

@RestController
public class OrderControllerV5 {

    private final OrderServiceV5 orderService;
    private final TraceTemplate traceTemplate;

    public OrderControllerV5(OrderServiceV5 orderService, LogTrace logTrace) {
        this.orderService = orderService;
        this.traceTemplate = new TraceTemplate(logTrace);
    }

    @GetMapping("/v5/request")
    public String order(String itemId) {
       return traceTemplate.execute("OrderController.order()", new TraceCallback<String>() {
            @Override
            public String call() {
                orderService.orderItem(itemId);
                return "ok";
            }
        });
    }
}
