package com.maple.demo.controller;

import com.maple.demo.util.ResultJson;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 笑小枫
 * @see <a href="https://www.xiaoxiaofeng.com">https://www.xiaoxiaofeng.com</a>
 * @date 2022/07/15
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/example")
public class TestResultController {

    @GetMapping("/testResult")
    public Test testResult() {
        Test test = new Test();
        test.setName("笑小枫");
        test.setAge(18);
        test.setRemark("大家好，我是笑小枫，喜欢我的小伙伴点个赞呗");
        return test;
    }

    /**
     * 普通的返回
     */
    @GetMapping("/testResultJson")
    public ResultJson testResultJson() {
        Test test = new Test();
        test.setName("笑小枫");
        test.setAge(18);
        test.setRemark("大家好，我是笑小枫，喜欢我的小伙伴点个赞呗");
        return new ResultJson(test);
    }

    @Data
    static class Test {
        private String name;

        private Integer age;

        private String remark;
    }
}
