package org.winterframework.aop.framework;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.winterframework.aop.AdvisedSupport;

import java.lang.reflect.Method;

/**
 * CGLIB代理实现类，基于继承的AOP代理
 * 
 * <p>该类使用CGLIB库创建动态代理，通过继承目标类来创建代理对象。
 * CGLIB代理可以代理任何类，包括没有实现接口的类，但无法代理final类和方法。
 * 
 * <p>代理对象会拦截所有方法调用，并根据配置的MethodMatcher判断是否需要
 * 执行横切逻辑。如果方法匹配，则调用MethodInterceptor；否则直接调用目标方法。
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/24
 * @see AopProxy
 * @see net.sf.cglib.proxy.MethodInterceptor
 */
public class CglibAopProxy implements AopProxy{

    /** AOP配置信息 */
    private final AdvisedSupport advised;

    /**
     * 构造函数
     * @param advised AOP配置信息
     */
    public CglibAopProxy(AdvisedSupport advised) {
        this.advised = advised;
    }

    /**
     * 创建CGLIB代理对象
     * 
     * @return 代理对象
     */
    @Override
    public Object getProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(advised.getTargetSource().getTarget().getClass());
        enhancer.setInterfaces(advised.getTargetSource().getTargetClass());
        enhancer.setCallback(new DynamicAdvisedInterceptor(advised));
        return enhancer.create();
    }

    /**
     * CGLIB动态代理拦截器
     * 
     * <p>该类实现了CGLIB的MethodInterceptor接口，用于拦截代理对象的方法调用。
     * 在方法调用时，会根据MethodMatcher判断是否需要执行横切逻辑。
     */
    private static class DynamicAdvisedInterceptor implements MethodInterceptor {
        /** AOP配置信息 */
        private final AdvisedSupport advised;

        /**
         * 构造函数
         * @param advised AOP配置信息
         */
        private DynamicAdvisedInterceptor(AdvisedSupport advised) {
            this.advised = advised;
        }

        /**
         * 拦截方法调用
         * 
         * @param o 代理对象
         * @param method 被调用的方法
         * @param objects 方法参数
         * @param methodProxy 方法代理对象
         * @return 方法执行结果
         * @throws Throwable 方法执行过程中抛出的异常
         */
        @Override
        public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
            CglibMethodInvocation methodInvocation = new CglibMethodInvocation(advised.getTargetSource().getTarget(), method, objects, methodProxy);
            if (advised.getMethodMatcher().matches(method, advised.getTargetSource().getTarget().getClass())) {
                // 方法匹配，执行横切逻辑
                return advised.getMethodInterceptor().invoke(methodInvocation);
            }
            // 方法不匹配，直接调用目标方法
            return methodInvocation.proceed();
        }
    }

    /**
     * CGLIB方法调用实现类
     * 
     * <p>该类继承自ReflectiveMethodInvocation，专门用于CGLIB代理的方法调用。
     * 使用CGLIB的MethodProxy来调用目标方法，性能比反射调用更好。
     */
    private static class CglibMethodInvocation extends ReflectiveMethodInvocation {

        /** CGLIB方法代理对象 */
        private final MethodProxy methodProxy;

        /**
         * 构造函数
         * @param target 目标对象
         * @param method 要调用的方法
         * @param arguments 方法参数
         * @param methodProxy CGLIB方法代理对象
         */
        public CglibMethodInvocation(Object target, Method method, Object[] arguments, MethodProxy methodProxy) {
            super(target, method, arguments);
            this.methodProxy = methodProxy;
        }

        /**
         * 使用CGLIB的MethodProxy执行目标方法
         * 
         * @return 方法执行结果
         * @throws Throwable 方法执行过程中抛出的异常
         */
        @Override
        public Object proceed() throws Throwable {
            return this.methodProxy.invoke(this.target, this.arguments);
        }
    }
}