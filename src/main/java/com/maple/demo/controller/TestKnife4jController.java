package com.maple.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 笑小枫
 * @see <a href="https://www.xiaoxiaofeng.com">https://www.xiaoxiaofeng.com</a>
 * @date 2022/6/30
 */
@Api(tags = "实例演示-Knife4j接口文档")
@RestController
@RequestMapping("/example")
public class TestKnife4jController {

    @ApiOperation(value = "Knife4j接口文档演示")
    @GetMapping("/testKnife4j")
    public Test testKnife4j(Test param) {
        Test test = new Test();
        test.setName("笑小枫");
        test.setAge(18);
        test.setRemark("大家好，我是笑小枫，喜欢我的小伙伴点个赞呗，欢迎访问我的个人博客：https://www.xiaoxiaofeng.com");
        return test;
    }

    @Data
    static class Test {
        @ApiModelProperty(value = "姓名")
        private String name;

        @ApiModelProperty(value = "年龄")
        private Integer age;

        @ApiModelProperty(value = "描述")
        private String remark;
    }
}