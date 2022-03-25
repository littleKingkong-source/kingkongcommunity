package com.littlekingkong.community.controller;

import com.littlekingkong.community.model.Question;
import com.littlekingkong.community.model.User;
import com.littlekingkong.community.service.QuestionService;
import com.littlekingkong.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/3/16 20:50*@since 1.0.0
 */
@Controller
public class PublishController {
    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish")
    public String publish() {
        return "/editor/publish";
    }

    @PostMapping("/publish")
    public String publish(@RequestParam(value = "title", required = false) String title,
                          @RequestParam(value = "description", required = false) String description,
                          @RequestParam(value = "tag", required = false) String tag,
                          HttpServletRequest request,
                          Model model) {
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);

        if (title == null || title == "") {
            model.addAttribute("error", "标题不能为空");
            return "/editor/publish";
        }
        if (description == null || description == "") {
            model.addAttribute("error", "问题不能为空");
            return "/editor/publish";
        }
        if (tag == null || tag == "") {
            model.addAttribute("error", "标签不能为空");
            return "/editor/publish";
        }
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "/editor/publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmt_create(System.currentTimeMillis());
        question.setGmt_modified(question.getGmt_create());
        questionService.createQuestion(question);
        return "redirect:/";


    }
}
