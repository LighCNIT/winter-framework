package org.winterframework.aop;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * AOP通知支持类，用于封装AOP代理所需的所有信息
 * 
 * <p>该类是AOP框架的核心配置类，包含了创建代理对象所需的所有必要信息：
 * <ul>
 *   <li>目标对象信息（TargetSource）</li>
 *   <li>方法拦截器（MethodInterceptor）</li>
 *   <li>方法匹配器（MethodMatcher）</li>
 * </ul>
 * 
 * <p>通过配置这些组件，可以创建JDK动态代理或CGLIB代理对象，
 * 实现对目标方法的横切关注点处理。
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/24
 * @see TargetSource
 * @see MethodMatcher
 * @see org.aopalliance.intercept.MethodInterceptor
 */
public class AdvisedSupport {

    private boolean proxyTargetClass = false;

    /** 目标对象源，封装被代理的目标对象信息 */
    private TargetSource targetSource;

    /** 方法拦截器，用于在目标方法执行前后进行横切处理 */
    private MethodInterceptor methodInterceptor;

    /** 方法匹配器，用于判断哪些方法需要被代理 */
    private MethodMatcher methodMatcher;

    public boolean isProxyTargetClass() {
        return proxyTargetClass;
    }

    public void setProxyTargetClass(boolean proxyTargetClass) {
        this.proxyTargetClass = proxyTargetClass;
    }

    /**
     * 获取目标对象源
     * @return 目标对象源
     */
    public TargetSource getTargetSource() {
        return targetSource;
    }

    /**
     * 设置目标对象源
     * @param targetSource 目标对象源
     */
    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }

    /**
     * 获取方法拦截器
     * @return 方法拦截器
     */
    public MethodInterceptor getMethodInterceptor() {
        return methodInterceptor;
    }

    /**
     * 设置方法拦截器
     * @param methodInterceptor 方法拦截器
     */
    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    /**
     * 获取方法匹配器
     * @return 方法匹配器
     */
    public MethodMatcher getMethodMatcher() {
        return methodMatcher;
    }

    /**
     * 设置方法匹配器
     * @param methodMatcher 方法匹配器
     */
    public void setMethodMatcher(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }
}