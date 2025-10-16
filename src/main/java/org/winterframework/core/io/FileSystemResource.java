package org.winterframework.core.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

/**
 * 文件系统资源实现 - 用于访问文件系统中的资源
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/16
 * @description 文件系统资源实现，用于访问位于文件系统中的资源文件
 *              这是Winter Framework资源管理模块的重要实现类，类似于Spring中的FileSystemResource
 * 
 * 设计思想：
 * 1. 路径灵活：支持相对路径和绝对路径
 * 2. 异常处理：提供清晰的异常信息，便于调试
 * 3. 性能优化：使用NIO.2的Files.newInputStream()方法
 * 
 * 支持资源类型：
 * - 配置文件（XML、Properties、YAML等）
 * - 模板文件（HTML、JSP、Thymeleaf等）
 * - 静态资源（CSS、JS、图片等）
 * - 数据文件（CSV、JSON、XML等）
 * - 其他文件系统中的文件
 * 
 * 路径规则：
 * - 支持相对路径：如"config/app.properties"
 * - 支持绝对路径：如"/home/user/config/app.properties"
 * - 支持Windows路径：如"C:\\config\\app.properties"
 * - 支持Unix路径：如"/home/user/config/app.properties"
 * 
 * 使用场景：
 * - 外部配置文件加载
 * - 用户上传文件处理
 * - 临时文件访问
 * - 开发环境资源访问
 * 
 * 示例：
 * FileSystemResource resource = new FileSystemResource("config/database.properties");
 * InputStream inputStream = resource.getInputStream();
 * Properties props = new Properties();
 * props.load(inputStream);
 */
public class FileSystemResource implements Resource {

    /**
     * 文件系统路径
     * 可以是相对路径或绝对路径
     */
    private final String filePath;

    /**
     * 构造方法
     * 
     * @param filePath 文件系统路径，不能为null
     * @throws IllegalArgumentException 如果filePath为null或空字符串
     */
    public FileSystemResource(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }
        this.filePath = filePath;
    }

    /**
     * 获取资源的输入流
     * 
     * 通过Files.newInputStream()方法获取文件输入流。
     * 如果文件不存在，会抛出FileNotFoundException异常。
     * 
     * @return 文件的输入流
     * @throws FileNotFoundException 当文件不存在时抛出
     * @throws IOException 当读取文件时发生IO异常
     */
    @Override
    public InputStream getInputStream() throws IOException {
        try {
            Path path = new File(this.filePath).toPath();
            return Files.newInputStream(path);
        } catch (NoSuchFileException ex) {
            throw new FileNotFoundException(ex.getMessage());
        }
    }

    /**
     * 获取文件路径
     * 
     * @return 文件系统路径
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * 检查文件是否存在
     * 
     * @return 如果文件存在则返回true，否则返回false
     */
    public boolean exists() {
        return new File(this.filePath).exists();
    }

    /**
     * 重写toString方法，便于调试
     * 
     * @return 格式化的FileSystemResource信息
     */
    @Override
    public String toString() {
        return "FileSystemResource{" +
                "filePath='" + filePath + '\'' +
                '}';
    }
}