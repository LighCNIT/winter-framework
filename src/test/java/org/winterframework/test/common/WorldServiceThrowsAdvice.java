package org.winterframework.test.common;

import org.winterframework.aop.MethodThrowsAdvice;

import java.lang.reflect.Method;

/**
 * @author Ligh
 * 2025/10/25 11:36
 **/
public class WorldServiceThrowsAdvice implements MethodThrowsAdvice {
    @Override
    public void throwsHandle(Throwable throwable, Method method, Object[] args, Object target) {
        System.out.println("ThrowsAdvice: do something when the earth explodes function throw an exception");
    }
}
