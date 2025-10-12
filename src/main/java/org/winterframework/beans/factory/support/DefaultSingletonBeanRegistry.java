package org.winterframework.beans.factory.support;

import org.winterframework.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * 默认单例Bean注册表实现类
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/12
 * @description 实现了SingletonBeanRegistry接口，提供单例Bean的存储和管理
 *              使用HashMap作为单例Bean的缓存容器（一级缓存）
 *              这是Spring三级缓存中的第一级缓存的简化版本
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    /**
     * 单例对象缓存池 - 一级缓存
     * key: beanName (Bean的唯一标识)
     * value: Bean实例对象
     * 
     * 作用：缓存已经完全初始化好的单例Bean，避免重复创建
     */
    private Map<String, Object> singletonObjects = new HashMap<>();

    /**
     * 获取已注册的单例Bean
     * 
     * @param beanName Bean名称
     * @return Bean实例，如果不存在返回null
     */
    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    /**
     * 添加单例Bean到缓存池
     * 使用protected修饰，只允许子类调用
     * 
     * @param beanName Bean名称
     * @param singletonObject 单例Bean实例
     */
    protected void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }
}