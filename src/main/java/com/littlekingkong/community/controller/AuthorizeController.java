package com.littlekingkong.community.controller;

import com.littlekingkong.community.dto.AccessTokenDTO;
import com.littlekingkong.community.dto.GitHubUser;
import com.littlekingkong.community.model.User;
import com.littlekingkong.community.provider.GitHubProvider;
import com.littlekingkong.community.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/3/11 21:45*@since 1.0.0
 */
@Controller
public class AuthorizeController {

    @Autowired
    private GitHubProvider gitHubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("/github.client.secret")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

//    @Resource
//    private UserMapper userMapper;

    @Resource
    private UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                            HttpServletRequest request) {

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();

        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        GitHubUser gitHubUser = gitHubProvider.getUser(accessToken);
        if(gitHubUser != null) {
            User user = new User();
            user.setAccount_id(String.valueOf(gitHubUser.getId()));
            user.setName(gitHubUser.getName());
            user.setToken(UUID.randomUUID().toString());
            user.setGmt_create(System.currentTimeMillis());
            user.setGmt_modified(user.getGmt_create());
            userService.insertUser(user);

            //登录成功，写cookie 和 session
            System.out.println(gitHubUser.getName()+gitHubUser.getBio()+gitHubUser.getId());
            System.out.println(gitHubUser);
            //登录成功，写cookie和 session
            request.getSession().setAttribute("gitHubUser",gitHubUser);
            return "redirect:/";
        }else {
            //登陆失败，重新登录
            return "redirect:/";
        }

    }
}