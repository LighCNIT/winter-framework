package org.winterframework.beans.factory.support;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import org.winterframework.beans.BeanException;
import org.winterframework.beans.factory.DisposableBean;
import org.winterframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Method;

/**
 * DisposableBean适配器
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/18
 * @description 用于统一处理Bean销毁逻辑的适配器类
 * 
 * <p>DisposableBeanAdapter是一个适配器类，用于统一处理Bean的销毁逻辑。
 * 它支持两种销毁方式：</p>
 * <ul>
 *   <li>实现DisposableBean接口的Bean：调用其destroy()方法</li>
 *   <li>配置了destroy-method的Bean：通过反射调用指定的销毁方法</li>
 * </ul>
 * 
 * <p>设计特点：</p>
 * <ul>
 *   <li>适配器模式：统一不同销毁方式的接口</li>
 *   <li>反射调用：支持通过方法名调用销毁方法</li>
 *   <li>避免重复：防止同时实现DisposableBean和配置destroy-method时重复调用</li>
 *   <li>异常处理：提供详细的异常信息便于调试</li>
 * </ul>
 * 
 * <p>使用场景：</p>
 * <ul>
 *   <li>统一管理Bean的销毁逻辑</li>
 *   <li>支持多种销毁方式的Bean</li>
 *   <li>在ApplicationContext关闭时批量销毁Bean</li>
 * </ul>
 * 
 * @see DisposableBean
 * @see BeanDefinition#getDestroyMethodName()
 * @see DefaultSingletonBeanRegistry#destroySingletons()
 */
public class DisposableBeanAdapter implements DisposableBean {

    /**
     * 要销毁的Bean实例
     */
    private final Object bean;

    /**
     * Bean名称，用于异常信息
     */
    private final String beanName;

    /**
     * 销毁方法名称（从BeanDefinition中获取）
     */
    private final String destroyMethodName;

    /**
     * 构造方法
     * 
     * @param bean 要销毁的Bean实例
     * @param beanName Bean名称
     * @param beanDefinition Bean定义信息，包含销毁方法名称
     */
    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }

    /**
     * 执行Bean的销毁逻辑
     * 
     * <p>按照以下顺序执行销毁逻辑：</p>
     * <ol>
     *   <li>如果Bean实现了DisposableBean接口，先调用其destroy()方法</li>
     *   <li>如果配置了destroy-method且不是"destroy"方法，则通过反射调用指定的销毁方法</li>
     * </ol>
     * 
     * <p>注意事项：</p>
     * <ul>
     *   <li>避免重复调用：如果Bean实现了DisposableBean接口且destroy-method也是"destroy"，则不会重复调用</li>
     *   <li>异常处理：如果销毁方法不存在，会抛出BeanException</li>
     *   <li>反射调用：使用Hutool的ClassUtil.getPublicMethod()方法获取公共方法</li>
     * </ul>
     * 
     * @throws Exception 如果销毁过程中发生错误
     */
    @Override
    public void destroy() throws Exception {
        // 1. 如果Bean实现了DisposableBean接口，先调用其destroy()方法
        if (bean instanceof DisposableBean) {
            ((DisposableBean) bean).destroy();
        }
        
        // 2. 如果配置了destroy-method且不是"destroy"方法，则通过反射调用
        if (StrUtil.isNotEmpty(destroyMethodName) && 
            !(bean instanceof DisposableBean && "destroy".equals(this.destroyMethodName))) {
            
            Method method = ClassUtil.getPublicMethod(bean.getClass(), destroyMethodName);
            if (method == null) {
                throw new BeanException("Couldn't find a destroy method named '" + destroyMethodName + 
                    "' on bean with name '" + beanName + "'");
            }
            method.invoke(bean);
        }
    }
}