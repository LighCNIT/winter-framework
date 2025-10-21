package org.winterframework.test.ioc.common;

import org.winterframework.beans.BeanException;
import org.winterframework.beans.factory.FactoryBean;
import org.winterframework.test.ioc.bean.Car;

/**
 * CarFactoryBean - FactoryBean实现示例
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/19
 * @description 用于测试FactoryBean功能的示例实现，演示如何通过FactoryBean创建Car对象
 * 
 * 设计特点：
 * 1. 实现FactoryBean<Car>接口，指定创建的对象类型为Car
 * 2. 支持属性注入：通过setBrand()方法设置汽车品牌
 * 3. 单例模式：isSingleton()返回true，确保创建的Car对象是单例
 * 4. 延迟创建：只有在实际获取Bean时才调用getObject()方法
 * 
 * 使用场景：
 * - 演示FactoryBean的基本用法
 * - 测试框架对FactoryBean的支持
 * - 验证FactoryBean的属性注入功能
 * - 验证FactoryBean的缓存机制
 * 
 * 配置示例：
 * <bean id="car" class="org.winterframework.test.ioc.common.CarFactoryBean">
 *     <property name="brand" value="porsche"/>
 * </bean>
 */
public class CarFactoryBean implements FactoryBean<Car> {

    /**
     * 汽车品牌属性
     * 通过XML配置的property标签注入
     */
    private String brand;

    /**
     * 创建Car对象实例
     * 
     * <p>此方法在每次需要获取Car Bean时被调用（如果isSingleton()返回false），
     * 或者在第一次获取时被调用并缓存结果（如果isSingleton()返回true）。</p>
     * 
     * <p>实现逻辑：</p>
     * <ol>
     *   <li>创建新的Car实例</li>
     *   <li>设置品牌属性（从FactoryBean的brand字段获取）</li>
     *   <li>返回配置好的Car对象</li>
     * </ol>
     * 
     * @return 配置好的Car对象实例
     * @throws BeanException 如果对象创建失败
     */
    @Override
    public Car getObject() throws BeanException {
        Car car = new Car();
        car.setBrand(brand);
        return car;
    }

    /**
     * 判断创建的Car对象是否为单例
     * 
     * <p>返回true表示单例模式：</p>
     * <ul>
     *   <li>getObject()只在第一次调用时执行</li>
     *   <li>后续调用返回缓存的对象</li>
     *   <li>所有通过相同Bean名称获取的Car对象都是同一个实例</li>
     * </ul>
     * 
     * @return true表示单例，false表示原型
     */
    @Override
    public boolean isSingleton() {
        return true;
    }

    /**
     * 设置汽车品牌
     * 
     * <p>此方法由框架在属性注入时调用，用于设置FactoryBean的brand属性。
     * 设置的brand值会在getObject()方法中用于配置创建的Car对象。</p>
     * 
     * @param brand 汽车品牌名称
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }
}