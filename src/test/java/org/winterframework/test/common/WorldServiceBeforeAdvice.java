package org.winterframework.test.common;

import org.winterframework.aop.BeforeAdvice;

import java.lang.reflect.Method;

/**
 * @author Ligh
 * 2025/10/25 10:22
 **/
public class WorldServiceBeforeAdvice implements BeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("BeforeAdvice: do something before the earth explodes");
    }
}
