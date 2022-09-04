package study.proxy.app.trace.callback;

public interface TraceCallback<T> {
    T call();
}
