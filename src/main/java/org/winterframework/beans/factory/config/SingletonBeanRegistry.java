package org.winterframework.beans.factory.config;

/**
 * 单例Bean注册表接口
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/12
 * @description 定义了单例Bean的注册和获取规范
 *              单例模式：确保一个Bean在容器中只有一个实例
 *              这是Spring中最常用的Bean作用域
 */
public interface SingletonBeanRegistry {

    /**
     * 获取单例Bean实例
     * 
     * @param beanName Bean的唯一标识名称
     * @return 单例Bean实例，如果不存在则返回null
     */
    Object getSingleton(String beanName);
}
