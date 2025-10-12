package org.winterframework.beans.factory.support;

import org.winterframework.beans.BeanException;
import org.winterframework.beans.factory.config.BeanDefinition;

/**
 * 具有自动装配能力的抽象Bean工厂
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/12
 * @description 继承AbstractBeanFactory，实现Bean的创建逻辑
 *              提供自动装配（Autowire）能力，目前实现了基本的实例化功能
 *              后续可以扩展属性注入、依赖注入等自动装配功能
 * 
 * 职责：
 * 1. 实现Bean的实例化（通过反射）
 * 2. 实现Bean的初始化（属性填充、依赖注入等）
 * 3. 将创建好的Bean注册到单例缓存中
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory{

    /**
     * Bean实例化策略
     * 默认使用SimpleInstantiationStrategy（JDK反射方式）
     * 可以通过setInstantiationStrategy()方法切换为Cglib方式
     */
    private InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

    /**
     * 创建Bean实例 - 实现父类的抽象方法
     * 
     * @param beanName Bean名称
     * @param beanDefinition Bean定义信息
     * @return 创建并初始化好的Bean实例
     * @throws BeanException 创建失败时抛出
     */
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeanException {
        // 调用实际的Bean创建方法
        return doCreateBean(beanName, beanDefinition);
    }

    /**
     * 执行Bean的创建 - 核心创建逻辑
     * 
     * Bean的生命周期简化版：
     * 1. 实例化：通过反射创建对象（newInstance）
     * 2. 属性填充：给对象的属性赋值（暂未实现，后续可扩展）
     * 3. 初始化：执行初始化方法（暂未实现，后续可扩展）
     * 4. 注册单例：将Bean放入单例缓存池
     * 
     * @param beanName Bean名称
     * @param beanDefinition Bean定义信息
     * @return 创建的Bean实例
     */
    protected Object doCreateBean(String beanName, BeanDefinition beanDefinition){
        // 从BeanDefinition中获取Bean的Class类型
        Class beanClass = beanDefinition.getBeanClass();
        Object bean = null;
        
        try {
            // 通过反射创建Bean实例
            // 使用无参构造方法创建对象（要求Bean必须有无参构造器）
            bean = createBeanInstance(beanDefinition);
        } catch (Exception e) {
            // 实例化失败，抛出Bean异常
            throw new BeanException("Instantiation of bean failed", e);
        }
        
        // 将创建好的Bean添加到单例缓存池中
        // 这样下次获取时可以直接从缓存中返回，实现单例模式
        addSingleton(beanName, bean);
        
        return bean;
    }

    /**
     * 创建Bean实例 - 使用实例化策略
     * 委托给InstantiationStrategy执行具体的实例化逻辑
     * 
     * @param beanDefinition Bean定义信息
     * @return 实例化后的Bean对象
     */
    protected Object createBeanInstance(BeanDefinition beanDefinition){
        return getInstantiationStrategy().instantiate(beanDefinition);
    }

    /**
     * 获取当前使用的实例化策略
     * 
     * @return 实例化策略对象
     */
    public InstantiationStrategy getInstantiationStrategy(){
        return instantiationStrategy;
    }

    /**
     * 设置实例化策略
     * 可以动态切换不同的实例化方式（JDK反射 或 Cglib代理）
     * 
     * @param instantiationStrategy 实例化策略对象
     */
    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy){
        this.instantiationStrategy = instantiationStrategy;
    }
}