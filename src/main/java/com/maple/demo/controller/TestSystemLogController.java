package com.maple.demo.controller;

import com.maple.demo.config.annotation.MapleLog;
import com.maple.demo.config.bean.ErrorCode;
import com.maple.demo.config.enums.BusinessTypeEnum;
import com.maple.demo.config.enums.OperateTypeEnum;
import com.maple.demo.config.exception.MapleCommonException;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

/**
 * @author 笑小枫
 * @date 2022/7/21
 */
@RestController
@RequestMapping("/example")
public class TestSystemLogController {

    @ApiOperation(value = "测试带参数、有返回结果的get请求")
    @GetMapping("/testGetLog/{id}")
    @MapleLog(businessType = BusinessTypeEnum.OTHER, operateType = OperateTypeEnum.OTHER)
    public Test testGetLog(@PathVariable Integer id) {
        Test test = new Test();
        test.setName("笑小枫");
        test.setAge(18);
        test.setRemark("大家好，我是笑小枫，喜欢我的小伙伴点个赞呗");
        return test;
    }

    @ApiOperation(value = "测试json参数、抛出异常的post请求")
    @PostMapping("/testPostLog")
    @MapleLog(businessType = BusinessTypeEnum.OTHER, operateType = OperateTypeEnum.OTHER, saveResult = false)
    public Test testPostLog(@RequestBody Test param) {
        Test test = new Test();
        test.setName("笑小枫");
        if (test.getAge() == null) {
            // 这里使用了自定义异常，测试可以直接抛出RuntimeException
            throw new MapleCommonException(ErrorCode.COMMON_ERROR);
        }
        test.setRemark("大家好，我是笑小枫，喜欢我的小伙伴点个赞呗");
        return test;
    }

    @Data
    static class Test {
        private String name;

        private Integer age;

        private String remark;
    }
}
