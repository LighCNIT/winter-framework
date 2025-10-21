package org.winterframework.test.ioc.common;

import org.winterframework.beans.BeanException;
import org.winterframework.beans.factory.ConfigurableListableBeanFactory;
import org.winterframework.beans.factory.PropertyValue;
import org.winterframework.beans.factory.PropertyValues;
import org.winterframework.beans.factory.config.BeanDefinition;
import org.winterframework.beans.factory.config.BeanFactoryPostProcessor;

/**
 * 自定义BeanFactoryPostProcessor实现类
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/18
 * @description 用于测试BeanFactoryPostProcessor功能的实现类
 * 
 * <p>本类实现了BeanFactoryPostProcess接口，用于演示如何在Bean实例化前
 * 修改BeanDefinition的属性值。</p>
 * 
 * <p>功能说明：</p>
 * <ul>
 *   <li>在postProcessBeanFactory方法中修改person Bean的name属性</li>
 *   <li>将name属性值修改为"ivy"</li>
 *   <li>演示BeanFactoryPostProcessor的基本用法</li>
 * </ul>
 * 
 * <p>使用场景：测试BeanFactoryPostProcessor功能</p>
 * 
 * @see CustomBeanFactoryPostProcessor
 * @see BeanDefinition
 * @see PropertyValue
 */
public class CustomBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    /**
     * 在BeanFactory标准初始化之后，修改BeanDefinition的属性值
     * 
     * <p>本方法演示了BeanFactoryPostProcessor的基本用法：</p>
     * <ol>
     *   <li>获取person Bean的BeanDefinition</li>
     *   <li>获取BeanDefinition的PropertyValues</li>
     *   <li>添加新的PropertyValue，将name属性修改为"ivy"</li>
     * </ol>
     * 
     * <p>执行时机：在所有的BeanDefinition加载完成后，但在Bean实例化之前</p>
     * 
     * @param beanFactory 可配置的BeanFactory，提供访问BeanDefinition的能力
     * @throws BeanException 如果处理过程中发生错误
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeanException {
        System.out.println("CustomBeanFactoryPostProcessor#postProcessBeanFactory");
        // 获取person Bean的BeanDefinition
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("person");
        PropertyValues propertyValues = beanDefinition.getPropertyValues();

        // 将person的name属性改为ivy
        propertyValues.addPropertyValue(new PropertyValue("name", "ivy"));
    }
}