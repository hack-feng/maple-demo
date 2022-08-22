package com.maple.demo.controller;

import com.maple.demo.config.bean.ErrorCode;
import com.maple.demo.config.exception.MapleCheckException;
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
public class TestErrorResultController {

    /**
     * 模拟系统空指针异常
     */
    @GetMapping("/testResultError")
    public Test testResultError() {
        Test test = null;
        test.setName("简单点，抛个空指针吧");
        return test;
    }

    /**
     * 模拟自定义异常
     */
    @GetMapping("/testMapleError")
    public Test testMapleError() {
        Test test = new Test();
        test.setName("笑小枫");
        if (test.getAge() == null) {
            throw new MapleCheckException(ErrorCode.COMMON_ERROR);
        }
        return test;
    }

    @Data
    static class Test {
        private String name;

        private Integer age;

        private String remark;
    }
}
