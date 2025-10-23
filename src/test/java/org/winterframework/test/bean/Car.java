package org.winterframework.test.bean;

/**
 * Car测试Bean类
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/15
 * @description 用于测试Winter Framework依赖注入功能的测试Bean
 * 
 * 设计特点：
 * 1. 简单POJO：包含基本的getter/setter方法
 * 2. 标准JavaBean：遵循JavaBean规范
 * 3. 测试友好：提供toString方法便于调试
 * 
 * 测试用途：
 * - 验证Bean之间的依赖注入功能
 * - 作为Person类的依赖对象
 * - 验证BeanReference的解析过程
 * 
 * 注意事项：
 * - 字段必须为private（符合JavaBean规范）
 * - 必须提供无参构造器（框架要求）
 * - 必须提供getter/setter方法（属性注入需要）
 */
public class Car {

    /**
     * 汽车品牌
     * 用于测试String类型属性的注入
     */
    private String brand;

    /**
     * 无参构造器
     * 框架创建Bean实例时需要使用
     */
    public Car() {
        // 默认构造器，框架反射创建实例时使用
    }

    /**
     * 获取汽车品牌
     * 
     * @return 汽车品牌
     */
    public String getBrand() {
        return brand;
    }

    /**
     * 设置汽车品牌
     * 框架在属性注入时会调用此方法
     * 
     * @param brand 汽车品牌
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * 重写toString方法，便于测试时观察对象状态
     * 
     * @return 格式化的Car对象信息
     */
    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                '}';
    }
}