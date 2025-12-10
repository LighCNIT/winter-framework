package org.winterframework.core.convert.converter;

/**
 * 类型转换器注册接口
 *
 * <p>由 {@link org.winterframework.core.convert.support.GenericConversionService} 实现，
 * 用于向 ConversionService 挂载 Converter、ConverterFactory 或 GenericConverter。</p>
 */
public interface ConverterRegistry {

    void addConverter(Converter<?,?> converter);

    void addConverterFactory(ConverterFactory<?,?> converterFactory);

    void addConverter(GenericConverter converter);
}
