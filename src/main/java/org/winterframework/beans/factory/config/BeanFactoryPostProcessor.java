package org.winterframework.beans.factory.config;

import org.winterframework.beans.BeanException;
import org.winterframework.beans.ConfigurableListableBeanFactory;

/**
 * BeanFactory后置处理器接口
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/18
 * @description 允许在BeanFactory标准初始化之后，自定义修改BeanDefinition的属性值
 * 
 * <p>BeanFactoryPostProcessor是Spring框架中一个重要的扩展点，它允许开发者在
 * Bean实例化之前对BeanDefinition进行修改。这个接口在以下场景中非常有用：</p>
 * 
 * <ul>
 *   <li>修改Bean的属性值</li>
 *   <li>添加新的Bean定义</li>
 *   <li>修改Bean的作用域</li>
 *   <li>实现配置的动态修改</li>
 * </ul>
 * 
 * <p>执行时机：在所有的BeanDefinition加载完成后，但在Bean实例化之前执行</p>
 * 
 * <p>使用示例：</p>
 * <pre>{@code
 * public class CustomBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
 *     @Override
 *     public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
 *         BeanDefinition beanDefinition = beanFactory.getBeanDefinition("person");
 *         PropertyValues propertyValues = beanDefinition.getPropertyValues();
 *         propertyValues.addPropertyValue(new PropertyValue("name", "ivy"));
 *     }
 * }
 * }</pre>
 * 
 * @see ConfigurableListableBeanFactory
 * @see BeanDefinition
 * @see PropertyValue
 */
public interface BeanFactoryPostProcessor {

    /**
     * 在BeanFactory标准初始化之后，修改BeanDefinition的属性值
     * 
     * <p>这个方法会在所有BeanDefinition加载完成后，但在Bean实例化之前被调用。
     * 此时可以安全地修改BeanDefinition中的属性值，这些修改会在后续的Bean
     * 实例化过程中生效。</p>
     * 
     * <p>注意事项：</p>
     * <ul>
     *   <li>不要在此方法中尝试获取Bean实例，因为此时Bean还未实例化</li>
     *   <li>可以安全地修改BeanDefinition的属性值</li>
     *   <li>可以添加新的BeanDefinition到容器中</li>
     *   <li>修改操作应该是幂等的，避免重复执行时产生副作用</li>
     * </ul>
     * 
     * @param beanFactory 可配置的BeanFactory，提供访问BeanDefinition的能力
     * @throws BeanException 如果处理过程中发生错误
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeanException;
}
