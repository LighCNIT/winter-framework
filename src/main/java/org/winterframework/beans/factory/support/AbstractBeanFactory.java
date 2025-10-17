package org.winterframework.beans.factory.support;

import org.winterframework.beans.BeanException;
import org.winterframework.beans.factory.BeanFactory;
import org.winterframework.beans.factory.config.BeanDefinition;
import org.winterframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * 抽象Bean工厂 - 模板方法模式的应用
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/12
 * @description 实现了BeanFactory接口，定义了获取Bean的模板流程
 *              继承DefaultSingletonBeanRegistry，拥有单例Bean管理能力
 *              
 * 设计模式：模板方法模式
 * - 父类定义算法骨架（getBean方法）
 * - 子类实现具体步骤（createBean、getBeanDefinition）
 * 
 * 职责：
 * 1. 定义获取Bean的标准流程
 * 2. 整合单例缓存和Bean创建逻辑
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {

    /**
     * 获取Bean实例 - 模板方法
     * 
     * 核心流程（类似Spring的Bean获取流程）：
     * 1. 先从单例缓存中获取（getSingleton）
     * 2. 如果缓存中存在，直接返回（避免重复创建）
     * 3. 如果缓存中不存在：
     *    a. 获取Bean的定义信息（getBeanDefinition）
     *    b. 根据定义信息创建Bean实例（createBean）
     * 
     * @param name Bean名称
     * @return Bean实例对象
     * @throws BeanException 如果Bean创建失败
     */
    @Override
    public Object getBean(String name) throws BeanException{
        // 第一步：尝试从单例缓存池中获取Bean
        Object bean = getSingleton(name);
        if (bean != null){
            // 缓存命中，直接返回
            return bean;
        }
        
        // 第二步：缓存未命中，需要创建新的Bean实例
        // 先获取Bean的定义信息
        BeanDefinition beanDefinition = getBeanDefinition(name);
        
        // 第三步：根据BeanDefinition创建Bean实例
        return createBean(name, beanDefinition);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeanException {
        return ((T) getBean(name));
    }

    /**
     * 创建Bean实例 - 抽象方法，由子类实现
     * 
     * @param beanName Bean名称
     * @param beanDefinition Bean定义信息
     * @return 创建的Bean实例
     * @throws BeanException 创建失败时抛出
     */
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition) throws BeanException;

    /**
     * 获取Bean定义信息 - 抽象方法，由子类实现
     * 子类需要维护BeanDefinition的存储结构
     * 
     * @param beanName Bean名称
     * @return Bean定义信息
     * @throws BeanException 如果Bean定义不存在
     */
    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeanException;
}