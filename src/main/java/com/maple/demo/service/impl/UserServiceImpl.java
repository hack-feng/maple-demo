package com.maple.demo.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.maple.demo.config.bean.ErrorCode;
import com.maple.demo.config.bean.GlobalConfig;
import com.maple.demo.config.bean.TokenBean;
import com.maple.demo.config.exception.MapleCheckException;
import com.maple.demo.entity.User;
import com.maple.demo.mapper.UserMapper;
import com.maple.demo.service.IUserService;
import com.maple.demo.util.JwtUtil;
import com.maple.demo.util.common.Md5Util;
import com.maple.demo.util.RedisUtil;
import com.maple.demo.vo.model.UserModel;
import com.maple.demo.vo.query.LoginQuery;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;


/**
 * <p>
 * 用户中心-用户信息表 服务实现类
 * </p>
 *
 * @author Maple
 * @since 2022-07-11
 */
@Service
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final UserMapper userMapper;

    private final RedisUtil redisUtil;

    @Override
    public UserModel login(LoginQuery req) {
        User user = userMapper.selectOne(Wrappers.lambdaQuery(User.class)
                .eq(User::getAccount, req.getAccount())
                .last("LIMIT 1"));
        if (Objects.isNull(user)) {
            throw new MapleCheckException(ErrorCode.USER_LOGIN_ERROR);
        }
        if ("1".equals(user.getStatus())) {
            throw new MapleCheckException(ErrorCode.USER_STATUS_ERROR);
        }
        if (!Md5Util.verifyPassword(req.getPassword(), user.getPassword(), user.getSalt())) {
            throw new MapleCheckException(ErrorCode.USER_LOGIN_ERROR);
        }

        TokenBean tokenBean = TokenBean.builder()
                .userId(user.getId())
                .userType(user.getUserType())
                .account(user.getUserName())
                .build();

        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(user, userModel);
        String token;
        try {
            token = JwtUtil.createToken(tokenBean);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new MapleCheckException(ErrorCode.COMMON_ERROR);
        }
        userModel.setToken(token);
        redisUtil.set(GlobalConfig.getRedisUserKey(user.getAccount()), token);
        return userModel;
    }

    @Override
    public void logout() {
        redisUtil.remove(GlobalConfig.getRedisUserKey(JwtUtil.getAccount()));
    }
}
