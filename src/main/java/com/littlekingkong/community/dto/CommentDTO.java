package com.littlekingkong.community.dto;

import lombok.Data;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/4/3 11:57*@since 1.0.0
 */
@Data
public class CommentDTO {
    private Long parent_id;
    private String content;
    private Integer type;
}
