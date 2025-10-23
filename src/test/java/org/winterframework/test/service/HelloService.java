package org.winterframework.test.service;

import org.winterframework.beans.BeanException;
import org.winterframework.beans.factory.BeanFactory;
import org.winterframework.beans.factory.BeanFactoryAware;
import org.winterframework.context.ApplicationContext;
import org.winterframework.context.ApplicationContextAware;

/**
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/12
 * @description TODO
 */
public class HelloService implements ApplicationContextAware, BeanFactoryAware {

    private ApplicationContext applicationContext;

    private BeanFactory beanFactory;

    public String sayHello(){
        System.out.println("hello");
        return "hello";
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeanException {
            this.beanFactory = beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeanException {
        this.applicationContext = applicationContext;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }
}