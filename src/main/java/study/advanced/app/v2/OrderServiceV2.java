package study.advanced.app.v2;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.advanced.app.trace.TraceId;
import study.advanced.app.trace.TraceStatus;
import study.advanced.app.trace.hellotrace.HelloTraceV2;

@Service
@RequiredArgsConstructor
public class OrderServiceV2 {

    private final OrderRepositoryV2 orderRepository;
    private final HelloTraceV2 trace;


    public void orderItem(TraceId traceId, String itemId) {
        TraceStatus status = null;
        try{
            status = trace.beginSync(traceId, "OrderServiceV1.orderItem()");
            orderRepository.save(status.getTraceId(), itemId);
            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
