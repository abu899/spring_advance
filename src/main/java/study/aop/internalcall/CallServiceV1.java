package study.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV1 {

    private CallServiceV1 callServiceV1; // 자기 자신

    // 스프링이 빈을 생성하는 단계와 세터 주입하는 단계가 나뉘어있기 때문에 이렇게 자기 자신을 주입해야한다
    @Autowired
    public void setCallServiceV1(CallServiceV1 callServiceV1) {
        log.info("callServiceV1 in Setter = {}", callServiceV1.getClass());
        this.callServiceV1 = callServiceV1;
    }

    public void external() {
        log.info("call external");
        callServiceV1.internal(); // 외부 호출 메서드로 변경
    }

    public void internal() {
        log.info("call internal");
    }
}
