package org.winterframework.beans.factory.support;

import org.winterframework.beans.BeanException;
import org.winterframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * 简单的Bean实例化策略 - JDK反射实现
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/12
 * @description 使用JDK自带的反射机制实例化Bean
 *              通过Class.getDeclaredConstructor().newInstance()创建对象
 *              这是最基础、最常用的实例化方式
 *              
 * 优点：
 * - 简单直接，无需额外依赖
 * - 性能较好，适合普通场景
 * 
 * 缺点：
 * - 只能调用无参构造器
 * - 不支持方法拦截等高级特性
 * 
 * 适用场景：
 * - 普通的Java Bean实例化
 * - 不需要AOP代理的场景
 */
public class SimpleInstantiationStrategy implements InstantiationStrategy {
    /**
     * 使用JDK反射实例化Bean
     * 通过无参构造器创建对象实例
     *
     * @param beanDefinition Bean定义信息
     * @return 实例化后的Bean对象
     * @throws BeanException 如果实例化失败（如没有无参构造器、构造器不可访问等）
     */
    @Override
    public Object instantiate(BeanDefinition beanDefinition) throws BeanException {
        Class beanClass = beanDefinition.getBeanClass();
        try {
            Constructor constructor = beanClass.getDeclaredConstructor();
            return constructor.newInstance();
        } catch (Exception e) {
            throw new BeanException("Failed to instantiate [" + beanClass.getName() + "]", e);
        }
    }
}