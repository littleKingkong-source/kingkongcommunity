package com.littlekingkong.community.dao;

import com.littlekingkong.community.model.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    //新用户注册
    Integer insertUser(User user);
    //查询token
    User findByTonken(String token);

    //根据用户id查询用户
    User findById(@Param("id") Long id);

    //根据accountId查询用户
    User findByAccountId(@Param("account_id") String account_id);

    void update(User dbUser);
}
