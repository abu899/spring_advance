# Aspect Oriented Programming

어플리케이션은 핵심 기능과 부가 기능으로 나눌 수 있다.
- 핵심 기능
  - OrderService 내의 주문 로직
- 부가 기능
  - 핵심 기능을 보조하기 위한 기능
  - 로그 추적, 트랜잭션 등
  - 부가 기능은 단독으로 사용되지 않고 핵심 기능과 함께 사용된다

<p align="center"><img src="/img/aop/aop.png" width="80%"></p>

보통 부가 기능은 여러 클래스에 걸쳐서 함께 사용된다. 다수의 클래스에서 로그를 찍어야하는 로깅과 같이 여러 곳에서
동일하게 사용되고 이러한 부가 기능은 `횡단 관심사(cross-cutting concern`이 된다. 하지만, 부가기능을 여러 곳에서 적용하려고
해도, 적용해야하는 클래스가 늘어날 수록 동일한 코드가 추가되어야 하며 수정이 발생하게되면 적용된 모든 곳을 수정해줘야하는 문제가 생긴다.

- 부가 기능 적용의 문제
  - 부가 기능 적용시, 많은 반복이 필요하다
  - 부가 기능이 여러 곳에 존재하기에 중복 코드가 생성된다
  - 부가 기능을 변경하게 되면 중복으로 인한 많은 수정이 필요하다
  - 부가 기능의 적용 대상이 변경되는 상황에서도 많은 수정이 필요하다

따라서, 소프트웨어 개발의 변경지점이 하나가 될 수 있도록 잘 모듈화 되어야 한다.

## Aspect

부가 기능 적용의 문제점에 대한 고민의 결과 `부가 기능을 핵심 기능과 분리`하고 `한 곳에서 관리`하도록 했다.
추가적으로 해당 부가 기능을 어디에 적용할지 선택하는 기능 또한 만들었다.
> 부가 기능 + 부가 기능을 어디에 적용할지 선택하는 기능 -> 하나의 모듈(Aspect)

<p align="center"><img src="/img/aop/aop_1.png" width="80%"></p>

어플리케이션을 바라보는 `관점을 개별적인 기능에서 횡단 관심사로` 관점을 달리 보는 것이다. AOP 는 OOP 를 대체하는게 아니라
횡단 관심사를 간결하게 처리하기 어려운 OOP 를 보조하는 목적으로 탄생했다.

- AspectJ Framework
  - AOP 의 대표적인 구현
    - 스프링도 AOP 를 지원하지만 AspectJ 의 문법을 차용하고, AspectJ 기능의 일부만 제공
  - 횡단 관심사의 깔끔한 모듈화
    - 오류 검사 및 처리(Validation)
    - 동기화
    - 성능 최적화(캐싱)
    - 모니터링 및 로깅

## AOP 의 적용 방식

AOP 를 통한 부가 기능 로직은 어떤 방식으로 실제 로직에 추가될 수 있을까?

### 컴파일 시점

<p align="center"><img src="/img/aop/weaving.png" width="80%"></p>

`.java` 코드가 컴파일러를 통해 `.class`가 만들어 지는 시점에 부가기능 로직을 추가할 수 있다.
이때 사용되는 컴파일러는 AspectJ 가 제공하는 특별한 컴파일러로, 생성된 `.class`파일에는 부가 기능관련 코드가 들어가게 된다.
이렇게 원본 로직에 부가기능 로직이 직접 추가되는 것을 `위빙(Weaving)`이라고 한다.

- 단점
  - 특별한 컴파일러가 필요하며 복잡하다

### 클래스 로딩 시점

<p align="center"><img src="/img/aop/weaving_1.png" width="80%"></p>


자바가 실행되면 `.class` 파일이 JVM 내부의 클래스 로더(Class Loader)에 보관하게 되는데, 이때 `.class` 파일을 조작해
부가 기능을 추가한 `.class`파일을 클래스 로더에 올리는 방법이다. 이를 `로드 타임 위빙` 이라고 한다.

