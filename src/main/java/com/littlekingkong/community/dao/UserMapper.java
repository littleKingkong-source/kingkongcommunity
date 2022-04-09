package com.littlekingkong.community.dao;

import com.littlekingkong.community.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    //新用户注册
    Integer insertUser(User user);
    //查询token
    User findByTonken(String token);

    //根据用户id查询用户
    User findById(@Param("id") Long id);

    //根据accountId查询用户
    User findByAccountId(@Param("account_id") String account_id);

    // 更新用户信息
    void update(User dbUser);


    // 用户表单
    List<User> selectByList(List<Long> userIds);
}
