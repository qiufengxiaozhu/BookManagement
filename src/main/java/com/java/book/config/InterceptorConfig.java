package com.java.book.config;

import com.java.book.common.Constant;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 邱高强
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    /**
     *  添加静态资源文件，外部可以直接访问地址
     *  资源映射路径
     *  addResourceHandler：访问映射路径
     *  addResourceLocations：资源绝对路径
     * @param registry registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // 第一个参数就是存入数据库的URL，第二个参数为本地真实路径
        registry.addResourceHandler(Constant.UPLOAD_FILE_URL + "**").addResourceLocations("file:" + Constant.UPLOAD_FILE_PATH);
    }
}
