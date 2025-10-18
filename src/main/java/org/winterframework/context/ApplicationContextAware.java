package org.winterframework.context;

import org.winterframework.beans.BeanException;
import org.winterframework.beans.factory.Aware;

/**
 * ApplicationContext感知接口
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/18
 * @description 用于让Bean感知ApplicationContext容器的接口
 * 
 * <p>ApplicationContextAware接口是Spring框架中最重要的Aware接口之一，它允许Bean在初始化过程中
 * 获取对ApplicationContext容器的引用，从而可以访问容器的所有功能。</p>
 * 
 * <p>使用场景：</p>
 * <ul>
 *   <li>Bean需要动态获取其他Bean实例</li>
 *   <li>Bean需要访问ApplicationContext的高级功能（如国际化、事件发布等）</li>
 *   <li>Bean需要获取容器中的资源文件</li>
 *   <li>Bean需要发布应用事件</li>
 *   <li>实现程序化的Bean查找和操作</li>
 * </ul>
 * 
 * <p>执行时机：通过ApplicationContextAwareProcessor在BeanPostProcessor前置处理阶段执行</p>
 * 
 * <p>使用示例：</p>
 * <pre>{@code
 * public class MyService implements ApplicationContextAware {
 *     private ApplicationContext applicationContext;
 *     
 *     @Override
 *     public void setApplicationContext(ApplicationContext applicationContext) throws BeanException {
 *         this.applicationContext = applicationContext;
 *     }
 *     
 *     public void doSomething() {
 *         // 动态获取其他Bean
 *         UserService userService = applicationContext.getBean("userService", UserService.class);
 *         
 *         // 发布应用事件
 *         applicationContext.publishEvent(new CustomEvent("Hello World"));
 *         
 *         // 获取资源文件
 *         Resource resource = applicationContext.getResource("classpath:config.properties");
 *     }
 * }
 * }</pre>
 * 
 * <p>注意事项：</p>
 * <ul>
 *   <li>ApplicationContextAware通过ApplicationContextAwareProcessor处理</li>
 *   <li>优先级高于BeanFactoryAware</li>
 *   <li>ApplicationContext提供了比BeanFactory更丰富的功能</li>
 *   <li>在BeanPostProcessor前置处理阶段执行</li>
 * </ul>
 * 
 * @see Aware
 * @see ApplicationContext
 * @see org.winterframework.beans.factory.BeanFactoryAware
 * @see ApplicationContextAwareProcessor
 */
public interface ApplicationContextAware extends Aware {

    /**
     * 设置ApplicationContext容器引用
     * 
     * <p>这个方法会在Bean初始化过程中被ApplicationContextAwareProcessor调用，
     * 将ApplicationContext实例注入到Bean中。此时Bean可以通过这个引用来访问容器的所有功能。</p>
     * 
     * @param applicationContext ApplicationContext容器实例
     * @throws BeanException 如果设置过程中发生错误
     */
    void setApplicationContext(ApplicationContext applicationContext) throws BeanException;
}
