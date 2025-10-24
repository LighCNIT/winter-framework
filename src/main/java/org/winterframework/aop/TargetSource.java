package org.winterframework.aop;

/**
 * 目标对象源，封装被代理的目标对象信息
 * 
 * <p>该类用于封装AOP代理的目标对象，提供获取目标对象和目标类接口的方法。
 * 在创建代理对象时，需要知道目标对象的具体类型和实现的接口。
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/24
 */
public class TargetSource {

    /** 被代理的目标对象 */
    private final Object target;

    /**
     * 构造函数
     * @param target 被代理的目标对象
     */
    public TargetSource(Object target) {
        this.target = target;
    }

    /**
     * 获取目标对象实现的接口数组
     * @return 目标对象实现的接口数组
     */
    public Class<?>[] getTargetClass(){
        return this.target.getClass().getInterfaces();
    }

    /**
     * 获取目标对象
     * @return 目标对象
     */
    public Object getTarget() {
        return this.target;
    }
}