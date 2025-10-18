package org.winterframework.beans.factory;

import org.winterframework.beans.BeanException;

/**
 * BeanFactory感知接口
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/18
 * @description 用于让Bean感知BeanFactory容器的接口
 * 
 * <p>BeanFactoryAware接口是Spring框架中的一个重要Aware接口，它允许Bean在初始化过程中
 * 获取对BeanFactory容器的引用，从而可以访问容器的功能。</p>
 * 
 * <p>使用场景：</p>
 * <ul>
 *   <li>Bean需要动态获取其他Bean实例</li>
 *   <li>Bean需要检查容器中是否存在某个Bean</li>
 *   <li>Bean需要访问BeanFactory的高级功能</li>
 *   <li>实现自定义的Bean查找逻辑</li>
 * </ul>
 * 
 * <p>执行时机：在Bean属性设置完成后，BeanPostProcessor前置处理之前</p>
 * 
 * <p>使用示例：</p>
 * <pre>{@code
 * public class MyService implements BeanFactoryAware {
 *     private BeanFactory beanFactory;
 *     
 *     @Override
 *     public void setBeanFactory(BeanFactory beanFactory) throws BeanException {
 *         this.beanFactory = beanFactory;
 *     }
 *     
 *     public void doSomething() {
 *         // 动态获取其他Bean
 *         UserService userService = beanFactory.getBean("userService", UserService.class);
 *         userService.createUser("张三");
 *     }
 * }
 * }</pre>
 * 
 * <p>注意事项：</p>
 * <ul>
 *   <li>BeanFactoryAware在BeanPostProcessor前置处理之前执行</li>
 *   <li>如果同时实现了BeanFactoryAware和ApplicationContextAware，ApplicationContextAware优先级更高</li>
 *   <li>建议优先使用ApplicationContextAware，它提供了更丰富的功能</li>
 * </ul>
 * 
 * @see Aware
 * @see BeanFactory
 * @see org.winterframework.context.ApplicationContextAware
 */
public interface BeanFactoryAware extends Aware {

    /**
     * 设置BeanFactory容器引用
     * 
     * <p>这个方法会在Bean初始化过程中被容器调用，将BeanFactory实例注入到Bean中。
     * 此时Bean可以通过这个引用来访问容器的各种功能。</p>
     * 
     * @param beanFactory BeanFactory容器实例
     * @throws BeanException 如果设置过程中发生错误
     */
    void setBeanFactory(BeanFactory beanFactory) throws BeanException;
}
