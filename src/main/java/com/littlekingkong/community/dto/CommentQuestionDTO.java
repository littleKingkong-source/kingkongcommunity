package com.littlekingkong.community.dto;

import com.littlekingkong.community.model.User;
import lombok.Data;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/4/6 16:18*@since 1.0.0
 */
@Data
public class CommentQuestionDTO {
    private Long id;
    private Long parent_id;
    private Integer type;
    private Long commentator;
    private Long gmt_create;
    private Long gmt_modified;
    private String content;
    private User user;
}
