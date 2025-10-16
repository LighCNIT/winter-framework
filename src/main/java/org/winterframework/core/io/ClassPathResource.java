package org.winterframework.core.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * ClassPath资源实现 - 用于访问classpath下的资源
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/16
 * @description classpath下的资源实现，用于访问位于classpath中的资源文件
 *              这是Winter Framework资源管理模块的重要实现类，类似于Spring中的ClassPathResource
 * 
 * 设计思想：
 * 1. 简单高效：直接使用ClassLoader.getResourceAsStream()方法
 * 2. 路径相对：使用相对于classpath的路径，无需绝对路径
 * 3. 异常明确：当资源不存在时提供清晰的异常信息
 * 
 * 支持资源类型：
 * - 配置文件（XML、Properties、YAML等）
 * - 模板文件（HTML、JSP、Thymeleaf等）
 * - 静态资源（CSS、JS、图片等）
 * - 其他classpath中的文件
 * 
 * 路径规则：
 * - 使用相对于classpath的路径，如"config/app.properties"
 * - 路径分隔符使用"/"，如"templates/index.html"
 * - 不需要以"/"开头，如"hello.txt"而不是"/hello.txt"
 * 
 * 使用场景：
 * - 配置文件加载
 * - 模板文件读取
 * - 静态资源访问
 * - 测试资源文件读取
 * 
 * 示例：
 * ClassPathResource resource = new ClassPathResource("config/database.properties");
 * InputStream inputStream = resource.getInputStream();
 * Properties props = new Properties();
 * props.load(inputStream);
 */
public class ClassPathResource implements Resource {

    /**
     * 资源在classpath中的相对路径
     * 例如："config/app.properties"、"templates/index.html"
     */
    private final String path;

    /**
     * 构造方法
     * 
     * @param path 资源在classpath中的相对路径，不能为null
     * @throws IllegalArgumentException 如果path为null或空字符串
     */
    public ClassPathResource(String path) {
        if (path == null || path.trim().isEmpty()) {
            throw new IllegalArgumentException("Path cannot be null or empty");
        }
        this.path = path;
    }

    /**
     * 获取资源的输入流
     * 
     * 通过ClassLoader.getResourceAsStream()方法获取资源输入流。
     * 如果资源不存在，会抛出FileNotFoundException异常。
     * 
     * @return 资源的输入流
     * @throws FileNotFoundException 当资源不存在时抛出
     * @throws IOException 当读取资源时发生IO异常
     */
    @Override
    public InputStream getInputStream() throws IOException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(this.path);
        if (inputStream == null) {
            throw new FileNotFoundException(this.path + " cannot be opened because it does not exist");
        }
        return inputStream;
    }

    /**
     * 获取资源路径
     * 
     * @return 资源在classpath中的相对路径
     */
    public String getPath() {
        return path;
    }

    /**
     * 重写toString方法，便于调试
     * 
     * @return 格式化的ClassPathResource信息
     */
    @Override
    public String toString() {
        return "ClassPathResource{" +
                "path='" + path + '\'' +
                '}';
    }
}