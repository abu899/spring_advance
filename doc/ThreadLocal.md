# ThreadLocal

## 동시성 문제

- 여러 쓰레드가 동시에 같은 인스턴스의 필드 값을 변경하면서 발생하는 문제.
- 따라서, 여러 쓰레드가 동시에 접근해야 하기에 트래픽이 적은 상황에서는 잘 나타나지 않지만, 트래픽이 늘어날 수록 발생하기 시작한다
- 특히 스프링 빈은 싱글톤 객체이기 때문에, 필드의 값을 변경하며 사용할때 동시성 문제가 발생한다.
- 인스턴스의 필드(주로 싱글톤)과 static 같은 공용 필드에 접근할 때 발생.
- 참고
  - 동시성 문제는 지역변수(멤버변수)에서는 발생하지 않는다
  - 지역변수는 쓰레드마다 각각 다른 메모리 영역에 할당되기 때문

## ThreadLocal

해당 쓰레드만 접근할 수 있는 특별한 저장소, 즉, 쓰레드마다 별도의 데이터 저장소를 사용하는 것.

<p align="center"><img src="/img/tl_1.png" width="80%"></p>
<p align="center"><img src="/img/tl_2.png" width="80%"></p>
<p align="center"><img src="/img/tl_3.png" width="80%"></p>

### ThreadLocal 주의사항

<p align="center"><img src="/img/tl_4.png" width="80%"></p>

- ThreadLocal 사용 시 반드시 `ThreadLocal.remove()`를 호출하여 저장된 값을 제거해줘야한다.
- remove 를 하지 않는 다면ThreadLocal 내에 데이터가 저장되어 있기 때문에, 쓰레드풀을 사용하는 경우 쓰레기 값이 들어간 상태로 쓰레드가 반납되고 
이를 다른 작업에서 사용될 수 있다
- WAS 입장에서 보면 UserA 가 ThreadLocal 에 데이터를 저장한 후, UserB 우연찮게 같은 쓰레드를 가지고 값을 가지고오는 경우,
UserB 는 UserA 의 정보를 보게 될 수 있다.