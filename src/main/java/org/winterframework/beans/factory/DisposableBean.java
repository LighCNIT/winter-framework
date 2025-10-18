package org.winterframework.beans.factory;


/**
 * Bean销毁回调接口
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/18
 * @description 用于在Bean销毁前执行自定义清理逻辑的接口
 * 
 * <p>DisposableBean接口是Spring框架中发布的一个重要的生命周期接口，
 * 它允许Bean在容器销毁时执行自定义的清理逻辑。</p>
 * 
 * <p>使用场景：</p>
 * <ul>
 *   <li>关闭数据库连接</li>
 *   <li>停止后台线程或定时任务</li>
 *   <li>释放外部资源</li>
 *   <li>保存数据到持久化存储</li>
 *   <li>执行任何需要在Bean销毁前执行的清理工作</li>
 * </ul>
 * 
 * <p>执行时机：在ApplicationContext关闭时，按照Bean依赖关系的逆序执行</p>
 * 
 * <p>使用示例：</p>
 * <pre>{@code
 * public class DatabaseService implements DisposableBean {
 *     private Connection connection;
 *     
 *     @Override
 *     public void destroy() throws Exception {
 *         if (connection != null && !connection.isClosed()) {
 *             connection.close();
 *             System.out.println("Database connection closed successfully");
 *         }
 *     }
 * }
 * }</pre>
 * 
 * <p>注意事项：</p>
 * <ul>
 *   <li>此方法会在ApplicationContext关闭时自动调用</li>
 *   <li>如果清理失败，会抛出Exception</li>
 *   <li>建议在XML配置中使用destroy-method属性替代实现此接口</li>
 *   <li>如果同时实现了此接口和配置了destroy-method，会按顺序执行</li>
 *   <li>销毁方法的执行顺序与Bean的创建顺序相反</li>
 * </ul>
 * 
 * @see InitializingBean
 * @see DisposableBeanAdapter
 * @see BeanDefinition#setDestroyMethodName(String)
 */
public interface DisposableBean {

    /**
     * 在Bean销毁前执行清理逻辑
     * 
     * <p>这个方法会在Bean被容器销毁之前被调用，
     * 此时Bean仍然可用，可以安全地访问其属性和执行清理工作。</p>
     * 
     * <p>典型的清理工作包括：</p>
     * <ul>
     *   <li>关闭数据库连接</li>
     *   <li>停止后台线程或定时任务</li>
     *   <li>释放外部资源（文件句柄、网络连接等）</li>
     *   <li>保存数据到持久化存储</li>
     *   <li>执行任何需要在Bean销毁前执行的清理工作</li>
     * </ul>
     * 
     * @throws Exception 如果清理过程中发生错误
     */
    void destroy() throws Exception;
}
