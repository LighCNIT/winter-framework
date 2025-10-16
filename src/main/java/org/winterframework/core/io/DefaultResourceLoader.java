package org.winterframework.core.io;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 默认资源加载器 - ResourceLoader接口的标准实现
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/16
 * @description 默认的资源加载器实现，根据资源位置字符串的不同前缀选择相应的Resource实现
 *              这是Winter Framework资源管理模块的核心实现类，类似于Spring中的DefaultResourceLoader
 * 
 * 设计思想：
 * 1. 策略模式：根据location前缀选择不同的Resource实现策略
 * 2. 容错处理：当URL解析失败时，自动降级为文件系统资源
 * 3. 简单高效：只处理最常用的资源类型，保持实现简单
 * 
 * 支持资源类型：
 * - ClassPathResource：classpath:前缀的资源
 * - UrlResource：有效的URL资源（http://、https://等）
 * - FileSystemResource：文件系统资源（默认降级处理）
 * 
 * 资源位置解析规则：
 * 1. "classpath:path" → ClassPathResource
 * 2. "http://..." 或 "https://..." → UrlResource
 * 3. 其他格式 → FileSystemResource（降级处理）
 * 
 * 使用场景：
 * - 配置文件加载（XML、Properties等）
 * - 模板文件读取
 * - 网络资源访问
 * - 文件系统资源访问
 * 
 * 示例：
 * ResourceLoader loader = new DefaultResourceLoader();
 * Resource resource = loader.getResource("classpath:config.xml");
 * InputStream inputStream = resource.getInputStream();
 */
public class DefaultResourceLoader implements ResourceLoader {

    /**
     * classpath资源位置前缀
     * 用于标识资源位于classpath中
     */
    public static final String CLASSPATH_URL_PREFIX = "classpath:";

    /**
     * 根据资源位置字符串获取Resource对象
     * 
     * 实现策略：
     * 1. 如果location以"classpath:"开头，创建ClassPathResource
     * 2. 如果location是有效的URL，创建UrlResource
     * 3. 其他情况，创建FileSystemResource（降级处理）
     * 
     * @param location 资源位置字符串
     * @return 相应的Resource实现对象
     */
    @Override
    public Resource getResource(String location) {
        // 处理classpath资源
        if (location.startsWith(CLASSPATH_URL_PREFIX)) {
            return new ClassPathResource(location.substring(CLASSPATH_URL_PREFIX.length()));
        } else {
            try {
                // 尝试解析为URL资源
                URL url = new URL(location);
                return new UrlResource(url);
            } catch (MalformedURLException ex) {
                // URL解析失败，降级为文件系统资源处理
                return new FileSystemResource(location);
            }
        }
    }
}