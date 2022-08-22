package com.maple.demo.config.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author 笑小枫
 * @date 2022/7/22
 * @see <a href="https://www.xiaoxiaofeng.com">https://www.xiaoxiaofeng.com</a>
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "xxf")
public class XxfProperties {

    private String name;

    private String site;
}
