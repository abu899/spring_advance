package study.proxy.config.v1_proxy.concrete_proxy;

import study.proxy.app.logtrace.LogTrace;
import study.proxy.app.trace.TraceStatus;
import study.proxy.app.v2.OrderServiceProxyV2;

public class OrderServiceConcreteProxy extends OrderServiceProxyV2 {

    private OrderServiceProxyV2 target;
    private LogTrace logTrace;

    public OrderServiceConcreteProxy(OrderServiceProxyV2 target, LogTrace logTrace) {
        super(null);
        this.target = target;
        this.logTrace = logTrace;
    }

    @Override
    public void orderItem(String itemId) {
        TraceStatus status = null;
        try {
            status = logTrace.begin("OrderService_Concrete.orderItem()");
            // 실제 객체 호출
            target.orderItem(itemId);
            logTrace.end(status);
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
