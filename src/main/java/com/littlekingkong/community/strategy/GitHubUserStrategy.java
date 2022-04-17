package com.littlekingkong.community.strategy;


import com.littlekingkong.community.dto.AccessTokenDTO;
import com.littlekingkong.community.provider.GitHubProvider;
import com.littlekingkong.community.provider.dto.GitHubUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GitHubUserStrategy implements UserStrategy{

    @Autowired
    private GitHubProvider gitHubProvider;

    @Override
    public LoginUserInfo getUser(String code, String state) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        GitHubUser gitHubUser = gitHubProvider.getUser(accessToken);
        System.out.println("---------------"+gitHubUser);
        LoginUserInfo loginUserInfo = new LoginUserInfo();
        loginUserInfo.setAvatar_url(gitHubUser.getAvatar_url());
        loginUserInfo.setId(gitHubUser.getId());
        loginUserInfo.setBio(gitHubUser.getBio());
        loginUserInfo.setName(gitHubUser.getName());
        return loginUserInfo;
    }

    @Override
    public String getSupportedType() {
        return "github";
    }
}
