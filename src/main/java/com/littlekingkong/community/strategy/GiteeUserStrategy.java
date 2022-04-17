package com.littlekingkong.community.strategy;

import com.littlekingkong.community.dto.AccessTokenDTO;
import com.littlekingkong.community.provider.GiteeProvider;
import com.littlekingkong.community.provider.dto.GiteeUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/4/17 12:18*@since 1.0.0
 */
@Service
public class GiteeUserStrategy implements UserStrategy{
    @Autowired
    private GiteeProvider giteeProvider;

    @Override
    public LoginUserInfo getUser(String code, String state) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        String accessToken = giteeProvider.getAccessToken(accessTokenDTO);
        GiteeUser giteeUser = giteeProvider.getUser(accessToken);
        System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
        LoginUserInfo loginUserInfo = new LoginUserInfo();
        loginUserInfo.setAvatar_url(giteeUser.getAvatar_url());
        loginUserInfo.setId(giteeUser.getId());
        loginUserInfo.setBio(giteeUser.getBio());
        loginUserInfo.setName(giteeUser.getName());

        return loginUserInfo;
    }

    @Override
    public String getSupportedType() {
        return "gitee";
    }
}
