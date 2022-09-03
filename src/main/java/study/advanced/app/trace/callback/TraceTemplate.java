package study.advanced.app.trace.callback;

import lombok.RequiredArgsConstructor;
import study.advanced.app.logtrace.LogTrace;
import study.advanced.app.trace.TraceStatus;

@RequiredArgsConstructor
public class TraceTemplate {

    private final LogTrace trace;

    public <T> T execute(String message, TraceCallback<T> callback) {
        TraceStatus status = null;
        try {
            status = trace.begin(message);

            //로직 호출
            T result = callback.call();

            trace.end(status);
            return result;
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
}
