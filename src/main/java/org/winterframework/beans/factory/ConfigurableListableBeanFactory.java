package org.winterframework.beans.factory;

import org.winterframework.beans.BeanException;
import org.winterframework.beans.factory.config.AutowireCapableBeanFactory;
import org.winterframework.beans.factory.config.BeanDefinition;
import org.winterframework.beans.factory.config.BeanPostProcessor;
import org.winterframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * 可配置的可列表Bean工厂接口
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/17
 * @description 集成了可列表Bean工厂、自动装配Bean工厂和可配置Bean工厂的功能
 * 
 * <p>ConfigurableListableBeanFactory是Winter Framework中最高级别的BeanFactory接口，
 * 它整合了以下功能：</p>
 * 
 * <ul>
 *   <li>ListableBeanFactory：提供Bean的列表查询功能</li>
 *   <li>AutowireCapableBeanFactory：提供自动装配和BeanPostProcessor功能</li>
 *   <li>ConfigurableBeanFactory：提供BeanPostProcessor管理功能</li>
 * </ul>
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>获取BeanDefinition信息</li>
 *   <li>提前实例化所有单例Bean</li>
 *   <li>管理BeanPostProcessor</li>
 *   <li>提供完整的Bean查询和管理能力</li>
 * </ul>
 * 
 * <p>这是框架中最完整的BeanFactory接口，通常被DefaultListableBeanFactory实现</p>
 * 
 * @see ListableBeanFactory
 * @see AutowireCapableBeanFactory
 * @see ConfigurableBeanFactory
 * @see org.winterframework.beans.factory.support.DefaultListableBeanFactory
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

    /**
     * 获取Bean定义信息
     * 
     * <p>根据Bean名称获取对应的BeanDefinition对象，BeanDefinition包含了
     * Bean的完整元数据信息，如类型、属性值、依赖关系等。</p>
     * 
     * @param beanName Bean的名称
     * @return Bean定义信息
     * @throws BeanException 如果Bean定义不存在
     */
    BeanDefinition getBeanDefinition(String beanName) throws BeanException;

    /**
     * 提前实例化所有单例Bean
     * 
     * <p>遍历容器中所有的BeanDefinition，提前创建所有单例Bean的实例。
     * 这通常在应用启动时调用，可以提前发现Bean创建过程中的问题。</p>
     * 
     * <p>执行时机：在BeanFactoryPostProcessor执行完成后，应用启动时</p>
     * 
     * <p>注意事项：</p>
     * <ul>
     *   <li>只实例化单例Bean，原型Bean不会提前创建</li>
     *   <li>会触发BeanPostProcessor的执行</li>
     *   <li>如果Bean创建失败，会抛出异常</li>
     * </ul>
     * 
     * @throws BeanException 如果Bean实例化过程中发生错误
     */
    void preInstantiateSingletons() throws BeanException;

    /**
     * 添加Bean后置处理器
     * 
     * <p>向容器注册一个BeanPostProcessor，该处理器会在Bean实例化后、
     * 初始化前后被调用，用于对Bean进行自定义处理。</p>
     * 
     * <p>使用场景：</p>
     * <ul>
     *   <li>修改Bean的属性值</li>
     *   <li>为Bean添加代理（AOP）</li>
     *   <li>实现Bean的增强功能</li>
     *   <li>添加Bean的生命周期回调</li>
     * </ul>
     * 
     * @param beanPostProcessor 要添加的Bean后置处理器
     * @see BeanPostProcessor
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}
