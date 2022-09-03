package study.advanced.app.trace.template.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractTemplate {
    public void execute() {
        long startTime = System.currentTimeMillis();
        // 비지니스 로직 수행
        call();
        // 비지니스 로직 종료
        long endTime = System.currentTimeMillis();
        log.info("result time = {}", endTime - startTime);
    }

    protected abstract void call();
}
