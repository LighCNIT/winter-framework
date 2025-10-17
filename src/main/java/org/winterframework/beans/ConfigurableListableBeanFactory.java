package org.winterframework.beans;

import org.winterframework.beans.factory.ListableBeanFactory;
import org.winterframework.beans.factory.config.AutowireCapableBeanFactory;
import org.winterframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * @author Ligh
 * @date 2025/10/17
 * @description TODO
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {
}
