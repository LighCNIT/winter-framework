package org.winterframework.test.common;

import org.winterframework.aop.MethodAfterReturningAdvice;

import java.lang.reflect.Method;

/**
 * @author Ligh
 * 2025/10/25 11:35
 **/
public class WorldServiceAfterReturningAdvice implements MethodAfterReturningAdvice {
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("AfterReturningAdvice: do something after the earth explodes return");
    }
}
