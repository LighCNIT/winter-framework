package org.winterframework.test.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.junit.Before;
import org.junit.Test;
import org.winterframework.aop.*;
import org.winterframework.aop.aspectj.AspectJExpressionPointcut;
import org.winterframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.winterframework.aop.framework.CglibAopProxy;
import org.winterframework.aop.framework.JdkDynamicAopProxy;
import org.winterframework.aop.framework.ProxyFactory;
import org.winterframework.aop.framework.adapter.MethodBeforeAdviceInterceptor;
import org.winterframework.test.common.WorldServiceBeforeAdvice;
import org.winterframework.test.common.WorldServiceInterceptor;
import org.winterframework.test.service.WorldService;
import org.winterframework.test.service.WorldServiceImpl;

/**
 * AOP动态代理测试类
 * 
 * <p>该类用于测试Winter框架的AOP功能，包括JDK动态代理和CGLIB代理。
 * 测试场景：对WorldService的explode方法进行代理，在方法执行前后添加横切逻辑。
 * 
 * <p>测试流程：
 * <ol>
 *   <li>创建目标对象WorldServiceImpl</li>
 *   <li>配置AOP信息（目标对象、拦截器、切点表达式）</li>
 *   <li>创建代理对象（JDK或CGLIB）</li>
 *   <li>调用代理方法，验证横切逻辑是否生效</li>
 * </ol>
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/24
 */
public class DynamicProxyTest {

    /** AOP配置信息 */
    private AdvisedSupport advisedSupport;

    /**
     * 测试前置设置，配置AOP代理所需的所有组件
     */
    @Before
    public void setup() {
        // 创建目标对象
        WorldService worldService = new WorldServiceImpl();

        // 配置AOP信息
        advisedSupport = new AdvisedSupport();
        TargetSource targetSource = new TargetSource(worldService);
        WorldServiceInterceptor methodInterceptor = new WorldServiceInterceptor();
        MethodMatcher methodMatcher = new AspectJExpressionPointcut("execution(* org.winterframework.test.service.WorldService.explode(..))").getMethodMatcher();
        
        advisedSupport.setTargetSource(targetSource);
        advisedSupport.setMethodInterceptor(methodInterceptor);
        advisedSupport.setMethodMatcher(methodMatcher);
    }

    /**
     * 测试JDK动态代理
     * 
     * <p>验证JDK动态代理能够正确拦截目标方法并执行横切逻辑。
     * 预期输出：
     * <pre>
     * Do something before the earth explodes
     * The Earth is going to explode
     * Do something after the earth explodes
     * </pre>
     * 
     * @throws Exception 测试过程中可能抛出的异常
     */
    @Test
    public void testJdkDynamicProxy() throws Exception {
        WorldService proxy = (WorldService) new JdkDynamicAopProxy(advisedSupport).getProxy();
        proxy.explode();
    }

    /**
     * 测试CGLIB动态代理
     * 
     * <p>验证CGLIB动态代理能够正确拦截目标方法并执行横切逻辑。
     * 预期输出：
     * <pre>
     * Do something before the earth explodes
     * The Earth is going to explode
     * Do something after the earth explodes
     * </pre>
     * 
     * @throws Exception 测试过程中可能抛出的异常
     */
    @Test
    public void testCglibDynamicProxy() throws Exception {
        WorldService proxy = (WorldService) new CglibAopProxy(advisedSupport).getProxy();
        proxy.explode();
    }

    @Test
    public void testProxyFactory(){
        advisedSupport.setProxyTargetClass(false);
        WorldService proxy = (WorldService) new ProxyFactory(advisedSupport).getProxy();
        proxy.explode();

        advisedSupport.setProxyTargetClass(true);
        proxy = (WorldService) new ProxyFactory(advisedSupport).getProxy();
        proxy.explode();
    }

    @Test
    public void testBeforeAdvice() throws Exception {
        //设置BeforeAdvice
        WorldServiceBeforeAdvice beforeAdvice = new WorldServiceBeforeAdvice();
        MethodBeforeAdviceInterceptor methodInterceptor = new MethodBeforeAdviceInterceptor(new WorldServiceBeforeAdvice());
        advisedSupport.setMethodInterceptor(methodInterceptor);

        WorldService proxy = (WorldService) new ProxyFactory(advisedSupport).getProxy();
        proxy.explode();
    }

    @Test
    public void testAdvisor() throws Exception {
        WorldService worldService = new WorldServiceImpl();

        //Advisor是Pointcut和Advice的组合
        String expression = "execution(* org.winterframework.test.service.WorldService.explode(..))";
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression(expression);
        MethodBeforeAdviceInterceptor methodInterceptor = new MethodBeforeAdviceInterceptor(new WorldServiceBeforeAdvice());
        advisor.setAdvice(methodInterceptor);

        ClassFilter classFilter = advisor.getPointcut().getClassFilter();
        if (classFilter.matches(worldService.getClass())) {
            AdvisedSupport advisedSupport = new AdvisedSupport();

            TargetSource targetSource = new TargetSource(worldService);
            advisedSupport.setTargetSource(targetSource);
            advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
            advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
//			advisedSupport.setProxyTargetClass(true);   //JDK or CGLIB

            WorldService proxy = (WorldService) new ProxyFactory(advisedSupport).getProxy();
            proxy.explode();
        }
    }
}