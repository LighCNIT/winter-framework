package org.winterframework.beans.factory.config;

import org.winterframework.beans.BeanException;
import org.winterframework.beans.factory.HierarchicalBeanFactory;

/**
 * 可配置的Bean工厂接口
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/17
 * @description 提供Bean工厂的配置能力，包括BeanPostProcessor管理
 * 
 * <p>ConfigurableBeanFactory是BeanFactory的扩展接口，提供了Bean工厂的配置能力。
 * 它继承了HierarchicalBeanFactory和SingletonBeanRegistry，整合了以下功能：</p>
 * 
 * <ul>
 *   <li>HierarchicalBeanFactory：提供Bean工厂的层次结构支持</li>
 *   <li>SingletonBeanRegistry：提供单例Bean的注册和管理</li>
 *   <li>BeanPostProcessor管理：提供Bean后置处理器的注册功能</li>
 * </ul>
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>管理BeanPostProcessor</li>
 *   <li>提供单例Bean的注册和获取</li>
 *   <li>支持Bean工厂的层次结构</li>
 * </ul>
 * 
 * <p>这是框架中BeanFactory配置功能的基础接口，为更高级的配置接口提供基础</p>
 * 
 * @see HierarchicalBeanFactory
 * @see SingletonBeanRegistry
 * @see BeanPostProcessor
 * @see ConfigurableListableBeanFactory
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

    /**
     * 添加Bean后置处理器
     * 
     * <p>向Bean工厂注册一个BeanPostProcessor，该处理器会在Bean实例化后、
     * 初始化前后被调用，用于对Bean进行自定义处理。</p>
     * 
     * <p>BeanPostProcessor的作用：</p>
     * <ul>
     *   <li>在Bean初始化前进行预处理</li>
     *   <li>在Bean初始化后进行后处理</li>
     *   <li>可以修改Bean的属性值</li>
     *   <li>可以为Bean添加代理（AOP的基础）</li>
     *   <li>可以实现Bean的增强功能</li>
     * </ul>
     * 
     * <p>执行时机：</p>
     * <ol>
     *   <li>Bean实例化完成后</li>
     *   <li>Bean初始化方法执行前（postProcessBeforeInitialization）</li>
     *   <li>Bean初始化方法执行后（postProcessAfterInitialization）</li>
     * </ol>
     * 
     * @param beanPostProcessor 要添加的Bean后置处理器
     * @see BeanPostProcessor
     * @see BeanPostProcessor#postProcessBeforeInitialization(Object, String)
     * @see BeanPostProcessor#postProcessAfterInitialization(Object, String)
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

    /**
     * 销毁单例bean
     */
    void destroySingletons();
}
