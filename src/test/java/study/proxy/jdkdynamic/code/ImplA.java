package study.proxy.jdkdynamic.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ImplA implements InterfaceA{
    @Override
    public String call() {
        log.info("A 호출");
        return "A";
    }
}
