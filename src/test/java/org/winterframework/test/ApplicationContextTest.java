package org.winterframework.test;

import org.junit.Test;
import org.winterframework.beans.factory.support.DefaultListableBeanFactory;
import org.winterframework.context.support.ClassPathXmlApplicationContext;
import org.winterframework.test.bean.Car;
import org.winterframework.test.bean.Person;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/18
 * @description TODO
 */
public class ApplicationContextTest {

    @Test
    public void testApplicationContext(){

        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");

        Person person = applicationContext.getBean("person", Person.class);
        System.out.println("after bean:"+person);
        //name属性在CustomBeanFactoryPostProcessor中被修改为ivy
        assertThat(person.getName()).isEqualTo("ivy");

        Car car = applicationContext.getBean("car", Car.class);
        System.out.println("after bean:"+car);
        //brand属性在CustomerBeanPostProcessor中被修改为lamborghini
        assertThat(car.getBrand()).isEqualTo("lamborghini");

    }
}