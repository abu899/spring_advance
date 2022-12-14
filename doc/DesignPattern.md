# 스프링에서 자주 사용하는 패턴

좋은 설계는 단순히 소스코드 몇 줄을 줄이는게 목적이 아니라, 변경이 있을 때 변경에 쉽게 대처할 수 있게하는 것.

## Template Method Pattern

<p align="center"><img src="/img/design_pattern/dp_1.png" width="80%"></p>

- 목적
  - 작업에서 알고리즘의 골격(템플릿)을 정의하고 하위 클래스로 구현한다. 
    - 변하는 것과 변하지 않는 것을 분리해서 모듈화 할 수 있다
    - 결국 상속과 오버라이딩을 통해 다형성으로 문제를 해결하는 것.
  - 단일 책임 원칙(SRP)를 통해, 변하지 않는 부분에 대한 변경 지점을 하나로 모아 변경에 쉽게 대처할 수 있는 구조를 만드는 것.
- 단점
  - 변하는 부분에 대한 서브클래스(SubClass1, SubClass2)를 계속 생성해야한다
    - 익명 내부클래스를 사용해 이를 보완할 수 있다
  - 상속에서 오는 단점
    - 자식 클래스가 부모 클래스와 컴파일시점에 강결합(Strong)된다
      - 즉, 자식 클래스 입장에서 부모클래스의 기능을 전혀 사용하지 않음.
      - 또한 자식 클래스는 부모클래스의 모든 기능을 내려받을 필요도 없다.
    - 부모 클래스에서 변경이 있다면 자식클래스도 영향을 받는다

## Strategy Pattern

<p align="center"><img src="/img/design_pattern/dp_2.png" width="80%"></p>

- 목적
  - `Context`는 변하지 않는 템플릿 역할을 하고, 변하는 부분은 `Strategy`로 알고리즘 역할을 한다
  - 상속이 아닌 위임으로 문제를 해결하는 것
  - 선 조립, 후 실행 방식
    - 컨텍스트 내에 미리 실행할 전략을 넣어둔고 나중에 실행만하는 방식이다
    - 스프링처럼 의존관계를 모두 주입해두고, 실제 요청을 처리하는 것과 같은 원리이다  
  - 전략 패턴은 필드에 저장하는 방식과 파라미터로 전달하는 방식이 존재
    - 필드에 저장하는 방식은 전략이 이미 설정된 상태기 때문에 단순히 실행만 하면된다 
    - 하지만,파라미터로 전달하는 방식은 전략을 실행할 때마다 전략을 지정해줘야하지만, 보다 유연하게 동작한다
- 단점
  - 전략을 변경하기가 번거롭다
    - `setter`를 통해 변경할 수 있지만, 컨텍스트를 싱글톤으로 사용하는 경우 고려해야되는 부분이 존재한다
    - 따라서 전략을 실시간으로 변경해야한다면, 컨텍스트를 새롭게 생성해서 전략을 주입하는게 더 나은 선택일 수 있다

## Template Callback Pattern

- 정의
  - `Strategy pattern`과 같이 다른 코드의 인수로서 넘겨주는 실행 가능한 코드를 콜백이라고 한다.
  콜백은 필요에 따라 즉시 또는 나중에 필요에 따라 실행 할 수 있다.
  - 코드가 호출(call)은 되는데 실행은 코드를 넘겨준 곳의 뒤(back)에서 실행
```text
    ContextV2 context = new ContextV2();
    context.execute(new StrategyLogic1());
    context.execute(new StrategyLogic2());
```
 위의 예제에서 보면, 클라이언트에서 직접 `Strategy`를 실행한게 아닌, `execute`를 실행할 때, `Strategy`를 넘겨주고
ContextV2 뒤에서 `Strategy`가 실행된다. 자바에서는 실행가능한 코드를 인수로 넘기기 위해선 객체 또는 람다를 통해 넘길 수 있다.
- 스프링 내부에서는 전략패턴과 같은 패턴을 `Template Callback Pattern`이라고 부른다
  - GOF 패턴이 아닌, 스프링 내부에서 이 방식을 자주 사용하기에 스프링에서만 이렇게 명칭함
  - `JdbcTemplate`, `RestTemplate`, `TransactionTemplate` 등 `XXXTemplate`라면 템플릿 콜백 패턴으로 만들어져있다.

