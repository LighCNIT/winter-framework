package org.winterframework.aop;

import org.aopalliance.aop.Advice;

import java.lang.reflect.Method;

/**
 * 后置通知接口，定义在目标方法执行后执行的横切逻辑
 * 
 * <p>该接口是AOP框架中后置通知的抽象，用于在目标方法执行后执行
 * 横切关注点。后置通知通常用于资源清理、日志记录、结果处理等场景。
 * 
 * <p>后置通知会在目标方法执行后被调用，无论目标方法是否正常返回或抛出异常。
 * 可以访问目标方法、参数和目标对象，但不能修改方法的返回值。
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/24
 * @see org.aopalliance.aop.Advice
 * @see BeforeAdvice
 * @see AfterReturningAdvice
 * @see ThrowsAdvice
 */
public interface AfterAdvice extends Advice {

    // 该接口作为后置通知的标记接口，具体的后置通知实现
    // 应该实现AfterReturningAdvice或ThrowsAdvice接口
}
