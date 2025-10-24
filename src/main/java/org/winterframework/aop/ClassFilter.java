package org.winterframework.aop;

/**
 * 类过滤器接口，用于判断指定类是否需要被代理
 * 
 * <p>该接口是AOP框架中切点（Pointcut）的核心组件之一，用于在类级别
 * 判断某个类是否匹配切点表达式，从而决定是否需要对该类进行代理。
 * 
 * <p>实现类可以根据类名、包名、注解、继承关系等多种条件进行匹配。
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/24
 * @see Pointcut
 */
public interface ClassFilter {

    /**
     * 判断指定类是否匹配切点条件
     * 
     * @param clazz 要检查的类
     * @return 如果类匹配切点条件返回true，否则返回false
     */
    boolean matches(Class<?> clazz);
}
