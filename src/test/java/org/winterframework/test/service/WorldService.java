package org.winterframework.test.service;

/**
 * 世界服务接口，用于AOP功能演示
 * 
 * <p>该接口定义了世界相关的服务方法，主要用于演示Winter框架的AOP功能。
 * 在AOP测试中，该接口的实现类会被代理，以验证横切逻辑是否能够正确执行。
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/24
 * @see WorldServiceImpl
 */
public interface WorldService {

    /**
     * 爆炸方法，用于演示AOP横切逻辑
     * 
     * <p>该方法在AOP测试中会被代理，在方法执行前后会添加相应的横切逻辑。
     * 预期行为：打印"The Earth is going to explode"信息。
     */
    void explode();
}