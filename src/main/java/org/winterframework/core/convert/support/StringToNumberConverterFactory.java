package org.winterframework.core.convert.support;

import org.winterframework.core.convert.converter.Converter;
import org.winterframework.core.convert.converter.ConverterFactory;

/**
 * 一个简易的 字符串 => 数字 的转换器
 *
 * <p>支持 Integer、Long 等常见包装类型，空字符串会转换为 null；其余数字类型可按需补充。</p>
 *
 * @author Ligh
 * 2025/12/10 20:57
 **/
public class StringToNumberConverterFactory implements ConverterFactory<String,Number> {
    @Override
    public <T extends Number> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToNumber<T>(targetType);
    }

    private static final class StringToNumber<T extends Number> implements Converter<String,T>{

        private final Class<T> targetType;

        public StringToNumber(Class<T> targetType) {
            this.targetType = targetType;
        }

        @Override
        public T convert(String source) {
            if (source.length() == 0) {
                return null;
            }

            if (targetType.equals(Integer.class)) {
                return (T) Integer.valueOf(source);
            } else if (targetType.equals(Long.class)) {
                return (T) Long.valueOf(source);
            }
            //TODO 其他数字类型

            else {
                throw new IllegalArgumentException(
                        "Cannot convert String [" + source + "] to target class [" + targetType.getName() + "]");
            }
        }
    }
}
