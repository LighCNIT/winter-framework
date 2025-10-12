package org.winterframework.test;


import org.junit.Test;
import org.winterframework.beans.factory.BeanFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/12
 * @description test
 */
public class SimpleBeanContainerTest {

    @Test
    public void testGetBean() throws Exception{
        BeanFactory beanFactory = new BeanFactory();
        beanFactory.registerBean("helloService",new HelloService());
        HelloService helloService = (HelloService) beanFactory.getBean("helloService");
        assertThat(helloService).isNotNull();
        assertThat(helloService.sayHello()).isEqualTo("hello");
    }

    class HelloService{
        public String sayHello(){
            System.out.println("hello");
            return "hello";
        }
    }
}