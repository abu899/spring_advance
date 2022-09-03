package study.advanced.app.trace.template;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import study.advanced.app.trace.template.code.AbstractTemplate;
import study.advanced.app.trace.template.code.SubClassLogic1;
import study.advanced.app.trace.template.code.SubClassLogic2;

@Slf4j
public class TemplateMethodTest {

    @Test
    void templateMethodV0() {
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
     * 템플릿 메서드 패턴 적용
     */
    @Test
    void templateMethodV1() {
        AbstractTemplate template1 = new SubClassLogic1();
        template1.execute();
        AbstractTemplate template2 = new SubClassLogic2();
        template2.execute();
    }

    /**
     * 템프릿 메서드 패턴 적용 - 익명 내부 클래스를 사용
     */
    @Test
    void templateMethodV2() {
        AbstractTemplate template1 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info("비지니스 로직1 실행");
            }
        };

        AbstractTemplate template2 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info("비지니스 로직2 실행");
            }
        };
        template1.execute();
        template2.execute();
    }
}
