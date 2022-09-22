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
class CallServiceV2Test {

    @Autowired CallServiceV2 callServiceV2;

    @Test
    void external() {
        // 디폴트로 순환참조는 불가하므로, config 에서 변경이 필요하다.
        log.info("target = {} ", callServiceV2.getClass());
        callServiceV2.external();
    }
}