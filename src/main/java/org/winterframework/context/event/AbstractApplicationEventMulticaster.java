package org.winterframework.context.event;

import org.winterframework.beans.factory.BeanFactory;
import org.winterframework.beans.factory.BeanFactoryAware;
import org.winterframework.context.ApplicationEvent;
import org.winterframework.context.ApplicationListener;

import java.util.HashSet;
import java.util.Set;

/**
 * 应用事件广播器抽象基类
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/21
 * @description 提供事件广播器的基础实现，管理事件监听器的注册和移除
 * 
 * <p>AbstractApplicationEventMulticaster是Winter框架中事件广播器的抽象基类，
 * 它实现了ApplicationEventMulticaster接口的基础功能，包括监听器的注册、移除
 * 和BeanFactory感知。具体的广播逻辑由子类实现。</p>
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>管理事件监听器的注册和移除</li>
 *   <li>提供BeanFactory感知能力</li>
 *   <li>维护监听器集合</li>
 *   <li>为子类提供基础实现</li>
 * </ul>
 * 
 * <p>设计模式：模板方法模式</p>
 * <ul>
 *   <li>定义事件广播的算法骨架</li>
 *   <li>子类实现具体的multicastEvent方法</li>
 *   <li>提供监听器管理的基础实现</li>
 * </ul>
 * 
 * <p>线程安全性：</p>
 * <ul>
 *   <li>使用HashSet存储监听器，非线程安全</li>
 *   <li>监听器的添加和移除操作需要外部同步</li>
 *   <li>事件广播过程中监听器集合不应被修改</li>
 * </ul>
 * 
 * @see ApplicationEventMulticaster
 * @see BeanFactoryAware
 * @see SimpleApplicationEventMulticaster
 */
public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster, BeanFactoryAware {

    /**
     * 事件监听器集合
     * 
     * <p>存储所有注册的事件监听器。使用HashSet确保监听器的唯一性，
     * 避免重复注册同一个监听器。</p>
     */
    public final Set<ApplicationListener<ApplicationEvent>> applicationListeners = new HashSet<>();

    /**
     * BeanFactory实例
     * 
     * <p>通过BeanFactoryAware接口注入的BeanFactory实例，
     * 用于获取Bean和进行依赖注入。</p>
     */
    private BeanFactory beanFactory;

    /**
     * 添加事件监听器
     * 
     * <p>将指定的事件监听器添加到监听器集合中。如果监听器已经存在，
     * HashSet会自动去重，不会重复添加。</p>
     * 
     * @param listener 要添加的事件监听器，不能为null
     */
    @Override
    public void addApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.add((ApplicationListener<ApplicationEvent>) listener);
    }

    /**
     * 移除事件监听器
     * 
     * <p>从监听器集合中移除指定的事件监听器。如果监听器不存在，
     * 操作不会产生任何影响。</p>
     * 
     * @param listener 要移除的事件监听器
     */
    @Override
    public void removeApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.remove(listener);
    }

    /**
     * 设置BeanFactory
     * 
     * <p>通过BeanFactoryAware接口注入BeanFactory实例，
     * 子类可以使用此BeanFactory进行Bean的获取和操作。</p>
     * 
     * @param beanFactory BeanFactory实例，不能为null
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}