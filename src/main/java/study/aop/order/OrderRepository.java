package study.aop.order;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class OrderRepository {

    public void save(String itemId) {
        log.info("OrderRepository Call");
        if (itemId.equals("ex")) {
            throw new IllegalStateException("Throw exception");
        }

    }
}
