package org.winterframework.beans.factory.config;

import org.winterframework.beans.factory.PropertyValue;
import org.winterframework.beans.factory.PropertyValues;

/**
 * Bean定义类 - Bean的元数据信息
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/12
 * @description 用于保存Bean的定义信息（元数据），包括Bean的Class类型等
 *              类似于Spring中的BeanDefinition，用于描述如何创建一个Bean
 *              后续可以扩展属性信息、依赖关系、作用域等更多信息
 */
public class BeanDefinition {

    /**
     * Bean的Class类型
     * 保存Bean的类信息，用于反射创建实例
     */
    private Class beanClass;

    /**
     * Bean的属性值集合
     * 存储Bean的所有属性信息，用于属性注入
     * 如果为null，则使用空的PropertyValues对象
     */
    private PropertyValues propertyValues;

    /**
     * 构造方法 - 只指定Bean类型
     * 
     * @param beanClass Bean的Class类型，不能为null
     * @throws IllegalArgumentException 如果beanClass为null
     */
    public BeanDefinition(Class beanClass){
        this(beanClass, null);
    }

    /**
     * 构造方法 - 指定Bean类型和属性值
     * 
     * @param beanClass Bean的Class类型，不能为null
     * @param propertyValues Bean的属性值集合，可以为null（会创建空的PropertyValues）
     * @throws IllegalArgumentException 如果beanClass为null
     */
    public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
        if (beanClass == null) {
            throw new IllegalArgumentException("Bean class cannot be null");
        }
        this.beanClass = beanClass;
        this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
    }

    /**
     * 获取Bean的Class类型
     * @return Bean的Class对象
     */
    public Class getBeanClass(){
        return beanClass;
    }

    /**
     * 设置Bean的Class类型
     * @param beanClass Bean的Class对象
     */
    public void setBeanClass(Class beanClass){
        this.beanClass = beanClass;
    }

    /**
     * 获取Bean的属性值集合
     * 
     * @return PropertyValues对象，不会为null
     */
    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    /**
     * 设置Bean的属性值集合
     * 
     * @param propertyValues 新的属性值集合，如果为null则创建空的PropertyValues
     */
    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
    }

    /**
     * 添加属性值
     * 便捷方法，直接向PropertyValues中添加属性
     * 
     * @param name 属性名称
     * @param value 属性值
     */
    public void addPropertyValue(String name, Object value) {
        this.propertyValues.addPropertyValue(new PropertyValue(name, value));
    }

    /**
     * 重写toString方法，便于调试
     * 
     * @return 格式化的Bean定义信息
     */
    @Override
    public String toString() {
        return "BeanDefinition{" +
                "beanClass=" + beanClass +
                ", propertyValues=" + propertyValues +
                '}';
    }
}