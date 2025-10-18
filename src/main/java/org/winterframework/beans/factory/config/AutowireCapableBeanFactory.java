package org.winterframework.beans.factory.config;

import org.winterframework.beans.BeanException;
import org.winterframework.beans.factory.BeanFactory;

/**
 * 具有自动装配能力的Bean工厂接口
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/17
 * @description 提供Bean的自动装配和BeanPostProcessor处理能力
 * 
 * <p>AutowireCapableBeanFactory是BeanFactory的扩展接口，提供了Bean的自动装配能力。
 * 它继承了BeanFactory的基本功能，并添加了以下高级功能：</p>
 * 
 * <ul>
 *   <li>BeanPostProcessor的执行能力</li>
 *   <li>Bean的自动装配支持</li>
 *   <li>Bean的增强和代理能力</li>
 * </ul>
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>执行BeanPostProcessor的前置处理</li>
 *   <li>执行BeanPostProcessor的后置处理</li>
 *   <li>为AOP提供基础支持</li>
 *   <li>支持Bean的自动装配</li>
 * </ul>
 * 
 * <p>这是框架中Bean自动装配功能的核心接口，为更高级的Bean工厂提供基础</p>
 * 
 * @see BeanFactory
 * @see BeanPostProcessor
 * @see ConfigurableListableBeanFactory
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

    /**
     * 执行BeanPostProcessor的前置处理方法
     * 
     * <p>遍历所有注册的BeanPostProcessor，依次执行它们的postProcessBeforeInitialization方法。
     * 这个方法会在Bean初始化方法执行之前被调用，用于对Bean进行预处理。</p>
     * 
     * <p>执行流程：</p>
     * <ol>
     *   <li>遍历所有注册的BeanPostProcessor</li>
     *   <li>依次调用每个处理器的postProcessBeforeInitialization方法</li>
     *   <li>如果某个处理器返回null，则停止后续处理</li>
     *   <li>返回最后一个处理器的结果</li>
     * </ol>
     * 
     * <p>使用场景：</p>
     * <ul>
     *   <li>修改Bean的属性值</li>
     *   <li>为Bean添加代理</li>
     *   <li>实现Bean的增强功能</li>
     *   <li>添加Bean的生命周期回调</li>
     * </ul>
     * 
     * @param existingBean 已实例化但未初始化的Bean对象
     * @param beanName Bean的名称
     * @return 处理后的Bean对象，如果某个处理器返回null则使用原始Bean
     * @throws BeanException 如果处理过程中发生错误
     * @see BeanPostProcessor#postProcessBeforeInitialization(Object, String)
     */
    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName)
            throws BeanException;

    /**
     * 执行BeanPostProcessor的后置处理方法
     * 
     * <p>遍历所有注册的BeanPostProcessor，依次执行它们的postProcessAfterInitialization方法。
     * 这个方法会在Bean初始化方法执行之后被调用，用于对Bean进行后处理。</p>
     * 
     * <p>执行流程：</p>
     * <ol>
     *   <li>遍历所有注册的BeanPostProcessor</li>
     *   <li>依次调用每个处理器的postProcessAfterInitialization方法</li>
     *   <li>如果某个处理器返回null，则停止后续处理</li>
     *   <li>返回最后一个处理器的结果</li>
     * </ol>
     * 
     * <p>使用场景：</p>
     * <ul>
     *   <li>为Bean添加代理（AOP的核心）</li>
     *   <li>实现Bean的最终增强</li>
     *   <li>注册Bean的监听器</li>
     *   <li>完成Bean的最终配置</li>
     * </ul>
     * 
     * <p>这是AOP代理创建的最佳时机，因为此时Bean已经完全初始化完成</p>
     * 
     * @param existingBean 已完全初始化的Bean对象
     * @param beanName Bean的名称
     * @return 处理后的Bean对象，如果某个处理器返回null则使用原始Bean
     * @throws BeanException 如果处理过程中发生错误
     * @see BeanPostProcessor#postProcessAfterInitialization(Object, String)
     */
    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName)
            throws BeanException;
}
