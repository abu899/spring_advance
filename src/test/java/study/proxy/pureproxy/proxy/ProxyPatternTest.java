package study.proxy.pureproxy.proxy;

import org.junit.jupiter.api.Test;
import study.proxy.pureproxy.proxy.code.CacheProxy;
import study.proxy.pureproxy.proxy.code.ProxyPatternClient;
import study.proxy.pureproxy.proxy.code.RealSubject;

public class ProxyPatternTest {

    @Test
    void noProxyTest() {
        RealSubject realSubject = new RealSubject();
        ProxyPatternClient client = new ProxyPatternClient(realSubject);
        client.execute();
        client.execute(); // 한번 조회하고 변하지 않는 데이터지만 호출하는데 동일한 시간이 걸린다
        client.execute(); // 이를 어딘가에 보관하고 이미 조회한 데이터를 사용하는 것이 성능상 이점이 있다
    }

    @Test
    void proxyTest() {
        CacheProxy proxy = new CacheProxy(new RealSubject());
        ProxyPatternClient client = new ProxyPatternClient(proxy);
        client.execute();
        client.execute();
        client.execute();
    }
}
