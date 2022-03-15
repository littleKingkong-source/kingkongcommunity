package com.littlekingkong.community.dao;

import com.littlekingkong.community.model.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    Integer insertUser(User user);
    User findByTonken(String token);
}
