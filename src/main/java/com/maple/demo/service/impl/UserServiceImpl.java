package com.maple.demo.service.impl;

import com.maple.demo.entity.User;
import com.maple.demo.mapper.UserMapper;
import com.maple.demo.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户中心-用户信息表 服务实现类
 * </p>
 *
 * @author 笑小枫
 * @since 2022-07-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
