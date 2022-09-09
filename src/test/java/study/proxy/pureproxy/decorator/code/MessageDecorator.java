package study.proxy.pureproxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageDecorator implements Component {

    private Component component;

    public MessageDecorator(RealComponent component) {
        this.component = component;
    }

    @Override
    public String operation() {
        log.info("MessageDecorator 호출");
        String result = component.operation();
        String decoResult = "*******" + result + "*******";
        log.info(" 변환 전 = {} , 변환 후 = {} ",result, decoResult);
        return decoResult;
    }
}
