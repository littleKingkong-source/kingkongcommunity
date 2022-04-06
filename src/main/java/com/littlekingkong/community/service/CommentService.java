package com.littlekingkong.community.service;

import com.littlekingkong.community.dto.CommentDTO;
import com.littlekingkong.community.dto.CommentQuestionDTO;
import com.littlekingkong.community.model.Comment;
import com.littlekingkong.community.model.Question;

import java.util.List;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/4/3 12:11*@since 1.0.0
 */

public interface CommentService {
     // 持久化评论
     void insertComment(Comment comment);

    List<CommentQuestionDTO> listByQuestionId(Long id);

    // 评论回复数

}
