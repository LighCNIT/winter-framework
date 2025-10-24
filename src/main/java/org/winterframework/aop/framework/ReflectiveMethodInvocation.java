package org.winterframework.aop.framework;

import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

/**
 * 反射方法调用实现类，封装了方法调用的上下文信息
 * 
 * <p>该类实现了AOP联盟的MethodInvocation接口，用于封装方法调用的所有信息，
 * 包括目标对象、方法、参数等。在AOP拦截器链中，每个拦截器都可以通过
 * 调用proceed()方法来继续执行下一个拦截器或目标方法。
 * 
 * <p>该类是AOP框架中方法调用的核心抽象，为不同的代理实现（JDK、CGLIB）
 * 提供了统一的方法调用接口。
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/24
 * @see org.aopalliance.intercept.MethodInvocation
 */
public class ReflectiveMethodInvocation implements MethodInvocation {
    
    /** 目标对象 */
    protected final Object target;

    /** 要调用的方法 */
    protected final Method method;

    /** 方法参数 */
    protected final Object[] arguments;

    /**
     * 构造函数
     * @param target 目标对象
     * @param method 要调用的方法
     * @param arguments 方法参数
     */
    public ReflectiveMethodInvocation(Object target, Method method, Object[] arguments) {
        this.target = target;
        this.method = method;
        this.arguments = arguments;
    }

    /**
     * 获取要调用的方法
     * @return 方法对象
     */
    @Override
    public Method getMethod() {
        return method;
    }

    /**
     * 获取方法参数
     * @return 参数数组
     */
    @Override
    public Object[] getArguments() {
        return arguments;
    }

    /**
     * 执行目标方法
     * @return 方法执行结果
     * @throws Throwable 方法执行过程中抛出的异常
     */
    @Override
    public Object proceed() throws Throwable {
        return method.invoke(target,arguments);
    }

    /**
     * 获取目标对象
     * @return 目标对象
     */
    @Override
    public Object getThis() {
        return target;
    }

    /**
     * 获取静态部分（方法对象）
     * @return 方法对象
     */
    @Override
    public AccessibleObject getStaticPart() {
        return method;
    }
}