package org.winterframework.test.ioc;

import org.junit.Test;
import org.winterframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/18
 * @description TODO
 */
public class InitAndDestroyMethodTest {

    @Test
    public void testInitAndDestroyMethod() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:init-and-destroy-method.xml");
        applicationContext.registerShutdownHook();  //或者手动关闭 applicationContext.close();
    }
}