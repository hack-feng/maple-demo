package com.maple.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.maple.demo.entity.User;
import com.maple.demo.vo.model.UserModel;
import com.maple.demo.vo.query.LoginQuery;

/**
 * <p>
 * 用户中心-用户信息表 服务类
 * </p>
 *
 * @author 笑小枫
 * @since 2022-07-11
 */
public interface IUserService extends IService<User> {
    /**
     * 用户登录
     *
     * @param req 用户信息
     * @return 用户登录信息
     */
    UserModel login(LoginQuery req);

    /**
     * 退出系统，清除用户token
     */
    void logout();
}