- 단점
  - 자바를 실행할 때 특별한 옵션(`java -javaagent`)를 통해 클래스 로더 조작기를 지정해야하는데 이 부분이 번거로움.

### 런타임 시점(Proxy)

이미 자바가 다 실행되고 main 메서드가 실행된 다음 수행하는 방법이다. 스프링과 같은 컨테이너의 도움을 받고 프록시, DI, 빈 후처리기와
같은 많은 개념을 이용해 이를 구현할 수 있다. 지금까지 학습한 내용이 프록시 방식의 AOP 이다.

- 단점
  - 프록시를 이용하기 때문에 AOP 기능에 일부 제약이 있음
    - `final` 에 대한 상속 및 오버라이딩 불가
    - 생성자에 대해 AOP 를 적용할 수 없음, Only method
    - 항상 프록시를 통해야 부가기능을 사용할 수 있음
  - 프록시를 이용하기에 항상 프록시를 만들어야함
- 적용 위치 (Join point)
  - 프록시는 오버라이딩 개념으로 동작한다.
    - 따라서, 생성자나 static 메서드, 필드 값 접근에는 프록시 개념이 적용될 수 없음
  - 스프링 AOP 의 조인 포인트는 `메서드 실행으로 제한`된다.
  - 또한 스프링 `컨테이너가 관리할 수 있는 스프링 빈에만` AOP 를 적용할 수 있다.

## AOP 용어 정리

<p align="center"><img src="/img/aop/aop_2.png" width="80%"></p>

- Join Point
  - AOP(어드바이스)를 적용될 수 있는 위치
  - 추상적인 개념이며, AOP 를 적용할 수 있는 모든 지점
  - `스프링 AOP`는 프록시 방식을 사용하므로, 항상 `메소드 실행 지점이 조인 포인트`가 된다
- Pointcut
  - 조인포인트들 중에서 어드바이스가 적용될 위치를 선별하는 기능
  - AspectJ 표현식을 사용해서 지정
  - 마찬가지로 스프링 AOP 는 메소드 실행지점만 포인트 컷으로 선별 가능
- Target
  - 어드바이스를 받는 객체
  - 즉, 포인트 컷으로 결정되는 객체
- Advice
  - 부가 기능 그 자체
  - Around, Before, After 와 같은 여러 종류의 어드바이스가 존재
- Aspect
  - 어드바이스 + 포인트컷을 모듈화 한 것(@Aspect)
  - 여러 어드바이스와 포인트컷이 함께 존재
- Advisor
  - 스프링 AOP 에서만 사용되는 용어로, 1개의 어드바이스와 1개의 포인트컷으로 구성
- Weaving
  - 포인트컷으로 결정된 타겟의 조인포인트에 어드바이스를 적용한 것
  - 위빙을 통해 핵심 기능 코드에 영향을 주지않고 부가 기능을 추가할 수 있다
- AOP 프록시
  - AOP 기능을 구현하기 위해 만든 프록시 객체
  - 스프링에서의 AOP 프록시는 JDK 동적 프록시 또는 CGLIB 프록시

## 구현

### @Aspect 의 순서

어드바이스는 기본적으로 순서를 보장하지 않으며 `@Aspect` 단위로 `@Order`를 적용해야 순서가 적용된다.
즉, `@Around`에 `@Order`를 지정해도 순서가 제대로 동작하지 않는다.
따라서, 하나의 에스팩트에 여러 어드바이스가 있다면 에스펙트를 별도의 클래스로 분리해야한다.
```java
public class AspectV5Order {
    @Aspect
    @Order(2)
    public static class LogAspect {
        @Around("study.aop.order.aop.Pointcuts.allOrder()")
        public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        }
    }

    @Aspect
    @Order(1)
    public static class TransactionAspect {
        @Around("study.aop.order.aop.Pointcuts.orderAndService()")
        public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        }
    }
}
```

### 어드바이스의 종류

<p align="center"><img src="/img/aop/advice.png" width="80%"></p>

