package org.winterframework.beans.factory;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean属性值集合 - 管理Bean的所有属性值
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/15
 * @description 用于管理Bean的所有属性值，提供属性的增删改查功能
 *              这是实现属性注入的核心数据结构，类似于Spring中的PropertyValues
 * 
 * 设计思想：
 * 1. 集合管理：使用List存储多个PropertyValue对象
 * 2. 线程安全：内部使用ArrayList，非线程安全（符合Spring设计）
 * 3. 便捷操作：提供添加、获取、查找等常用操作
 * 4. 不可变返回：getPropertyValues()返回数组副本，防止外部修改
 * 
 * 使用场景：
 * - Bean定义时存储所有属性值
 * - 属性注入时遍历所有属性进行赋值
 * - 配置解析时收集解析出的属性
 */
public class PropertyValues {

    /**
     * 属性值列表
     * 使用ArrayList存储PropertyValue对象，支持动态添加
     */
    private final List<PropertyValue> propertyValues = new ArrayList<>();

    /**
     * 添加属性值
     * 
     * @param propertyValue 要添加的属性值对象，不能为null
     * @throws IllegalArgumentException 如果propertyValue为null
     */
    public void addPropertyValue(PropertyValue propertyValue) {
        if (propertyValue == null) {
            throw new IllegalArgumentException("PropertyValue cannot be null");
        }
        propertyValues.add(propertyValue);
    }

    /**
     * 获取所有属性值数组
     * 返回数组副本，防止外部修改内部数据
     * 
     * @return 属性值数组，如果没有属性则返回空数组
     */
    public PropertyValue[] getPropertyValues() {
        return this.propertyValues.toArray(new PropertyValue[0]);
    }

    /**
     * 根据属性名查找属性值
     * 
     * @param propertyName 属性名称
     * @return 找到的属性值对象，如果不存在则返回null
     */
    public PropertyValue getPropertyValue(String propertyName) {
        if (propertyName == null) {
            return null;
        }
        
        for (PropertyValue propertyValue : this.propertyValues) {
            if (propertyName.equals(propertyValue.getName())) {
                return propertyValue;
            }
        }
        return null;
    }

    /**
     * 检查是否包含指定属性
     * 
     * @param propertyName 属性名称
     * @return 如果包含该属性则返回true，否则返回false
     */
    public boolean contains(String propertyName) {
        return getPropertyValue(propertyName) != null;
    }

    /**
     * 获取属性数量
     * 
     * @return 属性值的数量
     */
    public int size() {
        return propertyValues.size();
    }

    /**
     * 检查是否为空
     * 
     * @return 如果没有属性则返回true，否则返回false
     */
    public boolean isEmpty() {
        return propertyValues.isEmpty();
    }

    /**
     * 重写toString方法，便于调试
     * 
     * @return 格式化的属性集合信息
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("PropertyValues{");
        sb.append("propertyValues=[");
        for (int i = 0; i < propertyValues.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(propertyValues.get(i));
        }
        sb.append("]}");
        return sb.toString();
    }
}