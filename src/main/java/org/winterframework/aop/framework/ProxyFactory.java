package org.winterframework.aop.framework;

import org.winterframework.aop.AdvisedSupport;

/**
 * @author Ligh
 * 2025/10/25 9:42
 **/
public class ProxyFactory {

    private  AdvisedSupport advisedSupport;

    public ProxyFactory(AdvisedSupport advisedSupport) {
        this.advisedSupport = advisedSupport;
    }

    public Object getProxy(){
        return this.createAopProxy().getProxy();
    }

    private AopProxy createAopProxy(){
        if (advisedSupport.isProxyTargetClass()){
            return new CglibAopProxy(advisedSupport);
        }
        return new JdkDynamicAopProxy(advisedSupport);
    }
}
