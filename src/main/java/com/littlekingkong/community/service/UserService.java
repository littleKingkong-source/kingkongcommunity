package com.littlekingkong.community.service;

import com.littlekingkong.community.model.User;
import org.apache.ibatis.annotations.Param;

public interface UserService {
    //新建用户
    Integer insertUser(@Param("user") User user);

    //查询用户Token
    User findToken(String token);


    void creatOrUpdate(User user);
}
