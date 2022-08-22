package com.maple.demo.controller;

import com.maple.demo.service.IUserService;
import com.maple.demo.util.JwtUtil;
import com.maple.demo.vo.model.UserModel;
import com.maple.demo.vo.query.LoginQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 系统登录
 *
 * @author 笑小枫
 * @date 2022/7/20
 * @see <a href="https://www.xiaoxiaofeng.com">https://www.xiaoxiaofeng.com</a>
 */
@Api(tags = "管理系统-系统登录操作")
@RestController
@AllArgsConstructor
@RequestMapping(value = "/sso")
public class LoginController {

    private final IUserService userService;

    @ApiOperation(value = "用户登录")
    @PostMapping("/login")
    public UserModel login(@RequestBody LoginQuery req) {
        return userService.login(req);
    }

    @ApiOperation(value = "用户退出登录")
    @GetMapping("/logout")
    public void logout() {
        userService.logout();
    }

    @ApiOperation(value = "获取登录用户信息")
    @GetMapping("/getUserId")
    public String getUserId() {
        return "当前登录用户的ID为" + JwtUtil.getUserId();
    }
}