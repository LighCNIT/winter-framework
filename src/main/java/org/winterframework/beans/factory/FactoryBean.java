package org.winterframework.beans.factory;

import org.winterframework.beans.BeanException;

/**
 * FactoryBean接口 - 用于创建复杂对象的工厂Bean
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/19
 * @description 当需要创建复杂对象或需要特殊初始化逻辑时，可以使用FactoryBean
 *              框架会自动识别FactoryBean并调用其getObject()方法获取实际的对象
 *              
 * 设计特点：
 * 1. 延迟创建：只有在实际获取Bean时才调用getObject()方法
 * 2. 作用域支持：通过isSingleton()方法控制返回对象的作用域
 * 3. 异常处理：getObject()方法可以抛出BeanException
 * 4. 类型安全：使用泛型确保返回对象的类型安全
 * 
 * 使用场景：
 * - 创建复杂对象（如代理对象、连接池等）
 * - 需要特殊初始化逻辑的对象
 * - 需要根据配置动态创建的对象
 * - 需要包装或增强现有对象
 * 
 * 注意事项：
 * - FactoryBean本身也会被注册为Bean
 * - 获取FactoryBean实例需要在Bean名称前加"&"前缀
 * - 直接通过Bean名称获取的是FactoryBean创建的对象，不是FactoryBean本身
 */
public interface FactoryBean<T> {

    /**
     * 获取由FactoryBean创建的对象实例
     * 
     * <p>此方法在每次需要获取Bean实例时被调用，具体调用时机取决于isSingleton()的返回值：</p>
     * <ul>
     *   <li>如果isSingleton()返回true：只在第一次调用时创建对象，后续调用返回缓存的对象</li>
     *   <li>如果isSingleton()返回false：每次调用都创建新的对象实例</li>
     * </ul>
     * 
     * <p>实现注意事项：</p>
     * <ul>
     *   <li>方法应该是线程安全的，因为可能被多个线程同时调用</li>
     *   <li>不应该返回null，如果无法创建对象应该抛出异常</li>
     *   <li>应该处理所有可能的异常情况</li>
     * </ul>
     * 
     * @return 由FactoryBean创建的对象实例，不能为null
     * @throws BeanException 如果对象创建失败
     */
    T getObject() throws BeanException;

    /**
     * 判断FactoryBean创建的对象是否为单例
     * 
     * <p>此方法决定了getObject()方法的调用策略：</p>
     * <ul>
     *   <li>返回true：单例模式，getObject()只在第一次调用时执行，结果会被缓存</li>
     *   <li>返回false：原型模式，每次获取Bean时都会调用getObject()创建新实例</li>
     * </ul>
     * 
     * <p>选择建议：</p>
     * <ul>
     *   <li>无状态对象或可共享对象：返回true（单例）</li>
     *   <li>有状态对象或需要独立实例：返回false（原型）</li>
     * </ul>
     * 
     * @return true表示单例，false表示原型
     */
    boolean isSingleton();
}
