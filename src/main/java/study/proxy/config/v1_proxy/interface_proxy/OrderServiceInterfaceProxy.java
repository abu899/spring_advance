package study.proxy.config.v1_proxy.interface_proxy;

import lombok.RequiredArgsConstructor;
import study.proxy.app.logtrace.LogTrace;
import study.proxy.app.trace.TraceStatus;
import study.proxy.app.v1.OrderServiceProxyV1;

@RequiredArgsConstructor
public class OrderServiceInterfaceProxy implements OrderServiceProxyV1 {

    private final OrderServiceProxyV1 target;
    private final LogTrace logTrace;

    @Override
    public void orderItem(String itemId) {
        TraceStatus status = null;
        try {
            status = logTrace.begin("OrderService_Interface.orderItem()");
            // 실제 객체 호출
            target.orderItem(itemId);
            logTrace.end(status);
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
