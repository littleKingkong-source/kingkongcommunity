package com.littlekingkong.community.dto;

import com.littlekingkong.community.model.User;
import lombok.Data;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/3/18 23:28*@since 1.0.0
 */
@Data
public class QuestionDTO {
    private Long id;
    private String title;
    private String description;
    private Long gmt_create;
    private Long gmt_modified;
    private Long creator;
    private Integer comment_count;
    private Integer view_count;
    private Integer like_count;
    private String tag;

    private User user;
}
