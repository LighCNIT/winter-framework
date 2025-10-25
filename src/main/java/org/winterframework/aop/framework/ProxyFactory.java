package org.winterframework.aop.framework;

import org.winterframework.aop.AdvisedSupport;

/**
 * 代理工厂类，用于创建AOP代理对象
 * 
 * <p>该类是AOP框架中代理创建的工厂类，根据配置信息自动选择合适的代理实现。
 * 支持两种代理类型：
 * <ul>
 *   <li>JDK动态代理：适用于有接口的目标类，性能较好</li>
 *   <li>CGLIB代理：适用于没有接口的目标类，功能更强大</li>
 * </ul>
 * 
 * <p>代理工厂会根据AdvisedSupport中的配置信息，自动判断使用哪种代理实现：
 * <ul>
 *   <li>如果设置了proxyTargetClass为true，使用CGLIB代理</li>
 *   <li>否则使用JDK动态代理（需要目标类实现接口）</li>
 * </ul>
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/25
 * @see AdvisedSupport
 * @see AopProxy
 * @see JdkDynamicAopProxy
 * @see CglibAopProxy
 */
public class ProxyFactory {

    /** AOP配置信息 */
    private AdvisedSupport advisedSupport;

    /**
     * 构造函数
     * @param advisedSupport AOP配置信息
     */
    public ProxyFactory(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    /**
     * 创建代理对象
     * 
     * @return 代理对象，会根据配置自动选择合适的代理实现
     */
    public Object getProxy(){
        return this.createAopProxy().getProxy();
    }

    /**
     * 根据配置创建AOP代理实现
     * 
     * @return AOP代理实现对象
     */
    private AopProxy createAopProxy(){
        if (advisedSupport.isProxyTargetClass()){
            // 强制使用CGLIB代理
            return new CglibAopProxy(advisedSupport);
        }
        // 默认使用JDK动态代理
        return new JdkDynamicAopProxy(advisedSupport);
    }
}
