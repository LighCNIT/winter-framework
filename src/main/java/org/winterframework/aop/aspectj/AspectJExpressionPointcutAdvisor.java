package org.winterframework.aop.aspectj;

import org.aopalliance.aop.Advice;
import org.winterframework.aop.Pointcut;
import org.winterframework.aop.PointcutAdvisor;

/**
 * @author Ligh
 * 2025/10/25 10:31
 **/
public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {

    private AspectJExpressionPointcut pointcut;
    private Advice advice;
    private String expression;

    public void setExpression(String expression) {
        this.expression = expression;
        pointcut = new AspectJExpressionPointcut(expression);
    }

    @Override
    public Advice getAdvice() {
        return advice;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }
}
