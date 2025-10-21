package org.winterframework.context.event;

import org.winterframework.context.ApplicationEvent;
import org.winterframework.context.ApplicationListener;

/**
 * 应用事件广播器接口
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/21
 * @description 负责管理事件监听器并广播事件的接口，是事件机制的核心组件
 * 
 * <p>ApplicationEventMulticaster是Winter框架中事件广播机制的核心接口，它负责
 * 管理所有的事件监听器，并在事件发布时进行广播分发。通过此接口，可以实现
 * 一对多的事件通知机制。</p>
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>管理事件监听器的注册和移除</li>
 *   <li>接收事件并广播给匹配的监听器</li>
 *   <li>支持事件类型匹配和过滤</li>
 *   <li>提供事件分发的统一入口</li>
 * </ul>
 * 
 * <p>使用场景：</p>
 * <ul>
 *   <li>容器内部事件分发</li>
 *   <li>业务事件广播</li>
 *   <li>实现观察者模式</li>
 *   <li>解耦事件发布者和监听者</li>
 * </ul>
 * 
 * <p>事件广播流程：</p>
 * <ol>
 *   <li>接收ApplicationEventPublisher发布的事件</li>
 *   <li>遍历所有注册的ApplicationListener</li>
 *   <li>检查监听器是否支持该事件类型</li>
 *   <li>调用匹配监听器的onApplicationEvent方法</li>
 * </ol>
 * 
 * <p>实现类：</p>
 * <ul>
 *   <li>SimpleApplicationEventMulticaster：简单的事件广播器实现</li>
 *   <li>支持同步事件广播</li>
 *   <li>支持泛型类型匹配</li>
 * </ul>
 * 
 * @see ApplicationEvent
 * @see ApplicationListener
 * @see ApplicationEventPublisher
 * @see SimpleApplicationEventMulticaster
 */
public interface ApplicationEventMulticaster {

    /**
     * 添加事件监听器
     * 
     * <p>注册一个事件监听器到广播器中，该监听器将接收匹配的事件。</p>
     * 
     * @param listener 要添加的事件监听器，不能为null
     */
    void addApplicationListener(ApplicationListener<?> listener);

    /**
     * 移除事件监听器
     * 
     * <p>从广播器中移除指定的事件监听器，移除后该监听器将不再接收事件。</p>
     * 
     * @param listener 要移除的事件监听器
     */
    void removeApplicationListener(ApplicationListener<?> listener);

    /**
     * 广播事件
     * 
     * <p>将事件广播给所有匹配的监听器。广播过程是同步的，监听器的执行顺序
     * 可能不确定。如果某个监听器抛出异常，不会影响其他监听器的执行。</p>
     * 
     * @param event 要广播的事件对象，不能为null
     */
    void multicastEvent(ApplicationEvent event);
}
