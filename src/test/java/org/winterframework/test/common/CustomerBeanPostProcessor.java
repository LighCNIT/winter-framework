package org.winterframework.test.common;

import org.winterframework.beans.BeanException;
import org.winterframework.beans.factory.config.BeanPostProcessor;
import org.winterframework.test.bean.Car;

/**
 * 自定义BeanPostProcessor实现类
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/18
 * @description 用于测试BeanPostProcessor功能的实现类
 * 
 * <p>本类实现了BeanPostProcessor接口，用于演示如何在Bean实例化后、
 * 初始化前后对Bean进行处理。</p>
 * 
 * <p>功能说明：</p>
 * <ul>
 *   <li>在postProcessBeforeInitialization方法中修改car Bean的brand属性</li>
 *   <li>将brand属性值修改为"lamborghini"</li>
 *   <li>演示BeanPostProcessor的基本用法</li>
 * </ul>
 * 
 * <p>使用场景：测试BeanPostProcessor功能</p>
 * 
 * @see BeanPostProcessor
 * @see BeanPostProcessor#postProcessBeforeInitialization(Object, String)
 * @see BeanPostProcessor#postProcessAfterInitialization(Object, String)
 */
public class CustomerBeanPostProcessor implements BeanPostProcessor {
    /**
     * Bean初始化之前执行此方法
     * 
     * <p>本方法演示了BeanPostProcessor前置处理的基本用法：</p>
     * <ol>
     *   <li>打印Bean信息和处理器名称</li>
     *   <li>检查Bean名称是否为"car"</li>
     *   <li>如果是car Bean，则将其brand属性修改为"lamborghini"</li>
     *   <li>返回处理后的Bean</li>
     * </ol>
     * 
     * <p>执行时机：在Bean实例化之后、初始化方法执行之前</p>
     * 
     * @param bean 已实例化但未初始化的Bean对象
     * @param beanName Bean的名称
     * @return 处理后的Bean对象
     * @throws BeanException 如果处理过程中发生错误
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeanException {
        System.out.println("before bean:"+bean);
        System.out.println("CustomerBeanPostProcessor#postProcessBeforeInitialization");
        
        // 换兰博基尼：如果是car Bean，则修改其brand属性
        if ("car".equals(beanName)) {
            ((Car) bean).setBrand("lamborghini");
        }
        return bean;
    }

    /**
     * Bean初始化之后执行此方法
     * 
     * <p>本方法演示了BeanPostProcessor后置处理的基本用法：</p>
     * <ol>
     *   <li>打印处理器名称</li>
     *   <li>返回原始Bean（不进行修改）</li>
     * </ol>
     * 
     * <p>执行时机：在Bean初始化方法执行之后</p>
     * 
     * <p>注意：这里只是演示，实际使用中可以在这里添加代理、注册监听器等</p>
     * 
     * @param bean 已完全初始化的Bean对象
     * @param beanName Bean的名称
     * @return 处理后的Bean对象
     * @throws BeanException 如果处理过程中发生错误
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException {
        System.out.println("CustomerBeanPostProcessor#postProcessAfterInitialization");
        return bean;
    }
}