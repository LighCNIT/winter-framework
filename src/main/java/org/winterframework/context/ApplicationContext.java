package org.winterframework.context;

import org.winterframework.beans.factory.HierarchicalBeanFactory;
import org.winterframework.beans.factory.ListableBeanFactory;
import org.winterframework.core.io.ResourceLoader;

/**
 * 应用上下文接口
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/18
 * @description 应用上下文的核心接口，提供Bean管理和资源访问能力
 * 
 * <p>ApplicationContext是Spring框架中最重要的接口之一，它整合了以下功能：</p>
 * 
 * <ul>
 *   <li>ListableBeanFactory：提供Bean的列表查询功能</li>
 *   <li>HierarchicalBeanFactory：提供Bean工厂的层次结构支持</li>
 *   <li>ResourceLoader：提供资源加载能力</li>
 * </ul>
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>Bean的获取和管理</li>
 *   <li>Bean的列表查询和类型查询</li>
 *   <li>Bean工厂的层次结构管理</li>
 *   <li>资源的加载和访问</li>
 * </ul>
 * 
 * <p>这是应用上下文的基础接口，为更高级的配置接口提供基础</p>
 * 
 * @see ListableBeanFactory
 * @see HierarchicalBeanFactory
 * @see ResourceLoader
 * @see ConfigurableApplicationContext
 */
public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader {
}
