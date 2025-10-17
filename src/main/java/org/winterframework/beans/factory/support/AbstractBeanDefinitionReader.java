package org.winterframework.beans.factory.support;

import org.winterframework.beans.BeanException;
import org.winterframework.core.io.DefaultResourceLoader;
import org.winterframework.core.io.ResourceLoader;

/**
 * Bean定义读取器抽象基类
 * 
 * @author Ligh
 * @date 2025/10/17
 * @description 为Bean定义读取器提供通用的实现，包括资源加载器的管理和批量加载功能
 *              这是模板方法模式的体现，定义了Bean定义读取的基本流程
 *              子类只需要实现具体的解析逻辑，如XML解析、注解解析等
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader{

    /** Bean定义注册表，用于注册解析出的Bean定义 */
    private final BeanDefinitionRegistry registry;

    /** 资源加载器，用于加载配置文件 */
    private ResourceLoader resourceLoader;

    /**
     * 构造方法 - 使用默认资源加载器
     * 
     * @param registry Bean定义注册表
     */
    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this(registry, new DefaultResourceLoader());
    }

    /**
     * 构造方法 - 指定资源加载器
     * 
     * @param registry Bean定义注册表
     * @param resourceLoader 资源加载器
     */
    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        this.registry = registry;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    /**
     * 批量加载Bean定义
     * 
     * @param locations 资源位置数组
     * @throws BeanException 当任何一个资源加载失败时抛出
     */
    @Override
    public void loadBeanDefinitions(String[] locations) throws BeanException {
        for (String location : locations) {
            loadBeanDefinitions(location);
        }
    }

    /**
     * 设置资源加载器
     * 
     * @param resourceLoader 新的资源加载器
     */
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }
}
