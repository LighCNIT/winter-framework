package org.winterframework.test.common.event;

import org.winterframework.context.ApplicationListener;
import org.winterframework.context.event.ContextRefreshedEvent;

/**
 * 容器刷新完成事件监听器示例
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/21
 * @description 用于演示监听容器生命周期事件的监听器
 * 
 * <p>ContextRefreshedEventListener是Winter框架事件系统的示例监听器类，
 * 专门监听ContextRefreshedEvent事件。当ApplicationContext刷新完成时，
 * 此监听器会被自动调用。</p>
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>监听容器刷新完成事件</li>
 *   <li>在容器准备就绪后执行初始化逻辑</li>
 *   <li>演示容器生命周期事件的处理</li>
 * </ul>
 * 
 * <p>触发时机：</p>
 * <ul>
 *   <li>ApplicationContext.refresh()方法执行完成后</li>
 *   <li>所有单例Bean实例化完成后</li>
 *   <li>容器完全准备就绪后</li>
 * </ul>
 * 
 * <p>使用场景：</p>
 * <ul>
 *   <li>执行容器启动后的初始化任务</li>
 *   <li>启动定时任务或后台服务</li>
 *   <li>执行数据初始化或缓存预热</li>
 *   <li>发送容器就绪通知</li>
 * </ul>
 * 
 * @see ApplicationListener
 * @see ContextRefreshedEvent
 * @see ApplicationContext
 */
public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {
    
    /**
     * 处理容器刷新完成事件
     * 
     * <p>当ApplicationContext刷新完成时，此方法会被自动调用。
     * 此时容器已完全初始化，所有Bean都已准备就绪。</p>
     * 
     * @param event 要处理的ContextRefreshedEvent事件对象
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println(this.getClass().getName());
    }
}