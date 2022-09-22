package study.aop.proxyvs;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import study.aop.member.MemberService;
import study.aop.member.MemberServiceImpl;
import study.aop.proxyvs.code.ProxyDIAspect;

@Slf4j
//@SpringBootTest(properties = {"spring.aop.proxy-target-class=false"}) // JDK 동적 프록시
@SpringBootTest(properties = {"spring.aop.proxy-target-class=true"}) // CGLIB 프록시
@Import(ProxyDIAspect.class)
public class ProxyDITest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberServiceImpl memberServiceImpl; // JDK 사용, 주입시 문제 발생

    @Test
    void go() {
        log.info("memberService = {}", memberService.getClass());
        log.info("memberServiceImpl = {}", memberServiceImpl.getClass());
        memberServiceImpl.hello("hi");
    }


}
