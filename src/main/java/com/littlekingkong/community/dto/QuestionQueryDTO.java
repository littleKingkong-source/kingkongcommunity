package com.littlekingkong.community.dto;

import lombok.Data;

/**
 * *
 *
 * @author 邹玉沛
 * @date 2022/4/15 15:06*@since 1.0.0
 */
@Data
public class QuestionQueryDTO {
    private String search;
    private String tag;
    private Integer page;
    private Integer size;
}
