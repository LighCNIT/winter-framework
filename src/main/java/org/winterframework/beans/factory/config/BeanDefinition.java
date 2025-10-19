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
     * 单例作用域常量
     * 表示Bean在容器中只有一个实例，每次获取都返回同一个对象
     */
    public static String SCOPE_SINGLETON = "singleton";

    /**
     * 原型作用域常量
     * 表示Bean每次获取都创建新的实例，容器不管理原型Bean的生命周期
     */
    public static String SCOPE_PROTOTYPE = "prototype";

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
     * Bean初始化方法名称
     * 用于指定Bean初始化时要调用的方法名
     * 可以通过XML配置的init-method属性设置
     */
    private String initMethodName;

    /**
     * Bean销毁方法名称
     * 用于指定Bean销毁时要调用的方法名
     * 可以通过XML配置的destroy-method属性设置
     */
    private String destroyMethodName;

    /**
     * Bean的作用域
     * 默认为单例作用域（singleton），可以通过setScope()方法修改
     * 支持的作用域：singleton、prototype
     */
    private String scope = SCOPE_SINGLETON;

    /**
     * 是否为单例Bean
     * 当scope为"singleton"时为true，用于快速判断Bean的作用域类型
     * 与singleton标志位保持一致，避免重复计算
     */
    private boolean singleton = true;

    /**
     * 是否为原型Bean
     * 当scope为"prototype"时为true，用于快速判断Bean的作用域类型
     * 与prototype标志位保持一致，避免重复计算
     */
    private boolean prototype = false;

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
     * 设置Bean的作用域
     * 
     * <p>设置作用域的同时会更新singleton和prototype标志位，确保数据一致性。</p>
     * 
     * <p>支持的作用域：</p>
     * <ul>
     *   <li>singleton：单例作用域（默认）</li>
     *   <li>prototype：原型作用域</li>
     * </ul>
     * 
     * @param scope 作用域名称，支持"singleton"和"prototype"
     */
    public void setScope(String scope) {
        this.scope = scope;
        this.singleton = SCOPE_SINGLETON.equals(scope);
        this.prototype = SCOPE_PROTOTYPE.equals(scope);
    }

    /**
     * 判断是否为单例Bean
     * 
     * <p>单例Bean的特点：</p>
     * <ul>
     *   <li>容器中只有一个实例</li>
     *   <li>每次获取都返回同一个对象</li>
     *   <li>创建后注册到单例缓存池</li>
     *   <li>容器关闭时执行销毁方法</li>
     * </ul>
     * 
     * @return true表示是单例Bean，false表示不是
     */
    public boolean isSingleton() {
        return this.singleton;
    }

    /**
     * 判断是否为原型Bean
     * 
     * <p>原型Bean的特点：</p>
     * <ul>
     *   <li>每次获取都创建新的实例</li>
     *   <li>容器不管理原型Bean的生命周期</li>
     *   <li>不注册到单例缓存池</li>
     *   <li>不执行销毁方法</li>
     * </ul>
     * 
     * @return true表示是原型Bean，false表示不是
     */
    public boolean isPrototype() {
        return this.prototype;
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
     * 获取Bean初始化方法名称
     * 
     * @return 初始化方法名称，可能为null
     */
    public String getInitMethodName() {
        return initMethodName;
    }

    /**
     * 设置Bean初始化方法名称
     * 
     * @param initMethodName 初始化方法名称
     */
    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    /**
     * 获取Bean销毁方法名称
     * 
     * @return 销毁方法名称，可能为null
     */
    public String getDestroyMethodName() {
        return destroyMethodName;
    }

    /**
     * 设置Bean销毁方法名称
     * 
     * @param destroyMethodName 销毁方法名称
     */
    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
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