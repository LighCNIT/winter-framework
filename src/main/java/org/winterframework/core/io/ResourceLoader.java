package org.winterframework.core.io;

/**
 * 资源加载器接口 - 定义资源加载的统一规范
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/16
 * @description 资源加载器接口，定义了根据资源位置字符串加载Resource对象的规范
 *              这是Winter Framework资源管理模块的核心接口，类似于Spring中的ResourceLoader接口
 * 
 * 设计思想：
 * 1. 策略模式：根据资源位置字符串的不同前缀，选择不同的资源加载策略
 * 2. 统一接口：为不同类型的资源提供统一的加载入口
 * 3. 扩展性强：可以轻松添加新的资源类型支持
 * 
 * 支持资源位置格式：
 * - "classpath:path/to/resource"：classpath下的资源
 * - "file://path/to/resource"：文件系统资源
 * - "http://example.com/resource"：网络URL资源
 * - "path/to/resource"：默认按文件系统资源处理
 * 
 * 实现类：
 * - DefaultResourceLoader：默认的资源加载器实现
 * 
 * 使用场景：
 * - 配置文件加载
 * - 模板文件读取
 * - 网络资源访问
 * - 文件系统资源访问
 * 
 * 示例：
 * ResourceLoader loader = new DefaultResourceLoader();
 * Resource resource = loader.getResource("classpath:config.xml");
 * InputStream inputStream = resource.getInputStream();
 */
public interface ResourceLoader {

    /**
     * 根据资源位置字符串获取Resource对象
     * 
     * 根据location参数的不同格式，返回相应的Resource实现：
     * - classpath:前缀：返回ClassPathResource
     * - http://或https://前缀：返回UrlResource
     * - 其他格式：返回FileSystemResource
     * 
     * @param location 资源位置字符串，支持多种格式
     * @return Resource对象，用于访问资源内容
     */
    Resource getResource(String location);
}
