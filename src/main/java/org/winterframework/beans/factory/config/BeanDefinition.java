package org.winterframework.beans.factory.config;

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
     * 构造方法
     * @param beanClass Bean的Class类型
     */
    public BeanDefinition(Class beanClass){
        this.beanClass = beanClass;
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
}