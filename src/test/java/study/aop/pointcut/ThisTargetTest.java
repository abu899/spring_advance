package study.aop.pointcut;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import study.aop.member.MemberService;

@Slf4j
//@SpringBootTest(properties = "spring.aop.proxy-target-class=false") // yml 이나 SpringBootTest 에 설정
@SpringBootTest
@Import(ThisTargetTest.ThisTargetAspect.class)
public class ThisTargetTest {

    @Autowired
    MemberService memberService;

    @Test
    void success() {
        log.info("memberService proxy = {}", memberService.getClass());
        memberService.hello("ThisTargetTest");
    }

    @Slf4j
    @Aspect
    static class ThisTargetAspect {

        // 부모 타입 허용
        @Around("this(study.aop.member.MemberService)")
        public Object doThisInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this_interface] = {} ", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        // 부모 타입 허용
        @Around("target(study.aop.member.MemberService)")
        public Object doTargetInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target_interface] = {} ", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        @Around("this(study.aop.member.MemberServiceImpl)")
        public Object doThisConcrete(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this_concrete] = {} ", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        // 부모 타입 허용
        @Around("target(study.aop.member.MemberServiceImpl)")
        public Object doTargetConcrete(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target_concrete] = {} ", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }
}
