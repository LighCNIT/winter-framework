package org.winterframework.test.common;

import org.winterframework.aop.MethodAfterAdvice;

import java.lang.reflect.Method;

/**
 * @author Ligh
 * 2025/10/25 11:34
 **/
public class WorldServiceAfterAdvice implements MethodAfterAdvice {
    @Override
    public void after(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("AfterAdvice: do something after the earth explodes");
    }
}
