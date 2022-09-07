package study.proxy.config.v1_proxy.interface_proxy;

import lombok.RequiredArgsConstructor;
import study.proxy.app.logtrace.LogTrace;
import study.proxy.app.trace.TraceStatus;
import study.proxy.app.v1.OrderRepositoryProxyV1;

@RequiredArgsConstructor
public class OrderRepositoryInterfaceProxy implements OrderRepositoryProxyV1 {

    private final OrderRepositoryProxyV1 target;
    private final LogTrace logTrace;

    @Override
    public void save(String itemId) {

        TraceStatus status = null;
        try {
            status = logTrace.begin("OrderRepository_Interface.request()");
            // 실제 객체 호출
            target.save(itemId);
            logTrace.end(status);
       } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
