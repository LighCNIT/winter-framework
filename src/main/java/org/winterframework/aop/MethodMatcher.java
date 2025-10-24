package org.winterframework.aop;

import java.lang.reflect.Method;

/**
 * 方法匹配器接口，用于判断指定方法是否需要被代理
 * 
 * <p>该接口是AOP框架中切点（Pointcut）的核心组件之一，用于在运行时
 * 判断某个方法是否匹配切点表达式，从而决定是否需要对该方法进行代理。
 * 
 * <p>实现类可以根据方法名、参数类型、注解、类名等多种条件进行匹配。
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/24
 * @see Pointcut
 */
public interface MethodMatcher {

    /**
     * 判断指定方法是否匹配切点条件
     * 
     * @param method 要检查的方法
     * @param targetClass 目标类
     * @return 如果方法匹配切点条件返回true，否则返回false
     */
    boolean matches(Method method, Class<?> targetClass);
}
