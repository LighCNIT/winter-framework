package org.winterframework.test;

import org.junit.Test;
import org.winterframework.beans.factory.config.BeanDefinition;
import org.winterframework.beans.factory.support.DefaultListableBeanFactory;
import org.winterframework.test.service.HelloService;

/**
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/12
 * @description TODO
 */
public class BeanDefinitionAndBeanDefinitionRegistryTest {

    @Test
    public void testBeanFactory() throws Exception {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        BeanDefinition beanDefinition = new BeanDefinition(HelloService.class);
        beanFactory.registerBeanDefinition("helloService", beanDefinition);

        HelloService helloService = (HelloService) beanFactory.getBean("helloService");
        helloService.sayHello();
    }
}