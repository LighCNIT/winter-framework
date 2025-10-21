package org.winterframework.test.ioc.common.event;

import org.winterframework.context.ApplicationListener;

/**
 * 自定义事件监听器示例
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/21
 * @description 用于演示Winter框架事件监听机制的自定义监听器
 * 
 * <p>CustomEventListener是Winter框架事件系统的示例监听器类，用于演示如何
 * 创建和注册事件监听器。它实现了ApplicationListener接口，专门监听CustomEvent事件。</p>
 * 
 * <p>主要功能：</p>
 * <ul>
 *   <li>监听CustomEvent类型的自定义事件</li>
 *   <li>在事件发生时执行相应的处理逻辑</li>
 *   <li>演示事件监听器的基本实现模式</li>
 * </ul>
 * 
 * <p>注册方式：</p>
 * <ul>
 *   <li>XML配置：&lt;bean class="CustomEventListener"/&gt;</li>
 *   <li>注解配置：@Component + implements ApplicationListener</li>
 *   <li>程序化注册：applicationContext.addApplicationListener(listener)</li>
 * </ul>
 * 
 * <p>使用场景：</p>
 * <ul>
 *   <li>业务事件处理</li>
 *   <li>系统状态监控</li>
 *   <li>日志记录</li>
 *   <li>通知发送</li>
 * </ul>
 * 
 * @see ApplicationListener
 * @see CustomEvent
 * @see ApplicationEvent
 */
public class CustomEventListener implements ApplicationListener<CustomEvent> {
    
    /**
     * 处理自定义事件
     * 
     * <p>当CustomEvent事件被发布时，此方法会被自动调用。
     * 在此方法中实现具体的事件处理逻辑。</p>
     * 
     * @param event 要处理的CustomEvent事件对象
     */
    @Override
    public void onApplicationEvent(CustomEvent event) {
        System.out.println(this.getClass().getName());
    }
}