package com.littlekingkong.community.controller;

import com.littlekingkong.community.dao.UserMapper;
import com.littlekingkong.community.dto.PaginationDTO;
import com.littlekingkong.community.dto.QuestionDTO;
import com.littlekingkong.community.model.Question;
import com.littlekingkong.community.model.User;
import com.littlekingkong.community.service.NotificationService;
import com.littlekingkong.community.service.QuestionService;
import com.littlekingkong.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jws.WebParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/3/11 16:24*@since 1.0.0
 */
@Controller
public class IndexController {
    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size){
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
                    }
                    break;
                }
            }
        }
        PaginationDTO pagination = questionService.list2(page, size);
        model.addAttribute("pagination", pagination);

//        List<QuestionDTO> questionDTOList = questionService.listQuestion(page,size);
//        model.addAttribute("questions",questionDTOList);
        return "index";
    }

}