1. @Around
   - 메서드 호출 전 후에 실행 됨
   - 가장 강력한 어드바이스
   - 조인 포인트 실행 여부 선택, 반환 값 변환, 예외 변환 등이 가능
2. @Before
   - 조인 포인트 실행 이전에 실행
   - Around 와 달리 작업 흐름을 변경(proceed)할 순 없으며, 다음 대상을 직접 호출할 필요는 없다.
3. @AfterReturning
   - 조인 포인트 실행 이후에 실행
   - 반환 값 변환은 불가능하다
   - returning 에 지정한 타입 값을 반환하는 메서드만 대상으로 한다
4. @AfterThrowing
   - 조인 포인트가 예외 일 때 실행
   - 예외도 마찬가지로 throwing 에 지정된 예외 타입만 대상으로 한다
5. @After
   - 조인 포인트가 정상 또는 예외와 상관없이 실행
   - finally

> @Around 는 ProceedingJoinPoint 를 파라미터로 받고 그 외는 JoinPoint 를 받는다.
> ProceedingJoinPoint 에는 proceed() 가 존재하고, 이를 통해 다음 어드바이스나 타겟을 호출할 수 있다
   
사실상 `@Around`만 있으면 다른 어드바이스의 기능을 모두 사용할 수 있다. 하지만, 조인 포인트 실행 여부를 선택할 수 있다는
장점이 한편으론 실수로 호출하지 않는 상황이 발생하여 심각한 버그가 발생할 수 있다. 따라서, 다른 어드바이스를 통해 실수의 가능성을
줄이고 코드가 간결해진다. 또한 어노테이션 자체가 실행 위치를 한정하기 때문에 의도를 파악하기 쉬워진.

## Pointcut Detail

### Pointcut Designator(PCD, 포인트컷 지시자)

- execution
  - 메소드 실행 조인 포인트 매칭
  - 스프링 AOP 에서 가장 많이 사용하고 기능도 복잡하다
- within
  - 특정 타입 내의 조인포인트를 매칭
- args
  - 인자가 주어진 타입의 인스턴스 조인 포인트
- this
  - 스프링 빈 객체(스프링 AOP 프록시)를 대상으로 하는 조인포인트
- target
  - Target 객체(스프링 AOP 프록시가 가리키는 실제 대상)를 대상으로 하는 조인포인트
- @target
  - 실행 객체의 클래스에 위 어노테이션이 있는 조인포인트
- @within
  - 위 어노테이션이 있는 타입 내 조인포인트
- @annotation
  - 메서드가 위 어노테이션을 가지고 있는 조인포인트를 매칭
- @args
  - 전달된 실제 인수의 런타임 타입이 위 어노테이션을 갖는 조인 포인트
- bean
  - 스프링 전용 포인트컷 지시자
  - 빈의 이름으로 포인트 컷을 지정

사실상, `execution`을 가장 많이 사용하고 나머지는 자주 사용되지는 않는다.

### execution

```text
execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?namepattern(param-pattern)
 throws-pattern?)
 
execution(접근제어자? 반환타입 선언타입?메서드이름(파라미터) 예외?)
```

- `?`는 생략 가능
- pattern 이라고 붙어있는데 `*`과 같은 패턴을 지정할 수 있다는 의미
- 메소드 실행 조인 포인트를 매칭한다
- Detail
  - 접근 제어자 : public (생략 가능)
  - 반환타입 : String
  - 선업타입 : study.aop.member.MemberServiceImpl(생략가능)
  - 메서드 이름: hello
  - 파라미터 : String
  - 예외 : 생략 가능
- 하위 패키지를 포함하기 위해선 `..`을 사용해야 한다
- execution 에서 부모타입을 선언해도 자식 타입은 매칭된다
  - 하지만, 부모타입에 있는 메서드(override)만 허용된다.

### within

해당 타입이 매칭되면 그 안의 메서드들은 자동으로 매칭된다. execution 에서 타입부분만 사용한다고 보면된다.

- 주의점
  - 표현식에 부모타입을 지정하면 안된다

### args

execution 의 args 부분과 같다고 보면된다.

