package study.aop.proxyvs;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import study.aop.member.MemberService;
import study.aop.member.MemberServiceImpl;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
public class ProxyCastingTest {

    @Test
    void jdkProxy() {
        // 인터페이스도 있고, 구체 클래스도 존재한다
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(false); // JDK 동적 프록시로 생성

        // 프록시를 인터페이스로 캐스팅
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

        // 프록시를 구체클래스로 캐스팅 (구체클래스의 존재 자체를 모름)
//        MemberServiceImpl proxyImpl = (MemberServiceImpl) memberServiceProxy; // 오류(ClassCastException)
        assertThatThrownBy(() -> {
                    MemberServiceImpl proxyImpl = (MemberServiceImpl) memberServiceProxy;
                })
                .isInstanceOf(ClassCastException.class);
    }

    @Test
    void cglibProxy() {
        // 인터페이스도 있고, 구체 클래스도 존재한다
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true); // JDK 동적 프록시로 생성

        // 프록시를 인터페이스로 캐스팅
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

        // 프록시를 구체클래스로 캐스팅
        MemberServiceImpl proxyImpl = (MemberServiceImpl) memberServiceProxy; // 성공
    }
}
