package org.winterframework.context.support;

import org.winterframework.beans.BeanException;
import org.winterframework.beans.ConfigurableListableBeanFactory;
import org.winterframework.beans.factory.config.BeanFactoryPostProcessor;
import org.winterframework.beans.factory.config.BeanPostProcessor;
import org.winterframework.context.ConfigurableApplicationContext;
import org.winterframework.core.io.DefaultResourceLoader;

import java.util.Map;

/**
 * 应用上下文的抽象基类
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/18
 * @description 提供应用上下文的基础实现，实现refresh()方法的核心流程
 * 
 * <p>AbstractApplicationContext是应用上下文的核心抽象类，它实现了refresh()方法
 * 的标准流程，包括BeanFactory的创建、BeanFactoryPostProcessor的执行、
 * BeanPostProcessor的注册和单例Bean的预实例化。</p>
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>实现refresh()方法的标准流程</li>
 *   <li>执行BeanFactoryPostProcessor</li>
 *   <li>注册BeanPostProcessor</li>
 *   <li>预实例化单例Bean</li>
 *   <li>提供Bean获取的委托方法</li>
 * </ul>
 * 
 * <p>设计模式：模板方法模式</p>
 * <ul>
 *   <li>定义refresh()方法的算法骨架</li>
 *   <li>子类实现refreshBeanFactory()和getBeanFactory()方法</li>
 * </ul>
 * 
 * @see ConfigurableApplicationContext
 * @see DefaultResourceLoader
 * @see BeanFactoryPostProcessor
 * @see BeanPostProcessor
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext  {

    /**
     * 刷新应用上下文 - 核心方法
     * 
     * <p>实现应用上下文刷新的标准流程，按照以下顺序执行：</p>
     * <ol>
     *   <li>创建BeanFactory并加载BeanDefinition</li>
     *   <li>执行BeanFactoryPostProcessor（在Bean实例化前）</li>
     *   <li>注册BeanPostProcessor（在Bean实例化前）</li>
     *   <li>预实例化单例Bean</li>
     * </ol>
     * 
     * <p>这是Spring框架中最重要的方法之一，负责整个IoC容器的初始化过程。</p>
     * 
     * @throws BeanException 如果刷新过程中发生错误
     */
    @Override
    public void refresh() throws BeanException {
        // 1. 创建BeanFactory，并加载BeanDefinition
        refreshBeanFactory();
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // 2. 在bean实例化之前，执行BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);

        // 3. BeanPostProcessor需要提前与其他bean实例化之前注册
        registerBeanPostProcessors(beanFactory);

        // 4. 提前实例化单例bean，其实就是从缓存里面拿
        beanFactory.preInstantiateSingletons();
    }

    /**
     * 在Bean实例化之前，执行BeanFactoryPostProcessor
     * 
     * <p>遍历BeanFactory中所有的BeanFactoryPostProcessor类型的Bean，
     * 依次执行它们的postProcessBeanFactory方法。</p>
     * 
     * <p>执行时机：在BeanDefinition加载完成后，Bean实例化之前</p>
     * 
     * <p>使用场景：</p>
     * <ul>
     *   <li>修改BeanDefinition的属性值</li>
     *   <li>添加新的Bean定义</li>
     *   <li>实现配置的动态修改</li>
     * </ul>
     * 
     * @param beanFactory 可配置的BeanFactory
     * @see BeanFactoryPostProcessor
     * @see BeanFactoryPostProcessor#postProcessBeanFactory(ConfigurableListableBeanFactory)
     */
    protected void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory){
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()){
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    /**
     * 注册BeanPostProcessor
     * 
     * <p>遍历BeanFactory中所有的BeanPostProcessor类型的Bean，
     * 将它们注册到BeanFactory中，以便在Bean实例化过程中使用。</p>
     * 
     * <p>执行时机：在BeanFactoryPostProcessor执行完成后，Bean实例化之前</p>
     * 
     * <p>使用场景：</p>
     * <ul>
     *   <li>修改Bean的属性值</li>
     *   <li>为Bean添加代理（AOP的基础）</li>
     *   <li>实现Bean的增强功能</li>
     *   <li>添加Bean的生命周期回调</li>
     * </ul>
     * 
     * @param beanFactory 可配置的BeanFactory
     * @see BeanPostProcessor
     * @see ConfigurableListableBeanFactory#addBeanPostProcessor(BeanPostProcessor)
     */
    protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory){
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()){
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    /**
     * 根据名称和类型获取Bean
     * 
     * <p>委托给BeanFactory执行具体的Bean获取逻辑</p>
     * 
     * @param name Bean名称
     * @param requiredType 期望的Bean类型
     * @param <T> Bean类型
     * @return Bean实例
     * @throws BeanException 如果Bean获取失败
     */
    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeanException {
        return getBeanFactory().getBean(name, requiredType);
    }

    /**
     * 根据类型获取所有Bean
     * 
     * <p>委托给BeanFactory执行具体的Bean查询逻辑</p>
     * 
     * @param type Bean类型
     * @param <T> Bean类型
     * @return Bean名称到Bean实例的映射
     * @throws BeanException 如果查询失败
     */
    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeanException {
        return getBeanFactory().getBeansOfType(type);
    }

    /**
     * 根据名称获取Bean
     * 
     * <p>委托给BeanFactory执行具体的Bean获取逻辑</p>
     * 
     * @param name Bean名称
     * @return Bean实例
     * @throws BeanException 如果Bean获取失败
     */
    public Object getBean(String name) throws BeanException {
        return getBeanFactory().getBean(name);
    }

    /**
     * 获取所有Bean定义名称
     * 
     * <p>委托给BeanFactory执行具体的Bean定义查询逻辑</p>
     * 
     * @return Bean定义名称数组
     */
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    /**
     * 刷新BeanFactory - 抽象方法
     * 
     * <p>子类需要实现此方法，负责创建BeanFactory并加载BeanDefinition。
     * 这是refresh()方法的第一步，也是最重要的步骤。</p>
     * 
     * <p>实现要求：</p>
     * <ul>
     *   <li>创建新的BeanFactory实例</li>
     *   <li>加载BeanDefinition到BeanFactory中</li>
     *   <li>确保BeanFactory已准备就绪</li>
     * </ul>
     * 
     * @throws BeanException 如果BeanFactory创建或BeanDefinition加载失败
     */
    protected abstract void refreshBeanFactory() throws BeanException;

    /**
     * 获取BeanFactory - 抽象方法
     * 
     * <p>子类需要实现此方法，返回当前使用的BeanFactory实例。
     * 此方法在refresh()过程中被多次调用。</p>
     * 
     * @return 可配置的BeanFactory实例
     */
    public abstract ConfigurableListableBeanFactory getBeanFactory();

}
