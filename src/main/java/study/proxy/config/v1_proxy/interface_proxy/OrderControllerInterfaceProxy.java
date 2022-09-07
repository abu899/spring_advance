package study.proxy.config.v1_proxy.interface_proxy;

import lombok.RequiredArgsConstructor;
import study.proxy.app.logtrace.LogTrace;
import study.proxy.app.trace.TraceStatus;
import study.proxy.app.v1.OrderControllerProxyV1;

@RequiredArgsConstructor
public class OrderControllerInterfaceProxy implements OrderControllerProxyV1 {

    private final OrderControllerProxyV1 target;
    private final LogTrace logTrace;

    @Override
    public String request(String itemId) {
        TraceStatus status = null;
        try {
            status = logTrace.begin("OrderController_Interface.request()");
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
