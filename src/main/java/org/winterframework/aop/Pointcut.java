package org.winterframework.aop;

/**
 * 切点接口，定义了AOP中切点的抽象概念
 * 
 * <p>切点是AOP框架中的核心概念，用于确定在哪些类的哪些方法上应用横切关注点。
 * 一个切点由两部分组成：
 * <ul>
 *   <li>类过滤器（ClassFilter）：确定哪些类需要被代理</li>
 *   <li>方法匹配器（MethodMatcher）：确定哪些方法需要被代理</li>
 * </ul>
 * 
 * <p>只有同时满足类过滤器和方法匹配器条件的类和方法才会被代理。
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/24
 * @see ClassFilter
 * @see MethodMatcher
 */
public interface Pointcut {

    /**
     * 获取类过滤器
     * @return 类过滤器
     */
    ClassFilter getClassFilter();

    /**
     * 获取方法匹配器
     * @return 方法匹配器
     */
    MethodMatcher getMethodMatcher();
}
