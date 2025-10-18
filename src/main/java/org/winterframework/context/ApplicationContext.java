package org.winterframework.context;

import org.winterframework.beans.factory.HierarchicalBeanFactory;
import org.winterframework.beans.factory.ListableBeanFactory;
import org.winterframework.core.io.ResourceLoader;

/**
 * @author Ligh
 * @date 2025/10/18
 * @description 应用上下文
 */
public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader {
}
