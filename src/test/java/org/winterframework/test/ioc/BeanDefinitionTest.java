package org.winterframework.test.ioc;

import org.junit.Test;
import org.winterframework.beans.factory.config.BeanDefinition;
import org.winterframework.beans.factory.support.DefaultListableBeanFactory;
import org.winterframework.test.ioc.service.HelloService;

/**
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/12
 * @description TODO
 */
public class BeanDefinitionTest {

    @Test
    public void testBeanFactory(){
        DefaultListableBeanFactory  beanFactory = new DefaultListableBeanFactory();
        BeanDefinition beanDefinition = new BeanDefinition(HelloService.class);
        // 注册bean
        beanFactory.registerBeanDefinition("helloService",beanDefinition);
        // 获取bean
        HelloService helloService = (HelloService) beanFactory.getBean("helloService");
        helloService.sayHello();
    }
}