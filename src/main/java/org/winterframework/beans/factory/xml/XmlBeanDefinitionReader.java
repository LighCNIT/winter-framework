package org.winterframework.beans.factory.xml;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.winterframework.beans.BeanException;
import org.winterframework.beans.factory.PropertyValue;
import org.winterframework.beans.factory.config.BeanDefinition;
import org.winterframework.beans.factory.config.BeanReference;
import org.winterframework.beans.factory.support.AbstractBeanDefinitionReader;
import org.winterframework.beans.factory.support.BeanDefinitionRegistry;
import org.winterframework.core.io.Resource;
import org.winterframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;

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
        } catch (IOException ex) {
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
    protected void doLoadBeanDefinitions(InputStream inputStream) throws BeanException {
        // 使用Hutool工具解析XML文档
        Document document = XmlUtil.readXML(inputStream);
        Element element = document.getDocumentElement();
        NodeList nodeList = element.getChildNodes();
        
        // 遍历所有子节点，查找bean元素
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i) instanceof Element) {
                if (BEAN_ELEMENT.equals(nodeList.item(i).getNodeName())) {
                    Element bean = (Element) nodeList.item(i);
                    
                    // 解析bean的基本属性
                    String id = bean.getAttribute(ID_ATTRIBUTE);
                    String name = bean.getAttribute(NAME_ATTRIBUTE);
                    String className = bean.getAttribute(CLASS_ATTRIBUTE);
                    
                    // 验证class属性必须存在
                    if (StrUtil.isEmpty(className)) {
                        throw new BeanException("Bean class attribute is required");
                    }
                    
                    // 加载Bean的Class对象
                    Class<?> clazz = null;
                    try {
                        clazz = Class.forName(className);
                    } catch (ClassNotFoundException e) {
                        throw new BeanException("Cannot find class [" + className + "]");
                    }
                    
                    // 确定Bean名称：优先使用id，其次name，最后使用类名首字母小写
                    String beanName = StrUtil.isNotBlank(id) ? id : name;
                    if (StrUtil.isEmpty(beanName)) {
                        beanName = StrUtil.lowerFirst(clazz.getSimpleName());
                    }
                    
                    // 创建Bean定义对象
                    BeanDefinition beanDefinition = new BeanDefinition(clazz);
                    
                    // 解析property子元素
                    for (int j = 0; j < bean.getChildNodes().getLength(); j++) {
                        if (bean.getChildNodes().item(j) instanceof Element) {
                            if (PROPERTY_ELEMENT.equals(bean.getChildNodes().item(j).getNodeName())) {
                                Element property = (Element) bean.getChildNodes().item(j);
                                String nameAttribute = property.getAttribute(NAME_ATTRIBUTE);
                                String valueAttribute = property.getAttribute(VALUE_ATTRIBUTE);
                                String refAttribute = property.getAttribute(REF_ATTRIBUTE);
                                
                                // 验证name属性必须存在
                                if (StrUtil.isEmpty(nameAttribute)) {
                                    throw new BeanException("The name attribute cannot be null or empty");
                                }
                                
                                // 确定属性值：优先使用ref引用，其次使用value值
                                Object value = valueAttribute;
                                if (StrUtil.isNotEmpty(refAttribute)) {
                                    value = new BeanReference(refAttribute);
                                }
                                
                                // 创建属性值对象并添加到Bean定义中
                                PropertyValue propertyValue = new PropertyValue(nameAttribute, value);
                                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
                            }
                        }
                    }
                    
                    // 检查Bean名称是否重复
                    if (getRegistry().containBeanDefinition(beanName)) {
                        throw new BeanException("Duplicate beanName[" + beanName + "] is not allowed");
                    }
                    
                    // 注册Bean定义到注册表
                    getRegistry().registerBeanDefinition(beanName, beanDefinition);
                }
            }
        }
    }
}