package org.winterframework.beans.factory.config;

/**
 * Bean引用 - 表示一个Bean对另一个Bean的依赖引用
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/15
 * @description 用于实现Bean之间的依赖注入，当Bean的属性需要引用另一个Bean时使用
 *              这是实现依赖注入的核心数据结构，类似于Spring中的BeanReference
 * 
 * 设计思想：
 * 1. 延迟解析：不直接存储Bean实例，而是存储Bean名称，在需要时才解析
 * 2. 循环依赖支持：通过名称引用避免直接对象引用导致的循环依赖问题
 * 3. 简单封装：只包含被引用Bean的名称，保持结构简单
 * 
 * 使用场景：
 * - Bean属性需要注入其他Bean实例
 * - 实现Bean之间的依赖关系
 * - 支持循环依赖的解决
 * 
 * 示例：
 * Person依赖Car：
 * PropertyValue("car", new BeanReference("car"))
 * 
 * 解析过程：
 * 1. 创建Person实例
 * 2. 发现car属性是BeanReference类型
 * 3. 调用getBean("car")获取Car实例
 * 4. 将Car实例注入到Person的car属性中
 */
public class BeanReference {

    /**
     * 被引用的Bean名称
     * 用于在容器中查找对应的Bean实例
     */
    private final String beanName;

    /**
     * 构造方法
     * 
     * @param beanName 被引用的Bean名称，不能为null
     * @throws IllegalArgumentException 如果beanName为null或空字符串
     */
    public BeanReference(String beanName) {
        if (beanName == null || beanName.trim().isEmpty()) {
            throw new IllegalArgumentException("Bean name cannot be null or empty");
        }
        this.beanName = beanName;
    }

    /**
     * 获取被引用的Bean名称
     * 
     * @return Bean名称
     */
    public String getBeanName() {
        return beanName;
    }

    /**
     * 重写toString方法，便于调试
     * 
     * @return 格式化的Bean引用信息
     */
    @Override
    public String toString() {
        return "BeanReference{" +
                "beanName='" + beanName + '\'' +
                '}';
    }

    /**
     * 重写equals方法，支持Bean引用比较
     * 
     * @param obj 比较对象
     * @return 是否相等
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        BeanReference that = (BeanReference) obj;
        return beanName.equals(that.beanName);
    }

    /**
     * 重写hashCode方法，支持HashMap等集合操作
     * 
     * @return 哈希码
     */
    @Override
    public int hashCode() {
        return beanName.hashCode();
    }
}