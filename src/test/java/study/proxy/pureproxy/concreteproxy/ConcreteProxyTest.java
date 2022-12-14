package study.proxy.pureproxy.concreteproxy;

import org.junit.jupiter.api.Test;
import study.proxy.pureproxy.concreteproxy.code.ConcreteClient;
import study.proxy.pureproxy.concreteproxy.code.ConcreteLogic;
import study.proxy.pureproxy.concreteproxy.code.TimeProxy;

public class ConcreteProxyTest {

    @Test
    void noProxy() {
        ConcreteClient client = new ConcreteClient(new ConcreteLogic());
        client.execute();
    }

    @Test
    void timeProxy() {
        TimeProxy timeProxy = new TimeProxy(new ConcreteLogic());
        ConcreteClient client = new ConcreteClient(timeProxy);
        client.execute();
    }
}
