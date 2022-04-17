package com.littlekingkong.community.controller;

import com.littlekingkong.community.cache.HotTagCache;
import com.littlekingkong.community.dao.UserMapper;
import com.littlekingkong.community.dto.PaginationDTO;
import com.littlekingkong.community.dto.QuestionDTO;
import com.littlekingkong.community.model.Question;
import com.littlekingkong.community.model.User;
import com.littlekingkong.community.service.AdService;
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
import javax.servlet.http.HttpSession;
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

    @Autowired
    private HotTagCache hotTagCache;

    @Autowired
    private AdService adService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "10") Integer size,
                        @RequestParam(name = "search", required = false) String search,
                        @RequestParam(name = "tag"  , required = false) String tag){
        HttpSession session = request.getSession();
        session.setAttribute("ads",adService.list());

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
        // 最新和全部问题展示
        PaginationDTO pagination = questionService.listSearch(search, tag, page, size);

        // 消去0回复
        PaginationDTO pagination2 = questionService.listZeroCommentQuestion(page,size);


        // 获取热评
        List<String> tags = hotTagCache.getHots();
        System.out.println(tags);

        model.addAttribute("pagination", pagination);
        model.addAttribute("pagination2", pagination2);
        model.addAttribute("search", search);
        model.addAttribute("tag",tag);
        model.addAttribute("tags",tags);
        return "index";
    }

}
