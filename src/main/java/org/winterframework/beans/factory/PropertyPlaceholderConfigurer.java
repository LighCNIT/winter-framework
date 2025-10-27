package org.winterframework.beans.factory;

import org.winterframework.beans.BeanException;
import org.winterframework.beans.PropertyValue;
import org.winterframework.beans.PropertyValues;
import org.winterframework.beans.factory.ConfigurableListableBeanFactory;
import org.winterframework.beans.factory.config.BeanDefinition;
import org.winterframework.beans.factory.config.BeanFactoryPostProcessor;
import org.winterframework.beans.factory.config.BeanPostProcessor;
import org.winterframework.beans.factory.config.ConfigurableBeanFactory;
import org.winterframework.core.io.DefaultResourceLoader;
import org.winterframework.core.io.Resource;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/27
 * @description TODO
 */
public class PropertyPlaceholderConfigurer implements BeanFactoryPostProcessor {

    public static final String PLACEHOLDER_PREFIX = "${";

    public static final String PLACEHOLDER_SUFFIX = "}";

    private String location;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeanException {
        // 加载属性配置文件
        Properties properties = loadProperties();
        // 属性值替换占位符
        processProperties(beanFactory,properties);
    }

    private Properties loadProperties(){
        try {
            DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
            Resource resource = resourceLoader.getResource(location);
            Properties properties = new Properties();
            properties.load(resource.getInputStream());
            return properties;
        }catch (IOException e){
            throw new BeanException("Could not load properties", e);
        }
    }

    private void processProperties(ConfigurableListableBeanFactory beanFactory,Properties properties) throws BeanException{
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        for (String beanName : beanDefinitionNames){
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
            resolvePropertyValues(beanDefinition,properties);
        }
    }

    private void resolvePropertyValues(BeanDefinition beanDefinition,Properties properties){
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        for (PropertyValue propertyValue : propertyValues.getPropertyValues()){
            Object value = propertyValue.getValue();
            if (value instanceof  String){
                String strValue = (String) value;
                StringBuffer stringBuffer = new StringBuffer(strValue);
                int startIndex = strValue.indexOf(PLACEHOLDER_PREFIX);
                int endIndex = strValue.indexOf(PLACEHOLDER_SUFFIX);
                if (startIndex != -1 && endIndex!= -1 && startIndex < endIndex){
                    String proKey = strValue.substring(startIndex+2,endIndex);
                    String proValue = properties.getProperty(proKey);
                    stringBuffer.replace(startIndex,endIndex+1,proValue);
                    propertyValues.addPropertyValue(new PropertyValue(propertyValue.getName(),stringBuffer.toString()));
                }
            }
        }
    }

    public void setLocation(String location) {
        this.location = location;
    }
}