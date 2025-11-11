package org.winterframework.core.convert;

/**
 * 类型转换抽象接口
 * @author Ligh
 * 2025/11/11 21:36
 **/
public interface ConversionService {

    boolean canConvert(Class<?> sourceType, Class<?> targetType);

    <T> T convert(Object source, Class<T> targetType);
}
