package com.littlekingkong.community.controller;

import com.littlekingkong.community.dto.CommentDTO;
import com.littlekingkong.community.dto.ResultDTO;
import com.littlekingkong.community.exception.CustomizeErrorCode;
import com.littlekingkong.community.model.Comment;
import com.littlekingkong.community.model.User;
import com.littlekingkong.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/4/3 11:23*@since 1.0.0
 */
@Controller
public class CommentController {

    @Resource
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        System.out.println(user);
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NOT_LOGIN);
        }
        Comment comment = new Comment();
        comment.setParent_id(commentDTO.getParent_id());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmt_create(System.currentTimeMillis());
        comment.setGmt_modified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLike_count(0L);
        System.out.println("----------"+comment);
        commentService.insertComment(comment);
        return ResultDTO.okOf();
    }
}
