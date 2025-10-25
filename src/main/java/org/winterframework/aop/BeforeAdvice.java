package org.winterframework.aop;

import org.aopalliance.aop.Advice;

import java.lang.reflect.Method;

/**
 * 前置通知接口，定义在目标方法执行前执行的横切逻辑
 * 
 * <p>该接口是AOP框架中前置通知的抽象，用于在目标方法执行前执行
 * 横切关注点。前置通知通常用于日志记录、权限检查、参数验证等场景。
 * 
 * <p>前置通知会在目标方法执行前被调用，可以访问目标方法、参数和目标对象，
 * 但不能修改方法的执行流程或返回值。
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/24
 * @see org.aopalliance.aop.Advice
 * @see AfterAdvice
 * @see AroundAdvice
 */
public interface BeforeAdvice extends Advice {

    /**
     * 在目标方法执行前调用
     * 
     * @param method 目标方法
     * @param args 方法参数
     * @param target 目标对象
     * @throws Throwable 执行过程中可能抛出的异常
     */
    void before(Method method,Object[] args,Object target) throws Throwable;
}
