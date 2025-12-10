package org.winterframework.test.ioc;

import org.junit.Test;
import org.winterframework.context.support.ClassPathXmlApplicationContext;
import org.winterframework.test.bean.Car;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Ligh
 * 2025/12/10 21:21
 **/
public class TypeConversionSecondPartTest {

    @Test
    public void testConversionService() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:type-conversion-second-part.xml");

        Car car = applicationContext.getBean("car", Car.class);
        assertThat(car.getPrice()).isEqualTo(1000000);
        assertThat(car.getProduceDate()).isEqualTo(LocalDate.of(2021, 1, 1));
    }
}
