package org.winterframework.context.support;

import org.winterframework.beans.BeanException;
import org.winterframework.beans.factory.config.BeanPostProcessor;
import org.winterframework.context.ApplicationContext;
import org.winterframework.context.ApplicationContextAware;

/**
 * ApplicationContextAware处理器
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/18
 * @description 用于处理ApplicationContextAware接口的BeanPostProcessor实现
 * 
 * <p>ApplicationContextAwareProcessor是一个特殊的BeanPostProcessor，专门用于处理
 * 实现了ApplicationContextAware接口的Bean。它会在Bean初始化前将ApplicationContext
 * 实例注入到这些Bean中。</p>
 * 
 * <p>设计特点：</p>
 * <ul>
 *   <li>专门的Aware处理器：专门处理ApplicationContextAware接口</li>
 *   <li>前置处理：在BeanPostProcessor前置处理阶段执行</li>
 *   <li>自动注册：在ApplicationContext初始化时自动注册</li>
 *   <li>优先级高：确保ApplicationContextAware优先于BeanFactoryAware执行</li>
 * </ul>
 * 
 * <p>执行时机：在Bean属性设置完成后，BeanPostProcessor前置处理阶段</p>
 * 
 * <p>使用场景：</p>
 * <ul>
 *   <li>让Bean感知ApplicationContext容器</li>
 *   <li>实现Bean对容器功能的访问</li>
 *   <li>支持动态Bean查找和容器操作</li>
 * </ul>
 * 
 * @see BeanPostProcessor
 * @see ApplicationContextAware
 * @see ApplicationContext
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    /**
     * ApplicationContext实例
     * 用于注入到实现了ApplicationContextAware接口的Bean中
     */
    private final ApplicationContext applicationContext;

    /**
     * 构造方法
     * 
     * @param applicationContext ApplicationContext实例
     */
    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    /**
     * Bean初始化前的处理
     * 
     * <p>检查Bean是否实现了ApplicationContextAware接口，如果是则注入ApplicationContext实例。</p>
     * 
     * <p>执行逻辑：</p>
     * <ol>
     *   <li>检查Bean是否实现了ApplicationContextAware接口</li>
     *   <li>如果是，调用其setApplicationContext方法注入ApplicationContext实例</li>
     *   <li>返回处理后的Bean</li>
     * </ol>
     * 
     * @param bean 已实例化但未初始化的Bean对象
     * @param beanName Bean的名称
     * @return 处理后的Bean对象
     * @throws BeanException 如果处理过程中发生错误
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeanException {
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

    /**
     * Bean初始化后的处理
     * 
     * <p>ApplicationContextAwareProcessor不需要在初始化后进行特殊处理，
     * 直接返回原始Bean。</p>
     * 
     * @param bean 已完全初始化的Bean对象
     * @param beanName Bean的名称
     * @return 原始Bean对象
     * @throws BeanException 如果处理过程中发生错误
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException {
        return bean;
    }
}