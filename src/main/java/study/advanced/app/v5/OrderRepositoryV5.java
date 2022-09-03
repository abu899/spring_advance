package study.advanced.app.v5;

import org.springframework.stereotype.Repository;
import study.advanced.app.logtrace.LogTrace;
import study.advanced.app.trace.callback.TraceTemplate;

@Repository
public class OrderRepositoryV5 {

    private final TraceTemplate traceTemplate;

    public OrderRepositoryV5(LogTrace logTrace) {
        this.traceTemplate = new TraceTemplate(logTrace);
    }

    public void save(String itemId) {

        traceTemplate.execute("OrderRepository.save()", () ->
                {
                    if (itemId.equals("ex")) {
                        throw new IllegalStateException("Throw exception");
                    }
                    sleep(1000);
                   return null;
                });
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
