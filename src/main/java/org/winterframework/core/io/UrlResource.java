package org.winterframework.core.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * URL资源实现 - 用于访问网络URL资源
 * 
 * @author Ligh
 * @version JDK 8
 * @date 2025/10/16
 * @description URL资源实现，用于访问位于网络上的资源文件
 *              这是Winter Framework资源管理模块的重要实现类，类似于Spring中的UrlResource
 * 
 * 设计思想：
 * 1. 网络访问：支持HTTP、HTTPS等网络协议
 * 2. 连接管理：使用URLConnection进行连接管理
 * 3. 异常传播：直接传播网络IO异常，便于上层处理
 * 
 * 支持协议：
 * - HTTP：http://example.com/resource
 * - HTTPS：https://example.com/resource
 * - FTP：ftp://example.com/resource
 * - FILE：file:///path/to/resource
 * - 其他Java支持的URL协议
 * 
 * 使用场景：
 * - 远程配置文件加载
 * - 网络模板文件读取
 * - API响应数据获取
 * - 远程资源文件下载
 * - 分布式配置中心访问
 * 
 * 注意事项：
 * - 需要网络连接
 * - 可能受到网络延迟影响
 * - 需要处理网络异常
 * - 某些URL可能需要认证
 * 
 * 示例：
 * URL url = new URL("https://example.com/config/app.properties");
 * UrlResource resource = new UrlResource(url);
 * InputStream inputStream = resource.getInputStream();
 * Properties props = new Properties();
 * props.load(inputStream);
 */
public class UrlResource implements Resource {

    /**
     * 资源URL
     * 支持HTTP、HTTPS、FTP等协议
     */
    private final URL url;

    /**
     * 构造方法
     * 
     * @param url 资源URL，不能为null
     * @throws IllegalArgumentException 如果url为null
     */
    public UrlResource(URL url) {
        if (url == null) {
            throw new IllegalArgumentException("URL cannot be null");
        }
        this.url = url;
    }

    /**
     * 获取资源的输入流
     * 
     * 通过URLConnection.openConnection()方法建立网络连接，
     * 然后获取输入流。如果网络连接失败或资源不存在，会抛出IOException异常。
     * 
     * @return 资源的输入流
     * @throws IOException 当网络连接失败或读取资源时发生IO异常
     */
    @Override
    public InputStream getInputStream() throws IOException {
        URLConnection urlConnection = this.url.openConnection();
        try {
            return urlConnection.getInputStream();
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * 获取资源URL
     * 
     * @return 资源URL
     */
    public URL getUrl() {
        return url;
    }

    /**
     * 获取URL字符串
     * 
     * @return URL的字符串表示
     */
    public String getUrlString() {
        return url.toString();
    }

    /**
     * 重写toString方法，便于调试
     * 
     * @return 格式化的UrlResource信息
     */
    @Override
    public String toString() {
        return "UrlResource{" +
                "url='" + url + '\'' +
                '}';
    }
}