package org.winterframework.beans.factory.xml;

import cn.hutool.core.util.StrUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.winterframework.beans.BeanException;
import org.winterframework.beans.PropertyValue;
import org.winterframework.beans.factory.config.BeanDefinition;
import org.winterframework.beans.factory.config.BeanReference;
import org.winterframework.beans.factory.support.AbstractBeanDefinitionReader;
import org.winterframework.beans.factory.support.BeanDefinitionRegistry;
import org.winterframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.winterframework.core.io.Resource;
import org.winterframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * XML Bean定义读取器
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/17
 * @description 负责从XML配置文件中读取Bean定义信息，解析XML并注册到BeanDefinitionRegistry中
 *              支持标准的Spring XML配置格式，包括bean标签、property标签等
 *              这是配置解析阶段的核心组件，将XML配置转换为内存中的BeanDefinition对象
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    /** XML元素名称常量 */
    public static final String BEAN_ELEMENT = "bean";
    public static final String PROPERTY_ELEMENT = "property";
    
    /** XML属性名称常量 */
    public static final String ID_ATTRIBUTE = "id";
    public static final String NAME_ATTRIBUTE = "name";
    public static final String CLASS_ATTRIBUTE = "class";
    public static final String VALUE_ATTRIBUTE = "value";
    public static final String REF_ATTRIBUTE = "ref";

    /** Bean生命周期相关属性 */
    public static final String INIT_METHOD_ATTRIBUTE = "init-method";
    public static final String DESTROY_METHOD_ATTRIBUTE = "destroy-method";
    
    /** Bean作用域属性 */
    public static final String SCOPE_ATTRIBUTE = "scope";

    public static final String BASE_PACKAGE_ATTRIBUTE = "base-package";
    public static final String COMPONENT_SCAN_ELEMENT = "component-scan";


    /**
     * 构造方法 - 使用默认资源加载器
     * 
     * @param registry Bean定义注册表，用于注册解析出的Bean定义
     */
    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    /**
     * 构造方法 - 指定资源加载器
     * 
     * @param registry Bean定义注册表，用于注册解析出的Bean定义
     * @param resourceLoader 资源加载器，用于加载XML资源文件
     */
    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    /**
     * 从Resource对象加载Bean定义
     * 
     * @param resource 资源对象，包含XML配置内容
     * @throws BeanException 当XML解析失败或IO异常时抛出
     */
    @Override
    public void loadBeanDefinitions(Resource resource) throws BeanException {
        try {
            InputStream inputStream = resource.getInputStream();
            try {
                doLoadBeanDefinitions(inputStream);
            } finally {
                inputStream.close();
            }
        } catch (IOException | DocumentException ex) {
            throw new BeanException("IOException parsing XML document from " + resource, ex);
        }
    }

    /**
     * 从资源位置字符串加载Bean定义
     * 
     * @param location 资源位置，支持classpath:、file:、http:等前缀
     * @throws BeanException 当资源加载或XML解析失败时抛出
     */
    @Override
    public void loadBeanDefinitions(String location) throws BeanException {
        ResourceLoader resourceLoader = getResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        loadBeanDefinitions(resource);
    }

    /**
     * 执行实际的XML解析和Bean定义加载
     * 
     * @param inputStream XML文件的输入流
     * @throws BeanException 当XML解析失败、类找不到或Bean名称重复时抛出
     */
    protected void doLoadBeanDefinitions(InputStream inputStream) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        Element beans = document.getRootElement();

        Element componentScan = beans.element(COMPONENT_SCAN_ELEMENT);
        if (componentScan != null){
            String scanPath = componentScan.attributeValue(BASE_PACKAGE_ATTRIBUTE);
            if (StrUtil.isEmpty(scanPath)){
                throw new BeanException("The value of base-package attribute can not be empty or null");
            }
            scanPackage(scanPath);
        }

        List<Element> beanList = beans.elements(BEAN_ELEMENT);
        for (Element bean : beanList){
            String beanId = bean.attributeValue(ID_ATTRIBUTE);
            String beanName = bean.attributeValue(NAME_ATTRIBUTE);
            String className = bean.attributeValue(CLASS_ATTRIBUTE);
            // 解析Bean生命周期相关属性
            String initMethodName = bean.attributeValue(INIT_METHOD_ATTRIBUTE);
            String destroyMethodName = bean.attributeValue(DESTROY_METHOD_ATTRIBUTE);
            
            // 解析Bean作用域属性
            // 支持的作用域：singleton（默认）、prototype
            String beanScope = bean.attributeValue(SCOPE_ATTRIBUTE);
            Class<?> clazz;
            try {
                clazz = Class.forName(className);
            } catch (ClassNotFoundException e) {
                throw new BeanException("Cannot find class [" + className + "]");
            }
            //id优先于name
            beanName = StrUtil.isNotEmpty(beanId) ? beanId : beanName;
            if (StrUtil.isEmpty(beanName)) {
                //如果id和name都为空，将类名的第一个字母转为小写后作为bean的名称
                beanName = StrUtil.lowerFirst(clazz.getSimpleName());
            }

            // 创建BeanDefinition并设置基本属性
            BeanDefinition beanDefinition = new BeanDefinition(clazz);
            beanDefinition.setInitMethodName(initMethodName);
            beanDefinition.setDestroyMethodName(destroyMethodName);
            
            // 设置Bean作用域
            // 如果未指定scope属性，则使用默认的单例作用域
            if (StrUtil.isNotEmpty(beanScope)){
                beanDefinition.setScope(beanScope);
            }
            // 注意：BeanDefinition构造时默认scope为singleton，无需额外设置
            List<Element> propertyList = bean.elements(PROPERTY_ELEMENT);

            for (Element property : propertyList) {
                String propertyNameAttribute = property.attributeValue(NAME_ATTRIBUTE);
                String propertyValueAttribute = property.attributeValue(VALUE_ATTRIBUTE);
                String propertyRefAttribute = property.attributeValue(REF_ATTRIBUTE);

                if (StrUtil.isEmpty(propertyNameAttribute)) {
                    throw new BeanException("The name attribute cannot be null or empty");
                }

                Object value = propertyValueAttribute;
                if (StrUtil.isNotEmpty(propertyRefAttribute)) {
                    value = new BeanReference(propertyRefAttribute);
                }
                PropertyValue propertyValue = new PropertyValue(propertyNameAttribute, value);
                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
            }

            if (getRegistry().containsBeanDefinition(beanName)) {
                //beanName不能重名
                throw new BeanException("Duplicate beanName[" + beanName + "] is not allowed");
            }
            //注册BeanDefinition
            getRegistry().registerBeanDefinition(beanName, beanDefinition);
        }
     }

    /**
     * 扫描注解Component的类，提取信息，组装成BeanDefinition
     *
     * @param scanPath
     */
    private void scanPackage(String scanPath) {
        String[] basePackages = StrUtil.splitToArray(scanPath, ',');
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(getRegistry());
        scanner.doScan(basePackages);
    }
}