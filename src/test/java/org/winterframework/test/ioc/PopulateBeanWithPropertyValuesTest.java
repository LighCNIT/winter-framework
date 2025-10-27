package org.winterframework.test.ioc;

import org.junit.Test;
import org.winterframework.beans.PropertyValue;
import org.winterframework.beans.PropertyValues;
import org.winterframework.beans.factory.config.BeanDefinition;
import org.winterframework.beans.factory.config.BeanReference;
import org.winterframework.beans.factory.support.DefaultListableBeanFactory;
import org.winterframework.test.bean.Car;
import org.winterframework.test.bean.Person;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Bean属性注入测试类
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/15
 * @description 测试Winter Framework的属性注入功能
 *              验证Bean在创建时能够正确设置属性值
 * 
 * 测试场景：
 * 1. 创建BeanDefinition时指定属性值
 * 2. 通过BeanFactory获取Bean时自动注入属性
 * 3. 验证属性值是否正确设置
 * 
 * 技术要点：
 * - PropertyValue和PropertyValues的使用
 * - BeanDefinition的属性值配置
 * - 属性注入的完整流程
 */
public class PopulateBeanWithPropertyValuesTest {

    /**
     * 测试Bean属性注入功能
     * 
     * 测试步骤：
     * 1. 创建PropertyValues对象，添加属性值
     * 2. 创建BeanDefinition，指定Bean类型和属性值
     * 3. 注册BeanDefinition到BeanFactory
     * 4. 获取Bean实例，验证属性是否正确注入
     * 
     * 验证点：
     * - Bean实例不为null
     * - name属性值为"derek"
     * - age属性值为18
     * 
     * @throws Exception 测试过程中的异常
     */
    @Test
    public void testPopulateBeanWithPropertyValues() throws Exception {
        // 1. 创建BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        
        // 2. 创建属性值集合
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("name", "derek"));
        propertyValues.addPropertyValue(new PropertyValue("age", 18));
        
        // 3. 创建Bean定义，指定Bean类型和属性值
        BeanDefinition beanDefinition = new BeanDefinition(Person.class, propertyValues);
        
        // 4. 注册Bean定义到工厂
        beanFactory.registerBeanDefinition("person", beanDefinition);

        // 5. 获取Bean实例（此时会自动进行属性注入）
        Person person = (Person) beanFactory.getBean("person");
        
        // 6. 输出Bean信息，便于观察
        System.out.println("创建的Person对象: " + person);
        
        // 7. 验证属性值是否正确注入
        assertThat(person.getName()).isEqualTo("derek");
        assertThat(person.getAge()).isEqualTo(18);
        
        System.out.println("✅ 属性注入测试通过！");
    }

    /**
     * 测试空属性值的处理
     * 
     * 验证当BeanDefinition中没有属性值时，Bean仍能正常创建
     */
    @Test
    public void testBeanWithoutProperties() throws Exception {
        // 1. 创建BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        
        // 2. 创建没有属性值的Bean定义
        BeanDefinition beanDefinition = new BeanDefinition(Person.class);
        
        // 3. 注册Bean定义
        beanFactory.registerBeanDefinition("person", beanDefinition);

        // 4. 获取Bean实例
        Person person = (Person) beanFactory.getBean("person");
        
        // 5. 验证Bean创建成功，属性为默认值
        assertThat(person).isNotNull();
        assertThat(person.getName()).isNull();
        assertThat(person.getAge()).isEqualTo(0);
        
        System.out.println("✅ 无属性Bean创建测试通过！");
    }

    /**
     * 测试部分属性注入
     * 
     * 验证只设置部分属性时，其他属性保持默认值
     */
    @Test
    public void testPartialPropertyInjection() throws Exception {
        // 1. 创建BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        
        // 2. 只设置name属性，不设置age
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("name", "alice"));
        
        BeanDefinition beanDefinition = new BeanDefinition(Person.class, propertyValues);
        beanFactory.registerBeanDefinition("person", beanDefinition);

        // 3. 获取Bean实例
        Person person = (Person) beanFactory.getBean("person");
        
        // 4. 验证部分属性注入
        assertThat(person.getName()).isEqualTo("alice");
        assertThat(person.getAge()).isEqualTo(0); // age保持默认值
        
        System.out.println("✅ 部分属性注入测试通过！");
    }

    /**
     * 测试Bean之间的依赖注入功能
     * 
     * 测试场景：
     * 1. 创建Car Bean，设置brand属性为"porsche"
     * 2. 创建Person Bean，设置基本属性（name、age）
     * 3. 通过BeanReference让Person依赖Car
     * 4. 验证框架能正确解析BeanReference并注入Car实例
     * 
     * 技术要点：
     * - BeanReference的使用：PropertyValue("car", new BeanReference("car"))
     * - 依赖解析：框架自动调用getBean("car")获取Car实例
     * - 属性注入：将解析出的Car实例注入到Person的car属性中
     * 
     * 验证点：
     * - Person实例创建成功
     * - Person的基本属性正确注入（name="derek", age=18）
     * - Car实例正确注入到Person的car属性中
     * - Car的brand属性正确设置（brand="porsche"）
     * 
     * @throws Exception 测试过程中的异常
     */
    @Test
    public void testPopulateBeanWithBean() throws Exception {
        // 1. 创建BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2. 注册Car Bean定义
        PropertyValues propertyValuesForCar = new PropertyValues();
        propertyValuesForCar.addPropertyValue(new PropertyValue("brand", "porsche"));
        BeanDefinition carBeanDefinition = new BeanDefinition(Car.class, propertyValuesForCar);
        beanFactory.registerBeanDefinition("car", carBeanDefinition);

        // 3. 注册Person Bean定义（包含对Car的依赖）
        PropertyValues propertyValuesForPerson = new PropertyValues();
        propertyValuesForPerson.addPropertyValue(new PropertyValue("name", "derek"));
        propertyValuesForPerson.addPropertyValue(new PropertyValue("age", 18));
        // 关键：使用BeanReference表示对Car的依赖
        propertyValuesForPerson.addPropertyValue(new PropertyValue("car", new BeanReference("car")));
        BeanDefinition beanDefinition = new BeanDefinition(Person.class, propertyValuesForPerson);
        beanFactory.registerBeanDefinition("person", beanDefinition);

        // 4. 获取Person Bean（框架会自动解析依赖并注入Car）
        Person person = (Person) beanFactory.getBean("person");
        System.out.println("创建的Person对象: " + person);
        
        // 5. 验证Person的基本属性注入
        assertThat(person.getName()).isEqualTo("derek");
        assertThat(person.getAge()).isEqualTo(18);
        
        // 6. 验证Car依赖注入
        Car car = person.getCar();
        assertThat(car).isNotNull();
        assertThat(car.getBrand()).isEqualTo("porsche");
        
        System.out.println("✅ Bean依赖注入测试通过！");
    }
}