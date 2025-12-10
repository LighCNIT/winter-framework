package org.winterframework.core.convert;

/**
 * 统一的类型转换入口
 *
 * <p>用于Bean属性填充、@Value 注入等场景，屏蔽具体转换器实现，按源/目标类型选择合适的转换器。</p>
 *
 * @author Ligh
 * 2025/11/11 21:36
 **/
public interface ConversionService {

    boolean canConvert(Class<?> sourceType, Class<?> targetType);

    <T> T convert(Object source, Class<T> targetType);
}
