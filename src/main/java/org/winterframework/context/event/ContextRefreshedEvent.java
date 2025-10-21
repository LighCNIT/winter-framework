package org.winterframework.context.event;

import org.winterframework.context.ApplicationContext;

/**
 * 容器刷新完成事件
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/21
 * @description 当ApplicationContext刷新完成时发布的事件，表示容器已准备就绪
 * 
 * <p>ContextRefreshedEvent是Winter框架中最重要的生命周期事件之一，它在
 * ApplicationContext的refresh()方法执行完成后发布。此时容器已经完全初始化，
 * 所有单例Bean都已创建完成，容器处于可用状态。</p>
 * 
 * <p>发布时机：</p>
 * <ul>
 *   <li>在AbstractApplicationContext.finishRefresh()方法中发布</li>
 *   <li>在所有Bean实例化完成后发布</li>
 *   <li>在容器完全准备就绪后发布</li>
 * </ul>
 * 
 * <p>使用场景：</p>
 * <ul>
 *   <li>执行容器启动后的初始化逻辑</li>
 *   <li>启动定时任务或后台服务</li>
 *   <li>执行数据初始化或缓存预热</li>
 *   <li>发送容器就绪通知</li>
 *   <li>执行健康检查</li>
 * </ul>
 * 
 * <p>监听器示例：</p>
 * <pre>{@code
 * public class MyContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {
 *     @Override
 *     public void onApplicationEvent(ContextRefreshedEvent event) {
 *         ApplicationContext context = event.getApplicationContext();
 *         // 执行容器就绪后的初始化逻辑
 *         System.out.println("容器已准备就绪，开始执行初始化任务...");
 *     }
 * }
 * }</pre>
 * 
 * <p>注意事项：</p>
 * <ul>
 *   <li>此事件在容器完全初始化后发布，可以安全地访问所有Bean</li>
 *   <li>监听器中的异常不会影响容器的正常启动</li>
 *   <li>建议在监听器中执行轻量级的初始化逻辑</li>
 * </ul>
 * 
 * @see ApplicationContextEvent
 * @see ApplicationContext#refresh()
 * @see AbstractApplicationContext#finishRefresh()
 */
public class ContextRefreshedEvent extends ApplicationContextEvent{
    
    /**
     * 构造容器刷新完成事件
     * 
     * <p>创建一个表示ApplicationContext刷新完成的事件实例。</p>
     * 
     * @param source 发布此事件的ApplicationContext实例，不能为null
     */
    public ContextRefreshedEvent(ApplicationContext source) {
        super(source);
    }
}