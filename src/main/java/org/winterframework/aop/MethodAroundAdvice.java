package org.winterframework.aop;

import org.aopalliance.intercept.MethodInvocation;

/**
 * 方法环绕通知接口，定义在目标方法执行前后都执行的横切逻辑
 * 
 * <p>该接口是AOP框架中方法环绕通知的抽象，用于在目标方法执行前后都执行
 * 横切关注点。方法环绕通知是最强大的通知类型，可以完全控制目标方法的执行。
 * 
 * <p>方法环绕通知可以：
 * <ul>
 *   <li>在目标方法执行前执行前置逻辑</li>
 *   <li>控制目标方法是否执行（通过调用invocation.proceed()）</li>
 *   <li>在目标方法执行后执行后置逻辑</li>
 *   <li>修改目标方法的返回值</li>
 *   <li>处理目标方法抛出的异常</li>
 * </ul>
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/25
 * @see AroundAdvice
 * @see org.aopalliance.intercept.MethodInvocation
 */
public interface MethodAroundAdvice extends AroundAdvice{

    /**
     * 环绕目标方法执行
     * 
     * @param invocation 方法调用上下文，包含目标对象、方法、参数等信息
     * @return 目标方法的执行结果（可以修改）
     * @throws Throwable 执行过程中可能抛出的异常
     */
    Object around(MethodInvocation invocation) throws Throwable;
}
