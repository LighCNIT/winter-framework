package org.winterframework.beans.factory;

import org.winterframework.beans.BeanException;

/**
 * Bean初始化回调接口
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/18
 * @description 用于在Bean属性设置完成后执行自定义初始化逻辑的接口
 * 
 * <p>InitializingBean接口是Spring框架中发布的一个重要的生命周期接口，
 * 它允许Bean在属性设置完成后执行自定义的初始化逻辑。</p>
 * 
 * <p>使用场景：</p>
 * <ul>
 *   <li>在属性注入完成后进行额外的初始化工作</li>
 *   <li>验证配置属性的有效性</li>
 *   <li>建立与其他服务的连接</li>
 *   <li>启动后台线程或定时任务</li>
 *   <li>执行任何需要在Bean完全初始化后执行的逻辑</li>
 * </ul>
 * 
 * <p>执行时机：在Bean属性设置完成后，BeanPostProcessor前置处理之后</p>
 * 
 * <p>使用示例：</p>
 * <pre>{@code
 * public class DatabaseService implements InitializingBean {
 *     private String url;
 *     private String username;
 *     private String password;
 *     
 *     // getter/setter方法...
 *     
 *     @Override
 *     public void afterPropertiesSet() throws BeanException {
 *         // 验证数据库连接参数
 *         if (url == null || username == null || password == null) {
 *             throw new BeanException("Database connection parameters cannot be null");
 *         }
 *         // 建立数据库连接
 *         initializeConnection();
 *         System.out.println("Database connection initialized successfully");
 *     }
 * }
 * }</pre>
 * 
 * <p>注意事项：</p>
 * <ul>
 *   <li>此方法会在BeanPostProcessor前置处理之后执行</li>
 *   <li>如果初始化失败，会抛出BeanException</li>
 *   <li>建议在XML配置中使用init-method属性替代实现此接口</li>
 *   <li>如果同时实现了此接口和配置了init-method，会按顺序执行</li>
 * </ul>
 * 
 * @see DisposableBean
 * @see BeanPostProcessor
 * @see BeanDefinition#setInitMethodName(String)
 */
public interface InitializingBean {

    /**
     * 在Bean属性设置完成后执行初始化逻辑
     * 
     * <p>这个方法会在Bean的所有属性设置完成后被调用，
     * 此时Bean已经完全构造完成，可以安全地访问所有属性。</p>
     * 
     * <p>典型的初始化工作包括：</p>
     * <ul>
     *   <li>验证配置属性的有效性</li>
     *   <li>建立外部资源连接</li>
     *   <li>启动后台服务</li>
     *   <li>执行任何需要在Bean完全初始化后执行的逻辑</li>
     * </ul>
     * 
     * @throws BeanException 如果初始化过程中发生错误
     */
    void afterPropertiesSet() throws BeanException;
}