- 차이점
  - execution 은 타입이 정확하게 매칭되야 하지만, args 는 부모타입을 허용한다.
  - execution 은 클래스에 선언된 정보를 기반으로 판단하지만, args 는 실제 넘어온 파라미터 객체 인스턴스롤 보고 판단한다.

### @target, @within

<p align="center"><img src="/img/aop/tw.png" width="80%"></p>

- @target 은 인스턴스의 모든 메서드를 조인 포인트로 적용한다
- @within 은 해당 타입 내에 있는 메서드만 조인포인트로 적용한다
- 주의사항
  - `args, @args, @target`의 포인트컷 지시자는 단독으로 사용하면 안된다
    -즉, `execution(* study.aop..*(..))`와 같은 대상을 한정해줘야한다
  - 위 포인트컷 지시자는 실제 객체 인스턴스가 생성되고 실행될때 어드바이스 적용 여부를 확인할 수 있다.
    - 따라서, 포인트컷 적용 여부는 프록시가 있어야 실행시점에 판단이 가능하며 `프록시가 없다면 판단이 불가능`하다.
    - 하지만 `프록시는 스프링 컨테이너가 만들어지는 어플리케이션 로딩시점에 생성`되는데, 대상을 한정하지 않고 사용한다면
    `모든 스프링 빈에 대해 AOP 프록시를 적용하려고 하고 이때 final 로 선언된 빈들에서 에러`가 발생한다.

### bean

스프링 전용 포인트컷 지시자로 빈의 이름으로 지정한다.

- `bean(orderService) || bean(*Repository)`
- 패턴을 사용 가능하다.

### 매개변수 전달

`this, target, args, @target, @annotation, @args` 는 어드바이스에 매개변수를 전달 가능하다.
이때, 포인트컷의 이름과 매개변수의 이름을 맞추어야 하며, 타입이 메서드에서 지정한 타입으로 제한된다.

## this, target

this, target 은 적용 대상 타입을 정확하게 하나 지정해줘야 한다. 즉, 패턴 같은것 지정이 안되지만, 부모타입은 허용한다.
내용이 복잡하긴하지만, 보다시피 하나만 지정하기 때문에 범용성이 떨어지기에 잘 사용하지는 않는다.

- this: 프록시 객체(스프링 빈 객체)를 대상으로 한다.
- target: 실제 호출하는, 가리키는(target 객체) 대상으로 한다.

<p align="center"><img src="/img/aop/tt.png" width="80%"></p>
<p align="center"><img src="/img/aop/tt_1.png" width="80%"></p>

JDK 동적 프록시와 CGLIB 을 통해 스프링은 프록시를 생성하는데, 둘의 프록시 생성 방식이 다르기 때문에 차이가 생긴다.
결과적으로 `this의 경우 구체 클래스를 지정하면 프록시 생성 전략에 따라 결과가 달라`질 수 있다.

- JDK 동적 프록시: 인터페이스가 필수, `인터페이스를 구현한 프록시 객체`를 생성
  - MemberService 인터페이스 지정
    - this 와 target 모두 같은 값을 나타내며, `AOP 적용 대상`이 된다
  - MemberServiceImpl 구체 클래스 지정
    - this 는 프록시 객체 자체만을 보고 판단하기에, MemberServiceImpl 을 알 수 없어 `AOP 적용 대상 제외`.
    - target 은 객체를 보고 판단하므로 `AOP 적용 대상`이 된다 
- CGLIB : 인터페이스가 있더라도 `구체 클래스를 상속받아서 프록시 객체`를 생성
  - MemberService 인터페이스 지정
    - this 와 target 모두 같은 값을 나타내며, `AOP 적용 대상`이 된다
  - MemberServiceImpl 구체 클래스 지정
    - this 는 MemberServiceImpl 라는 프록시 객체를 가지고 판단하므로 `AOP 적용 대상`이 된다.
    - target 은 객체를 보고 판단하므로 `AOP 적용 대상`이 된다 
- 참고로 this, target 지시자는 단독으로 보단 파라미터 바인딩에서 주로 사용된다.