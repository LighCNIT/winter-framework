package org.winterframework.beans.factory.annotation;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.TypeUtil;
import org.winterframework.beans.BeanException;
import org.winterframework.beans.PropertyValues;
import org.winterframework.beans.factory.BeanFactory;
import org.winterframework.beans.factory.BeanFactoryAware;
import org.winterframework.beans.factory.ConfigurableListableBeanFactory;
import org.winterframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.winterframework.core.convert.ConversionService;

import java.lang.reflect.Field;

/**
 * 处理 @Autowired / @Value 注解的后置处理器
 *
 * <p>核心职责：</p>
 * <ul>
 *   <li>解析字段上的 @Value 占位符，支持通过 {@link ConfigurableListableBeanFactory#resolveEmbeddedValue(String)} 注入外部化配置</li>
 *   <li>在存在 {@link ConversionService} 时，完成 @Value 注入值的类型转换（如 String → Integer）</li>
 *   <li>解析 @Autowired/@Qualifier，按类型或指定 Bean 名称注入依赖</li>
 * </ul>
 *
 * <p>使用时机：在属性填充阶段触发，优先完成 @Value 注入和类型转换，再执行 @Autowired 注入。</p>
 *
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/27
 */
public class AutowiredAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

    private ConfigurableListableBeanFactory beanFactory;
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeanException {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeanException {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeanException {
        return null;
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String Name) throws BeanException {
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeanException {
        return true;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues propertyValues, Object bean, String beanName) throws BeanException {
        Class<?> clazz = bean.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields){
            Value valueAnnotation = field.getAnnotation(Value.class);
            if (valueAnnotation != null){
                Object value = valueAnnotation.value();
                value = beanFactory.resolveEmbeddedValue((String) value);

                // 如果配置了转换器，优先用ConversionService完成类型匹配（如String->Number）
                Class<?> sourceType = value.getClass();
                Class<?> targetType = (Class<?>) TypeUtil.getType(field);
                ConversionService conversionService = beanFactory.getConversionService();
                if (conversionService != null) {
                    if (conversionService.canConvert(sourceType, targetType)) {
                        value = conversionService.convert(value, targetType);
                    }
                }
                BeanUtil.setFieldValue(bean,field.getName(),value);
            }
        }

        for (Field field : fields){
            Autowired  autowiredAnnotation  = field.getAnnotation(Autowired .class);
            if (autowiredAnnotation != null){
                Class<?> fieldType = field.getType();
                String dependentBeanName = null;
                Qualifier qualifierAnnotation = field.getAnnotation(Qualifier.class);
                Object dependentBean = null;
                if (qualifierAnnotation != null){
                    dependentBeanName = qualifierAnnotation.value();;
                    dependentBean = beanFactory.getBean(dependentBeanName,fieldType);
                }else {
                    dependentBean = beanFactory.getBean(fieldType);
                }
                BeanUtil.setFieldValue(bean, field.getName(), dependentBean);
            }
        }


        return propertyValues;
    }
}