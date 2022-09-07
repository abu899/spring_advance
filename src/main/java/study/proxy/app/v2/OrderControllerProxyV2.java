package study.proxy.app.v2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@RequestMapping("/proxy")
@ResponseBody
public class OrderControllerProxyV2 {

    private final OrderServiceProxyV2 orderService;

    public OrderControllerProxyV2(OrderServiceProxyV2 orderServiceV2) {
        this.orderService = orderServiceV2;
    }

    @GetMapping("/v2/request")
    public String request(String itemId) {
        orderService.orderItem(itemId);
        return "v2-ok";
    }

    @GetMapping("/v2/no-log")
    public String noLog() {
        return "v2-ok";
    }
}
