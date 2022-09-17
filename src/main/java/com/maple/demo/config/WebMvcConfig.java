package com.maple.demo.config;

import com.maple.demo.config.bean.FileProperties;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 笑小枫
 * @date 2022/7/22
 * @see <a href="https://www.xiaoxiaofeng.com">https://www.xiaoxiaofeng.com</a>
 */
@Configuration
@AllArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final FileProperties fileProperties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //重写方法
        //修改tomcat 虚拟映射
        //定义图片存放路径 这里加/example是为了测试避开系统的token校验，实际访问地址根据自己需求来
        registry.addResourceHandler("/example/images/**").
                addResourceLocations("file:" + fileProperties.getImageFilePath());
        //定义文档存放路径
        registry.addResourceHandler("/example/doc/**").
                addResourceLocations("file:" + fileProperties.getDocFilePath());
    }
}
