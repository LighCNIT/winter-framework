package org.winterframework.test.ioc;

import org.junit.Test;
import org.winterframework.beans.factory.support.DefaultListableBeanFactory;
import org.winterframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.winterframework.test.ioc.bean.Car;
import org.winterframework.test.ioc.bean.Person;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * XML文件定义Bean测试类
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/17
 * @description 测试通过XML配置文件定义Bean的功能，验证XML解析和Bean创建是否正常工作
 *              包括基本属性注入和Bean依赖注入的测试
 */
public class XmlFileDefineBeanTest {

    /**
     * 测试XML文件定义Bean功能
     * 验证从XML配置文件中读取Bean定义并创建Bean实例的功能
     * 包括基本属性注入和Bean依赖注入的测试
     * 
     * @throws Exception 当测试过程中发生异常时抛出
     */
    @Test
    public void testXmlFile() throws Exception {
        // 1. 创建BeanFactory和XML读取器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        
        // 2. 从classpath加载XML配置文件
        beanDefinitionReader.loadBeanDefinitions("classpath:spring.xml");

        // 3. 获取Person Bean并验证属性注入
        Person person = (Person) beanFactory.getBean("person");
        System.out.println("Person Bean: " + person);
        assertThat(person.getName()).isEqualTo("derek");
        assertThat(person.getCar().getBrand()).isEqualTo("porsche");

        // 4. 获取Car Bean并验证属性注入
        Car car = (Car) beanFactory.getBean("car");
        System.out.println("Car Bean: " + car);
        assertThat(car.getBrand()).isEqualTo("porsche");
    }
}