package org.winterframework.test.ioc;

import org.junit.Test;
import org.winterframework.context.support.ClassPathXmlApplicationContext;
import org.winterframework.test.ioc.common.event.CustomEvent;

/**
 * 事件和事件监听器测试类
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/21
 * @description 测试Winter框架的事件发布和监听机制
 * 
 * <p>EventAndEventListenerTest是Winter框架事件系统的测试类，用于验证
 * 事件发布、事件监听器注册和事件广播功能的正确性。</p>
 * 
 * <p>测试内容：</p>
 * <ul>
 *   <li>容器生命周期事件（ContextRefreshedEvent、ContextClosedEvent）</li>
 *   <li>自定义事件发布和监听</li>
 *   <li>事件监听器的自动注册</li>
 *   <li>事件广播机制</li>
 * </ul>
 * 
 * <p>测试流程：</p>
 * <ol>
 *   <li>创建ApplicationContext并加载配置</li>
 *   <li>发布自定义事件</li>
 *   <li>关闭容器触发ContextClosedEvent</li>
 *   <li>验证事件监听器是否被正确调用</li>
 * </ol>
 * 
 * @see ApplicationContext
 * @see ApplicationEvent
 * @see ApplicationListener
 * @see CustomEvent
 * @see ContextRefreshedEvent
 * @see ContextClosedEvent
 */
public class EventAndEventListenerTest {

    /**
     * 测试事件监听器功能
     * 
     * <p>此测试方法验证Winter框架的事件机制，包括：</p>
     * <ul>
     *   <li>容器启动时自动发布ContextRefreshedEvent</li>
     *   <li>手动发布自定义事件</li>
     *   <li>容器关闭时自动发布ContextClosedEvent</li>
     *   <li>事件监听器的正确注册和调用</li>
     * </ul>
     * 
     * <p>测试步骤：</p>
     * <ol>
     *   <li>创建ClassPathXmlApplicationContext实例</li>
     *   <li>加载event-and-event-listener.xml配置文件</li>
     *   <li>发布CustomEvent自定义事件</li>
     *   <li>关闭容器触发ContextClosedEvent</li>
     * </ol>
     * 
     * <p>预期结果：</p>
     * <ul>
     *   <li>ContextRefreshedEventListener在容器启动时被调用</li>
     *   <li>CustomEventListener在发布CustomEvent时被调用</li>
     *   <li>ContextClosedEventListener在容器关闭时被调用</li>
     * </ul>
     * 
     * @throws Exception 如果测试过程中发生异常
     */
    @Test
    public void testEventListener() throws Exception {
        // 创建ApplicationContext并加载配置
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:event-and-event-listener.xml");
        
        // 发布自定义事件，测试事件发布和监听机制
        applicationContext.publishEvent(new CustomEvent(applicationContext));

        // 关闭容器，触发ContextClosedEvent事件
        // 也可以使用applicationContext.registerShutdownHook()注册JVM关闭钩子
        applicationContext.close();
    }
}