package org.winterframework.test;

import cn.hutool.core.io.IoUtil;
import org.junit.Test;
import org.winterframework.core.io.DefaultResourceLoader;
import org.winterframework.core.io.FileSystemResource;
import org.winterframework.core.io.Resource;
import org.winterframework.core.io.UrlResource;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Resource和ResourceLoader测试类
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/16
 * @description 测试Winter Framework的资源管理功能
 *              验证ResourceLoader能够正确加载不同类型的资源
 * 
 * 测试场景：
 * 1. ClassPath资源加载：验证classpath:前缀的资源加载
 * 2. 文件系统资源加载：验证文件系统路径的资源加载
 * 3. URL资源加载：验证网络URL资源的加载
 * 
 * 技术要点：
 * - ResourceLoader的策略模式应用
 * - 不同Resource实现类的功能验证
 * - 资源内容读取和验证
 * 
 * 注意事项：
 * - 需要确保hello.txt文件存在于src/test/resources/目录下
 * - 网络资源测试需要网络连接
 * - 文件路径需要根据实际项目结构调整
 */
public class ResourceAndResourceLoaderTest {

    /**
     * 测试ResourceLoader的完整功能
     * 
     * 测试步骤：
     * 1. 创建DefaultResourceLoader实例
     * 2. 测试classpath资源加载（hello.txt）
     * 3. 测试文件系统资源加载
     * 4. 测试URL资源加载
     * 
     * 验证点：
     * - Resource类型正确（ClassPathResource、FileSystemResource、UrlResource）
     * - 资源内容正确读取
     * - 异常处理正确
     * 
     * @throws Exception 测试过程中的异常
     */
    @Test
    public void testResourceLoader() throws Exception {
        // 1. 创建资源加载器
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();

        // 2. 测试classpath资源加载
        Resource resource = resourceLoader.getResource("classpath:hello.txt");
        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println("ClassPath资源内容: " + content);
        assertThat(content).isEqualTo("hello world!!");

        // 3. 测试文件系统资源加载
        resource = resourceLoader.getResource("src/test/resources/hello.txt");
        assertThat(resource instanceof FileSystemResource).isTrue();
        inputStream = resource.getInputStream();
        content = IoUtil.readUtf8(inputStream);
        System.out.println("文件系统资源内容: " + content);
        assertThat(content).isEqualTo("hello world!!");

        // 4. 测试URL资源加载
        resource = resourceLoader.getResource("https://www.baidu.com");
        assertThat(resource instanceof UrlResource).isTrue();
        inputStream = resource.getInputStream();
        content = IoUtil.readUtf8(inputStream);
        System.out.println("URL资源内容长度: " + content.length());
        // 验证返回的是HTML内容（包含百度相关标识）
        assertThat(content).contains("百度");
        
        System.out.println("✅ Resource和ResourceLoader测试通过！");
    }
}