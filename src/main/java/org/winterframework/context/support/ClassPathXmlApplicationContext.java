package org.winterframework.context.support;

import org.winterframework.beans.BeanException;

/**
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/18
 * @description xml文件应用上下文
 */
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext{

    private String[]  configLocations;
    public ClassPathXmlApplicationContext(String configLocation) throws BeanException{
        this(new String[]{configLocation});
    }

    /**
     * 从xml文件加载beanDefinition，并自动刷新上下文
     * @param configLocations
     * @throws BeanException
     */
    public ClassPathXmlApplicationContext(String[] configLocations) throws BeanException {
        this.configLocations = configLocations;
        refresh();
    }

    protected String[] getConfigLocations() {
        return this.configLocations;
    }
}