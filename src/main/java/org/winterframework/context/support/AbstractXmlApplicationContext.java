package org.winterframework.context.support;

import org.winterframework.beans.BeanException;
import org.winterframework.beans.factory.support.DefaultListableBeanFactory;
import org.winterframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * XML应用上下文抽象类
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/18
 * @description 提供基于XML配置的应用上下文基础实现
 * 
 * <p>AbstractXmlApplicationContext是AbstractRefreshableApplicationContext的子类，
 * 它专门用于处理基于XML配置的应用上下文。</p>
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>实现loadBeanDefinitions()方法，使用XmlBeanDefinitionReader加载XML配置</li>
 *   <li>提供XML配置文件的加载机制</li>
 *   <li>支持多个XML配置文件的加载</li>
 *   <li>集成ResourceLoader进行资源加载</li>
 * </ul>
 * 
 * <p>设计特点：</p>
 * <ul>
 *   <li>使用XmlBeanDefinitionReader进行XML解析</li>
 *   <li>支持classpath、文件系统、URL等多种资源类型</li>
 *   <li>提供灵活的配置文件位置配置</li>
 * </ul>
 * 
 * @see AbstractRefreshableApplicationContext
 * @see XmlBeanDefinitionReader
 * @see ClassPathXmlApplicationContext
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext{

    /**
     * 加载BeanDefinition - XML实现
     * 
     * <p>使用XmlBeanDefinitionReader从XML配置文件中加载BeanDefinition。
     * 这是XML应用上下文的核心实现。</p>
     * 
     * <p>实现流程：</p>
     * <ol>
     *   <li>创建XmlBeanDefinitionReader实例</li>
     *   <li>获取配置文件位置数组</li>
     *   <li>使用XmlBeanDefinitionReader加载所有配置文件</li>
     * </ol>
     * 
     * <p>技术特点：</p>
     * <ul>
     *   <li>使用XmlBeanDefinitionReader进行XML解析</li>
     *   <li>支持多个XML配置文件</li>
     *   <li>集成ResourceLoader进行资源加载</li>
     *   <li>支持classpath、文件系统、URL等资源类型</li>
     * </ul>
     * 
     * @param beanFactory 要加载BeanDefinition的BeanFactory
     * @throws BeanException 如果XML解析或BeanDefinition加载失败
     * @see XmlBeanDefinitionReader
     * @see #getConfigLocations()
     */
    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeanException {
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory,this);
        String[] configLocations = getConfigLocations();
        if (configLocations != null){
            beanDefinitionReader.loadBeanDefinitions(configLocations);
        }
    }

    /**
     * 获取配置文件位置 - 抽象方法
     * 
     * <p>子类需要实现此方法，返回XML配置文件的位置数组。
     * 这些位置可以是classpath路径、文件系统路径或URL。</p>
     * 
     * <p>支持的路径格式：</p>
     * <ul>
     *   <li>classpath:config/spring.xml - classpath资源</li>
     *   <li>file:config/spring.xml - 文件系统资源</li>
     *   <li>http://example.com/config/spring.xml - URL资源</li>
     *   <li>config/spring.xml - 相对路径（默认按classpath处理）</li>
     * </ul>
     * 
     * <p>实现要求：</p>
     * <ul>
     *   <li>返回配置文件位置数组</li>
     *   <li>支持多个配置文件</li>
     *   <li>路径格式要符合ResourceLoader的要求</li>
     * </ul>
     * 
     * @return XML配置文件位置数组，如果为null则不加载任何配置
     */
    protected abstract String[] getConfigLocations();
}