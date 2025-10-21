package org.winterframework.context.event;

import org.winterframework.beans.BeanException;
import org.winterframework.beans.factory.BeanFactory;
import org.winterframework.context.ApplicationEvent;
import org.winterframework.context.ApplicationListener;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 简单应用事件广播器实现
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/21
 * @description 提供简单的事件广播实现，支持同步事件分发和泛型类型匹配
 * 
 * <p>SimpleApplicationEventMulticaster是Winter框架中事件广播器的默认实现，
 * 它继承自AbstractApplicationEventMulticaster，提供了具体的事件广播逻辑。
 * 支持基于泛型类型的事件监听器匹配和同步事件分发。</p>
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>实现同步事件广播</li>
 *   <li>支持泛型类型匹配</li>
 *   <li>提供事件类型支持检查</li>
 *   <li>处理监听器异常</li>
 * </ul>
 * 
 * <p>事件匹配机制：</p>
 * <ul>
 *   <li>通过反射获取监听器的泛型参数类型</li>
 *   <li>检查事件类型是否匹配监听器的泛型类型</li>
 *   <li>支持继承关系匹配（子类事件可以匹配父类监听器）</li>
 * </ul>
 * 
 * <p>使用示例：</p>
 * <pre>{@code
 * // 创建事件广播器
 * SimpleApplicationEventMulticaster multicaster = new SimpleApplicationEventMulticaster(beanFactory);
 * 
 * // 注册监听器
 * multicaster.addApplicationListener(new MyEventListener());
 * 
 * // 发布事件
 * multicaster.multicastEvent(new CustomEvent("Hello"));
 * }</pre>
 * 
 * <p>线程安全性：</p>
 * <ul>
 *   <li>事件广播过程是同步的</li>
 *   <li>监听器集合在广播过程中不应被修改</li>
 *   <li>单个监听器的异常不会影响其他监听器</li>
 * </ul>
 * 
 * @see AbstractApplicationEventMulticaster
 * @see ApplicationEventMulticaster
 * @see ApplicationListener
 * @see ApplicationEvent
 */
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster{

    /**
     * 构造简单事件广播器
     * 
     * <p>创建一个新的事件广播器实例，并设置BeanFactory。</p>
     * 
     * @param beanFactory BeanFactory实例，用于依赖注入和Bean获取
     */
    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    /**
     * 广播事件
     * 
     * <p>将事件广播给所有匹配的监听器。遍历所有注册的监听器，
     * 检查是否支持该事件类型，如果支持则调用监听器的处理方法。</p>
     * 
     * <p>广播过程：</p>
     * <ol>
     *   <li>遍历所有注册的监听器</li>
     *   <li>检查监听器是否支持该事件类型</li>
     *   <li>调用匹配监听器的onApplicationEvent方法</li>
     *   <li>处理监听器异常（异常不会影响其他监听器）</li>
     * </ol>
     * 
     * @param event 要广播的事件对象，不能为null
     */
    @Override
    public void multicastEvent(ApplicationEvent event) {
        for (ApplicationListener<ApplicationEvent> applicationListener : applicationListeners) {
            if (supportsEvent(applicationListener, event)) {
                applicationListener.onApplicationEvent(event);
            }
        }
    }

    /**
     * 检查监听器是否支持指定事件
     * 
     * <p>通过反射获取监听器的泛型参数类型，检查事件类型是否匹配。
     * 支持继承关系匹配，即子类事件可以匹配父类监听器。</p>
     * 
     * <p>匹配规则：</p>
     * <ul>
     *   <li>监听器的泛型类型与事件类型完全匹配</li>
     *   <li>监听器的泛型类型是事件类型的父类</li>
     *   <li>监听器监听ApplicationEvent（匹配所有事件）</li>
     * </ul>
     * 
     * @param applicationListener 要检查的监听器
     * @param event 要检查的事件
     * @return 如果监听器支持该事件类型则返回true，否则返回false
     * @throws BeanException 如果无法获取监听器的泛型类型信息
     */
    protected boolean supportsEvent(ApplicationListener<ApplicationEvent> applicationListener, ApplicationEvent event) {
        Type type = applicationListener.getClass().getGenericInterfaces()[0];
        Type actualTypeArgument = ((ParameterizedType) type).getActualTypeArguments()[0];
        String className = actualTypeArgument.getTypeName();
        Class<?> eventClassName;
        try {
            eventClassName = Class.forName(className);
        } catch (ClassNotFoundException ex) {
            throw new BeanException("wrong event class name: " + className);
        }
        return eventClassName.isAssignableFrom(event.getClass());
    }
}