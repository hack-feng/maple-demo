package com.maple.demo.controller;

import com.maple.demo.entity.User;
import com.maple.demo.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户中心-用户信息表 前端控制器
 * </p>
 *
 * @author 笑小枫
 * @since 2022-07-11
 * @see <a href="https://www.xiaoxiaofeng.com">https://www.xiaoxiaofeng.com</a>
 */
@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Api(tags = "用户信息管理")
public class UserController {

    private final IUserService userService;

    @ApiOperation(value = "插入用户信息")
    @PostMapping("/insert")
    public User insert(@RequestBody User user){
         userService.save(user);
         return user;
    }

    @ApiOperation(value = "根据用户ID查询用户信息")
    @GetMapping("/selectById")
    public User selectById(Long userId){
        return userService.getById(userId);
    }

}
