package org.winterframework.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author Ligh
 * 2025/10/25 10:03
 **/
public class GenericInterceptor implements MethodInterceptor {

    private BeforeAdvice beforeAdvice;
    private AfterAdvice afterAdvice;
    private AfterReturningAdvice afterReturningAdvice;
    private ThrowsAdvice throwsAdvice;
    private AroundAdvice aroundAdvice;

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        if (aroundAdvice != null){
            return aroundAdvice.around(methodInvocation);
        }
        Object result = null;
        try {
            // 前置通知
            if (beforeAdvice != null){
                beforeAdvice.before(methodInvocation.getMethod(),methodInvocation.getArguments(),methodInvocation.getThis());
            }
            result = methodInvocation.proceed();
        }catch (Exception throwable){
            // 异常通知
            if (throwsAdvice != null){
                throwsAdvice.throwsHandle(throwable,methodInvocation.getMethod(),methodInvocation.getArguments(),methodInvocation.getThis());
            }
        }finally {
            if (afterAdvice != null){
                afterAdvice.after(methodInvocation.getMethod(), methodInvocation.getArguments(), methodInvocation.getThis());
            }
        }
        // 返回通知
        if (afterReturningAdvice != null){
            afterReturningAdvice.afterReturning(result,methodInvocation.getMethod(), methodInvocation.getArguments(), methodInvocation.getThis());
        }
        return result;
    }

    public void setBeforeAdvice(BeforeAdvice beforeAdvice) {
        this.beforeAdvice = beforeAdvice;
    }

    public void setAfterAdvice(AfterAdvice afterAdvice) {
        this.afterAdvice = afterAdvice;
    }

    public void setAfterReturningAdvice(AfterReturningAdvice afterReturningAdvice) {
        this.afterReturningAdvice = afterReturningAdvice;
    }

    public void setThrowsAdvice(ThrowsAdvice throwsAdvice) {
        this.throwsAdvice = throwsAdvice;
    }

    public void setAroundAdvice(AroundAdvice aroundAdvice) {
        this.aroundAdvice = aroundAdvice;
    }
}
