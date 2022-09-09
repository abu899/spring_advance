package study.proxy.jdkdynamic.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ImplB implements InterfaceB{
    @Override
    public String call() {
        log.info("B 호출");
        return "B";
    }
}
