package org.winterframework.test;

import org.junit.Test;
import org.winterframework.beans.BeanException;
import org.winterframework.beans.factory.config.BeanDefinition;
import org.winterframework.beans.factory.support.DefaultListableBeanFactory;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Bean工厂测试类
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/12
 * @description 测试Winter Framework的核心功能
 */
public class BeanFactoryTest {

    /**
     * 测试用的Bean类 - 用户服务
     */
    public static class UserService {
        private String name = "UserService";

        public void sayHello() {
            System.out.println("Hello from " + name);
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 测试用的Bean类 - 订单服务
     */
    public static class OrderService {
        private String name = "OrderService";

        public String getName() {
            return name;
        }
    }

    /**
     * 测试基本的Bean获取功能
     * 
     * 测试点：
     * 1. Bean的注册
     * 2. Bean的创建
     * 3. 单例模式验证（多次获取返回同一实例）
     */
    @Test
    public void testGetBean() {
        // 1. 创建BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2. 注册BeanDefinition
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        beanFactory.registerBeanDefinition("userService", beanDefinition);

        // 3. 第一次获取Bean（会创建新实例）
        UserService userService1 = (UserService) beanFactory.getBean("userService");
        assertThat(userService1).isNotNull();
        assertThat(userService1.getName()).isEqualTo("UserService");
        userService1.sayHello();

        // 4. 第二次获取Bean（从缓存中获取）
        UserService userService2 = (UserService) beanFactory.getBean("userService");
        assertThat(userService2).isNotNull();

        // 5. 验证单例模式：两次获取的是同一个实例
        assertThat(userService1).isSameAs(userService2);
        System.out.println("✅ 单例验证通过: userService1 == userService2");
    }

    /**
     * 测试注册多个Bean
     * 
     * 测试点：
     * 1. 多个Bean的注册
     * 2. 不同Bean之间互不影响
     */
    @Test
    public void testMultipleBeans() {
        // 1. 创建BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2. 注册多个BeanDefinition
        beanFactory.registerBeanDefinition("userService", 
            new BeanDefinition(UserService.class));
        beanFactory.registerBeanDefinition("orderService", 
            new BeanDefinition(OrderService.class));

        // 3. 获取不同的Bean
        UserService userService = (UserService) beanFactory.getBean("userService");
        OrderService orderService = (OrderService) beanFactory.getBean("orderService");

        // 4. 验证Bean都正确创建
        assertThat(userService).isNotNull();
        assertThat(orderService).isNotNull();
        assertThat(userService.getName()).isEqualTo("UserService");
        assertThat(orderService.getName()).isEqualTo("OrderService");
        
        System.out.println("✅ 多Bean测试通过");
    }

    /**
     * 测试获取不存在的Bean
     * 
     * 测试点：
     * 1. 异常处理
     * 2. 容器对非法请求的响应
     */
    @Test(expected = BeanException.class)
    public void testBeanNotFound() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 尝试获取未注册的Bean，应该抛出BeanException
        beanFactory.getBean("nonExistentBean");
    }

    /**
     * 测试单例缓存机制
     * 
     * 测试点：
     * 1. 验证缓存的有效性
     * 2. 性能：第二次获取不会重新创建
     */
    @Test
    public void testSingletonCache() {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        beanFactory.registerBeanDefinition("userService", 
            new BeanDefinition(UserService.class));

        // 多次获取Bean
        UserService service1 = (UserService) beanFactory.getBean("userService");
        UserService service2 = (UserService) beanFactory.getBean("userService");
        UserService service3 = (UserService) beanFactory.getBean("userService");

        // 验证都是同一个实例
        assertThat(service1).isSameAs(service2);
        assertThat(service2).isSameAs(service3);
        
        System.out.println("✅ 单例缓存测试通过: 三次获取返回同一实例");
    }
}





