package org.winterframework.context.support;

import org.winterframework.beans.BeanException;
import org.winterframework.beans.ConfigurableListableBeanFactory;
import org.winterframework.beans.factory.config.BeanFactoryPostProcessor;
import org.winterframework.beans.factory.config.BeanPostProcessor;
import org.winterframework.context.ConfigurableApplicationContext;
import org.winterframework.core.io.DefaultResourceLoader;

import java.util.Map;

/**
 * 可配置应用上下文的抽象基类
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/18
 * @description 提供应用上下文的基础实现
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext  {

    @Override
    public void refresh() throws BeanException {
        //创建BeanFactory，并加载BeanDefinition
        refreshBeanFactory();
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        //在bean实例化之前，执行BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);

        //BeanPostProcessor需要提前与其他bean实例化之前注册
        registerBeanPostProcessors(beanFactory);

        //提前实例化单例bean,其实就是从缓存里面拿
        beanFactory.preInstantiateSingletons();
    }

    /**
     * 在bean实例化之前，执行postProcessBeanFactory
     * @param beanFactory
     */
    protected void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory){
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()){
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    /**
     * 注册 beanPostProcessor
     * @param beanFactory
     */
    protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory){
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()){
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeanException {
        return getBeanFactory().getBean(name, requiredType);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeanException {
        return getBeanFactory().getBeansOfType(type);
    }

    public Object getBean(String name) throws BeanException {
        return getBeanFactory().getBean(name);
    }

    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    protected abstract void refreshBeanFactory() throws BeanException;

    public abstract ConfigurableListableBeanFactory getBeanFactory();

}
