package org.winterframework.test.aop;

import org.junit.Test;
import org.winterframework.context.support.ClassPathXmlApplicationContext;
import org.winterframework.test.service.WorldService;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Ligh
 * 2025/10/25 11:37
 **/
public class AutoProxyTest {

    @Test(expected = RuntimeException.class)
    public void testAutoProxy() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:populate-proxy-bean-with-property-values.xml");

        //获取代理对象
        WorldService worldService = applicationContext.getBean("worldService", WorldService.class);
        worldService.explode();
        assertThat(worldService.getName()).isEqualTo("earth");
    }
}