- 한계
  - 코드 최적화를 해도 원본코드를 수정해야되는 문제가 발생
  - 즉, V0 부터 V5까지 코드를 바꿔주는 과정이 있어 클래스가 늘어날 수록 결국 수정해야되는 코드는 늘어날 수 밖에 없다.

---

## Proxy

<p align="center"><img src="/img/design_pattern/proxy.png" width="80%"></p>

<p align="center"><img src="/img/design_pattern/proxy_1.png" width="80%"></p>

직접 호출 대신 간접 호출을 하면 `proxy`가 중간에서 여러가지 일을 할 수 있다는 장점이 있다.
또한, 프록시는 또다른 프록시를 호출(`Proxy Chain`)할 수 있으며, 결과적으로 서버는 그 결과만 받으면 된다.
하지만, 클라이언트는 서버에 요청한 것인지 프록시에 요청한것인지 알 수 없어야 한다. 즉, `서버와 프록시는 같은 인터페이스를 사용`해야한다
따라서, 클라이언트가 사용하는 서버 객체를 프록시 객체로 변경해도 클라이언트 코드를 변경하지 않고 동작할 수 있어야 한다.(대체가능)

- 주요 기능
  - 접근 제어
    - 권한에 따른 접근 차단
    - 캐싱
    - 지연 로딩
  - 부가 기능 추가
    - 원래 서버가 제공하는 기능에 더해서 부가 기능을 수행

GOF 디자인 패턴에서는 프록시 패턴과 데코레이터 패턴 모두 프록시를 사용하는 방법이지만, 의도에 따라 둘을 구분할 수 있다

- 프록시 패턴 : 접근 제어가 목적
- 데코레이터 패턴 : 새로운 기능 추가가 목적

### Proxy Pattern

<p align="center"><img src="/img/design_pattern/pp.png" width="80%"></p>

프록시 도입을 통해 client 코드는 전혀 손대지 않고, 접근제어를 할 수 있다.
또한 client 는 프록시가 적용됬는지, 실제 객체가 적용됬는지 알 수 없다는 장점 또한 존재한다.

- 다른 개체에 대한 `접근을 제어`하기 위한 대리자를 제공

### Decorator Pattern

<p align="center"><img src="/img/design_pattern/deco.png" width="80%"></p>

<p align="center"><img src="/img/design_pattern/deco_1.png" width="80%"></p>

데코레이터 패턴을 통해 클라이언트 변경없이 새로운 기능을 지속적으로 추가할 수 있다.

- 객체에 `추가 기능을 동적으로 추가`하고, 기능 확장을 위한 유연한 대안 제공

### 인터페이스 기반 vs 클래스 기반 프록시

- 클래스 기반 프록시는 인터페이스와 관계없이 생성할 수 있다.
  - 하지만 몇 가지 제약점이 존재한다
    - 부모 클래스의 상속자를 호출 (`super(null)`)
    - 클래스에 final 키워드가 붙으면 상속이 불가능하다
    - 메서드 자체에 final 키워드가 있다면 오버라이딩이 불가능하다
- 따라서 인터페이스 기반 프록시가 상속의 제약에서 자유로우며, 역할과 구현을 분리가 명확하다
  - 하지만, 인터페이스가 반드시 필요하다는 단점.

인터페이스를 통해 모든 객체를 역할과 구현을 나누면 좋겠지만, 실무에서는 구현체를 변경하는 일이 없는 클래스도 존재한다.
즉, 인터페이스는 변경 가능성이 있는 곳에 적용하는데, 실용적인 관점에서는 인터페이스를 사용하지 않고 바로 구체 클래스를 사용하는
경우도 발생한다는 것이다. 따라서 상황에 맞게 인터페이스와 구체 클래스를 이용한 프록시를 적용해야한다. 무조건적으로
인터페이스만으로 적용할 필요는 없다는 점.