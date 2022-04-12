package com.littlekingkong.community.controller;

import com.littlekingkong.community.dto.PaginationDTO;
import com.littlekingkong.community.model.User;
import com.littlekingkong.community.service.NotificationService;
import com.littlekingkong.community.service.QuestionService;
import com.littlekingkong.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/3/21 14:54*@since 1.0.0
 */
@Controller
public class ProfileController {


    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @Resource
    private NotificationService notificationService;

    //分页功能
    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action, Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

        if("questions".equals(action)) {
            model.addAttribute("section", "questions");
            model.addAttribute("sectionName", "我的提问");
            PaginationDTO paginationDTO = questionService.listUserQuestion(user.getId(), page, size);
            model.addAttribute("pagination",paginationDTO);
        } else if("replies".equals(action)) {
            PaginationDTO paginationDTO = notificationService.list(user.getId(), page, size);
            model.addAttribute("section","replies");
            model.addAttribute("pagination",paginationDTO);
            model.addAttribute("sectionName","最新回复");
        }
        return "/editor/profile";
    }


}
