package org.winterframework.beans.factory.support;

import org.winterframework.beans.BeanException;
import org.winterframework.core.io.Resource;
import org.winterframework.core.io.ResourceLoader;

/**
 * Bean定义读取器接口
 * 
 * @author Ligh
 * @date 2025/10/17
 * @description 定义从各种配置源（XML、注解、Java配置等）读取Bean定义信息的规范
 *              这是配置解析阶段的核心接口，负责将外部配置转换为内存中的BeanDefinition对象
 *              支持多种配置格式，为IoC容器提供灵活的配置方式
 */
public interface BeanDefinitionReader {

    /**
     * 获取Bean定义注册表
     * 
     * @return Bean定义注册表，用于注册解析出的Bean定义
     */
    BeanDefinitionRegistry getRegistry();

    /**
     * 获取资源加载器
     * 
     * @return 资源加载器，用于加载配置文件
     */
    ResourceLoader getResourceLoader();

    /**
     * 从Resource对象加载Bean定义
     * 
     * @param resource 资源对象，包含配置内容
     * @throws BeanException 当解析失败时抛出
     */
    void loadBeanDefinitions(Resource resource) throws BeanException;

    /**
     * 从资源位置字符串加载Bean定义
     * 
     * @param location 资源位置，支持classpath:、file:、http:等前缀
     * @throws BeanException 当资源加载或解析失败时抛出
     */
    void loadBeanDefinitions(String location) throws BeanException;

    /**
     * 批量加载Bean定义
     * 
     * @param locations 资源位置数组
     * @throws BeanException 当任何一个资源加载失败时抛出
     */
    void loadBeanDefinitions(String[] locations) throws BeanException;

}
