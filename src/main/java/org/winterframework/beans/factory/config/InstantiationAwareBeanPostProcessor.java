package org.winterframework.beans.factory.config;

import org.winterframework.beans.BeanException;
import org.winterframework.beans.PropertyValues;

public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    /**
     * 在bean实例化之前执行
     * @param beanClass
     * @param Name
     * @return
     * @throws BeanException
     */
    Object postProcessBeforeInstantiation(Class<?> beanClass, String Name) throws BeanException;

    /**
     * 在bean实例化之后，设置属性之前执行
     * @param bean
     * @param beanName
     * @return
     * @throws BeanException
     */
    boolean postProcessAfterInstantiation(Object bean,String beanName) throws BeanException;

    /**
     * 在bean实例化之后，设置属性之前执行
     * @param propertyValues
     * @param bean
     * @param beanName
     * @return
     * @throws BeanException
     */
    PropertyValues postProcessPropertyValues(PropertyValues propertyValues,Object bean,String beanName) throws BeanException;
}
