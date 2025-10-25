package org.winterframework.aop;

import org.aopalliance.aop.Advice;

import java.lang.reflect.Method;

/**
 * 异常通知接口，定义在目标方法抛出异常时执行的横切逻辑
 * 
 * <p>该接口是AOP框架中异常通知的抽象，用于在目标方法抛出异常时执行
 * 横切关注点。异常通知通常用于异常处理、日志记录、事务回滚等场景。
 * 
 * <p>异常通知只在目标方法抛出异常时被调用，如果目标方法正常返回则不会执行。
 * 可以访问抛出的异常、目标方法、参数和目标对象，用于异常处理和恢复。
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/24
 * @see org.aopalliance.aop.Advice
 * @see AfterAdvice
 * @see AfterReturningAdvice
 * @see BeforeAdvice
 */
public interface ThrowsAdvice extends Advice {

    /**
     * 在目标方法抛出异常时调用
     * 
     * @param throwable 目标方法抛出的异常
     * @param method 目标方法
     * @param args 方法参数
     * @param target 目标对象
     */
    void throwsHandle(Throwable throwable, Method method, Object[] args, Object target);
}
