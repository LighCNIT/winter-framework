package org.winterframework.aop.aspectj;

import org.aopalliance.aop.Advice;
import org.winterframework.aop.Pointcut;
import org.winterframework.aop.PointcutAdvisor;

/**
 * AspectJ表达式切点通知器，结合了AspectJ表达式切点和通知
 * 
 * <p>该类是AOP框架中AspectJ表达式切点通知器的实现，将AspectJ表达式切点
 * 和通知组合在一起，提供了基于AspectJ表达式的AOP配置能力。
 * 
 * <p>该类的主要功能：
 * <ul>
 *   <li>支持AspectJ切点表达式，可以精确匹配需要被代理的类和方法</li>
 *   <li>将切点和通知组合，形成完整的AOP配置</li>
 *   <li>支持动态设置切点表达式和通知</li>
 *   <li>延迟创建切点对象，提高性能</li>
 * </ul>
 * 
 * <p>使用示例：
 * <pre>
 * AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
 * advisor.setExpression("execution(* com.example.service.*.*(..))");
 * advisor.setAdvice(new MyAdvice());
 * </pre>
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/25
 * @see PointcutAdvisor
 * @see AspectJExpressionPointcut
 * @see org.aopalliance.aop.Advice
 */
public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {

    /** AspectJ表达式切点对象 */
    private AspectJExpressionPointcut pointcut;
    
    /** 通知对象 */
    private Advice advice;
    
    /** 切点表达式字符串 */
    private String expression;

    /**
     * 设置切点表达式
     * 
     * @param expression AspectJ切点表达式，如"execution(* com.example.service.*.*(..))"
     */
    public void setExpression(String expression) {
        this.expression = expression;
    }

    /**
     * 获取通知对象
     * 
     * @return 通知对象
     */
    @Override
    public Advice getAdvice() {
        return advice;
    }

    /**
     * 设置通知对象
     * 
     * @param advice 通知对象
     */
    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    /**
     * 获取切点对象
     * 
     * <p>如果切点对象尚未创建，则根据表达式创建新的切点对象。
     * 这种延迟创建的方式可以提高性能，避免不必要的对象创建。
     * 
     * @return 切点对象
     */
    @Override
    public Pointcut getPointcut() {
        if (pointcut == null){
            pointcut = new AspectJExpressionPointcut(expression);
        }
        return pointcut;
    }
}
