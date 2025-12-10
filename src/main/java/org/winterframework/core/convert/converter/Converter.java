package org.winterframework.core.convert.converter;

/**
 * 单向类型转换器
 * @param <S> 源类型
 * @param <T> 目标类型
 *
 * <p>由 {@link org.winterframework.core.convert.support.GenericConversionService} 适配并统一管理。</p>
 */
public interface Converter<S,T> {

    /**
     * 类型转换
     */
    T convert(S source);
}
