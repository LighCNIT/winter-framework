package org.winterframework.beans.factory.support;

import org.winterframework.beans.BeanException;
import org.winterframework.beans.factory.DisposableBean;
import org.winterframework.beans.factory.config.SingletonBeanRegistry;

import java.util.ArrayList;
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
     * 可销毁Bean的注册表
     * key: beanName (Bean的唯一标识)
     * value: DisposableBean (实现了DisposableBean接口的Bean)
     * 
     * 作用：存储需要在容器关闭时执行销毁逻辑的Bean
     */
    private final Map<String, DisposableBean> disposableBeans = new HashMap<>();

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
     * 添加单例Bean到缓存池*
     * @param beanName Bean名称
     * @param singletonObject 单例Bean实例
     */
    @Override
    public void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }

    /**
     * 注册可销毁的Bean
     * 
     * <p>将需要执行销毁逻辑的Bean注册到disposableBeans中，
     * 这些Bean会在容器关闭时按照依赖关系的逆序执行销毁方法。</p>
     * 
     * @param beanName Bean名称
     * @param bean 实现了DisposableBean接口的Bean实例
     */
    public void registerDisposableBean(String beanName, DisposableBean bean) {
        disposableBeans.put(beanName, bean);
    }

    /**
     * 销毁所有单例Bean
     * 
     * <p>按照Bean依赖关系的逆序执行所有已注册Bean的销毁方法。
     * 这确保了依赖Bean在被依赖Bean之前被销毁。</p>
     * 
     * <p>执行流程：</p>
     * <ol>
     *   <li>获取所有已注册的DisposableBean名称</li>
     *   <li>按照逆序执行每个Bean的destroy()方法</li>
     *   <li>如果销毁过程中发生异常，记录异常但继续销毁其他Bean</li>
     *   <li>清空所有缓存</li>
     * </ol>
     */
    public void destroySingletons() {
        ArrayList<String> beanNames = new ArrayList<>(disposableBeans.keySet());
        for (String beanName : beanNames){
            DisposableBean disposableBean =  disposableBeans.remove(beanName);
            try {
                disposableBean.destroy();
            }catch (Exception e){
                throw new BeanException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
            }
        }
    }
}