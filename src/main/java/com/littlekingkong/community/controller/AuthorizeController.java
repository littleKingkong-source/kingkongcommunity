package com.littlekingkong.community.controller;

import com.littlekingkong.community.dto.AccessTokenDTO;
import com.littlekingkong.community.provider.dto.GitHubUser;
import com.littlekingkong.community.model.User;
import com.littlekingkong.community.provider.GitHubProvider;
import com.littlekingkong.community.service.UserService;
import com.littlekingkong.community.strategy.LoginUserInfo;
import com.littlekingkong.community.strategy.UserStrategy;
import com.littlekingkong.community.strategy.UserStrategyFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/3/11 21:45*@since 1.0.0
 */
@Controller
@Slf4j
public class AuthorizeController {

    @Autowired
    private GitHubProvider gitHubProvider;

    @Autowired
    private UserStrategyFactory userStrategyFactory;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Resource
    private UserService userService;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                            HttpServletResponse response) {
        System.out.println("1111111111111111111111111111111111");
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        GitHubUser gitHubUser = gitHubProvider.getUser(accessToken);
        //System.out.println(gitHubUser.toString());
        if(gitHubUser != null) {
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setAccount_id(String.valueOf(gitHubUser.getId()));
            user.setName(gitHubUser.getName());
            user.setToken(token);
            user.setAvatar_url(gitHubUser.getAvatar_url());
            user.setBio(gitHubUser.getBio());
            userService.creatOrUpdate(user);
            //自动写入Cookie
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        }else {
            //登陆失败，重新登录
            log.error("callback github user error {}",gitHubUser);
            return "redirect:/";
        }

    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response) {
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }

    @GetMapping("/callback/{type}")
    public String newCallback(@PathVariable(name = "type") String type,
                              @RequestParam(name = "code") String code,
                              @RequestParam(name = "state", required = false) String state,
                              HttpServletRequest request,
                              HttpServletResponse response) {
        System.out.println(type);
        UserStrategy userStrategy = userStrategyFactory.getStrategy(type);
        System.out.println("==============" + userStrategy);
        LoginUserInfo loginUserInfo = userStrategy.getUser(code, state);
        System.out.println(loginUserInfo);
        if (loginUserInfo != null) {
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(loginUserInfo.getName());
            user.setAccount_id(String.valueOf(loginUserInfo.getId()));
            user.setBio(loginUserInfo.getBio());
            user.setAvatar_url(loginUserInfo.getAvatar_url());
            user.setType(type);
            System.out.println("user=====" + user);
            userService.creatOrUpdate(user);
            Cookie cookie = new Cookie("token", token);
            cookie.setMaxAge(60 * 60 * 24 * 30 * 6);
            cookie.setPath("/");
            response.addCookie(cookie);
            return "redirect:/";
        } else {
            log.error("callback get github error,{}", loginUserInfo);
            // 登录失败，重新登录
            return "redirect:/";
        }
    }



}
