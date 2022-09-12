package study.proxy.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanPostProcessorTest {

    @Test
    void basicConfig() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PostProcessorConfig.class);

        // Bean A의 이름으로 Bean B가 등록(후처리기)
        B beanB = context.getBean("beanA", B.class);
        beanB.helloB();

        // B는 빈으로 등록되지 않은 상태
        Assertions.assertThrows(NoSuchBeanDefinitionException.class,
                () -> context.getBean(A.class));
    }

    @Slf4j
    @Configuration
    static class PostProcessorConfig {
        @Bean(name = "beanA")
        public A a() {
            return new A();
        }

        @Bean
        public AToBPostProcessor postProcessor() {
            return new AToBPostProcessor();
        }
    }


    @Slf4j
    static class A {
        public void helloA() {
            log.info("hello A");
        }
    }

    @Slf4j
    static class B {
        public void helloB() {
            log.info("hello B");
        }
    }

    @Slf4j
    static class AToBPostProcessor implements BeanPostProcessor {

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            log.info("Bean Name = {}, bean = {} ", beanName, bean);
            if (bean instanceof A) {
                return new B();
            }

            return bean;
        }
    }
}
