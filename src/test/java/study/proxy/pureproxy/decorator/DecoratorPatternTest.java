package study.proxy.pureproxy.decorator;

import org.junit.jupiter.api.Test;
import study.proxy.pureproxy.decorator.code.DecoratorPatternClient;
import study.proxy.pureproxy.decorator.code.RealComponent;
import study.proxy.pureproxy.decorator.code.MessageDecorator;
import study.proxy.pureproxy.decorator.code.TimeDecorator;

public class DecoratorPatternTest {

    @Test
    void noDecorator() {
        RealComponent realComponent = new RealComponent();
        DecoratorPatternClient client = new DecoratorPatternClient(realComponent);
        client.execute();
    }

    @Test
    void decorator1() {
        MessageDecorator decorator = new MessageDecorator(new RealComponent());
        DecoratorPatternClient client = new DecoratorPatternClient(decorator);
        client.execute();
    }

    @Test
    void decorator2() {
        MessageDecorator messageDecorator = new MessageDecorator(new RealComponent());
        TimeDecorator timeDecorator = new TimeDecorator(messageDecorator);
        DecoratorPatternClient client = new DecoratorPatternClient(timeDecorator);
        client.execute();
    }
}
