package org.winterframework.aop.framework.adapter;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.winterframework.aop.MethodAroundAdvice;

/**
 * 环绕通知拦截器，将环绕通知适配为方法拦截器
 * 
 * <p>该类是AOP框架中的适配器，用于将MethodAroundAdvice适配为MethodInterceptor。
 * 通过实现MethodInterceptor接口，环绕通知可以参与到AOP拦截器链中。
 * 
 * <p>该拦截器的工作流程：
 * <ol>
 *   <li>将方法调用上下文传递给环绕通知的around方法</li>
 *   <li>环绕通知可以完全控制目标方法的执行</li>
 *   <li>返回环绕通知处理后的结果</li>
 * </ol>
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/25
 * @see org.aopalliance.intercept.MethodInterceptor
 * @see MethodAroundAdvice
 * @see org.aopalliance.intercept.MethodInvocation
 */
public class MethodAroundAdviceInterceptor implements MethodInterceptor {

    /** 环绕通知对象 */
    private MethodAroundAdvice advice;

    /**
     * 默认构造函数
     */
    public MethodAroundAdviceInterceptor() {
    }

    /**
     * 构造函数
     * @param advice 环绕通知对象
     */
    public MethodAroundAdviceInterceptor(MethodAroundAdvice advice) {
        this.advice = advice;
    }

    /**
     * 设置环绕通知
     * @param advice 环绕通知对象
     */
    public void setAdvice(MethodAroundAdvice advice) {
        this.advice = advice;
    }

    /**
     * 拦截方法调用，执行环绕通知
     * 
     * @param invocation 方法调用上下文
     * @return 方法执行结果
     * @throws Throwable 执行过程中可能抛出的异常
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return advice.around(invocation);
    }
}
