package org.winterframework.beans.factory;


import org.winterframework.beans.BeanException;

/**
 * Bean工厂接口 - IoC容器的核心接口
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/12
 * @description 定义了获取Bean实例的基本方法，是整个IoC容器的顶层抽象
 *              类似于Spring中的BeanFactory接口，提供了依赖查找的能力
 */
public interface BeanFactory {

    /**
     * 根据Bean名称获取Bean实例
     * 
     * @param name Bean的唯一标识名称
     * @return Bean实例对象
     * @throws BeanException 如果Bean不存在或创建失败时抛出
     */
    Object getBean(String name) throws BeanException;

    <T> T getBean(String name,Class<T> requiredType) throws BeanException;

    <T> T getBean(Class<T> requiredType) throws BeanException;
}