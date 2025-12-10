package org.winterframework.core.convert.support;

import org.winterframework.core.convert.converter.ConverterRegistry;

/**
 * 框架默认的 ConversionService 实现
 *
 * <p>启动时自动注册框架内置的转换器集合（当前仅 String -> Number 工厂），
 * 便于开箱即用地支持 @Value、属性填充的基础类型转换。</p>
 *
 * @author Ligh
 * 2025/12/10 20:56
 **/
public class DefaultConversionService extends GenericConversionService{

    public DefaultConversionService() {
        addDefaultConverters(this);
    }

    /**
     * 注册框架默认的 Converter/ConverterFactory 集合
     */
    public static void addDefaultConverters(ConverterRegistry converterRegistry) {
        converterRegistry.addConverterFactory(new StringToNumberConverterFactory());
        //TODO 添加其他ConverterFactory
    }
}
