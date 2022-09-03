package study.advanced.app.trace.strategy;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import study.advanced.app.trace.strategy.code.strategy.ContextV1;
import study.advanced.app.trace.strategy.code.strategy.Strategy;
import study.advanced.app.trace.strategy.code.strategy.StrategyLogic1;
import study.advanced.app.trace.strategy.code.strategy.StrategyLogic2;

@Slf4j
public class ContextV1Test {

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
     * 전략 패턴 사용
     */
    @Test
    void strategyV1() {
        StrategyLogic1 strategyLogic1 = new StrategyLogic1();
        ContextV1 context1 = new ContextV1(strategyLogic1);
        context1.execute();
        StrategyLogic2 strategyLogic2 = new StrategyLogic2();
        ContextV1 context2 = new ContextV1(strategyLogic2);
        context2.execute();
    }

    /**
     * 전략 패턴 사용 - 익명 내부 클래스 사용
     */
    @Test
    void strategyV2() {
        Strategy strategy1 = new Strategy() {
            @Override
            public void call() {
                log.info("비지니스 로직 1 실행");
            }
        };
        ContextV1 context1 = new ContextV1(strategy1);
        context1.execute();

        ContextV1 context2 = new ContextV1(new Strategy() {
            @Override
            public void call() {
                log.info("비지니스 로직 2 실행");
            }
        });
        context2.execute();

        // 람다 - 인터페이스에 함수가 1개만 있을 때

        Strategy strategy3 = () -> log.info("비지니스 로직 3 실행");
        ContextV1 context3 = new ContextV1(strategy3);
        context3.execute();

        ContextV1 context4 = new ContextV1(() -> log.info("비지니스 로직 4 실행"));
        context4.execute();
    }

    /**
     * 전략 패턴 사용 - 람다 사용
     * 인터페이스에 함수가 1개만 있을 때
     */
    @Test
    void strategyV3() {
        Strategy strategy1 = () -> log.info("비지니스 로직 1 실행");
        ContextV1 context1 = new ContextV1(strategy1);
        context1.execute();

        ContextV1 context2 = new ContextV1(() -> log.info("비지니스 로직 4 실행"));
        context2.execute();
    }
}
