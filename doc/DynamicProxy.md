# 동적 프록시

이전 프록시 패턴을 사용했을 때, 클래스 수 만큼 프록시 클래스를 만들어야하는 단점을 확인할 수 있었다.
또한, 프록시 클래스들은 대부분 비슷한 코드를 공유하고 있는 모습으로 중복이 많다는 점도 단점이 될 수 있다.
하지만, 자바가 기본적으로 제공하는 `JDK 동적 프록시`나 `CGLIB` 같은 프록시 생성 기술을 활용하면 프록시 객체를 동적으로 생성할 수 있다.
즉, 프록시를 적용할 코드 하나만 만들어두고 프록시 객체를 찍어낼 수 있게되는 것이다.

## Reflection

동적 프록시를 이해하기 위해서는 사전에 리플렉션(Reflection) 기술을 이해해야한다.
리플렉션 기술은 클래스 또는 메서드의 메타정보를 동적으로 획득하고 코드 또한 동적으로 호출 가능한 기술이다.

- 장점
  - 클래스나 메서드 정보를 동적으로 변경할 수 있다.
  - `Class` 또는 `Method`라는 정보를 추상화를 통해 동적으로 제공할 수 있다.
- 주의
  - 런타임에 동작하기에 컴파일 시점에 오류를 잡을 수 없다
  - 즉 클래스 내 메서드 이름을 문자열로 적는데, 잘못 넣은 경우에도 컴파일이 되버림.
  - 따라서 일반적인 상황에서는 Reflection 은 사용하면 안된다

## JDK 동적 프록시

<p align="center"><img src="/img/dynamic_proxy/jdk.png" width="80%"></p>
<p align="center"><img src="/img/dynamic_proxy/jdk_1.png" width="80%"></p>

`인터페이스를 기반`으로 프록시 객체를 동적으로 런타임에 만들어준다.
JDK 동적 프록시에 적용할 로직은 `InvocationHandler` 인터페이스 구현해서 작성한다

- 파라미터
  - Object proxy : 프록시 자신
  - Method method : 호출할 메서드
  - Object[] args : 메서드 호출 시 파라미터 

JDK 동적 프록시는 인터페이스가 필수이기 때문에, 구현 클래스만 존재하는 경우 프록시를 적용할 수 없는 한계가 존재한다.

## CGLIB (Code Generate Library)

<p align="center"><img src="/img/dynamic_proxy/cg.png" width="80%"></p>

인터페이스가 아니더라도 프록시를 적용할 수 있는 방법. `MehtodInterceptor`의 구현을 통해 작성한다

- 특징
  - 바이트코드를 조작해서 동적으로 클래스를 생성하는 기술을 제공한다
  - 인터페이스가 없어도 구체 클래스만 가지고 동적 프록시를 만들어 낼 수 있다.
  - 현재 스프링에서는 `ProxyFactory`라는 것이 이것을 편리하게 사용할 수 있다
  - JDK 동적 프록시는 구현(`implement`)을 통해 프록시를 만들고 CGLIB 은 상속(`extends`)을 통해 프록시를 만든다
- 제약
  - 상속을 통해 구현되기 때문에, 부모 클래스의 생성자를 체크해야한다
    - 즉, 자식 클래스를 동적으로 생성하기 떄문에 기본 생성자가 필요하다
  - 클래스에 `final` 키워드가 붙거나, 메서드에 `final`이 붙으면 상속 또는 오버라이딩이 불가능하다

## ProxyFactory

<p align="center"><img src="/img/dynamic_proxy/pf.png" width="80%"></p>

이전 동적 프록시 기술의 한계

- 인터페이스가 있는 경우는 JDK 동적프록시를 적용하고, 그렇지 않으면 CGLIB 을 적용
- 두 기술을 함께 사용할 때, 부가기능을 제공하기 위한 클래스는 각각 `InvocationHandler`와 `MethodInterceptor`인데 이를 각각 개발해야 하나
- 특정 조건에 따라 프록시 로직을 적용하는 기능도 공통화 시켜야하나

<p align="center"><img src="/img/dynamic_proxy/pf_1.png" width="80%"></p>

스프링은 동적 프록시를 통합해서 편리하게 만들어주는 `ProxyFactory`라는 기능을 제공한다.
이전에는 상황에 따라 JDK 동적프록시나 CGLIB 을 선택해야하지만, 프록시 팩토리를 사용하면 편리하게 동적 프록시를 생성할 수 있다.

- 프록시 팩토리는 부가 기능을 적용할 때 `Advice`라는 개념을 도입했다.
  - 따라서, `InvocationHandler`나 `MethodInterceptor` 신경쓰지 않아도 된다.
  - 프록시 팩토리를 사용해 `Advice`를 호출하면, 내부적으로 `Handler`가 사용된다.
- 특정 조건에 로직을 적용하는 기능은 `Pointcut`이라는 개념을 도입했다.

### 포인트 컷, 어드바이스, 어드바이저

<p align="center"><img src="/img/dynamic_proxy/pf_2.png" width="80%"></p>


포인트 컷(Pointcut)

- 어디에 부가 기능을 적용할지, 어디에 부가 기능을 적용하지 말지 판단하는 `필터링 로직`
- 주로 클래스와 메서드 이름으로 필터링
- 어떤 포인트(point)에 기능을 적용할지 않을지 잘라서(cut) 구분
- 스프링이 제공하는 포인트 컷
  - NamedMatchMethodPointcut: 메서드 이름으로 매칭, 내부에 `PatternMatchUtils`를 사용한다
  - JDkRegexMethodPointcut: DJK 정규 표현식 기반 포인트컷 매칭
  - TruePointcut: 항상 참
  - AnnotationMatchingPointcut: 어노테이션으로 매칭
  - `AspectJExpressionPointcut`
    - aspectJ 표현식으로 매칭한다
    - 가장 중요하고, 실무에서 사용하기 편리하며 가장 많은 표현식을 사용한다

