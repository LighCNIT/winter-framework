package org.winterframework.beans.factory.support;

import org.winterframework.beans.BeanException;
import org.winterframework.beans.factory.BeanFactory;
import org.winterframework.beans.factory.FactoryBean;
import org.winterframework.beans.factory.config.BeanDefinition;
import org.winterframework.beans.factory.config.BeanPostProcessor;
import org.winterframework.beans.factory.config.ConfigurableBeanFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 抽象Bean工厂 - 模板方法模式的应用
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/12
 * @description 实现了BeanFactory接口，定义了获取Bean的模板流程
 *              继承DefaultSingletonBeanRegistry，拥有单例Bean管理能力
 *              
 * 设计模式：模板方法模式
 * - 父类定义算法骨架（getBean方法）
 * - 子类实现具体步骤（createBean、getBeanDefinition）
 * 
 * 职责：
 * 1. 定义获取Bean的标准流程
 * 2. 整合单例缓存和Bean创建逻辑
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {

    /**
     * Bean后置处理器列表
     * 
     * <p>存储所有注册的BeanPostProcessor，在Bean初始化过程中会被依次调用。
     * 这些处理器可以在Bean初始化前后对Bean进行自定义处理。</p>
     * 
     * <p>使用场景：</p>
     * <ul>
     *   <li>修改Bean的属性值</li>
     *   <li>为Bean添加代理（AOP的基础）</li>
     *   <li>实现Bean的增强功能</li>
     *   <li>添加Bean的生命周期回调</li>
     * </ul>
     * 
     * <p>执行时机：在Bean实例化后、初始化前后</p>
     */
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    /**
     * FactoryBean对象缓存池
     * 
     * <p>用于缓存单例FactoryBean的getObject()方法返回的对象，避免重复创建。</p>
     * 
     * <p>缓存策略：</p>
     * <ul>
     *   <li>单例FactoryBean：getObject()结果会被缓存，键为Bean名称</li>
     *   <li>原型FactoryBean：不缓存，每次调用getObject()创建新实例</li>
     * </ul>
     * 
     * <p>使用场景：</p>
     * <ul>
     *   <li>复杂对象的创建（如代理对象、连接池等）</li>
     *   <li>需要特殊初始化逻辑的对象</li>
     *   <li>创建成本较高的对象</li>
     * </ul>
     * 
     * <p>注意事项：</p>
     * <ul>
     *   <li>只缓存单例FactoryBean的对象</li>
     *   <li>缓存的对象与FactoryBean本身是分离的</li>
     *   <li>容器关闭时缓存会被清空</li>
     * </ul>
     */
    private final Map<String,Object> factoryBeanObjectCache = new HashMap<>();


    /**
     * 获取Bean实例 - 模板方法
     * 
     * 核心流程（类似Spring的Bean获取流程）：
     * 1. 先从单例缓存中获取（getSingleton）
     * 2. 如果缓存中存在，直接返回（避免重复创建）
     * 3. 如果缓存中不存在：
     *    a. 获取Bean的定义信息（getBeanDefinition）
     *    b. 根据定义信息创建Bean实例（createBean）
     * 
     * @param name Bean名称
     * @return Bean实例对象
     * @throws BeanException 如果Bean创建失败
     */
    @Override
    public Object getBean(String name) throws BeanException {
        // 第一步：尝试从单例缓存池中获取Bean
        Object sharedInstance = getSingleton(name);
        if (sharedInstance != null) {
            // 缓存命中，直接返回
            return getObjectForBeanInstance(sharedInstance, name);
        }

        // 第二步：缓存未命中，需要创建新的Bean实例
        // 先获取Bean的定义信息
        BeanDefinition beanDefinition = getBeanDefinition(name);

        // 第三步：根据BeanDefinition创建Bean实例
        Object bean = createBean(name, beanDefinition);
        return getObjectForBeanInstance(bean, name);
    }

    /**
     * 处理FactoryBean实例，获取其创建的实际对象
     * 
     * <p>此方法是FactoryBean支持的核心实现，负责区分FactoryBean本身和FactoryBean创建的对象：</p>
     * <ul>
     *   <li>如果beanInstance是FactoryBean：调用其getObject()方法获取实际对象</li>
     *   <li>如果beanInstance不是FactoryBean：直接返回原对象</li>
     * </ul>
     * 
     * <p>FactoryBean对象缓存策略：</p>
     * <ul>
     *   <li>单例FactoryBean：getObject()结果会被缓存，避免重复创建</li>
     *   <li>原型FactoryBean：每次调用getObject()创建新实例</li>
     * </ul>
     * 
     * <p>使用示例：</p>
     * <pre>
     * // 获取FactoryBean创建的对象
     * Car car = beanFactory.getBean("carFactory", Car.class);
     * 
     * // 获取FactoryBean本身（需要在Bean名称前加"&"）
     * CarFactoryBean factoryBean = beanFactory.getBean("&carFactory", CarFactoryBean.class);
     * </pre>
     * 
     * @param beanInstance Bean实例，可能是FactoryBean或普通Bean
     * @param beanName Bean名称，用于缓存键和异常信息
     * @return 实际要返回的对象（FactoryBean创建的对象或原Bean实例）
     * @throws BeanException 如果FactoryBean.getObject()方法执行失败
     */
    protected Object getObjectForBeanInstance(Object beanInstance, String beanName){
        Object object = beanInstance;
        
        // 检查是否为FactoryBean实例
        if (beanInstance instanceof FactoryBean){
            FactoryBean factoryBean = (FactoryBean) beanInstance;
            try {
                // 根据FactoryBean的作用域决定是否缓存getObject()的结果
                if (factoryBean.isSingleton()){
                    // 单例FactoryBean：先检查缓存，如果不存在则创建并缓存
                    object = this.factoryBeanObjectCache.get(beanName);
                    if (object == null){
                        object = factoryBean.getObject();
                        this.factoryBeanObjectCache.put(beanName, object);
                    }
                } else {
                    // 原型FactoryBean：每次调用getObject()创建新实例
                    object = factoryBean.getObject();
                }
            } catch (Exception ex){
                // 包装FactoryBean异常，提供详细的错误信息
                throw new BeanException("FactoryBean threw exception on object[" + beanName + "] creation", ex);
            }
        }
        // 如果不是FactoryBean，直接返回原实例
        return object;
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeanException {
        return ((T) getBean(name));
    }

    /**
     * 创建Bean实例 - 抽象方法，由子类实现
     * 
     * @param beanName Bean名称
     * @param beanDefinition Bean定义信息
     * @return 创建的Bean实例
     * @throws BeanException 创建失败时抛出
     */
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition) throws BeanException;

    /**
     * 获取Bean定义信息 - 抽象方法，由子类实现
     * 子类需要维护BeanDefinition的存储结构
     * 
     * @param beanName Bean名称
     * @return Bean定义信息
     * @throws BeanException 如果Bean定义不存在
     */
    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeanException;

    /**
     * 添加Bean后置处理器
     * 
     * <p>向Bean工厂注册一个BeanPostProcessor，该处理器会在Bean实例化后、
     * 初始化前后被调用，用于对Bean进行自定义处理。</p>
     * 
     * <p>实现特点：</p>
     * <ul>
     *   <li>如果处理器已存在，会先移除再添加（覆盖策略）</li>
     *   <li>处理器按照添加顺序执行</li>
     *   <li>支持动态添加和移除处理器</li>
     * </ul>
     * 
     * <p>使用场景：</p>
     * <ul>
     *   <li>修改Bean的属性值</li>
     *   <li>为Bean添加代理（AOP的基础）</li>
     *   <li>实现Bean的增强功能</li>
     *   <li>添加Bean的生命周期回调</li>
     * </ul>
     * 
     * @param beanPostProcessor 要添加的Bean后置处理器
     * @see BeanPostProcessor
     * @see BeanPostProcessor#postProcessBeforeInitialization(Object, String)
     * @see BeanPostProcessor#postProcessAfterInitialization(Object, String)
     */
    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        // 有则覆盖：先移除已存在的处理器，再添加新的处理器
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    /**
     * 获取所有Bean后置处理器
     * 
     * <p>返回当前注册的所有BeanPostProcessor列表，这些处理器会在Bean
     * 初始化过程中按照注册顺序依次执行。</p>
     * 
     * <p>注意事项：</p>
     * <ul>
     *   <li>返回的是处理器列表的引用，不是副本</li>
     *   <li>处理器按照添加顺序执行</li>
     *   <li>如果某个处理器返回null，会停止后续处理</li>
     * </ul>
     * 
     * @return Bean后置处理器列表
     * @see BeanPostProcessor
     */
    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }
}