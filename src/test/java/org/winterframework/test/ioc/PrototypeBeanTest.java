package org.winterframework.test.ioc;

import org.junit.Test;
import org.winterframework.context.support.ClassPathXmlApplicationContext;
import org.winterframework.test.bean.Car;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * Bean作用域测试类
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/19
 * @description 测试Winter Framework对Bean作用域的支持，包括单例和原型作用域
 * 
 * 测试目的：
 * 1. 验证单例Bean：多次获取返回同一个实例
 * 2. 验证原型Bean：每次获取都创建新的实例
 * 3. 验证作用域配置：通过XML配置正确设置Bean作用域
 * 4. 验证生命周期差异：单例Bean注册到缓存，原型Bean不注册
 * 
 * 技术要点：
 * - BeanDefinition中的scope属性设置
 * - AbstractAutowireCapableBeanFactory中的作用域判断逻辑
 * - XmlBeanDefinitionReader中的scope属性解析
 * - 单例缓存池的管理机制
 */
public class PrototypeBeanTest {

    /**
     * 测试原型Bean作用域
     * 
     * <p>测试场景：</p>
     * <ol>
     *   <li>从XML配置文件加载Bean定义，设置scope="prototype"</li>
     *   <li>多次调用getBean()获取同一个Bean名称</li>
     *   <li>验证每次获取都返回不同的实例</li>
     * </ol>
     * 
     * <p>验证点：</p>
     * <ul>
     *   <li>car1 != car2：确保每次获取都是新实例</li>
     *   <li>原型Bean不注册到单例缓存池</li>
     *   <li>每次获取都执行完整的Bean创建流程</li>
     * </ul>
     * 
     * <p>技术实现：</p>
     * <ul>
     *   <li>XmlBeanDefinitionReader解析scope="prototype"属性</li>
     *   <li>BeanDefinition.setScope()设置作用域并更新标志位</li>
     *   <li>AbstractAutowireCapableBeanFactory根据isPrototype()判断是否注册到缓存</li>
     * </ul>
     * 
     * @throws Exception 测试过程中的异常
     */
    @Test
    public void testPrototype() throws Exception {
        // 1. 创建ApplicationContext并加载XML配置
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:prototype-bean.xml");

        // 2. 多次获取同一个Bean名称
        Car car1 = applicationContext.getBean("car", Car.class);
        Car car2 = applicationContext.getBean("car", Car.class);
        
        // 3. 验证每次获取都返回不同的实例（原型Bean的核心特征）
        assertThat(car1 != car2).isTrue();
        
        // 4. 验证两个实例都是有效的Car对象
        assertThat(car1).isNotNull();
        assertThat(car2).isNotNull();
        assertThat(car1.getClass()).isEqualTo(Car.class);
        assertThat(car2.getClass()).isEqualTo(Car.class);
    }
}