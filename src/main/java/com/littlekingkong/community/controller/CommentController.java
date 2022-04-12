package com.littlekingkong.community.controller;

import com.littlekingkong.community.dto.CommentDTO;
import com.littlekingkong.community.dto.CommentQuestionDTO;
import com.littlekingkong.community.dto.ResultDTO;
import com.littlekingkong.community.enums.CommentTypeEnum;
import com.littlekingkong.community.exception.CustomizeErrorCode;
import com.littlekingkong.community.model.Comment;
import com.littlekingkong.community.model.User;
import com.littlekingkong.community.service.CommentService;
import com.littlekingkong.community.service.NotificationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NOT_LOGIN);
        }
        if (commentDTO == null || StringUtils.isBlank(commentDTO.getContent())) {
            return ResultDTO.errorOf(CustomizeErrorCode.COMMENT_IS_NULL);
        }
        System.out.println(commentDTO);
        Comment comment = new Comment();
        comment.setParent_id(commentDTO.getParent_id());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmt_create(System.currentTimeMillis());
        comment.setGmt_modified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLike_count(0L);
        System.out.println(comment);
        commentService.insertComment(comment,user);

        return ResultDTO.okOf();
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDTO<List> comments(@PathVariable(name = "id") Long id) {
        List<CommentQuestionDTO> commentQuestionDTOS = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okOf(commentQuestionDTOS);
    }



}
