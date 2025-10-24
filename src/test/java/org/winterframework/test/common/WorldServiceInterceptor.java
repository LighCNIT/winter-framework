package org.winterframework.test.common;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.Method;

/**
 * WorldService方法拦截器，用于演示AOP横切逻辑
 * 
 * <p>该类实现了AOP联盟的MethodInterceptor接口，用于在目标方法执行前后
 * 添加横切关注点。在AOP测试中，该拦截器会在WorldService.explode()方法
 * 执行前后打印相应的日志信息。
 * 
 * <p>拦截器执行流程：
 * <ol>
 *   <li>执行前置逻辑（打印"before"日志）</li>
 *   <li>调用目标方法（methodInvocation.proceed()）</li>
 *   <li>执行后置逻辑（打印"after"日志）</li>
 *   <li>返回方法执行结果</li>
 * </ol>
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/24
 * @see org.aopalliance.intercept.MethodInterceptor
 * @see org.aopalliance.intercept.MethodInvocation
 */
public class WorldServiceInterceptor implements MethodInterceptor {

    /**
     * 拦截方法调用，在目标方法执行前后添加横切逻辑
     * 
     * @param methodInvocation 方法调用上下文，包含目标对象、方法、参数等信息
     * @return 目标方法的执行结果
     * @throws Throwable 目标方法执行过程中抛出的异常
     */
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        System.out.println("Do something before the earth explodes");
        Object result = methodInvocation.proceed();
        System.out.println("Do something after the earth explodes");
        return result;
    }
}