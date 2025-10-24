package org.winterframework.aop.aspectj;

import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;
import org.winterframework.aop.ClassFilter;
import org.winterframework.aop.MethodMatcher;
import org.winterframework.aop.Pointcut;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * AspectJ表达式切点实现类
 * 
 * <p>该类实现了AspectJ表达式语法的切点匹配功能，支持使用AspectJ的切点表达式
 * 来定义需要被代理的方法。目前支持execution表达式，可以精确匹配方法签名。
 * 
 * <p>该类同时实现了Pointcut、ClassFilter和MethodMatcher接口，提供了完整的
 * 切点匹配功能。通过AspectJ的PointcutParser解析表达式，并在运行时进行匹配。
 * 
 * <p>支持的表达式示例：
 * <ul>
 *   <li>execution(* org.example.service.*.*(..)) - 匹配org.example.service包下所有类的所有方法</li>
 *   <li>execution(* org.example.service.UserService.save(..)) - 匹配UserService的save方法</li>
 *   <li>execution(public * org.example.service.*.save*(..)) - 匹配public的save开头的方法</li>
 * </ul>
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/23
 * @see Pointcut
 * @see ClassFilter
 * @see MethodMatcher
 */
public class AspectJExpressionPointcut implements Pointcut, ClassFilter, MethodMatcher {

    /** 支持的切点原语集合，目前只支持EXECUTION */
    private static final Set<PointcutPrimitive> SUPPORTED_PRIMITIVES = new HashSet<PointcutPrimitive>();
    static {
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.EXECUTION);
    }
    
    /** 解析后的切点表达式 */
    private final PointcutExpression pointcutExpression;

    /**
     * 构造函数
     * @param expression AspectJ切点表达式
     */
    public AspectJExpressionPointcut(String expression) {
        PointcutParser pointcutParser = PointcutParser.getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution(SUPPORTED_PRIMITIVES, this.getClass().getClassLoader());
        pointcutExpression = pointcutParser.parsePointcutExpression(expression);
    }

    /**
     * 判断指定类是否匹配切点条件
     * 
     * @param clazz 要检查的类
     * @return 如果类匹配切点条件返回true，否则返回false
     */
    @Override
    public boolean matches(Class<?> clazz) {
        return pointcutExpression.couldMatchJoinPointsInType(clazz);
    }

    /**
     * 判断指定方法是否匹配切点条件
     * 
     * @param method 要检查的方法
     * @param targetClass 目标类
     * @return 如果方法匹配切点条件返回true，否则返回false
     */
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return pointcutExpression.matchesMethodExecution(method).alwaysMatches();
    }

    /**
     * 获取类过滤器（返回自身）
     * @return 类过滤器
     */
    @Override
    public ClassFilter getClassFilter() {
        return this;
    }

    /**
     * 获取方法匹配器（返回自身）
     * @return 方法匹配器
     */
    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }
}
