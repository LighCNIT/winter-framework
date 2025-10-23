package org.winterframework.test.ioc;

import org.junit.Test;
import org.winterframework.beans.factory.support.DefaultListableBeanFactory;
import org.winterframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.winterframework.test.bean.Car;
import org.winterframework.test.bean.Person;
import org.winterframework.test.common.CustomBeanFactoryPostProcessor;
import org.winterframework.test.common.CustomerBeanPostProcessor;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * BeanFactoryPostProcessor和BeanPostProcessor功能测试类
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/18
 * @description 测试BeanFactoryPostProcessor和BeanPostProcessor的功能
 * 
 * <p>本测试类验证以下功能：</p>
 * <ul>
 *   <li>BeanFactoryPostProcessor：在Bean实例化前修改BeanDefinition</li>
 *   <li>BeanPostProcessor：在Bean实例化后、初始化前后对Bean进行处理</li>
 * </ul>
 * 
 * <p>测试场景：</p>
 * <ul>
 *   <li>通过BeanFactoryPostProcessor修改Bean的属性值</li>
 *   <li>通过BeanPostProcessor在Bean初始化前后进行处理</li>
 * </ul>
 */
public class BeanFactoryPostProcessorAndBeanPostProcessorTest {

    /**
     * 测试BeanFactoryPostProcessor功能
     * 
     * <p>验证BeanFactoryPostProcessor在Bean实例化前修改BeanDefinition的功能。
     * 测试流程：</p>
     * <ol>
     *   <li>创建BeanFactory和XML读取器</li>
     *   <li>从XML文件加载Bean定义</li>
     *   <li>创建并执行BeanFactoryPostProcessor</li>
     *   <li>获取Bean实例并验证属性值是否被修改</li>
     * </ol>
     * 
     * <p>预期结果：Person的name属性应该被BeanFactoryPostProcessor修改为"ivy"</p>
     * 
     * @throws Exception 如果测试过程中发生异常
     */
    @Test
    public void testBeanFactoryPostProcessor() throws Exception {
        // 1. 创建BeanFactory和XML读取器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        
        // 2. 从XML文件加载Bean定义
        beanDefinitionReader.loadBeanDefinitions("classpath:spring.xml");

        // 3. 在所有BeanDefinition加载完成后，但在bean实例化之前，修改BeanDefinition的属性值
        CustomBeanFactoryPostProcessor beanFactoryPostProcessor = new CustomBeanFactoryPostProcessor();
        beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);

        // 4. 获取Bean实例并验证属性值
        Person person = (Person) beanFactory.getBean("person");
        System.out.println(person);
        
        // 验证：name属性在CustomBeanFactoryPostProcessor中被修改为ivy
        assertThat(person.getName()).isEqualTo("ivy");
    }

    /**
     * 测试BeanPostProcessor功能
     * 
     * <p>验证BeanPostProcessor在Bean实例化后、初始化前后对Bean进行处理的功能。
     * 测试流程：</p>
     * <ol>
     *   <li>创建BeanFactory和XML读取器</li>
     *   <li>从XML文件加载Bean定义</li>
     *   <li>添加BeanPostProcessor到BeanFactory</li>
     *   <li>获取Bean实例并验证属性值是否被修改</li>
     * </ol>
     * 
     * <p>预期结果：Car的brand属性应该被BeanPostProcessor修改为"lamborghini"</p>
     * 
     * @throws Exception 如果测试过程中发生异常
     */
    @Test
    public void testBeanPostProcessor() throws Exception {
        // 1. 创建BeanFactory和XML读取器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        
        // 2. 从XML文件加载Bean定义
        beanDefinitionReader.loadBeanDefinitions("classpath:spring.xml");

        // 3. 添加bean实例化后的处理器
        CustomerBeanPostProcessor customerBeanPostProcessor = new CustomerBeanPostProcessor();
        beanFactory.addBeanPostProcessor(customerBeanPostProcessor);

        // 4. 获取Bean实例并验证属性值
        Car car = (Car) beanFactory.getBean("car");
        System.out.println("after bean:"+car);
        
        // 验证：brand属性在CustomerBeanPostProcessor中被修改为lamborghini
        assertThat(car.getBrand()).isEqualTo("lamborghini");
    }
}