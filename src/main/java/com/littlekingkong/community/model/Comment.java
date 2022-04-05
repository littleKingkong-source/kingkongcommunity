package com.littlekingkong.community.model;

import lombok.Data;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/4/3 10:43*@since 1.0.0
 */
@Data
public class Comment {
    private Integer id;
    private Long parent_id;
    private Integer type;
    private Long commentator;
    private Long gmt_create;
    private Long gmt_modified;
    private Long like_count;
    private String content;
}

