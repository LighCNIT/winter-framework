package org.winterframework.aop;

public interface PointcutAdvisor extends Advisor{

    Pointcut getPointcut();
}
