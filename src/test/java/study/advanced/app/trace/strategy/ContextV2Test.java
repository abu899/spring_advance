package study.advanced.app.trace.strategy;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import study.advanced.app.trace.strategy.code.strategy.ContextV2;
import study.advanced.app.trace.strategy.code.strategy.Strategy;
import study.advanced.app.trace.strategy.code.strategy.StrategyLogic1;
import study.advanced.app.trace.strategy.code.strategy.StrategyLogic2;

@Slf4j
public class ContextV2Test {

    @Test
    void strategyV0() {
        logic1();
        logic2();
    }

    private void logic1() {
        long startTime = System.currentTimeMillis();
        // 비지니스 로직 수행
        log.info("비지니스 로직1 실행");
        // 비지니스 로직 종료
        long endTime = System.currentTimeMillis();
        log.info("result time = {}", endTime - startTime);
    }

    private void logic2() {
        long startTime = System.currentTimeMillis();
        // 비지니스 로직 수행
        log.info("비지니스 로직2 실행");
        // 비지니스 로직 종료
        long endTime = System.currentTimeMillis();
        log.info("result time = {}", endTime - startTime);
    }

    /**
     * 전략 패턴 사용 - 파라미터 사용
     */
    @Test
    void strategyV1() {
        ContextV2 context = new ContextV2();
        context.execute(new StrategyLogic1());
        context.execute(new StrategyLogic2());
        context.execute(new Strategy() {
            @Override
            public void call() {
                log.info("비지니스 로직 3 실행");
            }
        });
        context.execute(() -> log.info("비지니스 로직 4 실행"));
    }
}
