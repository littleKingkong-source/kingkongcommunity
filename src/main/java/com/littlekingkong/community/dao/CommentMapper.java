package com.littlekingkong.community.dao;

import com.littlekingkong.community.model.Comment;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/4/3 12:33*@since 1.0.0
 */

public interface CommentMapper {
    //评论持久化
    Integer insertComment(Comment comment);

    Comment selectById(Long parent_id);
}
