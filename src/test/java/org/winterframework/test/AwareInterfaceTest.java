package org.winterframework.test;

import org.junit.Test;
import org.winterframework.context.support.ClassPathXmlApplicationContext;
import org.winterframework.test.service.HelloService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/18
 * @description TODO
 */
public class AwareInterfaceTest {

    @Test
    public void test() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        HelloService helloService = applicationContext.getBean("helloService", HelloService.class);
        assertThat(helloService.getApplicationContext()).isNotNull();
        assertThat(helloService.getBeanFactory()).isNotNull();
    }
}