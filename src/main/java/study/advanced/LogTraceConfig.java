package study.advanced;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.advanced.app.logtrace.LogTrace;
import study.advanced.app.logtrace.ThreadLocalLogTrace;

@Configuration
public class LogTraceConfig {

//    @Bean
//    public LogTrace logTrace() {
//        return new FieldLogTrace();
//    }

    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }
}
