package org.winterframework.test.bean;

/**
 * Person测试Bean类
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/15
 * @description 用于测试Winter Framework属性注入功能的测试Bean
 * 
 * 设计特点：
 * 1. 简单POJO：包含基本的getter/setter方法
 * 2. 标准JavaBean：遵循JavaBean规范
 * 3. 测试友好：提供toString方法便于调试
 * 
 * 测试用途：
 * - 验证基本类型属性注入（int age）
 * - 验证引用类型属性注入（String name）
 * - 验证属性注入的完整流程
 * 
 * 注意事项：
 * - 字段必须为private（符合JavaBean规范）
 * - 必须提供无参构造器（框架要求）
 * - 必须提供getter/setter方法（属性注入需要）
 */
public class Person {

    /**
     * 姓名
     * 用于测试String类型属性的注入
     */
    private String name;

    /**
     * 年龄
     * 用于测试基本类型属性的注入
     */
    private int age;

    /**
     * 无参构造器
     * 框架创建Bean实例时需要使用
     */
    public Person() {
        // 默认构造器，框架反射创建实例时使用
    }

    /**
     * 获取姓名
     * 
     * @return 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置姓名
     * 
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取年龄
     * 
     * @return 年龄
     */
    public int getAge() {
        return age;
    }

    /**
     * 设置年龄
     * 
     * @param age 年龄
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * 重写toString方法，便于测试时观察对象状态
     * 
     * @return 格式化的Person对象信息
     */
    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}