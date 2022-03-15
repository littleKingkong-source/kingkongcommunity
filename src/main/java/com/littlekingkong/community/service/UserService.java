package com.littlekingkong.community.service;

import com.littlekingkong.community.model.User;
import org.apache.ibatis.annotations.Param;

public interface UserService {
    Integer insertUser(@Param("user") User user);
}
