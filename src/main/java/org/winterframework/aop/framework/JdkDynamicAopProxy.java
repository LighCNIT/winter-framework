package org.winterframework.aop.framework;

import org.aopalliance.intercept.MethodInterceptor;
import org.winterframework.aop.AdvisedSupport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * JDK动态代理实现类，基于接口的AOP代理
 * 
 * <p>该类使用JDK的Proxy类创建动态代理，适用于目标类实现了接口的情况。
 * JDK动态代理只能代理接口中定义的方法，无法代理类中未在接口中声明的方法。
 * 
 * <p>代理对象会拦截所有方法调用，并根据配置的MethodMatcher判断是否需要
 * 执行横切逻辑。如果方法匹配，则调用MethodInterceptor；否则直接调用目标方法。
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/24
 * @see AopProxy
 * @see java.lang.reflect.InvocationHandler
 */
public class JdkDynamicAopProxy implements AopProxy, InvocationHandler {

    /** AOP配置信息 */
    private final AdvisedSupport advised;

    /**
     * 构造函数
     * @param advised AOP配置信息
     */
    public JdkDynamicAopProxy(AdvisedSupport advised) {
        this.advised = advised;
    }

    /**
     * 代理方法调用处理器
     * 
     * @param proxy 代理对象
     * @param method 被调用的方法
     * @param args 方法参数
     * @return 方法执行结果
     * @throws Throwable 方法执行过程中抛出的异常
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (advised.getMethodMatcher().matches(method, advised.getTargetSource().getTarget().getClass())) {
            // 方法匹配，执行横切逻辑
            MethodInterceptor methodInterceptor = advised.getMethodInterceptor();
            return methodInterceptor.invoke(new ReflectiveMethodInvocation(advised.getTargetSource().getTarget(), method, args));
        }
        // 方法不匹配，直接调用目标方法
        return method.invoke(advised.getTargetSource().getTarget(), args);
    }

    /**
     * 创建JDK动态代理对象
     * 
     * @return 代理对象
     */
    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(getClass().getClassLoader(), advised.getTargetSource().getTargetClass(), this);
    }
}