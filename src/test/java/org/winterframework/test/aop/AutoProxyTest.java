package org.winterframework.test.aop;

import org.junit.Test;
import org.winterframework.context.support.ClassPathXmlApplicationContext;
import org.winterframework.test.service.WorldService;

/**
 * @author Ligh
 * 2025/10/25 11:37
 **/
public class AutoProxyTest {

    @Test(expected = RuntimeException.class)
    public void testAutoProxy() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:auto-proxy.xml");
        WorldService worldService = applicationContext.getBean("worldService", WorldService.class);
        // 因为有环绕通知，所以是走到环绕通知，其余的可以自行测试（例如删掉配置aroundAdviceInterceptor）
        worldService.explode();
        WorldService worldServiceWithException = applicationContext.getBean("worldServiceWithException", WorldService.class);
        worldServiceWithException.explode();
    }
}
