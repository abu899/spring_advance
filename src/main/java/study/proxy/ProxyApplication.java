package study.proxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import study.proxy.config.AppV1Config;
import study.proxy.config.AppV2Config;

@Import({AppV1Config.class, AppV2Config.class})
@SpringBootApplication(scanBasePackages = "study.proxy.app")
public class ProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProxyApplication.class, args);
    }
}
