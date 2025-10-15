package org.winterframework.beans.factory;

/**
 * Bean属性值 - 表示Bean的一个属性及其值
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/15
 * @description 用于封装Bean的属性信息，包括属性名和属性值
 *              这是实现属性注入的基础数据结构，类似于Spring中的PropertyValue
 * 
 * 设计思想：
 * 1. 不可变对象：一旦创建，属性名和值不能修改，保证数据一致性
 * 2. 简单封装：只包含最基本的属性名和值信息
 * 3. 类型安全：属性值使用Object类型，支持任意类型的属性值
 * 
 * 使用场景：
 * - Bean定义时指定属性值
 * - 属性注入时传递属性信息
 * - 配置解析时存储解析出的属性
 */
public class PropertyValue {

    /**
     * 属性名称
     * 对应Bean中字段的名称，如"name"、"age"等
     */
    private final String name;

    /**
     * 属性值
     * 属性的具体值，可以是任意类型（String、Integer、Object等）
     */
    private final Object value;

    /**
     * 构造方法
     * 
     * @param name 属性名称，不能为null
     * @param value 属性值，可以为null
     * @throws IllegalArgumentException 如果name为null或空字符串
     */
    public PropertyValue(String name, Object value) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Property name cannot be null or empty");
        }
        this.name = name;
        this.value = value;
    }

    /**
     * 获取属性名称
     * 
     * @return 属性名称
     */
    public String getName() {
        return name;
    }

    /**
     * 获取属性值
     * 
     * @return 属性值，可能为null
     */
    public Object getValue() {
        return value;
    }

    /**
     * 重写toString方法，便于调试
     * 
     * @return 格式化的属性信息
     */
    @Override
    public String toString() {
        return "PropertyValue{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }

    /**
     * 重写equals方法，支持属性值比较
     * 
     * @param obj 比较对象
     * @return 是否相等
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        PropertyValue that = (PropertyValue) obj;
        return name.equals(that.name) && 
               (value != null ? value.equals(that.value) : that.value == null);
    }

    /**
     * 重写hashCode方法，支持HashMap等集合操作
     * 
     * @return 哈希码
     */
    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}