package org.winterframework.test.ioc.common.event;

import org.winterframework.context.ApplicationListener;
import org.winterframework.context.event.ContextClosedEvent;

/**
 * 容器关闭事件监听器示例
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/21
 * @description 用于演示监听容器关闭事件的监听器
 * 
 * <p>ContextClosedEventListener是Winter框架事件系统的示例监听器类，
 * 专门监听ContextClosedEvent事件。当ApplicationContext关闭时，
 * 此监听器会被自动调用。</p>
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>监听容器关闭事件</li>
 *   <li>在容器关闭前执行清理逻辑</li>
 *   <li>演示容器生命周期事件的处理</li>
 * </ul>
 * 
 * <p>触发时机：</p>
 * <ul>
 *   <li>ApplicationContext.close()方法调用时</li>
 *   <li>容器开始销毁过程时</li>
 *   <li>JVM关闭钩子执行时</li>
 * </ul>
 * 
 * <p>使用场景：</p>
 * <ul>
 *   <li>执行容器关闭前的清理任务</li>
 *   <li>停止定时任务或后台服务</li>
 *   <li>保存应用状态或数据</li>
 *   <li>发送容器关闭通知</li>
 * </ul>
 * 
 * @see ApplicationListener
 * @see ContextClosedEvent
 * @see ApplicationContext
 */
public class ContextClosedEventListener implements ApplicationListener<ContextClosedEvent> {
    
    /**
     * 处理容器关闭事件
     * 
     * <p>当ApplicationContext关闭时，此方法会被自动调用。
     * 此时容器开始销毁过程，建议执行快速的清理逻辑。</p>
     * 
     * @param event 要处理的ContextClosedEvent事件对象
     */
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println(this.getClass().getName());
    }
}