package com.maple.demo.controller;

import com.maple.demo.config.bean.XxfProperties;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 笑小枫
 * @date 2022/7/22
 * @see <a href="https://www.xiaoxiaofeng.com">https://www.xiaoxiaofeng.com</a>
 */
@Slf4j
@RestController
@RequestMapping("/example")
@RequiredArgsConstructor
@Api(tags = "实例演示-application文件配置")
public class TestConfigController {

    /**
     * 通用-不区分环境
     */
    @Value("${xxf.name}")
    private String name;
    @Value("${xxf.site}")
    private String site;

    /**
     * 区分环境
     */
    @Value("${xxf.desc}")
    private String desc;

    /**
     * 同组配置，可以使用类的形式注入
     */
    private final XxfProperties xxfProperties;

    @ApiOperation(value = "不同环境的配置测试用例")
    @GetMapping("/configEnv")
    public String chooseEnv() {
        return desc;
    }

    @ApiOperation(value = "配置包含的测试用例")
    @GetMapping("/commonEnv")
    public String commonEnv() {
        return name + site;
    }

    @ApiOperation(value = "用类统一使用配置的测试用例")
    @GetMapping("/configClass")
    public String configClass() {
        return xxfProperties.getName();
    }

}
