package org.winterframework.aop;

/**
 * 切点通知器接口，结合了切点和通知的AOP抽象
 * 
 * <p>该接口扩展了Advisor接口，增加了切点（Pointcut）的概念。
 * 切点通知器是AOP框架中最常用的通知器类型，它将切点和通知组合在一起，
 * 定义了在哪些切点执行什么样的横切逻辑。
 * 
 * <p>切点通知器通过切点来确定需要被代理的类和方法，通过通知来定义
 * 具体的横切逻辑实现。这种设计使得AOP配置更加灵活和精确。
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/24
 * @see Advisor
 * @see Pointcut
 * @see org.aopalliance.aop.Advice
 */
public interface PointcutAdvisor extends Advisor{

    /**
     * 获取切点对象
     * 
     * @return 切点对象，用于确定需要被代理的类和方法
     */
    Pointcut getPointcut();
}
