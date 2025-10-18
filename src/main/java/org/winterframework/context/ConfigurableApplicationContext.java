package org.winterframework.context;

/**
 * 可配置的应用上下文接口
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/18
 * @description 提供应用上下文的配置能力
 */
public interface ConfigurableApplicationContext extends ApplicationContext {

    /**
     * 刷新应用上下文
     * 
     * <p>重新加载应用上下文，包括：</p>
     * <ol>
     *   <li>关闭已存在的BeanFactory</li>
     *   <li>创建新的BeanFactory</li>
     *   <li>加载Bean定义</li>
     *   <li>执行BeanFactoryPostProcessor</li>
     *   <li>注册BeanPostProcessor</li>
     *   <li>预实例化单例Bean</li>
     * </ol>
     * 
     * @throws Exception 如果刷新过程中发生错误
     */
    void refresh() throws Exception;

}