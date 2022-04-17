package com.littlekingkong.community.strategy;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/4/17 12:02*@since 1.0.0
 */

public interface UserStrategy {
    LoginUserInfo getUser(String code, String state);
    String getSupportedType();
}
