package org.winterframework.beans.factory.support;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.winterframework.beans.BeanException;
import org.winterframework.beans.factory.config.BeanDefinition;

/**
 * Cglib子类代理实例化策略
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/12
 * @description 使用Cglib动态字节码技术创建Bean的子类代理对象
 *              Cglib采用继承的方式，在运行时动态生成目标类的子类
 *              可以拦截父类的方法调用，支持AOP等高级特性
 *              
 * 优点：
 * - 支持方法拦截，可以在方法执行前后添加增强逻辑
 * - 不需要实现接口，直接对类进行代理
 * - 为后续实现AOP提供基础
 * 
 * 缺点：
 * - 需要引入Cglib依赖
 * - 性能略低于直接反射
 * - 无法代理final类和final方法
 * 
 * 适用场景：
 * - 需要AOP支持的Bean
 * - 需要方法拦截的场景
 * - Spring默认的代理方式之一（对于没有接口的类）
 */
public class CglibSubclassInstantiationStrategy implements InstantiationStrategy{
    /**
     * 使用Cglib动态生成目标类的子类
     * 通过Enhancer创建代理对象，支持方法拦截
     * 
     * @param beanDefinition Bean定义信息
     * @return Cglib代理对象（目标类的子类实例）
     * @throws BeanException 如果代理创建失败
     */
    @Override
    public Object instantiate(BeanDefinition beanDefinition) throws BeanException {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(beanDefinition.getBeanClass());
        enhancer.setCallback((MethodInterceptor)(obj,method,argsTemp,proxy) -> proxy.invoke(obj,argsTemp));
        return enhancer.create();
    }
}