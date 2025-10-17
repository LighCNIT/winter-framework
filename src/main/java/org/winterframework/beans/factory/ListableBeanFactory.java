package org.winterframework.beans.factory;

import org.winterframework.beans.BeanException;

import java.util.Map;

/**
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/17
 * @description TODO
 */
public interface ListableBeanFactory extends BeanFactory {

    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeanException;

    String[] getBeanDefinitionNames();
}