package org.winterframework.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.winterframework.aop.framework.adapter.*;

/**
 * 组合通知拦截器，支持多种通知类型的组合使用
 * 
 * <p>该类是AOP框架中的组合拦截器，可以同时支持多种类型的通知：
 * <ul>
 *   <li>前置通知（BeforeAdvice）</li>
 *   <li>后置通知（AfterAdvice）</li>
 *   <li>返回后通知（AfterReturningAdvice）</li>
 *   <li>异常通知（ThrowsAdvice）</li>
 *   <li>环绕通知（AroundAdvice）</li>
 * </ul>
 * 
 * <p>该拦截器的工作流程：
 * <ol>
 *   <li>如果配置了环绕通知，优先执行环绕通知（环绕通知可以完全控制执行流程）</li>
 *   <li>否则按以下顺序执行：前置通知 → 目标方法 → 后置通知/返回后通知/异常通知</li>
 * </ol>
 * 
 * <p>通知执行顺序：
 * <ul>
 *   <li>前置通知：在目标方法执行前</li>
 *   <li>目标方法：执行被代理的方法</li>
 *   <li>后置通知：无论目标方法是否正常返回都会执行</li>
 *   <li>返回后通知：仅在目标方法正常返回时执行</li>
 *   <li>异常通知：仅在目标方法抛出异常时执行</li>
 * </ul>
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/25
 * @see org.aopalliance.intercept.MethodInterceptor
 * @see org.aopalliance.intercept.MethodInvocation
 */
public class CombineAdviceInterceptor implements MethodInterceptor {

    /** 前置通知拦截器 */
    private MethodBeforeAdviceInterceptor beforeAdviceInterceptor;
    
    /** 后置通知拦截器 */
    private MethodAfterAdviceInterceptor afterAdviceInterceptor;
    
    /** 返回后通知拦截器 */
    private MethodAfterReturningAdviceInterceptor afterReturningAdviceInterceptor;
    
    /** 异常通知拦截器 */
    private MethodThrowsAdviceInterceptor throwsAdviceInterceptor;
    
    /** 环绕通知拦截器 */
    private MethodAroundAdviceInterceptor aroundAdviceInterceptor;

    /**
     * 默认构造函数
     */
    public CombineAdviceInterceptor() {
    }

    /**
     * 构造函数，初始化所有通知拦截器
     * 
     * @param beforeAdviceInterceptor 前置通知拦截器
     * @param afterAdviceInterceptor 后置通知拦截器
     * @param afterReturningAdviceInterceptor 返回后通知拦截器
     * @param throwsAdviceInterceptor 异常通知拦截器
     * @param aroundAdviceInterceptor 环绕通知拦截器
     */
    public CombineAdviceInterceptor(MethodBeforeAdviceInterceptor beforeAdviceInterceptor,
                                    MethodAfterAdviceInterceptor afterAdviceInterceptor,
                                    MethodAfterReturningAdviceInterceptor afterReturningAdviceInterceptor,
                                    MethodThrowsAdviceInterceptor throwsAdviceInterceptor,
                                    MethodAroundAdviceInterceptor aroundAdviceInterceptor) {
        this.beforeAdviceInterceptor = beforeAdviceInterceptor;
        this.afterAdviceInterceptor = afterAdviceInterceptor;
        this.afterReturningAdviceInterceptor = afterReturningAdviceInterceptor;
        this.throwsAdviceInterceptor = throwsAdviceInterceptor;
        this.aroundAdviceInterceptor = aroundAdviceInterceptor;
    }

    /**
     * 设置前置通知拦截器
     * @param beforeAdviceInterceptor 前置通知拦截器
     */
    public void setBeforeAdviceInterceptor(MethodBeforeAdviceInterceptor beforeAdviceInterceptor) {
        this.beforeAdviceInterceptor = beforeAdviceInterceptor;
    }

