package com.maple.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 启动测试
 *
 * @author 笑小枫
 * @date 2022/7/10
 * @see <a href="https://www.xiaoxiaofeng.com">https://www.xiaoxiaofeng.com</a>
 */
@RestController
public class TestController {

    @GetMapping(value = "test")
    public String test(){
        return "项目启动成功";
    }
}
