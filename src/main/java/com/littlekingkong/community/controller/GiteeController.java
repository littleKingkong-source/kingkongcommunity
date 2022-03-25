package com.littlekingkong.community.controller;

import com.littlekingkong.community.dto.AccessTokenDTO;
import com.littlekingkong.community.dto.GiteeUser;
import com.littlekingkong.community.model.User;
import com.littlekingkong.community.provider.GiteeProvider;
import com.littlekingkong.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/3/25 9:55*@since 1.0.0
 */
@Controller
public class GiteeController {

    @Autowired
    private GiteeProvider giteeProvider;

    @Autowired
    private UserService userService;

    @GetMapping("/callback/gitee")
    public String callbackGitee(@RequestParam(name = "code") String code,
                                @RequestParam(name = "state") String state,
                                HttpServletResponse response,HttpServletRequest request) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("297f2d6977941e7e464986e04fe3ea17d044edddef4625602de808f34009189d");
        accessTokenDTO.setClient_secret("e734d4fae7956128cb49ab5c81194a2b38960ea3f7020dfb8a25b8bcf8592b63");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri("http://localhost:8777/callback/gitee");
        String accessToken = giteeProvider.getAccessToken(accessTokenDTO);
        GiteeUser giteeUser = giteeProvider.getGiteeUser(accessToken);
        System.out.println(giteeUser.toString());
        if (giteeUser != null) {
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setAccount_id(String.valueOf(giteeUser.getId()));
            user.setName(giteeUser.getName());
            user.setToken(token);
            user.setAvatar_url(giteeUser.getAvatar_url());
            userService.creatOrUpdate(user);
            System.out.println(user);
            //自动写入Cookie
            Cookie cookie = new Cookie("token",token);
            response.addCookie(cookie);
            return "redirect:/";
        } else {
            //登陆失败，重新登录
            return "redirect:/";
        }


    }
}