    /**
     * 设置后置通知拦截器
     * @param afterAdviceInterceptor 后置通知拦截器
     */
    public void setAfterAdviceInterceptor(MethodAfterAdviceInterceptor afterAdviceInterceptor) {
        this.afterAdviceInterceptor = afterAdviceInterceptor;
    }

    /**
     * 设置返回后通知拦截器
     * @param afterReturningAdviceInterceptor 返回后通知拦截器
     */
    public void setAfterReturningAdviceInterceptor(MethodAfterReturningAdviceInterceptor afterReturningAdviceInterceptor) {
        this.afterReturningAdviceInterceptor = afterReturningAdviceInterceptor;
    }

    /**
     * 设置异常通知拦截器
     * @param throwsAdviceInterceptor 异常通知拦截器
     */
    public void setThrowsAdviceInterceptor(MethodThrowsAdviceInterceptor throwsAdviceInterceptor) {
        this.throwsAdviceInterceptor = throwsAdviceInterceptor;
    }

    /**
     * 设置环绕通知拦截器
     * @param aroundAdviceInterceptor 环绕通知拦截器
     */
    public void setAroundAdviceInterceptor(MethodAroundAdviceInterceptor aroundAdviceInterceptor) {
        this.aroundAdviceInterceptor = aroundAdviceInterceptor;
    }

    /**
     * 拦截方法调用，按顺序执行各种通知
     * 
     * <p>该方法实现了组合通知的执行逻辑：
     * <ol>
     *   <li>如果配置了环绕通知，优先执行环绕通知（环绕通知可以完全控制执行流程）</li>
     *   <li>否则按以下顺序执行：前置通知 → 目标方法 → 后置通知/返回后通知/异常通知</li>
     * </ol>
     * 
     * <p>通知执行顺序说明：
     * <ul>
     *   <li>前置通知：在目标方法执行前执行</li>
     *   <li>目标方法：通过methodInvocation.proceed()执行</li>
     *   <li>后置通知：在finally块中执行，无论目标方法是否正常返回都会执行</li>
     *   <li>返回后通知：仅在目标方法正常返回时执行</li>
     *   <li>异常通知：仅在目标方法抛出异常时执行</li>
     * </ul>
     * 
     * @param methodInvocation 方法调用上下文
     * @return 目标方法的执行结果
     * @throws Throwable 执行过程中可能抛出的异常
     */
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        // 如果配置了环绕通知，则优先执行环绕通知
        if (aroundAdviceInterceptor != null) {
            return aroundAdviceInterceptor.invoke(methodInvocation);
        }

        Object result = null; // 用于存储目标方法执行的结果

        try {
            // 前置通知：如果配置了 BeforeAdvice，则在目标方法执行前调用其 before 方法
            if (beforeAdviceInterceptor != null) {
                beforeAdviceInterceptor.invoke(methodInvocation);
            }

            result = methodInvocation.proceed();

        } catch (Throwable throwable) {
            // 异常通知：如果配置了 ThrowsAdvice，则在捕获到异常后调用其 throwsHandle 方法
            if (throwsAdviceInterceptor != null) {
                // 将捕获到的异常、方法、参数和目标对象传递给异常通知处理器
                throwsAdviceInterceptor.invoke(methodInvocation);
            }
            throw throwable; // 重新抛出异常，确保异常行为传播
        } finally {
            // 后置通知：无论目标方法是否抛出异常，都会在方法执行结束后执行此处的逻辑
            if (afterAdviceInterceptor != null) {
                afterAdviceInterceptor.invoke(methodInvocation);
            }
        }

        // 返回通知：仅在目标方法成功执行（没有抛出异常）并返回结果后执行此处的逻辑
        // 注意：此部分位于 try-catch-finally 块之外，确保只有成功返回才触发
        if (afterReturningAdviceInterceptor != null) {
            afterReturningAdviceInterceptor.invoke(methodInvocation);
        }

        // 返回目标方法的执行结果
        return result;
    }
}
