package com.littlekingkong.community.dao;

import com.littlekingkong.community.dto.CommentQuestionDTO;
import com.littlekingkong.community.model.Comment;

import java.util.List;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/4/3 12:33*@since 1.0.0
 */

public interface CommentMapper {
    //评论持久化
    Integer insertComment(Comment comment);


    // 查询一级评论
    Comment selectById(Long parent_id);

    // 查询二级评论
    Comment selectSubCommentById(Long id);

    // 列出评论列表
    List<Comment> selectByQuestion(Comment comment);

    // 增加评论数
    void updateCommentCount(Comment comment);
}
