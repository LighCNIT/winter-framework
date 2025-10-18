package org.winterframework.beans.factory.config;

import org.winterframework.beans.BeanException;

/**
 * Bean后置处理器接口
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/18
 * @description 用于对实例化后的Bean进行修改的扩展接口
 * 
 * <p>BeanPostProcessor是Spring框架中另一个重要的扩展点，它允许开发者在
 * Bean实例化之后、初始化前后对Bean进行自定义处理。这个接口在以下场景中非常有用：</p>
 * 
 * <ul>
 *   <li>修改Bean的属性值</li>
 *   <li>为Bean添加代理（AOP的基础）</li>
 *   <li>实现Bean的增强功能</li>
 *   <li>添加Bean的生命周期回调</li>
 *   <li>实现Bean的监控和统计</li>
 * </ul>
 * 
 * <p>执行时机：在Bean实例化之后，初始化方法执行前后</p>
 * 
 * <p>使用示例：</p>
 * <pre>{@code
 * public class CustomBeanPostProcessor implements BeanPostProcessor {
 *     @Override
 *     public Object postProcessBeforeInitialization(Object bean, String beanName) {
 *         if ("car".equals(beanName)) {
 *             ((Car) bean).setBrand("lamborghini");
 *         }
 *         return bean;
 *     }
 * 
 *     @Override
 *     public Object postProcessAfterInitialization(Object bean, String beanName) {
 *         // 可以在这里添加代理逻辑
 *         return bean;
 *     }
 * }
 * }</pre>
 * 
 * @see BeanFactoryPostProcessor
 * @see ConfigurableBeanFactory#addBeanPostProcessor(BeanPostProcessor)
 */
public interface BeanPostProcessor {

    /**
     * Bean初始化之前执行此方法
     * 
     * <p>这个方法会在Bean实例化之后、初始化方法执行之前被调用。
     * 此时Bean已经创建完成，但还没有执行初始化方法，可以安全地修改Bean的属性。</p>
     * 
     * <p>注意事项：</p>
     * <ul>
     *   <li>可以修改Bean的属性值</li>
     *   <li>可以返回原始Bean或修改后的Bean</li>
     *   <li>如果返回null，会使用原始Bean</li>
     *   <li>可以在这里为AOP做准备</li>
     * </ul>
     * 
     * @param bean 已实例化但未初始化的Bean对象
     * @param beanName Bean的名称
     * @return 处理后的Bean对象，如果返回null则使用原始Bean
     * @throws BeanException 如果处理过程中发生错误
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeanException;

    /**
     * Bean初始化之后执行此方法
     * 
     * <p>这个方法会在Bean初始化方法执行之后被调用。
     * 此时Bean已经完全初始化完成，可以在这里进行最终的增强处理，
     * 比如添加代理、注册监听器等。</p>
     * 
     * <p>注意事项：</p>
     * <ul>
     *   <li>可以返回原始Bean或增强后的Bean</li>
     *   <li>如果返回null，会使用原始Bean</li>
     *   <li>这是AOP代理创建的最佳时机</li>
     *   <li>可以在这里添加Bean的最终增强逻辑</li>
     * </ul>
     * 
     * @param bean 已完全初始化的Bean对象
     * @param beanName Bean的名称
     * @return 处理后的Bean对象，如果返回null则使用原始Bean
     * @throws BeanException 如果处理过程中发生错误
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException;
}
