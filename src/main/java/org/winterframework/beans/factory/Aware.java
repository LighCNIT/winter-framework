package org.winterframework.beans.factory;

/**
 * Aware接口 - 标记接口
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/18
 * @description 标记接口，用于标识Bean能够感知Spring容器的特定组件
 * 
 * <p>Aware接口是Spring框架中的一个重要设计模式，用于实现依赖注入的反向控制。
 * 当一个Bean实现了Aware接口的子接口时，容器会在适当的时机将相应的组件注入到Bean中。</p>
 * 
 * <p>设计思想：</p>
 * <ul>
 *   <li>反向控制：Bean主动感知容器组件，而不是被动等待注入</li>
 *   <li>生命周期感知：在Bean生命周期的特定阶段注入容器组件</li>
 *   <li>解耦设计：Bean可以访问容器功能而不需要直接依赖容器</li>
 * </ul>
 * 
 * <p>常见的Aware子接口：</p>
 * <ul>
 *   <li>BeanFactoryAware：感知BeanFactory</li>
 *   <li>ApplicationContextAware：感知ApplicationContext</li>
 *   <li>BeanNameAware：感知Bean名称</li>
 *   <li>ApplicationEventPublisherAware：感知事件发布器</li>
 * </ul>
 * 
 * <p>使用场景：</p>
 * <ul>
 *   <li>Bean需要访问容器功能（如获取其他Bean）</li>
 *   <li>Bean需要知道自己的名称或容器信息</li>
 *   <li>Bean需要发布事件或访问容器服务</li>
 * </ul>
 * 
 * @see BeanFactoryAware
 * @see org.winterframework.context.ApplicationContextAware
 */
public interface Aware {
}
