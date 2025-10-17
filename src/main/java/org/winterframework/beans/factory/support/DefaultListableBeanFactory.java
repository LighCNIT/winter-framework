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
        return null;
    }

    @Override
    public String[] getBeanDefinitionNames() {
        Set<String> beanNames = beanDefinitionMap.keySet();
        return beanNames.toArray(new String[beanNames.size()]);
    }
}
