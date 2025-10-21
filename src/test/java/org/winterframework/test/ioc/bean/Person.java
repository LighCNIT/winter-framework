package org.winterframework.test.ioc.bean;

import org.winterframework.beans.BeanException;
import org.winterframework.beans.factory.DisposableBean;
import org.winterframework.beans.factory.InitializingBean;

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
public class Person implements InitializingBean, DisposableBean {

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
     * 汽车
     * 用于测试Bean之间的依赖注入功能
     * 当Person需要依赖Car时，框架会自动注入Car实例
     */
    private Car car;


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
     * 获取汽车
     * 
     * @return 汽车实例，可能为null
     */
    public Car getCar() {
        return car;
    }

    /**
     * 设置汽车
     * 框架在依赖注入时会调用此方法
     * 
     * @param car 汽车实例
     */
    public void setCar(Car car) {
        this.car = car;
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
                ", car=" + car +
                '}';
    }

    public void customInitMethod() {
        System.out.println("I was born in the method named customInitMethod");
    }

    public void customDestroyMethod() {
        System.out.println("I died in the method named customDestroyMethod");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("I died in the method named destroy");
    }

    @Override
    public void afterPropertiesSet() throws BeanException {
        System.out.println("I was born in the method named afterPropertiesSet");
    }
}