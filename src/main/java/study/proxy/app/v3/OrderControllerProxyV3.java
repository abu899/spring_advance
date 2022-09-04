package study.proxy.app.v3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/proxy")
public class OrderControllerProxyV3 {

    private final OrderServiceProxyV3 orderService;

    public OrderControllerProxyV3(OrderServiceProxyV3 orderServiceV1) {
        this.orderService = orderServiceV1;
    }

    @GetMapping("/v3/request")
    public String request(String itemId) {
        orderService.orderItem(itemId);
        return "v3-ok";
    }

    @GetMapping("/v3/no-log")
    public String noLog() {
        return "v3-ok";
    }
}
