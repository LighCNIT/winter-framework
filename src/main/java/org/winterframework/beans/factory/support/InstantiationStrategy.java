package org.winterframework.beans.factory.support;

import org.winterframework.beans.BeanException;
import org.winterframework.beans.factory.config.BeanDefinition;

/**
 * Bean实例化策略接口
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/12
 * @description 定义Bean实例化的策略接口，支持多种实例化方式
 *              采用策略模式，允许灵活切换不同的实例化实现
 *              
 * 实现类：
 * - SimpleInstantiationStrategy: 使用JDK反射进行实例化
 * - CglibSubclassInstantiationStrategy: 使用Cglib动态代理进行实例化
 * 
 * 设计模式：策略模式（Strategy Pattern）
 * - 定义一系列算法，把它们封装起来，并且使它们可以相互替换
 * - 本策略接口让实例化算法独立于使用它的客户端
 */
public interface InstantiationStrategy {

    /**
     * 实例化Bean
     * 
     * @param beanDefinition Bean的定义信息，包含Bean的Class类型
     * @return 实例化后的Bean对象
     * @throws BeanException 实例化失败时抛出异常
     */
    Object instantiate(BeanDefinition beanDefinition) throws BeanException;
}
