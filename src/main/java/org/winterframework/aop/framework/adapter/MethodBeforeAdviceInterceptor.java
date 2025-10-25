package org.winterframework.aop.framework.adapter;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.winterframework.aop.MethodBeforeAdvice;

/**
 * 前置通知拦截器，将前置通知适配为方法拦截器
 * 
 * <p>该类是AOP框架中的适配器，用于将BeforeAdvice适配为MethodInterceptor。
 * 通过实现MethodInterceptor接口，前置通知可以参与到AOP拦截器链中。
 * 
 * <p>该拦截器的工作流程：
 * <ol>
 *   <li>在目标方法执行前调用前置通知的before方法</li>
 *   <li>继续执行拦截器链中的下一个拦截器或目标方法</li>
 * </ol>
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/25
 * @see org.aopalliance.intercept.MethodInterceptor
 * @see MethodBeforeAdvice
 * @see org.aopalliance.intercept.MethodInvocation
 */
public class MethodBeforeAdviceInterceptor implements MethodInterceptor {

    /** 前置通知对象 */
    private MethodBeforeAdvice advice;

    /**
     * 默认构造函数
     */
    public MethodBeforeAdviceInterceptor() {
    }

    /**
     * 构造函数
     * @param advice 前置通知对象
     */
    public MethodBeforeAdviceInterceptor(MethodBeforeAdvice advice) {
        this.advice = advice;
    }

    /**
     * 设置前置通知
     * @param advice 前置通知对象
     */
    public void setAdvice(MethodBeforeAdvice advice) {
        this.advice = advice;
    }

    /**
     * 拦截方法调用，执行前置通知
     * 
     * @param invocation 方法调用上下文
     * @return 方法执行结果
     * @throws Throwable 执行过程中可能抛出的异常
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        // 在执行被代理方法之前，先执行before advice操作
        this.advice.before(invocation.getMethod(), invocation.getArguments(), invocation.getThis());
        return invocation.proceed();
    }
}
