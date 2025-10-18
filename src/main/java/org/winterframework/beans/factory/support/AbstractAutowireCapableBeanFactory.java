package org.winterframework.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import org.winterframework.beans.BeanException;
import org.winterframework.beans.factory.BeanFactoryAware;
import org.winterframework.beans.factory.DisposableBean;
import org.winterframework.beans.factory.InitializingBean;
import org.winterframework.beans.factory.PropertyValue;
import org.winterframework.beans.factory.config.BeanDefinition;
import org.winterframework.beans.factory.config.BeanPostProcessor;
import org.winterframework.beans.factory.config.BeanReference;

import java.lang.reflect.Method;

/**
 * 具有自动装配能力的抽象Bean工厂
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/12
 * @description 继承AbstractBeanFactory，实现Bean的创建逻辑
 *              提供自动装配（Autowire）能力，目前实现了基本的实例化功能
 *              后续可以扩展属性注入、依赖注入等自动装配功能
 * 
 * 职责：
 * 1. 实现Bean的实例化（通过反射）
 * 2. 实现Bean的初始化（属性填充、依赖注入等）
 * 3. 将创建好的Bean注册到单例缓存中
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory{

    /**
     * Bean实例化策略
     * 默认使用SimpleInstantiationStrategy（JDK反射方式）
     * 可以通过setInstantiationStrategy()方法切换为Cglib方式
     */
    private InstantiationStrategy instantiationStrategy = new SimpleInstantiationStrategy();

    /**
     * 创建Bean实例 - 实现父类的抽象方法
     * 
     * @param beanName Bean名称
     * @param beanDefinition Bean定义信息
     * @return 创建并初始化好的Bean实例
     * @throws BeanException 创建失败时抛出
     */
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeanException {
        // 调用实际的Bean创建方法
        return doCreateBean(beanName, beanDefinition);
    }

    /**
     * 执行Bean的创建 - 核心创建逻辑
     * 
     * Bean的生命周期简化版：
     * 1. 实例化：通过反射创建对象（newInstance）
     * 2. 属性填充：给对象的属性赋值（暂未实现，后续可扩展）
     * 3. 初始化：执行初始化方法（暂未实现，后续可扩展）
     * 4. 注册单例：将Bean放入单例缓存池
     * 
     * @param beanName Bean名称
     * @param beanDefinition Bean定义信息
     * @return 创建的Bean实例
     */
    protected Object doCreateBean(String beanName, BeanDefinition beanDefinition){
        Object bean = null;
        
        try {
            // 通过反射创建Bean实例
            // 使用无参构造方法创建对象（要求Bean必须有无参构造器）
            bean = createBeanInstance(beanDefinition);
            // 为bean填充属性
            applyPropertyValues(beanName,bean,beanDefinition);
            //执行bean的初始化方法和BeanPostProcessor的前置和后置处理方法
            bean = initializeBean(beanName, bean, beanDefinition);
        } catch (Exception e) {
            // 实例化失败，抛出Bean异常
            throw new BeanException("Instantiation of bean failed", e);
        }
        //注册有销毁方法的bean
        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);
        // 将创建好的Bean添加到单例缓存池中
        // 这样下次获取时可以直接从缓存中返回，实现单例模式
        addSingleton(beanName, bean);
        
        return bean;
    }

    /**
     * 注册可销毁的Bean（如果需要）
     * 
     * <p>检查Bean是否需要注册销毁逻辑，如果需要则创建DisposableBeanAdapter并注册。</p>
     * 
     * <p>注册条件：</p>
     * <ul>
     *   <li>Bean实现了DisposableBean接口</li>
     *   <li>Bean配置了destroy-method属性</li>
     * </ul>
     * 
     * <p>注册后的Bean会在ApplicationContext关闭时自动调用销毁方法。</p>
     * 
     * @param beanName Bean名称
     * @param bean Bean实例
     * @param beanDefinition Bean定义信息
     */
    protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
            super.registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
        }
    }



    /**
     * 初始化Bean - 执行BeanPostProcessor和初始化方法
     * 
     * <p>这是Bean初始化过程的核心方法，按照以下顺序执行：</p>
     * <ol>
     *   <li>执行BeanPostProcessor的前置处理</li>
     *   <li>执行Bean的初始化方法</li>
     *   <li>执行BeanPostProcessor的后置处理</li>
     * </ol>
     * 
     * <p>BeanPostProcessor的作用：</p>
     * <ul>
     *   <li>前置处理：在Bean初始化前进行预处理，如修改属性值</li>
     *   <li>后置处理：在Bean初始化后进行后处理，如添加代理</li>
     * </ul>
     * 
     * @param beanName Bean名称
     * @param bean Bean实例
     * @param beanDefinition Bean定义信息
     * @return 初始化后的Bean实例
     * @throws BeanException 如果初始化过程中发生错误
     */
    protected Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
        // 处理BeanFactoryAware接口
        // 在BeanPostProcessor前置处理之前执行，确保BeanFactoryAware优先于ApplicationContextAware
        if (bean instanceof BeanFactoryAware) {
            ((BeanFactoryAware) bean).setBeanFactory(this);
        }
        
        // 执行BeanPostProcessor的前置处理
        // 此时Bean已实例化但未初始化，ApplicationContextAwareProcessor会在这里处理ApplicationContextAware
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

        // 执行Bean的初始化方法
        try {
            invokeInitMethods(beanName, wrappedBean, beanDefinition);
        } catch (Throwable ex) {
            throw new BeanException("Invocation of init method of bean[" + beanName + "] failed", ex);
        }

        // 执行BeanPostProcessor的后置处理
        // 此时Bean已完全初始化，可以进行后处理，如添加代理
        wrappedBean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        return wrappedBean;
    }

    /**
     * 执行BeanPostProcessor的前置处理方法
     * 
     * <p>遍历所有注册的BeanPostProcessor，依次执行它们的postProcessBeforeInitialization方法。
     * 这个方法会在Bean初始化方法执行之前被调用，用于对Bean进行预处理。</p>
     * 
     * <p>执行流程：</p>
     * <ol>
     *   <li>遍历所有注册的BeanPostProcessor</li>
     *   <li>依次调用每个处理器的postProcessBeforeInitialization方法</li>
     *   <li>如果某个处理器返回null，则停止后续处理并返回当前结果</li>
     *   <li>将处理器的结果作为下一个处理器的输入</li>
     * </ol>
     * 
     * <p>使用场景：</p>
     * <ul>
     *   <li>修改Bean的属性值</li>
     *   <li>为Bean添加代理</li>
     *   <li>实现Bean的增强功能</li>
     *   <li>添加Bean的生命周期回调</li>
     * </ul>
     * 
     * @param existingBean 已实例化但未初始化的Bean对象
     * @param beanName Bean的名称
     * @return 处理后的Bean对象，如果某个处理器返回null则使用当前结果
     * @throws BeanException 如果处理过程中发生错误
     * @see BeanPostProcessor#postProcessBeforeInitialization(Object, String)
     */
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName)
            throws BeanException {
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessBeforeInitialization(result, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }

    /**
     * 执行BeanPostProcessor的后置处理方法
     * 
     * <p>遍历所有注册的BeanPostProcessor，依次执行它们的postProcessAfterInitialization方法。
     * 这个方法会在Bean初始化方法执行之后被调用，用于对Bean进行后处理。</p>
     * 
     * <p>执行流程：</p>
     * <ol>
     *   <li>遍历所有注册的BeanPostProcessor</li>
     *   <li>依次调用每个处理器的postProcessAfterInitialization方法</li>
     *   <li>如果某个处理器返回null，则停止后续处理并返回当前结果</li>
     *   <li>将处理器的结果作为下一个处理器的输入</li>
     * </ol>
     * 
     * <p>使用场景：</p>
     * <ul>
     *   <li>为Bean添加代理（AOP的核心）</li>
     *   <li>实现Bean的最终增强</li>
     *   <li>注册Bean的监听器</li>
     *   <li>完成Bean的最终配置</li>
     * </ul>
     * 
     * <p>这是AOP代理创建的最佳时机，因为此时Bean已经完全初始化完成</p>
     * 
     * @param existingBean 已完全初始化的Bean对象
     * @param beanName Bean的名称
     * @return 处理后的Bean对象，如果某个处理器返回null则使用当前结果
     * @throws BeanException 如果处理过程中发生错误
     * @see BeanPostProcessor#postProcessAfterInitialization(Object, String)
     */
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName)
            throws BeanException {

        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessAfterInitialization(result, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }



    /**
     * 创建Bean实例 - 使用实例化策略
     * 委托给InstantiationStrategy执行具体的实例化逻辑
     * 
     * @param beanDefinition Bean定义信息
     * @return 实例化后的Bean对象
     */
    protected Object createBeanInstance(BeanDefinition beanDefinition){
        return getInstantiationStrategy().instantiate(beanDefinition);
    }

    /**
     * 为Bean应用属性值 - 实现属性注入
     * 
     * 这是属性注入的核心方法，负责将BeanDefinition中定义的属性值
     * 通过反射设置到Bean实例的对应字段中
     * 
     * 实现原理：
     * 1. 遍历BeanDefinition中的所有PropertyValue
     * 2. 使用Hutool的BeanUtil.setFieldValue()方法设置字段值
     * 3. 支持基本类型、包装类型、String等常见类型的自动转换
     * 
     * 技术特点：
     * - 使用Hutool工具库简化反射操作
     * - 支持字段名自动匹配（忽略大小写）
     * - 提供详细的异常信息便于调试
     * 
     * @param beanName Bean名称，用于异常信息
     * @param bean Bean实例，要设置属性的对象
     * @param beanDefinition Bean定义，包含属性值信息
     * @throws BeanException 属性设置失败时抛出
     */
    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition){
        try {
            // 遍历Bean定义中的所有属性值
            for (PropertyValue propertyValue : beanDefinition.getPropertyValues().getPropertyValues()){
                String name = propertyValue.getName();
                Object value = propertyValue.getValue();
                if (value instanceof BeanReference) {
                    // beanA依赖beanB，先实例化beanB
                    BeanReference beanReference = (BeanReference) value;
                    value = getBean(beanReference.getBeanName());
                }
                // 使用Hutool工具库设置字段值
                // 支持类型自动转换和字段名匹配
                BeanUtil.setFieldValue(bean, name, value);
            }
        } catch (Exception ex) {
            // 包装异常信息，便于定位问题
            throw new BeanException("Error setting property values for bean: " + beanName, ex);
        }
    }

    /**
     * 执行Bean的初始化方法
     * 
     * <p>按照以下顺序执行初始化逻辑：</p>
     * <ol>
     *   <li>如果Bean实现了InitializingBean接口，先调用其afterPropertiesSet()方法</li>
     *   <li>如果配置了init-method且不是"afterPropertiesSet"方法，则通过反射调用指定的初始化方法</li>
     * </ol>
     * 
     * <p>注意事项：</p>
     * <ul>
     *   <li>避免重复调用：如果Bean实现了InitializingBean接口且init-method也是"afterPropertiesSet"，则不会重复调用</li>
     *   <li>异常处理：如果初始化方法不存在，会抛出BeanException</li>
     *   <li>反射调用：使用Hutool的ClassUtil.getPublicMethod()方法获取公共方法</li>
     *   <li>执行时机：在BeanPostProcessor前置处理之后执行</li>
     * </ul>
     * 
     * @param beanName Bean名称
     * @param bean Bean实例
     * @param beanDefinition Bean定义信息
     * @throws Throwable 如果初始化过程中发生错误
     */
    protected void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) throws Throwable {
        // 1. 如果Bean实现了InitializingBean接口，先调用其afterPropertiesSet()方法
        if (bean instanceof InitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        }
        
        // 2. 如果配置了init-method且不是"afterPropertiesSet"方法，则通过反射调用
        String initMethodName = beanDefinition.getInitMethodName();
        if (StrUtil.isNotEmpty(initMethodName) && 
            !(bean instanceof InitializingBean && "afterPropertiesSet".equals(initMethodName))) {
            
            Method initMethod = ClassUtil.getPublicMethod(beanDefinition.getBeanClass(), initMethodName);
            if (initMethod == null) {
                throw new BeanException("Could not find an init method named '" + initMethodName + 
                    "' on bean with name '" + beanName + "'");
            }
            initMethod.invoke(bean);
        }
        
        System.out.println("执行bean[" + beanName + "]的初始化方法");
    }

    /**
     * 获取当前使用的实例化策略
     * 
     * @return 实例化策略对象
     */
    public InstantiationStrategy getInstantiationStrategy(){
        return instantiationStrategy;
    }

    /**
     * 设置实例化策略
     * 可以动态切换不同的实例化方式（JDK反射 或 Cglib代理）
     * 
     * @param instantiationStrategy 实例化策略对象
     */
    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy){
        this.instantiationStrategy = instantiationStrategy;
    }
}