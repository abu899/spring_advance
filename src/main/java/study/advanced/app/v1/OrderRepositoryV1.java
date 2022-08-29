package study.advanced.app.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.advanced.app.trace.TraceStatus;
import study.advanced.app.trace.hellotrace.HelloTraceV1;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV1 {

    private final HelloTraceV1 trace;

    public void save(String itemId) {

        TraceStatus status = null;
        try{
            status = trace.begin("OrderRepositoryV1.save()");
            if (itemId.equals("ex")) {
                throw new IllegalStateException("Throw exception");
            }

            sleep(1000);

            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }


    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
