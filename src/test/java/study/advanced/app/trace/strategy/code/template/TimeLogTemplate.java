package study.advanced.app.trace.strategy.code.template;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeLogTemplate {

    public void execute(Callback callback) {
        long startTime = System.currentTimeMillis();
        // 비지니스 로직 수행
        callback.call(); // 위임
        // 비지니스 로직 종료
        long endTime = System.currentTimeMillis();
        log.info("result time = {}", endTime - startTime);
    }
}
