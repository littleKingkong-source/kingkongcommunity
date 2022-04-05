package com.littlekingkong.community.controller;

import com.littlekingkong.community.model.Question;
import com.littlekingkong.community.model.User;
import com.littlekingkong.community.service.QuestionService;
import com.littlekingkong.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * *flyway_schema_history
 *
 * @author 邹玉沛
 * @date 2022/3/16 20:50*@since 1.0.0
 */
@Controller
public class PublishController {

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
                          @RequestParam(value = "id", required = false) Long id,
                          @RequestParam(value = "comment_count",defaultValue = "0",required = false) Integer comment_count,
                          @RequestParam(value = "view_count",defaultValue = "0",required = false) Integer view_count,
                          @RequestParam(value = "like_count",defaultValue = "0",required = false) Integer like_count,
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
        question.setId(id);
        question.setLike_count(like_count);
        question.setComment_count(comment_count);
        question.setView_count(view_count);
        questionService.createOrUpdate(question);

        //questionService.createQuestion(question);
        return "redirect:/";

    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Long id,
                       Model model) {

        Question question = questionService.getQuestionById(id);
        System.out.println(question);
        model.addAttribute("title", question.getTitle());
        model.addAttribute("description", question.getDescription());
        model.addAttribute("tag", question.getTag());
        model.addAttribute("id", question.getId());
        return "editor/publish";
    }

}
