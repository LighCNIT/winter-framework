package org.winterframework.aop;

import java.lang.reflect.Method;

public interface MethodAfterReturningAdvice extends AfterReturningAdvice{
    void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable;
}
