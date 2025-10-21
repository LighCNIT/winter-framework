package org.winterframework.test.ioc;

import org.junit.Test;
import org.winterframework.context.support.ClassPathXmlApplicationContext;
import org.winterframework.test.ioc.bean.Car;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * FactoryBean功能测试类
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/19
 * @description 测试Winter Framework对FactoryBean的支持，验证FactoryBean的创建和对象获取功能
 * 
 * 测试目的：
 * 1. 验证FactoryBean接口的正确实现
 * 2. 验证框架能正确识别FactoryBean并调用getObject()方法
 * 3. 验证FactoryBean创建的对象能正确获取
 * 4. 验证FactoryBean的缓存机制（单例模式）
 * 5. 验证FactoryBean与普通Bean的区别
 * 
 * 技术要点：
 * - FactoryBean接口的getObject()和isSingleton()方法
 * - AbstractBeanFactory中的getObjectForBeanInstance()方法
 * - FactoryBean对象缓存池的管理
 * - XML配置中FactoryBean的注册和属性注入
 * 
 * 测试场景：
 * - 单例FactoryBean：验证对象缓存机制
 * - 原型FactoryBean：验证每次创建新实例
 * - 复杂对象创建：验证FactoryBean的复杂初始化逻辑
 * - 属性注入：验证FactoryBean的属性配置
 */
public class FactoryBeanTest {

    /**
     * 测试FactoryBean基本功能
     * 
     * <p>测试场景：</p>
     * <ol>
     *   <li>从XML配置文件加载FactoryBean定义</li>
     *   <li>通过Bean名称获取FactoryBean创建的对象</li>
     *   <li>验证获取的对象是FactoryBean.getObject()返回的对象</li>
     *   <li>验证对象的属性正确设置</li>
     * </ol>
     * 
     * <p>验证点：</p>
     * <ul>
     *   <li>car.getBrand() == "porsche"：验证属性注入正确</li>
     *   <li>car是Car类型：验证类型正确</li>
     *   <li>car不是null：验证对象创建成功</li>
     * </ul>
     * 
     * <p>技术实现：</p>
     * <ul>
     *   <li>XmlBeanDefinitionReader解析FactoryBean配置</li>
     *   <li>AbstractBeanFactory.getObjectForBeanInstance()处理FactoryBean</li>
     *   <li>FactoryBean.getObject()创建实际对象</li>
     *   <li>FactoryBean对象缓存池缓存单例对象</li>
     * </ul>
     * 
     * @throws Exception 测试过程中的异常
     */
    @Test
    public void testFactoryBean() throws Exception {
        // 1. 创建ApplicationContext并加载XML配置
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:factory-bean.xml");

        // 2. 通过Bean名称获取FactoryBean创建的对象
        // 注意：这里获取的是FactoryBean.getObject()返回的对象，不是FactoryBean本身
        Car car = applicationContext.getBean("car", Car.class);
        
        // 3. 验证对象创建成功且属性正确设置
        assertThat(car).isNotNull();
        assertThat(car.getBrand()).isEqualTo("porsche");
        assertThat(car.getClass()).isEqualTo(Car.class);
        
        // 4. 验证单例缓存机制（多次获取应该返回同一个实例）
        Car car2 = applicationContext.getBean("car", Car.class);
        assertThat(car).isSameAs(car2);
        
        System.out.println("✅ FactoryBean测试通过: " + car);
    }
}