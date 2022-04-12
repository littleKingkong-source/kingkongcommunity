package com.littlekingkong.community.service;

import com.littlekingkong.community.dto.CommentDTO;
import com.littlekingkong.community.dto.CommentQuestionDTO;
import com.littlekingkong.community.enums.CommentTypeEnum;
import com.littlekingkong.community.model.Comment;
import com.littlekingkong.community.model.Question;
import com.littlekingkong.community.model.User;

import java.util.List;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/4/3 12:11*@since 1.0.0
 */

public interface CommentService {
     // 持久化评论
     void insertComment(Comment comment, User user);

    List<CommentQuestionDTO> listByTargetId(Long id, CommentTypeEnum type);

    // 评论回复数

}
