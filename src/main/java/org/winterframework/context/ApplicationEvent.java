package org.winterframework.context;

import java.util.EventListener;
import java.util.EventObject;

/**
 * 应用事件抽象基类
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/21
 * @description 所有应用事件的基类，继承自EventObject，提供事件的基础功能
 * 
 * <p>ApplicationEvent是Winter框架中所有应用事件的抽象基类，它继承自Java标准库的EventObject，
 * 为框架中的事件机制提供了基础支持。</p>
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>提供事件源（source）的存储和访问</li>
 *   <li>作为所有应用事件的统一基类</li>
 *   <li>支持事件的时间戳记录（通过父类EventObject）</li>
 * </ul>
 * 
 * <p>使用场景：</p>
 * <ul>
 *   <li>容器生命周期事件（如ContextRefreshedEvent、ContextClosedEvent）</li>
 *   <li>业务自定义事件（如用户注册事件、订单创建事件等）</li>
 *   <li>系统状态变化事件</li>
 * </ul>
 * 
 * <p>设计模式：观察者模式</p>
 * <ul>
 *   <li>事件发布者发布ApplicationEvent</li>
 *   <li>事件监听器监听特定类型的ApplicationEvent</li>
 *   <li>通过ApplicationEventMulticaster进行事件分发</li>
 * </ul>
 * 
 * @see EventObject
 * @see ApplicationListener
 * @see ApplicationEventMulticaster
 * @see org.winterframework.context.event.ContextRefreshedEvent
 * @see org.winterframework.context.event.ContextClosedEvent
 */
public abstract class ApplicationEvent extends EventObject {


    /**
     * 构造应用事件
     * 
     * <p>创建一个新的应用事件实例，事件源将被传递给父类EventObject进行存储。</p>
     *
     * @param source 事件源对象，通常是发布事件的ApplicationContext或其他对象
     * @throws IllegalArgumentException 如果source为null
     */
    public ApplicationEvent(Object source) {
        super(source);
    }
}