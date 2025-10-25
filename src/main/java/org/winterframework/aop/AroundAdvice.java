package org.winterframework.aop;

import org.aopalliance.intercept.MethodInvocation;

/**
 * 环绕通知
 */
public interface AroundAdvice {

    Object around(MethodInvocation invocation) throws Throwable;
}
