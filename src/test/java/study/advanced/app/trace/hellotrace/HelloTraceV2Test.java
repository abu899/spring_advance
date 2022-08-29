package study.advanced.app.trace.hellotrace;

import org.junit.jupiter.api.Test;
import study.advanced.app.trace.TraceStatus;

class HelloTraceV2Test {

    @Test
    void beginEnd() {
        HelloTraceV2 trace = new HelloTraceV2();
        TraceStatus status = trace.begin("hello1");
        TraceStatus status2 = trace.beginSync(status.getTraceId(), "hello2");
        trace.end(status2);
        trace.end(status);
    }

    @Test
    void beginException() {
        HelloTraceV2 trace = new HelloTraceV2();
        TraceStatus status = trace.begin("hello");
        TraceStatus status2 = trace.beginSync(status.getTraceId(), "hello2");
        trace.exception(status2, new IllegalStateException());
        trace.exception(status, new IllegalStateException());
    }

}