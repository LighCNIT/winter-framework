package org.winterframework.context.support;

import org.winterframework.beans.BeanException;
import org.winterframework.beans.factory.ConfigurableListableBeanFactory;
import org.winterframework.beans.factory.support.DefaultListableBeanFactory;

import java.util.Map;

/**
 * 可刷新的应用上下文抽象类
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/18
 * @description 提供可刷新应用上下文的基础实现，支持BeanFactory的重新创建
 * 
 * <p>AbstractRefreshableApplicationContext是AbstractApplicationContext的子类，
 * 它提供了可刷新应用上下文的基础实现，支持BeanFactory的重新创建和BeanDefinition的重新加载。</p>
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>实现refreshBeanFactory()方法，支持BeanFactory的重新创建</li>
 *   <li>提供BeanFactory的创建和管理</li>
 *   <li>支持BeanDefinition的重新加载</li>
 *   <li>提供BeanFactory的获取方法</li>
 * </ul>
 * 
 * <p>设计特点：</p>
 * <ul>
 *   <li>每次刷新都会创建新的BeanFactory实例</li>
 *   <li>支持热重载和配置更新</li>
 *   <li>提供灵活的BeanDefinition加载机制</li>
 * </ul>
 * 
 * @see AbstractApplicationContext
 * @see DefaultListableBeanFactory
 * @see ConfigurableListableBeanFactory
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext{

    /**
     * BeanFactory实例
     * 
     * <p>存储当前使用的BeanFactory实例，每次刷新时会重新创建</p>
     */
    private DefaultListableBeanFactory beanFactory;

    /**
     * 刷新BeanFactory - 具体实现
     * 
     * <p>实现BeanFactory的刷新逻辑，包括：</p>
     * <ol>
     *   <li>创建新的BeanFactory实例</li>
     *   <li>加载BeanDefinition到新的BeanFactory中</li>
     *   <li>将新的BeanFactory设置为当前使用的BeanFactory</li>
     * </ol>
     * 
     * <p>每次调用此方法都会完全重新创建BeanFactory，确保配置的更新能够生效</p>
     * 
     * @throws BeanException 如果BeanFactory创建或BeanDefinition加载失败
     */
    protected final void refreshBeanFactory() throws BeanException {
        DefaultListableBeanFactory beanFactory = createBeanFactory();
        loadBeanDefinitions(beanFactory);
        this.beanFactory = beanFactory;
    }

    /**
     * 创建BeanFactory实例
     * 
     * <p>创建新的DefaultListableBeanFactory实例。
     * 子类可以重写此方法来自定义BeanFactory的创建逻辑。</p>
     * 
     * <p>默认实现：创建标准的DefaultListableBeanFactory实例</p>
     * 
     * @return 新创建的BeanFactory实例
     * @see DefaultListableBeanFactory
     */
    protected DefaultListableBeanFactory createBeanFactory(){
        return new DefaultListableBeanFactory();
    }

    /**
     * 加载BeanDefinition - 抽象方法
     * 
     * <p>子类需要实现此方法，负责将BeanDefinition加载到指定的BeanFactory中。
     * 这是BeanFactory刷新的核心步骤。</p>
     * 
     * <p>实现要求：</p>
     * <ul>
     *   <li>从配置源（XML、注解等）加载BeanDefinition</li>
     *   <li>将BeanDefinition注册到指定的BeanFactory中</li>
     *   <li>确保所有必要的BeanDefinition都已加载</li>
     * </ul>
     * 
     * @param beanFactory 要加载BeanDefinition的BeanFactory
     * @throws BeanException 如果BeanDefinition加载失败
     */
    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory)throws BeanException;

    /**
     * 获取BeanFactory实例
     * 
     * <p>返回当前使用的BeanFactory实例。如果BeanFactory尚未创建，
     * 此方法可能返回null。</p>
     * 
     * @return 当前使用的BeanFactory实例
     */
    @Override
    public DefaultListableBeanFactory getBeanFactory() {
        return beanFactory;
    }
}