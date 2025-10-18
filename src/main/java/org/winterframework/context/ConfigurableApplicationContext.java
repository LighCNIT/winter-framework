package org.winterframework.context;

/**
 * 可配置的应用上下文接口
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/18
 * @description 提供应用上下文的配置和管理能力
 * 
 * <p>ConfigurableApplicationContext是ApplicationContext的扩展接口，
 * 提供了应用上下文的配置和管理能力。</p>
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>刷新应用上下文</li>
 *   <li>关闭应用上下文</li>
 *   <li>获取BeanFactory</li>
 *   <li>注册关闭钩子</li>
 * </ul>
 * 
 * <p>这是应用上下文的高级接口，提供了完整的生命周期管理能力</p>
 * 
 * @see ApplicationContext
 * @see org.winterframework.context.support.AbstractApplicationContext
 */
public interface ConfigurableApplicationContext extends ApplicationContext {

    /**
     * 刷新应用上下文
     * 
     * <p>重新加载应用上下文，包括以下步骤：</p>
     * <ol>
     *   <li>关闭已存在的BeanFactory（如果存在）</li>
     *   <li>创建新的BeanFactory</li>
     *   <li>加载Bean定义</li>
     *   <li>执行BeanFactoryPostProcessor</li>
     *   <li>注册BeanPostProcessor</li>
     *   <li>预实例化单例Bean</li>
     * </ol>
     * 
     * <p>执行时机：</p>
     * <ul>
     *   <li>应用启动时</li>
     *   <li>手动调用refresh()方法时</li>
     *   <li>配置热重载时</li>
     * </ul>
     * 
     * <p>注意事项：</p>
     * <ul>
     *   <li>刷新过程中会重新创建所有Bean</li>
     *   <li>如果刷新失败，应用上下文可能处于不一致状态</li>
     *   <li>刷新是线程不安全的，需要外部同步</li>
     * </ul>
     * 
     * @throws Exception 如果刷新过程中发生错误
     */
    void refresh() throws Exception;

}