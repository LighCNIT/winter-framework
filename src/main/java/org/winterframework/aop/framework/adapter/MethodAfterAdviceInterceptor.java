package org.winterframework.aop.framework.adapter;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.winterframework.aop.MethodAfterAdvice;

/**
 * 后置通知拦截器，将后置通知适配为方法拦截器
 * 
 * <p>该类是AOP框架中的适配器，用于将MethodAfterAdvice适配为MethodInterceptor。
 * 通过实现MethodInterceptor接口，后置通知可以参与到AOP拦截器链中。
 * 
 * <p>该拦截器的工作流程：
 * <ol>
 *   <li>先执行目标方法（调用methodInvocation.proceed()）</li>
 *   <li>在目标方法执行后调用后置通知的after方法</li>
 *   <li>返回目标方法的执行结果</li>
 * </ol>
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/25
 * @see org.aopalliance.intercept.MethodInterceptor
 * @see MethodAfterAdvice
 * @see org.aopalliance.intercept.MethodInvocation
 */
public class MethodAfterAdviceInterceptor implements MethodInterceptor {

    /** 后置通知对象 */
    private MethodAfterAdvice advice;

    /**
     * 默认构造函数
     */
    public MethodAfterAdviceInterceptor() {
    }

    /**
     * 构造函数
     * @param advice 后置通知对象
     */
    public MethodAfterAdviceInterceptor(MethodAfterAdvice advice) {
        this.advice = advice;
    }

    /**
     * 设置后置通知
     * @param advice 后置通知对象
     */
    public void setAdvice(MethodAfterAdvice advice) {
        this.advice = advice;
    }

    /**
     * 拦截方法调用，执行后置通知
     * 
     * @param methodInvocation 方法调用上下文
     * @return 方法执行结果
     * @throws Throwable 执行过程中可能抛出的异常
     */
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object retVal = methodInvocation.proceed();
        this.advice.after(methodInvocation.getMethod(), methodInvocation.getArguments(), methodInvocation.getThis());
        return retVal;
    }
}
