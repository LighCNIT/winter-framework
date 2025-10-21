package org.winterframework.test.ioc.common.event;

import org.winterframework.context.ApplicationEvent;

/**
 * 自定义事件示例
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/21
 * @description 用于演示Winter框架事件机制的自定义事件类
 * 
 * <p>CustomEvent是Winter框架事件系统的示例事件类，用于演示如何创建
 * 和发布自定义事件。它继承自ApplicationEvent，遵循框架的事件规范。</p>
 * 
 * <p>使用场景：</p>
 * <ul>
 *   <li>业务事件发布（如用户注册、订单创建等）</li>
 *   <li>系统状态变化通知</li>
 *   <li>组件间松耦合通信</li>
 *   <li>事件驱动架构实现</li>
 * </ul>
 * 
 * <p>使用示例：</p>
 * <pre>{@code
 * // 发布自定义事件
 * applicationContext.publishEvent(new CustomEvent(applicationContext));
 * 
 * // 监听自定义事件
 * public class MyListener implements ApplicationListener<CustomEvent> {
 *     @Override
 *     public void onApplicationEvent(CustomEvent event) {
 *         System.out.println("收到自定义事件: " + event);
 *     }
 * }
 * }</pre>
 * 
 * @see ApplicationEvent
 * @see ApplicationListener
 * @see ApplicationEventPublisher
 */
public class CustomEvent extends ApplicationEvent {
    
    /**
     * 构造自定义事件
     * 
     * <p>创建一个新的自定义事件实例，事件源通常是发布事件的ApplicationContext
     * 或其他相关对象。</p>
     *
     * @param source 事件源对象，通常是发布事件的ApplicationContext，不能为null
     * @throws IllegalArgumentException 如果source为null
     */
    public CustomEvent(Object source) {
        super(source);
    }
}