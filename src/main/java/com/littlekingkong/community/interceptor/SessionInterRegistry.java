package com.littlekingkong.community.interceptor;

import com.littlekingkong.community.model.User;
import com.littlekingkong.community.service.AdService;
import com.littlekingkong.community.service.NotificationService;
import com.littlekingkong.community.service.UserService;
import com.littlekingkong.community.service.impl.AdServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/3/22 14:54*@since 1.0.0
 */
@Service
public class SessionInterRegistry implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AdService adService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler
                            ) throws Exception {


        Cookie[] cookies = request.getCookies();
        if(cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userService.findToken(token);
                    if(user != null) {
                        request.getSession().setAttribute("user", user);
                        Long unReadCount = notificationService.unReadCount(user.getId());
                        request.getSession().setAttribute("unReadMessage",unReadCount);
                        return true;
                    }
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
