package org.winterframework.context.event;

import org.winterframework.context.ApplicationContext;
import org.winterframework.context.ApplicationEvent;

/**
 * 应用上下文事件抽象基类
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/21
 * @description 所有与ApplicationContext相关的事件的基类，提供对ApplicationContext的访问
 * 
 * <p>ApplicationContextEvent是Winter框架中所有与ApplicationContext相关事件的抽象基类。
 * 它继承自ApplicationEvent，并提供了对ApplicationContext的便捷访问方法。</p>
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>提供ApplicationContext的访问方法</li>
 *   <li>作为容器相关事件的统一基类</li>
 *   <li>确保事件源是ApplicationContext类型</li>
 * </ul>
 * 
 * <p>使用场景：</p>
 * <ul>
 *   <li>容器生命周期事件（如ContextRefreshedEvent、ContextClosedEvent）</li>
 *   <li>容器状态变化事件</li>
 *   <li>需要访问ApplicationContext的自定义事件</li>
 * </ul>
 * 
 * <p>子类示例：</p>
 * <ul>
 *   <li>ContextRefreshedEvent：容器刷新完成事件</li>
 *   <li>ContextClosedEvent：容器关闭事件</li>
 *   <li>ContextStartedEvent：容器启动事件（可扩展）</li>
 *   <li>ContextStoppedEvent：容器停止事件（可扩展）</li>
 * </ul>
 * 
 * @see ApplicationEvent
 * @see ApplicationContext
 * @see ContextRefreshedEvent
 * @see ContextClosedEvent
 */
public abstract class ApplicationContextEvent extends ApplicationEvent {

    /**
     * 构造应用上下文事件
     * 
     * <p>创建一个与ApplicationContext相关的事件，事件源必须是ApplicationContext实例。</p>
     * 
     * @param source 事件源ApplicationContext，不能为null
     * @throws IllegalArgumentException 如果source为null
     */
    public ApplicationContextEvent(ApplicationContext source){
        super(source);
    }

    /**
     * 获取事件源ApplicationContext
     * 
     * <p>返回发布此事件的ApplicationContext实例。由于事件源在构造时已经验证
     * 为ApplicationContext类型，因此可以安全地进行类型转换。</p>
     * 
     * @return 发布此事件的ApplicationContext实例
     */
    public final ApplicationContext getApplicationContext() {
        return (ApplicationContext) getSource();
    }

}