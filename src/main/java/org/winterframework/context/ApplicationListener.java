package org.winterframework.context;

import java.util.EventListener;

/**
 * 应用事件监听器接口
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/21
 * @description 用于监听和处理应用事件的接口，支持泛型以监听特定类型的事件
 * 
 * <p>ApplicationListener是Winter框架中事件监听机制的核心接口，它允许Bean监听和处理
 * 特定类型的应用事件。通过泛型支持，可以确保类型安全的事件处理。</p>
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>定义事件处理的标准方法onApplicationEvent</li>
 *   <li>支持泛型，确保类型安全的事件监听</li>
 *   <li>继承EventListener，符合Java事件模型规范</li>
 * </ul>
 * 
 * <p>使用场景：</p>
 * <ul>
 *   <li>监听容器生命周期事件（如ContextRefreshedEvent）</li>
 *   <li>监听业务自定义事件</li>
 *   <li>实现事件驱动的业务逻辑</li>
 *   <li>解耦组件间的依赖关系</li>
 * </ul>
 * 
 * <p>实现示例：</p>
 * <pre>{@code
 * public class MyEventListener implements ApplicationListener<CustomEvent> {
 *     @Override
 *     public void onApplicationEvent(CustomEvent event) {
 *         // 处理自定义事件
 *         System.out.println("收到自定义事件: " + event);
 *     }
 * }
 * }</pre>
 * 
 * <p>注册方式：</p>
 * <ul>
 *   <li>通过XML配置：&lt;bean class="com.example.MyEventListener"/&gt;</li>
 *   <li>通过注解：@Component + implements ApplicationListener</li>
 *   <li>程序化注册：applicationContext.addApplicationListener(listener)</li>
 * </ul>
 * 
 * @param <E> 要监听的事件类型，必须继承自ApplicationEvent
 * @see ApplicationEvent
 * @see ApplicationEventMulticaster
 * @see EventListener
 * @see org.winterframework.context.event.ContextRefreshedEvent
 * @see org.winterframework.context.event.ContextClosedEvent
 */
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {

    /**
     * 处理应用事件
     * 
     * <p>当监听器注册的事件类型被发布时，此方法将被调用。
     * 实现类应该在此方法中处理具体的事件逻辑。</p>
     * 
     * @param event 要处理的事件对象
     */
    void onApplicationEvent(E event);
}