어드바이스(Advice)

- 프록시가 호출하는 부가 기능
- 즉, 프록시가 실제 호출하는 기능

어드바이저(Advisor)

- 1개의 포인트컷 + 1개의 어드바이스를 합쳐둔 것

포인트컷으로 `어디에 적용할지 선택`하고, 어드바이스로 `어떤 로직을 적용할지 선택`.
어드바이저는 `어디에 어떤 로직을 선택`할지 알고있다.

## 남은 문제

1. 너무 많은 Config 파일
   - `ProxyFactoryConfigV1`, `ProxyFactoryConfigV2` 내용이 중복되며 설정파일이 너무 많다.
   - 프록시를 통해 적용할 스프링 빈의 갯수만큼 프록시 생성 코드를 만들어야한다.
   - 또한 요샌 `Bean`을 직접 사용하지 않고 `ComponentScan`을 사용하는데 `직접 빈 등록 + 프록시 적용 코드`를 작업해야한다 
2. 컴포넌트 스캔
   - 컴포넌트 스캔 방법으로는 지금까지 학습한 방법으로는 프록시 적용 불가...
     - 왜냐면 지금까지는 `프록시를 스프링 빈으로 등록`하는 반면, 컴포넌트 스캔은 스프링이 실제 객체를 스프링 빈에 그냥 바로 등록해버린다
     - 즉, 우리가 끼어들 여지가 없음.

## 빈 후처리기 (Bean Post Processor)

<p align="center"><img src="/img/dynamic_proxy/bean.png" width="80%"></p>

`@Bean`이나 `ComponentScan`을 통해 스프링 빈을 등록하게 되면 위와 같은 순서로 스프링 빈 저장소에 등록하게된다.
빈 후처리기는 `생성 후 등록을 할 시점(1->2)`에 조작을 가하고자 할 때 사용한다. 즉, 빈을 생성한 후 무언가를 처리하는 용도로
사용된다.

### 과정

<p align="center"><img src="/img/dynamic_proxy/bean_1.png" width="80%"></p>

여기서 빈 후처리기의 `후 처리 작업`에서는 전달된 스프링 빈 객체를 조작하거나 아예 다른 객체로 바꿔치기 할 수 있다.

- BeanPostProcessor Interface
  - 인터페이스를 구현하고 스프링 빈으로 등록하여 사용한다
  - `postProcessorBeforeInitialization`
    - 객체 생성 이후 `@PostConstruct` 같은 초기화 발생 전 호출
  - `postProcessorAfterInitialization`
    - 객체 생성 이후 `@PostConstruct` 같은 초기화 발생 후 호출

빈 후처리기는 빈을 조작하고 변경할 수 있는 `Hooking Point`. 일반적으로 컴포넌트 스캔의 대상이 되는 빈들은
중간에 조작할 방법이 없는데, 빈 후처리기를 통해 빈을 조작할 수 있다. 즉, `Bean 객체를 프록시로 교체`하는 것 또한 가능하다

## 스프링이 제공하는 빈 후처리기

<p align="center"><img src="/img/dynamic_proxy/bean_2.png" width="80%"></p>

- AutoProxyCreator
  - 스프링 부트의 자동 설정으로 `AnnotationAwareAspectJAutoProxyCreator`라는 빈 후처리기가 스프링 빈에 자동 등록된다
  - 이 후처리기는 스프링 빈으로 등록된 `Advisor`를 자동으로 찾아 프록시가 필요한 곳에 자동으로 적용해준다
  - Advisor 내의 `Pointcut`으로 어떤 스프링 빈에 프록시를 적용할 지 알 수 있고, `Advice`로 부가 기능을 적용한다

### 포인트 컷이 적용되는 2가지 상황

1. 프록시 적용 여부 판단 - 생성
   - 자동 프록시 생성기에서 포인트컷을 통해 해당 빈을 프록시로 생성할 지 아닐 지 체크
   - 클래스 + 메서드 조건을 모두 비교
   - 하나라도 조건에 맞다면 프록시를 생성한다
2. 어드바이스 적용 여부 판단 - 사용
   - 프록시가 호출되었을 때 부가기능인 어드바이스를 적용할지 말지를 판단
   - 프록시로 생성된 클래스 중에서도 어드바이스가 적용하지 말아야할 메서드가 존재할 수 있으므로.

## @Aspect Proxy

<p align="center"><img src="/img/dynamic_proxy/aspect.png" width="80%"></p>

이전에 작성한 `AutoProxyConfig`를 보면 어드바이저를 스프링빈으로 등록하고 자동 프록시 생성기로 프록시를 자동으로 생성해준다.
스프링에서는 `@Aspect` 어노테이션으로 편리하게 포인트컷과 어드바이스로 구성된 어드바이저 생성 기능을 제공한다
> `@Aspect`는 AOP 를 가능하게하는 AspectJ 프로젝트에서 제공하는 어노테이션

<p align="center"><img src="/img/dynamic_proxy/aspect_1.png" width="80%"></p>

앞서 자동 프록시 생성기였던, `AnnotationAwareAspectJAutoProxyCreator`은 
- 빈으로 등록된 `Advisor`을 찾아서 등록해주는 기능
- `@Aspect` 어노테이션을 찾아서 `Advisor`로 만들어 변환 후 저장한다.

최종적으로 어드바이저를 기반으로 프록시를 생성하는 과정은 다음과 같다.

<p align="center"><img src="/img/dynamic_proxy/aspect_2.png" width="80%"></p>
