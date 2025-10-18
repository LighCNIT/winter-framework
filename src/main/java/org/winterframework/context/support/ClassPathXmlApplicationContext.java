package org.winterframework.context.support;

import org.winterframework.beans.BeanException;

/**
 * ClassPath XML应用上下文实现类
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/18
 * @description 从classpath加载XML配置文件的应用上下文实现
 * 
 * <p>ClassPathXmlApplicationContext是AbstractXmlApplicationContext的具体实现，
 * 专门用于从classpath中加载XML配置文件。</p>
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>从classpath加载XML配置文件</li>
 *   <li>支持单个或多个XML配置文件</li>
 *   <li>自动刷新应用上下文</li>
 *   <li>提供便捷的构造方法</li>
 * </ul>
 * 
 * <p>使用场景：</p>
 * <ul>
 *   <li>基于XML配置的Spring应用</li>
 *   <li>需要从classpath加载配置的场景</li>
 *   <li>简单的应用上下文配置</li>
 * </ul>
 * 
 * <p>使用示例：</p>
 * <pre>{@code
 * // 单个配置文件
 * ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
 * 
 * // 多个配置文件
 * ApplicationContext context = new ClassPathXmlApplicationContext(
 *     new String[]{"spring.xml", "spring-dao.xml", "spring-service.xml"});
 * 
 * // 获取Bean
 * UserService userService = context.getBean("userService", UserService.class);
 * }</pre>
 * 
 * @see AbstractXmlApplicationContext
 * @see ApplicationContext
 * @see ConfigurableApplicationContext
 */
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext{

    /**
     * 配置文件位置数组
     * 
     * <p>存储XML配置文件的位置，支持classpath路径、文件系统路径和URL</p>
     */
    private String[] configLocations;

    /**
     * 构造方法 - 单个配置文件
     * 
     * <p>创建ClassPathXmlApplicationContext实例，从指定的classpath位置加载XML配置文件，
     * 并自动刷新应用上下文。</p>
     * 
     * <p>使用示例：</p>
     * <pre>{@code
     * ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
     * }</pre>
     * 
     * @param configLocation XML配置文件位置（classpath路径）
     * @throws BeanException 如果配置文件加载失败
     */
    public ClassPathXmlApplicationContext(String configLocation) throws BeanException{
        this(new String[]{configLocation});
    }

    /**
     * 构造方法 - 多个配置文件
     * 
     * <p>创建ClassPathXmlApplicationContext实例，从指定的classpath位置数组加载多个XML配置文件，
     * 并自动刷新应用上下文。</p>
     * 
     * <p>使用示例：</p>
     * <pre>{@code
     * ApplicationContext context = new ClassPathXmlApplicationContext(
     *     new String[]{"spring.xml", "spring-dao.xml", "spring-service.xml"});
     * }</pre>
     * 
     * <p>配置文件加载顺序：</p>
     * <ol>
     *   <li>按照数组顺序依次加载配置文件</li>
     *   <li>后加载的配置会覆盖先加载的配置（相同Bean名称）</li>
     *   <li>所有配置文件加载完成后才进行Bean实例化</li>
     * </ol>
     * 
     * @param configLocations XML配置文件位置数组（classpath路径）
     * @throws BeanException 如果配置文件加载失败
     */
    public ClassPathXmlApplicationContext(String[] configLocations) throws BeanException {
        this.configLocations = configLocations;
        refresh();
    }

    /**
     * 获取配置文件位置数组
     * 
     * <p>返回当前配置的XML配置文件位置数组。
     * 这些位置会在loadBeanDefinitions()方法中被使用。</p>
     * 
     * <p>路径格式：</p>
     * <ul>
     *   <li>spring.xml - 相对classpath路径</li>
     *   <li>config/spring.xml - 相对classpath路径</li>
     *   <li>classpath:spring.xml - 显式classpath前缀</li>
     * </ul>
     * 
     * @return XML配置文件位置数组
     * @see AbstractXmlApplicationContext#loadBeanDefinitions(DefaultListableBeanFactory)
     */
    protected String[] getConfigLocations() {
        return this.configLocations;
    }
}