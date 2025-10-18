package org.winterframework.beans.factory.support;

import org.winterframework.beans.BeanException;
import org.winterframework.beans.ConfigurableListableBeanFactory;
import org.winterframework.beans.factory.config.BeanDefinition;
import org.winterframework.beans.factory.config.ConfigurableBeanFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 默认可列表的Bean工厂 - 完整实现类
 *
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/12
 * @description 继承AbstractAutowireCapableBeanFactory，实现BeanDefinitionRegistry
 * 这是一个可以实际使用的完整Bean工厂实现
 * <p>
 * 职责：
 * 1. 实现BeanDefinition的注册和存储
 * 2. 实现BeanDefinition的查询
 * 3. 整合Bean的创建、缓存、定义管理功能
 * <p>
 * 类似于Spring中的DefaultListableBeanFactory
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory
        implements ConfigurableListableBeanFactory, BeanDefinitionRegistry {

    /**
     * 存储BeanDefinition的Map容器
     * key: beanName (Bean的唯一标识)
     * value: BeanDefinition (Bean的定义信息)
     * <p>
     * 作用：保存所有注册的Bean定义，用于后续创建Bean实例
     */
    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    /**
     * 注册Bean定义
     * 将Bean的元数据信息存储到容器中
     *
     * @param beanName       Bean的唯一标识名称
     * @param beanDefinition Bean的定义信息
     */
    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    /**
     * 获取Bean定义信息
     * 实现父类的抽象方法，从beanDefinitionMap中查询
     *
     * @param beanName Bean名称
     * @return Bean定义信息
     * @throws BeanException 如果Bean定义不存在
     */
    @Override
    public BeanDefinition getBeanDefinition(String beanName) throws BeanException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            throw new BeanException("No bean named '" + beanName + "' is defined");
        }
        return beanDefinition;
    }

    @Override
    public boolean containBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeanException {
        Map<String, T> result = new HashMap<>();
        beanDefinitionMap.forEach((beanName, beanDefinition) -> {
            Class beanClass = beanDefinition.getBeanClass();
            if (type.isAssignableFrom(beanClass)) {
                T bean = (T) getBean(beanName);
                result.put(beanName, bean);
            }
        });
        return result;
    }

    @Override
    public String[] getBeanDefinitionNames() {
        Set<String> beanNames = beanDefinitionMap.keySet();
        return beanNames.toArray(new String[beanNames.size()]);
    }

    /**
     * 提前实例化所有单例Bean
     * 
     * <p>遍历容器中所有的BeanDefinition，提前创建所有单例Bean的实例。
     * 这通常在应用启动时调用，可以提前发现Bean创建过程中的问题。</p>
     * 
     * <p>执行流程：</p>
     * <ol>
     *   <li>遍历beanDefinitionMap中的所有Bean名称</li>
     *   <li>对每个Bean名称调用getBean()方法</li>
     *   <li>触发Bean的完整创建和初始化过程</li>
     *   <li>包括BeanPostProcessor的执行</li>
     * </ol>
     * 
     * <p>注意事项：</p>
     * <ul>
     *   <li>只实例化单例Bean，原型Bean不会提前创建</li>
     *   <li>会触发BeanPostProcessor的执行</li>
     *   <li>如果Bean创建失败，会抛出异常</li>
     *   <li>重复调用是安全的，已创建的Bean会从缓存返回</li>
     * </ul>
     * 
     * <p>使用场景：</p>
     * <ul>
     *   <li>应用启动时提前创建所有Bean</li>
     *   <li>提前发现Bean创建过程中的问题</li>
     *   <li>确保所有Bean都已正确初始化</li>
     * </ul>
     * 
     * @throws BeanException 如果Bean实例化过程中发生错误
     * @see #getBean(String)
     * @see BeanPostProcessor
     */
    @Override
    public void preInstantiateSingletons() throws BeanException {
        beanDefinitionMap.keySet().forEach(this::getBean);
    }
}
