package study.proxy.config.v1_proxy.concrete_proxy;

import lombok.RequiredArgsConstructor;
import study.proxy.app.logtrace.LogTrace;
import study.proxy.app.trace.TraceStatus;
import study.proxy.app.v2.OrderRepositoryProxyV2;

@RequiredArgsConstructor
public class OrderRepositoryConcreteProxy extends OrderRepositoryProxyV2 {

    private final OrderRepositoryProxyV2 target;
    private final LogTrace logTrace;

    @Override
    public void save(String itemId) {
        TraceStatus status = null;
        try {
            status = logTrace.begin("OrderRepository_Concrete.request()");
            // 실제 객체 호출
            target.save(itemId);
            logTrace.end(status);
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
