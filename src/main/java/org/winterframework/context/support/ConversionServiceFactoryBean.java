package org.winterframework.context.support;

import org.winterframework.beans.BeanException;
import org.winterframework.beans.factory.FactoryBean;
import org.winterframework.beans.factory.InitializingBean;
import org.winterframework.core.convert.ConversionService;
import org.winterframework.core.convert.converter.Converter;
import org.winterframework.core.convert.converter.ConverterFactory;
import org.winterframework.core.convert.converter.ConverterRegistry;
import org.winterframework.core.convert.converter.GenericConverter;
import org.winterframework.core.convert.support.DefaultConversionService;
import org.winterframework.core.convert.support.GenericConversionService;

import java.util.Set;

/**
 * 提供可配置的 ConversionService 工厂Bean
 *
 * <p>功能：</p>
 * <ul>
 *   <li>默认实例化 {@link DefaultConversionService}，内置基础转换器</li>
 *   <li>支持通过属性 converters 注册自定义 Converter / ConverterFactory / GenericConverter</li>
 *   <li>以单例形式暴露到容器，供 BeanFactory 的类型转换调用</li>
 * </ul>
 *
 * <p>典型用法：在上下文配置中声明本 FactoryBean，并通过 setConverters 注入自定义转换器集合。</p>
 *
 * @author Ligh
 * 2025/12/10 20:53
 **/
public class ConversionServiceFactoryBean implements FactoryBean<ConversionService>, InitializingBean {

    private Set<?> converters;

    private GenericConversionService conversionService;

    @Override
    public ConversionService getObject() throws BeanException {
        return conversionService;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws BeanException {
        conversionService = new DefaultConversionService();
        registerConverters(converters, conversionService);
    }

    /**
     * 将用户提供的转换器集合注册到 ConversionService
     */
    private void registerConverters(Set<?> converters, ConverterRegistry registry) {
        if (converters != null) {
            for (Object converter : converters) {
                if (converter instanceof GenericConverter) {
                    registry.addConverter((GenericConverter) converter);
                } else if (converter instanceof Converter<?, ?>) {
                    registry.addConverter((Converter<?, ?>) converter);
                } else if (converter instanceof ConverterFactory<?, ?>) {
                    registry.addConverterFactory((ConverterFactory<?, ?>) converter);
                } else {
                    throw new IllegalArgumentException("Each converter object must implement one of the " +
                            "Converter, ConverterFactory, or GenericConverter interfaces");
                }
            }
        }
    }

    public void setConverters(Set<?> converters) {
        this.converters = converters;
    }
}
