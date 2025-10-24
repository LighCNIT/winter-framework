package org.winterframework.aop.framework;

/**
 * AOP代理接口，定义了创建代理对象的统一规范
 * 
 * <p>该接口是AOP框架中代理创建的抽象，定义了获取代理对象的标准方法。
 * 框架提供了两种代理实现：
 * <ul>
 *   <li>JDK动态代理：基于接口的代理，适用于有接口的目标类</li>
 *   <li>CGLIB代理：基于继承的代理，适用于没有接口的目标类</li>
 * </ul>
 * 
 * <p>代理对象会在运行时拦截目标方法的调用，并根据配置的切点
 * 和通知来决定是否执行横切逻辑。
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/24
 * @see JdkDynamicAopProxy
 * @see CglibAopProxy
 */
public interface AopProxy {

    /**
     * 获取代理对象
     * 
     * @return 代理对象，该对象会拦截目标方法的调用
     */
    Object getProxy();
}
