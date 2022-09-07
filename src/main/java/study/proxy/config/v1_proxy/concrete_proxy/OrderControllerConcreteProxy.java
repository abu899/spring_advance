package study.proxy.config.v1_proxy.concrete_proxy;

import study.proxy.app.logtrace.LogTrace;
import study.proxy.app.trace.TraceStatus;
import study.proxy.app.v2.OrderControllerProxyV2;

public class OrderControllerConcreteProxy extends OrderControllerProxyV2 {

    private OrderControllerProxyV2 target;
    private LogTrace logTrace;

    public OrderControllerConcreteProxy(OrderControllerProxyV2 target, LogTrace logTrace) {
        super(null);
        this.target = target;
        this.logTrace = logTrace;
    }

    @Override
    public String request(String itemId) {
        TraceStatus status = null;
        try {
            status = logTrace.begin("OrderController_concrete.request()");
            // 실제 객체 호출
            String result = target.request(itemId);
            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }

    @Override
    public String noLog() {
        return target.noLog();
    }
}
