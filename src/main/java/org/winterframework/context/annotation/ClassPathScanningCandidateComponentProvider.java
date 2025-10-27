package org.winterframework.context.annotation;

import cn.hutool.core.util.ClassUtil;
import org.winterframework.beans.factory.config.BeanDefinition;
import org.winterframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/27
 * @description TODO
 */
public class ClassPathScanningCandidateComponentProvider {

    public Set<BeanDefinition> findCandidateComponents(String basePackage){
        Set<BeanDefinition> candidates = new LinkedHashSet<BeanDefinition>();
        // 扫描有org.springframework.stereotype.Component注解的类
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        for (Class<?> clazz : classes) {
            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            candidates.add(beanDefinition);
        }
        return candidates;
    }
}