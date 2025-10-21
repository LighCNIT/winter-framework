package org.winterframework.context.event;

import org.winterframework.context.ApplicationContext;

/**
 * 容器关闭事件
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/21
 * @description 当ApplicationContext关闭时发布的事件，表示容器即将销毁
 * 
 * <p>ContextClosedEvent是Winter框架中重要的生命周期事件之一，它在
 * ApplicationContext关闭时发布。此时容器开始销毁过程，但Bean可能还未完全销毁。</p>
 * 
 * <p>发布时机：</p>
 * <ul>
 *   <li>在ApplicationContext.close()方法调用时发布</li>
 *   <li>在容器开始销毁过程时发布</li>
 *   <li>在JVM关闭钩子执行时发布</li>
 * </ul>
 * 
 * <p>使用场景：</p>
 * <ul>
 *   <li>执行容器关闭前的清理逻辑</li>
 *   <li>停止定时任务或后台服务</li>
 *   <li>保存应用状态或数据</li>
 *   <li>发送容器关闭通知</li>
 *   <li>执行资源清理</li>
 * </ul>
 * 
 * <p>监听器示例：</p>
 * <pre>{@code
 * public class MyContextClosedListener implements ApplicationListener<ContextClosedEvent> {
 *     @Override
 *     public void onApplicationEvent(ContextClosedEvent event) {
 *         ApplicationContext context = event.getApplicationContext();
 *         // 执行容器关闭前的清理逻辑
 *         System.out.println("容器即将关闭，开始执行清理任务...");
 *     }
 * }
 * }</pre>
 * 
 * <p>注意事项：</p>
 * <ul>
 *   <li>此事件在容器开始关闭时发布，此时Bean可能还未完全销毁</li>
 *   <li>监听器中的异常不会影响容器的正常关闭</li>
 *   <li>建议在监听器中执行快速的清理逻辑</li>
 *   <li>避免在监听器中执行耗时操作</li>
 * </ul>
 * 
 * @see ApplicationContextEvent
 * @see ApplicationContext#close()
 * @see AbstractApplicationContext#close()
 */
public class ContextClosedEvent extends ApplicationContextEvent {
    
    /**
     * 构造容器关闭事件
     * 
     * <p>创建一个表示ApplicationContext关闭的事件实例。</p>
     * 
     * @param source 发布此事件的ApplicationContext实例，不能为null
     */
    public ContextClosedEvent(ApplicationContext source) {
        super(source);
    }
}