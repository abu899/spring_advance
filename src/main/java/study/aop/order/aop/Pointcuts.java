package study.aop.order.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    @Pointcut("execution(* study.aop.order..*(..))")
    public void allOrder() {}

    // 클래스 이름 패턴이 *Service 인 것만
    @Pointcut("execution(* *..*Service.*(..))")
    public void allService() {}

    @Pointcut("allOrder() && allService()")
    public void orderAndService() {}
}
