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
class CallServiceV3Test {

    @Autowired CallServiceV3 callServiceV3;

    @Test
    void external() {
        log.info("target = {} ", callServiceV3.getClass());
        callServiceV3.external();
    }
}