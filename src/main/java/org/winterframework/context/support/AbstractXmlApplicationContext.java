package org.winterframework.context.support;

import org.winterframework.beans.BeanException;
import org.winterframework.beans.factory.support.DefaultListableBeanFactory;
import org.winterframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/18
 * @description TODO
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext{

    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeanException {
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory,this);
        String[] configLocations = getConfigLocations();
        if (configLocations != null){
            beanDefinitionReader.loadBeanDefinitions(configLocations);
        }
    }

    protected abstract String[] getConfigLocations();
}