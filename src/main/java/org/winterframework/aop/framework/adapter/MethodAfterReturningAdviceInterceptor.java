package org.winterframework.aop.framework.adapter;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.winterframework.aop.AfterReturningAdvice;

/**
 * 返回后通知拦截器，将返回后通知适配为方法拦截器
 * 
 * <p>该类是AOP框架中的适配器，用于将AfterReturningAdvice适配为MethodInterceptor。
 * 通过实现MethodInterceptor接口，返回后通知可以参与到AOP拦截器链中。
 * 
 * <p>该拦截器的工作流程：
 * <ol>
 *   <li>先执行目标方法（调用methodInvocation.proceed()）</li>
 *   <li>在目标方法正常返回后调用返回后通知的afterReturning方法</li>
 *   <li>返回目标方法的执行结果</li>
 * </ol>
 * 
 * <p>注意：该拦截器只在目标方法正常返回时执行，如果目标方法抛出异常则不会执行。
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/25
 * @see org.aopalliance.intercept.MethodInterceptor
 * @see AfterReturningAdvice
 * @see org.aopalliance.intercept.MethodInvocation
 */
public class MethodAfterReturningAdviceInterceptor implements MethodInterceptor {

    /** 返回后通知对象 */
    private AfterReturningAdvice advice;

    /**
     * 默认构造函数
     */
    public MethodAfterReturningAdviceInterceptor() {
    }

    /**
     * 构造函数
     * @param advice 返回后通知对象
     */
    public MethodAfterReturningAdviceInterceptor(AfterReturningAdvice advice) {
        this.advice = advice;
    }

    /**
     * 设置返回后通知
     * @param advice 返回后通知对象
     */
    public void setAdvice(AfterReturningAdvice advice) {
        this.advice = advice;
    }

    /**
     * 拦截方法调用，执行返回后通知
     * 
     * @param methodInvocation 方法调用上下文
     * @return 方法执行结果
     * @throws Throwable 执行过程中可能抛出的异常
     */
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object retVal = methodInvocation.proceed();
        this.advice.afterReturning(retVal, methodInvocation.getMethod(), methodInvocation.getArguments(), methodInvocation.getThis());
        return retVal;
    }
}
