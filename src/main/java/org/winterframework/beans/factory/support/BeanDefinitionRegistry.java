package org.winterframework.beans.factory.support;

import org.winterframework.beans.factory.config.BeanDefinition;

/**
 * Bean定义注册表接口
 * 
 * @author Ligh
 * @date 2025/10/12
 * @description 定义了Bean定义的注册规范
 *              提供将Bean的元数据信息（BeanDefinition）注册到容器的能力
 *              这是配置阶段的核心接口，在Spring中通过XML、注解等方式
 *              解析配置后，会将BeanDefinition注册到容器中
 */
public interface BeanDefinitionRegistry {

    /**
     * 注册Bean定义
     * 将Bean的元数据信息存储到容器中，后续可以根据这些信息创建Bean实例
     * 
     * @param beanName Bean的唯一标识名称
     * @param beanDefinition Bean的定义信息（元数据）
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
}
