package org.winterframework.aop;

import java.lang.reflect.Method;

/**
 * 方法后置通知接口，定义在目标方法执行后执行的横切逻辑
 * 
 * <p>该接口是AOP框架中方法后置通知的抽象，用于在目标方法执行后执行
 * 横切关注点。方法后置通知通常用于资源清理、日志记录、结果处理等场景。
 * 
 * <p>方法后置通知会在目标方法执行后被调用，无论目标方法是否正常返回或抛出异常。
 * 可以访问目标方法、参数和目标对象，但不能修改方法的返回值。
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/25
 * @see AfterAdvice
 * @see MethodBeforeAdvice
 * @see MethodAroundAdvice
 */
public interface MethodAfterAdvice extends AfterAdvice{

    /**
     * 在目标方法执行后调用
     * 
     * @param method 目标方法
     * @param args 方法参数
     * @param target 目标对象
     * @throws Throwable 执行过程中可能抛出的异常
     */
    void after(Method method, Object[] args, Object target) throws Throwable;
}
