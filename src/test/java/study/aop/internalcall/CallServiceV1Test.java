package study.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import study.aop.internalcall.aop.CallLogAspect;

@SpringBootTest
@Import(CallLogAspect.class)
@Slf4j
class CallServiceV1Test {

    @Autowired CallServiceV1 callServiceV1;

    @Test
    void external() {
        // 디폴트로 순환참조는 불가하므로, config 에서 변경이 필요하다.
        log.info("target = {} ", callServiceV1.getClass());
        callServiceV1.external();
    }
}