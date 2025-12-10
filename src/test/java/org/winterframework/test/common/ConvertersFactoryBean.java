package org.winterframework.test.common;

import org.winterframework.beans.factory.FactoryBean;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Ligh
 * 2025/12/10 21:23
 **/
public class ConvertersFactoryBean implements FactoryBean<Set<?>> {

    @Override
    public Set<?> getObject() {
        HashSet<Object> converters = new HashSet<>();
        StringToLocalDateConverter stringToLocalDateConverter = new StringToLocalDateConverter("yyyy-MM-dd");
        converters.add(stringToLocalDateConverter);
        return converters;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
