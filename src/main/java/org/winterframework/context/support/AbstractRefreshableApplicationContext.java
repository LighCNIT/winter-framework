package org.winterframework.context.support;

import org.winterframework.beans.BeanException;
import org.winterframework.beans.ConfigurableListableBeanFactory;
import org.winterframework.beans.factory.support.DefaultListableBeanFactory;

import java.util.Map;

/**
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/18
 * @description TODO
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext{

    private DefaultListableBeanFactory beanFactory;

    protected final void refreshBeanFactory() throws BeanException {
        DefaultListableBeanFactory beanFactory = createBeanFactory();
        loadBeanDefinitions(beanFactory);
        this.beanFactory = beanFactory;
    }

    /**
     * 创建bean工厂
     * @return
     */
    protected DefaultListableBeanFactory createBeanFactory(){
        return new DefaultListableBeanFactory();
    }

    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory)throws BeanException;

    @Override
    public DefaultListableBeanFactory getBeanFactory() {
        return beanFactory;
    }
}