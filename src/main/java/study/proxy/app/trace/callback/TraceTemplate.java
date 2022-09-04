package study.proxy.app.trace.callback;

import lombok.RequiredArgsConstructor;
import study.proxy.app.logtrace.LogTrace;
import study.proxy.app.trace.TraceStatus;

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
