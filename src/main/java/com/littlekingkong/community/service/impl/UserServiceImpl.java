package com.littlekingkong.community.service.impl;

import com.littlekingkong.community.dao.UserMapper;
import com.littlekingkong.community.model.User;
import com.littlekingkong.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/3/14 8:35*@since 1.0.0
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Override
    public Integer insertUser(User user) {
        return userMapper.insertUser(user);
    }

}
