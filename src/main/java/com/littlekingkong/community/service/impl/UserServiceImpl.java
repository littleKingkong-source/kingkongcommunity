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

    //实现新用户登录，向数据库记录信息
    @Override
    public Integer insertUser(User user) {
        return userMapper.insertUser(user);
    }


    //自动获取token
    @Override
    public User findToken(String token) {
        return userMapper.findByTonken(token);
    }

    //判断数据库是否存在该用户，存在则更新token
    @Override
    public void creatOrUpdate(User user) {
        User dbUser = userMapper.findByAccountId(String.valueOf(user.getId()));
        if (dbUser == null) {
            //用户不存在则，视为注册
            user.setGmt_create(System.currentTimeMillis());
            user.setGmt_modified(user.getGmt_create());
            userMapper.insertUser(user);
        } else {
            dbUser.setGmt_modified(System.currentTimeMillis());
            dbUser.setAvatar_url(user.getAvatar_url());
            dbUser.setName(user.getName());
            dbUser.setToken(user.getToken());
            //用户存在，更新
            userMapper.update(dbUser);
        }
    }

}
