package org.winterframework.aop.framework.adapter;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.winterframework.aop.MethodThrowsAdvice;

/**
 * 异常通知拦截器，将异常通知适配为方法拦截器
 * 
 * <p>该类是AOP框架中的适配器，用于将MethodThrowsAdvice适配为MethodInterceptor。
 * 通过实现MethodInterceptor接口，异常通知可以参与到AOP拦截器链中。
 * 
 * <p>该拦截器的工作流程：
 * <ol>
 *   <li>尝试执行目标方法（调用invocation.proceed()）</li>
 *   <li>如果目标方法抛出异常，调用异常通知的throwsHandle方法</li>
 *   <li>重新抛出异常，保持原有的异常行为</li>
 * </ol>
 * 
 * <p>注意：该拦截器只在目标方法抛出异常时执行，如果目标方法正常返回则不会执行。
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/25
 * @see org.aopalliance.intercept.MethodInterceptor
 * @see MethodThrowsAdvice
 * @see org.aopalliance.intercept.MethodInvocation
 */
public class MethodThrowsAdviceInterceptor implements MethodInterceptor {

    /** 异常通知对象 */
    private MethodThrowsAdvice advice;

    /**
     * 默认构造函数
     */
    public MethodThrowsAdviceInterceptor() {
    }

    /**
     * 构造函数
     * @param advice 异常通知对象
     */
    public MethodThrowsAdviceInterceptor(MethodThrowsAdvice advice) {
        this.advice = advice;
    }

    /**
     * 设置异常通知
     * @param advice 异常通知对象
     */
    public void setAdvice(MethodThrowsAdvice advice) {
        this.advice = advice;
    }

    /**
     * 拦截方法调用，执行异常通知
     * 
     * @param invocation 方法调用上下文
     * @return 方法执行结果
     * @throws Throwable 执行过程中可能抛出的异常
     */
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            return invocation.proceed();
        }
        catch (Throwable ex) {
            this.advice.throwsHandle(ex, invocation.getMethod(), invocation.getArguments(), invocation.getThis());
            throw ex;
        }
    }
}
