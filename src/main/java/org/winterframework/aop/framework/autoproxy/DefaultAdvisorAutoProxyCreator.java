package org.winterframework.aop.framework.autoproxy;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.winterframework.aop.*;
import org.winterframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.winterframework.aop.framework.ProxyFactory;
import org.winterframework.beans.BeanException;
import org.winterframework.beans.PropertyValues;
import org.winterframework.beans.factory.BeanFactory;
import org.winterframework.beans.factory.BeanFactoryAware;
import org.winterframework.beans.factory.config.BeanDefinition;
import org.winterframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.winterframework.beans.factory.support.DefaultListableBeanFactory;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 默认顾问自动代理创建器
 * 
 * <p>该类是AOP框架中的核心组件，负责自动为匹配的Bean创建代理对象。
 * 它实现了Spring的BeanPostProcessor机制，在Bean实例化之前检查是否需要创建代理。
 * 
 * <p>主要功能：
 * <ul>
 *   <li>自动扫描容器中的所有AspectJExpressionPointcutAdvisor</li>
 *   <li>根据切点表达式判断目标Bean是否需要代理</li>
 *   <li>为匹配的Bean创建JDK动态代理或CGLIB代理</li>
 *   <li>避免对基础设施类（Advice、Pointcut、Advisor等）进行代理</li>
 * </ul>
 * 
 * <p>工作原理：
 * <ol>
 *   <li>在Bean实例化前，通过postProcessBeforeInstantiation方法拦截</li>
 *   <li>检查目标类是否为基础设施类，如果是则跳过代理创建</li>
 *   <li>获取缓存的AspectJExpressionPointcutAdvisor并检查是否匹配目标类</li>
 *   <li>如果匹配，则创建AdvisedSupport配置并生成代理对象</li>
 *   <li>如果不匹配，返回null让容器正常创建Bean</li>
 * </ol>
 * 
 * <p>使用示例：
 * <pre>{@code
 * <bean class="org.winterframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator"/>
 * <bean id="pointcutAdvisor" class="org.winterframework.aop.aspectj.AspectJExpressionPointcutAdvisor">
 *     <property name="expression" value="execution(* com.example.service.*.*(..))"/>
 *     <property name="advice" ref="myAdvice"/>
 * </bean>
 * }</pre>
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/25
 * @see InstantiationAwareBeanPostProcessor
 * @see BeanFactoryAware
 * @see AspectJExpressionPointcutAdvisor
 * @see ProxyFactory
 */
public class DefaultAdvisorAutoProxyCreator implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    /** Bean工厂，用于获取Bean定义和创建Bean实例 */
    private DefaultListableBeanFactory beanFactory;
    
    /** 缓存已创建的Advisor，避免重复创建 */
    private Collection<AspectJExpressionPointcutAdvisor> cachedAdvisors;

    /**
     * 判断是否为基础设施类
     * 
     * <p>基础设施类包括AOP框架自身的组件，这些类不应该被代理，
     * 否则会导致无限递归或循环依赖问题。
     * 
     * @param beanClass 要检查的Bean类
     * @return 如果是基础设施类返回true，否则返回false
     */
    private boolean isInfrastructureClass(Class<?> beanClass) {
        return Advice.class.isAssignableFrom(beanClass)
                || Pointcut.class.isAssignableFrom(beanClass)
                || Advisor.class.isAssignableFrom(beanClass)
                || DefaultAdvisorAutoProxyCreator.class.isAssignableFrom(beanClass);
    }

    /**
     * 设置Bean工厂
     * 
     * @param beanFactory Bean工厂实例
     * @throws BeanException 如果设置失败
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeanException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    /**
     * Bean初始化前的后处理
     * 
     * @param bean Bean实例
     * @param beanName Bean名称
     * @return 处理后的Bean实例
     * @throws BeanException 如果处理失败
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeanException {
        return bean;
    }

    /**
     * Bean初始化后的后处理
     * 
     * @param bean Bean实例
     * @param beanName Bean名称
     * @return 处理后的Bean实例
     * @throws BeanException 如果处理失败
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException {
        //避免死循环
        if (isInfrastructureClass(bean.getClass())) {
            return bean;
        }

        Collection<AspectJExpressionPointcutAdvisor> advisors = beanFactory.getBeansOfType(AspectJExpressionPointcutAdvisor.class).values();
        try {
            for (AspectJExpressionPointcutAdvisor advisor : advisors) {
                ClassFilter classFilter = advisor.getPointcut().getClassFilter();
                if (classFilter.matches(bean.getClass())) {
                    AdvisedSupport advisedSupport = new AdvisedSupport();
                    TargetSource targetSource = new TargetSource(bean);
                    advisedSupport.setTargetSource(targetSource);
                    advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
                    advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());

                    //返回代理对象
                    return new ProxyFactory(advisedSupport).getProxy();
                }
            }
        } catch (Exception ex) {
            throw new BeanException("Error create proxy bean for: " + beanName, ex);
        }
        return bean;
    }

    /**
     * Bean实例化前的后处理
     * 
     * <p>这是自动代理创建的核心方法，在Bean实例化前被调用。
     * 如果返回非null值，则使用返回值作为Bean实例，不再进行正常的实例化流程。
     * 
     * <p>处理流程：
     * <ol>
     *   <li>检查目标类是否为基础设施类，如果是则跳过代理创建</li>
     *   <li>获取所有AspectJExpressionPointcutAdvisor</li>
     *   <li>遍历每个Advisor，检查其切点是否匹配目标类</li>
     *   <li>如果匹配，创建AdvisedSupport配置并生成代理对象</li>
     *   <li>如果不匹配，返回null让容器正常创建Bean</li>
     * </ol>
     * 
     * @param beanClass 要实例化的Bean类
     * @param beanName Bean名称
     * @return 代理对象（如果需要代理）或null（正常实例化）
     * @throws BeanException 如果创建代理失败
     */
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeanException {
        return null;
    }
    
    /**
     * 获取缓存的Advisor，避免递归调用
     * 
     * <p>该方法使用懒加载模式，只在第一次调用时创建Advisor集合。
     * 通过缓存机制避免在每次代理创建时都调用getBeansOfType方法，
     * 从而防止无限递归问题。
     * 
     * @return Advisor集合
     */
    private Collection<AspectJExpressionPointcutAdvisor> getCachedAdvisors() {
        if (cachedAdvisors == null) {
            cachedAdvisors = new ArrayList<>();
            String[] beanNames = beanFactory.getBeanDefinitionNames();
            for (String name : beanNames) {
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(name);
                if (AspectJExpressionPointcutAdvisor.class.isAssignableFrom(beanDefinition.getBeanClass())) {
                    try {
                        AspectJExpressionPointcutAdvisor advisor = (AspectJExpressionPointcutAdvisor) beanFactory.getBean(name);
                        cachedAdvisors.add(advisor);
                    } catch (Exception e) {
                        // 忽略无法创建的Advisor，继续处理下一个
                    }
                }
            }
        }
        return cachedAdvisors;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues propertyValues, Object bean, String beanName) throws BeanException {
        return propertyValues;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeanException {
        return true;
    }
}
