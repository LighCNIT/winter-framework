package org.winterframework.test.expanding;

import org.junit.Test;
import org.winterframework.beans.BeanException;
import org.winterframework.context.support.ClassPathXmlApplicationContext;
import org.winterframework.test.bean.Car;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/27
 * @description TODO
 */
public class PropertyPlaceholderConfigurerTest {

    @Test
    public void test() throws BeanException{
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:property-placeholder-configurer.xml");
        Car car = applicationContext.getBean("car",Car.class);
        assertThat(car.getBrand()).isEqualTo("lamborghini");
    }
}