package org.winterframework.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * 资源访问接口 - 定义资源访问的统一规范
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/16
 * @description 资源的抽象和访问接口，定义了访问各种资源（文件、URL、classpath等）的统一规范
 *              这是Winter Framework资源管理模块的核心接口，类似于Spring中的Resource接口
 * 
 * 设计思想：
 * 1. 统一抽象：为不同类型的资源提供统一的访问接口
 * 2. 简单易用：只定义最核心的getInputStream()方法
 * 3. 扩展性强：可以轻松添加新的资源类型实现
 * 
 * 支持资源类型：
 * - ClassPathResource：classpath下的资源（如配置文件）
 * - FileSystemResource：文件系统中的资源
 * - UrlResource：网络URL资源
 * 
 * 使用场景：
 * - 配置文件加载（XML、Properties等）
 * - 模板文件读取（HTML、XML等）
 * - 网络资源访问
 * - 文件系统资源访问
 * 
 * 示例：
 * Resource resource = resourceLoader.getResource("classpath:config.xml");
 * InputStream inputStream = resource.getInputStream();
 * String content = IoUtil.readUtf8(inputStream);
 */
public interface Resource {

    /**
     * 获取资源的输入流
     * 
     * 这是Resource接口的核心方法，用于获取资源的输入流。
     * 调用者可以通过输入流读取资源的内容。
     * 
     * @return 资源的输入流
     * @throws IOException 当资源不存在或无法访问时抛出
     */
    InputStream getInputStream() throws IOException;
}
