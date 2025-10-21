package org.winterframework.context;

/**
 * 应用事件发布者接口
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/21
 * @description 用于发布应用事件的接口，是事件驱动架构的核心组件
 * 
 * <p>ApplicationEventPublisher是Winter框架中事件发布机制的核心接口，它定义了
 * 发布应用事件的标准方法。通过此接口，任何组件都可以发布事件，实现松耦合的
 * 组件间通信。</p>
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>提供统一的事件发布接口</li>
 *   <li>支持异步和同步事件发布</li>
 *   <li>与ApplicationEventMulticaster协作进行事件分发</li>
 * </ul>
 * 
 * <p>使用场景：</p>
 * <ul>
 *   <li>容器生命周期事件发布（如ContextRefreshedEvent）</li>
 *   <li>业务事件发布（如用户注册、订单创建等）</li>
 *   <li>系统状态变化通知</li>
 *   <li>实现观察者模式</li>
 * </ul>
 * 
 * <p>实现示例：</p>
 * <pre>{@code
 * public class MyService implements ApplicationEventPublisherAware {
 *     private ApplicationEventPublisher eventPublisher;
 *     
 *     public void doSomething() {
 *         // 发布自定义事件
 *         eventPublisher.publishEvent(new CustomEvent("业务处理完成"));
 *     }
 * }</pre>
 * 
 * <p>事件发布流程：</p>
 * <ol>
 *   <li>调用publishEvent方法发布事件</li>
 *   <li>ApplicationEventMulticaster接收事件</li>
 *   <li>查找匹配的ApplicationListener</li>
 *   <li>调用监听器的onApplicationEvent方法</li>
 * </ol>
 * 
 * @see ApplicationEvent
 * @see ApplicationListener
 * @see ApplicationEventMulticaster
 * @see ApplicationContext
 */
public interface ApplicationEventPublisher {

    /**
     * 发布应用事件
     * 
     * <p>发布一个应用事件，框架会自动查找并通知所有匹配的监听器。
     * 事件发布是同步的，监听器的执行顺序可能不确定。</p>
     * 
     * <p>事件匹配规则：</p>
     * <ul>
     *   <li>监听器泛型类型与事件类型完全匹配</li>
     *   <li>监听器泛型类型是事件类型的父类</li>
     *   <li>监听器监听ApplicationEvent（监听所有事件）</li>
     * </ul>
     * 
     * @param event 要发布的事件对象，不能为null
     * @throws IllegalArgumentException 如果event为null
     */
    void publishEvent(ApplicationEvent event);
}
