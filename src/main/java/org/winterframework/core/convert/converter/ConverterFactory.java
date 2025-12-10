package org.winterframework.core.convert.converter;

/**
 * 批量创建一组目标子类型转换器的工厂
 * @param <S> 源类型
 * @param <R> 目标父类型
 *
 * <p>典型用例：String -> Number 系列，通过 targetType 决定具体的数字类型。</p>
 */
public interface ConverterFactory<S,R> {

    <T extends R> Converter<S, T> getConverter(Class<T> targetType);
}
