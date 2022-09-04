package study.proxy.app.trace.template;

import study.proxy.app.logtrace.LogTrace;
import study.proxy.app.trace.TraceStatus;

public abstract class AbstractTemplate<T> {

    private final LogTrace trace;

    public AbstractTemplate(LogTrace trace) {
        this.trace = trace;
    }

    public T execute(String message) {
        TraceStatus status = null;
        try {
            status = trace.begin(message);

            //로직 호출
            T result = call();

            trace.end(status);
            return result;
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    protected abstract T call();
}
