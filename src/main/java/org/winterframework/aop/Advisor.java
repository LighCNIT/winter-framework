package org.winterframework.aop;

import org.aopalliance.aop.Advice;

/**
 * 通知器接口，定义了AOP中通知的抽象概念
 * 
 * <p>该接口是AOP框架中通知器的核心抽象，用于封装横切关注点的实现。
 * 通知器将切点（Pointcut）和通知（Advice）组合在一起，定义了在哪些
 * 切点执行什么样的横切逻辑。
 * 
 * <p>通知器是AOP框架中连接切点和通知的桥梁，通过实现该接口可以创建
 * 各种类型的通知器，如前置通知器、后置通知器等。
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/24
 * @see org.aopalliance.aop.Advice
 * @see PointcutAdvisor
 */
public interface Advisor {

    /**
     * 获取通知对象
     * 
     * @return 通知对象，包含具体的横切逻辑实现
     */
    Advice getAdvice();
}
