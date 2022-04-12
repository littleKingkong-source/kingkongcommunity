package com.littlekingkong.community.controller;

import com.littlekingkong.community.dto.NotificationDTO;
import com.littlekingkong.community.enums.NotificationTypeEnum;
import com.littlekingkong.community.model.User;
import com.littlekingkong.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/4/13 1:03*@since 1.0.0
 */
@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;
    // outerId是问题Id，或者是该评论的Id
    // 标记通知为已读
    @GetMapping("/notification/{id}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "id") Long id) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        NotificationDTO notificationDTO = notificationService.read(id, user);
        if (NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType() ||
            NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType()) {
            return "redirect:/question/" + notificationDTO.getOuterId();
        } else {
            return "redirect:/";
        }

    }
}
