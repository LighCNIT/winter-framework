package org.winterframework.aop;

import org.aopalliance.aop.Advice;

import java.lang.reflect.Method;

/**
 * 返回后通知接口，定义在目标方法正常返回后执行的横切逻辑
 * 
 * <p>该接口是AOP框架中返回后通知的抽象，用于在目标方法正常返回后执行
 * 横切关注点。返回后通知通常用于结果处理、日志记录、缓存更新等场景。
 * 
 * <p>返回后通知只在目标方法正常返回时被调用，如果目标方法抛出异常则不会执行。
 * 可以访问目标方法的返回值、方法、参数和目标对象，但不能修改返回值。
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/24
 * @see org.aopalliance.aop.Advice
 * @see AfterAdvice
 * @see ThrowsAdvice
 * @see BeforeAdvice
 */
public interface AfterReturningAdvice extends Advice {

    /**
     * 在目标方法正常返回后调用
     * 
     * @param returnValue 目标方法的返回值
     * @param method 目标方法
     * @param args 方法参数
     * @param target 目标对象
     * @throws Throwable 执行过程中可能抛出的异常
     */
    void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable;

}
