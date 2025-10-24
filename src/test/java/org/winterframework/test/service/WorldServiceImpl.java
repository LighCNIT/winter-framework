package org.winterframework.test.service;

/**
 * 世界服务实现类，用于AOP功能演示
 * 
 * <p>该类实现了WorldService接口，提供了具体的业务逻辑实现。
 * 在AOP测试中，该类的实例会被作为目标对象进行代理，以验证
 * Winter框架的AOP功能是否能够正确拦截方法调用并执行横切逻辑。
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/24
 * @see WorldService
 */
public class WorldServiceImpl implements WorldService{
    
    /**
     * 爆炸方法的具体实现
     * 
     * <p>该方法在AOP测试中会被代理拦截，在方法执行前后会添加相应的横切逻辑。
     * 当方法被调用时，会打印"The Earth is going to explode"信息。
     */
    @Override
    public void explode() {
        System.out.println("The Earth is going to explode");
    }
}