package study.advanced.app.trace.hellotrace;

import org.junit.jupiter.api.Test;
import study.advanced.app.trace.TraceStatus;

import static org.junit.jupiter.api.Assertions.*;

class HelloTraceV1Test {

    @Test
    void beginEnd() {
        HelloTraceV1 trace = new HelloTraceV1();
        TraceStatus status = trace.begin("hello");
        trace.end(status);
    }

    @Test
    void beginException() {
        HelloTraceV1 trace = new HelloTraceV1();
        TraceStatus status = trace.begin("hello");
        trace.exception(status, new IllegalStateException());
    }

}