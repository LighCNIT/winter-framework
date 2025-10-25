package org.winterframework.beans.factory.config;

import org.winterframework.beans.BeanException;

public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    Object postProcessBeforeInstantiation(Class<?> beanClass, String Name) throws BeanException;
}
